/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.openproject.handler.impl;

import ai.gebo.application.messaging.IGMessageBroker;
import ai.gebo.application.messaging.model.GStandardModulesConstraints;
import ai.gebo.architecture.buildsystems.abstraction.layer.IGBuildSystemHandlerRepositoryPattern;
import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.architecture.contenthandling.interfaces.IGDocumentReferenceFactory;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.knlowledgebase.model.systems.GContentManagementSystemType;
import ai.gebo.openproject.handler.GOpenProjectProjectEndpoint;
import ai.gebo.openproject.handler.GOpenProjectSystem;
import ai.gebo.openproject.handler.IGOpenProjectContentManagementSystemHandler;
import ai.gebo.openproject.handler.IGOpenProjectVirtualFilesystemConsumingService;
import ai.gebo.openproject.handler.OpenProjectResourceReference;
import ai.gebo.system.ingestion.IGDocumentReferenceIngestionHandler;
import ai.gebo.systems.abstraction.layer.GAbstractRemoteVirtualFilesystemContentManagementSystemHandler;
import ai.gebo.systems.abstraction.layer.IGLocalPersistentFolderDiscoveryService;

/**
 * The OpenProject content handler.
 */
public class OpenProjectContentManagementHandlerImpl extends
		GAbstractRemoteVirtualFilesystemContentManagementSystemHandler<GOpenProjectSystem, GOpenProjectProjectEndpoint, OpenProjectResourceReference, IGOpenProjectVirtualFilesystemConsumingService>
		implements IGOpenProjectContentManagementSystemHandler {
	public static final String OPENPROJECT = "OPENPROJECT";
	private static final GContentManagementSystemType systemType = new GContentManagementSystemType();
	static {
		systemType.setCode(OPENPROJECT);
		systemType.setDescription("OpenProject project management");
	}

	public OpenProjectContentManagementHandlerImpl(IGBuildSystemHandlerRepositoryPattern buildSystemHandlerRepository,
			IGDocumentReferenceFactory contentHandler, OpenProjectSystemsConfigurationDao systemsDao,
			OpenProjectProjectEndpointConfigurationDao endpointsDao,
			IGLocalPersistentFolderDiscoveryService localFolderDiscoveryService,
			IGPersistentObjectManager persistentObjectManager, IGMessageBroker messageBroker,
			IGOpenProjectVirtualFilesystemConsumingService consumingService,
			IGDocumentReferenceIngestionHandler ingestionHandler) {
		super(buildSystemHandlerRepository, contentHandler, systemsDao, endpointsDao, localFolderDiscoveryService,
				persistentObjectManager, messageBroker, consumingService, ingestionHandler);
	}

	@Override
	public GContentManagementSystemType getHandledSystemType() {
		return systemType;
	}

	@Override
	public GOpenProjectProjectEndpoint findProjectEndPoint(String systemCode, String projectEndpointCode)
			throws GeboContentHandlerSystemException {
		return endpointsDao.findByCode(projectEndpointCode);
	}

	@Override
	public boolean isManagedEndpoint(GProjectEndpoint endpoint) {
		return endpoint instanceof GOpenProjectProjectEndpoint;
	}

	@Override
	public GOpenProjectSystem getSystem(GOpenProjectProjectEndpoint projectEndPoint) throws GeboContentHandlerSystemException {
		return configurationsDao.findByCode(projectEndPoint.getOpenProjectSystemCode());
	}

	@Override
	public String getMessagingModuleId() {
		return GStandardModulesConstraints.OPENPROJECT_MODULE;
	}
}
