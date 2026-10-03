package ai.gebo.llms.agent.standard.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.ai.document.Document;

import ai.gebo.architecture.agents.model.AgentsCollaborationSessionContext;
import ai.gebo.architecture.agents.model.SearchAgentCommand;
import ai.gebo.architecture.agents.services.IAgentRoleDao;
import ai.gebo.architecture.agents.services.INotificationSink;
import ai.gebo.architecture.ai.service.IGDocumentContentRendererProvider;
import ai.gebo.architecture.ai.service.IGPromptConfigDao;
import ai.gebo.architecture.ai.service.IGToolCallbackSourceRepositoryPattern;
import ai.gebo.architecture.documents.cache.model.ChunkingParams;
import ai.gebo.architecture.documents.cache.service.IDocumentsChunkService;
import ai.gebo.architecture.patterns.IGRuntimeBinder;
import ai.gebo.architecture.search.model.SearchResult;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GResponseDocumentRef;
import ai.gebo.llms.chat.abstraction.layer.services.IGRankerService;
import ai.gebo.security.services.IGSecurityService;

/**
 * Base class for the standard document-search agents that are backed by external
 * sources returning raw {@link SearchResult}s, which must be chunked into
 * content-bearing Spring AI {@link Document}s before being (optionally) ranked.
 * <p>
 * On top of the ranking concern inherited from
 * {@link GAbstractStandardDocumentsSearchAgentService}, this class owns the
 * chunking service together with the chunking policy/settings and the
 * per-document chunk cap. None of these are meaningful for searchers whose
 * backing service already returns ready document chunks (such as the internal
 * knowledge base searcher), which is why they live here rather than in the
 * shared standard base.
 */
public abstract class GAbstractExternalDocumentsSearchAgentService extends GAbstractStandardDocumentsSearchAgentService {

	/** Target chunk size when feeding chunks to an LLM (larger than the embedding default of 512). */
	private static final int LLM_CHUNK_TOKENS = SearchResultsChunker.LLM_CHUNK_TOKENS;
	/** Fraction of the agent model context window allotted to retrieved documents. */
	private static final double DOC_BUDGET_FRACTION = 0.5;
	/** Fallback per-document chunk cap when none (or a non-positive one) is configured. */
	private static final int DEFAULT_MAX_CHUNKS_PER_DOCUMENT = 10;

	protected final IDocumentsChunkService chunkingService;
	/** Hard cap on the number of chunks kept per source document (bounds the ranker candidate pool). */
	protected final int maxChunksPerDocument;
	/** The documents found loaded and chunked at the same time. */
	protected final int documentsParallelism;

	public GAbstractExternalDocumentsSearchAgentService(IGChatModelRuntimeConfigurationDao chatModelsDao,
			IGToolCallbackSourceRepositoryPattern toolsRepositoryPattern, IGPromptConfigDao promptsDao,
			IGSecurityService securityService, IAgentRoleDao agentRoleDao, IGRuntimeBinder runtimeBinder,
			IGDocumentContentRendererProvider rendererFactory, IDocumentsChunkService chunkingService,
			IGRankerService rankerService, int maxChunksPerDocument, int documentsParallelism) {
		super(chatModelsDao, toolsRepositoryPattern, promptsDao, securityService, agentRoleDao, runtimeBinder,
				rendererFactory, rankerService);
		this.chunkingService = chunkingService;
		this.maxChunksPerDocument = maxChunksPerDocument > 0 ? maxChunksPerDocument : DEFAULT_MAX_CHUNKS_PER_DOCUMENT;
		this.documentsParallelism = documentsParallelism > 0 ? documentsParallelism
				: SearchResultsChunker.DEFAULT_DOCUMENTS_PARALLELISM;
	}

