package ai.gebo.llms.deepsearch.service.impl;

import static org.assertj.core.api.Assertions.assertThatIllegalStateException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.ai.tokenizer.JTokkitTokenCountEstimator;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.ai.model.GPromptTemplateConfig;
import ai.gebo.architecture.ai.service.IGPromptConfigDao;
import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.architecture.documents.cache.service.IDocumentsChunkService;
import ai.gebo.architecture.fulltext.service.FullTextException;
import ai.gebo.architecture.multithreading.IGeboThreadManager;
import ai.gebo.architecture.rag.support.layer.model.AIDocumentsSet;
import ai.gebo.architecture.search.model.SearchResult;
import ai.gebo.architecture.search.model.SearchServiceException;
import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;
import ai.gebo.llms.abstraction.layer.services.BaseLLMSInvokingAndProvidingService;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableEmbeddingModel;
import ai.gebo.llms.abstraction.layer.services.IGEmbeddingModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.abstraction.layer.services.LLMInputDocument;
import ai.gebo.llms.chat.abstraction.layer.config.GeboPromptsLibrary;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.ChatNotificationContent.NotificationType;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GInputProcessingEvent;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GResponseDocumentRef;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatMessageEnvelope;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatResponse;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMChatRequestResources;
import ai.gebo.llms.chat.abstraction.layer.services.GeboChatSessionLifecycleException;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatSessionLifeCycleService;
import ai.gebo.llms.chat.abstraction.layer.services.TokensBudgetCalculator;
import ai.gebo.llms.chat.abstraction.layer.services.TokensBudgetFluxCoordinator;
import ai.gebo.llms.chat.abstraction.layer.services.TokensBudgetFluxCoordinator.GenerativeFunction;
import ai.gebo.llms.chat.abstraction.layer.services.TokensBudgetFluxCoordinator.LastWork;
import ai.gebo.llms.chat.abstraction.layer.services.TokensBudgetFluxCoordinator.TokensLimitCompute;
import ai.gebo.llms.chat.abstraction.layer.session.model.MinimalChatContext;
import ai.gebo.llms.chat.pipelines.model.ChatPipelineExecutionRuntimeData;
import ai.gebo.llms.chat.pipelines.service.ISinkUIEmitter;
import ai.gebo.llms.chat.pipelines.service.defaultsteps.impl.DefaultRoutingChatPipelineStepServiceImpl;
import ai.gebo.llms.deepsearch.config.DeepSearchDefaultConfig;
import ai.gebo.llms.deepsearch.datasources.model.AbstractPureSearchDocumentResultEntry;
import ai.gebo.llms.deepsearch.datasources.model.DeepSearchDataSourceResponse;
import ai.gebo.llms.deepsearch.datasources.model.PureSearchDocumentResultError;
import ai.gebo.llms.deepsearch.datasources.model.events.DeepSearchDataSourceProcessedEvent;
import ai.gebo.llms.deepsearch.model.DeepSearchConfig;
import ai.gebo.llms.deepsearch.model.DeepSearchRequest;
import ai.gebo.llms.deepsearch.model.DeepSearchResponse;
import ai.gebo.llms.deepsearch.model.DeepSearchState;
import ai.gebo.llms.deepsearch.model.IDeepSearchResult;
import ai.gebo.llms.deepsearch.model.events.AbstractDeepSearchEvent;
import ai.gebo.llms.deepsearch.model.events.DeepSearchErrorEvent;
import ai.gebo.llms.deepsearch.model.events.DeepSearchNotificationEvent;
import ai.gebo.llms.deepsearch.model.events.DeepSearchProcessedEvent;
import ai.gebo.llms.deepsearch.service.IGDeepSearchConfigProvider;
import ai.gebo.llms.deepsearch.service.IGInternalKnlowledgeBaseRagDeepSearchService;
import ai.gebo.llms.deepsearch.service.IGReactiveDeepSearchDataSourceService;
import ai.gebo.llms.deepsearch.service.IGReactiveDeepSearchDataSourceService.DocumentWithSearchResult;
import ai.gebo.llms.deepsearch.service.IGReactiveDeepSearchDataSourceServiceRepositoryPattern;
import ai.gebo.llms.deepsearch.service.IGReactiveDynamicDataSourceServicesProvider;
import ai.gebo.llms.deepsearch.service.IGReactiveEnabledDeepSearchDataSourceLookupService;
import ai.gebo.model.DocumentMetaInfos;
import ai.gebo.model.GUserMessage;
import ai.gebo.model.base.GBaseObject;
import ai.gebo.security.repository.UserRepository.UserInfos;
import ai.gebo.security.services.ReactiveIdentityUtil;
import ai.gebo.system.ingestion.GeboIngestionException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;

