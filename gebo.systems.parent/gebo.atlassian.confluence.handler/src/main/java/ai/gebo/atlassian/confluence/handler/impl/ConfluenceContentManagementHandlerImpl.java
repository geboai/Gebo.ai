/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

/**
 * AI generated comments
 * Implementation of the Confluence Content Management Handler.
 * This service extends the abstract remote virtual filesystem content management system handler
 * to provide specific functionality for Atlassian Confluence integrations.
 */
package ai.gebo.atlassian.confluence.handler.impl;

import org.springframework.stereotype.Service;

import ai.gebo.application.messaging.IGMessageBroker;
import ai.gebo.application.messaging.model.GStandardModulesConstraints;
import ai.gebo.architecture.buildsystems.abstraction.layer.IGBuildSystemHandlerRepositoryPattern;
import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.architecture.contenthandling.interfaces.IGDocumentReferenceFactory;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.atlassian.confluence.handler.GConfluenceProjectEndpoint;
import ai.gebo.atlassian.confluence.handler.GConfluenceSystem;
import ai.gebo.atlassian.confluence.handler.IGConfluenceContentManagementHandler;
import ai.gebo.atlassian.confluence.handler.IGConfluenceVirtualFilesystemConsumingService;
import ai.gebo.atlassian.confluence.handler.impl.model.ConfluenceResourceReference;
import ai.gebo.atlassian.confluence.handler.repositories.ConfluenceProjectEndpointRepository;
import ai.gebo.atlassian.confluence.handler.repositories.ConfluenceSystemRepository;
import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.knlowledgebase.model.systems.GContentManagementSystemType;
import ai.gebo.secrets.services.IGeboSecretsAccessService;
import ai.gebo.system.ingestion.IGDocumentReferenceIngestionHandler;
import ai.gebo.systems.abstraction.layer.GAbstractRemoteVirtualFilesystemContentManagementSystemHandler;
import ai.gebo.systems.abstraction.layer.IGContentManagementSystemConfigurationDao;
import ai.gebo.systems.abstraction.layer.IGLocalPersistentFolderDiscoveryService;
import ai.gebo.systems.abstraction.layer.IGProjectEndpointRuntimeConfigurationDao;

@Service
public class ConfluenceContentManagementHandlerImpl extends
		GAbstractRemoteVirtualFilesystemContentManagementSystemHandler<GConfluenceSystem, GConfluenceProjectEndpoint, ConfluenceResourceReference, IGConfluenceVirtualFilesystemConsumingService>
		implements IGConfluenceContentManagementHandler {
	/**
	 * Constant representing the system type code for Atlassian Confluence.
	 */
	public static final String ATLASSIAN_CONFLUENCE = "ATLASSIAN-CONFLUENCE";
	
	/**
	 * Static instance of GContentManagementSystemType representing Confluence.
	 */
	private static final GContentManagementSystemType systemType = new GContentManagementSystemType();
	
	/**
	 * Service for accessing secrets needed for Confluence authentication.
	 */
	protected IGeboSecretsAccessService secretsService = null;
	
	static {
		systemType.setCode(ATLASSIAN_CONFLUENCE);
		systemType.setDescription("Atlassian confluence");
	}

	/**
	 * Constructor for ConfluenceContentManagementHandlerImpl.
	 * Initializes the handler with necessary dependencies for managing Confluence content.
	 *
	 * @param buildSystemHandlerRepository Repository for build system handlers
	 * @param contentHandler Factory for document references
	 * @param systemsRepo Repository for Confluence systems
	 * @param endpointRepo Repository for Confluence project endpoints
	 * @param localFolderDiscoveryService Service for discovering local folders
	 * @param persistentObjectManager Manager for persistent objects
	 * @param messageBroker Broker for messaging
	 * @param secretsService Service for accessing secrets
	 * @param consumingService Service for consuming Confluence virtual filesystem
	 * @param ingestionHandler Handler for document reference ingestion
	 */
	public ConfluenceContentManagementHandlerImpl(
			IGBuildSystemHandlerRepositoryPattern buildSystemHandlerRepository,
			IGDocumentReferenceFactory contentHandler, ConfluenceSystemRepository systemsRepo,
			ConfluenceProjectEndpointRepository endpointRepo,
			IGLocalPersistentFolderDiscoveryService localFolderDiscoveryService,
			IGPersistentObjectManager persistentObjectManager, IGMessageBroker messageBroker,
			IGeboSecretsAccessService secretsService, IGConfluenceVirtualFilesystemConsumingService consumingService, IGDocumentReferenceIngestionHandler ingestionHandler) {
		super(buildSystemHandlerRepository, contentHandler, IGContentManagementSystemConfigurationDao.of(systemsRepo),
				IGProjectEndpointRuntimeConfigurationDao.of(endpointRepo), localFolderDiscoveryService,
				persistentObjectManager, messageBroker, consumingService, ingestionHandler);
		this.secretsService = secretsService;

	}

	/**
	 * Returns the content management system type handled by this implementation.
	 * 
	 * @return The Confluence system type
	 */
	@Override
	public GContentManagementSystemType getHandledSystemType() {
		return systemType;
	}

	/**
	 * Finds a Confluence project endpoint by its system code and project endpoint code.
	 *
	 * @param systemCode The system code
	 * @param projectEndpointCode The project endpoint code
	 * @return The found GConfluenceProjectEndpoint
	 * @throws GeboContentHandlerSystemException If an error occurs during the search
	 */
	@Override
	public GConfluenceProjectEndpoint findProjectEndPoint(String systemCode, String projectEndpointCode)
			throws GeboContentHandlerSystemException {
		return endpointsDao.findByCode(projectEndpointCode);
	}

	/**
	 * Checks if the given endpoint is managed by this handler.
	 *
	 * @param endpoint The project endpoint to check
	 * @return true if the endpoint is a GConfluenceProjectEndpoint, false otherwise
	 */
	@Override
	public boolean isManagedEndpoint(GProjectEndpoint endpoint) {
		return endpoint instanceof GConfluenceProjectEndpoint;
	}

	/**
	 * Gets the Confluence system associated with the given project endpoint.
	 *
	 * @param projectEndPoint The Confluence project endpoint
	 * @return The associated Confluence system
	 * @throws GeboContentHandlerSystemException If an error occurs while retrieving the system
	 */
	@Override
	public GConfluenceSystem getSystem(GConfluenceProjectEndpoint projectEndPoint)
			throws GeboContentHandlerSystemException {
		return configurationsDao.findByCode(projectEndPoint.getConfluenceSystemCode());
	}

	/**
	 * Gets the messaging module ID for Confluence.
	 *
	 * @return The Atlassian Confluence module ID
	 */
	@Override
	public String getMessagingModuleId() {
		return GStandardModulesConstraints.ATLASSIAN_CONFLUENCE_MODULE;
	}
}