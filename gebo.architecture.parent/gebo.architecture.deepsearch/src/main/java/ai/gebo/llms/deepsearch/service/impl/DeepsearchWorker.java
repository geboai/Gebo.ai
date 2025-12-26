package ai.gebo.llms.deepsearch.service.impl;

import static org.assertj.core.api.Assertions.assertThatIllegalStateException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.ai.tokenizer.JTokkitTokenCountEstimator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.architecture.graphrag.persistence.model.KnowledgeGraphSearchResult;
import ai.gebo.architecture.graphrag.services.IKnowledgeGraphSearchService;
import ai.gebo.architecture.search.model.SearchServiceException;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.knowledgebase.repositories.DocumentReferenceRepository;
import ai.gebo.llms.abstraction.layer.model.RagDocumentFragment;
import ai.gebo.llms.abstraction.layer.model.RagDocumentReferenceItem;
import ai.gebo.llms.abstraction.layer.model.RagDocumentsCachedDaoResult;
import ai.gebo.llms.abstraction.layer.services.BaseLlmsInvokingService;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableEmbeddingModel;
import ai.gebo.llms.abstraction.layer.services.IGEmbeddingModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGRagDocumentsCachedDao;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.deepsearch.datasources.model.DeepSearchDataSourceResponse;
import ai.gebo.llms.deepsearch.datasources.model.events.DeepSearchDataSourceProcessedEvent;
import ai.gebo.llms.deepsearch.model.DataSourceExecutionTime;
import ai.gebo.llms.deepsearch.model.DeepSearchConfig;
import ai.gebo.llms.deepsearch.model.DeepSearchConfig.SearchType;
import ai.gebo.llms.deepsearch.model.events.AbstractDeepSearchEvent;
import ai.gebo.llms.deepsearch.model.events.DeepSearchDocumentEvent;
import ai.gebo.llms.deepsearch.model.events.DeepSearchProcessedEvent;
import ai.gebo.llms.deepsearch.model.DeepSearchDocumentAnalisysResultStep;
import ai.gebo.llms.deepsearch.model.DeepSearchPhase;
import ai.gebo.llms.deepsearch.model.DeepSearchRequest;
import ai.gebo.llms.deepsearch.model.DeepSearchResponse;
import ai.gebo.llms.deepsearch.model.DeepSearchState;
import ai.gebo.llms.deepsearch.model.IDeepSearchResult;
import ai.gebo.llms.deepsearch.service.IDynamicDataSourceServicesProvider;
import ai.gebo.llms.deepsearch.service.IGDeepSearchDataSourceService;
import ai.gebo.llms.deepsearch.service.IGDeepSearchDataSourceServiceRepositoryPattern;
import ai.gebo.model.GUserMessage;
import ai.gebo.model.base.GBaseObject;
import ai.gebo.security.repository.UserRepository.UserInfos;
import ai.gebo.system.ingestion.GeboIngestionException;

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

	public DeepsearchWorker(IGChatModelRuntimeConfigurationDao chatModelsConfigDao,
			IGEmbeddingModelRuntimeConfigurationDao embeddingModelsRuntimeDao) {
		super(chatModelsConfigDao, embeddingModelsRuntimeDao);
	}

	private static final JTokkitTokenCountEstimator tokenEstimator = new JTokkitTokenCountEstimator();

	private AbstractDeepSearchEvent dataSourcesNextStep(DeepSearchRequest request,
			List<AbstractDeepSearchEvent> history, List<IDeepSearchResult> dataSourcesResults, DeepSearchState state,
			List<IGDeepSearchDataSourceService> handlers, IGConfigurableChatModel chatModel,
			DeepSearchConfig deepSearchConfig) throws LLMConfigException, IOException, GeboIngestionException,
			GeboContentHandlerSystemException, SearchServiceException {

		AbstractDeepSearchEvent nextStepValue = null;
		// if already CurrentDataSourceHandlerRunning is initialized continue processing
		// next step
		if (state.getCurrentDataSourceHandlerRunning() != null) {
			Optional<IGDeepSearchDataSourceService> handler = handlers.stream()
					.filter(x -> x.getHandlerId().equals(state.getCurrentDataSourceHandlerRunning())).findFirst();

			if (handler.isPresent()) {
				IGDeepSearchDataSourceService businessLogic = handler.get();
				nextStepValue = businessLogic.nextStep(chatModel, deepSearchConfig, request, dataSourcesResults,
						state.getDataSourcesStatus().get(businessLogic.getHandlerId()), state.getConsolidatedResult());

			}
		} else {
			// find next data source to evaluate end execute
			for (IGDeepSearchDataSourceService handler : handlers) {
				if (!state.getDataSourcesStatus().containsKey(handler.getHandlerId())
						&& handler.isEnabled(chatModel, deepSearchConfig, request)) {
					state.setCurrentDataSourceHandlerRunning(handler.getHandlerId());
					Object initialState = handler.createInitialState(chatModel, deepSearchConfig, request);
					state.getDataSourcesStatus().put(handler.getHandlerId(), initialState);
					nextStepValue = handler.nextStep(chatModel, deepSearchConfig, request, dataSourcesResults,
							initialState, state.getConsolidatedResult());
				}
			}
		}
		if (nextStepValue != null) {
			// if next step is final one for datasource reset actual
			// CurrentDataSourceHandlerRunning
			if (nextStepValue instanceof DeepSearchDataSourceProcessedEvent) {
				state.setCurrentDataSourceHandlerRunning(null);
			}
			return nextStepValue;
		}
		return null;
	}

	private AbstractDeepSearchEvent knowledgeBaseDeepSearchNextStep(DeepSearchRequest request,
			List<IDeepSearchResult> dataSourcesResults, List<AbstractDeepSearchEvent> history, DeepSearchState state,
			DeepSearchConfig configuration, UserInfos userInfos, IGConfigurableChatModel chatModel) {

		if (state.getDocumentSearchResults() != null
				&& state.getRagDocumentsPointer() < state.getDocumentSearchResults().getDocumentItems().size()) {
			int tokensBudget = chatModel.getContextLength();
			RagDocumentReferenceItem foundDocument = state.getDocumentSearchResults().getDocumentItems()
					.get(state.getRagDocumentsPointer());
			String documentCode = foundDocument.getCode();
			Optional<GDocumentReference> docdata = documentRepo.findById(documentCode);
			if (docdata.isPresent()) {
				List<Document> currentProcessedFragments = new ArrayList<Document>();

				int initialFragmentPointer = state.getRagDocumentFragmentPointer();
				boolean lastFragmentReached = false;
				if (initialFragmentPointer < foundDocument.getFragments().size()) {
					boolean stopCycling = false;
					for (int i = initialFragmentPointer; !stopCycling && i < foundDocument.getFragments().size(); i++) {
						RagDocumentFragment fragment = foundDocument.getFragments().get(i);
						stopCycling = tokensBudget < fragment.getNTokens();
						if (!stopCycling) {
							state.setRagDocumentsPointer(i);
							tokensBudget -= fragment.getNTokens();
							lastFragmentReached = i == foundDocument.getFragments().size() - 1;
							currentProcessedFragments.add(fragment.toAIDocument());
							state.setRagDocumentFragmentPointer(i);
						}
					}
					if (lastFragmentReached) {
						state.setRagDocumentsPointer(state.getRagDocumentsPointer() + 1);
						state.setRagDocumentFragmentPointer(0);
					}
				}
				if (!currentProcessedFragments.isEmpty()) {
					state.setElaboratedFragmentsCount(
							state.getElaboratedFragmentsCount() + currentProcessedFragments.size());
					String result = callLLMWithDocuments(chatModel, configuration.getAnalisysPrompt(),
							currentProcessedFragments, request.getQuery());
					DeepSearchDocumentAnalisysResultStep resultStep = new DeepSearchDocumentAnalisysResultStep();
					resultStep.setDeepsearchCode(request.getCode());
					resultStep.setFragment(result);
					resultStep.setIndex(history.size());
					resultStep.setDocumentCode(documentCode);
					resultStep.setFragmentsCodes(currentProcessedFragments.stream().map(x -> x.getId()).toList());
					DeepSearchDocumentEvent event = new DeepSearchDocumentEvent();
					event.setProcessPercentage(state.calculateProcessedPercent());
					event.setInputData(docdata.get());
					event.setOutputData(resultStep);

					return event;
				}
			}

		}
		return null;
	}

	private List<IGDeepSearchDataSourceService> filterChoosed(List<IGDeepSearchDataSourceService> handlers,
			DeepSearchRequest request) {
		if (request.getDeepSearchDataSources() == null)
			return handlers;
		if (request.getDeepSearchDataSources().isEmpty())
			return List.of();
		return handlers.stream().filter(x -> request.getDeepSearchDataSources().contains(x.getHandlerId())).toList();
	}

	public AbstractDeepSearchEvent nextStep(DeepSearchRequest request, List<AbstractDeepSearchEvent> history,
			DeepSearchState state, DeepSearchConfig configuration, UserInfos userInfos,
			List<IGConfigurableEmbeddingModel> embeddingModels, IGConfigurableChatModel chatModel)
			throws LLMConfigException {

		if (request.getQuery() == null || request.getQuery().trim().length() == 0 || request.getKnowledgeBases() == null
				|| request.getKnowledgeBases().isEmpty()) {
			throw new IllegalStateException("Cannot run a deepsearch with no query or no knowledge bases list");
		}
		List<IGDeepSearchDataSourceService> providedDeepSearchSourceService = this.dataSourcesProvider
				.getDynamicDeepSearchServices();
		List<IDeepSearchResult> dataSourcesResults = new ArrayList<IDeepSearchResult>();
		if (chatModel != null && request.getKnowledgeBases() != null && !request.getKnowledgeBases().isEmpty()) {
			switch (state.getPhase()) {
			case BEFORE_KNOWLEDGE_BASE_SEARCH: {
				// Streaming search steps from handlers before knowledge base search
				List<IGDeepSearchDataSourceService> handlers = deepSearchDataSourcesRepositoryPattern
						.findByExecutionTime(DataSourceExecutionTime.RUNS_BEFORE_DOCUMENTS_SEARCH);
				handlers = new ArrayList<IGDeepSearchDataSourceService>(handlers);
				handlers.addAll(providedDeepSearchSourceService.stream()
						.filter(x -> x.getExecutionTime() == DataSourceExecutionTime.RUNS_BEFORE_DOCUMENTS_SEARCH)
						.toList());
				handlers = filterChoosed(handlers, request);
				if (!handlers.isEmpty()) {
					AbstractDeepSearchEvent nextStepValue = null;
					try {
						nextStepValue = dataSourcesNextStep(request, history, dataSourcesResults, state, handlers,
								chatModel, configuration);
					} catch (Throwable e) {
						LOGGER.error("Exception accessing deep search data source", e);
						DeepSearchDataSourceProcessedEvent processedDataSource = new DeepSearchDataSourceProcessedEvent();
						processedDataSource.setInputData(request);
						processedDataSource.setOutputData(new DeepSearchDataSourceResponse());
						processedDataSource.getOutputData().setSearchResultsEmpty(true);
						processedDataSource.getOutputData().setDeepsearchCode(request.getCode());
						processedDataSource.getOutputData()
								.setErrorMessage(GUserMessage.errorMessage("Exception in deep search", e));
						processedDataSource.setProcessPercentage(100);
						nextStepValue = processedDataSource;
					}
					if (nextStepValue != null) {
						if (nextStepValue instanceof DeepSearchDataSourceProcessedEvent processedDataSource) {
							if (processedDataSource.getOutputData().getSearchResultsEmpty() == null
									|| !processedDataSource.getOutputData().getSearchResultsEmpty()) {
								dataSourcesResults.add(processedDataSource.getOutputData());
								String _consolidatedResult = callLLMWithDocumentsAndConsolidation(chatModel,
										configuration.getConsolidationPrompt(),
										processedDataSource.getOutputData().getResponse(), request.getQuery(),
										state.getConsolidatedResult() != null ? state.getConsolidatedResult() : "");
								state.setConsolidatedResult(_consolidatedResult);
							}
						}
						return nextStepValue;
					}
				}

			}

			case KNOWLEDGE_BASE_SEARCH: {
				// Streaming search steps from knowledge base search
				state.setPhase(DeepSearchPhase.KNOWLEDGE_BASE_SEARCH);
				if (state.getDocumentSearchResults() == null) {

					RagDocumentsCachedDaoResult consolidatedDaoResult = getSearchResults(request, configuration,
							userInfos, embeddingModels);
					state.setDocumentSearchResults(consolidatedDaoResult);
					state.setFragmentsCount(consolidatedDaoResult.countFragments());
				}

				AbstractDeepSearchEvent nextStepValue = knowledgeBaseDeepSearchNextStep(request, dataSourcesResults,
						history, state, configuration, userInfos, chatModel);
				if (nextStepValue != null)
					return nextStepValue;
				else {
					String consolidatedResult = this.consolidateResult(chatModel, history, request, state,
							configuration);
					state.setConsolidatedResult(consolidatedResult);
				}

			}

			case AFTER_KNOWLEDGE_BASE_SEARCH: {
				// Streaming search steps after knowledge base search
				state.setPhase(DeepSearchPhase.AFTER_KNOWLEDGE_BASE_SEARCH);
				List<IGDeepSearchDataSourceService> handlers = deepSearchDataSourcesRepositoryPattern
						.findByExecutionTime(DataSourceExecutionTime.RUNS_AFTER_DOCUMENTS_SEARCH);
				handlers = new ArrayList<IGDeepSearchDataSourceService>(handlers);
				handlers.addAll(providedDeepSearchSourceService.stream()
						.filter(x -> x.getExecutionTime() == DataSourceExecutionTime.RUNS_AFTER_DOCUMENTS_SEARCH)
						.toList());
				handlers = filterChoosed(handlers, request);
				if (!handlers.isEmpty()) {
					AbstractDeepSearchEvent nextStepValue = null;
					try {
						nextStepValue = dataSourcesNextStep(request, history, dataSourcesResults, state, handlers,
								chatModel, configuration);
					} catch (Throwable e) {
						LOGGER.error("Exception accessing deep search data source", e);
						DeepSearchDataSourceProcessedEvent processedDataSource = new DeepSearchDataSourceProcessedEvent();
						processedDataSource.setInputData(request);
						processedDataSource.setOutputData(new DeepSearchDataSourceResponse());
						processedDataSource.getOutputData().setSearchResultsEmpty(true);
						processedDataSource.getOutputData().setDeepsearchCode(request.getCode());
						processedDataSource.getOutputData()
								.setErrorMessage(GUserMessage.errorMessage("Exception in deep search", e));
						processedDataSource.setProcessPercentage(100);
						nextStepValue = processedDataSource;
					}
					if (nextStepValue != null) {
						if (nextStepValue instanceof DeepSearchDataSourceProcessedEvent processedDataSource) {
							if (processedDataSource.getOutputData().getSearchResultsEmpty() == null
									|| !processedDataSource.getOutputData().getSearchResultsEmpty())
								dataSourcesResults.add(processedDataSource.getOutputData());
							String consolidatedResult = callLLMWithDocumentsAndConsolidation(chatModel,
									configuration.getConsolidationPrompt(),
									processedDataSource.getOutputData().getResponse(), request.getQuery(),
									state.getConsolidatedResult() != null ? state.getConsolidatedResult() : "");
							state.setConsolidatedResult(consolidatedResult);
						}
						return nextStepValue;
					}
				}
			}

			}

		}
		DeepSearchProcessedEvent consolidatedResult = new DeepSearchProcessedEvent();
		consolidatedResult.setInputData(request);
		consolidatedResult.setOutputData(new DeepSearchResponse());
		consolidatedResult.getOutputData().setResponse(state.getConsolidatedResult());
		boolean knowledgeBaseSearchesHaveResults = state.getDocumentSearchResults() != null
				&& state.getDocumentSearchResults().getDocumentItems().size() > 0;
		boolean otherDataSourceHaveResults = dataSourcesResults.size() > 0;
		consolidatedResult.getOutputData()
				.setSearchResultsEmpty(!(knowledgeBaseSearchesHaveResults || otherDataSourceHaveResults));
		return consolidatedResult;

	}

	private RagDocumentsCachedDaoResult getSearchResults(DeepSearchRequest request, DeepSearchConfig configuration,
			UserInfos userInfos, List<IGConfigurableEmbeddingModel> embeddingModels) {
		RagDocumentsCachedDaoResult consolidatedDaoResult = new RagDocumentsCachedDaoResult();
		for (IGConfigurableEmbeddingModel embeddingModel : embeddingModels) {
			RagDocumentsCachedDaoResult semanticDaoResult = new RagDocumentsCachedDaoResult();
			SearchType searchType = configuration.getSearchType();
			if (searchType == null) {
				searchType = SearchType.MULTI_HOP;
			}
			switch (searchType) {
			case MULTI_HOP: {
				semanticDaoResult = ragDocumentsCachedDao.multiHopSemanticSearch(request.getQuery(),
						configuration.getRagQueryOptions(), request.getKnowledgeBases(), embeddingModel,
						configuration.getFirstHopSimilarityThreashold(),
						configuration.getSecondHopSimilarityThreashold(), userInfos);
			}
				break;
			case SINGLE_HOP: {
				semanticDaoResult = ragDocumentsCachedDao.semanticSearch(request.getQuery(),
						configuration.getRagQueryOptions(), request.getKnowledgeBases(), embeddingModel, userInfos);
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

	private String consolidateResult(IGConfigurableChatModel chatModel, List<AbstractDeepSearchEvent> history,
			DeepSearchRequest request, DeepSearchState state, DeepSearchConfig configuration) {
		final int tokensBudget = chatModel.getContextLength();
		int tokens = 0;
		String consolidated = state.getConsolidatedResult() != null ? state.getConsolidatedResult() : "";

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