@Service
public class FullReactiveDeepsearchWorker extends BaseLLMSInvokingAndProvidingService {
	private static final String ERROR_IN_PROCESS = "<!-ERROR-IN-PROCESS->";
	private static final String PARTIAL_ANALISYS_SATISFACTORY = "<IS-COMPLETELY-SATISFACTORY/>";
	private final static Logger LOGGER = LoggerFactory.getLogger(FullReactiveDeepsearchWorker.class);
	private static final String DOCUMENT_NAME = "DOCUMENT NAME:";
	private static final String END_DOCUMENT_EXTRACTION = "[END DOCUMENT EXTRACTION]\r\n";
	private static final String DOCUMENT_EXTRACTION_BEGIN = "[BEGIN DOCUMENT EXTRACTION]\r\n";
	private final IGReactiveEnabledDeepSearchDataSourceLookupService enabledDataSourcesLookupService;
	private final DeepSearchDefaultConfig defaultDeepsearchConfig;
	private final IGPromptConfigDao promptsDao;
	private final IGeboThreadManager threadManager;
	private final IGInternalKnlowledgeBaseRagDeepSearchService internalKnowledgeBaseDeepSearchService;
	private final IGChatSessionLifeCycleService sessionLifecycleService;
	private final IGDeepSearchConfigProvider deepSearchConfigProvider;
	protected final IDocumentsChunkService chunkingService;

	public FullReactiveDeepsearchWorker(IGChatModelRuntimeConfigurationDao chatModelsConfigDao,
			IGEmbeddingModelRuntimeConfigurationDao embeddingModelsRuntimeDao, IGeboThreadManager threadManager,
			IGPromptConfigDao promptsDao,
			IGInternalKnlowledgeBaseRagDeepSearchService internalKnowledgeBaseDeepSearchService,
			DeepSearchDefaultConfig defaultDeepsearchConfig,
			IGReactiveDeepSearchDataSourceServiceRepositoryPattern deepSearchDataSourcesRepositoryPattern,
			IGReactiveDynamicDataSourceServicesProvider dataSourcesProvider,
			IGReactiveEnabledDeepSearchDataSourceLookupService enabledDataSourcesLookupService,
			IGChatSessionLifeCycleService sessionLifecycleService, IDocumentsChunkService chunkingService,
			IGDeepSearchConfigProvider deepSearchConfigProvider) {
		super(chatModelsConfigDao, embeddingModelsRuntimeDao);
		this.enabledDataSourcesLookupService = enabledDataSourcesLookupService;
		this.defaultDeepsearchConfig = defaultDeepsearchConfig;
		this.promptsDao = promptsDao;
		this.threadManager = threadManager;
		this.internalKnowledgeBaseDeepSearchService = internalKnowledgeBaseDeepSearchService;
		this.deepSearchConfigProvider = deepSearchConfigProvider;
		this.sessionLifecycleService = sessionLifecycleService;
		this.chunkingService = chunkingService;

	}

	private static final JTokkitTokenCountEstimator tokenEstimator = new JTokkitTokenCountEstimator();

