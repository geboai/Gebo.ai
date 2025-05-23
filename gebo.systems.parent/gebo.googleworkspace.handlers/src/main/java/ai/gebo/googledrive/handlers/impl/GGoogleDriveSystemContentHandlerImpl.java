/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.googledrive.handlers.impl;

/**
 * Implementation of Google Drive content handling system.
 * This class manages interactions with Google Drive as a content management system.
 * AI generated comments
 */
import org.springframework.stereotype.Service;

import ai.gebo.application.messaging.IGMessageBroker;
import ai.gebo.application.messaging.model.GStandardModulesConstraints;
import ai.gebo.architecture.buildsystems.abstraction.layer.IGBuildSystemHandlerRepositoryPattern;
import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.architecture.contenthandling.interfaces.IGDocumentReferenceFactory;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.googledrive.handlers.GGoogleDriveProjectEndpoint;
import ai.gebo.googledrive.handlers.GGoogleDriveSystem;
import ai.gebo.googledrive.handlers.IGGoogleDriveSystemContentHandler;
import ai.gebo.googledrive.handlers.IGGoogleDriveVirtualFilesystemConsumingService;
import ai.gebo.googledrive.handlers.impl.model.GoogleDriveResourceReference;
import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.knlowledgebase.model.systems.GContentManagementSystemType;
import ai.gebo.system.ingestion.IGDocumentReferenceIngestionHandler;
import ai.gebo.systems.abstraction.layer.GAbstractRemoteVirtualFilesystemContentManagementSystemHandler;
import ai.gebo.systems.abstraction.layer.IGLocalPersistentFolderDiscoveryService;

@Service
public class GGoogleDriveSystemContentHandlerImpl extends
		GAbstractRemoteVirtualFilesystemContentManagementSystemHandler<GGoogleDriveSystem, GGoogleDriveProjectEndpoint, GoogleDriveResourceReference, IGGoogleDriveVirtualFilesystemConsumingService>
		implements IGGoogleDriveSystemContentHandler {
	/**
	 * Constant identifier for the Google Drive handler
	 */
	public static final String GOOGLE_DRIVE_HANDLER = "google-drive-handler";
	
	/**
	 * Static content management system type configuration for Google Drive
	 */
	static GContentManagementSystemType systemType = new GContentManagementSystemType();
	static {
		systemType.setCode(GOOGLE_DRIVE_HANDLER);
		systemType.setDescription("Google drive content handler");
	}

	/**
	 * Constructor for Google Drive system content handler implementation.
	 * 
	 * @param buildSystemHandlerRepository Repository for build system handlers
	 * @param contentHandler Factory for document references
	 * @param configurationsDao Data access object for Google Drive systems
	 * @param endpointsDao Data access object for Google Drive project endpoints
	 * @param localFolderDiscoveryService Service for discovering local persistent folders
	 * @param persistentObjectManager Manager for persistent objects
	 * @param messageBroker Broker for system messages
	 * @param consumingService Service for consuming Google Drive virtual filesystem
	 * @param ingestionHandler Handler for document reference ingestion
	 */
	public GGoogleDriveSystemContentHandlerImpl(IGBuildSystemHandlerRepositoryPattern buildSystemHandlerRepository,
			IGDocumentReferenceFactory contentHandler, GoogleDriveSystemsDao configurationsDao,
			GoogleDriveProjectEndpointDao endpointsDao,
			IGLocalPersistentFolderDiscoveryService localFolderDiscoveryService,
			IGPersistentObjectManager persistentObjectManager, IGMessageBroker messageBroker,
			IGGoogleDriveVirtualFilesystemConsumingService consumingService, IGDocumentReferenceIngestionHandler ingestionHandler) {
		super(buildSystemHandlerRepository, contentHandler, configurationsDao, endpointsDao,
				localFolderDiscoveryService, persistentObjectManager, messageBroker, consumingService, ingestionHandler);

	}

	/**
	 * Returns the type of content management system handled by this class.
	 * 
	 * @return Google Drive content management system type
	 */
	@Override
	public GContentManagementSystemType getHandledSystemType() {
		return systemType;
	}

	/**
	 * Finds a project endpoint by its system code and project endpoint code.
	 * 
	 * @param systemCode The system code identifier
	 * @param projectEndpointCode The project endpoint code
	 * @return The matching Google Drive project endpoint
	 * @throws GeboContentHandlerSystemException if endpoint cannot be found
	 */
	@Override
	public GGoogleDriveProjectEndpoint findProjectEndPoint(String systemCode, String projectEndpointCode)
			throws GeboContentHandlerSystemException {
		return endpointsDao.findByCode(projectEndpointCode);
	}

	/**
	 * Determines if the given project endpoint is managed by this handler.
	 * 
	 * @param endpoint The project endpoint to check
	 * @return true if the endpoint is a Google Drive project endpoint
	 */
	@Override
	public boolean isManagedEndpoint(GProjectEndpoint endpoint) {
		return endpoint instanceof GGoogleDriveProjectEndpoint;
	}

	/**
	 * Retrieves the Google Drive system associated with the given project endpoint.
	 * 
	 * @param projectEndPoint The Google Drive project endpoint
	 * @return The associated Google Drive system
	 * @throws GeboContentHandlerSystemException if system cannot be found
	 */
	@Override
	public GGoogleDriveSystem getSystem(GGoogleDriveProjectEndpoint projectEndPoint)
			throws GeboContentHandlerSystemException {
		return configurationsDao.findByCode(projectEndPoint.getDriveSystemCode());
	}

	/**
	 * Gets the messaging module identifier for Google Drive.
	 * 
	 * @return The standard Google Drive module identifier
	 */
	@Override
	public String getMessagingModuleId() {
		return GStandardModulesConstraints.GOOGLE_DRIVE_MODULE;
	}
}