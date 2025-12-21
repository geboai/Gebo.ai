package ai.gebo.llms.deepsearch.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.ai.document.Document;

import ai.gebo.llms.abstraction.layer.services.BaseLlmsInvokingService;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.IGEmbeddingModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.deepsearch.datasources.model.AnalyzedSearchResult;
import ai.gebo.llms.deepsearch.datasources.model.BaseDataSourceExtractionDataType;
import ai.gebo.llms.deepsearch.datasources.model.ExtractedSearchQueries;
import ai.gebo.llms.deepsearch.datasources.model.RemoteReferenceAnalyzedDeepSearchEvent;
import ai.gebo.llms.deepsearch.datasources.model.RemoteSystemDeepSearchDataSourceStandardState;
import ai.gebo.llms.deepsearch.datasources.model.SearchQuery;
import ai.gebo.llms.deepsearch.datasources.model.SearchResult;
import ai.gebo.llms.deepsearch.datasources.model.SearchResults;
import ai.gebo.llms.deepsearch.model.AbstractDeepSearchEvent;
import ai.gebo.llms.deepsearch.model.DeepSearchConfig;
import ai.gebo.llms.deepsearch.model.DeepSearchDataSourceProcessedEvent;
import ai.gebo.llms.deepsearch.model.DeepSearchDataSourceResponse;
import ai.gebo.llms.deepsearch.model.DeepSearchRequest;
import ai.gebo.llms.deepsearch.model.IDeepSearchResult;

