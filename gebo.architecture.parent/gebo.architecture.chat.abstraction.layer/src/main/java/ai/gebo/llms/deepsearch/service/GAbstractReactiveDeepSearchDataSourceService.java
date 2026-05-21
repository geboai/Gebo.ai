package ai.gebo.llms.deepsearch.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.ai.tokenizer.JTokkitTokenCountEstimator;

import ai.gebo.architecture.ai.service.IGPromptConfigDao;
import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.architecture.documents.cache.model.ChunkingParams;
import ai.gebo.architecture.documents.cache.model.ChunkingPolicy;
import ai.gebo.architecture.documents.cache.model.DocumentChunk;
import ai.gebo.architecture.documents.cache.model.IDocumentChunkWithRef;
import ai.gebo.architecture.documents.cache.model.TextChunkingSpecs;
import ai.gebo.architecture.documents.cache.service.IDocumentsChunkService;
import ai.gebo.architecture.multithreading.IGeboThreadManager;
import ai.gebo.architecture.search.model.BaseSearchResultsExtractionDataType;
import ai.gebo.architecture.search.model.SearchResult;
import ai.gebo.architecture.search.model.SearchResultAnalisysOutcome;
import ai.gebo.architecture.search.model.SearchServiceException;
import ai.gebo.architecture.search.model.SearchWithResults;
import ai.gebo.llms.abstraction.layer.services.BaseLLMSInvokingAndProvidingService;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.IGEmbeddingModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.services.GeboChatSessionLifecycleException;
import ai.gebo.llms.chat.abstraction.layer.session.model.MinimalChatContext;
import ai.gebo.llms.chat.pipelines.model.ChatPipelineExecutionRuntimeData;
import ai.gebo.llms.chat.pipelines.service.ISinkUIEmitter;
import ai.gebo.llms.deepsearch.config.DeepSearchDefaultConfig;
import ai.gebo.llms.deepsearch.datasources.model.AbstractPureSearchDocumentResultEntry;
import ai.gebo.llms.deepsearch.datasources.model.PureSearchDocumentResultError;
import ai.gebo.llms.deepsearch.datasources.model.PureSearchExternalDataSourceResultEntry;
import ai.gebo.llms.deepsearch.model.DeepSearchConfig;
import ai.gebo.llms.deepsearch.model.DeepSearchRequest;

import ai.gebo.llms.deepsearch.model.events.AbstractDeepSearchEvent;
import ai.gebo.model.GUserMessage;
import ai.gebo.model.GUserMessage.MsgServerity;
import ai.gebo.security.services.ReactiveIdentityUtil;
import ai.gebo.system.ingestion.GeboIngestionException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import reactor.core.publisher.Flux;
import reactor.core.publisher.ParallelFlux;
import reactor.core.scheduler.Schedulers;

