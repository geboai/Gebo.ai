package ai.gebo.llms.agent.standard.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.ai.document.Document;

import ai.gebo.architecture.agents.model.SearchAgentCommand;
import ai.gebo.architecture.agents.services.IAgentRoleDao;
import ai.gebo.architecture.agents.services.INotificationSink;
import ai.gebo.architecture.ai.service.IGDocumentContentRendererProvider;
import ai.gebo.architecture.ai.service.IGPromptConfigDao;
import ai.gebo.architecture.ai.service.IGToolCallbackSourceRepositoryPattern;
import ai.gebo.architecture.documents.cache.model.ChunkingParams;
import ai.gebo.architecture.documents.cache.model.ChunkingPolicy;
import ai.gebo.architecture.documents.cache.model.DocumentChunk;
import ai.gebo.architecture.documents.cache.model.IDocumentChunkWithRef;
import ai.gebo.architecture.documents.cache.model.TextChunkingSpecs;
import ai.gebo.architecture.documents.cache.service.IDocumentsChunkService;
import ai.gebo.architecture.patterns.IGRuntimeBinder;
import ai.gebo.architecture.search.model.SearchResult;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
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
	private static final int LLM_CHUNK_TOKENS = 1024;
	/** Fraction of the agent model context window allotted to retrieved documents. */
	private static final double DOC_BUDGET_FRACTION = 0.5;
	/** Cache-file batching granularity for the chunking service. */
	private static final long DEFAULT_TOKENS_PER_CHUNK_SET = 50000L;
	/** Minimum token length of a word to be kept as a matching keyword. */
	private static final int MIN_KEYWORD_LENGTH = 3;
	/** Fallback per-document chunk cap when none (or a non-positive one) is configured. */
	private static final int DEFAULT_MAX_CHUNKS_PER_DOCUMENT = 10;

	protected final IDocumentsChunkService chunkingService;
	/** Hard cap on the number of chunks kept per source document (bounds the ranker candidate pool). */
	protected final int maxChunksPerDocument;

	public GAbstractExternalDocumentsSearchAgentService(IGChatModelRuntimeConfigurationDao chatModelsDao,
			IGToolCallbackSourceRepositoryPattern toolsRepositoryPattern, IGPromptConfigDao promptsDao,
			IGSecurityService securityService, IAgentRoleDao agentRoleDao, IGRuntimeBinder runtimeBinder,
			IGDocumentContentRendererProvider rendererFactory, IDocumentsChunkService chunkingService,
			IGRankerService rankerService, int maxChunksPerDocument) {
		super(chatModelsDao, toolsRepositoryPattern, promptsDao, securityService, agentRoleDao, runtimeBinder,
				rendererFactory, rankerService);
		this.chunkingService = chunkingService;
		this.maxChunksPerDocument = maxChunksPerDocument > 0 ? maxChunksPerDocument : DEFAULT_MAX_CHUNKS_PER_DOCUMENT;
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
			return new ArrayList<>();
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin chunkToDocuments(...) agent id:" + getId() + " searchResults:" + results.size()
					+ " keywords:" + (keywords != null ? keywords.size() : 0));
		}
		final ChunkingParams params = buildSearchChunkingParams(agentModel, command, keywords);
		final String chunkingSession = chunkingService.createChunkingSession("agent-search:" + UUID.randomUUID());
		try {
			List<IDocumentChunkWithRef> chunks = chunkingService
					.streamChunks(results, params, chunkingSession, 4).sequential().collectList().block();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("chunkToDocuments(...) produced " + (chunks != null ? chunks.size() : 0)
						+ " raw chunk(s) in session:" + chunkingSession);
			}
			List<Document> documents = new ArrayList<>();
			if (chunks != null) {
				final Map<String, Integer> chunksPerDocument = new HashMap<>();
				int cappedOut = 0;
				for (IDocumentChunkWithRef chunkWithRef : chunks) {
					if (chunkWithRef == null || chunkWithRef.isErrorState() || chunkWithRef.getChunk() == null) {
						continue;
					}
					DocumentChunk chunk = chunkWithRef.getChunk();
					if (chunk.getChunkData() == null || chunk.getChunkData().isBlank()) {
						continue;
					}
					final String sourceCode = chunk.getOriginalDocumentCode() != null
							? chunk.getOriginalDocumentCode()
							: "";
					final int kept = chunksPerDocument.getOrDefault(sourceCode, 0);
					if (kept >= maxChunksPerDocument) {
						cappedOut++;
						continue;
					}
					chunksPerDocument.put(sourceCode, kept + 1);
					documents.add(new Document(chunk.getChunkData(), chunk.getMetaData()));
				}
				if (LOGGER.isDebugEnabled() && cappedOut > 0) {
					LOGGER.debug("chunkToDocuments(...) agent id:" + getId() + " dropped " + cappedOut
							+ " chunk(s) over the per-document cap of " + maxChunksPerDocument + " across "
							+ chunksPerDocument.size() + " source document(s)");
				}
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("End chunkToDocuments(...) agent id:" + getId() + " kept " + documents.size()
						+ " content document(s)");
			}
			return documents;
		} finally {
			chunkingService.disposeChunkingSession(chunkingSession);
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
		final int perDocumentBudget = (int) Math.max(LLM_CHUNK_TOKENS,
				(agentModel.getContextLength() * DOC_BUDGET_FRACTION) / topK);
		final int maxNumChunks = Math.min(maxChunksPerDocument,
				Math.max(1, (int) Math.ceil((perDocumentBudget * 2.0) / LLM_CHUNK_TOKENS)));
		final TextChunkingSpecs specs = TextChunkingSpecs.of(LLM_CHUNK_TOKENS,
				TextChunkingSpecs.MIN_CHUNKS_LENGTH_TO_EMBED, maxNumChunks);

		final List<String> matchingKeywords = keywords != null
				? keywords.stream().filter(k -> k != null && !k.isBlank()).map(String::trim).distinct().toList()
				: List.of();

		final ChunkingParams params = new ChunkingParams();
		params.setChunkingSpecs(List.of(specs));
		params.setEnrichWithMetaData(true);
		params.setTokensPerChunkSet(DEFAULT_TOKENS_PER_CHUNK_SET);
		if (!matchingKeywords.isEmpty()) {
			params.setChunkingPolicy(ChunkingPolicy.MATCHING_CHUNKS_AFTER_THREASHOLD);
			params.setTokensThreashold(perDocumentBudget);
			params.setMatchingKeywords(matchingKeywords);
			params.setKeywordHits(1);
		} else {
			params.setChunkingPolicy(ChunkingPolicy.SPLIT_CHUNKS);
		}
		return params;
	}

	/**
	 * Derives a set of matching keywords from the search command text, used as a
	 * relevance filter on the tail of large documents.
	 */
	protected List<String> keywordsFromCommand(SearchAgentCommand command) {
		if (command == null || command.getCommand() == null) {
			return List.of();
		}
		return Arrays.stream(command.getCommand().split("\\W+")).map(String::trim)
				.filter(word -> word.length() >= MIN_KEYWORD_LENGTH).distinct().toList();
	}
}
