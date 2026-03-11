package ai.gebo.llms.deepsearch.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.ai.service.IGPromptConfigDao;
import ai.gebo.architecture.graphrag.services.IKnowledgeGraphSearchService;
import ai.gebo.architecture.multithreading.IGeboThreadManager;
import ai.gebo.architecture.rag.support.layer.model.AIDocumentFragment;
import ai.gebo.architecture.rag.support.layer.model.AIDocumentsSet;
import ai.gebo.architecture.rag.support.layer.services.IGSemanticSearchDocumentsCachedDao;
import ai.gebo.architecture.rag_threasholds_autotune.service.IRagThreasholdAutotuneService;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.knowledgebase.repositories.DocumentReferenceRepository;
import ai.gebo.llms.abstraction.layer.services.BaseLLMSInvokingService;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableEmbeddingModel;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.chat.abstraction.layer.config.GeboPromptsLibrary;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMRequestGenerationPolicy;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.UserUploadContentServerSide;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.UserUploadedContent;
import ai.gebo.llms.chat.abstraction.layer.repository.UserUploadContentServerSideRepository;
import ai.gebo.llms.chat.abstraction.layer.services.CommonChatPromptParamsUtil;
import ai.gebo.llms.chat.abstraction.layer.services.GeboChatSessionLifecycleException;
import ai.gebo.llms.chat.abstraction.layer.session.model.MinimalChatContext;
import ai.gebo.llms.chat.pipelines.service.IInternalKnowledgeLLMAssistedRetrieveService;
import ai.gebo.llms.deepsearch.config.DeepSearchDefaultConfig;
import ai.gebo.llms.deepsearch.model.DeepSearchAnalyzedDocument;
import ai.gebo.llms.deepsearch.model.DeepSearchConfig;
import ai.gebo.llms.deepsearch.model.DeepSearchDocumentAnalisysResultStep;
import ai.gebo.llms.deepsearch.model.DeepSearchKnowledgebasesResultStep;
import ai.gebo.llms.deepsearch.model.DeepSearchRequest;
import ai.gebo.llms.deepsearch.model.DeepSearchSourceType;
import ai.gebo.llms.deepsearch.model.DeepSearchState;
import ai.gebo.llms.deepsearch.model.events.AbstractDeepSearchEvent;
import ai.gebo.llms.deepsearch.model.events.DeepSearchDocumentEvent;
import ai.gebo.llms.deepsearch.model.events.DeepSearchErrorEvent;
import ai.gebo.llms.deepsearch.model.events.DeepSearchKnowledgeBasesProcessedEvent;
import ai.gebo.llms.deepsearch.model.events.DeepSearchNotificationEvent;
import ai.gebo.llms.deepsearch.model.events.DeepSearchOperationEndedEvent;
import ai.gebo.llms.deepsearch.model.events.DeepSearchUploadedDocumentEvent;
import ai.gebo.llms.deepsearch.service.IGInternalKnlowledgeBaseRagDeepSearchService;
import ai.gebo.llms.deepsearch.service.IGReactiveDeepSearchDataSourceServiceRepositoryPattern;
import ai.gebo.llms.deepsearch.service.IGReactiveDynamicDataSourceServicesProvider;
import ai.gebo.model.DocumentMetaInfos;
import ai.gebo.model.GUserMessage;
import ai.gebo.security.repository.UserRepository.UserInfos;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.ParallelFlux;

