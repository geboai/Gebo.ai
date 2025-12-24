package ai.gebo.llms.deepsearch.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.architecture.search.model.BaseSearchResultsExtractionDataType;
import ai.gebo.architecture.search.model.SearchQuery;
import ai.gebo.architecture.search.model.SearchResult;
import ai.gebo.architecture.search.model.SearchResultReference;
import ai.gebo.architecture.search.model.SearchWithResults;
import ai.gebo.llms.abstraction.layer.services.BaseLlmsInvokingService;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.IGEmbeddingModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.deepsearch.datasources.model.DeepSearchDataSourceDocumentResult;
import ai.gebo.llms.deepsearch.datasources.model.DeepSearchDataSourceExtractedSearchQueries;
import ai.gebo.llms.deepsearch.datasources.model.DeepSearchDataSourceResponse;
import ai.gebo.llms.deepsearch.datasources.model.DeepSearchDataSourceStandardState;
import ai.gebo.llms.deepsearch.datasources.model.events.DeepSearchDataSourceDocumentResultEvent;
import ai.gebo.llms.deepsearch.datasources.model.events.DeepSearchDataSourceProcessedEvent;
import ai.gebo.llms.deepsearch.model.DeepSearchConfig;
import ai.gebo.llms.deepsearch.model.DeepSearchRequest;
import ai.gebo.llms.deepsearch.model.IDeepSearchResult;
import ai.gebo.llms.deepsearch.model.events.AbstractDeepSearchEvent;
import ai.gebo.system.ingestion.GeboIngestionException;

