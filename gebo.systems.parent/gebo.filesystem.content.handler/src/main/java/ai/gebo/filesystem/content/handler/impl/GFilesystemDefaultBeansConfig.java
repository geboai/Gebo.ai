/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.filesystem.content.handler.impl;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ai.gebo.application.messaging.IGMessageBroker;
import ai.gebo.architecture.buildsystems.abstraction.layer.IGBuildSystemHandlerRepositoryPattern;
import ai.gebo.architecture.contenthandling.interfaces.IGDocumentReferenceFactory;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.filesystem.content.handler.GFilesystemContentManagementSystem;
import ai.gebo.filesystem.content.handler.GFilesystemProjectEndpoint;
import ai.gebo.filesystem.content.handler.IGFilesystemContentManagementSystemHandler;
import ai.gebo.system.ingestion.IGDocumentReferenceIngestionHandler;
import ai.gebo.systems.abstraction.layer.IGContentManagementSystemConfigurationDao;
import ai.gebo.systems.abstraction.layer.IGLocalPersistentFolderDiscoveryService;
import ai.gebo.systems.abstraction.layer.IGProjectEndpointRuntimeConfigurationDao;

/**
 * Registers the default filesystem content handler as a bean that a
 * downstream module can override by supplying its own bean of the
 * corresponding interface.
 */
@Configuration
public class GFilesystemDefaultBeansConfig {

	@Bean
	@ConditionalOnMissingBean(IGFilesystemContentManagementSystemHandler.class)
	public IGFilesystemContentManagementSystemHandler filesystemContentManagementHandler(
			IGBuildSystemHandlerRepositoryPattern buildSystemHandlerRepository,
			IGDocumentReferenceFactory contentHandler,
			IGContentManagementSystemConfigurationDao<GFilesystemContentManagementSystem> configurationsDao,
			IGProjectEndpointRuntimeConfigurationDao<GFilesystemProjectEndpoint> endpointsDao,
			IGLocalPersistentFolderDiscoveryService localFolderDiscoveryService,
			IGPersistentObjectManager persistentObjectManager, IGMessageBroker messageBroker,
			IGDocumentReferenceIngestionHandler ingestionHandler) {
		return new GFilesystemContentManagementSystemHandlerImpl(buildSystemHandlerRepository, contentHandler,
				configurationsDao, endpointsDao, localFolderDiscoveryService, persistentObjectManager, messageBroker,
				ingestionHandler);
	}

}
