package ai.gebo.llms.agent.standard.services;

import java.util.ArrayList;
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
import ai.gebo.architecture.documents.cache.model.DocumentChunk;
import ai.gebo.architecture.documents.cache.model.IDocumentChunkWithRef;
import ai.gebo.architecture.documents.cache.service.IDocumentsChunkService;
import ai.gebo.architecture.patterns.IGRuntimeBinder;
import ai.gebo.architecture.search.model.SearchResult;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
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
	 * Chunks the given search results into content-bearing Spring AI documents.
	 * Error chunks and chunks with blank content are skipped.
	 */
	protected List<Document> chunkToDocuments(List<SearchResult> results) {
		if (results == null || results.isEmpty()) {
			return new ArrayList<>();
		}
		final String chunkingSession = chunkingService.createChunkingSession("agent-search:" + UUID.randomUUID());
		try {
			List<IDocumentChunkWithRef> chunks = chunkingService
					.streamChunks(results, new ChunkingParams(), chunkingSession, 4).sequential().collectList().block();
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
}
