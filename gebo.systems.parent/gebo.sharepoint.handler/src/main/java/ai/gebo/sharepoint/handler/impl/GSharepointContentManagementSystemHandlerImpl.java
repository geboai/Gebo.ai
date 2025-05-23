/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.sharepoint.handler.impl;

/**
 * AI generated comments
 * 
 * Implementation of SharePoint Content Management System Handler.
 * This class extends the abstract remote virtual filesystem handler and implements
 * the SharePoint-specific interface for handling SharePoint content management systems.
 */
import java.util.List;

import org.springframework.stereotype.Service;

import ai.gebo.application.messaging.IGMessageBroker;
import ai.gebo.application.messaging.model.GStandardModulesConstraints;
import ai.gebo.architecture.buildsystems.abstraction.layer.IGBuildSystemHandlerRepositoryPattern;
import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.architecture.contenthandling.interfaces.IGDocumentReferenceFactory;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.knlowledgebase.model.systems.GContentManagementSystemType;
import ai.gebo.knlowledgebase.model.systems.GSystemRole;
import ai.gebo.sharepoint.handler.GSharepointContentManagementSystem;
import ai.gebo.sharepoint.handler.GSharepointProjectEndpoint;
import ai.gebo.sharepoint.handler.IGMicrosoftGraphVirtualFilesystemConsumingService;
import ai.gebo.sharepoint.handler.IGSharepointContentManagementSystemHandler;
import ai.gebo.sharepoint.handler.impl.model.MicrosoftGraphResourceReference;
import ai.gebo.system.ingestion.IGDocumentReferenceIngestionHandler;
import ai.gebo.systems.abstraction.layer.GAbstractRemoteVirtualFilesystemContentManagementSystemHandler;
import ai.gebo.systems.abstraction.layer.IGLocalPersistentFolderDiscoveryService;

@Service
public class GSharepointContentManagementSystemHandlerImpl extends
		GAbstractRemoteVirtualFilesystemContentManagementSystemHandler<GSharepointContentManagementSystem, GSharepointProjectEndpoint,MicrosoftGraphResourceReference,IGMicrosoftGraphVirtualFilesystemConsumingService>
		implements IGSharepointContentManagementSystemHandler {
	
	/**
	 * Static definition of the SharePoint content management system type.
	 */
	private static final GContentManagementSystemType systemType = new GContentManagementSystemType();
	
	/**
	 * Cache item key for SharePoint system.
	 */
	private static final String SHAREPOINT_SYSTEM_CACHE_ITEM = "SHAREPOINT_SYSTEM_CACHE_ITEM";
	
	/**
	 * Cache item key for SharePoint endpoint.
	 */
	private static final String SHAREPOINT_ENDPOINT_CACHE_ITEM = "SHAREPOINT_ENDPOINT_CACHE_ITEM";
	
	// Static initializer block to configure the system type
	static {
		systemType.setCode(GStandardModulesConstraints.SHAREPOINT_MODULE);
		systemType.setDescription("Microsoft Sharepoint");
		systemType.setCapabilities(List.of(GSystemRole.DOCUMENTS_MANAGEMENT));
	}
	
	/**
	 * Microsoft Graph Virtual Filesystem service for browsing operations.
	 */
	final GMicrosoftGraphVirtualFilesystemBrowsingServiceImpl msGraphVirtualService;

	/**
	 * Constructor for SharePoint Content Management System Handler.
	 * 
	 * @param buildSystemHandlerRepository Repository for build system handlers
	 * @param contentHandler Factory for document references
	 * @param configurationsDao DAO for SharePoint system configurations
	 * @param endpointsDao DAO for SharePoint endpoint configurations
	 * @param localFolderDiscoveryService Service for discovering local folders
	 * @param persistentObjectManager Manager for persistent objects
	 * @param msGraphVirtualService Microsoft Graph virtual filesystem browsing service
	 * @param messageBroker Message broker for system communications
	 * @param consumingService Service for consuming Microsoft Graph virtual filesystem
	 * @param ingestionHandler Handler for document reference ingestion
	 */
	public GSharepointContentManagementSystemHandlerImpl(
			IGBuildSystemHandlerRepositoryPattern buildSystemHandlerRepository,
			IGDocumentReferenceFactory contentHandler, SharepointSystemsConfiguratoinDao configurationsDao,
			SharepointProjectEndpointConfiguratoinDao endpointsDao,
			IGLocalPersistentFolderDiscoveryService localFolderDiscoveryService,
			IGPersistentObjectManager persistentObjectManager,
			GMicrosoftGraphVirtualFilesystemBrowsingServiceImpl msGraphVirtualService, IGMessageBroker messageBroker,
			IGMicrosoftGraphVirtualFilesystemConsumingService consumingService, IGDocumentReferenceIngestionHandler ingestionHandler) {
		super(buildSystemHandlerRepository, contentHandler, configurationsDao, endpointsDao,
				localFolderDiscoveryService, persistentObjectManager, messageBroker, consumingService, ingestionHandler);
		this.msGraphVirtualService = msGraphVirtualService;
	}

	/**
	 * Returns the content management system type handled by this implementation.
	 * 
	 * @return The SharePoint content management system type
	 */
	@Override
	public GContentManagementSystemType getHandledSystemType() {
		return systemType;
	}

	/**
	 * Finds a SharePoint project endpoint by system code and endpoint code.
	 * 
	 * @param systemCode The system code
	 * @param projectEndpointCode The project endpoint code
	 * @return The matching SharePoint project endpoint
	 * @throws GeboContentHandlerSystemException If an error occurs during the operation
	 */
	@Override
	public GSharepointProjectEndpoint findProjectEndPoint(String systemCode, String projectEndpointCode)
			throws GeboContentHandlerSystemException {
		return this.endpointsDao.findByCode(projectEndpointCode);
	}

	/**
	 * Determines if the provided endpoint is a SharePoint project endpoint.
	 * 
	 * @param endpoint The project endpoint to check
	 * @return true if the endpoint is a SharePoint project endpoint, false otherwise
	 */
	@Override
	public boolean isManagedEndpoint(GProjectEndpoint endpoint) {
		return endpoint instanceof GSharepointProjectEndpoint;
	}

	/**
	 * Gets the messaging module ID for SharePoint.
	 * 
	 * @return The SharePoint module ID from standard modules constraints
	 */
	@Override
	public String getMessagingModuleId() {
		return GStandardModulesConstraints.SHAREPOINT_MODULE;
	}

	/**
	 * Retrieves the SharePoint system associated with a project endpoint.
	 * 
	 * @param projectEndPoint The SharePoint project endpoint
	 * @return The associated SharePoint content management system
	 * @throws GeboContentHandlerSystemException If an error occurs during the operation
	 */
	@Override
	public GSharepointContentManagementSystem getSystem(GSharepointProjectEndpoint projectEndPoint)
			throws GeboContentHandlerSystemException {
		return this.configurationsDao.findByCode(projectEndPoint.getSharePointSystemCode());
	}
}