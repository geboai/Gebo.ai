package ai.gebo.architecture.documents.cache.service;

import java.io.IOException;
import java.io.InputStream;

import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.document.model.GeboDocument;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.system.ingestion.GeboIngestionException;

public interface IDocumentsCacheService {
	public GeboDocument getDocument(GDocumentReference reference) throws DocumentCacheAccessException, GeboContentHandlerSystemException, IOException, GeboIngestionException;

	public InputStream streamDocument(GDocumentReference reference) throws DocumentCacheAccessException, GeboContentHandlerSystemException, IOException;
}
