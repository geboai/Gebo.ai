package ai.gebo.llms.deepsearch.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.tokenizer.JTokkitTokenCountEstimator;

import ai.gebo.architecture.ai.service.IGPromptConfigDao;
import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.architecture.documents.cache.model.AbstractChunkingSpecs;
import ai.gebo.architecture.documents.cache.model.ChinkingPolicy;
import ai.gebo.architecture.documents.cache.model.ChunkingParams;
import ai.gebo.architecture.documents.cache.model.IDocumentChunkWithRef;
import ai.gebo.architecture.documents.cache.model.TextChunkingSpecs;
import ai.gebo.architecture.documents.cache.service.IDocumentsChunkService;
import ai.gebo.architecture.multithreading.IGeboThreadManager;
import ai.gebo.architecture.search.model.BaseSearchResultsExtractionDataType;
import ai.gebo.architecture.search.model.SearchResult;
import ai.gebo.architecture.search.model.SearchResultAnalisysOutcome;
import ai.gebo.architecture.search.model.SearchResultReference;
import ai.gebo.architecture.search.model.SearchServiceException;
import ai.gebo.architecture.search.model.SearchWithResults;
import ai.gebo.llms.abstraction.layer.services.BaseLLMSInvokingAndProvidingService;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.IGEmbeddingModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.chat.abstraction.layer.config.GeboPromptsLibrary;
import ai.gebo.llms.chat.abstraction.layer.services.CommonChatPromptParamsUtil;
import ai.gebo.llms.chat.abstraction.layer.session.model.MinimalChatContext;
import ai.gebo.llms.deepsearch.config.DeepSearchDefaultConfig;
import ai.gebo.llms.deepsearch.datasources.model.DeepSearchDataSourceDocumentResult;
import ai.gebo.llms.deepsearch.datasources.model.DeepSearchDataSourceResponse;
import ai.gebo.llms.deepsearch.datasources.model.events.DeepSearchDataSourceDocumentResultEvent;
import ai.gebo.llms.deepsearch.datasources.model.events.DeepSearchDataSourceProcessedEvent;
import ai.gebo.llms.deepsearch.model.DeepSearchAnalyzedDocument;
import ai.gebo.llms.deepsearch.model.DeepSearchConfig;
import ai.gebo.llms.deepsearch.model.DeepSearchRequest;
import ai.gebo.llms.deepsearch.model.DeepSearchSourceType;
import ai.gebo.llms.deepsearch.model.DeepSearchState;
import ai.gebo.llms.deepsearch.model.IDeepSearchResult;
import ai.gebo.llms.deepsearch.model.SearchResultsStepInfo;
import ai.gebo.llms.deepsearch.model.events.AbstractDeepSearchEvent;
import ai.gebo.llms.deepsearch.model.events.DeepSearchErrorEvent;
import ai.gebo.llms.deepsearch.model.events.DeepSearchOperationEndedEvent;
import ai.gebo.llms.deepsearch.model.ratings.SharedRatingsStructure;
import ai.gebo.llms.deepsearch.service.impl.Common;
import ai.gebo.llms.deepsearch.service.impl.SearchEndingDetectionLogic;
import ai.gebo.model.GUserMessage;
import ai.gebo.system.ingestion.GeboIngestionException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.ParallelFlux;

