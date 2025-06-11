/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.integration.content.handler.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import ai.gebo.application.messaging.IGMessageBroker;
import ai.gebo.architecture.contenthandling.interfaces.IGContentConsumerFactory;
import ai.gebo.integration.content.handler.GIntegrationContentSystem;
import ai.gebo.integration.content.handler.GIntegrationProjectEndpoint;
import ai.gebo.integration.content.handler.IGIntegrationSystemContentHandler;
import ai.gebo.knowledgebase.repositories.DocumentReferenceRepository;
import ai.gebo.knowledgebase.repositories.DocumentReferenceSnapshotRepository;
import ai.gebo.knowledgebase.repositories.VirtualFolderRepository;
import ai.gebo.systems.abstraction.layer.GIOCModuleContentsDispatcher;
import ai.gebo.systems.abstraction.layer.GIOCModuleContentsDispatcher.SingletonBuilder;
import ai.gebo.systems.abstraction.layer.IGContentDispatchingEvaluator;
import ai.gebo.systems.abstraction.layer.IGDocumentReferenceEnricherMapFactory;
import ai.gebo.systems.abstraction.layer.config.ContentSystemsLayerConfiguration;

/**
 * AI generated comments Configuration class responsible for setting up the
 * Google Drive contents dispatcher. This class extends the SingletonBuilder to
 * create a dispatcher for handling Google Drive system and project endpoint
 * interactions.
 */
@Configuration
public class GIntegrationContentsDispatcherConfig
		extends SingletonBuilder<GIntegrationContentSystem, GIntegrationProjectEndpoint> {

	/**
	 * Constructor that initializes the 3rd party integration contents dispatcher
	 * configuration.
	 * 
	 * @param handler           The 3rd party integration system content handler
	 *                          implementation
	 * @param broker            The message broker for dispatching events
	 * @param consumerFactory   Factory for creating content consumers
	 * @param evaluator         Evaluator for content dispatching decisions
	 * @param mapperFactory     Factory for document reference enricher maps
	 * @param docSnapshotRepo   Repository for document reference snapshots
	 * @param config            Configuration for the content systems layer
	 * @param documentsRepo     Repository for document references
	 * @param virtualFolderRepo Repository for virtual folders
	 */
	public GIntegrationContentsDispatcherConfig(IGIntegrationSystemContentHandler handler, IGMessageBroker broker,
			IGContentConsumerFactory consumerFactory, IGContentDispatchingEvaluator evaluator,
			IGDocumentReferenceEnricherMapFactory mapperFactory, DocumentReferenceSnapshotRepository docSnapshotRepo,
			ContentSystemsLayerConfiguration config, DocumentReferenceRepository documentsRepo,
			VirtualFolderRepository virtualFolderRepo) {
		super(handler, broker, consumerFactory, evaluator, mapperFactory, docSnapshotRepo, config, documentsRepo,
				virtualFolderRepo);

	}

	/**
	 * Creates and returns a singleton instance of the Google Drive contents
	 * dispatcher.
	 * 
	 * @return A configured GIOCModuleContentsDispatcher for Google Drive
	 */
	@Bean
	@Scope("singleton")
	@Qualifier("integrationContentsDispatcher")

	public GIOCModuleContentsDispatcher<GIntegrationContentSystem, GIntegrationProjectEndpoint> getIntegrationContentsDispatcher() {

		return super.getDispatcher();
	}
}