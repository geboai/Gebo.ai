package ai.gebo.llms.deepsearch.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.ai.document.Document;

import ai.gebo.architecture.ai.model.GPromptConfig;
import ai.gebo.architecture.ai.service.IGPromptConfigDao;
import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.architecture.fulltext.service.FullTextException;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.architecture.rag.support.layer.model.AIDocumentReferenceItem;
import ai.gebo.architecture.rag.support.layer.model.AIDocumentsSet;
import ai.gebo.architecture.rag.support.layer.services.IGAIDocumentsCacheService;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.knlowledgebase.model.contents.GKnowledgeBase;
import ai.gebo.knowledgebase.repositories.DocumentReferenceRepository;
import ai.gebo.llms.abstraction.layer.services.BaseLLMSInvokingService;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.chat.abstraction.layer.config.GeboPromptsLibrary;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMChatRequestResources;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMGeneratedResource;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMRequestGenerationPolicy;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.UserUploadContentServerSide;
import ai.gebo.llms.chat.abstraction.layer.repository.LLMGeneratedResourceRepository;
import ai.gebo.llms.chat.abstraction.layer.repository.UserUploadContentServerSideRepository;
import ai.gebo.llms.chat.abstraction.layer.services.CommonChatPromptParamsUtil;
import ai.gebo.llms.chat.abstraction.layer.services.GeboChatSessionLifecycleException;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatSessionLifeCycleService;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatStorageAreaService;
import ai.gebo.llms.chat.abstraction.layer.services.IGDocumentsSearchService;
import ai.gebo.llms.chat.pipelines.config.ChatPipelinesConfiguration;
import ai.gebo.llms.chat.pipelines.model.ChatPipelineExecutionRuntimeData;
import ai.gebo.llms.chat.pipelines.model.DocumentsEnrichDecision;
import ai.gebo.llms.chat.pipelines.model.SearchesSuggestions;
import ai.gebo.llms.chat.pipelines.service.IInternalKnowledgeLLMAssistedRetrieveService;
import ai.gebo.llms.chat.pipelines.service.defaultsteps.impl.DefaultPipelineSharedPromptPlaceholders;
import ai.gebo.system.ingestion.GeboIngestionException;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;

