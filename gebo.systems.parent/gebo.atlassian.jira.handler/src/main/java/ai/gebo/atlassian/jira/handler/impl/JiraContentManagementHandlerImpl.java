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
 * Implementation of the Jira Content Management Handler that extends the abstract remote virtual filesystem 
 * content management system handler for Jira specific functionality.
 * This class manages interactions with Atlassian Jira systems.
 */
package ai.gebo.atlassian.jira.handler.impl;

import org.springframework.stereotype.Service;

import ai.gebo.application.messaging.IGMessageBroker;
import ai.gebo.application.messaging.model.GStandardModulesConstraints;
import ai.gebo.architecture.buildsystems.abstraction.layer.IGBuildSystemHandlerRepositoryPattern;
import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.architecture.contenthandling.interfaces.IGDocumentReferenceFactory;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.atlassian.jira.handler.GJiraProjectEndpoint;
import ai.gebo.atlassian.jira.handler.GJiraSystem;
import ai.gebo.atlassian.jira.handler.IGJiraContentManagementHandler;
import ai.gebo.atlassian.jira.handler.IGJiraVirtualFilesystemConsumingService;
import ai.gebo.atlassian.jira.handler.impl.model.JiraResourceReference;
import ai.gebo.atlassian.jira.handler.repository.JiraProjectEndpointRepository;
import ai.gebo.atlassian.jira.handler.repository.JiraSystemRepository;
import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.knlowledgebase.model.systems.GContentManagementSystemType;
import ai.gebo.secrets.services.IGeboSecretsAccessService;
import ai.gebo.system.ingestion.IGDocumentReferenceIngestionHandler;
import ai.gebo.systems.abstraction.layer.GAbstractRemoteVirtualFilesystemContentManagementSystemHandler;
import ai.gebo.systems.abstraction.layer.IGContentManagementSystemConfigurationDao;
import ai.gebo.systems.abstraction.layer.IGLocalPersistentFolderDiscoveryService;
import ai.gebo.systems.abstraction.layer.IGProjectEndpointRuntimeConfigurationDao;

@Service
public class JiraContentManagementHandlerImpl extends
		GAbstractRemoteVirtualFilesystemContentManagementSystemHandler<GJiraSystem, GJiraProjectEndpoint, JiraResourceReference, IGJiraVirtualFilesystemConsumingService>
		implements IGJiraContentManagementHandler {
	/** Constant identifying Atlassian Jira systems */
	public static final String ATLASSIAN_JIRA = "ATLASSIAN-JIRA";
	
	/** Content management system type for Jira */
	private static final GContentManagementSystemType systemType = new GContentManagementSystemType();
	
	/** Service for accessing secrets */
	protected IGeboSecretsAccessService secretsService = null;
	
	// Static initialization of system type
	static {
		systemType.setCode(ATLASSIAN_JIRA);
		systemType.setDescription("Atlassian jira");
	}

	/**
	 * Constructor for JiraContentManagementHandlerImpl.
	 * 
	 * @param buildSystemHandlerRepository Repository for build system handler
	 * @param contentHandler Factory for document references
	 * @param systemsRepo Repository for Jira systems
	 * @param endpointRepo Repository for Jira project endpoints
	 * @param localFolderDiscoveryService Service for discovering local folders
	 * @param persistentObjectManager Manager for persistent objects
	 * @param messageBroker Message broker for system communications
	 * @param secretsService Service for accessing secrets
	 * @param consumingService Service for consuming the Jira virtual filesystem
	 * @param ingestionHandler Handler for document reference ingestion
	 */
	public JiraContentManagementHandlerImpl(IGBuildSystemHandlerRepositoryPattern buildSystemHandlerRepository,
			IGDocumentReferenceFactory contentHandler, JiraSystemRepository systemsRepo,
			JiraProjectEndpointRepository endpointRepo,
			IGLocalPersistentFolderDiscoveryService localFolderDiscoveryService,
			IGPersistentObjectManager persistentObjectManager, IGMessageBroker messageBroker,
			IGeboSecretsAccessService secretsService, IGJiraVirtualFilesystemConsumingService consumingService,
			IGDocumentReferenceIngestionHandler ingestionHandler) {
		super(buildSystemHandlerRepository, contentHandler, IGContentManagementSystemConfigurationDao.of(systemsRepo),
				IGProjectEndpointRuntimeConfigurationDao.of(endpointRepo), localFolderDiscoveryService,
				persistentObjectManager, messageBroker, consumingService, ingestionHandler);
		this.secretsService = secretsService;

	}

	/**
	 * Returns the content management system type handled by this implementation.
	 * 
	 * @return The Jira content management system type
	 */
	@Override
	public GContentManagementSystemType getHandledSystemType() {
		return systemType;
	}

	/**
	 * Finds a Jira project endpoint by system code and project endpoint code.
	 * 
	 * @param systemCode The system code
	 * @param projectEndpointCode The project endpoint code
	 * @return The found Jira project endpoint
	 * @throws GeboContentHandlerSystemException If an error occurs during the lookup
	 */
	@Override
	public GJiraProjectEndpoint findProjectEndPoint(String systemCode, String projectEndpointCode)
			throws GeboContentHandlerSystemException {
		return endpointsDao.findByCode(projectEndpointCode);
	}

	/**
	 * Determines if the given endpoint is managed by this handler.
	 * 
	 * @param endpoint The project endpoint to check
	 * @return true if the endpoint is a GJiraProjectEndpoint, false otherwise
	 */
	@Override
	public boolean isManagedEndpoint(GProjectEndpoint endpoint) {
		return endpoint instanceof GJiraProjectEndpoint;
	}

	/**
	 * Gets the Jira system associated with the given project endpoint.
	 * 
	 * @param projectEndPoint The Jira project endpoint
	 * @return The associated Jira system
	 * @throws GeboContentHandlerSystemException If an error occurs retrieving the system
	 */
	@Override
	public GJiraSystem getSystem(GJiraProjectEndpoint projectEndPoint) throws GeboContentHandlerSystemException {
		return configurationsDao.findByCode(projectEndPoint.getJiraSystemCode());
	}

	/**
	 * Gets the messaging module ID for Jira.
	 * 
	 * @return The Atlassian Jira module ID
	 */
	@Override
	public String getMessagingModuleId() {
		return GStandardModulesConstraints.ATLASSIAN_JIRA_MODULE;
	}

}