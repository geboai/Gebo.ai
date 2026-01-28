package ai.gebo.architecture.rag.support.layer.services;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.architecture.rag.support.layer.model.AIDocumentCacheItem;
import ai.gebo.architecture.rag.support.layer.model.AIDocumentReferenceItem;
import ai.gebo.architecture.rag.support.layer.model.AIDocumentsSet;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.model.base.GObjectRef;
import ai.gebo.system.ingestion.GeboIngestionException;
import ai.gebo.systems.abstraction.layer.IGContentManagementSystemHandler;

public interface IGAIDocumentsCacheService {
	public void addCachedOrRetrieve(GObjectRef<GProjectEndpoint> objectRef, List<GDocumentReference> docList,
			AIDocumentsSet result)
			throws GeboPersistenceException, GeboContentHandlerSystemException, IOException, GeboIngestionException;

	public void addCacheOrRetrieve(GDocumentReference document, IGContentManagementSystemHandler handler,
			GProjectEndpoint endpoint, AIDocumentsSet result, Map handlerWorkCache)
			throws GeboContentHandlerSystemException, IOException, GeboIngestionException, GeboPersistenceException;

	public void addToRetrieved(AIDocumentCacheItem cacheItem, GDocumentReference document, AIDocumentsSet result);

	public void loadAddCacheAndAddToRetrieved(GDocumentReference document, IGContentManagementSystemHandler handler,
			GProjectEndpoint endpoint, AIDocumentsSet result, Map handlerWorkCache)
			throws GeboContentHandlerSystemException, IOException, GeboIngestionException, GeboPersistenceException;

	public AIDocumentReferenceItem retrieve(GDocumentReference document)
			throws GeboPersistenceException, GeboContentHandlerSystemException, IOException, GeboIngestionException;

}
