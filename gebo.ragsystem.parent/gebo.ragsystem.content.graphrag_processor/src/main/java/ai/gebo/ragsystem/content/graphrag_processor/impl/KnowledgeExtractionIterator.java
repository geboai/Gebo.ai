package ai.gebo.ragsystem.content.graphrag_processor.impl;

import java.io.IOException;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;

import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.architecture.documents.cache.model.DocumentChunk;
import ai.gebo.architecture.documents.cache.model.DocumentChunkingResponse;
import ai.gebo.architecture.documents.cache.service.DocumentCacheAccessException;
import ai.gebo.architecture.documents.cache.service.IDocumentsChunkService;
import ai.gebo.architecture.graphrag.extraction.model.LLMExtractionResult;
import ai.gebo.architecture.graphrag.extraction.services.IGraphDataExtractionService;
import ai.gebo.architecture.graphrag.persistence.model.KnowledgeExtractionData;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.system.ingestion.GeboIngestionException;

public class KnowledgeExtractionIterator implements Iterator<KnowledgeExtractionData> {
	private final GDocumentReference reference;
	private final IDocumentsChunkService chunkingService;
	private final IGraphDataExtractionService graphRagExtractionService;
	private DocumentChunkingResponse current = null;
	private boolean prepared = false; // per gestire lo stato di fine
	private boolean hasNextCached = false;
	private boolean exceptionOccurred = false;
	private static final Logger LOGGER = LoggerFactory.getLogger(KnowledgeExtractionIterator.class);

	public KnowledgeExtractionIterator(GDocumentReference reference, IDocumentsChunkService chunkingService,
			IGraphDataExtractionService graphRagExtractionService) throws DocumentCacheAccessException, IOException,
			GeboContentHandlerSystemException, GeboIngestionException {
		this.reference = reference;
		this.chunkingService = chunkingService;
		this.graphRagExtractionService = graphRagExtractionService;
		this.current = chunkingService.getCachedChunk(reference);
	}

	@Override
	public boolean hasNext() {
		if (exceptionOccurred)
			return false;
		if (!prepared) {
			hasNextCached = (current != null && !current.isEmpty());
			prepared = true;
		}
		return hasNextCached;
	}

	@Override
	public KnowledgeExtractionData next() {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin next()");
		}
		if (!hasNext())
			throw new java.util.NoSuchElementException();
		KnowledgeExtractionData data = null;
		try {

			DocumentChunk chunk = current.getCurrentChunk();
			Document document = new Document(chunk.getId(), chunk.getChunkData(), chunk.getMetaData());
			LLMExtractionResult extraction = graphRagExtractionService.extract(document, reference);
			data = new KnowledgeExtractionData(extraction, document);

			if (current.getNextChunkId() != null) {
				current = chunkingService.getNextChunk(reference, current.getId(), current.getNextChunkId());
			} else {
				current = null;
			}
		} catch (Throwable throwable) {
			LOGGER.error("Exception in knowledge extraction", throwable);
			exceptionOccurred = true;
			throw new java.util.NoSuchElementException();
		}
		prepared = false;
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("End next()");
		}
		return data;
	}

	public boolean isExceptionOccurred() {
		return exceptionOccurred;
	}
}