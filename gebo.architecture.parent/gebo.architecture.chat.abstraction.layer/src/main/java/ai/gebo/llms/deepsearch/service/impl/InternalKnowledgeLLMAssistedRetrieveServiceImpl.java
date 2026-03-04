package ai.gebo.llms.deepsearch.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ai.gebo.architecture.ai.model.GPromptConfig;
import ai.gebo.architecture.ai.service.IGPromptConfigDao;
import ai.gebo.architecture.fulltext.service.FullTextException;
import ai.gebo.architecture.rag.support.layer.model.AIDocumentsSet;
import ai.gebo.architecture.rag.support.layer.services.IGAIDocumentsCacheService;
import ai.gebo.knlowledgebase.model.contents.GKnowledgeBase;
import ai.gebo.knowledgebase.repositories.DocumentReferenceRepository;
import ai.gebo.llms.abstraction.layer.services.BaseLLMSInvokingService;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.chat.abstraction.layer.config.GeboPromptsLibrary;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMRequestGenerationPolicy;
import ai.gebo.llms.chat.abstraction.layer.repository.LLMGeneratedResourceRepository;
import ai.gebo.llms.chat.abstraction.layer.repository.UserUploadContentServerSideRepository;
import ai.gebo.llms.chat.abstraction.layer.services.CommonChatPromptParamsUtil;
import ai.gebo.llms.chat.abstraction.layer.services.GeboChatSessionLifecycleException;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatSessionLifeCycleService;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatStorageAreaService;
import ai.gebo.llms.chat.abstraction.layer.services.IGDocumentsSearchService;
import ai.gebo.llms.chat.abstraction.layer.session.model.MinimalChatContext;
import ai.gebo.llms.chat.pipelines.config.ChatPipelinesConfiguration;
import ai.gebo.llms.chat.pipelines.model.SearchesSuggestions;
import ai.gebo.llms.chat.pipelines.service.IInternalKnowledgeLLMAssistedRetrieveService;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;

@AllArgsConstructor
public class InternalKnowledgeLLMAssistedRetrieveServiceImpl extends BaseLLMSInvokingService
		implements IInternalKnowledgeLLMAssistedRetrieveService {
	private static final String DOCUMENTS = "documents";
	private static final String FULL_TEXT_QUERIES_FIELD = "fullTextQueries";
	private static final String SEMANTIC_QUERIES_FIELD = "semanticQueries";
	protected final IGAIDocumentsCacheService documentsCacheService;
	protected final IGChatStorageAreaService chatStorageAreaService;
	protected final DocumentReferenceRepository docreferenceRepo;
	protected final UserUploadContentServerSideRepository uploadsRepo;
	protected final LLMGeneratedResourceRepository generatedRepo;
	protected final IGChatSessionLifeCycleService chatSessionLifecycleService;
	protected final ChatPipelinesConfiguration configuration;
	protected final IGPromptConfigDao promptsDao;
	private final IGDocumentsSearchService searchesService;

	private SearchesSuggestions askSearchesSuggestion(MinimalChatContext minimalChatContext,
			IGConfigurableChatModel targetChatModel) {
		GPromptConfig prompt = promptsDao
				.findByPromptUse(GeboPromptsLibrary.DEFAULT_PIPELINE_RAG_SEARCH_PLANNER_PROMPT);
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> chatContextParams = CommonChatPromptParamsUtil.preparePromptParameters(minimalChatContext);
		params.putAll(chatContextParams);
		Map<String, List<String>> fieldEntries = callLLMRepeatableFieldEntryOutput(targetChatModel, prompt.getPrompt(),
				GeboChatRequest.actualQuery(minimalChatContext.getCurrentRequest()), params,
				List.of(SEMANTIC_QUERIES_FIELD, FULL_TEXT_QUERIES_FIELD));
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
			IGConfigurableChatModel targetChatModel, LLMRequestGenerationPolicy policy)
			throws GeboChatSessionLifecycleException, FullTextException, LLMConfigException {
		List<GKnowledgeBase> knowledgeBases = chatSessionLifecycleService
				.getSessionAvailableKnowledgeBases(minimalChatContext.getCurrentRequest());
		Flux<AIDocumentsSet> flux = Flux.defer(() -> {
			AIDocumentsSet de = null;

			try {
				SearchesSuggestions searchSuggestions = askSearchesSuggestion(minimalChatContext, targetChatModel);
				de = this.integrateWithAISuggestedSearch(minimalChatContext, targetChatModel, searchSuggestions, knowledgeBases,
						policy);

			} catch (Throwable e) {
				String msg = "Error accessing search/llm assisted";
				LOGGER.error(msg, e);
				de = new AIDocumentsSet();
			}
			return Flux.just(de);
		});
		return flux;
	}

	private AIDocumentsSet integrateWithAISuggestedSearch(MinimalChatContext minimalChatContext,
			IGConfigurableChatModel targetChatModel, SearchesSuggestions searchSuggestions,
			List<GKnowledgeBase> knowledgeBases, LLMRequestGenerationPolicy policy)
			throws GeboChatSessionLifecycleException, FullTextException, LLMConfigException {

		AIDocumentsSet out = new AIDocumentsSet();

		AIDocumentsSet searchResult = this.search(minimalChatContext, searchSuggestions, targetChatModel,
				targetChatModel.getContextLength());
		out = AIDocumentsSet.join(out, searchResult);

		return out;
	}

	private AIDocumentsSet search(MinimalChatContext minimalChatContext, SearchesSuggestions searchRewritings,
			IGConfigurableChatModel targetChatModel, int contextWindowLength)
			throws FullTextException, LLMConfigException, GeboChatSessionLifecycleException {

		int tokensBudget = contextWindowLength / 4;
		AIDocumentsSet documentSet = searchesService.search(minimalChatContext.getCurrentRequest(),
				searchRewritings.getRewrittenSemanticSearchSentences(),
				searchRewritings.getRewrittenFullTextSearchSentences(),
				GeboChatRequest.actualQuery(minimalChatContext.getCurrentRequest()), configuration.getGlobalRagTopK(),
				tokensBudget);

		return documentSet;
	}

}
