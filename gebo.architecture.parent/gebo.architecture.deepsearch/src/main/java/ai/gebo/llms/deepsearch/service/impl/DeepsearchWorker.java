package ai.gebo.llms.deepsearch.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.tokenizer.JTokkitTokenCountEstimator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.architecture.graphrag.persistence.model.KnowledgeGraphSearchResult;
import ai.gebo.architecture.graphrag.services.IKnowledgeGraphSearchService;
import ai.gebo.architecture.rag_threasholds_autotune.model.OptimizedThreashold;
import ai.gebo.architecture.rag_threasholds_autotune.service.IRagThreasholdAutotuneService;
import ai.gebo.architecture.search.model.SearchServiceException;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.knowledgebase.repositories.DocumentReferenceRepository;
import ai.gebo.llms.abstraction.layer.model.RagDocumentFragment;
import ai.gebo.llms.abstraction.layer.model.RagDocumentsCachedDaoResult;
import ai.gebo.llms.abstraction.layer.model.RagQueryOptions;
import ai.gebo.llms.abstraction.layer.services.BaseLlmsInvokingService;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableEmbeddingModel;
import ai.gebo.llms.abstraction.layer.services.IGEmbeddingModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGRagDocumentsCachedDao;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.deepsearch.config.DeepSearchDefaultConfig;
import ai.gebo.llms.deepsearch.datasources.model.DeepSearchDataSourceResponse;
import ai.gebo.llms.deepsearch.datasources.model.events.DeepSearchDataSourceProcessedEvent;
import ai.gebo.llms.deepsearch.model.DataSourceExecutionTime;
import ai.gebo.llms.deepsearch.model.DeepSearchConfig;
import ai.gebo.llms.deepsearch.model.DeepSearchConfig.SearchType;
import ai.gebo.llms.deepsearch.model.DeepSearchDocumentAnalisysResultStep;
import ai.gebo.llms.deepsearch.model.DeepSearchKnowledgebasesResultStep;
import ai.gebo.llms.deepsearch.model.DeepSearchPhase;
import ai.gebo.llms.deepsearch.model.DeepSearchRequest;
import ai.gebo.llms.deepsearch.model.DeepSearchResponse;
import ai.gebo.llms.deepsearch.model.DeepSearchState;
import ai.gebo.llms.deepsearch.model.IDeepSearchResult;
import ai.gebo.llms.deepsearch.model.events.AbstractDeepSearchEvent;
import ai.gebo.llms.deepsearch.model.events.DeepSearchDocumentEvent;
import ai.gebo.llms.deepsearch.model.events.DeepSearchErrorEvent;
import ai.gebo.llms.deepsearch.model.events.DeepSearchKnowledgeBasesProcessedEvent;
import ai.gebo.llms.deepsearch.model.events.DeepSearchProcessedEvent;
import ai.gebo.llms.deepsearch.service.IDynamicDataSourceServicesProvider;
import ai.gebo.llms.deepsearch.service.IGDeepSearchDataSourceService;
import ai.gebo.llms.deepsearch.service.IGDeepSearchDataSourceServiceRepositoryPattern;
import ai.gebo.model.DocumentMetaInfos;
import ai.gebo.model.GUserMessage;
import ai.gebo.model.base.GBaseObject;
import ai.gebo.security.repository.UserRepository.UserInfos;
import ai.gebo.system.ingestion.GeboIngestionException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;

@Service
public class DeepsearchWorker extends BaseLlmsInvokingService {

	private static final String NEWLINE = "\r\n";
	private static final String SEARCH_MODULE_NAME = "Search module name:";
	private static final String END_DEEP_SEARCH_MODULE_RESULT = "[End Deep search module result]\r\n";
	private static final String BEGIN_DEEP_SEARCH_MODULE_RESULT = "[Begin Deep search module result]\r\n";
	private final static Logger LOGGER = LoggerFactory.getLogger(DeepsearchWorker.class);
	private static final String DOCUMENT_NAME = "DOCUMENT NAME:";
	private static final String END_DOCUMENT_EXTRACTION = "[END DOCUMENT EXTRACTION]\r\n";
	private static final String DOCUMENT_EXTRACTION_BEGIN = "[BEGIN DOCUMENT EXTRACTION]\r\n";
	@Autowired(required = false)
	private IKnowledgeGraphSearchService graphRagSearchService;
	@Autowired
	private IGRagDocumentsCachedDao ragDocumentsCachedDao;
	@Autowired
	private DocumentReferenceRepository documentRepo;
	@Autowired
	private IGDeepSearchDataSourceServiceRepositoryPattern deepSearchDataSourcesRepositoryPattern;
	@Autowired
	private IDynamicDataSourceServicesProvider dataSourcesProvider;
	@Autowired
	private DeepSearchDefaultConfig defaultDeepsearchConfig;
	@Autowired
	private IRagThreasholdAutotuneService threasholdAutotuneService;