public abstract class GAbstractDeepSearchDataSourceService<CustomContentExtractionType extends BaseDataSourceExtractionDataType>
		extends BaseLlmsInvokingService implements
		IGDeepSearchDataSourceService<RemoteSystemDeepSearchDataSourceStandardState, SearchResult, AnalyzedSearchResult, RemoteReferenceAnalyzedDeepSearchEvent> {

	final Class<CustomContentExtractionType> customContentExtractionType;
	private static final int MAX_NESTING_LEVEL = 2;
	private static final int MAX_DOCUMENT_TOKENS_SIZE_CONTEXT_MOLTIPLICATOR = 10;

	protected GAbstractDeepSearchDataSourceService(IGChatModelRuntimeConfigurationDao chatModelsConfigDao,
			IGEmbeddingModelRuntimeConfigurationDao embeddingModelsRuntimeDao,
			Class<CustomContentExtractionType> customContentExtractionType) {
		super(chatModelsConfigDao, embeddingModelsRuntimeDao);
		this.customContentExtractionType = customContentExtractionType;
	}

	@Override
	public RemoteSystemDeepSearchDataSourceStandardState createInitialState(IGConfigurableChatModel chatModel,
			DeepSearchConfig deepSearchConfig, DeepSearchRequest request) {
		RemoteSystemDeepSearchDataSourceStandardState state = new RemoteSystemDeepSearchDataSourceStandardState();
		return state;
	}

	@Override
	public AbstractDeepSearchEvent nextStep(IGConfigurableChatModel chatModel, DeepSearchConfig deepSearchConfig,
			DeepSearchRequest request, List<IDeepSearchResult> pastSystemsResponses,
			RemoteSystemDeepSearchDataSourceStandardState state, String previusConsolidatedResult)
			throws LLMConfigException {
		if (state.getExtractedSearchQueries() == null) {
			ExtractedSearchQueries searchQueries = this.extractSearchQueries(request, pastSystemsResponses,
					deepSearchConfig, chatModel, previusConsolidatedResult);
			state.setExtractedSearchQueries(searchQueries);
			if (searchQueries.getSearchIsUnnecessary() != null && searchQueries.getSearchIsUnnecessary()) {
				DeepSearchDataSourceProcessedEvent returned = new DeepSearchDataSourceProcessedEvent();
				returned.setInputData(request);
				returned.setOutputData(new DeepSearchDataSourceResponse());
				returned.getOutputData().setSearchResultsEmpty(true);
				return returned;
			}
			List<SearchResults> queryResults = new ArrayList<SearchResults>();
			for (SearchQuery query : searchQueries.getSearchQuery()) {
				List<SearchResult> results = executeSearch(query, request);
				if (results.isEmpty())
					continue;
				SearchResults sr = new SearchResults();
				sr.setResults(results);
				sr.setSearchQuery(query);
				queryResults.add(sr);
			}
			state.setQueryResults(queryResults);
		}
		if (state.getQueryResults() == null || state.getQueryResults().isEmpty()) {
			DeepSearchDataSourceProcessedEvent returned = new DeepSearchDataSourceProcessedEvent();
			returned.setInputData(request);
			returned.setOutputData(new DeepSearchDataSourceResponse());
			returned.getOutputData().setSearchResultsEmpty(true);
			return returned;
		}
		if (state.getQueryResultsIndex() < state.getQueryResults().size()) {
			SearchResult actualSearchResultToLoad = null;
			SearchResults actualResult = state.getQueryResults().get(state.getQueryResultsIndex());
			if (state.getQueryResultsReferenceIndex() < actualResult.getResults().size()) {
				actualSearchResultToLoad = actualResult.getResults().get(state.getQueryResultsReferenceIndex());
			} else {
				int nextIndex = state.getQueryResultsIndex() + 1;
				state.setQueryResultsIndex(nextIndex);
				state.setQueryResultsReferenceIndex(0);
				if (nextIndex < state.getQueryResults().size()) {
					actualResult = state.getQueryResults().get(state.getQueryResultsIndex());
					if (state.getQueryResultsReferenceIndex() < actualResult.getResults().size()) {
						actualSearchResultToLoad = actualResult.getResults().get(state.getQueryResultsReferenceIndex());
					}
				}
			}
			if (actualSearchResultToLoad != null) {
				int maxTokens = MAX_DOCUMENT_TOKENS_SIZE_CONTEXT_MOLTIPLICATOR * chatModel.getContextLength();
				List<Document> documents = this.loadDocumentFragments(actualSearchResultToLoad, request, maxTokens);
				String prompt = deepSearchConfig.getAnalisysPrompt();
				List<Document> batches = this.prepareBatches(documents, prompt, previusConsolidatedResult, chatModel);
				List<CustomContentExtractionType> analyzed = new ArrayList();
				for (Document document : batches) {
					CustomContentExtractionType returned = callLLMWithDocumentsStructuredReturn(chatModel, prompt,
							document, request.getQuery(), customContentExtractionType);
					analyzed.add(returned);
					if (actualSearchResultToLoad.getNestingLevel() < MAX_NESTING_LEVEL) {
						List<SearchResult> additionalResults = this.extractAdditionalReferencesToScan(returned, state);
						// Enqueue other references to visit after the actual visit
						if (additionalResults != null && !additionalResults.isEmpty()) {
							for (SearchResult r : additionalResults) {
								r.setNestingLevel(actualSearchResultToLoad.getNestingLevel() + 1);
							}
							SearchResults sr = new SearchResults();
							sr.setResults(additionalResults);
							state.getQueryResults().set(state.getQueryResultsIndex() + 1, sr);
						}
					}
				}
				RemoteReferenceAnalyzedDeepSearchEvent analyzedEvent = new RemoteReferenceAnalyzedDeepSearchEvent();
				analyzedEvent.setInputData(actualSearchResultToLoad);
				analyzedEvent.setOutputData(new AnalyzedSearchResult());
				analyzedEvent.getOutputData().setEmptyResult(analyzed.isEmpty());
				if (!analyzed.isEmpty()) {
					if (analyzed.size() == 1) {
						analyzedEvent.getOutputData().setAnalyzedResult(analyzed.get(0).getExtractedRelevantContent());
					} else {
						String analyzedResult = consolidateReferenceResults(analyzed, request, deepSearchConfig,
								chatModel);
						analyzedEvent.getOutputData().setAnalyzedResult(analyzedResult);
					}
					state.getCumulatedAnalisys().add(analyzedEvent.getOutputData());
				}

				return analyzedEvent;

			}
		}
		DeepSearchDataSourceProcessedEvent returned = consolidate(state.getCumulatedAnalisys(), request,
				deepSearchConfig, chatModel);
		return returned;
	}

	private DeepSearchDataSourceProcessedEvent consolidate(List<AnalyzedSearchResult> cumulatedAnalisys,
			DeepSearchRequest request, DeepSearchConfig deepSearchConfig, IGConfigurableChatModel chatModel) {
		// TODO Auto-generated method stub
		return null;
	}

	private String consolidateReferenceResults(List<CustomContentExtractionType> analyzed, DeepSearchRequest request,
			DeepSearchConfig deepSearchConfig, IGConfigurableChatModel chatModel) {
		// TODO Auto-generated method stub
		return null;
	}

	protected abstract List<SearchResult> extractAdditionalReferencesToScan(CustomContentExtractionType returned,
			RemoteSystemDeepSearchDataSourceStandardState state);

	protected List<Document> prepareBatches(List<Document> documents, String prompt, String previusConsolidatedResult,
			IGConfigurableChatModel chatModel) {
		// TODO Auto-generated method stub
		return null;
	}

	protected abstract List<Document> loadDocumentFragments(SearchResult actualSearchResultToLoad,
			DeepSearchRequest request, int maxTokens);

	protected abstract List<SearchResult> executeSearch(SearchQuery query, DeepSearchRequest request);

	protected ExtractedSearchQueries extractSearchQueries(DeepSearchRequest request,
			List<IDeepSearchResult> pastSystemsResponses, DeepSearchConfig deepSearchConfig,
			IGConfigurableChatModel chatModel, String consolidatedText) throws LLMConfigException {
		String prompt = createExtractSearchQueriesPrompt(request, pastSystemsResponses, deepSearchConfig, chatModel);

		return super.callLLMWithConsolidationStructuredReturn(chatModel, prompt, request.getQuery(),
				consolidatedText != null ? consolidatedText : "", ExtractedSearchQueries.class);
	}

	protected abstract String createExtractSearchQueriesPrompt(DeepSearchRequest request,
			List<IDeepSearchResult> pastSystemsResponses, DeepSearchConfig deepSearchConfig,
			IGConfigurableChatModel chatModel);
}
