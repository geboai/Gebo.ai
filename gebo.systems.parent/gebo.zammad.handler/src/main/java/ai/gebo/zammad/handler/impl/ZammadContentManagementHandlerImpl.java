/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.zammad.handler.impl;

import ai.gebo.application.messaging.IGMessageBroker;
import ai.gebo.application.messaging.model.GStandardModulesConstraints;
import ai.gebo.architecture.buildsystems.abstraction.layer.IGBuildSystemHandlerRepositoryPattern;
import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.architecture.contenthandling.interfaces.IGDocumentReferenceFactory;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.knlowledgebase.model.systems.GContentManagementSystemType;
import ai.gebo.zammad.handler.GZammadProjectEndpoint;
import ai.gebo.zammad.handler.GZammadSystem;
import ai.gebo.zammad.handler.IGZammadContentManagementSystemHandler;
import ai.gebo.zammad.handler.IGZammadVirtualFilesystemConsumingService;
import ai.gebo.zammad.handler.ZammadResourceReference;
import ai.gebo.system.ingestion.IGDocumentReferenceIngestionHandler;
import ai.gebo.systems.abstraction.layer.GAbstractRemoteVirtualFilesystemContentManagementSystemHandler;
import ai.gebo.systems.abstraction.layer.IGLocalPersistentFolderDiscoveryService;

/**
 * The Zammad content handler.
 */
public class ZammadContentManagementHandlerImpl extends
		GAbstractRemoteVirtualFilesystemContentManagementSystemHandler<GZammadSystem, GZammadProjectEndpoint, ZammadResourceReference, IGZammadVirtualFilesystemConsumingService>
		implements IGZammadContentManagementSystemHandler {
	public static final String ZAMMAD = "ZAMMAD";
	private static final GContentManagementSystemType systemType = new GContentManagementSystemType();
	static {
		systemType.setCode(ZAMMAD);
		systemType.setDescription("Zammad helpdesk");
	}

	public ZammadContentManagementHandlerImpl(IGBuildSystemHandlerRepositoryPattern buildSystemHandlerRepository,
			IGDocumentReferenceFactory contentHandler, ZammadSystemsConfigurationDao systemsDao,
			ZammadProjectEndpointConfigurationDao endpointsDao,
			IGLocalPersistentFolderDiscoveryService localFolderDiscoveryService,
			IGPersistentObjectManager persistentObjectManager, IGMessageBroker messageBroker,
			IGZammadVirtualFilesystemConsumingService consumingService,
			IGDocumentReferenceIngestionHandler ingestionHandler) {
		super(buildSystemHandlerRepository, contentHandler, systemsDao, endpointsDao, localFolderDiscoveryService,
				persistentObjectManager, messageBroker, consumingService, ingestionHandler);
	}

	@Override
	public GContentManagementSystemType getHandledSystemType() {
		return systemType;
	}

	@Override
	public GZammadProjectEndpoint findProjectEndPoint(String systemCode, String projectEndpointCode)
			throws GeboContentHandlerSystemException {
		return endpointsDao.findByCode(projectEndpointCode);
	}

	@Override
	public boolean isManagedEndpoint(GProjectEndpoint endpoint) {
		return endpoint instanceof GZammadProjectEndpoint;
	}

	@Override
	public GZammadSystem getSystem(GZammadProjectEndpoint projectEndPoint) throws GeboContentHandlerSystemException {
		return configurationsDao.findByCode(projectEndPoint.getZammadSystemCode());
	}

	@Override
	public String getMessagingModuleId() {
		return GStandardModulesConstraints.ZAMMAD_MODULE;
	}
}