@AllArgsConstructor
public class InternalKnowledgeLLMAssistedRetrieveServiceImpl extends BaseLLMSInvokingService
		implements IInternalKnowledgeLLMAssistedRetrieveService {
	private static final String DOCUMENT_CODES_FIELD = "documentCodes";
	private static final String FULL_TEXT_QUERIES_FIELD = "fullTextQueries";
	private static final String SEMANTIC_QUERIES_FIELD = "semanticQueries";
	private static final String DATASOURCES_FIELD = "datasources";
	protected final IGAIDocumentsCacheService documentsCacheService;
	protected final IGChatStorageAreaService chatStorageAreaService;
	protected final DocumentReferenceRepository docreferenceRepo;
	protected final UserUploadContentServerSideRepository uploadsRepo;
	protected final LLMGeneratedResourceRepository generatedRepo;
	protected final IGChatSessionLifeCycleService chatSessionLifecycleService;
	protected final ChatPipelinesConfiguration configuration;
	protected final IGPromptConfigDao promptsDao;
	private final IGDocumentsSearchService searchesService;

	private SearchesSuggestions askSearchesSuggestion(ChatPipelineExecutionRuntimeData runtimeData,
			IGConfigurableChatModel targetChatModel) {
		GPromptConfig prompt = promptsDao.findByPromptUse(GeboPromptsLibrary.DEFAULT_PIPELINE_SEARCH_PLANNER_PROMPT);
		Map<String, Object> params = DefaultPipelineSharedPromptPlaceholders.extractSharedPromptParameters(
				runtimeData.getSharedEnvironment(),
				DefaultPipelineSharedPromptPlaceholders.DEEP_SEARCH_DATA_SOURCES_TEMPLATE_PARAM,
				DefaultPipelineSharedPromptPlaceholders.INTERNAL_KNOWLEDGE_BASE_CATALOG_TEMPLATE_PARAM,
				DefaultPipelineSharedPromptPlaceholders.DOCUMENTS_TEMPLATE_PARAM,
				DefaultPipelineSharedPromptPlaceholders.LATEST_INTERACTIONS_TEMPLATE_PARAM);
		Map<String, Object> chatContextParams = CommonChatPromptParamsUtil
				.preparePromptParameters(runtimeData.getMinimalChatContext());
		params.putAll(chatContextParams);
		Map<String, List<String>> fieldEntries = callLLMRepeatableFieldEntryOutput(targetChatModel, prompt.getPrompt(),
				GeboChatRequest.actualQuery(runtimeData.getRequestResources().getCurrentRequest()), params,
				List.of(DATASOURCES_FIELD, SEMANTIC_QUERIES_FIELD, FULL_TEXT_QUERIES_FIELD, DOCUMENT_CODES_FIELD));
		SearchesSuggestions outValue = new SearchesSuggestions();
		outValue.setDeepSearchDataSources(fieldEntries.get(DATASOURCES_FIELD));
		outValue.setRewrittenFullTextSearchSentences(fieldEntries.get(FULL_TEXT_QUERIES_FIELD));
		outValue.setRewrittenSemanticSearchSentences(fieldEntries.get(SEMANTIC_QUERIES_FIELD));
		outValue.setSuggestedDocuments(fieldEntries.get(DOCUMENT_CODES_FIELD));
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Searches suggestions:" + outValue);
		}
		return outValue;
	}

	@Override
	public Flux<DocumentsEnrichDecision> doDocumentsRetrieve(ChatPipelineExecutionRuntimeData runtimeData,
			IGConfigurableChatModel targetChatModel, LLMRequestGenerationPolicy policy)
			throws GeboChatSessionLifecycleException, FullTextException, LLMConfigException {
		List<GKnowledgeBase> knowledgeBases = chatSessionLifecycleService.getSessionAvailableKnowledgeBases(runtimeData.getRequestResources().getCurrentRequest());
		Flux<DocumentsEnrichDecision> flux = Flux.defer(() -> {
			DocumentsEnrichDecision de = null;
			LLMChatRequestResources req;
			try {
				SearchesSuggestions searchSuggestions = askSearchesSuggestion(runtimeData, targetChatModel);
				req = this.integrateWithAISuggestedSearchAndDocuments(runtimeData, targetChatModel, searchSuggestions,knowledgeBases,
						policy);
				de = new DocumentsEnrichDecision(req, searchSuggestions);
			} catch (Throwable e) {
				String msg = "Error accessing search/llm assisted";
				LOGGER.error(msg, e);
				de = new DocumentsEnrichDecision(runtimeData.getRequestResources(), new SearchesSuggestions());
			}
			return Flux.just(de);
		});
		return flux;
	}

	private LLMChatRequestResources integrateWithAISuggestedSearchAndDocuments(
			ChatPipelineExecutionRuntimeData runtimeData, IGConfigurableChatModel targetChatModel,
			SearchesSuggestions searchSuggestions, List<GKnowledgeBase> knowledgeBases, LLMRequestGenerationPolicy policy)
			throws GeboChatSessionLifecycleException, FullTextException, LLMConfigException {

		List<String> docsList = searchSuggestions.getSuggestedDocuments();
		LLMChatRequestResources rc = runtimeData.getRequestResources();
		AIDocumentsSet out = new AIDocumentsSet();
		if (docsList != null && !docsList.isEmpty()) {
			LOGGER.info("Try loading AI suggested docs:" + docsList);

			for (String docId : docsList) {

				AIDocumentReferenceItem item = runtimeData.getRequestResources().findAIDocumentReferenceByCode(docId);
				if (item != null) {
					runtimeData.getRequestResources().removeAIDocumentReferenceByCode(docId);
				}
				AIDocumentReferenceItem ingested = null;
				try {
					Optional<GDocumentReference> docopt = docreferenceRepo.findById(docId);
					if (docopt.isPresent()) {

						ingested = documentsCacheService.retrieve(docopt.get());

					} else {
						Optional<UserUploadContentServerSide> uploadedopt = uploadsRepo.findById(docId);
						if (uploadedopt.isPresent()) {
							List<Document> documents = chatStorageAreaService.getIngestedContentsOf(uploadedopt.get());
							if (!documents.isEmpty()) {
								AIDocumentsSet set = AIDocumentsSet.from(documents);
								if (set.getDocumentItems().size() > 0) {
									ingested = set.getDocumentItems().get(0);
								}
							}
						} else {
							Optional<LLMGeneratedResource> generatedopt = generatedRepo.findById(docId);
							if (generatedopt.isPresent()) {
								List<Document> documents = chatStorageAreaService
										.getIngestedContentsOf(generatedopt.get());
								if (!documents.isEmpty()) {
									AIDocumentsSet set = AIDocumentsSet.from(documents);
									if (set.getDocumentItems().size() > 0) {
										ingested = set.getDocumentItems().get(0);
									}
								}
							} else {
								LOGGER.error("The code " + docId
										+ " is not a documentref or uploaded or generated document");
							}
						}
					}
					if (ingested != null) {
						out.getDocumentItems().add(ingested);
						out.recalculateSize();
					} else {
						LOGGER.error("The code " + docId
								+ " cannot be retrieved as documentref or uploaded or generated document");
					}
				} catch (GeboPersistenceException | GeboContentHandlerSystemException | IOException
						| GeboIngestionException e) {
					LOGGER.error("Exception ingesting document: " + docId, e);
				}
			}

		}
		AIDocumentsSet searchResult = this.search(searchSuggestions, runtimeData, targetChatModel,
				targetChatModel.getContextLength());
		out = AIDocumentsSet.join(out, searchResult);
		if (!out.getDocumentItems().isEmpty()) {
			rc = chatSessionLifecycleService.addRetrievedDocuments(
					runtimeData.getRequestResources().getCurrentRequest(), out, targetChatModel, policy);
		} else
			rc = runtimeData.getRequestResources();
		return rc;
	}

	private AIDocumentsSet search(SearchesSuggestions searchRewritings, ChatPipelineExecutionRuntimeData runtimeData,
			IGConfigurableChatModel targetChatModel, int contextWindowLength)
			throws FullTextException, LLMConfigException, GeboChatSessionLifecycleException {

		int tokensBudget = contextWindowLength / 4;
		AIDocumentsSet documentSet = searchesService.search(runtimeData.getRequestResources().getCurrentRequest(),
				searchRewritings.getRewrittenSemanticSearchSentences(),
				searchRewritings.getRewrittenFullTextSearchSentences(),
				GeboChatRequest.actualQuery(runtimeData.getRequestResources().getCurrentRequest()),
				configuration.getGlobalRagTopK(), tokensBudget);

		return documentSet;
	}

}
