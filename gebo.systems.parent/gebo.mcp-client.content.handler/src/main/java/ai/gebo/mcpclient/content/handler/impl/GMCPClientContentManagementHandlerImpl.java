/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.mcpclient.content.handler.impl;

import java.util.List;


import ai.gebo.application.messaging.IGMessageBroker;
import ai.gebo.architecture.buildsystems.abstraction.layer.IGBuildSystemHandlerRepositoryPattern;
import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.architecture.contenthandling.interfaces.IGDocumentReferenceFactory;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.knlowledgebase.model.systems.GContentManagementSystemType;
import ai.gebo.knlowledgebase.model.systems.GSystemRole;
import ai.gebo.mcpclient.content.handler.GMCPClientSystem;
import ai.gebo.mcpclient.content.handler.IGMCPClientContentManagementHandler;
import ai.gebo.mcpclient.content.handler.IGMCPClientVirtualFilesystemConsumingService;
import ai.gebo.mcpclient.content.handler.MCPClientProjectEndpoint;
import ai.gebo.mcpclient.content.handler.repository.MCPClientProjectEndpointRepository;
import ai.gebo.system.ingestion.IGDocumentReferenceIngestionHandler;
import ai.gebo.systems.abstraction.layer.GAbstractRemoteVirtualFilesystemContentManagementSystemHandler;
import ai.gebo.systems.abstraction.layer.IGLocalPersistentFolderDiscoveryService;
import ai.gebo.systems.abstraction.layer.IGProjectEndpointRuntimeConfigurationDao;

/**
 * Content management handler that exposes an MCP server as a remote virtual
 * filesystem. The MCP system is a singleton (served by
 * {@link GMCPClientConfigurationDao}); each endpoint references its own MCP
 * client configuration by code.
 */
public class GMCPClientContentManagementHandlerImpl extends
		GAbstractRemoteVirtualFilesystemContentManagementSystemHandler<GMCPClientSystem, MCPClientProjectEndpoint, ai.gebo.mcpclient.content.handler.impl.model.MCPClientResourceReference, IGMCPClientVirtualFilesystemConsumingService>
		implements IGMCPClientContentManagementHandler {

	/** Content management system type code for MCP client integrations. */
	public static final String MCP_CLIENT = GMCPClientConfigurationDao.MCP_CLIENT_TYPE;

	private static final GContentManagementSystemType systemType = new GContentManagementSystemType();

	static {
		systemType.setCode(MCP_CLIENT);
		systemType.setDescription("MCP server");
		systemType.setCapabilities(List.of(GSystemRole.SOURCE_MANAGEMENT, GSystemRole.DOCUMENTS_MANAGEMENT));
	}

	/**
	 * @param buildSystemHandlerRepository build system handler repository
	 * @param contentHandler               document reference factory
	 * @param configurationsDao            singleton MCP system configuration DAO
	 * @param endpointRepo                 repository of MCP project endpoints
	 * @param localFolderDiscoveryService  local persistent folder discovery
	 * @param persistentObjectManager      persistent object manager
	 * @param messageBroker                message broker
	 * @param consumingService             MCP virtual filesystem consuming service
	 * @param ingestionHandler             document ingestion handler
	 */
	public GMCPClientContentManagementHandlerImpl(IGBuildSystemHandlerRepositoryPattern buildSystemHandlerRepository,
			IGDocumentReferenceFactory contentHandler, GMCPClientConfigurationDao configurationsDao,
			MCPClientProjectEndpointRepository endpointRepo,
			IGLocalPersistentFolderDiscoveryService localFolderDiscoveryService,
			IGPersistentObjectManager persistentObjectManager, IGMessageBroker messageBroker,
			IGMCPClientVirtualFilesystemConsumingService consumingService,
			IGDocumentReferenceIngestionHandler ingestionHandler) {
		super(buildSystemHandlerRepository, contentHandler, configurationsDao,
				IGProjectEndpointRuntimeConfigurationDao.of(endpointRepo), localFolderDiscoveryService,
				persistentObjectManager, messageBroker, consumingService, ingestionHandler);
	}

	@Override
	public GContentManagementSystemType getHandledSystemType() {
		return systemType;
	}

	@Override
	public MCPClientProjectEndpoint findProjectEndPoint(String systemCode, String projectEndpointCode)
			throws GeboContentHandlerSystemException {
		return endpointsDao.findByCode(projectEndpointCode);
	}

	@Override
	public boolean isManagedEndpoint(GProjectEndpoint endpoint) {
		return endpoint instanceof MCPClientProjectEndpoint;
	}

	@Override
	public GMCPClientSystem getSystem(MCPClientProjectEndpoint projectEndPoint) throws GeboContentHandlerSystemException {
		// MCP has a single, static system (no per-instance system record).
		List<GMCPClientSystem> configs = configurationsDao.getConfigurations();
		return configs.isEmpty() ? null : configs.get(0);
	}

	@Override
	public String getMessagingModuleId() {
		return GMCPClientRemoteVirtualFilesystemConsumingServiceImpl.MCP_CLIENT_MODULE;
	}
}
