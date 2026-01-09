package ai.gebo.llms.deepsearch.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.architecture.search.model.BaseSearchResultsExtractionDataType;
import ai.gebo.architecture.search.model.SearchQuery;
import ai.gebo.architecture.search.model.SearchResult;
import ai.gebo.architecture.search.model.SearchResultReference;
import ai.gebo.architecture.search.model.SearchServiceException;
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
import lombok.AllArgsConstructor;
import lombok.Getter;

public abstract class GAbstractDeepSearchDataSourceService<CustomContentExtractionType extends BaseSearchResultsExtractionDataType>
		extends BaseLlmsInvokingService implements
		IGDeepSearchDataSourceService<DeepSearchDataSourceStandardState, SearchResult, DeepSearchDataSourceDocumentResult, DeepSearchDataSourceDocumentResultEvent> {
	private static final Logger LOGGER = LoggerFactory.getLogger(GAbstractDeepSearchDataSourceService.class);
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

	@Getter
	@AllArgsConstructor
	static class SearchResultsStepInfo {
		final SearchResult actualSearchResult;
		final SearchWithResults actualResult;

		boolean isEmpty() {
			return actualResult == null || actualSearchResult == null;
		}
	}

	protected SearchResultsStepInfo popSearchResult(DeepSearchDataSourceStandardState state) {
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
		return new SearchResultsStepInfo(actualSearchResultToLoad, actualResult);
	}

	@Override
	public AbstractDeepSearchEvent nextStep(IGConfigurableChatModel chatModel, DeepSearchConfig deepSearchConfig,
			DeepSearchRequest request, List<IDeepSearchResult> pastSystemsResponses,
			DeepSearchDataSourceStandardState state, String previusConsolidatedResult) throws LLMConfigException,
			IOException, GeboIngestionException, GeboContentHandlerSystemException, SearchServiceException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin nextStep(....) handler=" + getHandlerId());
		}
		DeepSearchDataSourceDocumentResultEvent analyzedEvent = null;
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
				try {
					List<SearchResult> results = executeSearch(query, request);
					if (results.isEmpty())
						continue;
					SearchWithResults sr = new SearchWithResults();
					sr.setResults(flattenResults(results));
					sr.setSearchQuery(query);
					queryResults.add(sr);
				} catch (Throwable th) {
					LOGGER.error("Handler:" + getHandlerId() + " fails running query:" + query, th);
				}
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Queries results=>" + queryResults);
			}
			queryResults = cleanAndRemoveDuplicated(queryResults);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Cleaned Queries results=>" + queryResults);
			}
			state.setQueryResults(queryResults);

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
			SearchResultsStepInfo actualSearchResultRef = null;

			int maxTokens = MAX_DOCUMENT_TOKENS_SIZE_CONTEXT_MOLTIPLICATOR * chatModel.getContextLength();
			List<ConsolidationInput> inputs = null;
			CustomContentExtractionType actualContribute = null;
			boolean currentIsInError = false;
			do {
				currentIsInError = false;
				actualSearchResultRef = popSearchResult(state);
				if (!actualSearchResultRef.isEmpty()) {
					try {
						inputs = this.loadDocumentFragments(actualSearchResultRef.actualSearchResult, request,
								maxTokens);
						if (inputs != null && !inputs.isEmpty()) {
							if (LOGGER.isDebugEnabled()) {
								LOGGER.debug("Sending to llm for structure consolidation content:"
										+ actualSearchResultRef.actualSearchResult);
							}
							String prompt = deepSearchConfig.getAnalisysPrompt();
							BiFunction<CustomContentExtractionType, CustomContentExtractionType, CustomContentExtractionType> aggregator = (
									CustomContentExtractionType actualData,
									CustomContentExtractionType currentConsolidation) -> {
								return this.customStructureConsolidation(actualData, currentConsolidation);
							};
							CustomContentExtractionType returned = super.callLLMConsolidateStructuredReturn(chatModel,
									prompt, request.getQuery(), previusConsolidatedResult,
									this.customContentExtractionType, aggregator, inputs);
							if (LOGGER.isDebugEnabled()) {
								LOGGER.debug("Consolidated structured output:" + returned);
							}
							if (returned != null && returned.getContentIsRelevant() != null
									&& returned.getContentIsRelevant()) {
								if (LOGGER.isDebugEnabled()) {
									LOGGER.debug("Actual content contribute is considered");
								}
								actualContribute = returned;
							} else {
								if (LOGGER.isDebugEnabled()) {
									LOGGER.warn("Actual content contribute is NOT considered!");
								}
							}
						}
					} catch (Throwable exception) {
						currentIsInError = true;
						LOGGER.error("Errors loading=>" + actualSearchResultRef.actualSearchResult, exception);
					}
				}
			} while (!actualSearchResultRef.isEmpty()
					&& (currentIsInError || (inputs == null || inputs.isEmpty() || actualContribute == null)));
			if (actualContribute != null && !actualSearchResultRef.isEmpty()) {

				analyzedEvent = new DeepSearchDataSourceDocumentResultEvent();
				analyzedEvent.setInputData(actualSearchResultRef.actualSearchResult);
				analyzedEvent.setOutputData(new DeepSearchDataSourceDocumentResult());
				analyzedEvent.getOutputData().setEmptyResult(false);
				analyzedEvent.getOutputData().setHandlerId(getHandlerId());
				analyzedEvent.getOutputData().setDeepsearchCode(request.getCode());
				analyzedEvent.getOutputData().setAnalyzedSearchResult(actualSearchResultRef.actualSearchResult);
				analyzedEvent.getOutputData()
						.setDataSourceDescription(getDescription(chatModel, deepSearchConfig, request));
				analyzedEvent.getOutputData().setDocumentIndex(state.getQueryResultsReferenceIndex());

				analyzedEvent.getOutputData()
						.setDataSourceDescription(getDescription(chatModel, deepSearchConfig, request));

				analyzedEvent.getOutputData().setAnalyzedResult(actualContribute.getExtractedRelevantContent());
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("End nextStep(....) handler=" + getHandlerId() + " returning " + analyzedEvent);
				}
				return analyzedEvent;
			}
		}

		DeepSearchDataSourceProcessedEvent returned = consolidate(state.getCumulatedAnalisys(), request,
				deepSearchConfig, chatModel);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("End nextStep(....) handler=" + getHandlerId() + " returning data source consolidation=> "
					+ returned);
		}
		return returned;
	}

	protected List<SearchResult> flattenResults(List<SearchResult> results) {
		List<SearchResult> flattened = new ArrayList<SearchResult>();
		for (SearchResult entry : results) {
			flattened.add(entry);
			flattened.addAll(flattenResults(entry.getChilds()));
		}
		return flattened;
	}

	protected abstract List<SearchWithResults> cleanAndRemoveDuplicated(List<SearchWithResults> queryResults);

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
			throws IOException, GeboIngestionException, GeboContentHandlerSystemException, SearchServiceException;

	protected abstract List<SearchResult> executeSearch(SearchQuery query, DeepSearchRequest request)
			throws IOException, SearchServiceException;

	protected DeepSearchDataSourceExtractedSearchQueries extractSearchQueries(DeepSearchRequest request,
			List<IDeepSearchResult> pastSystemsResponses, DeepSearchConfig deepSearchConfig,
			IGConfigurableChatModel chatModel, String consolidatedText) throws LLMConfigException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin extractSearchQueries(...) handler:" + getHandlerId());
		}
		String prompt = createExtractSearchQueriesPrompt(request, pastSystemsResponses, deepSearchConfig, chatModel);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Extracting queries with prompt:" + prompt);
		}
		Map<String, Object> additionalVariables = new HashMap<String, Object>();
		// With latest specialized prompt for each data source the following is not
		// needed
		// additionalVariables.put(DATA_SOURCE_DESCRIPTION, getDescription(chatModel,
		// deepSearchConfig, request));
		DeepSearchDataSourceExtractedSearchQueries searches = super.callLLMWithConsolidationStructuredReturn(chatModel,
				prompt, request.getQuery(), consolidatedText != null ? consolidatedText : "", additionalVariables,
				DeepSearchDataSourceExtractedSearchQueries.class);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("End extractSearchQueries(...) handler:" + getHandlerId() + " returning " + searches);
		}
		return searches;
	}

	protected abstract String createExtractSearchQueriesPrompt(DeepSearchRequest request,
			List<IDeepSearchResult> pastSystemsResponses, DeepSearchConfig deepSearchConfig,
			IGConfigurableChatModel chatModel);
}
