package ai.gebo.architecture.documents.cache.service;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Flow.Publisher;

import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.architecture.documents.cache.model.AbstractChunkingSpecs;
import ai.gebo.architecture.documents.cache.model.DocumentChunk;
import ai.gebo.architecture.documents.cache.model.DocumentChunkingResponse;
import ai.gebo.architecture.documents.cache.model.IDocumentChunkWithRef;
import ai.gebo.architecture.search.model.SearchServiceException;
import ai.gebo.model.base.IGComponentOriginatedDocument;
import ai.gebo.system.ingestion.GeboIngestionException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.ParallelFlux;

public interface IDocumentsChunkService {
	DocumentChunkingResponse prepareChunks(IGComponentOriginatedDocument document,
			List<AbstractChunkingSpecs> chunkingSpecs, boolean enrichWithMetaData, long tokensPerChunkSet)
			throws DocumentCacheAccessException, IOException, GeboContentHandlerSystemException, GeboIngestionException,
			SearchServiceException;

	DocumentChunkingResponse getCachedChunkSet(IGComponentOriginatedDocument document)
			throws DocumentCacheAccessException, IOException, GeboContentHandlerSystemException, GeboIngestionException;

	DocumentChunkingResponse getChunkSet(IGComponentOriginatedDocument document,
			List<AbstractChunkingSpecs> chunkingSpecs, boolean enrichWithMetaData, long tokensPerChunkSet)
			throws DocumentCacheAccessException, IOException, GeboContentHandlerSystemException, GeboIngestionException,
			SearchServiceException;

	DocumentChunkingResponse getNextChunkSet(IGComponentOriginatedDocument document, String chunkRequestId,
			String nextChunkId) throws DocumentCacheAccessException, IOException;

	public ParallelFlux<IDocumentChunkWithRef> streamChunks(List<? extends IGComponentOriginatedDocument> documents,
			List<AbstractChunkingSpecs> chunkingSpecs, boolean enrichWithMetaData, long tokensPerChunkSet);

	public ParallelFlux<IDocumentChunkWithRef> streamChunks(
			org.reactivestreams.Publisher<List<IGComponentOriginatedDocument>> documentsPublisher,
			List<AbstractChunkingSpecs> chunkingSpecs, boolean enrichWithMetaData, long tokensPerChunkSet);

	public Flux<IDocumentChunkWithRef> streamChunks(IGComponentOriginatedDocument document,
			List<AbstractChunkingSpecs> chunkingSpecs, boolean enrichWithMetaData, long tokensPerChunkSet);
}
