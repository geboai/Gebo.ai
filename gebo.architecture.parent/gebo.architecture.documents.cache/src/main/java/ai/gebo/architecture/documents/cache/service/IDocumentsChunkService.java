package ai.gebo.architecture.documents.cache.service;

import java.io.IOException;
import java.util.List;

import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.architecture.documents.cache.model.AbstractChunkingSpecs;
import ai.gebo.architecture.documents.cache.model.DocumentChunkingResponse;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.system.ingestion.GeboIngestionException;

public interface IDocumentsChunkService {
	DocumentChunkingResponse getChunk(GDocumentReference document, List<AbstractChunkingSpecs> chunkingSpecs)
			throws DocumentCacheAccessException, IOException, GeboContentHandlerSystemException, GeboIngestionException;

	DocumentChunkingResponse getNextChunk(GDocumentReference document, String chunkRequestId, String nextChunkId)
			throws DocumentCacheAccessException, IOException;
}
