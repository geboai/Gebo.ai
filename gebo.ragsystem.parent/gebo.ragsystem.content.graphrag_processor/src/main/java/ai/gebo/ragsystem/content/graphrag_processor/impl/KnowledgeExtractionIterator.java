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
	// Current chunks set
	private DocumentChunkingResponse current = null;
	// index on the current chunks set of the current Chunk
	private int currentIndex = 0;
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
		this.current = chunkingService.getCachedChunkSet(reference);
	}

	@Override
	public boolean hasNext() {
		if (exceptionOccurred)
			return false;
		if (!prepared) {
			// there is a next element if the current response is not empty and
			// the current index points to an existing chunk in the actual set or
			// there is a next set to be loaded
			hasNextCached = (current != null && !current.isEmpty()
					&& (currentIndex < current.getCurrentChunkSet().getChunks().size()
							|| current.getNextChunkSetId() != null));
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
			if (currentIndex >= current.getCurrentChunkSet().getChunks().size()) {
				this.currentIndex = 0;
				if (current.getNextChunkSetId() != null) {
					current = chunkingService.getNextChunkSet(reference, current.getId(), current.getNextChunkSetId());
				} else {
					current = null;
				}
			}
			if (current == null) {
				throw new java.util.NoSuchElementException();
			}
			DocumentChunk chunk = current.getCurrentChunkSet().getChunks().get(currentIndex);
			currentIndex++;
			Document document = new Document(chunk.getId(), chunk.getChunkData(), chunk.getMetaData());
			LLMExtractionResult extraction = graphRagExtractionService.extract(document, reference);
			data = new KnowledgeExtractionData(extraction, document);

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