	public DeepsearchWorker(IGChatModelRuntimeConfigurationDao chatModelsConfigDao,
			IGEmbeddingModelRuntimeConfigurationDao embeddingModelsRuntimeDao) {
		super(chatModelsConfigDao, embeddingModelsRuntimeDao);
	}

	private static final JTokkitTokenCountEstimator tokenEstimator = new JTokkitTokenCountEstimator();

	private Flux<AbstractDeepSearchEvent> dataSourcesNextStep(DeepSearchRequest request,
			List<AbstractDeepSearchEvent> history, List<IDeepSearchResult> dataSourcesResults, DeepSearchState state,
			List<IGDeepSearchDataSourceService> handlers, IGConfigurableChatModel chatModel,
			DeepSearchConfig deepSearchConfig, Scheduler deepSearchScheduler, String chunkingSessionId)
			throws LLMConfigException, IOException, GeboIngestionException, GeboContentHandlerSystemException,
			SearchServiceException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin dataSourcesNextStep(....)");
		}

		// if already CurrentDataSourceHandlerRunning is initialized continue processing
		// next step
		Flux<AbstractDeepSearchEvent> dataSourcesFlux = null;
		// find next data source to evaluate end execute
		for (IGDeepSearchDataSourceService handler : handlers) {
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

				nextStepValue = handler.streamSearch(chatModel, deepSearchConfig, request, dataSourcesResults,
						chunkingSessionId, totalSteps, doneSteps, state);
				nextStepValue.subscribeOn(deepSearchScheduler);
				if (dataSourcesFlux == null) {
					dataSourcesFlux = nextStepValue;
				} else {
					dataSourcesFlux = Flux.concat(dataSourcesFlux, nextStepValue);
				}
			}
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("End dataSourcesNextStep(....)");
		}
		return dataSourcesFlux;
	}

	private Flux<AbstractDeepSearchEvent> knowledgeBaseDeepSearchNextStep(DeepSearchRequest request,
			List<IDeepSearchResult> dataSourcesResults, List<AbstractDeepSearchEvent> history, DeepSearchState state,
			DeepSearchConfig configuration, UserInfos userInfos, IGConfigurableChatModel chatModel,
			String chunkingSessionId, List<IGConfigurableEmbeddingModel> embeddingModels) {
		RagDocumentsCachedDaoResult consolidatedDaoResult = getSearchResults(request, configuration, userInfos,
				embeddingModels);
		final String analisysPrompt = configuration.getAnalisysPrompt();

		final Vector<ConsolidationInput> results = new Vector<ConsolidationInput>();

		Flux<AbstractDeepSearchEvent> body = Flux.fromIterable(consolidatedDaoResult.getDocumentItems())
				.map((refItem) -> {
					String documentCode = refItem.getCode();
					Optional<GDocumentReference> docdata = documentRepo.findById(refItem.getCode());
					if (docdata.isPresent()) {
						List<RagDocumentFragment> fragments = refItem.getFragments();

						List<ConsolidationInput> inputs = new ArrayList<ConsolidationInput>();
						for (RagDocumentFragment f : fragments) {
							Map<String, Object> meta = f.getMetaData();
							String docReference = meta != null ? (String) meta.get(DocumentMetaInfos.GEBO_FILE_NAME)
									: null;
							String url = meta != null ? (String) meta.get(DocumentMetaInfos.CONTENT_ORIGINAL_URL)
									: null;
							String title = meta != null ? (String) meta.get(DocumentMetaInfos.TITLE) : null;
							if (title == null)
								title = docReference;
							ConsolidationInput cInput = new ConsolidationInput(docReference, url, title,
									f.getDocumentContent());
							inputs.add(cInput);
						}
						try {
							String result = callLLMConsolidateText(chatModel, analisysPrompt, request.getQuery(), "",
									inputs);
							DeepSearchDocumentAnalisysResultStep resultStep = new DeepSearchDocumentAnalisysResultStep();
							resultStep.setDeepsearchCode(request.getCode());
							resultStep.setFragment(result);
							resultStep.setIndex(history.size());
							resultStep.setDocumentCode(documentCode);
							resultStep.setFragmentsCodes(fragments.stream().map(x -> x.getCode()).toList());
							DeepSearchDocumentEvent event = new DeepSearchDocumentEvent();
							resultStep.setProcessPercentage(state.calculateProcessedPercent());
							event.setInputData(docdata.get());
							event.setOutputData(resultStep);
							ConsolidationInput input = new ConsolidationInput(event.getInputData().getName(),
									event.getInputData().getUri(), event.getInputData().getName(), result);
							results.add(input);
							return (AbstractDeepSearchEvent) event;
						} catch (Throwable th) {
							LOGGER.error("Error calling llm", th);
							DeepSearchErrorEvent event = new DeepSearchErrorEvent();
							event.setInputData(request);
							event.setOutputData(GUserMessage.errorMessage("Error calling llm", th));
							return (AbstractDeepSearchEvent) event;
						}
					} else
						return null;
				}).filter(Objects::nonNull);
		Flux<AbstractDeepSearchEvent> trail = Flux.defer(() -> {
			AbstractDeepSearchEvent evt = null;
			DeepSearchKnowledgeBasesProcessedEvent event = new DeepSearchKnowledgeBasesProcessedEvent();
			event.setInputData(request);
			event.setOutputData(new DeepSearchKnowledgebasesResultStep());
			event.getOutputData().setDataSourceDescription("Knowledge bases");
			event.getOutputData().setDeepsearchCode(request.getCode());
			event.getOutputData().setSearchResultsEmpty(results.isEmpty());
			try {
				if (!results.isEmpty()) {
					String result = callLLMConsolidateText(chatModel, configuration.getConsolidationPrompt(),
							request.getQuery(), "", new ArrayList(results));
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
		return Flux.concat(body, trail);
	}

	private List<IGDeepSearchDataSourceService> filterChoosed(List<IGDeepSearchDataSourceService> handlers,
			DeepSearchRequest request) {
		if (request.getDeepSearchDataSources() == null)
			return handlers;
		if (request.getDeepSearchDataSources().isEmpty())
			return List.of();
		return handlers.stream().filter(x -> request.getDeepSearchDataSources().contains(x.getHandlerId())).toList();
	}

	public Flux<AbstractDeepSearchEvent> streamDeepSearch(DeepSearchRequest request,
			List<AbstractDeepSearchEvent> history, DeepSearchState state, DeepSearchConfig configuration,
			UserInfos userInfos, List<IGConfigurableEmbeddingModel> embeddingModels, IGConfigurableChatModel chatModel,
			Scheduler deepSearchScheduler, String chunkingSessionId) throws LLMConfigException {

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin nextStep(....)");
		}
		if (request.getQuery() == null || request.getQuery().trim().length() == 0
				|| request.getKnowledgeBases() == null) {
			throw new IllegalStateException("Cannot run a deepsearch with no query or null knowledge bases list");
		}
		List<IGDeepSearchDataSourceService> providedDeepSearchSourceService = this.dataSourcesProvider
				.getDynamicDeepSearchServices();
		boolean externalSourcesEnabled = defaultDeepsearchConfig.isExternalSourcesEnabled();
		List<IDeepSearchResult> dataSourcesResults = new ArrayList<IDeepSearchResult>();
		Flux<AbstractDeepSearchEvent> composedFlux = null;

		if (chatModel != null) {

			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Actual phase:" + state.getPhase());
			}
			switch (state.getPhase()) {
			case BEFORE_KNOWLEDGE_BASE_SEARCH: {
				if (externalSourcesEnabled) {

					// Streaming search steps from handlers before knowledge base search
					List<IGDeepSearchDataSourceService> handlers = deepSearchDataSourcesRepositoryPattern
							.findByExecutionTime(DataSourceExecutionTime.RUNS_BEFORE_DOCUMENTS_SEARCH);
					handlers = new ArrayList<IGDeepSearchDataSourceService>(handlers);
					handlers.addAll(providedDeepSearchSourceService.stream()
							.filter(x -> x.getExecutionTime() == DataSourceExecutionTime.RUNS_BEFORE_DOCUMENTS_SEARCH)
							.toList());
					handlers = filterChoosed(handlers, request);
					if (!handlers.isEmpty()) {
						Flux<AbstractDeepSearchEvent> nextStepValue = null;
						try {
							nextStepValue = dataSourcesNextStep(request, history, dataSourcesResults, state, handlers,
									chatModel, configuration, deepSearchScheduler, chunkingSessionId);

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
							nextStepValue = Flux.just(processedDataSource);

						}
						if (nextStepValue != null) {
							if (composedFlux == null) {
								composedFlux = nextStepValue;
							} else {
								composedFlux = Flux.concat(composedFlux, nextStepValue);
							}
						}

					}
				}

			}

			case KNOWLEDGE_BASE_SEARCH: {
				// Streaming search steps from knowledge base search
				state.setPhase(DeepSearchPhase.KNOWLEDGE_BASE_SEARCH);
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Actual phase:" + state.getPhase());
				}
				if (request.getKnowledgeBases() != null && !request.getKnowledgeBases().isEmpty()) {

					Flux<AbstractDeepSearchEvent> nextStepValue = knowledgeBaseDeepSearchNextStep(request,
							dataSourcesResults, history, state, configuration, userInfos, chatModel, chunkingSessionId,
							embeddingModels);
					if (nextStepValue == null) {
						boolean singleSource = !thereAreNotEmpty(dataSourcesResults);
						String consolidatedResult = null;
						if (state.getDocumentSearchResults().getDocumentItems().size() > 0) {
							// TODO: CONSOLIDATION NOT CALLED IF ONLY 1 NOT EMPTY DATA SOURCE RESULT BUT
							// SIMPLY GET THE DATASOURCE RESULTING TEXT (CHECK)
							consolidatedResult = this.consolidateKnowledgeBaseResult(chatModel, history, request, state,
									configuration);

						}
						// Here i have to return the new
						DeepSearchKnowledgeBasesProcessedEvent event = new DeepSearchKnowledgeBasesProcessedEvent();
						event.setInputData(request);
						event.setOutputData(new DeepSearchKnowledgebasesResultStep());
						event.getOutputData().setCode(UUID.randomUUID().toString());
						event.getOutputData().setDeepsearchCode(request.getCode());
						event.getOutputData().setResponse(consolidatedResult);
						event.getOutputData()
								.setSearchResultsEmpty(state.getDocumentSearchResults().getDocumentItems().isEmpty());
						event.getOutputData().setDataSourceDescription(
								"RAG/GRAPHRAG Knowledge bases " + request.getKnowledgeBases());
						event.getOutputData().setProcessPercentage(state.calculateProcessedPercent());
						state.setPhase(DeepSearchPhase.AFTER_KNOWLEDGE_BASE_SEARCH);
						dataSourcesResults.add(event.getOutputData());
						nextStepValue = Flux.just(event);
					}
					if (nextStepValue != null) {
						if (composedFlux == null) {
							composedFlux = nextStepValue;
						} else {
							composedFlux = Flux.concat(composedFlux, nextStepValue);
						}
					}
				} else {
					state.setPhase(DeepSearchPhase.AFTER_KNOWLEDGE_BASE_SEARCH);
				}

			}

			case AFTER_KNOWLEDGE_BASE_SEARCH: {
				// Streaming search steps after knowledge base search
				if (externalSourcesEnabled) {
					state.setPhase(DeepSearchPhase.AFTER_KNOWLEDGE_BASE_SEARCH);
					if (LOGGER.isDebugEnabled()) {
						LOGGER.debug("Actual phase:" + state.getPhase());
					}
					List<IGDeepSearchDataSourceService> handlers = deepSearchDataSourcesRepositoryPattern
							.findByExecutionTime(DataSourceExecutionTime.RUNS_AFTER_DOCUMENTS_SEARCH);
					handlers = new ArrayList<IGDeepSearchDataSourceService>(handlers);
					handlers.addAll(providedDeepSearchSourceService.stream()
							.filter(x -> x.getExecutionTime() == DataSourceExecutionTime.RUNS_AFTER_DOCUMENTS_SEARCH)
							.toList());
					handlers = filterChoosed(handlers, request);
					if (!handlers.isEmpty()) {
						Flux<AbstractDeepSearchEvent> nextStepValue = null;
						try {
							nextStepValue = dataSourcesNextStep(request, history, dataSourcesResults, state, handlers,
									chatModel, configuration, deepSearchScheduler, chunkingSessionId);
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
							nextStepValue = Flux.just(processedDataSource);
						}
						if (nextStepValue != null) {
							if (composedFlux == null) {
								composedFlux = nextStepValue;
							} else {
								composedFlux = Flux.concat(composedFlux, nextStepValue);
							}
						}

					}

				}

			}
			}
		} else {

			throw new RuntimeException("Cannot run deep search without a chat model");
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Consolidate final result");
		}

		Flux<AbstractDeepSearchEvent> outFlux = enqueueDeepSearchProcessedEvent(composedFlux, request, history, state,
				configuration, userInfos, embeddingModels, chatModel);

		return outFlux;

	}

	private Flux<AbstractDeepSearchEvent> enqueueDeepSearchProcessedEvent(Flux<AbstractDeepSearchEvent> composedFlux,
			DeepSearchRequest request, List<AbstractDeepSearchEvent> history, DeepSearchState state,
			DeepSearchConfig configuration, UserInfos userInfos, List<IGConfigurableEmbeddingModel> embeddingModels,
			IGConfigurableChatModel chatModel) {
		List<IDeepSearchResult> dataSourcesResults = null;
		final DeepSearchProcessedEvent consolidatedResult = new DeepSearchProcessedEvent();
		consolidatedResult.setInputData(request);
		consolidatedResult.setOutputData(new DeepSearchResponse());
		if (composedFlux != null) {
			final Flux<IDeepSearchResult> sourcesFlux = composedFlux.map(AbstractDeepSearchEvent::getOutputData)
					.filter(Objects::nonNull)
					.filter(x -> x instanceof IDeepSearchResult sr
							&& (sr.getSearchResultsEmpty() == null || !sr.getSearchResultsEmpty()))
					.cast(IDeepSearchResult.class);
			final Flux<ConsolidationInput> mapped = sourcesFlux.map(x -> {
				ConsolidationInput consolidated = new ConsolidationInput(x.getDataSourceDescription(), null, null,
						x.getResponse());
				return consolidated;
			});
			Mono<List<ConsolidationInput>> monoList = mapped.collectList();
			String consolidatedText = callLLMConsolidateText(chatModel, configuration.getConsolidationPrompt(),
					request.getQuery(), "", monoList.block());
			consolidatedResult.getOutputData().setResponse(consolidatedText);
			consolidatedResult.getOutputData().setProcessPercentage(100);
			boolean haveResults = consolidatedText != null && consolidatedText.trim().length() > 0;

			consolidatedResult.getOutputData().setSearchResultsEmpty(!haveResults);
			consolidatedResult.getOutputData().setProcessPercentage(100);
			composedFlux = Flux.concat(composedFlux, Flux.just(consolidatedResult));
			return composedFlux;
		} else {

			consolidatedResult.getOutputData().setResponse(null);
			consolidatedResult.getOutputData().setProcessPercentage(100);
			boolean knowledgeBaseSearchesHaveResults = state.getDocumentSearchResults() != null
					&& state.getDocumentSearchResults().getDocumentItems().size() > 0;

			consolidatedResult.getOutputData().setSearchResultsEmpty(true);
			return Flux.just(consolidatedResult);
		}

	}

	private boolean thereAreNotEmpty(List<IDeepSearchResult> dataSourcesResults) {
		if (dataSourcesResults == null || dataSourcesResults.isEmpty())
			return false;
		return dataSourcesResults.stream().filter(x -> x.getSearchResultsEmpty() == null || !x.getSearchResultsEmpty())
				.count() > 0;
	}

	private RagDocumentsCachedDaoResult getSearchResults(DeepSearchRequest request, DeepSearchConfig configuration,
			UserInfos userInfos, List<IGConfigurableEmbeddingModel> embeddingModels) {
		RagDocumentsCachedDaoResult consolidatedDaoResult = new RagDocumentsCachedDaoResult();
		for (IGConfigurableEmbeddingModel embeddingModel : embeddingModels) {
			OptimizedThreashold optimizedSetting = this.threasholdAutotuneService
					.findByEmbeddingModelCode(embeddingModel.getCode());
			RagDocumentsCachedDaoResult semanticDaoResult = new RagDocumentsCachedDaoResult();
			SearchType searchType = configuration.getSearchType();
			if (searchType == null) {
				searchType = SearchType.MULTI_HOP;
			}
			switch (searchType) {
			case MULTI_HOP: {
				double firstHopSimilarityThreashold = optimizedSetting != null
						? optimizedSetting.getFirstHopOptimizedThreashold()
						: defaultDeepsearchConfig.getFirstHopSimilarityThreashold();
				double secondHopSimilarityThreashold = optimizedSetting != null
						? optimizedSetting.getSecondHopOptimizedThreashold()
						: defaultDeepsearchConfig.getSecondHopSimilarityThreashold();
				if (configuration.getManualThreasholdsConfiguration() != null
						&& configuration.getManualThreasholdsConfiguration()
						&& configuration.getFirstHopSimilarityThreashold() != null
						&& configuration.getSecondHopSimilarityThreashold() != null) {
					firstHopSimilarityThreashold = configuration.getFirstHopSimilarityThreashold();
					secondHopSimilarityThreashold = configuration.getSecondHopSimilarityThreashold();
				}
				semanticDaoResult = ragDocumentsCachedDao.multiHopSemanticSearch(request.getQuery(),
						configuration.getRagQueryOptions(), request.getKnowledgeBases(), embeddingModel,
						firstHopSimilarityThreashold, secondHopSimilarityThreashold, userInfos);
			}
				break;
			case SINGLE_HOP: {
				double similarityThreashold = optimizedSetting != null ? optimizedSetting.getOptimizedThreashold()
						: defaultDeepsearchConfig.getRagQueryOptions().getSimilarityThreashold();
				RagQueryOptions ragQueryOptions = new RagQueryOptions(configuration.getRagQueryOptions());
				ragQueryOptions.setSimilarityThreashold(similarityThreashold);
				semanticDaoResult = ragDocumentsCachedDao.semanticSearch(request.getQuery(), ragQueryOptions,
						request.getKnowledgeBases(), embeddingModel, userInfos);
			}
				break;
			}

			consolidatedDaoResult = RagDocumentsCachedDaoResult.join(semanticDaoResult, consolidatedDaoResult);
		}

		if (graphRagSearchService != null && graphRagSearchService.isConfigured(null)) {
			try {

				List<KnowledgeGraphSearchResult> graphRagResult = graphRagSearchService.knowledgeGraphSearch(
						request.getQuery(), request.getKnowledgeBases(), configuration.getGraphRagTopN().intValue());
				RagDocumentsCachedDaoResult graphragDocumentsResult = graphRagSearchService
						.toRagDocumentsCachedDaoResult(graphRagResult);
				consolidatedDaoResult = RagDocumentsCachedDaoResult.join(consolidatedDaoResult,
						graphragDocumentsResult);
			} catch (LLMConfigException e) {
				LOGGER.error("Error calling the graphrag logic", e);
			}
		}
		return consolidatedDaoResult;
	}

	private String consolidateKnowledgeBaseResult(IGConfigurableChatModel chatModel,
			List<AbstractDeepSearchEvent> history, DeepSearchRequest request, DeepSearchState state,
			DeepSearchConfig configuration) {
		final int tokensBudget = chatModel.getContextLength();
		int tokens = 0;
		String consolidated = "";

		if (!history.isEmpty()) {
			StringBuffer fragments = new StringBuffer();
			List<DeepSearchDocumentAnalisysResultStep> steps = new ArrayList<DeepSearchDocumentAnalisysResultStep>();
			for (AbstractDeepSearchEvent event : history) {
				if (event instanceof DeepSearchDocumentEvent docEvent) {
					steps.add(docEvent.getOutputData());
					String actualFragment = docEvent.getOutputData().getFragment();
					GDocumentReference document = docEvent.getInputData();
					int length = tokenEstimator.estimate(actualFragment);
					if (tokens + length >= tokensBudget) {

						consolidated = callLLMWithDocumentsAndConsolidation(chatModel,
								configuration.getConsolidationPrompt(), fragments.toString(), request.getQuery(),
								consolidated);
						fragments = new StringBuffer();
						tokens = 0;

					}

					fragments.append(DOCUMENT_EXTRACTION_BEGIN);
					fragments.append(DOCUMENT_NAME + document.getName());
					fragments.append(actualFragment);
					fragments.append(END_DOCUMENT_EXTRACTION);
					tokens += length;
				}
			}
			if (!fragments.isEmpty()) {
				consolidated = callLLMWithDocumentsAndConsolidation(chatModel, configuration.getConsolidationPrompt(),
						fragments.toString(), request.getQuery(), consolidated);
			}
		}

		return consolidated;
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
		List<IGDeepSearchDataSourceService> handlersFullList = new ArrayList<IGDeepSearchDataSourceService>();
		List<IGDeepSearchDataSourceService> handlers = this.deepSearchDataSourcesRepositoryPattern
				.findImplementations(x -> {
					try {
						return x.isEnabled(fChatModel, configuration, null);
					} catch (Throwable e) {
						LOGGER.error("Error calling isEnabled", e);

						return false;
					}
				});
		List<IGDeepSearchDataSourceService> dynamicHandlers = this.dataSourcesProvider.getDynamicDeepSearchServices()
				.stream().filter(x -> {
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
