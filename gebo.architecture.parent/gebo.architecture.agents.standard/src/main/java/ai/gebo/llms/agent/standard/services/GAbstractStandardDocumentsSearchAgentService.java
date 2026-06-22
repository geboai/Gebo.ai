package ai.gebo.llms.agent.standard.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.ai.document.Document;

import ai.gebo.architecture.agents.model.SearchAgentCommand;
import ai.gebo.architecture.agents.services.AgentException;
import ai.gebo.architecture.agents.services.GAbstractDocumentsSearchNetworkAgentService;
import ai.gebo.architecture.agents.services.IAgentRoleDao;
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
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.chat.abstraction.layer.services.IGRankerService;
import ai.gebo.security.services.IGSecurityService;

/**
 * Base class for the standard document-search agents. It centralizes the two
 * shared concerns of those agents: turning {@link SearchResult}s into Spring AI
 * {@link Document}s (via the chunking service) and optionally ranking the result
 * documents with the {@link IGRankerService}.
 */
public abstract class GAbstractStandardDocumentsSearchAgentService extends GAbstractDocumentsSearchNetworkAgentService {

	/** Target chunk size when feeding chunks to an LLM (larger than the embedding default of 512). */
	private static final int LLM_CHUNK_TOKENS = 1024;
	/** Fraction of the agent model context window allotted to retrieved documents. */
	private static final double DOC_BUDGET_FRACTION = 0.5;
	/** Cache-file batching granularity for the chunking service. */
	private static final long DEFAULT_TOKENS_PER_CHUNK_SET = 50000L;
	/** Minimum token length of a word to be kept as a matching keyword. */
	private static final int MIN_KEYWORD_LENGTH = 3;

	protected final IDocumentsChunkService chunkingService;
	protected final IGRankerService rankerService;

	public GAbstractStandardDocumentsSearchAgentService(IGChatModelRuntimeConfigurationDao chatModelsDao,
			IGToolCallbackSourceRepositoryPattern toolsRepositoryPattern, IGPromptConfigDao promptsDao,
			IGSecurityService securityService, IAgentRoleDao agentRoleDao, IGRuntimeBinder runtimeBinder,
			IGDocumentContentRendererProvider rendererFactory, IDocumentsChunkService chunkingService,
			IGRankerService rankerService) {
		super(chatModelsDao, toolsRepositoryPattern, promptsDao, securityService, agentRoleDao, runtimeBinder,
				rendererFactory);
		this.chunkingService = chunkingService;
		this.rankerService = rankerService;
	}

	/**
	 * Chunks the given search results into content-bearing Spring AI documents using
	 * LLM-fit chunk sizing and per-document bounds (see
	 * {@link #buildSearchChunkingParams(IGConfigurableChatModel, SearchAgentCommand, List)}).
	 * Error chunks and chunks with blank content are skipped.
	 */
	protected List<Document> chunkToDocuments(List<SearchResult> results, IGConfigurableChatModel agentModel,
			SearchAgentCommand command, List<String> keywords) {
		if (results == null || results.isEmpty()) {
			return new ArrayList<>();
		}
		final ChunkingParams params = buildSearchChunkingParams(agentModel, command, keywords);
		final String chunkingSession = chunkingService.createChunkingSession("agent-search:" + UUID.randomUUID());
		try {
			List<IDocumentChunkWithRef> chunks = chunkingService
					.streamChunks(results, params, chunkingSession, 4).sequential().collectList().block();
			List<Document> documents = new ArrayList<>();
			if (chunks != null) {
				for (IDocumentChunkWithRef chunkWithRef : chunks) {
					if (chunkWithRef == null || chunkWithRef.isErrorState() || chunkWithRef.getChunk() == null) {
						continue;
					}
					DocumentChunk chunk = chunkWithRef.getChunk();
					if (chunk.getChunkData() == null || chunk.getChunkData().isBlank()) {
						continue;
					}
					documents.add(new Document(chunk.getChunkData(), chunk.getMetaData()));
				}
			}
			return documents;
		} finally {
			chunkingService.disposeChunkingSession(chunkingSession);
		}
	}

	/**
	 * Ranks the documents with the ranker service when the command requests it and a
	 * ranker is configured; otherwise returns them unchanged.
	 */
	protected List<Document> maybeRank(List<Document> documents, SearchAgentCommand command) throws AgentException {
		if (documents == null || documents.isEmpty()) {
			return documents;
		}
		if (rankingRequested(command) && rankerService.isRankerConfigured()) {
			try {
				return rankerService.call(documents, command.getCommand(), command.getTopK());
			} catch (LLMConfigException e) {
				throw new AgentException("Error ranking search documents", e);
			}
		}
		return documents;
	}

	/**
	 * Number of documents to retrieve before ranking. When ranking is going to run,
	 * a wider candidate set is fetched (topK*2) so the ranker has material to reorder.
	 */
	protected int retrievalTopK(SearchAgentCommand command) {
		int topK = command != null ? command.getTopK() : 20;
		return (rankingRequested(command) && rankerService.isRankerConfigured()) ? topK * 2 : topK;
	}

	private boolean rankingRequested(SearchAgentCommand command) {
		return command != null && Boolean.TRUE.equals(command.getExecuteRanking());
	}

	/**
	 * Builds chunking parameters tuned for feeding chunks to an LLM rather than for
	 * embedding: chunks are sized at {@value #LLM_CHUNK_TOKENS} tokens and each
	 * document is bounded so a huge or off-topic file cannot dominate the context
	 * window. The per-document budget is derived from the agent model context window
	 * and the requested {@code topK}. When keywords are available the tail of a
	 * document (beyond the head budget) is kept only if it matches the request, while
	 * still preserving chunk granularity so the ranker can reorder individual chunks.
	 */
	protected ChunkingParams buildSearchChunkingParams(IGConfigurableChatModel agentModel, SearchAgentCommand command,
			List<String> keywords) {
		final int topK = command != null ? Math.max(1, command.getTopK()) : 1;
		final int perDocumentBudget = (int) Math.max(LLM_CHUNK_TOKENS,
				(agentModel.getContextLength() * DOC_BUDGET_FRACTION) / topK);
		final int maxNumChunks = Math.max(1, (int) Math.ceil((perDocumentBudget * 2.0) / LLM_CHUNK_TOKENS));
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
