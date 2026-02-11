package ai.gebo.llms.deepsearch.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.tokenizer.JTokkitTokenCountEstimator;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.architecture.multithreading.IGeboThreadManager;
import ai.gebo.architecture.rag.support.layer.model.AIDocumentsSet;
import ai.gebo.architecture.search.model.SearchServiceException;
import ai.gebo.llms.abstraction.layer.services.BaseLLMSInvokingAndProvidingService;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableEmbeddingModel;
import ai.gebo.llms.abstraction.layer.services.IGEmbeddingModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.chat.abstraction.layer.config.GeboPromptsLibrary;
import ai.gebo.llms.chat.abstraction.layer.services.IGPromptConfigDao;
import ai.gebo.llms.deepsearch.config.DeepSearchDefaultConfig;
import ai.gebo.llms.deepsearch.datasources.model.DeepSearchDataSourceResponse;
import ai.gebo.llms.deepsearch.datasources.model.events.DeepSearchDataSourceProcessedEvent;
import ai.gebo.llms.deepsearch.model.DeepSearchConfig;
import ai.gebo.llms.deepsearch.model.DeepSearchRequest;
import ai.gebo.llms.deepsearch.model.DeepSearchResponse;
import ai.gebo.llms.deepsearch.model.DeepSearchState;
import ai.gebo.llms.deepsearch.model.IDeepSearchResult;
import ai.gebo.llms.deepsearch.model.events.AbstractDeepSearchEvent;
import ai.gebo.llms.deepsearch.model.events.DeepSearchNotificationEvent;
import ai.gebo.llms.deepsearch.model.events.DeepSearchProcessedEvent;
import ai.gebo.llms.deepsearch.service.IGReactiveDeepSearchDataSourceService;
import ai.gebo.llms.deepsearch.service.IGReactiveDeepSearchDataSourceServiceRepositoryPattern;
import ai.gebo.llms.deepsearch.service.IGReactiveDynamicDataSourceServicesProvider;
import ai.gebo.model.GUserMessage;
import ai.gebo.model.base.GBaseObject;
import ai.gebo.security.repository.UserRepository.UserInfos;
import ai.gebo.system.ingestion.GeboIngestionException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;

@Service
public class FullReactiveDeepsearchWorker extends BaseLLMSInvokingAndProvidingService {

	private static final String NEWLINE = "\r\n";
	private static final String SEARCH_MODULE_NAME = "Search module name:";
	private static final String END_DEEP_SEARCH_MODULE_RESULT = "[End Deep search module result]\r\n";
	private static final String BEGIN_DEEP_SEARCH_MODULE_RESULT = "[Begin Deep search module result]\r\n";
	private final static Logger LOGGER = LoggerFactory.getLogger(FullReactiveDeepsearchWorker.class);
	private static final String DOCUMENT_NAME = "DOCUMENT NAME:";
	private static final String END_DOCUMENT_EXTRACTION = "[END DOCUMENT EXTRACTION]\r\n";
	private static final String DOCUMENT_EXTRACTION_BEGIN = "[BEGIN DOCUMENT EXTRACTION]\r\n";
	private final IGReactiveDeepSearchDataSourceServiceRepositoryPattern deepSearchDataSourcesRepositoryPattern;
	private final IGReactiveDynamicDataSourceServicesProvider dataSourcesProvider;
	private final DeepSearchDefaultConfig defaultDeepsearchConfig;
	private final IGPromptConfigDao promptsDao;
	private final IGeboThreadManager threadManager;
	private final InternalKnowledgeBaseRagDeepSearchService internalKnowledgeBaseDeepSearchService;

	public FullReactiveDeepsearchWorker(IGChatModelRuntimeConfigurationDao chatModelsConfigDao,
			IGEmbeddingModelRuntimeConfigurationDao embeddingModelsRuntimeDao, IGeboThreadManager threadManager,
			IGPromptConfigDao promptsDao,
			InternalKnowledgeBaseRagDeepSearchService internalKnowledgeBaseDeepSearchService,
			DeepSearchDefaultConfig defaultDeepsearchConfig,
			IGReactiveDeepSearchDataSourceServiceRepositoryPattern deepSearchDataSourcesRepositoryPattern,
			IGReactiveDynamicDataSourceServicesProvider dataSourcesProvider) {
		super(chatModelsConfigDao, embeddingModelsRuntimeDao);
		this.deepSearchDataSourcesRepositoryPattern = deepSearchDataSourcesRepositoryPattern;
		this.dataSourcesProvider = dataSourcesProvider;
		this.defaultDeepsearchConfig = defaultDeepsearchConfig;
		this.promptsDao = promptsDao;
		this.threadManager = threadManager;
		this.internalKnowledgeBaseDeepSearchService = internalKnowledgeBaseDeepSearchService;
	}

	private static final JTokkitTokenCountEstimator tokenEstimator = new JTokkitTokenCountEstimator();