	private List<Flux<AbstractDeepSearchEvent>> dataSourcesDeepSearch(DeepSearchRequest request,
			MinimalChatContext minimalChatContext, List<AbstractDeepSearchEvent> history,
			List<IDeepSearchResult> dataSourcesResults, DeepSearchState state,
			List<IGReactiveDeepSearchDataSourceService> handlers, IGConfigurableChatModel chatModel,
			IGConfigurableChatModel serviceModel, DeepSearchConfig deepSearchConfig, Scheduler deepSearchScheduler,
			String chunkingSessionId) throws LLMConfigException, IOException, GeboIngestionException,
			GeboContentHandlerSystemException, SearchServiceException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin dataSourcesNextStep(....)");
		}
		List<Flux<AbstractDeepSearchEvent>> out = new ArrayList<Flux<AbstractDeepSearchEvent>>();
		// if already CurrentDataSourceHandlerRunning is initialized continue processing
		// next step

		// find next data source to evaluate end execute
		for (IGReactiveDeepSearchDataSourceService handler : handlers) {
			if (handler.isEnabled(deepSearchConfig)) {
				Flux<AbstractDeepSearchEvent> nextStepValue = null;
				nextStepValue = handler.streamSearch(request, minimalChatContext, state, chatModel, serviceModel,
						deepSearchConfig, dataSourcesResults, chunkingSessionId);
				if (nextStepValue != null) {
					Flux<AbstractDeepSearchEvent> notificationFlux = DeepSearchNotificationEvent.flux(request,
							"Extracting relevant documents", handler.getDescription(deepSearchConfig));
					nextStepValue = Flux.concat(notificationFlux, nextStepValue);
					nextStepValue.onErrorResume(Common.commonFallBack(request));
					nextStepValue.subscribeOn(deepSearchScheduler);
					out.add(nextStepValue);
				}

			}
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("End dataSourcesNextStep(....)");
		}
		return out;
	}

	private List<IGReactiveDeepSearchDataSourceService> filterChoosed(
			List<IGReactiveDeepSearchDataSourceService> handlers, DeepSearchRequest request) {
		if (request.getDeepSearchDataSources() == null)
			return handlers;
		if (request.getDeepSearchDataSources().isEmpty())
			return List.of();
		return handlers.stream().filter(x -> request.getDeepSearchDataSources().contains(x.getHandlerId())).toList();
	}

	Flux<AbstractDeepSearchEvent> streamDeepSearch(DeepSearchRequest request, MinimalChatContext minimalChatContext,
			AIDocumentsSet sessionDocuments, List<AbstractDeepSearchEvent> history, DeepSearchConfig configuration,
			UserInfos userInfos, List<IGConfigurableEmbeddingModel> embeddingModels, IGConfigurableChatModel chatModel,
			IGConfigurableChatModel serviceModel, Scheduler deepSearchScheduler, String chunkingSessionId)
			throws LLMConfigException, GeboChatSessionLifecycleException, FullTextException {

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin nextStep(....)");
		}
		if (request.getQuery() == null || request.getQuery().trim().length() == 0
				|| request.getKnowledgeBases() == null) {
			throw new IllegalStateException("Cannot run a deepsearch with no query or null knowledge bases list");
		}
		final IChatRequestContext context = minimalChatContext.createChatRequestContext();
		final int satisfactoryDocumentsThreashold = this.defaultDeepsearchConfig
				.getInTopicSatisfactoryDocumentsThreashold(request.getUserIntent());
		DeepSearchState state = new DeepSearchState();
		state.setSatisfactoryDocumentsThreashold(satisfactoryDocumentsThreashold);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Using satisfactory documents threashold: " + satisfactoryDocumentsThreashold);
		}
		boolean externalSourcesEnabled = true;
		List<IDeepSearchResult> dataSourcesResults = new ArrayList<IDeepSearchResult>();
		Flux<AbstractDeepSearchEvent> composedFlux = DeepSearchNotificationEvent.flux(request,
				"Accessing data sources and internal knowledge base", "");
		List<Flux<AbstractDeepSearchEvent>> sources = new ArrayList<Flux<AbstractDeepSearchEvent>>();
		if (chatModel != null) {

			if (externalSourcesEnabled) {

				// Streaming search steps from handlers before knowledge base search
				List<IGReactiveDeepSearchDataSourceService> handlers = enabledDataSourcesLookupService
						.enabledDataSources(configuration);

				handlers = filterChoosed(handlers, request);
				if (!handlers.isEmpty()) {

					try {
						List<Flux<AbstractDeepSearchEvent>> newSources = dataSourcesDeepSearch(request,
								minimalChatContext, history, dataSourcesResults, state, handlers, chatModel,
								serviceModel, configuration, deepSearchScheduler, chunkingSessionId);
						sources.addAll(newSources);
					} catch (Throwable e) {
						LOGGER.error("Exception accessing deep search data source", e);
						DeepSearchDataSourceProcessedEvent processedDataSource = new DeepSearchDataSourceProcessedEvent();
						processedDataSource.setInputData(request);
						processedDataSource.setOutputData(new DeepSearchDataSourceResponse());
						processedDataSource.getOutputData().setSearchResultsEmpty(true);
						processedDataSource.getOutputData().setDeepsearchCode(request.getCode());
						processedDataSource.getOutputData()
								.setErrorMessage(GUserMessage.errorMessage("Exception in deep search", e));
						processedDataSource.getOutputData().setProcessPercentage(state.calculateProcessedPercent());
						sources.add(Flux.just(processedDataSource));

					}

				}
			}
			if ((request.getKnowledgeBases() != null && !request.getKnowledgeBases().isEmpty())
					|| (sessionDocuments != null && !sessionDocuments.getDocumentItems().isEmpty())) {

				Flux<AbstractDeepSearchEvent> nextStepValue = this.internalKnowledgeBaseDeepSearchService
						.knowledgeBaseDeepSearch(request, true, state, minimalChatContext, sessionDocuments,
								configuration, userInfos, chatModel, serviceModel, chunkingSessionId, embeddingModels);
				if (nextStepValue != null) {
					nextStepValue = nextStepValue.onErrorResume(Common.commonFallBack(request));
					nextStepValue.subscribeOn(deepSearchScheduler);
					sources.add(nextStepValue);
				}

			}
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Consolidate final result");
		}
		final Vector<IDeepSearchResult> intermediates = new Vector<IDeepSearchResult>();
		Flux<AbstractDeepSearchEvent> mergedFlux = Flux.merge(sources);
		composedFlux = Flux.concat(composedFlux, mergedFlux);
		composedFlux = composedFlux.map(event -> {
			if (event != null && event.getOutputData() != null
					&& event.getOutputData() instanceof IDeepSearchResult intermediateResult
					&& (intermediateResult.getSearchResultsEmpty() == null
							|| intermediateResult.getSearchResultsEmpty() == false)) {
				intermediates.add(intermediateResult);
			}
			return event;
		});
		Flux<AbstractDeepSearchEvent> outFlux = enqueueDeepSearchProcessedEvent(composedFlux, request, context, history,
				state, configuration, userInfos, embeddingModels, chatModel, intermediates);
		return outFlux;

	}

	private Flux<AbstractDeepSearchEvent> enqueueDeepSearchProcessedEvent(Flux<AbstractDeepSearchEvent> composedFlux,
			DeepSearchRequest request, IChatRequestContext context, List<AbstractDeepSearchEvent> history,
			DeepSearchState state, DeepSearchConfig configuration, UserInfos userInfos,
			List<IGConfigurableEmbeddingModel> embeddingModels, IGConfigurableChatModel chatModel,
			Vector<IDeepSearchResult> intermediates) {
		final ReactiveIdentityUtil runAs = ReactiveIdentityUtil.create();
		final Map<String, Object> promptParams = new HashMap<>();
		Mono<AbstractDeepSearchEvent> deferred = Mono.fromCallable(() -> {
			return runAs.doRunAsWithReturn(() -> {
				final DeepSearchProcessedEvent consolidatedResult = new DeepSearchProcessedEvent();
				consolidatedResult.setInputData(request);
				consolidatedResult.setOutputData(new DeepSearchResponse());
				consolidatedResult.getOutputData().setDeepsearchCode(request.getCode());
				consolidatedResult.getOutputData().processedBy(chatModel);
				try {
					if (intermediates != null && !intermediates.isEmpty()) {
						List<LLMInputDocument> inputs = new ArrayList<LLMInputDocument>();
						for (IDeepSearchResult x : intermediates) {
							LLMInputDocument consolidated = new LLMInputDocument(x.getDataSourceDescription(), null,
									null, x.getResponse());
							inputs.add(consolidated);
						}
						String consolidatedText = callLLMConsolidateText(chatModel,
								promptsDao.findByPromptUse(GeboPromptsLibrary.DEEP_SEARCH_CONSOLIDATION_PROMPT),
								context, "", promptParams, inputs);
						consolidatedResult.getOutputData().setResponse(consolidatedText);
						consolidatedResult.getOutputData().setProcessPercentage(100);
						boolean haveResults = consolidatedText != null && consolidatedText.trim().length() > 0;
						consolidatedResult.getOutputData().setSearchResultsEmpty(!haveResults);
						consolidatedResult.getOutputData().setProcessPercentage(100);

					} else {
						String backupText = callLLM(chatModel,
								promptsDao
										.findByPromptUse(GeboPromptsLibrary.DEEP_SEARCH_EMPTY_RESULTS_FALLBACK_PROMPT),
								context, promptParams);
						consolidatedResult.getOutputData().setResponse(backupText);
						consolidatedResult.getOutputData().setProcessPercentage(100);
						consolidatedResult.getOutputData().setSearchResultsEmpty(true);

					}
					return consolidatedResult;
				} catch (Throwable th) {
					LOGGER.error("Error in consolidation", th);
					DeepSearchErrorEvent ee = new DeepSearchErrorEvent();
					ee.setInputData(request);
					ee.setOutputData(GUserMessage.errorMessage("Error in deep search", th));
					return ee;
				}
			});
		});
		return Flux.concat(composedFlux, deferred);
	}

	private boolean thereAreNotEmpty(List<IDeepSearchResult> dataSourcesResults) {
		if (dataSourcesResults == null || dataSourcesResults.isEmpty())
			return false;
		return dataSourcesResults.stream().filter(x -> x.getSearchResultsEmpty() == null || !x.getSearchResultsEmpty())
				.count() > 0;
	}

	Flux<AbstractPureSearchDocumentResultEntry> streamPureSearch(LLMChatRequestResources request,
			MinimalChatContext minimalChatContext, GeboChatRequest geboChatRequest, ISinkUIEmitter emitter,
			IGConfigurableChatModel chatModel, IGConfigurableChatModel serviceModel, List<String> searchDataSources,
			int perDataSourceK, int globalK, int sampleTextTokensSize, String chunkSessionId) {
		final ReactiveIdentityUtil runAs = ReactiveIdentityUtil.create();
		if (searchDataSources == null || searchDataSources.isEmpty()) {
			if (request.getCurrentRequest().getDeepSearchDataSources() != null
					&& !request.getCurrentRequest().getDeepSearchDataSources().isEmpty()) {
				searchDataSources = request.getCurrentRequest().getDeepSearchDataSources();
			} else
				searchDataSources = List
						.of(DefaultRoutingChatPipelineStepServiceImpl.INTERNAL_KNOWLEDGE_BASE_SYSTEM_ID);
		}
		final List<String> sampledDataSources = searchDataSources;
		DeepSearchConfig configuration = this.deepSearchConfigProvider.get();
		if (configuration == null) {
			configuration = this.defaultDeepsearchConfig;
		}
		final DeepSearchConfig sampledConfig = configuration;
		List<IGReactiveDeepSearchDataSourceService> handlersFullList = this.enabledDataSourcesLookupService
				.enabledDataSources(configuration);
		List<IGReactiveDeepSearchDataSourceService> filtered = handlersFullList.stream()
				.filter(handler -> sampledDataSources != null && sampledDataSources.contains(handler.getHandlerId()))
				.toList();
		List<Supplier<Flux<AbstractPureSearchDocumentResultEntry>>> suppliers = new ArrayList<>();
		for (IGReactiveDeepSearchDataSourceService handler : filtered) {
			Supplier<Flux<AbstractPureSearchDocumentResultEntry>> supplier = () -> {
				return runAs.doRunAsWithReturn(() -> {
					try {
						emitter.notifyUser("search-" + handler.getHandlerId(),
								"Running search on " + handler.getDescription(sampledConfig), "pi pi-file", 3000l,
								NotificationType.INFO);
						return handler.streamPureSearch(minimalChatContext, emitter, chatModel, serviceModel,
								perDataSourceK, sampleTextTokensSize, chunkSessionId);
					} catch (Throwable e) {
						PureSearchDocumentResultError error = new PureSearchDocumentResultError(null, null,
								GUserMessage.warnMessage("Error running search", e.getMessage()));
						return Flux.just((AbstractPureSearchDocumentResultEntry) error);
					}
				});
			};
			suppliers.add(supplier);
		}
		if (sampledDataSources.contains(DefaultRoutingChatPipelineStepServiceImpl.INTERNAL_KNOWLEDGE_BASE_SYSTEM_ID)) {
			Supplier<Flux<AbstractPureSearchDocumentResultEntry>> supplier = () -> {
				return runAs.doRunAsWithReturn(() -> {
					try {
						emitter.notifyUser("search-ikb", "Running search on internal Knowledge Base", "pi pi-file",
								3000l, NotificationType.INFO);
						return this.internalKnowledgeBaseDeepSearchService.streamPureSearch(minimalChatContext, emitter,
								serviceModel, serviceModel, chunkSessionId, perDataSourceK, sampleTextTokensSize);
					} catch (Throwable e) {
						PureSearchDocumentResultError error = new PureSearchDocumentResultError(null, null,
								GUserMessage.warnMessage("Error running search", e.getMessage()));
						return Flux.just((AbstractPureSearchDocumentResultEntry) error);
					}
				});
			};
			suppliers.add(supplier);
		}
		if (suppliers.isEmpty()) {
			return Flux.empty();
		}

		Flux<AbstractPureSearchDocumentResultEntry> outFlux = Flux.fromIterable(suppliers).concatMap(supplier -> {
			return supplier.get();
		}).subscribeOn(threadManager.getBoundedElastic());
		return outFlux;

	}

	public List<GBaseObject> getDeepSearchActiveHandlers(DeepSearchConfig configuration) {

		IGConfigurableChatModel chatModel = null;

		if (chatModel == null) {
			chatModel = chatModelsConfigDao.defaultHandler();
		}
		if (chatModel == null)
			return List.of();
		final IGConfigurableChatModel fChatModel = chatModel;
		List<IGReactiveDeepSearchDataSourceService> handlersFullList = this.enabledDataSourcesLookupService
				.enabledDataSources(configuration);
		return handlersFullList.stream().map(x -> {
			GBaseObject ds = new GBaseObject();
			ds.setCode(x.getHandlerId());
			ds.setDescription(x.getDescription(configuration));
			return ds;
		}).toList();
	}

	public Flux<GeboChatMessageEnvelope> streamNewDeepSearch(ChatPipelineExecutionRuntimeData runtimeData,
			ISinkUIEmitter sinkUIEmitter, IGConfigurableChatModel chatModel, IGConfigurableChatModel serviceModel,
			List<String> searchDataSources, int perDataSourceK, int globalK) {
		final String chunkSessionId = this.chunkingService
				.createChunkingSession("request:" + runtimeData.getRequestResources().getCurrentRequest().getId());
		final ReactiveIdentityUtil runAs = ReactiveIdentityUtil.create();
		if (searchDataSources == null || searchDataSources.isEmpty()) {

			searchDataSources = List.of(DefaultRoutingChatPipelineStepServiceImpl.INTERNAL_KNOWLEDGE_BASE_SYSTEM_ID);
		}
		final List<String> sampledDataSources = searchDataSources;
		DeepSearchConfig configuration = this.deepSearchConfigProvider.get();
		if (configuration == null) {
			configuration = this.defaultDeepsearchConfig;
		}
		final DeepSearchConfig sampledConfig = configuration;
		List<IGReactiveDeepSearchDataSourceService> handlersFullList = this.enabledDataSourcesLookupService
				.enabledDataSources(configuration);
		List<IGReactiveDeepSearchDataSourceService> filtered = handlersFullList.stream()
				.filter(handler -> sampledDataSources != null && sampledDataSources.contains(handler.getHandlerId()))
				.toList();
		final Map<String, GResponseDocumentRef> results = new Hashtable<>();
		final Map<String, GResponseDocumentRef> docrefs = new Hashtable<>();
		List<Supplier<Flux<Document>>> suppliers = new ArrayList<>();
		for (IGReactiveDeepSearchDataSourceService handler : filtered) {
			Supplier<Flux<Document>> supplier = () -> {
				return runAs.doRunAsWithReturn(() -> {
					try {
						sinkUIEmitter.notifyUser("search-" + handler.getHandlerId(),
								"Running search on " + handler.getDescription(sampledConfig), "pi pi-file", 3000l,
								NotificationType.INFO);
						Flux<DocumentWithSearchResult> fl = handler.streamSearchResults(runtimeData, sinkUIEmitter,
								chatModel, serviceModel, chunkSessionId, globalK);
						return fl.map(x -> {
							if (!results.containsKey(x.getSearchResult().getCode())) {
								GResponseDocumentRef ref = new GResponseDocumentRef(x.getSearchResult());
								results.put(x.getSearchResult().getCode(), ref);
								GInputProcessingEvent processingEvent = new GInputProcessingEvent(ref);
								sinkUIEmitter.next(new GeboChatMessageEnvelope(processingEvent));
							}
							return x.getDocument();
						});
					} catch (Throwable e) {
						LOGGER.error("Error in straming", e);
						return Flux.empty();
					}
				});
			};
			suppliers.add(supplier);
		}

		if (sampledDataSources.contains(DefaultRoutingChatPipelineStepServiceImpl.INTERNAL_KNOWLEDGE_BASE_SYSTEM_ID)) {
			Supplier<Flux<Document>> supplier = () -> {
				return runAs.doRunAsWithReturn(() -> {
					try {
						sinkUIEmitter.notifyUser("search-ikb", "Running search on internal Knowledge Base",
								"pi pi-file", 3000l, NotificationType.INFO);
						return this.internalKnowledgeBaseDeepSearchService.streamSearchResults(runtimeData,
								sinkUIEmitter, chatModel, serviceModel, chunkSessionId, globalK).map(doc -> {

									String code = doc.getMetadata() != null
											&& doc.getMetadata().containsKey(DocumentMetaInfos.CONTENT_CODE)
													? doc.getMetadata().get(DocumentMetaInfos.CONTENT_CODE).toString()
													: null;
									if (code != null && !docrefs.containsKey(code)) {
										GResponseDocumentRef ref = new GResponseDocumentRef(doc);
										docrefs.put(code, new GResponseDocumentRef(doc));
										GInputProcessingEvent processingEvent = new GInputProcessingEvent(ref);
										sinkUIEmitter.next(new GeboChatMessageEnvelope(processingEvent));
									}
									return doc;
								});
					} catch (Throwable e) {

						return Flux.empty();
					}
				});
			};
			suppliers.add(supplier);
		}
		if (suppliers.isEmpty()) {
			return Flux.empty();
		}
		final IChatRequestContext context = runtimeData.getRequestResources().createChatRequestContext();
		// prompt template for input document analisys
		final GPromptTemplateConfig cumulativeAnalisysPrompt = promptsDao
				.findByPromptUse(GeboPromptsLibrary.DEEP_SEARCH_FILE_ANALISYS_PROMPT);
		// prompt template for final analisys
		final GPromptTemplateConfig finalAnalisysPrompt = promptsDao
				.findByPromptUse(GeboPromptsLibrary.DEEP_SEARCH_CONSOLIDATION_PROMPT);
		// prompt template for empty documents
		final GPromptTemplateConfig emptyResponsePrompt = promptsDao
				.findByPromptUse(GeboPromptsLibrary.DEEP_SEARCH_EMPTY_RESULTS_FALLBACK_PROMPT);
		// raw tokens budget calculation
		final long tokensBudget = serviceModel.getContextLength() * 2 / 3;
		final GeboChatResponse response = runtimeData.getChatResponse();
		final GeboChatRequest request = runtimeData.getRequestResources().getCurrentRequest();

		GenerativeFunction<Document, String> intermediateProcess = (initialValue, _emitter, documentsList) -> {
			return runAs.doRunAsWithReturnAndException(() -> {
				return callLLMWithDocumentsAndConsolidation(serviceModel, cumulativeAnalisysPrompt, context,
						documentsList, initialValue);
			});

		};
		LastWork<String, String> finalAnalisysWork = (list, _emitter) -> {
			return runAs.doRunAsWithReturnAndException(() -> {
				if (list != null && !list.isEmpty()) {
					Map<String, Object> params = new HashMap<>();
					params.put(IChatRequestContext.DOCUMENTS_PROMPT_PLACEHOLDER, list);
					params.put(CONSOLIDATED_TEMPLATE_VARIABLE, "");
					params.put("agentDeliverableCompleteness", request.getUserIntent().name());
					return callLLMReactive(chatModel, finalAnalisysPrompt, context, params);
				} else {
					return callLLMReactive(chatModel, finalAnalisysPrompt, context, Map.of());
				}
			});
		};

		Flux<Document> documentFlux = Flux.fromIterable(suppliers).concatMap(x -> x.get());
		Predicate<Document> isValidDocument = (document) -> document.isText() && document.getText() != null
				&& document.getText().trim().length() > 0;
		TokensLimitCompute<Document> tokensLimitCompute = (list, budget) -> TokensBudgetCalculator
				.higherThanBudget(list, budget);
		Predicate<String> outOfBandString = (v) -> v == null || v.equals(ERROR_IN_PROCESS);
		Predicate<String> isEndOfProcessingCondition = (text) -> text != null
				&& text.toUpperCase().contains(PARTIAL_ANALISYS_SATISFACTORY);
		Function<String, String> outputShortCutFunction = (text) -> text.replace(PARTIAL_ANALISYS_SATISFACTORY, "");
		Flux<String> resultFlux = TokensBudgetFluxCoordinator.tokenBudgetCoordinate(documentFlux, sinkUIEmitter,
				isValidDocument, tokensLimitCompute, intermediateProcess, finalAnalisysWork, "", ERROR_IN_PROCESS,
				outOfBandString, ERROR_IN_PROCESS, outOfBandString, isEndOfProcessingCondition, outputShortCutFunction,
				tokensBudget);
		final StringBuffer cumulative = new StringBuffer();
		Flux<GeboChatMessageEnvelope> intermediateStreamingFlux = resultFlux.map(x -> {
			cumulative.append(x);
			return x;
		}).map(piece -> new GeboChatMessageEnvelope<>(piece));
		Flux<GeboChatMessageEnvelope> finalMessages = Flux.defer(() -> {
			return runAs.doRunAsWithReturn(() -> {
				response.setQueryResponse(cumulative.toString());
				ArrayList docs = new ArrayList<>(docrefs.values());
				docs.addAll(results.values());
				response.setDocumentsRef(docs);
				GeboChatMessageEnvelope envelope = new GeboChatMessageEnvelope(response);
				envelope.setLastMessage(true);
				return Flux.fromIterable(List.of(envelope, GeboChatMessageEnvelope.FINAL_MESSAGE));
			});
		});
		Flux<GeboChatMessageEnvelope> finalFlux = Flux.concat(intermediateStreamingFlux, finalMessages);
		finalFlux.publishOn(threadManager.getScheduler()).doOnComplete(() -> {
			runAs.doAs(() -> {
				try {
					this.chunkingService.disposeChunkingSession(chunkSessionId);
				} catch (Throwable th) {
				}
				try {
					sessionLifecycleService.endRequest(request, response);
				} catch (Throwable e) {
					LOGGER.error("Error ending request", e);
				}
				try {
					sessionLifecycleService.chatRequestCompleted(request, chatModel);
				} catch (Throwable e) {
					LOGGER.error("Error completing request", e);
				}
			});
		});
		return finalFlux;

	}

}
