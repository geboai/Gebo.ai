/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.architecture.contentsystems.abstraction.layer.test;

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

/**
 * Configuration class for the test content dispatcher system.
 * AI generated comments
 * 
 * This class extends the SingletonBuilder to create a properly configured 
 * GIOCModuleContentsDispatcher for test content management systems and project endpoints.
 * It integrates with Spring's dependency injection framework to provide 
 * a singleton instance of the content dispatcher.
 */
@Configuration
public class TestContentsDispatcherConfig extends SingletonBuilder<TestContentManagementSystem, TestProjectEndpoint>{

    /**
     * Constructor that initializes the TestContentsDispatcherConfig with all required dependencies.
     * 
     * @param handler The implementation of the test content system handler
     * @param broker Message broker for content-related communication
     * @param consumerFactory Factory for creating content consumers
     * @param evaluator Evaluator for content dispatching decisions
     * @param mapperFactory Factory for document reference enricher maps
     * @param docSnapshotRepo Repository for document reference snapshots
     * @param config Configuration for the content systems layer
     * @param documentsRepo Repository for document references
     * @param virtualFolderRepo Repository for virtual folders
     */
	public TestContentsDispatcherConfig(
			TestContentSystemHandlerImpl handler,
			IGMessageBroker broker, IGContentConsumerFactory consumerFactory, IGContentDispatchingEvaluator evaluator,
			IGDocumentReferenceEnricherMapFactory mapperFactory, DocumentReferenceSnapshotRepository docSnapshotRepo, ContentSystemsLayerConfiguration config, DocumentReferenceRepository documentsRepo, VirtualFolderRepository virtualFolderRepo) {
		super(handler, broker, consumerFactory, evaluator, mapperFactory,docSnapshotRepo, config, documentsRepo, virtualFolderRepo);
		
	}
	
	/**
	 * Provides a singleton instance of the GIOCModuleContentsDispatcher configured for test environments.
	 * This bean is qualified with "testContentsDispatcher" for specific injection.
	 * 
	 * @return A configured GIOCModuleContentsDispatcher for handling test content management systems and project endpoints
	 */
	@Bean
	@Scope("singleton")
	@Qualifier("testContentsDispatcher")
	public GIOCModuleContentsDispatcher<TestContentManagementSystem, TestProjectEndpoint> getTestContentsDispatcher() {
		return super.getDispatcher();
	}
}