	private List<Flux<AbstractDeepSearchEvent>> dataSourcesDeepSearch(DeepSearchRequest request,
			List<AbstractDeepSearchEvent> history, List<IDeepSearchResult> dataSourcesResults, DeepSearchState state,
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
			if (handler.isEnabled(chatModel, deepSearchConfig, request)) {
				Flux<AbstractDeepSearchEvent> nextStepValue = null;
				state.setCurrentDataSourceHandlerRunning(handler.getHandlerId());
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Evaluating first found not yet executed external data source handler step:"
							+ state.getCurrentDataSourceHandlerRunning());
				}
				AtomicInteger totalSteps = new AtomicInteger(0);
				AtomicInteger doneSteps = new AtomicInteger(0);
				state.getDataSourcesStatusTotalSteps().put(handler.getHandlerId(), totalSteps);
				state.getDataSourcesStatusDoneSteps().put(handler.getHandlerId(), doneSteps);

				nextStepValue = handler.streamSearch(chatModel, serviceModel, deepSearchConfig, request,
						dataSourcesResults, chunkingSessionId, totalSteps, doneSteps, state);
				if (nextStepValue != null) {
					Flux<AbstractDeepSearchEvent> notificationFlux = DeepSearchNotificationEvent.flux(request,
							"Analyzing data from " + handler.getDescription(chatModel, deepSearchConfig, request));
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

	public Flux<AbstractDeepSearchEvent> streamDeepSearch(DeepSearchRequest request, AIDocumentsSet sessionDocuments,
			List<AbstractDeepSearchEvent> history, DeepSearchState state, DeepSearchConfig configuration,
			UserInfos userInfos, List<IGConfigurableEmbeddingModel> embeddingModels, IGConfigurableChatModel chatModel,
			IGConfigurableChatModel serviceModel, Scheduler deepSearchScheduler, String chunkingSessionId)
			throws LLMConfigException {

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin nextStep(....)");
		}
		if (request.getQuery() == null || request.getQuery().trim().length() == 0
				|| request.getKnowledgeBases() == null) {
			throw new IllegalStateException("Cannot run a deepsearch with no query or null knowledge bases list");
		}

		boolean externalSourcesEnabled = defaultDeepsearchConfig.isExternalSourcesEnabled();
		List<IDeepSearchResult> dataSourcesResults = new ArrayList<IDeepSearchResult>();
		Flux<AbstractDeepSearchEvent> composedFlux = DeepSearchNotificationEvent.flux(request,
				"Deep search data sources analisys...");
		List<Flux<AbstractDeepSearchEvent>> sources = new ArrayList<Flux<AbstractDeepSearchEvent>>();
		if (chatModel != null) {

			if (externalSourcesEnabled) {

				// Streaming search steps from handlers before knowledge base search
				List<IGReactiveDeepSearchDataSourceService> handlers = deepSearchDataSourcesRepositoryPattern
						.getImplementations();
				List<IGReactiveDeepSearchDataSourceService> dynamic = this.dataSourcesProvider
						.getDynamicDeepSearchServices();
				handlers.addAll(dynamic);
				handlers = handlers.stream().filter(x -> {
					try {
						return x.isEnabled(chatModel, configuration, request);
					} catch (SearchServiceException e) {
						LOGGER.error("Exception evaluating enabled services", e);
						return false;
					}
				}).toList();
				handlers = filterChoosed(handlers, request);
				if (!handlers.isEmpty()) {

					try {
						List<Flux<AbstractDeepSearchEvent>> newSources = dataSourcesDeepSearch(request, history,
								dataSourcesResults, state, handlers, chatModel, serviceModel, configuration,
								deepSearchScheduler, chunkingSessionId);
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
						.knowledgeBaseDeepSearch(request, sessionDocuments, dataSourcesResults, history, state,
								configuration, userInfos, chatModel, chunkingSessionId, embeddingModels);
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
					&& event.getOutputData() instanceof IDeepSearchResult intermediateResult) {
				intermediates.add(intermediateResult);
			}
			return event;
		});
		Flux<AbstractDeepSearchEvent> outFlux = enqueueDeepSearchProcessedEvent(composedFlux, request, history, state,
				configuration, userInfos, embeddingModels, chatModel, intermediates);

		return outFlux;

	}

	private Flux<AbstractDeepSearchEvent> enqueueDeepSearchProcessedEvent(Flux<AbstractDeepSearchEvent> composedFlux,
			DeepSearchRequest request, List<AbstractDeepSearchEvent> history, DeepSearchState state,
			DeepSearchConfig configuration, UserInfos userInfos, List<IGConfigurableEmbeddingModel> embeddingModels,
			IGConfigurableChatModel chatModel, Vector<IDeepSearchResult> intermediates) {
		Mono<AbstractDeepSearchEvent> deferred = Mono.fromCallable(() -> {
			final DeepSearchProcessedEvent consolidatedResult = new DeepSearchProcessedEvent();
			consolidatedResult.setInputData(request);
			consolidatedResult.setOutputData(new DeepSearchResponse());
			consolidatedResult.getOutputData().setDeepsearchCode(request.getCode());
			if (intermediates != null && !intermediates.isEmpty()) {
				List<LLMInputDocument> inputs = new ArrayList<LLMInputDocument>();
				for (IDeepSearchResult x : intermediates) {
					LLMInputDocument consolidated = new LLMInputDocument(x.getDataSourceDescription(), null, null,
							x.getResponse());
					inputs.add(consolidated);
				}
				String consolidatedText = callLLMConsolidateText(chatModel,
						promptsDao.findByPromptUse(GeboPromptsLibrary.DEEP_SEARCH_CONSOLIDATION_PROMPT).getPrompt(),
						request.getQuery(), "", inputs);
				consolidatedResult.getOutputData().setResponse(consolidatedText);
				consolidatedResult.getOutputData().setProcessPercentage(100);
				boolean haveResults = consolidatedText != null && consolidatedText.trim().length() > 0;
				consolidatedResult.getOutputData().setSearchResultsEmpty(!haveResults);
				consolidatedResult.getOutputData().setProcessPercentage(100);

			} else {
				consolidatedResult.getOutputData().setResponse(null);
				consolidatedResult.getOutputData().setProcessPercentage(100);
				consolidatedResult.getOutputData().setSearchResultsEmpty(true);

			}
			return consolidatedResult;
		});
		return Flux.concat(composedFlux, deferred);
	}

	private boolean thereAreNotEmpty(List<IDeepSearchResult> dataSourcesResults) {
		if (dataSourcesResults == null || dataSourcesResults.isEmpty())
			return false;
		return dataSourcesResults.stream().filter(x -> x.getSearchResultsEmpty() == null || !x.getSearchResultsEmpty())
				.count() > 0;
	}

	protected List<IGReactiveDeepSearchDataSourceService> getDeepSearchActiveHandlersInternal(
			DeepSearchConfig configuration) {
		if (!defaultDeepsearchConfig.isExternalSourcesEnabled())
			return List.of();
		IGConfigurableChatModel chatModel = null;
		if (configuration.getChatModelConfiguration() != null) {
			chatModel = chatModelsConfigDao.findByModelReference(configuration.getChatModelConfiguration());
		}
		if (chatModel == null) {
			chatModel = chatModelsConfigDao.defaultHandler();
		}
		if (chatModel == null)
			return List.of();
		final IGConfigurableChatModel fChatModel = chatModel;
		List<IGReactiveDeepSearchDataSourceService> handlersFullList = new ArrayList<IGReactiveDeepSearchDataSourceService>();
		List<IGReactiveDeepSearchDataSourceService> handlers = this.deepSearchDataSourcesRepositoryPattern
				.findImplementations(x -> {
					try {
						return x.isEnabled(fChatModel, configuration, null);
					} catch (Throwable e) {
						LOGGER.error("Error calling isEnabled", e);

						return false;
					}
				});
		List<IGReactiveDeepSearchDataSourceService> dynamicHandlers = this.dataSourcesProvider
				.getDynamicDeepSearchServices().stream().filter(x -> {
					try {
						return x.isEnabled(fChatModel, configuration, null);
					} catch (Throwable e) {
						LOGGER.error("Error calling isEnabled", e);
						return false;
					}
				}).toList();
		handlersFullList.addAll(handlers);
		handlersFullList.addAll(dynamicHandlers);
		return handlersFullList;
	}

	public List<GBaseObject> getDeepSearchActiveHandlers(DeepSearchConfig configuration) {
		if (!defaultDeepsearchConfig.isExternalSourcesEnabled())
			return List.of();
		IGConfigurableChatModel chatModel = null;
		if (configuration.getChatModelConfiguration() != null) {
			chatModel = chatModelsConfigDao.findByModelReference(configuration.getChatModelConfiguration());
		}
		if (chatModel == null) {
			chatModel = chatModelsConfigDao.defaultHandler();
		}
		if (chatModel == null)
			return List.of();
		final IGConfigurableChatModel fChatModel = chatModel;
		List<IGReactiveDeepSearchDataSourceService> handlersFullList = new ArrayList<IGReactiveDeepSearchDataSourceService>();
		List<IGReactiveDeepSearchDataSourceService> handlers = this.deepSearchDataSourcesRepositoryPattern
				.findImplementations(x -> {
					try {
						return x.isEnabled(fChatModel, configuration, null);
					} catch (Throwable e) {
						LOGGER.error("Error calling isEnabled", e);

						return false;
					}
				});
		List<IGReactiveDeepSearchDataSourceService> dynamicHandlers = this.dataSourcesProvider
				.getDynamicDeepSearchServices().stream().filter(x -> {
					try {
						return x.isEnabled(fChatModel, configuration, null);
					} catch (Throwable e) {
						LOGGER.error("Error calling isEnabled", e);
						return false;
					}
				}).toList();
		handlersFullList.addAll(handlers);
		handlersFullList.addAll(dynamicHandlers);
		return handlersFullList.stream().map(x -> {
			GBaseObject ds = new GBaseObject();
			ds.setCode(x.getHandlerId());
			ds.setDescription(x.getDescription(fChatModel, configuration, null));
			return ds;
		}).toList();
	}

}