public abstract class GAbstractDeepSearchDataSourceService<CustomContentExtractionType extends BaseSearchResultsExtractionDataType>
		extends BaseLlmsInvokingService implements
		IGDeepSearchDataSourceService<DeepSearchDataSourceStandardState, SearchResult, DeepSearchDataSourceDocumentResult, DeepSearchDataSourceDocumentResultEvent> {

	final Class<CustomContentExtractionType> customContentExtractionType;
	protected static final String DATA_SOURCE_DESCRIPTION = "dataSourceDescription";
	private static final int MAX_NESTING_LEVEL = 2;
	private static final int MAX_DOCUMENT_TOKENS_SIZE_CONTEXT_MOLTIPLICATOR = 10;

	protected GAbstractDeepSearchDataSourceService(IGChatModelRuntimeConfigurationDao chatModelsConfigDao,
			IGEmbeddingModelRuntimeConfigurationDao embeddingModelsRuntimeDao,
			Class<CustomContentExtractionType> customContentExtractionType) {
		super(chatModelsConfigDao, embeddingModelsRuntimeDao);
		this.customContentExtractionType = customContentExtractionType;
	}

	@Override
	public DeepSearchDataSourceStandardState createInitialState(IGConfigurableChatModel chatModel,
			DeepSearchConfig deepSearchConfig, DeepSearchRequest request) {
		DeepSearchDataSourceStandardState state = new DeepSearchDataSourceStandardState();
		return state;
	}

	@Override
	public AbstractDeepSearchEvent nextStep(IGConfigurableChatModel chatModel, DeepSearchConfig deepSearchConfig,
			DeepSearchRequest request, List<IDeepSearchResult> pastSystemsResponses,
			DeepSearchDataSourceStandardState state, String previusConsolidatedResult)
			throws LLMConfigException, IOException, GeboIngestionException, GeboContentHandlerSystemException {
		if (state.getExtractedSearchQueries() == null) {
			DeepSearchDataSourceExtractedSearchQueries searchQueries = this.extractSearchQueries(request,
					pastSystemsResponses, deepSearchConfig, chatModel, previusConsolidatedResult);
			state.setExtractedSearchQueries(searchQueries);
			if (searchQueries.getSearchIsUnnecessary() != null && searchQueries.getSearchIsUnnecessary()) {
				DeepSearchDataSourceProcessedEvent returned = new DeepSearchDataSourceProcessedEvent();
				returned.setInputData(request);
				returned.setOutputData(new DeepSearchDataSourceResponse());
				returned.getOutputData().setSearchResultsEmpty(true);
				returned.getOutputData().setHandlerId(getHandlerId());
				returned.getOutputData().setDataSourceDescription(getDescription(chatModel, deepSearchConfig, request));
				returned.getOutputData().setDataSourceIndex(state.getDataSourceIndex());
				returned.getOutputData().setDeepsearchCode(request.getCode());
				state.setDataSourceIndex(state.getDataSourceIndex() + 1);
				return returned;
			}
			List<SearchWithResults> queryResults = new ArrayList<SearchWithResults>();
			for (SearchQuery query : searchQueries.getSearchQuery()) {
				List<SearchResult> results = executeSearch(query, request);
				if (results.isEmpty())
					continue;
				SearchWithResults sr = new SearchWithResults();
				sr.setResults(results);
				sr.setSearchQuery(query);
				queryResults.add(sr);
			}
			state.setQueryResults(cleanAndRemoveDuplicated(queryResults));
		}
		if (state.getQueryResults() == null || state.getQueryResults().isEmpty()) {
			DeepSearchDataSourceProcessedEvent returned = new DeepSearchDataSourceProcessedEvent();
			returned.setInputData(request);
			returned.setOutputData(new DeepSearchDataSourceResponse());
			returned.getOutputData().setSearchResultsEmpty(true);
			returned.getOutputData().setHandlerId(getHandlerId());
			returned.getOutputData().setDataSourceDescription(getDescription(chatModel, deepSearchConfig, request));
			returned.getOutputData().setDataSourceIndex(state.getDataSourceIndex());
			returned.getOutputData().setDeepsearchCode(request.getCode());
			state.setDataSourceIndex(state.getDataSourceIndex() + 1);

			return returned;
		}

		if (state.getQueryResultsIndex() < state.getQueryResults().size()) {
			SearchResult actualSearchResultToLoad = null;
			SearchWithResults actualResult = state.getQueryResults().get(state.getQueryResultsIndex());
			if (state.getQueryResultsReferenceIndex() < actualResult.getResults().size()) {
				actualSearchResultToLoad = actualResult.getResults().get(state.getQueryResultsReferenceIndex());
				int index = state.getQueryResultsReferenceIndex() + 1;
				state.setQueryResultsReferenceIndex(index);

			} else {
				int nextIndex = state.getQueryResultsIndex() + 1;
				state.setQueryResultsIndex(nextIndex);
				state.setQueryResultsReferenceIndex(0);
				if (nextIndex < state.getQueryResults().size()) {
					actualResult = state.getQueryResults().get(state.getQueryResultsIndex());
					if (state.getQueryResultsReferenceIndex() < actualResult.getResults().size()) {
						actualSearchResultToLoad = actualResult.getResults().get(state.getQueryResultsReferenceIndex());
						int index = state.getQueryResultsReferenceIndex() + 1;
						state.setQueryResultsReferenceIndex(index);
					}
				}
			}
			List<CustomContentExtractionType> analyzed = new ArrayList();
			if (actualSearchResultToLoad != null) {
				int maxTokens = MAX_DOCUMENT_TOKENS_SIZE_CONTEXT_MOLTIPLICATOR * chatModel.getContextLength();
				List<ConsolidationInput> inputs = this.loadDocumentFragments(actualSearchResultToLoad, request,
						maxTokens);
				String prompt = deepSearchConfig.getAnalisysPrompt();
				BiFunction<CustomContentExtractionType, CustomContentExtractionType, CustomContentExtractionType> aggregator = (
						CustomContentExtractionType actualData, CustomContentExtractionType currentConsolidation) -> {
					return this.customStructureConsolidation(actualData, currentConsolidation);
				};
				CustomContentExtractionType returned = super.callLLMConsolidateStructuredReturn(chatModel, prompt,
						request.getQuery(), previusConsolidatedResult, this.customContentExtractionType, aggregator,
						inputs);
				if (returned != null) {
					analyzed.add(returned);
				}
				if (actualSearchResultToLoad.getNestingLevel() < MAX_NESTING_LEVEL) {
					List<SearchResult> additionalResults = this.extractAdditionalReferencesToScan(returned, state);
					// Enqueue other references to visit after the actual visit
					if (additionalResults != null && !additionalResults.isEmpty()) {
						for (SearchResult r : additionalResults) {
							r.setNestingLevel(actualSearchResultToLoad.getNestingLevel() + 1);
						}
						SearchWithResults sr = new SearchWithResults();
						sr.setResults(additionalResults);
						state.getQueryResults().set(state.getQueryResultsIndex() + 1, sr);
					}
				}
			}
			DeepSearchDataSourceDocumentResultEvent analyzedEvent = new DeepSearchDataSourceDocumentResultEvent();
			analyzedEvent.setInputData(actualSearchResultToLoad);
			analyzedEvent.setOutputData(new DeepSearchDataSourceDocumentResult());
			analyzedEvent.getOutputData().setEmptyResult(analyzed.isEmpty());
			analyzedEvent.getOutputData().setHandlerId(getHandlerId());
			analyzedEvent.getOutputData().setDeepsearchCode(request.getCode());
			analyzedEvent.getOutputData().setAnalyzedSearchResult(actualSearchResultToLoad);
			analyzedEvent.getOutputData()
					.setDataSourceDescription(getDescription(chatModel, deepSearchConfig, request));
			analyzedEvent.getOutputData().setDocumentIndex(state.getQueryResultsReferenceIndex());

			analyzedEvent.getOutputData()
					.setDataSourceDescription(getDescription(chatModel, deepSearchConfig, request));
			if (!analyzed.isEmpty()) {
				if (analyzed.size() == 1) {
					analyzedEvent.getOutputData().setAnalyzedResult(analyzed.get(0).getExtractedRelevantContent());
				} else {
					String analyzedResult = consolidateReferenceResults(analyzed, request, deepSearchConfig, chatModel);
					analyzedEvent.getOutputData().setAnalyzedResult(analyzedResult);
				}
				state.getCumulatedAnalisys().add(analyzedEvent.getOutputData());
			}

			return analyzedEvent;

		}

		DeepSearchDataSourceProcessedEvent returned = consolidate(state.getCumulatedAnalisys(), request,
				deepSearchConfig, chatModel);
		return returned;
	}

	protected abstract List<SearchWithResults> cleanAndRemoveDuplicated(
			List<SearchWithResults> queryResults);

	protected abstract CustomContentExtractionType customStructureConsolidation(CustomContentExtractionType actualData,
			CustomContentExtractionType currentConsolidation);

	private DeepSearchDataSourceProcessedEvent consolidate(List<DeepSearchDataSourceDocumentResult> cumulatedAnalisys,
			DeepSearchRequest request, DeepSearchConfig deepSearchConfig, IGConfigurableChatModel chatModel) {
		List<ConsolidationInput> inputs = cumulatedAnalisys.stream().map(x -> {
			ConsolidationInput input = new ConsolidationInput(null, null, null, x.getAnalyzedResult());
			return input;
		}).toList();
		DeepSearchDataSourceProcessedEvent event = new DeepSearchDataSourceProcessedEvent();
		event.setInputData(request);
		event.setOutputData(new DeepSearchDataSourceResponse());
		event.getOutputData().setDataSourceDescription(this.getDescription(chatModel, deepSearchConfig, request));
		event.getOutputData().setHandlerId(getHandlerId());
		event.getOutputData().setDeepsearchCode(request.getCode());
		event.getOutputData().setSearchResultsEmpty(cumulatedAnalisys.isEmpty());
		List<SearchResultReference> dsReferences = new ArrayList<SearchResultReference>();
		for (DeepSearchDataSourceDocumentResult r : cumulatedAnalisys) {
			if (r.getAnalyzedSearchResult() != null && r.getAnalyzedSearchResult().getResultReference() != null)
				dsReferences.add(r.getAnalyzedSearchResult().getResultReference());
		}
		event.getOutputData().setDataSourceReferences(dsReferences);
		if (!cumulatedAnalisys.isEmpty()) {
			String data = super.callLLMConsolidateText(chatModel, deepSearchConfig.getConsolidationPrompt(),
					request.getQuery(), null, inputs);
			event.getOutputData().setResponse(data);
		}
		return event;
	}

	private String consolidateReferenceResults(List<CustomContentExtractionType> analyzed, DeepSearchRequest request,
			DeepSearchConfig deepSearchConfig, IGConfigurableChatModel chatModel) {
		List<ConsolidationInput> inputs = analyzed.stream().filter(
				x -> x.getExtractedRelevantContent() != null && x.getExtractedRelevantContent().trim().length() > 0)
				.map(x -> {
					ConsolidationInput entry = new ConsolidationInput(null, null, null,
							x.getExtractedRelevantContent());
					return entry;
				}).toList();
		if (inputs.isEmpty())
			return "";
		return callLLMConsolidateText(chatModel, deepSearchConfig.getAnalisysPrompt(), request.getQuery(), null,
				inputs);
	}

	protected abstract List<SearchResult> extractAdditionalReferencesToScan(CustomContentExtractionType returned,
			DeepSearchDataSourceStandardState state);

	protected abstract List<ConsolidationInput> loadDocumentFragments(SearchResult actualSearchResultToLoad,
			DeepSearchRequest request, int maxTokens)
			throws IOException, GeboIngestionException, GeboContentHandlerSystemException;

	protected abstract List<SearchResult> executeSearch(SearchQuery query, DeepSearchRequest request)
			throws IOException;

	protected DeepSearchDataSourceExtractedSearchQueries extractSearchQueries(DeepSearchRequest request,
			List<IDeepSearchResult> pastSystemsResponses, DeepSearchConfig deepSearchConfig,
			IGConfigurableChatModel chatModel, String consolidatedText) throws LLMConfigException {
		String prompt = createExtractSearchQueriesPrompt(request, pastSystemsResponses, deepSearchConfig, chatModel);
		Map<String, Object> additionalVariables = new HashMap<String, Object>();
		additionalVariables.put(DATA_SOURCE_DESCRIPTION, getDescription(chatModel, deepSearchConfig, request));
		return super.callLLMWithConsolidationStructuredReturn(chatModel, prompt, request.getQuery(),
				consolidatedText != null ? consolidatedText : "", additionalVariables,
				DeepSearchDataSourceExtractedSearchQueries.class);
	}

	protected abstract String createExtractSearchQueriesPrompt(DeepSearchRequest request,
			List<IDeepSearchResult> pastSystemsResponses, DeepSearchConfig deepSearchConfig,
			IGConfigurableChatModel chatModel);
}
