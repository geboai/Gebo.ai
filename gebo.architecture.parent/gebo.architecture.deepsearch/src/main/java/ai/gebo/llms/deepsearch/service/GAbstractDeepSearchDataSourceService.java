package ai.gebo.llms.deepsearch.service;

import static org.assertj.core.api.Assertions.in;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.tokenizer.JTokkitTokenCountEstimator;

import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.architecture.documents.cache.model.AbstractChunkingSpecs;
import ai.gebo.architecture.documents.cache.model.DocumentChunk;
import ai.gebo.architecture.documents.cache.model.IDocumentChunkWithRef;
import ai.gebo.architecture.documents.cache.model.TextChunkingSpecs;
import ai.gebo.architecture.documents.cache.service.IDocumentsChunkService;
import ai.gebo.architecture.multithreading.IGeboThreadManager;
import ai.gebo.architecture.search.model.BaseSearchResultsExtractionDataType;
import ai.gebo.architecture.search.model.SearchQuery;
import ai.gebo.architecture.search.model.SearchResult;
import ai.gebo.architecture.search.model.SearchResultAnalisysOutcome;
import ai.gebo.architecture.search.model.SearchResultReference;
import ai.gebo.architecture.search.model.SearchServiceException;
import ai.gebo.architecture.search.model.SearchWithResults;
import ai.gebo.llms.abstraction.layer.services.BaseLlmsInvokingService;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.IGEmbeddingModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.abstraction.layer.services.BaseLlmsInvokingService.ConsolidationInput;
import ai.gebo.llms.deepsearch.datasources.model.DeepSearchDataSourceDocumentResult;
import ai.gebo.llms.deepsearch.datasources.model.DeepSearchDataSourceExtractedSearchQueries;
import ai.gebo.llms.deepsearch.datasources.model.DeepSearchDataSourceResponse;
import ai.gebo.llms.deepsearch.datasources.model.DeepSearchDataSourceStandardState;
import ai.gebo.llms.deepsearch.datasources.model.events.DeepSearchDataSourceDocumentResultEvent;
import ai.gebo.llms.deepsearch.datasources.model.events.DeepSearchDataSourceProcessedEvent;
import ai.gebo.llms.deepsearch.model.DeepSearchConfig;
import ai.gebo.llms.deepsearch.model.DeepSearchRequest;
import ai.gebo.llms.deepsearch.model.DeepSearchState;
import ai.gebo.llms.deepsearch.model.IDeepSearchResult;
import ai.gebo.llms.deepsearch.model.SearchResultsStepInfo;
import ai.gebo.llms.deepsearch.model.events.AbstractDeepSearchEvent;
import ai.gebo.llms.deepsearch.model.events.DeepSearchErrorEvent;
import ai.gebo.model.GUserMessage;
import ai.gebo.system.ingestion.GeboIngestionException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import reactor.core.publisher.Flux;

