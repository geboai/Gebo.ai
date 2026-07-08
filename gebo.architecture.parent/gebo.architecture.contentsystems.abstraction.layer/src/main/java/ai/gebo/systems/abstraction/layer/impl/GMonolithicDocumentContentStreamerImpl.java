package ai.gebo.systems.abstraction.layer.impl;

import java.io.IOException;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.architecture.documents.access.DocumentContentStreamerException;
import ai.gebo.architecture.documents.access.IGDocumentContentStreamer;
import ai.gebo.architecture.documents.access.StreamingPurpose;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.architecture.search.model.SearchResult;
import ai.gebo.architecture.search.model.SearchServiceException;
import ai.gebo.architecture.search.service.ISearchService;
import ai.gebo.architecture.search.service.ISearchServiceRepositoryPattern;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.model.base.GObjectRef;
import ai.gebo.model.base.GeboComponentInfo;
import ai.gebo.model.base.IGComponentOriginatedDocument;
import ai.gebo.model.base.TypedInputStream;
import ai.gebo.systems.abstraction.layer.IGContentManagementSystemHandler;
import ai.gebo.systems.abstraction.layer.IGContentManagementSystemHandlerRepositoryPattern;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class GMonolithicDocumentContentStreamerImpl implements IGDocumentContentStreamer {
	private static final String ERROR_RETRIEVING_DOCUMENT = "Error retrieving document";
	private final static Logger LOGGER = LoggerFactory.getLogger(GMonolithicDocumentContentStreamerImpl.class);
	private final IGContentManagementSystemHandlerRepositoryPattern contentManagementSystemHandlerRepositoryPattern;
	private final ISearchServiceRepositoryPattern searchServicesRepository;
	private final IGPersistentObjectManager persistentObjectManager;

	@Override
	public TypedInputStream streamContent(StreamingPurpose purpose, IGComponentOriginatedDocument document)
			throws DocumentContentStreamerException, IOException {
		try {
			if (document instanceof GDocumentReference doc) {

				return retrieveHandler(doc).streamContent(purpose, doc, new HashMap());

			} else if (document instanceof SearchResult searchResult) {
				GeboComponentInfo originComponent = searchResult.getOriginComponent();
				ISearchService searchService = searchServicesRepository.findByOriginComponent(originComponent);
				return searchService.loadSearchResult(searchResult);
			}
		} catch (GeboContentHandlerSystemException | IOException | DocumentContentStreamerException
				| SearchServiceException e) {
			LOGGER.error(ERROR_RETRIEVING_DOCUMENT, e);
			throw new DocumentContentStreamerException(ERROR_RETRIEVING_DOCUMENT, e);
		}
		return null;
	}

	private IGContentManagementSystemHandler retrieveHandler(GDocumentReference reference)
			throws DocumentContentStreamerException, IOException {
		GObjectRef<GProjectEndpoint> projectEndpointReference = reference.getProjectEndpointReference();
		GProjectEndpoint endpoint;
		try {
			endpoint = persistentObjectManager.findByReference(projectEndpointReference, GProjectEndpoint.class);
			if (endpoint == null)
				throw new DocumentContentStreamerException("Endpoint is unfound");
			IGContentManagementSystemHandler handler = contentManagementSystemHandlerRepositoryPattern
					.findByHandledEndpoint(endpoint);
			if (handler == null)
				throw new DocumentContentStreamerException("Project endpoint handler not found");
			return handler;
		} catch (GeboPersistenceException e) {
			throw new DocumentContentStreamerException("Exception while accessing project endpoint", e);
		}

	}
}
