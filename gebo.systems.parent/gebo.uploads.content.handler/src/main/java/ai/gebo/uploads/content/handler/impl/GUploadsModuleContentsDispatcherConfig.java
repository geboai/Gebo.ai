/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.uploads.content.handler.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import ai.gebo.application.messaging.IGMessageBroker;
import ai.gebo.architecture.contenthandling.interfaces.IGContentConsumerFactory;
import ai.gebo.knowledgebase.repositories.DocumentReferenceRepository;
import ai.gebo.knowledgebase.repositories.DocumentReferenceSnapshotRepository;
import ai.gebo.knowledgebase.repositories.VirtualFolderRepository;
import ai.gebo.systems.abstraction.layer.GIOCModuleContentsDispatcher;
import ai.gebo.systems.abstraction.layer.GIOCModuleContentsDispatcher.SingletonBuilder;
import ai.gebo.systems.abstraction.layer.IGContentDispatchingEvaluator;
import ai.gebo.systems.abstraction.layer.IGDocumentReferenceEnricherMapFactory;
import ai.gebo.systems.abstraction.layer.config.ContentSystemsLayerConfiguration;
import ai.gebo.uploads.content.handler.GUploadsContentManagementSystem;
import ai.gebo.uploads.content.handler.GUploadsProjectEndpoint;
import ai.gebo.uploads.content.handler.IGUploadsContentManagementSystemHandler;

/**
 * AI generated comments
 * Configuration class for the Uploads Module Contents Dispatcher.
 * This class extends SingletonBuilder to create a singleton instance of
 * a content dispatcher specifically for uploads handling.
 */
@Configuration
public class GUploadsModuleContentsDispatcherConfig
		extends SingletonBuilder<GUploadsContentManagementSystem, GUploadsProjectEndpoint> {

	/**
	 * Constructor for GUploadsModuleContentsDispatcherConfig.
	 * Initializes the dispatcher with all necessary dependencies for handling uploads.
	 * 
	 * @param handler The handler for uploads content management system
	 * @param broker Message broker for communication
	 * @param consumerFactory Factory for content consumers
	 * @param evaluator Evaluator for content dispatching decisions
	 * @param mapperFactory Factory for document reference enrichers
	 * @param docSnapshotRepo Repository for document reference snapshots
	 * @param config Configuration for the content systems layer
	 * @param documentsRepo Repository for document references
	 * @param virtualFolderRepo Repository for virtual folders
	 */
	public GUploadsModuleContentsDispatcherConfig(IGUploadsContentManagementSystemHandler handler,
			IGMessageBroker broker, IGContentConsumerFactory consumerFactory, IGContentDispatchingEvaluator evaluator,
			IGDocumentReferenceEnricherMapFactory mapperFactory, DocumentReferenceSnapshotRepository docSnapshotRepo, ContentSystemsLayerConfiguration config, DocumentReferenceRepository documentsRepo, VirtualFolderRepository virtualFolderRepo) {
		super(handler, broker, consumerFactory, evaluator, mapperFactory,docSnapshotRepo, config, documentsRepo, virtualFolderRepo);
	}

	/**
	 * Creates and returns a singleton bean for the uploads content dispatcher.
	 * This dispatcher is responsible for managing the flow of upload content through the system.
	 * 
	 * @return A singleton instance of GIOCModuleContentsDispatcher configured for uploads
	 */
	@Bean
	@Scope("singleton")
	@Qualifier("uploadsContentsDispatcher")
	public GIOCModuleContentsDispatcher<GUploadsContentManagementSystem, GUploadsProjectEndpoint> getUploadsContentsDispatcher() {
		return super.getDispatcher();
	}
}