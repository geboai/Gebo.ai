/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.awss3.content.handler.impl;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ai.gebo.application.messaging.IGMessageBroker;
import ai.gebo.architecture.buildsystems.abstraction.layer.IGBuildSystemHandlerRepositoryPattern;
import ai.gebo.architecture.contenthandling.interfaces.IGDocumentReferenceFactory;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.awss3.content.handler.IGAwsS3SystemContentHandler;
import ai.gebo.awss3.content.handler.IGAwsS3VirtualFilesystemConsumingService;
import ai.gebo.system.ingestion.IGDocumentReferenceIngestionHandler;
import ai.gebo.systems.abstraction.layer.IGLocalPersistentFolderDiscoveryService;

/**
 * Registers the default AWS S3 content handlers as beans that a downstream
 * module can override by supplying its own bean of the corresponding
 * interface.
 */
@Configuration
public class GAwsS3DefaultBeansConfig {

	@Bean
	@ConditionalOnMissingBean(IGAwsS3VirtualFilesystemConsumingService.class)
	public IGAwsS3VirtualFilesystemConsumingService awsS3VirtualFilesystemConsumingService(
			IGDocumentReferenceFactory documentFactory, AwsS3ConnectionFactory connectionFactory) {
		return new AwsS3VirtualFilesystemConsumingService(documentFactory, connectionFactory);
	}

	@Bean
	@ConditionalOnMissingBean(IGAwsS3SystemContentHandler.class)
	public IGAwsS3SystemContentHandler awsS3SystemContentHandler(
			IGBuildSystemHandlerRepositoryPattern buildSystemHandlerRepository,
			IGDocumentReferenceFactory contentHandler, AwsS3SystemsDao configurationsDao,
			AwsS3ProjectEndpointDao endpointsDao,
			IGLocalPersistentFolderDiscoveryService localFolderDiscoveryService,
			IGPersistentObjectManager persistentObjectManager, IGMessageBroker messageBroker,
			IGAwsS3VirtualFilesystemConsumingService consumingService,
			IGDocumentReferenceIngestionHandler ingestionHandler) {
		return new GAwsS3SystemContentHandlerImpl(buildSystemHandlerRepository, contentHandler, configurationsDao,
				endpointsDao, localFolderDiscoveryService, persistentObjectManager, messageBroker, consumingService,
				ingestionHandler);
	}

}
