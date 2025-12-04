package ai.gebo.architecture.documents.cache.service;

import java.io.IOException;
import java.io.InputStream;

import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;

public interface IDocumentsCacheService {
	

	public InputStream streamDocument(GDocumentReference reference) throws DocumentCacheAccessException, GeboContentHandlerSystemException, IOException;
}