public abstract class GAbstractReactiveDeepSearchDataSourceService<CustomContentExtractionType extends BaseSearchResultsExtractionDataType>
		extends BaseLLMSInvokingAndProvidingService implements
		IGReactiveDeepSearchDataSourceService<SearchResult, DeepSearchDataSourceDocumentResult, DeepSearchDataSourceDocumentResultEvent> {
	private static final Logger LOGGER = LoggerFactory.getLogger(GAbstractReactiveDeepSearchDataSourceService.class);
	protected final Class<CustomContentExtractionType> customContentExtractionType;
	protected final IDocumentsChunkService chunkingService;
	protected final IGeboThreadManager threadManager;
	protected final SearchResultsRankingService rankingService;
	protected final DeepSearchDefaultConfig deepSearchDefaultConfig;
	protected final IGPromptConfigDao promptsDao;
	protected static final String DATA_SOURCE_DESCRIPTION = "dataSourceDescription";
	private static final int MAX_NESTING_LEVEL = 2;
	private static final JTokkitTokenCountEstimator tokenCountEstimator = new JTokkitTokenCountEstimator();
	private static final int MAX_DOCUMENT_TOKENS_SIZE_CONTEXT_MOLTIPLICATOR = 10;

	protected GAbstractReactiveDeepSearchDataSourceService(IGChatModelRuntimeConfigurationDao chatModelsConfigDao,
			IGEmbeddingModelRuntimeConfigurationDao embeddingModelsRuntimeDao, IDocumentsChunkService chunkingService,
			Class<CustomContentExtractionType> customContentExtractionType, IGeboThreadManager threadManager,
			SearchResultsRankingService rankingService, DeepSearchDefaultConfig deepSearchDefaultConfig,
			IGPromptConfigDao promptsDao) {
		super(chatModelsConfigDao, embeddingModelsRuntimeDao);
		this.customContentExtractionType = customContentExtractionType;
		this.chunkingService = chunkingService;
		this.threadManager = threadManager;
		this.rankingService = rankingService;
		this.deepSearchDefaultConfig = deepSearchDefaultConfig;
		this.promptsDao = promptsDao;
	}

	@AllArgsConstructor
	@Getter
	static class LLMCallStep<CustomContentExtractionType extends BaseSearchResultsExtractionDataType> {
		final CustomContentExtractionType returned;
		final IDocumentChunkWithRef chunkWithRef;
		final SearchResultAnalisysOutcome analisysDeepRefs;
		final AbstractDeepSearchEvent event;
	}

	private final static int NCONTEXT_WINDOW_LENGTH_THREASHOLD = 2;

	@Data
	public final static class KeywordsList {
		private List<String> keywords = new ArrayList<String>();
	};

	static class SearchResultsList extends ArrayList<SearchResult> {
	};

	@Override
	public Flux<AbstractDeepSearchEvent> streamSearch(DeepSearchRequest request, MinimalChatContext minimalChatContext,
			AtomicInteger totalSteps, AtomicInteger doneSteps, AtomicInteger satisfactoryDocuments,
			AtomicBoolean completed, int satisfactoryDocumentsThreashold, IGConfigurableChatModel chatModel,
			IGConfigurableChatModel serviceModel, DeepSearchConfig deepSearchConfig,
			List<IDeepSearchResult> pastSystemsResponses, String chunkingSessionId, DeepSearchState deepSearchState)
			throws LLMConfigException, IOException, GeboIngestionException, GeboContentHandlerSystemException,
			SearchServiceException {
		final Hashtable<String, Boolean> avoidMultipleAccess = new Hashtable<String, Boolean>();
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin streamSearch(....) handler=" + getHandlerId());
		}
		final SharedRatingsStructure sharedRatingStructure = new SharedRatingsStructure();
		final Map<String, Object> chatContextTemplateParams = CommonChatPromptParamsUtil
				.preparePromptParameters(minimalChatContext);
		if (completed.get()) {
			return DeepSearchOperationEndedEvent.justFlux(request);
		}
		Flux<SearchResultsList> searchFlux = Flux.defer(() -> {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Deferred generation for handler id: " + getHandlerId());
			}

			SearchResultsList actualResultsSnapshots = new SearchResultsList();
			boolean _completed = completed.get();
			if (_completed) {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Handling search operations ending execution step");
				}
				return Flux.just(actualResultsSnapshots);
			}
			List<SearchWithResults> queryResults = new ArrayList<SearchWithResults>();
			try {
				queryResults = executeSearches(request, minimalChatContext, pastSystemsResponses, deepSearchConfig,
						chatModel, serviceModel, "");
			} catch (Throwable e) {
				LOGGER.error("Exception executing searches", e);
				throw new RuntimeException("Exception executing searches", e);

			}
			if (completed.get()) {
				return Flux.just(actualResultsSnapshots);
			}
			if (queryResults.isEmpty()) {
				DeepSearchDataSourceProcessedEvent returned = new DeepSearchDataSourceProcessedEvent();
				returned.setInputData(request);
				returned.setOutputData(new DeepSearchDataSourceResponse());
				returned.getOutputData().setSearchResultsEmpty(true);
				returned.getOutputData().setHandlerId(getHandlerId());
				returned.getOutputData().setDataSourceDescription(getDescription(chatModel, deepSearchConfig, request));
				returned.getOutputData().setDeepsearchCode(request.getCode());
				return Flux.just(actualResultsSnapshots);
			}

			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Queries results=>" + queryResults);
			}
			queryResults = cleanAndRemoveDuplicated(queryResults);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Cleaned Queries results=>" + queryResults);
			}

			for (SearchWithResults searchWithResults : queryResults) {
				actualResultsSnapshots.addAll(searchWithResults.getResults());
			}

			return Flux.just(actualResultsSnapshots);
		});

		final String analisysPrompt = promptsDao
				.findByPromptUse(GeboPromptsLibrary.DEEP_SEARCH_DATA_SOURCES_FILE_ANALISYS_PROMPT).getPrompt();
		final int promptTokens = tokenCountEstimator.estimate(analisysPrompt);
		final int queryTokens = tokenCountEstimator.estimate(request.getQuery());
		final double tokensTotalExactBudget = chatModel.getContextLength() - (promptTokens + queryTokens);
		final int tokensBudget = (int) Math.round(tokensTotalExactBudget * 0.7);
		final List<AbstractChunkingSpecs> specs = List.of(TextChunkingSpecs.maximizedLength(tokensBudget));

		KeywordsList chunkingKeywordsMatching = callLLMStructuredReturn(serviceModel,
				promptsDao.findByPromptUse(GeboPromptsLibrary.DEEP_SEARCH_KEYWORD_GENERATION_PROMPT).getPrompt(),
				request.getQuery(), chatContextTemplateParams, KeywordsList.class);

		final ChunkingParams params = new ChunkingParams(ChinkingPolicy.MATCHING_CHUNKS_AFTER_THREASHOLD,
				(serviceModel.getContextLength() / 2) * NCONTEXT_WINDOW_LENGTH_THREASHOLD, 1,
				chunkingKeywordsMatching.getKeywords(), specs, false, serviceModel.getContextLength() * 50);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("chunkingParams=" + params);
		}
		final BiFunction<CustomContentExtractionType, CustomContentExtractionType, CustomContentExtractionType> aggregator = (
				CustomContentExtractionType actualData, CustomContentExtractionType currentConsolidation) -> {
			if (currentConsolidation != null) {
				SearchEndingDetectionLogic.manageTrigger(totalSteps, doneSteps, satisfactoryDocuments, completed,
						satisfactoryDocumentsThreashold, currentConsolidation);
				currentConsolidation.setExtractedRelevantContent(
						SearchEndingDetectionLogic.cleanFromTag(currentConsolidation.getExtractedRelevantContent()));
			}
			return this.customStructureConsolidation(actualData, currentConsolidation);
		};
		final Function<CustomContentExtractionType, String> consolidationExtractor = (
				data) -> data != null && data.getExtractedRelevantContent() != null ? data.getExtractedRelevantContent()
						: "";
		final Function<List<SearchResult>, ParallelFlux<IDocumentChunkWithRef>> chunksLoadFunction = (
				List<SearchResult> list) -> {
			List<SearchResult> cleanList = new ArrayList<SearchResult>();
			boolean _completed = completed.get();
			if (_completed) {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Handling search operations ending execution step");
				}
			}
			if (list != null && !list.isEmpty() && !_completed) {
				cleanList = cleanAndRemoveDuplicatedResults(list, avoidMultipleAccess);
				if (LOGGER.isDebugEnabled()) {
					List<String> contentsCodes = cleanList.stream().map(x -> x.getCode()).toList();
					LOGGER.info("List of unique contents:" + contentsCodes);
				}
			}
			List<SearchResult> nextList = new ArrayList<SearchResult>();
			try {
				rankingService.rateReferences(serviceModel, minimalChatContext, deepSearchConfig, list, request,
						sharedRatingStructure);
				int delta = deepSearchDefaultConfig.getPerDataSourceMaxVisited() - doneSteps.intValue();
				int nExtract = delta;
				Math.min(delta, deepSearchDefaultConfig.getMaxExternalSourcesSearchResults());
				for (int i = 0; i < delta; i++) {
					SearchResult toVisit = sharedRatingStructure.popHigherRanked();
					if (toVisit != null) {
						nextList.add(toVisit);
					} else {
						break;
					}
				}
			} catch (Throwable e) {
				nextList = cleanList;
			}
			int nTotal = totalSteps.addAndGet(nextList.size());
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Total steps incremented to:" + nTotal);
			}
			return chunkingService.streamChunks(nextList, params, chunkingSessionId,
					deepSearchConfig.getDocumentsParallelism());
		};
		// Flux<IDocumentChunkWithRef> loaded =
		// searchFlux.concatMap(chunksLoadFunction);
		Flux<IDocumentChunkWithRef> loadedChunks = searchFlux.concatMap(chunksLoadFunction);
		final Function<IDocumentChunkWithRef, LLMCallStep<CustomContentExtractionType>> llmElaborate = docWithRef -> {

			SearchResult actualSearchResultToLoad = (SearchResult) docWithRef.getDocumentRef();
			LLMInputDocument cInput = new LLMInputDocument(actualSearchResultToLoad.getResultReference().getName(),
					actualSearchResultToLoad.getResultReference().getUri(),
					actualSearchResultToLoad.getResultReference().getTitle(), docWithRef.getChunk().getChunkData());
			List<LLMInputDocument> inputs = List.of(cInput);
			if (completed.get()) {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Handling search operations ending execution step");
				}
				LLMCallStep<CustomContentExtractionType> callStep = new LLMCallStep<CustomContentExtractionType>(null,
						docWithRef, null, DeepSearchOperationEndedEvent.of(request));
				return callStep;
			}
			CustomContentExtractionType returned;
			try {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Thread=>" + Thread.currentThread().getName() + " calling llm for chunk:"
							+ docWithRef.getChunk().getId() + " with position:"
							+ docWithRef.getChunk().getChunkPosition() + " tokens:"
							+ docWithRef.getChunk().getTokensSize() + " content of:"
							+ actualSearchResultToLoad.getCode());
				}
				returned = super.callLLMConsolidateStructuredReturn(serviceModel, analisysPrompt, request.getQuery(),
						"", chatContextTemplateParams, this.customContentExtractionType, aggregator,
						consolidationExtractor, inputs, false);
				/*
				 * SearchEndingDetectionLogic.manageTrigger(totalSteps, doneSteps,
				 * satisfactoryDocuments, completed, satisfactoryDocumentsThreashold,
				 * returned.getExtractedRelevantContent());
				 * returned.setExtractedRelevantContent(
				 * SearchEndingDetectionLogic.cleanFromTag(returned.getExtractedRelevantContent(
				 * )));
				 */
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
				deepStepAnalisys = extractRelatedAnalisysReferences(data, returned, deepSearchConfig, serviceModel);

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
			_analyzedEvent.getOutputData().setAnalyzedDocument(createAnalyzedDocument(actualSearchResultToLoad,
					chatModel, serviceModel, deepSearchConfig, request));
			_analyzedEvent.getOutputData().setDeepsearchCode(request.getCode());
			_analyzedEvent.getOutputData().setAnalyzedSearchResult(actualSearchResultToLoad);
			_analyzedEvent.getOutputData()
					.setDataSourceDescription(getDescription(chatModel, deepSearchConfig, request));

			_analyzedEvent.getOutputData()
					.setDataSourceDescription(getDescription(chatModel, deepSearchConfig, request));
			_analyzedEvent.getOutputData().setAnalisysResult(returned.getExtractedRelevantContent());
			LLMCallStep<CustomContentExtractionType> out = new LLMCallStep<CustomContentExtractionType>(returned,
					docWithRef, deepStepAnalisys, _analyzedEvent);
			doneSteps.incrementAndGet();
			_analyzedEvent.getOutputData().setProcessPercentage(deepSearchState.calculateProcessedPercent());
			return out;
		};
		Flux<LLMCallStep<CustomContentExtractionType>> relevantContents = loadedChunks.map(llmElaborate)
				.filter(x -> x != null && x.getReturned() != null && x.getReturned().getContentIsRelevant() != null
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
				deepSearchConfig, furtherAnalisys, llmElaborate, listedEvents, avoidMultipleAccess);
		Flux<AbstractDeepSearchEvent> trail = consolidateDeepSearchDataSourceProcessedEvent(request,
				chatContextTemplateParams, chatModel, deepSearchConfig, listedEvents, deepSearchState);

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("End streamSearch(....) handler=" + getHandlerId());
		}

		return Flux.concat(outFlux, additionalAnalisys, trail).subscribeOn(threadManager.getBoundedElastic())
				.onErrorResume(Common.commonFallBack(request));
	}

	protected abstract List<SearchWithResults> executeSearches(DeepSearchRequest request,
			MinimalChatContext minimalChatContext, List<IDeepSearchResult> pastSystemsResponses,
			DeepSearchConfig deepSearchConfig, IGConfigurableChatModel chatModel, IGConfigurableChatModel serviceModel,
			String string) throws LLMConfigException, IOException, SearchServiceException;

	private Flux<AbstractDeepSearchEvent> additionalAnalisys(
			Function<List<SearchResult>, ParallelFlux<IDocumentChunkWithRef>> chunksLoadFunction,
			DeepSearchRequest request, IGConfigurableChatModel chatModel, DeepSearchConfig deepSearchConfig,
			Vector<SearchResultAnalisysOutcome> furtherAnalisys,
			Function<IDocumentChunkWithRef, LLMCallStep<CustomContentExtractionType>> llmElaborate,
			Vector<AbstractDeepSearchEvent> listedEvents, Map<String, Boolean> avoidMultipleAccess) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin additionalAnalisys(....)");
		}
		Flux<AbstractDeepSearchEvent> outValue = Flux.defer(() -> {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Begin deferred additionalAnalisys(...)");
			}
			List<SearchResult> searchResults = new ArrayList<SearchResult>();
			for (SearchResultAnalisysOutcome o : furtherAnalisys) {
				if (o.getRelatedResults() != null) {
					searchResults.addAll(o.getRelatedResults());
				}
			}

			ParallelFlux<IDocumentChunkWithRef> chunks = chunksLoadFunction.apply(searchResults);

			ParallelFlux<AbstractDeepSearchEvent> events = chunks.map(llmElaborate).filter(
					x -> x != null && x.returned != null && Boolean.TRUE.equals(x.returned.getContentIsRelevant()))
					.map(step -> {
						AbstractDeepSearchEvent ev = step.getEvent();
						if (ev instanceof DeepSearchDataSourceDocumentResultEvent docResult) {
							listedEvents.add(docResult); // Vector: thread-safe
						}
						return ev;
					});
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("End deferred additionalAnalisys(...)");
			}
			return events.sequential();
		});

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("End additionalAnalisys(....)");
		}
		return outValue;
	}

	private Flux<AbstractDeepSearchEvent> consolidateDeepSearchDataSourceProcessedEvent(DeepSearchRequest request,
			Map<String, Object> currentChatContextParams, IGConfigurableChatModel chatModel,
			DeepSearchConfig deepSearchConfig, Vector<AbstractDeepSearchEvent> listedEvents,
			DeepSearchState deepSearchState) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin consolidateDeepSearchDataSourceProcessedEvent(....)");
		}
		Mono<AbstractDeepSearchEvent> trailProducer = Mono.fromCallable(() -> {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Begin trailProducer(....)");
			}
			AbstractDeepSearchEvent outValue = null;
			DeepSearchDataSourceProcessedEvent processed = new DeepSearchDataSourceProcessedEvent();
			processed.setInputData(request);
			processed.setOutputData(new DeepSearchDataSourceResponse());
			processed.getOutputData().setSearchResultsEmpty(listedEvents.isEmpty());
			processed.getOutputData().setDataSourceDescription(getDescription(chatModel, deepSearchConfig, request));
			processed.getOutputData().setDeepsearchCode(request.getCode());
			List<LLMInputDocument> input = new ArrayList<BaseLLMSInvokingAndProvidingService.LLMInputDocument>();
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
						if (docName == null && evds.getOutputData().getAnalyzedSearchResult()
								.getNavigationReference().path != null) {
							docName = evds.getOutputData().getAnalyzedSearchResult().getNavigationReference().path.name;

						}
						if (title == null && evds.getOutputData().getAnalyzedSearchResult()
								.getNavigationReference().path != null) {
							title = evds.getOutputData().getAnalyzedSearchResult().getNavigationReference().path.name;
						}
					}
					String text = evds.getOutputData().getAnalisysResult();
					LLMInputDocument data = new LLMInputDocument(
							processed.getOutputData().getDataSourceDescription() + " " + docName, url, title, text);
					input.add(data);
				}
			}

			try {
				processed.getOutputData()
						.setResponse(callLLMConsolidateText(chatModel, promptsDao
								.findByPromptUse(GeboPromptsLibrary.DEEP_SEARCH_CONSOLIDATION_PROMPT).getPrompt(),
								request.getQuery(), "", currentChatContextParams, input));
				processed.getOutputData().setProcessPercentage(deepSearchState.calculateProcessedPercent());
				outValue = processed;
			} catch (Throwable th) {
				LOGGER.error("Error in trail operation", th);
				DeepSearchErrorEvent deepSourceError = new DeepSearchErrorEvent();
				deepSourceError.setInputData(request);
				deepSourceError.setOutputData(GUserMessage.errorMessage("Error in the final report writing", th));
				outValue = deepSourceError;
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("End trailProducer(....)");
			}
			return outValue;
		});
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("End consolidateDeepSearchDataSourceProcessedEvent(....)");
		}
		return trailProducer.flux();
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
		return cleanAndRemoveDuplicatedResults(results, new HashMap<String, Boolean>());
	}

	protected List<SearchWithResults> cleanAndRemoveDuplicated(List<SearchWithResults> queryResults) {
		return this.cleanAndRemoveDuplicated(queryResults, new HashMap());
	}

	protected List<SearchResult> cleanAndRemoveDuplicatedResults(List<SearchResult> queryResults,
			Map<String, Boolean> nodups) {
		List<SearchResult> out = new ArrayList<SearchResult>();
		for (SearchResult searchResult : queryResults) {
			if (!nodups.containsKey(searchResult.getCode())) {
				try {
					SearchResult cloned = (SearchResult) searchResult.clone();
					if ((cloned.getResultReference() != null && cloned.getResultReference().getUri() != null)
							|| (cloned.getNavigationReference() != null)) {
						cloned.setChilds(cleanAndRemoveDuplicatedResults(searchResult.getChilds(), nodups));
						out.add(cloned);
					} else {
						LOGGER.warn("Removing result:" + cloned.getCode());
					}
				} catch (CloneNotSupportedException e) {
					LOGGER.error("Clone not supported!!", e);
				}
				nodups.put(searchResult.getCode(), true);
			}
		}
		return out;
	}

	protected List<SearchWithResults> cleanAndRemoveDuplicated(List<SearchWithResults> queryResults,
			Map<String, Boolean> nodups) {
		List<SearchWithResults> outValue = new ArrayList<SearchWithResults>();
		if (queryResults != null) {
			for (SearchWithResults searchWithResults : queryResults) {
				SearchWithResults copy = new SearchWithResults();
				copy.setSearchQuery(searchWithResults.getSearchQuery());
				copy.setResults(cleanAndRemoveDuplicatedResults(searchWithResults.getResults(), nodups));
				if (!copy.getResults().isEmpty()) {
					outValue.add(copy);
				}
			}
		}
		return outValue;
	}

	protected abstract CustomContentExtractionType customStructureConsolidation(CustomContentExtractionType actualData,
			CustomContentExtractionType currentConsolidation);

	protected abstract List<SearchResult> extractAdditionalReferencesToScan(CustomContentExtractionType returned);

	private DeepSearchAnalyzedDocument createAnalyzedDocument(SearchResult sr, IGConfigurableChatModel chatModel,
			IGConfigurableChatModel serviceModel, DeepSearchConfig deepSearchConfig, DeepSearchRequest request) {
		DeepSearchAnalyzedDocument doc = new DeepSearchAnalyzedDocument();
		doc.setDataSourceCode(getHandlerId());
		doc.setDataSourceDescription(getDescription(chatModel, deepSearchConfig, request));

		if (sr != null) {
			StringBuffer computedCode = new StringBuffer();
			computedCode.append(getHandlerId() + ":");
			if (sr.getNavigationReference() != null) {

				if (sr.getNavigationReference().root != null && sr.getNavigationReference().root.getCode() != null) {
					computedCode.append(sr.getNavigationReference().root.getCode());
					computedCode.append(":");
				}
				if (sr.getNavigationReference().path != null) {
					computedCode.append(sr.getNavigationReference().path.absolutePath);
				}
				if (sr.getNavigationReference().path != null && sr.getNavigationReference().path.name != null) {
					doc.setName(sr.getNavigationReference().path.name);
				}
			} else if (sr.getResultReference() != null && sr.getResultReference().getUri() != null) {
				computedCode.append(sr.getResultReference().getUri());
			}
			doc.setCode(computedCode.toString());
			SearchResultReference rr = sr.getResultReference();
			if (rr != null && rr.getName() != null) {
				doc.setName(rr.getName());

			}
			if (rr != null && rr.getUri() != null) {
				doc.setUrl(rr.getUri());
			}

		}
		doc.setSourceType(DeepSearchSourceType.EXTERNAL_SEARCH);
		return doc;
	}

}
