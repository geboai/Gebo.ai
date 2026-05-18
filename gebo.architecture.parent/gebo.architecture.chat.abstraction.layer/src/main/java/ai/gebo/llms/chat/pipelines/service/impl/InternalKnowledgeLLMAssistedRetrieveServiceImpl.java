package ai.gebo.llms.chat.pipelines.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ai.gebo.acl.AclGrantType;
import ai.gebo.acl.ContentAccessPolicy;
import ai.gebo.architecture.ai.model.GPromptTemplateConfig;
import ai.gebo.architecture.ai.service.IGPromptConfigDao;
import ai.gebo.architecture.fulltext.model.FullTextSearchMetaDataFilter;
import ai.gebo.architecture.fulltext.service.FullTextException;
import ai.gebo.architecture.rag.support.layer.model.AIDocumentsSet;
import ai.gebo.architecture.rag.support.layer.model.SemanticSearchMetaDataFilter;
import ai.gebo.knlowledgebase.model.contents.GKnowledgeBase;
import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;
import ai.gebo.llms.abstraction.layer.services.BaseLLMSInvokingService;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.chat.abstraction.layer.config.GeboPromptsLibrary;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMRequestGenerationPolicy;
import ai.gebo.llms.chat.abstraction.layer.services.CommonChatPromptParamsUtil;
import ai.gebo.llms.chat.abstraction.layer.services.GeboChatSessionLifecycleException;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatSessionLifeCycleService;
import ai.gebo.llms.chat.abstraction.layer.services.IGDocumentsSearchService;
import ai.gebo.llms.chat.abstraction.layer.services.IGRankerService;
import ai.gebo.llms.chat.abstraction.layer.session.model.MinimalChatContext;
import ai.gebo.llms.chat.pipelines.config.ChatPipelinesConfiguration;
import ai.gebo.llms.chat.pipelines.model.SearchesSuggestions;
import ai.gebo.llms.chat.pipelines.service.IInternalKnowledgeLLMAssistedRetrieveService;
import ai.gebo.security.services.IGSecurityService;
import ai.gebo.security.services.ReactiveIdentityUtil;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;

