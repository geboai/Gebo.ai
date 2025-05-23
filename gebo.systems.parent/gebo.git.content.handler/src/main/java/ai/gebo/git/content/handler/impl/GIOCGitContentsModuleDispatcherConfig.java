/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.git.content.handler.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import ai.gebo.application.messaging.IGMessageBroker;
import ai.gebo.architecture.contenthandling.interfaces.IGContentConsumerFactory;
import ai.gebo.git.content.handler.GGitContentManagementSystem;
import ai.gebo.git.content.handler.GGitProjectEndpoint;
import ai.gebo.git.content.handler.IGBaseGitContentManagementSystemHandler;
import ai.gebo.knowledgebase.repositories.DocumentReferenceRepository;
import ai.gebo.knowledgebase.repositories.DocumentReferenceSnapshotRepository;
import ai.gebo.knowledgebase.repositories.VirtualFolderRepository;
import ai.gebo.systems.abstraction.layer.GIOCModuleContentsDispatcher;
import ai.gebo.systems.abstraction.layer.GIOCModuleContentsDispatcher.SingletonBuilder;
import ai.gebo.systems.abstraction.layer.IGContentDispatchingEvaluator;
import ai.gebo.systems.abstraction.layer.IGDocumentReferenceEnricherMapFactory;
import ai.gebo.systems.abstraction.layer.config.ContentSystemsLayerConfiguration;

/**
 * AI generated comments
 * 
 * Configuration class for Git content module dispatcher.
 * This class extends SingletonBuilder to create a singleton instance of
 * GIOCModuleContentsDispatcher specialized for Git content management.
 * It provides the Spring bean configuration for Git content handling capabilities.
 */
@Configuration
public class GIOCGitContentsModuleDispatcherConfig
		extends SingletonBuilder<GGitContentManagementSystem, GGitProjectEndpoint> {

	/**
	 * Constructor for the Git contents module dispatcher configuration.
	 * 
	 * @param handler The handler for Git content management system operations
	 * @param broker Message broker for communication between components
	 * @param consumerFactory Factory for creating content consumers
	 * @param evaluator Evaluator for content dispatching decisions
	 * @param mapperFactory Factory for document reference enricher maps
	 * @param docSnapshotRepo Repository for document reference snapshots
	 * @param config Configuration for the content systems layer
	 * @param documentsRepo Repository for document references
	 * @param virtualFolderRepo Repository for virtual folders
	 */
	public GIOCGitContentsModuleDispatcherConfig(
			IGBaseGitContentManagementSystemHandler<GGitContentManagementSystem, GGitProjectEndpoint> handler,
			IGMessageBroker broker, IGContentConsumerFactory consumerFactory, IGContentDispatchingEvaluator evaluator,
			IGDocumentReferenceEnricherMapFactory mapperFactory, DocumentReferenceSnapshotRepository docSnapshotRepo, ContentSystemsLayerConfiguration config, DocumentReferenceRepository documentsRepo, VirtualFolderRepository virtualFolderRepo) {
		super(handler, broker, consumerFactory, evaluator, mapperFactory,docSnapshotRepo, config, documentsRepo, virtualFolderRepo);

	}

	/**
	 * Creates and provides a singleton instance of the Git contents module dispatcher.
	 * This bean is responsible for dispatching Git content related operations.
	 * 
	 * @return A singleton instance of GIOCModuleContentsDispatcher for Git content
	 */
	@Bean
	@Scope("singleton")
	@Qualifier("gitContentsModuleDispatcher")
	public GIOCModuleContentsDispatcher<GGitContentManagementSystem, GGitProjectEndpoint> getGitContentsModuleDispatcher() {

		return super.getDispatcher();
	}
}