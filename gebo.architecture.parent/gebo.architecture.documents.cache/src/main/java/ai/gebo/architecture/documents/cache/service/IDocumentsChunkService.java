package ai.gebo.architecture.documents.cache.service;

import java.io.IOException;
import java.util.List;

import ai.gebo.architecture.documents.cache.model.ChunkingParams;
import ai.gebo.architecture.documents.cache.model.DocumentChunkingResponse;
import ai.gebo.architecture.documents.cache.model.IDocumentChunkWithRef;
import ai.gebo.architecture.search.model.SearchServiceException;
import ai.gebo.model.base.IGComponentOriginatedDocument;
import reactor.core.publisher.Flux;
import reactor.core.publisher.ParallelFlux;

public interface IDocumentsChunkService {
	DocumentChunkingResponse prepareChunks(IGComponentOriginatedDocument document, ChunkingParams chunkingSpecs,
			String chunkingSessionId) throws DocumentCacheAccessException, IOException, SearchServiceException;

	DocumentChunkingResponse getCachedChunkSet(IGComponentOriginatedDocument document, String chunkSessionId)
			throws DocumentCacheAccessException, IOException;

	DocumentChunkingResponse getChunkSet(IGComponentOriginatedDocument document, ChunkingParams chunkingSpecs,
			String chunkSessionId) throws DocumentCacheAccessException, IOException, SearchServiceException;

	DocumentChunkingResponse getNextChunkSet(IGComponentOriginatedDocument document, String chunkRequestId,
			String nextChunkId, String chunkSessionId) throws DocumentCacheAccessException, IOException;

	public ParallelFlux<IDocumentChunkWithRef> streamChunks(List<? extends IGComponentOriginatedDocument> documents,
			ChunkingParams chunkingSpecs, String chunkSessionId, int docConcurrency);

	public ParallelFlux<IDocumentChunkWithRef> streamChunks(
			org.reactivestreams.Publisher<List<? extends IGComponentOriginatedDocument>> documentsPublisher,
			ChunkingParams chunkingSpecs, String chunkSessionId, int docConcurrency);

	public Flux<IDocumentChunkWithRef> streamChunks(IGComponentOriginatedDocument document,
			ChunkingParams chunkingSpecs, String chunkSessionId);

	public String createChunkingSession(String reference);

	public String retrieveChunkingSession(String reference);

	public void disposeChunkingSession(String chunkSessionId);
}
