/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.glpi.handler.impl;

import ai.gebo.application.messaging.IGMessageBroker;
import ai.gebo.application.messaging.model.GStandardModulesConstraints;
import ai.gebo.architecture.buildsystems.abstraction.layer.IGBuildSystemHandlerRepositoryPattern;
import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.architecture.contenthandling.interfaces.IGDocumentReferenceFactory;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.glpi.handler.GGlpiProjectEndpoint;
import ai.gebo.glpi.handler.GGlpiSystem;
import ai.gebo.glpi.handler.GlpiResourceReference;
import ai.gebo.glpi.handler.IGGlpiContentManagementSystemHandler;
import ai.gebo.glpi.handler.IGGlpiVirtualFilesystemConsumingService;
import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.knlowledgebase.model.systems.GContentManagementSystemType;
import ai.gebo.system.ingestion.IGDocumentReferenceIngestionHandler;
import ai.gebo.systems.abstraction.layer.GAbstractRemoteVirtualFilesystemContentManagementSystemHandler;
import ai.gebo.systems.abstraction.layer.IGLocalPersistentFolderDiscoveryService;

public class GlpiContentManagementHandlerImpl extends
		GAbstractRemoteVirtualFilesystemContentManagementSystemHandler<GGlpiSystem, GGlpiProjectEndpoint, GlpiResourceReference, IGGlpiVirtualFilesystemConsumingService>
		implements IGGlpiContentManagementSystemHandler {

	/** The system type code, also the secrets context code of the GLPI credentials */
	public static final String GLPI_SYSTEM_TYPE = "GLPI";

	private static final GContentManagementSystemType systemType = new GContentManagementSystemType();

	static {
		systemType.setCode(GLPI_SYSTEM_TYPE);
		systemType.setDescription("GLPI service management");
	}

	public GlpiContentManagementHandlerImpl(IGBuildSystemHandlerRepositoryPattern buildSystemHandlerRepository,
			IGDocumentReferenceFactory contentHandler, GlpiSystemsConfigurationDao systemsDao,
			GlpiProjectEndpointConfigurationDao endpointsDao,
			IGLocalPersistentFolderDiscoveryService localFolderDiscoveryService,
			IGPersistentObjectManager persistentObjectManager, IGMessageBroker messageBroker,
			IGGlpiVirtualFilesystemConsumingService consumingService,
			IGDocumentReferenceIngestionHandler ingestionHandler) {
		super(buildSystemHandlerRepository, contentHandler, systemsDao, endpointsDao, localFolderDiscoveryService,
				persistentObjectManager, messageBroker, consumingService, ingestionHandler);
	}

	@Override
	public GContentManagementSystemType getHandledSystemType() {
		return systemType;
	}

	@Override
	public GGlpiProjectEndpoint findProjectEndPoint(String systemCode, String projectEndpointCode)
			throws GeboContentHandlerSystemException {
		return endpointsDao.findByCode(projectEndpointCode);
	}

	@Override
	public boolean isManagedEndpoint(GProjectEndpoint endpoint) {
		return endpoint instanceof GGlpiProjectEndpoint;
	}

	@Override
	public GGlpiSystem getSystem(GGlpiProjectEndpoint projectEndPoint) throws GeboContentHandlerSystemException {
		return configurationsDao.findByCode(projectEndPoint.getGlpiSystemCode());
	}

	@Override
	public String getMessagingModuleId() {
		return GStandardModulesConstraints.GLPI_MODULE;
	}
}