public abstract class GAbstractReactiveDeepSearchDataSourceService<CustomContentExtractionType extends BaseSearchResultsExtractionDataType>
		extends BaseLLMSInvokingAndProvidingService implements IGReactiveDeepSearchDataSourceService {
	private static final Logger LOGGER = LoggerFactory.getLogger(GAbstractReactiveDeepSearchDataSourceService.class);
	protected final Class<CustomContentExtractionType> customContentExtractionType;
	protected final IDocumentsChunkService chunkingService;
	protected final IGeboThreadManager threadManager;
	protected final DeepSearchDefaultConfig deepSearchDefaultConfig;
	protected final IGPromptConfigDao promptsDao;
	protected static final String DATA_SOURCE_DESCRIPTION = "dataSourceDescription";
	private static final int MAX_NESTING_LEVEL = 2;
	private static final JTokkitTokenCountEstimator tokenCountEstimator = new JTokkitTokenCountEstimator();
	private static final int MAX_DOCUMENT_TOKENS_SIZE_CONTEXT_MOLTIPLICATOR = 10;

	protected GAbstractReactiveDeepSearchDataSourceService(IGChatModelRuntimeConfigurationDao chatModelsConfigDao,
			IGEmbeddingModelRuntimeConfigurationDao embeddingModelsRuntimeDao, IDocumentsChunkService chunkingService,
			Class<CustomContentExtractionType> customContentExtractionType, IGeboThreadManager threadManager,
			DeepSearchDefaultConfig deepSearchDefaultConfig, IGPromptConfigDao promptsDao) {
		super(chatModelsConfigDao, embeddingModelsRuntimeDao);
		this.customContentExtractionType = customContentExtractionType;
		this.chunkingService = chunkingService;
		this.threadManager = threadManager;
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

	static class InTopicChunksNotebook {
		int offTopic = 0;
		int inTopic = 0;
		int contiguousOffTopic = 0;
	}

	protected abstract List<SearchWithResults> executeSearches(DeepSearchRequest request,
			MinimalChatContext minimalChatContext, DeepSearchConfig deepSearchConfig, IGConfigurableChatModel chatModel,
			IGConfigurableChatModel serviceModel, String string, int topK)
			throws LLMConfigException, IOException, SearchServiceException;

	protected List<SearchResult> flattenSearchResults(List<SearchResult> results) {
		List<SearchResult> flattened = new ArrayList<SearchResult>();
		for (SearchResult entry : results) {
			boolean isNotAFolder = entry.getNavigationReference() == null
					|| (entry.getNavigationReference().path != null && !entry.getNavigationReference().path.folder);
			if (isNotAFolder) {
				flattened.add(entry);
			}
			flattened.addAll(flattenSearchResults(entry.getChilds()));
		}
		return cleanAndRemoveDuplicatedResults(flattened, new HashMap<String, Boolean>());
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

	@Override
	public Flux<AbstractPureSearchDocumentResultEntry> streamPureSearch(MinimalChatContext minimalChatContext,
			ISinkUIEmitter emitter, IGConfigurableChatModel chatModel, IGConfigurableChatModel serviceModel, int topK,
			int sampleTextTokensSize, String chunkingSessionId) throws LLMConfigException, IOException,
			GeboIngestionException, GeboContentHandlerSystemException, SearchServiceException {
		DeepSearchRequest dsr = new DeepSearchRequest();
		dsr.setChatRequestCode(minimalChatContext.getCurrentRequest().getId());
		dsr.setUserChatContextCode(minimalChatContext.getCurrentRequest().getUserChatContextCode());
		dsr.setQuery(GeboChatRequest.actualQuery(minimalChatContext.getCurrentRequest()));
		final ReactiveIdentityUtil runAs = ReactiveIdentityUtil.create();
		Flux<List<SearchWithResults>> searchBlockFlux = Flux.defer(() -> {
			return runAs.doRunAsWithReturn(() -> {
				try {
					return Flux.just(executeSearches(dsr, minimalChatContext, deepSearchDefaultConfig, chatModel,
							serviceModel, chunkingSessionId, topK));
				} catch (Throwable e) {
					throw new RuntimeException(e);

				}
			});
		});

		ParallelFlux<List<SearchWithResults>> searchResults = ParallelFlux.from(searchBlockFlux);

		ParallelFlux<IDocumentChunkWithRef> chunksFlux = searchResults.concatMap(results -> {

			return runAs.doRunAsWithReturn(() -> {
				Map<String, Boolean> joinedKeywords = new HashMap<>();
				List<SearchResult> found = new ArrayList<>();
				for (SearchWithResults item : results) {
					found.addAll(item.getResults());
					if (item.getSearchQuery() != null && item.getSearchQuery().getRelevantKeywords() != null) {
						for (String kw : item.getSearchQuery().getRelevantKeywords()) {
							joinedKeywords.put(kw.toLowerCase(), true);
						}

					}
					if (item.getNativeQueryObject() != null) {
						List<String> kws = item.getNativeQueryObject().relevantKeywords();
						for (String kw : kws) {
							joinedKeywords.put(kw.toLowerCase(), true);
						}
					}

				}
				List<String> keywords = new ArrayList<>(joinedKeywords.keySet());
				ChunkingParams chunkingParams = new ChunkingParams();
				chunkingParams.setChunkingPolicy(ChunkingPolicy.ONLY_MATCHING_CHUNKS);
				chunkingParams.setEnrichWithMetaData(false);
				chunkingParams.setTokensPerChunkSet(sampleTextTokensSize);
				chunkingParams.setTokensThreashold(sampleTextTokensSize);
				chunkingParams.setKeywordHits(keywords.size() > 3 ? 2 : 1);
				chunkingParams.setMatchingKeywords(keywords);
				TextChunkingSpecs textChunkingSpecs = TextChunkingSpecs.of(sampleTextTokensSize);
				chunkingParams.getChunkingSpecs().add(textChunkingSpecs);
				chunkingParams.setSamplingMode(true);
				chunkingParams.setSampledTokens(sampleTextTokensSize);
				return this.chunkingService.streamChunks(found, chunkingParams, chunkingSessionId,
						deepSearchDefaultConfig.getDocumentsParallelism());
			});
		});

		ParallelFlux<AbstractPureSearchDocumentResultEntry> parallelResult = chunksFlux.map(resultEntry -> {
			if (!resultEntry.isErrorState()) {
				SearchResult entry = (SearchResult) resultEntry.getDocumentRef();
				PureSearchExternalDataSourceResultEntry value = new PureSearchExternalDataSourceResultEntry(entry,
						resultEntry.getChunk().getChunkData());
				return value;
			}
			GUserMessage message = resultEntry.getErrorMessage();
			message.setSeverity(MsgServerity.warn);
			return new PureSearchDocumentResultError(null, null, message);
		});
		return parallelResult.sequential();
	}

	@Override
	public Flux<DocumentWithSearchResult> streamSearchResults(ChatPipelineExecutionRuntimeData runtimeData,
			ISinkUIEmitter sinkUIEmitter, IGConfigurableChatModel chatModel, IGConfigurableChatModel serviceModel,
			String chunkingSessionId, int topK) throws LLMConfigException, IOException, GeboIngestionException,
			GeboContentHandlerSystemException, SearchServiceException, GeboChatSessionLifecycleException {
		GeboChatRequest request = runtimeData.getRequestResources().getCurrentRequest();
		DeepSearchRequest dsr = new DeepSearchRequest();
		dsr.setChatRequestCode(request.getId());
		dsr.setUserChatContextCode(request.getUserChatContextCode());
		dsr.setQuery(GeboChatRequest.actualQuery(request));
		MinimalChatContext minimalChatContext = runtimeData.getMinimalChatContext();
		final ReactiveIdentityUtil runAs = ReactiveIdentityUtil.create();
		Flux<List<SearchWithResults>> searchBlockFlux = Flux.defer(() -> {
			return runAs.doRunAsWithReturn(() -> {
				try {
					return Flux.just(executeSearches(dsr, minimalChatContext, deepSearchDefaultConfig, chatModel,
							serviceModel, chunkingSessionId, topK));
				} catch (Throwable e) {
					throw new RuntimeException(e);

				}
			});
		});

		ParallelFlux<List<SearchWithResults>> searchResults = ParallelFlux.from(searchBlockFlux);

		ParallelFlux<IDocumentChunkWithRef> chunksFlux = searchResults.concatMap(results -> {

			return runAs.doRunAsWithReturn(() -> {
				Map<String, Boolean> joinedKeywords = new HashMap<>();
				List<SearchResult> found = new ArrayList<>();
				for (SearchWithResults item : results) {
					found.addAll(item.getResults());
					if (item.getSearchQuery() != null && item.getSearchQuery().getRelevantKeywords() != null) {
						for (String kw : item.getSearchQuery().getRelevantKeywords()) {
							joinedKeywords.put(kw.toLowerCase(), true);
						}

					}
					if (item.getNativeQueryObject() != null) {
						List<String> kws = item.getNativeQueryObject().relevantKeywords();
						for (String kw : kws) {
							joinedKeywords.put(kw.toLowerCase(), true);
						}
					}

				}
				List<String> keywords = new ArrayList<>(joinedKeywords.keySet());
				ChunkingParams chunkingParams = new ChunkingParams();
				chunkingParams.setChunkingPolicy(ChunkingPolicy.SPLIT_CHUNKS);
				chunkingParams.setEnrichWithMetaData(false);
				chunkingParams.setMatchingKeywords(keywords);
				TextChunkingSpecs textChunkingSpecs = TextChunkingSpecs.of(4096);
				chunkingParams.getChunkingSpecs().add(textChunkingSpecs);

				return this.chunkingService.streamChunks(found, chunkingParams, chunkingSessionId,
						deepSearchDefaultConfig.getDocumentsParallelism());
			});
		}).runOn(Schedulers.parallel());

		ParallelFlux<DocumentWithSearchResult> parallelResult = chunksFlux.map(resultEntry -> {
			if (!resultEntry.isErrorState()) {
				SearchResult entry = (SearchResult) resultEntry.getDocumentRef();
				DocumentChunk chunk = resultEntry.getChunk();
				Document document = new Document(chunk.getId(), chunk.getChunkData(), chunk.getMetaData());
				DocumentWithSearchResult value = new DocumentWithSearchResult(entry, document);
				return value;
			} else
				return null;
		});
		return parallelResult.sequential().filter(x -> x != null);
	}

}
