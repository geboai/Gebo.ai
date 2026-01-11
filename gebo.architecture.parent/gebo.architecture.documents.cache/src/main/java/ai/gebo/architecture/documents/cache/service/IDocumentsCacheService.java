package ai.gebo.architecture.documents.cache.service;

import java.io.IOException;

import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.architecture.search.model.SearchServiceException;
import ai.gebo.model.base.IGComponentOriginatedDocument;
import ai.gebo.model.base.TypedInputStream;

public interface IDocumentsCacheService {

	public TypedInputStream streamDocument(IGComponentOriginatedDocument reference)
			throws DocumentCacheAccessException, GeboContentHandlerSystemException, IOException, SearchServiceException;
}
