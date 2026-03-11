package ai.gebo.llms.deepsearch.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.tokenizer.JTokkitTokenCountEstimator;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.ai.service.IGPromptConfigDao;
import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.architecture.fulltext.service.FullTextException;
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
import ai.gebo.llms.chat.abstraction.layer.services.CommonChatPromptParamsUtil;
import ai.gebo.llms.chat.abstraction.layer.services.GeboChatSessionLifecycleException;
import ai.gebo.llms.chat.abstraction.layer.session.model.MinimalChatContext;
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
import ai.gebo.llms.deepsearch.service.IGInternalKnlowledgeBaseRagDeepSearchService;
import ai.gebo.llms.deepsearch.service.IGReactiveDeepSearchDataSourceService;
import ai.gebo.llms.deepsearch.service.IGReactiveDeepSearchDataSourceServiceRepositoryPattern;
import ai.gebo.llms.deepsearch.service.IGReactiveDynamicDataSourceServicesProvider;
import ai.gebo.llms.deepsearch.service.IGReactiveEnabledDeepSearchDataSourceLookupService;
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

	private final static Logger LOGGER = LoggerFactory.getLogger(FullReactiveDeepsearchWorker.class);
	private static final String DOCUMENT_NAME = "DOCUMENT NAME:";
	private static final String END_DOCUMENT_EXTRACTION = "[END DOCUMENT EXTRACTION]\r\n";
	private static final String DOCUMENT_EXTRACTION_BEGIN = "[BEGIN DOCUMENT EXTRACTION]\r\n";
	private final IGReactiveEnabledDeepSearchDataSourceLookupService enabledDataSourcesLookupService;
	private final DeepSearchDefaultConfig defaultDeepsearchConfig;
	private final IGPromptConfigDao promptsDao;
	private final IGeboThreadManager threadManager;
	private final IGInternalKnlowledgeBaseRagDeepSearchService internalKnowledgeBaseDeepSearchService;

	public FullReactiveDeepsearchWorker(IGChatModelRuntimeConfigurationDao chatModelsConfigDao,
			IGEmbeddingModelRuntimeConfigurationDao embeddingModelsRuntimeDao, IGeboThreadManager threadManager,
			IGPromptConfigDao promptsDao,
			IGInternalKnlowledgeBaseRagDeepSearchService internalKnowledgeBaseDeepSearchService,
			DeepSearchDefaultConfig defaultDeepsearchConfig,
			IGReactiveDeepSearchDataSourceServiceRepositoryPattern deepSearchDataSourcesRepositoryPattern,
			IGReactiveDynamicDataSourceServicesProvider dataSourcesProvider,
			IGReactiveEnabledDeepSearchDataSourceLookupService enabledDataSourcesLookupService) {
		super(chatModelsConfigDao, embeddingModelsRuntimeDao);
		this.enabledDataSourcesLookupService = enabledDataSourcesLookupService;
		this.defaultDeepsearchConfig = defaultDeepsearchConfig;
		this.promptsDao = promptsDao;
		this.threadManager = threadManager;
		this.internalKnowledgeBaseDeepSearchService = internalKnowledgeBaseDeepSearchService;
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
							"Extracting relevant documents",
							handler.getDescription(deepSearchConfig));
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

	public Flux<AbstractDeepSearchEvent> streamDeepSearch(DeepSearchRequest request,
			MinimalChatContext minimalChatContext, AIDocumentsSet sessionDocuments,
			List<AbstractDeepSearchEvent> history, DeepSearchConfig configuration, UserInfos userInfos,
			List<IGConfigurableEmbeddingModel> embeddingModels, IGConfigurableChatModel chatModel,
			IGConfigurableChatModel serviceModel, Scheduler deepSearchScheduler, String chunkingSessionId)
			throws LLMConfigException, GeboChatSessionLifecycleException, FullTextException {

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin nextStep(....)");
		}
		if (request.getQuery() == null || request.getQuery().trim().length() == 0
				|| request.getKnowledgeBases() == null) {
			throw new IllegalStateException("Cannot run a deepsearch with no query or null knowledge bases list");
		}

		final int satisfactoryDocumentsThreashold = this.defaultDeepsearchConfig
				.getInTopicSatisfactoryDocumentsThreashold(request.getUserIntent());
		DeepSearchState state = new DeepSearchState();
		state.setSatisfactoryDocumentsThreashold(satisfactoryDocumentsThreashold);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Using satisfactory documents threashold: " + satisfactoryDocumentsThreashold);
		}
		boolean externalSourcesEnabled = defaultDeepsearchConfig.isExternalSourcesEnabled();
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
					&& event.getOutputData() instanceof IDeepSearchResult intermediateResult) {
				intermediates.add(intermediateResult);
			}
			return event;
		});
		Flux<AbstractDeepSearchEvent> outFlux = enqueueDeepSearchProcessedEvent(composedFlux, request,
				minimalChatContext, history, state, configuration, userInfos, embeddingModels, chatModel,
				intermediates);

		return outFlux;

	}

	private Flux<AbstractDeepSearchEvent> enqueueDeepSearchProcessedEvent(Flux<AbstractDeepSearchEvent> composedFlux,
			DeepSearchRequest request, MinimalChatContext minimalChatContext, List<AbstractDeepSearchEvent> history,
			DeepSearchState state, DeepSearchConfig configuration, UserInfos userInfos,
			List<IGConfigurableEmbeddingModel> embeddingModels, IGConfigurableChatModel chatModel,
			Vector<IDeepSearchResult> intermediates) {
		final ReactiveIdentityUtil runAs = ReactiveIdentityUtil.create();
		final Map<String, Object> promptParams = CommonChatPromptParamsUtil.preparePromptParameters(minimalChatContext);
		Mono<AbstractDeepSearchEvent> deferred = Mono.fromCallable(() -> {
			return runAs.doRunAsWithReturn(() -> {
				final DeepSearchProcessedEvent consolidatedResult = new DeepSearchProcessedEvent();
				consolidatedResult.setInputData(request);
				consolidatedResult.setOutputData(new DeepSearchResponse());
				consolidatedResult.getOutputData().setDeepsearchCode(request.getCode());
				consolidatedResult.getOutputData().processedBy(chatModel);
				if (intermediates != null && !intermediates.isEmpty()) {
					List<LLMInputDocument> inputs = new ArrayList<LLMInputDocument>();
					for (IDeepSearchResult x : intermediates) {
						LLMInputDocument consolidated = new LLMInputDocument(x.getDataSourceDescription(), null, null,
								x.getResponse());
						inputs.add(consolidated);
					}
					String consolidatedText = callLLMConsolidateText(chatModel,
							promptsDao.findByPromptUse(GeboPromptsLibrary.DEEP_SEARCH_CONSOLIDATION_PROMPT).getPrompt(),
							request.getQuery(), "", promptParams, inputs);
					consolidatedResult.getOutputData().setResponse(consolidatedText);
					consolidatedResult.getOutputData().setProcessPercentage(100);
					boolean haveResults = consolidatedText != null && consolidatedText.trim().length() > 0;
					consolidatedResult.getOutputData().setSearchResultsEmpty(!haveResults);
					consolidatedResult.getOutputData().setProcessPercentage(100);

				} else {
					String backupText = callLLM(chatModel, promptsDao
							.findByPromptUse(GeboPromptsLibrary.DEEP_SEARCH_EMPTY_RESULTS_FALLBACK_PROMPT).getPrompt(),
							request.getQuery(), promptParams);
					consolidatedResult.getOutputData().setResponse(backupText);
					consolidatedResult.getOutputData().setProcessPercentage(100);
					consolidatedResult.getOutputData().setSearchResultsEmpty(true);

				}
				return consolidatedResult;
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
		List<IGReactiveDeepSearchDataSourceService> handlersFullList = this.enabledDataSourcesLookupService
				.enabledDataSources(configuration);
		return handlersFullList.stream().map(x -> {
			GBaseObject ds = new GBaseObject();
			ds.setCode(x.getHandlerId());
			ds.setDescription(x.getDescription(configuration));
			return ds;
		}).toList();
	}

}
