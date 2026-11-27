/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.zammad.handler.impl;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ai.gebo.application.messaging.IGMessageBroker;
import ai.gebo.architecture.buildsystems.abstraction.layer.IGBuildSystemHandlerRepositoryPattern;
import ai.gebo.architecture.contenthandling.interfaces.IGDocumentReferenceFactory;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.zammad.handler.IGZammadContentManagementSystemHandler;
import ai.gebo.zammad.handler.IGZammadVirtualFilesystemConsumingService;
import ai.gebo.system.ingestion.IGDocumentReferenceIngestionHandler;
import ai.gebo.systems.abstraction.layer.IGLocalPersistentFolderDiscoveryService;

/**
 * The default Zammad consuming service and handler, replaceable by beans of
 * the same interfaces.
 */
@Configuration
public class GZammadDefaultBeansConfig {
	@Bean
	@ConditionalOnMissingBean(IGZammadVirtualFilesystemConsumingService.class)
	public IGZammadVirtualFilesystemConsumingService zammadVirtualFilesystemConsumingService(
			IGDocumentReferenceFactory documentFactory, ZammadApiClientFactory clientFactory) {
		return new GZammadRemoteVirtualFilesystemConsumingServiceImpl(documentFactory, clientFactory);
	}

	@Bean
	@ConditionalOnMissingBean(IGZammadContentManagementSystemHandler.class)
	public IGZammadContentManagementSystemHandler zammadContentManagementHandler(
			IGBuildSystemHandlerRepositoryPattern buildSystemHandlerRepository,
			IGDocumentReferenceFactory contentHandler, ZammadSystemsConfigurationDao systemsDao,
			ZammadProjectEndpointConfigurationDao endpointsDao,
			IGLocalPersistentFolderDiscoveryService localFolderDiscoveryService,
			IGPersistentObjectManager persistentObjectManager, IGMessageBroker messageBroker,
			IGZammadVirtualFilesystemConsumingService consumingService,
			IGDocumentReferenceIngestionHandler ingestionHandler) {
		return new ZammadContentManagementHandlerImpl(buildSystemHandlerRepository, contentHandler, systemsDao,
				endpointsDao, localFolderDiscoveryService, persistentObjectManager, messageBroker, consumingService,
				ingestionHandler);
	}
}