	/**
	 * Chunks the given search results into content-bearing Spring AI documents using
	 * LLM-fit chunk sizing and per-document bounds (see
	 * {@link #buildSearchChunkingParams(IGConfigurableChatModel, SearchAgentCommand, List)}).
	 * Error chunks and chunks with blank content are skipped, and no more than
	 * {@link #maxChunksPerDocument} chunks are kept per source document so the ranker
	 * candidate pool stays bounded regardless of how large the source documents are.
	 * @param notificationSink TODO
	 */
	protected List<Document> chunkToDocuments(List<SearchResult> results, INotificationSink notificationSink,
			IGConfigurableChatModel agentModel, SearchAgentCommand command, List<String> keywords) {
		if (results == null || results.isEmpty()) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("chunkToDocuments(...) agent id:" + getId() + " has no search result to chunk");
			}
			return new ArrayList<>();
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin chunkToDocuments(...) agent id:" + getId() + " searchResults:" + results.size()
					+ " keywords:" + (keywords != null ? keywords.size() : 0));
		}
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("Matching keywords derived for chunking: " + keywords);
		}
		final ChunkingParams params = buildSearchChunkingParams(agentModel, command, keywords);
		// The chunking itself (session lifecycle, per-document cap, error chunks) is shared
		// with the search tools, see SearchResultsChunker.
		final List<Document> documents = SearchResultsChunker.chunkToDocuments(chunkingService, results, params,
				maxChunksPerDocument, getId(), documentsParallelism);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("End chunkToDocuments(...) agent id:" + getId() + " kept " + documents.size()
					+ " content document(s)");
		}
		return documents;
	}

	/**
	 * Publishes rich {@link GResponseDocumentRef}s for the given external search results
	 * into the shared session environment, keyed by {@link SearchResult#getCode()} - the
	 * same code the ingested Document is keyed on (CONTENT_CODE). Built here, upstream,
	 * where the typed {@link SearchResult} is available, so the ref carries
	 * {@code nestedSearchResult} and the user can "chat with" the external result; the
	 * report writer prefers these over rebuilding a (SearchResult-less) ref from the
	 * Document. Entries are merged across searchers, first write per code wins.
	 */
	protected void publishChatWithDocumentRefs(List<SearchResult> results, AgentsCollaborationSessionContext session) {
		if (results == null || results.isEmpty() || session == null) {
			return;
		}
		final Map<String, GResponseDocumentRef> byCode = chatWithDocumentRefs(session);
		for (SearchResult result : results) {
			if (result != null && result.getCode() != null) {
				byCode.computeIfAbsent(result.getCode(), key -> new GResponseDocumentRef(result));
			}
		}
	}

	@SuppressWarnings("unchecked")
	private Map<String, GResponseDocumentRef> chatWithDocumentRefs(AgentsCollaborationSessionContext session) {
		final Map<String, Object> environment = session.getEnvironment();
		synchronized (environment) {
			Object existing = environment.get(StandardAgentsNetworkEnvironmentEntries.CHAT_WITH_DOC_REFS_BY_CODE);
			if (existing instanceof Map<?, ?>) {
				return (Map<String, GResponseDocumentRef>) existing;
			}
			Map<String, GResponseDocumentRef> byCode = new ConcurrentHashMap<>();
			environment.put(StandardAgentsNetworkEnvironmentEntries.CHAT_WITH_DOC_REFS_BY_CODE, byCode);
			return byCode;
		}
	}

	/**
	 * Builds chunking parameters tuned for feeding chunks to an LLM rather than for
	 * embedding: chunks are sized at {@value #LLM_CHUNK_TOKENS} tokens and each
	 * document is bounded so a huge or off-topic file cannot dominate the context
	 * window. The per-document budget is derived from the agent model context window
	 * and the requested {@code topK}, and the chunk count is additionally clamped to
	 * {@link #maxChunksPerDocument}. When keywords are available the tail of a
	 * document (beyond the head budget) is kept only if it matches the request, while
	 * still preserving chunk granularity so the ranker can reorder individual chunks.
	 */
	protected ChunkingParams buildSearchChunkingParams(IGConfigurableChatModel agentModel, SearchAgentCommand command,
			List<String> keywords) {
		final int topK = command != null ? Math.max(1, command.getTopK()) : 1;
		final int perDocumentBudget = (int) Math.max(SearchResultsChunker.LLM_CHUNK_TOKENS,
				(agentModel.getContextLength() * DOC_BUDGET_FRACTION) / topK);
		final int maxNumChunks = Math.min(maxChunksPerDocument,
				Math.max(1, (int) Math.ceil((perDocumentBudget * 2.0) / SearchResultsChunker.LLM_CHUNK_TOKENS)));
		final ChunkingParams params = SearchResultsChunker.buildChunkingParams(perDocumentBudget, maxNumChunks,
				keywords);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("buildSearchChunkingParams(...) agent id:" + getId() + " topK:" + topK + " perDocumentBudget:"
					+ perDocumentBudget + " (tok) maxNumChunks:" + maxNumChunks + " policy:" + params.getChunkingPolicy()
					+ " matchingKeywords:"
					+ (params.getMatchingKeywords() != null ? params.getMatchingKeywords().size() : 0));
		}
		return params;
	}

	/**
	 * Derives a set of matching keywords from the search command text, used as a
	 * relevance filter on the tail of large documents.
	 */
	protected List<String> keywordsFromCommand(SearchAgentCommand command) {
		if (command == null || command.getCommand() == null) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("keywordsFromCommand(...) agent id:" + getId() + " has no command text to derive from");
			}
			return List.of();
		}
		List<String> keywords = SearchResultsChunker.keywordsFromText(command.getCommand());
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("keywordsFromCommand(...) agent id:" + getId() + " derived " + keywords.size()
					+ " matching keyword(s)");
		}
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("Derived keywords: " + keywords);
		}
		return keywords;
	}
}
