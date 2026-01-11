package ai.gebo.architecture.documents.cache.service;

import java.io.IOException;
import java.util.List;

import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.architecture.documents.cache.model.AbstractChunkingSpecs;
import ai.gebo.architecture.documents.cache.model.DocumentChunkingResponse;
import ai.gebo.architecture.search.model.SearchServiceException;
import ai.gebo.model.base.IGComponentOriginatedDocument;
import ai.gebo.system.ingestion.GeboIngestionException;

public interface IDocumentsChunkService {
	DocumentChunkingResponse prepareChunks(IGComponentOriginatedDocument document, List<AbstractChunkingSpecs> chunkingSpecs,
			boolean enrichWithMetaData, long tokensPerChunkSet)
			throws DocumentCacheAccessException, IOException, GeboContentHandlerSystemException, GeboIngestionException, SearchServiceException;

	DocumentChunkingResponse getCachedChunkSet(IGComponentOriginatedDocument document)
			throws DocumentCacheAccessException, IOException, GeboContentHandlerSystemException, GeboIngestionException;

	DocumentChunkingResponse getChunkSet(IGComponentOriginatedDocument document, List<AbstractChunkingSpecs> chunkingSpecs,
			boolean enrichWithMetaData, long tokensPerChunkSet)
			throws DocumentCacheAccessException, IOException, GeboContentHandlerSystemException, GeboIngestionException, SearchServiceException;

	DocumentChunkingResponse getNextChunkSet(IGComponentOriginatedDocument document, String chunkRequestId, String nextChunkId)
			throws DocumentCacheAccessException, IOException;
}
