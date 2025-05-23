/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.git.content.handler.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import ai.gebo.application.messaging.IGMessageBroker;
import ai.gebo.application.messaging.model.GStandardModulesConstraints;
import ai.gebo.architecture.buildsystems.abstraction.layer.IGBuildSystemHandlerRepositoryPattern;
import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.architecture.contenthandling.interfaces.IGDocumentReferenceFactory;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.git.content.handler.GGitContentManagementSystem;
import ai.gebo.git.content.handler.GGitProjectEndpoint;
import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.knlowledgebase.model.systems.GContentManagementSystemType;
import ai.gebo.knlowledgebase.model.systems.GSystemRole;
import ai.gebo.secrets.services.IGeboSecretsAccessService;
import ai.gebo.system.ingestion.IGDocumentReferenceIngestionHandler;
import ai.gebo.systems.abstraction.layer.IGContentManagementSystemConfigurationDao;
import ai.gebo.systems.abstraction.layer.IGLocalPersistentFolderDiscoveryService;
import ai.gebo.systems.abstraction.layer.IGProjectEndpointRuntimeConfigurationDao;

/**
 * AI generated comments
 * 
 * Default implementation of Git content management system handler.
 * This class handles git-based content management systems and provides 
 * functionality to manage git project endpoints.
 */
@Service
public class GDefaultGitContentManagementSystemHandler
		extends GAbstractGitContentManagementSystemHandler<GGitContentManagementSystem, GGitProjectEndpoint> {

	/**
	 * Constructor for the default Git content management system handler.
	 * 
	 * @param buildSystemHandlerRepository repository for build system handlers
	 * @param contentHandler factory for document references
	 * @param configurationsDao DAO for accessing Git content management system configurations
	 * @param endpointDao DAO for accessing Git project endpoint configurations
	 * @param secretsAccessService service for accessing secrets
	 * @param localFolderDiscoveryService service for discovering local folders
	 * @param persistentObjectManager manager for persistent objects
	 * @param messageBroker broker for message passing
	 * @param ingestionHandler handler for document reference ingestion
	 */
	public GDefaultGitContentManagementSystemHandler(IGBuildSystemHandlerRepositoryPattern buildSystemHandlerRepository,
			IGDocumentReferenceFactory contentHandler,
			IGContentManagementSystemConfigurationDao<GGitContentManagementSystem> configurationsDao,
			IGProjectEndpointRuntimeConfigurationDao<GGitProjectEndpoint> endpointDao,
			IGeboSecretsAccessService secretsAccessService,
			IGLocalPersistentFolderDiscoveryService localFolderDiscoveryService,
			IGPersistentObjectManager persistentObjectManager, IGMessageBroker messageBroker,
			IGDocumentReferenceIngestionHandler ingestionHandler) {
		super(buildSystemHandlerRepository, contentHandler, configurationsDao, endpointDao, secretsAccessService,
				localFolderDiscoveryService, persistentObjectManager, messageBroker, ingestionHandler);

	}

	/**
	 * Static definition of the Git content management system type with its capabilities.
	 */
	private static final GContentManagementSystemType handledSystemType = new GContentManagementSystemType();
	static {
		handledSystemType.setCode("DEFAULT.GIT.CONTENT.HANDLER");
		handledSystemType.setDescription("Generical Git server handler");
		handledSystemType.setCapabilities(List.of(GSystemRole.SOURCE_MANAGEMENT, GSystemRole.DOCUMENTS_MANAGEMENT));

	}

	/**
	 * Returns the system type that this handler is designed to handle.
	 * 
	 * @return the Git content management system type
	 */
	@Override
	public GContentManagementSystemType getHandledSystemType() {
		return handledSystemType;
	}

	/**
	 * Finds a project endpoint by its system code and project endpoint code.
	 * 
	 * @param systemCode the code of the system
	 * @param projectEndpointCode the code of the project endpoint
	 * @return the Git project endpoint
	 * @throws GeboContentHandlerSystemException if an error occurs during retrieval
	 */
	@Override
	public GGitProjectEndpoint findProjectEndPoint(String systemCode, String projectEndpointCode)
			throws GeboContentHandlerSystemException {
		GGitProjectEndpoint endpoint = endpointsDao.findByCode(projectEndpointCode);
		return endpoint;
	}

	/**
	 * Determines if the given endpoint is managed by this handler.
	 * 
	 * @param endpoint the project endpoint to check
	 * @return true if this handler manages the endpoint, false otherwise
	 */
	@Override
	public boolean isManagedEndpoint(GProjectEndpoint endpoint) {
		// Checks if the endpoint is exactly a GGitProjectEndpoint (not a subclass)
		return endpoint instanceof GGitProjectEndpoint && GGitProjectEndpoint.class.equals(endpoint.getClass());
	}

	/**
	 * Retrieves the Git content management system associated with the given project endpoint.
	 * 
	 * @param projectEndPoint the Git project endpoint
	 * @return the associated Git content management system
	 * @throws GeboContentHandlerSystemException if an error occurs during retrieval
	 */
	@Override
	public GGitContentManagementSystem getSystem(GGitProjectEndpoint projectEndPoint)
			throws GeboContentHandlerSystemException {
		return this.configurationsDao.findByCode(projectEndPoint.getContentManagementSystem());
	}

	/**
	 * Returns the messaging module ID used for communication related to Git operations.
	 * 
	 * @return the Git module identifier
	 */
	@Override
	public String getMessagingModuleId() {
		return GStandardModulesConstraints.GIT_MODULE;
	}

}