@Service
@AllArgsConstructor
public class InternalKnowledgeBaseRagDeepSearchService extends BaseLLMSInvokingService
		implements IGInternalKnlowledgeBaseRagDeepSearchService {
	private static final Logger LOGGER = LoggerFactory.getLogger(InternalKnowledgeBaseRagDeepSearchService.class);
	private final IGPromptConfigDao promptsDao;
	private final IKnowledgeGraphSearchService graphRagSearchService;
	private final IGSemanticSearchDocumentsCachedDao ragDocumentsCachedDao;
	private final DocumentReferenceRepository documentRepo;
	private final IGReactiveDeepSearchDataSourceServiceRepositoryPattern deepSearchDataSourcesRepositoryPattern;
	private final IGReactiveDynamicDataSourceServicesProvider dataSourcesProvider;
	private final DeepSearchDefaultConfig defaultDeepsearchConfig;
	private final IRagThreasholdAutotuneService threasholdAutotuneService;
	private final UserUploadContentServerSideRepository userUploadedRepository;
	private final IInternalKnowledgeLLMAssistedRetrieveService llmAssistedRetriveService;
	private final IGeboThreadManager threadManager;

	@Override
	public Flux<AbstractDeepSearchEvent> knowledgeBaseDeepSearch(DeepSearchRequest request, boolean runSearches,
			DeepSearchState state, MinimalChatContext minimalChatContext, AIDocumentsSet sessionDocuments,
			DeepSearchConfig configuration, UserInfos userInfos, IGConfigurableChatModel chatModel,
			IGConfigurableChatModel serviceModel, String chunkingSessionId,
			List<IGConfigurableEmbeddingModel> embeddingModels)
			throws GeboChatSessionLifecycleException, LLMConfigException {

		AtomicBoolean completed = state.getCompleted();
		Map<String, Object> promptsParameters = CommonChatPromptParamsUtil.preparePromptParameters(minimalChatContext);
		final String analisysPrompt = promptsDao.findByPromptUse(GeboPromptsLibrary.DEEP_SEARCH_FILE_ANALISYS_PROMPT)
				.getPrompt();
		final Vector<LLMInputDocument> results = new Vector<LLMInputDocument>();
		final int topK = this.defaultDeepsearchConfig.getInternalKnowledgeDeepSearchTopK();
		Flux<AIDocumentsSet> retrievedFlux = null;
		if (runSearches) {
			// if run searches then the search will run differited
			retrievedFlux = this.llmAssistedRetriveService.doDocumentsRetrieve(minimalChatContext, chatModel,
					LLMRequestGenerationPolicy.ADDING_RESOURCES_DO_NOT_FIT_TOKENS_BUDGET, topK);
		} else {
			// if run searches is false then the initial flux is an empty document singleton
			// that will be enriched with other documents being in the
			// actual session
			retrievedFlux = Flux.just(new AIDocumentsSet());
		}
		Flux<AIDocumentsSet> integratedWithSessionDocuments = retrievedFlux.map(retrieved -> {
			boolean _completed = completed.get();

			if (_completed) {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Handling search operations ending execution step");
				}
			}
			AIDocumentsSet consolidatedDaoResult = new AIDocumentsSet();
			if (_completed) {
				return consolidatedDaoResult;
			}
			consolidatedDaoResult = retrieved;
			if (sessionDocuments != null && !sessionDocuments.getDocumentItems().isEmpty()) {
				consolidatedDaoResult = AIDocumentsSet.join(sessionDocuments, consolidatedDaoResult);
			}
			return consolidatedDaoResult;
		});

		ParallelFlux<AbstractDeepSearchEvent> body = integratedWithSessionDocuments
				.flatMap(s -> Flux.fromIterable(s.getDocumentItems())).parallel(configuration.getDocumentsParallelism())
				.map((refItem) -> {
					boolean _completed = completed.get();
					if (_completed) {
						if (LOGGER.isDebugEnabled()) {
							LOGGER.debug("Handling search operations ending execution step");
						}
						return DeepSearchOperationEndedEvent.of(request);
					}
					String documentCode = refItem.getCode();
					GDocumentReference documentReference = null;
					UserUploadedContent uploadedContent = null;
					DeepSearchAnalyzedDocument analyzed = null;
					{
						Optional<GDocumentReference> docdata = documentRepo.findById(documentCode);
						if (docdata.isPresent()) {
							documentReference = docdata.get();
							analyzed = KnowledgeBaseDocRefUtil.create(documentReference);
						} else {
							Optional<UserUploadContentServerSide> updopt = this.userUploadedRepository
									.findById(documentCode);
							if (updopt.isPresent()) {
								uploadedContent = new UserUploadedContent(updopt.get());
							}
							analyzed = new DeepSearchAnalyzedDocument();
							analyzed.setCode(uploadedContent.getCode());
							analyzed.setDataSourceCode("User uploaded file");
							analyzed.setDataSourceDescription("User uploaded file");
							analyzed.setName(uploadedContent.getFileName());
							analyzed.setSourceType(DeepSearchSourceType.UPLOADED_FILE);
						}
					}
					if (analyzed != null || uploadedContent != null) {
						if (LOGGER.isDebugEnabled()) {
							LOGGER.debug(
									"Loading on " + Thread.currentThread().getName() + " document:" + documentCode);
						}
						List<AIDocumentFragment> fragments = refItem.getFragments();

						List<LLMInputDocument> inputs = new ArrayList<LLMInputDocument>();
						for (AIDocumentFragment f : fragments) {
							Map<String, Object> meta = f.getMetaData();
							String docReference = meta != null ? (String) meta.get(DocumentMetaInfos.GEBO_FILE_NAME)
									: null;
							String url = meta != null ? (String) meta.get(DocumentMetaInfos.CONTENT_ORIGINAL_URL)
									: null;
							String title = meta != null ? (String) meta.get(DocumentMetaInfos.TITLE) : null;
							if (title == null)
								title = docReference;
							LLMInputDocument cInput = new LLMInputDocument(docReference, url, title,
									f.getDocumentContent());
							inputs.add(cInput);
						}
						try {

							String result = callLLMConsolidateText(serviceModel, analisysPrompt, request.getQuery(), "",
									promptsParameters, inputs);
							SearchEndingDetectionLogic.manageTrigger(state, result);
							result = SearchEndingDetectionLogic.cleanFromTag(result);
							DeepSearchDocumentAnalisysResultStep resultStep = new DeepSearchDocumentAnalisysResultStep();
							resultStep.setDeepsearchCode(request.getCode());
							resultStep.setAnalisysResult(result);
							resultStep.setAnalyzedDocument(analyzed);
							resultStep.setFragmentsCodes(fragments.stream().map(x -> x.getCode()).toList());
							resultStep.processedBy(serviceModel);
							AbstractDeepSearchEvent outEvent = null;
							if (documentReference != null) {
								DeepSearchDocumentEvent event = new DeepSearchDocumentEvent();
								resultStep.setProcessPercentage(state.calculateProcessedPercent());
								event.setInputData(documentReference);
								event.setOutputData(resultStep);
								LLMInputDocument input = new LLMInputDocument(event.getInputData().getName(),
										event.getInputData().getUri(), event.getInputData().getName(), result);
								results.add(input);
								outEvent = event;
							}
							if (uploadedContent != null) {
								DeepSearchUploadedDocumentEvent event = new DeepSearchUploadedDocumentEvent();
								event.setInputData(uploadedContent);
								resultStep.setProcessPercentage(state.calculateProcessedPercent());
								event.setOutputData(resultStep);
								LLMInputDocument input = new LLMInputDocument(uploadedContent.getFileName(), null,
										uploadedContent.getFileName(), result);
								results.add(input);
								outEvent = event;
							}
							return outEvent;
						} catch (Throwable th) {
							LOGGER.error("Error calling llm", th);
							DeepSearchErrorEvent event = new DeepSearchErrorEvent();
							event.setInputData(request);
							event.setOutputData(GUserMessage.errorMessage("Error calling llm", th));
							return (AbstractDeepSearchEvent) event;
						}
					} else
						return null;
				}).runOn(threadManager.getScheduler()).filter(Objects::nonNull);
		Flux<AbstractDeepSearchEvent> trail = Flux.defer(() -> {
			AbstractDeepSearchEvent evt = null;
			DeepSearchKnowledgeBasesProcessedEvent event = new DeepSearchKnowledgeBasesProcessedEvent();
			event.setInputData(request);
			event.setOutputData(new DeepSearchKnowledgebasesResultStep());
			event.getOutputData().setDataSourceDescription("Knowledge bases");
			event.getOutputData().setDeepsearchCode(request.getCode());
			event.getOutputData().setSearchResultsEmpty(results.isEmpty());
			event.getOutputData().processedBy(chatModel);
			try {
				if (!results.isEmpty()) {
					String result = callLLMConsolidateText(chatModel,
							promptsDao.findByPromptUse(GeboPromptsLibrary.DEEP_SEARCH_CONSOLIDATION_PROMPT).getPrompt(),
							request.getQuery(), "", promptsParameters, new ArrayList(results));
					event.getOutputData().setResponse(result);
				} else {
					String result = callLLM(chatModel, promptsDao
							.findByPromptUse(GeboPromptsLibrary.DEEP_SEARCH_EMPTY_RESULTS_FALLBACK_PROMPT).getPrompt(),
							request.getQuery(), promptsParameters);
					event.getOutputData().setResponse(result);
				}
				evt = event;
			} catch (Throwable th) {
				LOGGER.error("Error calling llm to consolidate", th);
				DeepSearchErrorEvent eevent = new DeepSearchErrorEvent();
				eevent.setInputData(request);
				eevent.setOutputData(GUserMessage.errorMessage("Error calling llm", th));
				evt = eevent;
			}
			return Flux.just(evt);
		});

		Flux<AbstractDeepSearchEvent> notificationFlux = DeepSearchNotificationEvent.flux(request,
				"Searching documents", "Internal knowledge base");
		return Flux.concat(notificationFlux, body, trail).onErrorResume(Common.commonFallBack(request))
				.subscribeOn(this.threadManager.getScheduler());
	}

}
