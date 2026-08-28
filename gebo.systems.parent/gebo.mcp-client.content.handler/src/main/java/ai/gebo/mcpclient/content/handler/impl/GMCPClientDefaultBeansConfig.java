/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.mcpclient.content.handler.impl;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ai.gebo.application.messaging.IGMessageBroker;
import ai.gebo.architecture.buildsystems.abstraction.layer.IGBuildSystemHandlerRepositoryPattern;
import ai.gebo.architecture.contenthandling.interfaces.IGDocumentReferenceFactory;
import ai.gebo.architecture.mcpclients.service.McpClientManagementService;
import ai.gebo.architecture.mcpclients.service.impl.McpClientPool;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.mcpclient.content.handler.IGMCPClientContentManagementHandler;
import ai.gebo.mcpclient.content.handler.IGMCPClientVirtualFilesystemConsumingService;
import ai.gebo.mcpclient.content.handler.repository.MCPClientProjectEndpointRepository;
import ai.gebo.system.ingestion.IGDocumentReferenceIngestionHandler;
import ai.gebo.systems.abstraction.layer.IGLocalPersistentFolderDiscoveryService;

/**
 * Registers the default MCP client content handlers as beans that a
 * downstream module can override by supplying its own bean of the
 * corresponding interface.
 */
@Configuration
public class GMCPClientDefaultBeansConfig {

	@Bean
	@ConditionalOnMissingBean(IGMCPClientVirtualFilesystemConsumingService.class)
	public IGMCPClientVirtualFilesystemConsumingService mcpClientVirtualFilesystemConsumingService(
			IGDocumentReferenceFactory documentFactory, McpClientPool clientPool,
			McpClientManagementService managementService) {
		return new GMCPClientRemoteVirtualFilesystemConsumingServiceImpl(documentFactory, clientPool,
				managementService);
	}

	@Bean
	@ConditionalOnMissingBean(IGMCPClientContentManagementHandler.class)
	public IGMCPClientContentManagementHandler mcpClientContentManagementHandler(
			IGBuildSystemHandlerRepositoryPattern buildSystemHandlerRepository,
			IGDocumentReferenceFactory contentHandler, GMCPClientConfigurationDao configurationsDao,
			MCPClientProjectEndpointRepository endpointRepo,
			IGLocalPersistentFolderDiscoveryService localFolderDiscoveryService,
			IGPersistentObjectManager persistentObjectManager, IGMessageBroker messageBroker,
			IGMCPClientVirtualFilesystemConsumingService consumingService,
			IGDocumentReferenceIngestionHandler ingestionHandler) {
		return new GMCPClientContentManagementHandlerImpl(buildSystemHandlerRepository, contentHandler,
				configurationsDao, endpointRepo, localFolderDiscoveryService, persistentObjectManager, messageBroker,
				consumingService, ingestionHandler);
	}

}
