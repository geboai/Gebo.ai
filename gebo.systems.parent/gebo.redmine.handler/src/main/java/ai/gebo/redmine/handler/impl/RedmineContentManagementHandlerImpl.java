/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.redmine.handler.impl;

import ai.gebo.application.messaging.IGMessageBroker;
import ai.gebo.application.messaging.model.GStandardModulesConstraints;
import ai.gebo.architecture.buildsystems.abstraction.layer.IGBuildSystemHandlerRepositoryPattern;
import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.architecture.contenthandling.interfaces.IGDocumentReferenceFactory;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.knlowledgebase.model.systems.GContentManagementSystemType;
import ai.gebo.redmine.handler.GRedmineProjectEndpoint;
import ai.gebo.redmine.handler.GRedmineSystem;
import ai.gebo.redmine.handler.IGRedmineContentManagementSystemHandler;
import ai.gebo.redmine.handler.IGRedmineVirtualFilesystemConsumingService;
import ai.gebo.redmine.handler.RedmineResourceReference;
import ai.gebo.system.ingestion.IGDocumentReferenceIngestionHandler;
import ai.gebo.systems.abstraction.layer.GAbstractRemoteVirtualFilesystemContentManagementSystemHandler;
import ai.gebo.systems.abstraction.layer.IGLocalPersistentFolderDiscoveryService;

/**
 * The Redmine content handler.
 */
public class RedmineContentManagementHandlerImpl extends
		GAbstractRemoteVirtualFilesystemContentManagementSystemHandler<GRedmineSystem, GRedmineProjectEndpoint, RedmineResourceReference, IGRedmineVirtualFilesystemConsumingService>
		implements IGRedmineContentManagementSystemHandler {
	public static final String REDMINE = "REDMINE";
	private static final GContentManagementSystemType systemType = new GContentManagementSystemType();
	static {
		systemType.setCode(REDMINE);
		systemType.setDescription("Redmine project management");
	}

	public RedmineContentManagementHandlerImpl(IGBuildSystemHandlerRepositoryPattern buildSystemHandlerRepository,
			IGDocumentReferenceFactory contentHandler, RedmineSystemsConfigurationDao systemsDao,
			RedmineProjectEndpointConfigurationDao endpointsDao,
			IGLocalPersistentFolderDiscoveryService localFolderDiscoveryService,
			IGPersistentObjectManager persistentObjectManager, IGMessageBroker messageBroker,
			IGRedmineVirtualFilesystemConsumingService consumingService,
			IGDocumentReferenceIngestionHandler ingestionHandler) {
		super(buildSystemHandlerRepository, contentHandler, systemsDao, endpointsDao, localFolderDiscoveryService,
				persistentObjectManager, messageBroker, consumingService, ingestionHandler);
	}

	@Override
	public GContentManagementSystemType getHandledSystemType() {
		return systemType;
	}

	@Override
	public GRedmineProjectEndpoint findProjectEndPoint(String systemCode, String projectEndpointCode)
			throws GeboContentHandlerSystemException {
		return endpointsDao.findByCode(projectEndpointCode);
	}

	@Override
	public boolean isManagedEndpoint(GProjectEndpoint endpoint) {
		return endpoint instanceof GRedmineProjectEndpoint;
	}

	@Override
	public GRedmineSystem getSystem(GRedmineProjectEndpoint projectEndPoint) throws GeboContentHandlerSystemException {
		return configurationsDao.findByCode(projectEndPoint.getRedmineSystemCode());
	}

	@Override
	public String getMessagingModuleId() {
		return GStandardModulesConstraints.REDMINE_MODULE;
	}
}
