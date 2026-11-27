/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */
package ai.gebo.glpi.handler.impl;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ai.gebo.application.messaging.IGMessageBroker;
import ai.gebo.architecture.buildsystems.abstraction.layer.IGBuildSystemHandlerRepositoryPattern;
import ai.gebo.architecture.contenthandling.interfaces.IGDocumentReferenceFactory;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.glpi.handler.IGGlpiContentManagementSystemHandler;
import ai.gebo.glpi.handler.IGGlpiVirtualFilesystemConsumingService;
import ai.gebo.system.ingestion.IGDocumentReferenceIngestionHandler;
import ai.gebo.systems.abstraction.layer.IGLocalPersistentFolderDiscoveryService;

@Configuration
public class GGlpiDefaultBeansConfig {

	@Bean
	@ConditionalOnMissingBean(IGGlpiVirtualFilesystemConsumingService.class)
	public IGGlpiVirtualFilesystemConsumingService glpiVirtualFilesystemConsumingService(
			IGDocumentReferenceFactory documentFactory, GlpiApiClientFactory apiClientFactory) {
		return new GGlpiRemoteVirtualFilesystemConsumingServiceImpl(documentFactory, apiClientFactory);
	}

	@Bean
	@ConditionalOnMissingBean(IGGlpiContentManagementSystemHandler.class)
	public IGGlpiContentManagementSystemHandler glpiContentManagementHandler(
			IGBuildSystemHandlerRepositoryPattern buildSystemHandlerRepository,
			IGDocumentReferenceFactory contentHandler, GlpiSystemsConfigurationDao systemsDao,
			GlpiProjectEndpointConfigurationDao endpointsDao,
			IGLocalPersistentFolderDiscoveryService localFolderDiscoveryService,
			IGPersistentObjectManager persistentObjectManager, IGMessageBroker messageBroker,
			IGGlpiVirtualFilesystemConsumingService consumingService,
			IGDocumentReferenceIngestionHandler ingestionHandler) {
		return new GlpiContentManagementHandlerImpl(buildSystemHandlerRepository, contentHandler, systemsDao,
				endpointsDao, localFolderDiscoveryService, persistentObjectManager, messageBroker, consumingService,
				ingestionHandler);
	}
}
