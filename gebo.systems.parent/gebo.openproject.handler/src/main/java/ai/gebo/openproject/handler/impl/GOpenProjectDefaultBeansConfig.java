/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.openproject.handler.impl;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ai.gebo.application.messaging.IGMessageBroker;
import ai.gebo.architecture.buildsystems.abstraction.layer.IGBuildSystemHandlerRepositoryPattern;
import ai.gebo.architecture.contenthandling.interfaces.IGDocumentReferenceFactory;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.openproject.handler.IGOpenProjectContentManagementSystemHandler;
import ai.gebo.openproject.handler.IGOpenProjectVirtualFilesystemConsumingService;
import ai.gebo.system.ingestion.IGDocumentReferenceIngestionHandler;
import ai.gebo.systems.abstraction.layer.IGLocalPersistentFolderDiscoveryService;

/**
 * The default OpenProject consuming service and handler, replaceable by beans of
 * the same interfaces.
 */
@Configuration
public class GOpenProjectDefaultBeansConfig {
	@Bean
	@ConditionalOnMissingBean(IGOpenProjectVirtualFilesystemConsumingService.class)
	public IGOpenProjectVirtualFilesystemConsumingService openprojectVirtualFilesystemConsumingService(
			IGDocumentReferenceFactory documentFactory, OpenProjectApiClientFactory clientFactory) {
		return new GOpenProjectRemoteVirtualFilesystemConsumingServiceImpl(documentFactory, clientFactory);
	}

	@Bean
	@ConditionalOnMissingBean(IGOpenProjectContentManagementSystemHandler.class)
	public IGOpenProjectContentManagementSystemHandler openprojectContentManagementHandler(
			IGBuildSystemHandlerRepositoryPattern buildSystemHandlerRepository,
			IGDocumentReferenceFactory contentHandler, OpenProjectSystemsConfigurationDao systemsDao,
			OpenProjectProjectEndpointConfigurationDao endpointsDao,
			IGLocalPersistentFolderDiscoveryService localFolderDiscoveryService,
			IGPersistentObjectManager persistentObjectManager, IGMessageBroker messageBroker,
			IGOpenProjectVirtualFilesystemConsumingService consumingService,
			IGDocumentReferenceIngestionHandler ingestionHandler) {
		return new OpenProjectContentManagementHandlerImpl(buildSystemHandlerRepository, contentHandler, systemsDao,
				endpointsDao, localFolderDiscoveryService, persistentObjectManager, messageBroker, consumingService,
				ingestionHandler);
	}
}
