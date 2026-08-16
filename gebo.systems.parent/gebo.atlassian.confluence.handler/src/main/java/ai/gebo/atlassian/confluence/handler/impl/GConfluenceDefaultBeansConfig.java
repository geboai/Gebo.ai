/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.atlassian.confluence.handler.impl;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ai.gebo.application.messaging.IGMessageBroker;
import ai.gebo.architecture.buildsystems.abstraction.layer.IGBuildSystemHandlerRepositoryPattern;
import ai.gebo.architecture.contenthandling.interfaces.IGDocumentReferenceFactory;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.atlassian.confluence.handler.IGConfluenceContentManagementHandler;
import ai.gebo.atlassian.confluence.handler.IGConfluenceVirtualFilesystemConsumingService;
import ai.gebo.atlassian.confluence.handler.repositories.ConfluenceProjectEndpointRepository;
import ai.gebo.atlassian.confluence.handler.repositories.ConfluenceSystemRepository;
import ai.gebo.secrets.services.IGeboSecretsAccessService;
import ai.gebo.system.ingestion.IGDocumentReferenceIngestionHandler;
import ai.gebo.systems.abstraction.layer.IGLocalPersistentFolderDiscoveryService;

/**
 * Registers the default Confluence content handlers as beans that a
 * downstream module can override by supplying its own bean of the
 * corresponding interface.
 */
@Configuration
public class GConfluenceDefaultBeansConfig {

	@Bean
	@ConditionalOnMissingBean(IGConfluenceVirtualFilesystemConsumingService.class)
	public IGConfluenceVirtualFilesystemConsumingService confluenceVirtualFilesystemConsumingService(
			IGDocumentReferenceFactory documentFactory, ConfluenceConnectionFactory connectionFactory) {
		return new GConfluenceRemoteVirtualFilesystemConsumingServiceImpl(documentFactory, connectionFactory);
	}

	@Bean
	@ConditionalOnMissingBean(IGConfluenceContentManagementHandler.class)
	public IGConfluenceContentManagementHandler confluenceContentManagementHandler(
			IGBuildSystemHandlerRepositoryPattern buildSystemHandlerRepository,
			IGDocumentReferenceFactory contentHandler, ConfluenceSystemRepository systemsRepo,
			ConfluenceProjectEndpointRepository endpointRepo,
			IGLocalPersistentFolderDiscoveryService localFolderDiscoveryService,
			IGPersistentObjectManager persistentObjectManager, IGMessageBroker messageBroker,
			IGeboSecretsAccessService secretsService, IGConfluenceVirtualFilesystemConsumingService consumingService,
			IGDocumentReferenceIngestionHandler ingestionHandler) {
		return new ConfluenceContentManagementHandlerImpl(buildSystemHandlerRepository, contentHandler, systemsRepo,
				endpointRepo, localFolderDiscoveryService, persistentObjectManager, messageBroker, secretsService,
				consumingService, ingestionHandler);
	}

}