public abstract class GAbstractDeepSearchDataSourceService<CustomContentExtractionType extends BaseSearchResultsExtractionDataType>
		extends BaseLlmsInvokingService implements
		IGDeepSearchDataSourceService<DeepSearchDataSourceStandardState, SearchResult, DeepSearchDataSourceDocumentResult, DeepSearchDataSourceDocumentResultEvent> {
	private static final Logger LOGGER = LoggerFactory.getLogger(GAbstractDeepSearchDataSourceService.class);
	protected final Class<CustomContentExtractionType> customContentExtractionType;
	protected final IDocumentsChunkService chunkingService;
	protected final IGeboThreadManager threadManager;
	protected static final String DATA_SOURCE_DESCRIPTION = "dataSourceDescription";
	private static final int MAX_NESTING_LEVEL = 2;
	private static final JTokkitTokenCountEstimator tokenCountEstimator = new JTokkitTokenCountEstimator();
	private static final int MAX_DOCUMENT_TOKENS_SIZE_CONTEXT_MOLTIPLICATOR = 10;

	protected GAbstractDeepSearchDataSourceService(IGChatModelRuntimeConfigurationDao chatModelsConfigDao,
			IGEmbeddingModelRuntimeConfigurationDao embeddingModelsRuntimeDao, IDocumentsChunkService chunkingService,
			Class<CustomContentExtractionType> customContentExtractionType, IGeboThreadManager threadManager) {
		super(chatModelsConfigDao, embeddingModelsRuntimeDao);
		this.customContentExtractionType = customContentExtractionType;
		this.chunkingService = chunkingService;
		this.threadManager = threadManager;
	}

	@Override
	public DeepSearchDataSourceStandardState createInitialState(IGConfigurableChatModel chatModel,
			DeepSearchConfig deepSearchConfig, DeepSearchRequest request) {
		DeepSearchDataSourceStandardState state = new DeepSearchDataSourceStandardState();
		return state;
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

	@AllArgsConstructor
	@Getter
	static class LLMCallStep<CustomContentExtractionType extends BaseSearchResultsExtractionDataType> {
		final CustomContentExtractionType returned;
		final IDocumentChunkWithRef chunkWithRef;
		final SearchResultAnalisysOutcome analisysDeepRefs;
		final AbstractDeepSearchEvent event;
	}

	@Override
	public Flux<AbstractDeepSearchEvent> streamSearch(IGConfigurableChatModel chatModel,
			DeepSearchConfig deepSearchConfig, DeepSearchRequest request, List<IDeepSearchResult> pastSystemsResponses,
			DeepSearchDataSourceStandardState state, DeepSearchState deepSearchSharedState) throws LLMConfigException,
			IOException, GeboIngestionException, GeboContentHandlerSystemException, SearchServiceException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin streamSearch(....) handler=" + getHandlerId());
		}
		String previusConsolidatedResult = deepSearchSharedState.getConsolidatedResult();

		final List<SearchResult> actualResultsSnapshots = new ArrayList<SearchResult>();
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
				return Flux.just(returned);
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
			for (SearchWithResults searchWithResults : queryResults) {
				actualResultsSnapshots.addAll(searchWithResults.getResults());
			}
			int totalSteps = state.totalStepsCount();
			int actualStep = state.actualStepsCount();
			deepSearchSharedState.getDataSourcesStatusTotalSteps().put(getHandlerId(), totalSteps);
			deepSearchSharedState.getDataSourcesStatusDoneSteps().put(getHandlerId(), actualStep);

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
			return Flux.just(returned);
		}
		final String analisysPrompt = deepSearchConfig.getAnalisysPrompt();
		final int promptTokens = tokenCountEstimator.estimate(analisysPrompt);
		final int queryTokens = tokenCountEstimator.estimate(request.getQuery());
		final double tokensTotalExactBudget = chatModel.getContextLength() - (promptTokens + queryTokens);
		final int tokensBudget = (int) Math.round(tokensTotalExactBudget * 0.7);
		List<AbstractChunkingSpecs> specs = List.of(TextChunkingSpecs.of(tokensBudget));
		final BiFunction<CustomContentExtractionType, CustomContentExtractionType, CustomContentExtractionType> aggregator = (
				CustomContentExtractionType actualData, CustomContentExtractionType currentConsolidation) -> {
			return this.customStructureConsolidation(actualData, currentConsolidation);
		};
		final Function<List<SearchResult>, Flux<IDocumentChunkWithRef>> chunksLoadFunction = (list) -> chunkingService
				.streamChunks(list, specs, false, tokensBudget * 10).subscribeOn(threadManager.getScheduler());
		Flux<IDocumentChunkWithRef> loadedChunks = chunksLoadFunction.apply(actualResultsSnapshots);
		final Function<IDocumentChunkWithRef, LLMCallStep<CustomContentExtractionType>> llmElaborate = docWithRef -> {

			SearchResult actualSearchResultToLoad = (SearchResult) docWithRef.getDocumentRef();
			ConsolidationInput cInput = new ConsolidationInput(actualSearchResultToLoad.getResultReference().getName(),
					actualSearchResultToLoad.getResultReference().getUri(),
					actualSearchResultToLoad.getResultReference().getTitle(), docWithRef.getChunk().getChunkData());
			List<ConsolidationInput> inputs = List.of(cInput);
			CustomContentExtractionType returned;
			try {
				returned = super.callLLMConsolidateStructuredReturn(chatModel, analisysPrompt, request.getQuery(), "",
						this.customContentExtractionType, aggregator, inputs);
			} catch (Throwable th) {
				LOGGER.error("Error in mapping calling llm", th);
				DeepSearchErrorEvent errorEvent = new DeepSearchErrorEvent();
				errorEvent.setInputData(request);
				errorEvent.setOutputData(GUserMessage.errorMessage("Error calling large language model", th));
				LLMCallStep<CustomContentExtractionType> out = new LLMCallStep<CustomContentExtractionType>(null,
						docWithRef, null, errorEvent);
				return out;
			}
			SearchResultAnalisysOutcome deepStepAnalisys = null;
			if (actualSearchResultToLoad.getNestingLevel() <= MAX_NESTING_LEVEL) {

				SearchResultsStepInfo data = new SearchResultsStepInfo(actualSearchResultToLoad, null);
				deepStepAnalisys = extractRelatedAnalisysReferences(data, returned, deepSearchConfig, chatModel);

				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Further deep step analisys to do:" + deepStepAnalisys);
				}

			}
			DeepSearchDataSourceDocumentResultEvent _analyzedEvent = new DeepSearchDataSourceDocumentResultEvent();
			_analyzedEvent.setInputData(actualSearchResultToLoad);
			_analyzedEvent.setOutputData(new DeepSearchDataSourceDocumentResult());
			_analyzedEvent.getOutputData()
					.setEmptyResult(returned.getContentIsRelevant() == null || !returned.getContentIsRelevant());
			_analyzedEvent.getOutputData().setHandlerId(getHandlerId());
			_analyzedEvent.getOutputData().setDeepsearchCode(request.getCode());
			_analyzedEvent.getOutputData().setAnalyzedSearchResult(actualSearchResultToLoad);
			_analyzedEvent.getOutputData()
					.setDataSourceDescription(getDescription(chatModel, deepSearchConfig, request));
			_analyzedEvent.getOutputData().setDocumentIndex(state.getQueryResultsReferenceIndex());
			_analyzedEvent.getOutputData()
					.setDataSourceDescription(getDescription(chatModel, deepSearchConfig, request));
			_analyzedEvent.getOutputData().setAnalyzedResult(returned.getExtractedRelevantContent());
			LLMCallStep<CustomContentExtractionType> out = new LLMCallStep<CustomContentExtractionType>(returned,
					docWithRef, deepStepAnalisys, _analyzedEvent);
			return out;
		};
		Flux<LLMCallStep<CustomContentExtractionType>> relevantContents = loadedChunks.map(llmElaborate)
				.filter(x -> x != null && x.getReturned().getContentIsRelevant() != null
						&& x.getReturned().getContentIsRelevant());
		final Vector<AbstractDeepSearchEvent> listedEvents = new Vector<AbstractDeepSearchEvent>();
		final Vector<SearchResultAnalisysOutcome> furtherAnalisys = new Vector<SearchResultAnalisysOutcome>();
		Flux<AbstractDeepSearchEvent> outFlux = relevantContents.map(x -> {
			AbstractDeepSearchEvent event = x.getEvent();
			listedEvents.add(event);
			if (x.getAnalisysDeepRefs() != null) {
				furtherAnalisys.add(x.getAnalisysDeepRefs());
			}
			return event;
		});
		Flux<AbstractDeepSearchEvent> additionalAnalisys = additionalAnalisys(chunksLoadFunction, request, chatModel,
				deepSearchConfig, furtherAnalisys, llmElaborate, listedEvents);
		Flux<AbstractDeepSearchEvent> trail = consolidateAsFlux(request, chatModel, deepSearchConfig, listedEvents);

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("End streamSearch(....) handler=" + getHandlerId());
		}
		return Flux.concat(outFlux, additionalAnalisys, trail);
	}

	private Flux<AbstractDeepSearchEvent> additionalAnalisys(
			Function<List<SearchResult>, Flux<IDocumentChunkWithRef>> chunksLoadFunction, DeepSearchRequest request,
			IGConfigurableChatModel chatModel, DeepSearchConfig deepSearchConfig,
			Vector<SearchResultAnalisysOutcome> furtherAnalisys,
			Function<IDocumentChunkWithRef, LLMCallStep<CustomContentExtractionType>> llmElaborate,
			Vector<AbstractDeepSearchEvent> listedEvents) {

		List<SearchResult> searchResults = new ArrayList<SearchResult>();
		for (SearchResultAnalisysOutcome o : furtherAnalisys) {
			if (o.getRelatedResults() != null) {
				searchResults.addAll(o.getRelatedResults());
			}
		}
		Flux<IDocumentChunkWithRef> flux = chunksLoadFunction.apply(searchResults);
		Flux<LLMCallStep<CustomContentExtractionType>> intermediate = flux.map(llmElaborate)
				.filter(x -> x.returned != null && x.returned.getContentIsRelevant() != null
						&& x.returned.getContentIsRelevant());
		Flux<AbstractDeepSearchEvent> outValue = intermediate.map(y -> {
			if (y.getEvent() != null && y.getEvent() instanceof DeepSearchDataSourceDocumentResultEvent docResult) {
				listedEvents.add(docResult);
			}
			return y.getEvent();
		});
		return outValue;
	}

	private Flux<AbstractDeepSearchEvent> consolidateAsFlux(DeepSearchRequest request,
			IGConfigurableChatModel chatModel, DeepSearchConfig deepSearchConfig,
			Vector<AbstractDeepSearchEvent> listedEvents) {
		DeepSearchDataSourceProcessedEvent processed = new DeepSearchDataSourceProcessedEvent();
		processed.setInputData(request);
		processed.setOutputData(new DeepSearchDataSourceResponse());
		processed.getOutputData().setSearchResultsEmpty(listedEvents.isEmpty());
		processed.getOutputData().setDataSourceDescription(getDescription(chatModel, deepSearchConfig, request));
		List<ConsolidationInput> input = new ArrayList<BaseLlmsInvokingService.ConsolidationInput>();
		for (AbstractDeepSearchEvent ev : listedEvents) {
			if (ev instanceof DeepSearchDataSourceDocumentResultEvent evds) {
				String docName = null;
				String url = null;
				String title = null;
				if (evds.getOutputData().getAnalyzedSearchResult() != null
						&& evds.getOutputData().getAnalyzedSearchResult().getResultReference() != null) {
					docName = evds.getOutputData().getAnalyzedSearchResult().getResultReference().getName();
					url = evds.getOutputData().getAnalyzedSearchResult().getResultReference().getUri();
					title = evds.getOutputData().getAnalyzedSearchResult().getResultReference().getTitle();
				}
				if (evds.getOutputData().getAnalyzedSearchResult() != null
						&& evds.getOutputData().getAnalyzedSearchResult().getNavigationReference() != null) {
					if (docName == null
							&& evds.getOutputData().getAnalyzedSearchResult().getNavigationReference().path != null) {
						docName = evds.getOutputData().getAnalyzedSearchResult().getNavigationReference().path.name;

					}
					if (title == null
							&& evds.getOutputData().getAnalyzedSearchResult().getNavigationReference().path != null) {
						title = evds.getOutputData().getAnalyzedSearchResult().getNavigationReference().path.name;
					}
				}
				String text = evds.getOutputData().getAnalyzedResult();
				ConsolidationInput data = new ConsolidationInput(
						processed.getOutputData().getDataSourceDescription() + " " + docName, url, title, text);
				input.add(data);
			}
		}
		processed.getOutputData().setResponse(callLLMConsolidateText(chatModel,
				deepSearchConfig.getConsolidationPrompt(), request.getQuery(), "", input));
		return Flux.just(processed);
	}

	protected abstract SearchResultAnalisysOutcome extractRelatedAnalisysReferences(
			SearchResultsStepInfo actualSearchResultRef, CustomContentExtractionType returned,
			DeepSearchConfig deepSearchConfig, IGConfigurableChatModel chatModel);

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
			String documentUrl = null;
			String title = null;
			if (x.getAnalyzedSearchResult().getResultReference() != null) {
				documentUrl = x.getAnalyzedSearchResult().getResultReference().getUri();
				title = x.getAnalyzedSearchResult().getResultReference().getTitle();
			}
			if (x.getAnalyzedSearchResult().getNavigationReference() != null
					&& x.getAnalyzedSearchResult().getNavigationReference().path != null) {
				title = x.getAnalyzedSearchResult().getNavigationReference().path.name;
			}
			if (title == null) {
				title = documentUrl;
			}
			String documentReference = "extracted from: " + x.getDataSourceDescription() + " document: " + title;

			ConsolidationInput input = new ConsolidationInput(documentReference, documentUrl, title,
					x.getAnalyzedResult());
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
