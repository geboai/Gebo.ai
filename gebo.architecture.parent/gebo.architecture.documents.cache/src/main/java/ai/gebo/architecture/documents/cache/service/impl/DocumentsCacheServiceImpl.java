package ai.gebo.architecture.documents.cache.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import org.springframework.stereotype.Service;

import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.architecture.documents.cache.service.DocumentCacheAccessException;
import ai.gebo.architecture.documents.cache.service.IDocumentsCacheService;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.document.model.GeboDocument;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.model.base.GObjectRef;
import ai.gebo.system.ingestion.GeboIngestionException;
import ai.gebo.systems.abstraction.layer.IGContentManagementSystemHandler;
import ai.gebo.systems.abstraction.layer.IGContentManagementSystemHandlerRepositoryPattern;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DocumentsCacheServiceImpl implements IDocumentsCacheService {
	private final IGContentManagementSystemHandlerRepositoryPattern contentManagementSystemHandlerRepositoryPattern;
	private final IGPersistentObjectManager persistentObjectManager;

	private IGContentManagementSystemHandler retrieveHandler(GDocumentReference reference)
			throws DocumentCacheAccessException {
		GObjectRef<GProjectEndpoint> projectEndpointReference = reference.getProjectEndpointReference();
		GProjectEndpoint endpoint;
		try {
			endpoint = persistentObjectManager.findByReference(projectEndpointReference, GProjectEndpoint.class);
			if (endpoint == null)
				throw new DocumentCacheAccessException("Endpoint is unfound");
			IGContentManagementSystemHandler handler = contentManagementSystemHandlerRepositoryPattern
					.findByHandledEndpoint(endpoint);
			if (handler == null)
				throw new DocumentCacheAccessException("Project endpoint handler not found");
			return handler;
		} catch (GeboPersistenceException e) {
			throw new DocumentCacheAccessException("Exception while accessing project endpoint", e);
		}

	}
	
	@Override
	public GeboDocument getDocument(GDocumentReference reference) throws DocumentCacheAccessException, GeboContentHandlerSystemException, IOException, GeboIngestionException {
		
		return this.getDocumentOnCacheMiss(reference);
	}

	private GeboDocument getDocumentOnCacheMiss(GDocumentReference reference) throws DocumentCacheAccessException, GeboContentHandlerSystemException, IOException, GeboIngestionException {
		IGContentManagementSystemHandler handler = retrieveHandler(reference);
		return handler.readDocument(reference, new HashMap());
		
	}

	@Override
	public InputStream streamDocument(GDocumentReference reference) throws DocumentCacheAccessException, GeboContentHandlerSystemException, IOException {
		
		return streamDocumentOnCacheMiss(reference);
	}

	private InputStream streamDocumentOnCacheMiss(GDocumentReference reference) throws DocumentCacheAccessException, GeboContentHandlerSystemException, IOException {
		IGContentManagementSystemHandler handler = retrieveHandler(reference);
		return handler.streamContent(reference, new HashMap());
	}

}