@AllArgsConstructor
public class InternalKnowledgeLLMAssistedRetrieveServiceImpl extends BaseLLMSInvokingService
		implements IInternalKnowledgeLLMAssistedRetrieveService {
	private static final String DOCUMENTS = "documents";
	private static final String FULL_TEXT_QUERIES_FIELD = "fullTextQueries";
	private static final String SEMANTIC_QUERIES_FIELD = "semanticQueries";

	private final IGChatSessionLifeCycleService chatSessionLifecycleService;
	private final ChatPipelinesConfiguration configuration;
	private final IGPromptConfigDao promptsDao;
	private final IGDocumentsSearchService searchesService;
	private final IGSecurityService securityService;
	private final IGRankerService rankerService;

	private SearchesSuggestions askSearchesSuggestion(MinimalChatContext minimalChatContext,
			IGConfigurableChatModel targetChatModel) throws LLMConfigException {
		GPromptTemplateConfig prompt = promptsDao
				.findByPromptUse(GeboPromptsLibrary.DEFAULT_PIPELINE_RAG_SEARCH_PLANNER_PROMPT);
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> chatContextParams = new HashMap<>();
		params.putAll(chatContextParams);
		IChatRequestContext context = minimalChatContext.createChatRequestContext();
		Map<String, List<String>> fieldEntries = callLLMRepeatableFieldEntryOutput(targetChatModel, prompt, context,
				params, List.of(SEMANTIC_QUERIES_FIELD, FULL_TEXT_QUERIES_FIELD));
		SearchesSuggestions outValue = new SearchesSuggestions();
		outValue.setRewrittenFullTextSearchSentences(fieldEntries.get(FULL_TEXT_QUERIES_FIELD));
		outValue.setRewrittenSemanticSearchSentences(fieldEntries.get(SEMANTIC_QUERIES_FIELD));

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Searches suggestions:" + outValue);
		}
		return outValue;
	}

	@Override
	public Flux<AIDocumentsSet> doDocumentsRetrieve(MinimalChatContext minimalChatContext,
			IGConfigurableChatModel targetChatModel, LLMRequestGenerationPolicy policy, int topK)
			throws GeboChatSessionLifecycleException, LLMConfigException {
		List<GKnowledgeBase> knowledgeBases = chatSessionLifecycleService
				.getSessionAvailableKnowledgeBases(minimalChatContext.getCurrentRequest());
		final ReactiveIdentityUtil runAs = ReactiveIdentityUtil.create();

		Flux<AIDocumentsSet> flux = Flux.defer(() -> {
			return runAs.doRunAsWithReturn(() -> {
				AIDocumentsSet de = null;

				try {
					SearchesSuggestions searchSuggestions = askSearchesSuggestion(minimalChatContext, targetChatModel);
					de = this.integrateWithAISuggestedSearch(minimalChatContext, targetChatModel, searchSuggestions,
							knowledgeBases, policy, topK);

				} catch (Throwable e) {
					String msg = "Error accessing search/llm assisted";
					LOGGER.error(msg, e);
					de = new AIDocumentsSet();
				}
				return Flux.just(de);
			});
		});

		return flux;

	}

	private AIDocumentsSet integrateWithAISuggestedSearch(MinimalChatContext minimalChatContext,
			IGConfigurableChatModel targetChatModel, SearchesSuggestions searchSuggestions,
			List<GKnowledgeBase> knowledgeBases, LLMRequestGenerationPolicy policy, int topK)
			throws GeboChatSessionLifecycleException, FullTextException, LLMConfigException {

		AIDocumentsSet out = new AIDocumentsSet();
		SemanticSearchMetaDataFilter semanticSearchMetaDataFilter = new SemanticSearchMetaDataFilter();
		FullTextSearchMetaDataFilter fullTextSearchMetaDataFilter = new FullTextSearchMetaDataFilter();
		List<String> kbs = knowledgeBases.stream().map(x -> x.getCode()).toList();
		if (securityService.getPlatformContentAccessPolicy() == ContentAccessPolicy.ACL_BASED
				&& !securityService.isCurrentUserAdmin()) {
			List<Integer> aclAliases = securityService.getCurrentAclGrantedAccessor(AclGrantType.READ)
					.getAllOwnedAclAliases();
			fullTextSearchMetaDataFilter.setAclAliases(aclAliases);
			semanticSearchMetaDataFilter.setAclAliases(aclAliases);
		}
		fullTextSearchMetaDataFilter.setKnowledgebaseCodes(kbs);
		semanticSearchMetaDataFilter.setKnowledgeBasesCodes(kbs);
		final boolean userRanker = rankerService.isRankerConfigured();
		final int calculatedTopK = userRanker ? topK * 2 : topK;
		AIDocumentsSet searchResult = this.search(minimalChatContext, searchSuggestions, targetChatModel,
				targetChatModel.getContextLength(), semanticSearchMetaDataFilter, fullTextSearchMetaDataFilter,
				calculatedTopK);
		out = AIDocumentsSet.join(out, searchResult);

		return userRanker
				? this.rankerService.call(out, GeboChatRequest.actualQuery(minimalChatContext.getCurrentRequest()),
						topK)
				: out;
	}

	private AIDocumentsSet search(MinimalChatContext minimalChatContext, SearchesSuggestions searchRewritings,
			IGConfigurableChatModel targetChatModel, int contextWindowLength,
			SemanticSearchMetaDataFilter semanticSearchMetaDataFilter,
			FullTextSearchMetaDataFilter fullTextSearchMetaDataFilter, int topK)
			throws FullTextException, LLMConfigException, GeboChatSessionLifecycleException {

		int tokensBudget = contextWindowLength / 4;
		AIDocumentsSet documentSet = searchesService.search(minimalChatContext.getCurrentRequest(),
				searchRewritings.getRewrittenSemanticSearchSentences(), semanticSearchMetaDataFilter,
				searchRewritings.getRewrittenFullTextSearchSentences(), fullTextSearchMetaDataFilter,
				GeboChatRequest.actualQuery(minimalChatContext.getCurrentRequest()), topK, tokensBudget);

		return documentSet;
	}

}
