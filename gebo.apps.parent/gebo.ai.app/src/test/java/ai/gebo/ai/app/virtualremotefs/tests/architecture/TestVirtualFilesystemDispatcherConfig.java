/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.ai.app.virtualremotefs.tests.architecture;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import ai.gebo.application.messaging.IGMessageBroker;
import ai.gebo.architecture.contenthandling.interfaces.IGContentConsumerFactory;
import ai.gebo.atlassian.confluence.handler.GConfluenceProjectEndpoint;
import ai.gebo.atlassian.confluence.handler.GConfluenceSystem;
import ai.gebo.knowledgebase.repositories.DocumentReferenceRepository;
import ai.gebo.knowledgebase.repositories.DocumentReferenceSnapshotRepository;
import ai.gebo.knowledgebase.repositories.VirtualFolderRepository;
import ai.gebo.systems.abstraction.layer.GIOCModuleContentsDispatcher.SingletonBuilder;
import ai.gebo.systems.abstraction.layer.GIOCModuleContentsDispatcher;
import ai.gebo.systems.abstraction.layer.IGContentDispatchingEvaluator;
import ai.gebo.systems.abstraction.layer.IGContentManagementSystemHandler;
import ai.gebo.systems.abstraction.layer.IGDocumentReferenceEnricherMapFactory;
import ai.gebo.systems.abstraction.layer.config.ContentSystemsLayerConfiguration;

/**
 * Configuration class for setting up the test virtual filesystem dispatcher.
 * 
 * <p>
 * This class extends {@link SingletonBuilder} to provide a configuration for
 * managing content systems layer specifically for testing purposes within a
 * virtual remote filesystem setup.
 * </p>
 * 
 * @version 1.0
 * @author AI generated comments
 */
@Configuration
public class TestVirtualFilesystemDispatcherConfig
		extends SingletonBuilder<TestVirtualRemoteSystem, TestVirtualRemoteProjectEndpoint> {

	/**
	 * Constructs a new configuration instance for the test virtual filesystem
	 * dispatcher.
	 * 
	 * @param handler                           the content management system handler
	 * @param broker                            the message broker
	 * @param consumerFactory                   the content consumer factory
	 * @param evaluator                         the content dispatching evaluator
	 * @param mapperFactory                     the document reference enricher map factory
	 * @param documentsReferenceSnapshotRepository the document reference snapshot repository
	 * @param configuration                     the content systems layer configuration
	 * @param documentReferenceRepository       the document reference repository
	 * @param virtualFolderRepository           the virtual folder repository
	 */
	public TestVirtualFilesystemDispatcherConfig(
			IGContentManagementSystemHandler<TestVirtualRemoteSystem, TestVirtualRemoteProjectEndpoint> handler,
			IGMessageBroker broker, IGContentConsumerFactory consumerFactory, IGContentDispatchingEvaluator evaluator,
			IGDocumentReferenceEnricherMapFactory mapperFactory,
			DocumentReferenceSnapshotRepository documentsReferenceSnapshotRepository,
			ContentSystemsLayerConfiguration configuration, DocumentReferenceRepository documentReferenceRepository,
			VirtualFolderRepository virtualFolderRepository) {
		super(handler, broker, consumerFactory, evaluator, mapperFactory, documentsReferenceSnapshotRepository,
				configuration, documentReferenceRepository, virtualFolderRepository);

	}

	/**
	 * Creates a singleton bean for the test remote virtual filesystem dispatcher.
	 * 
	 * <p>
	 * It leverages the dispatcher from the superclass to manage content system
	 * operations within a virtual remote filesystem environment for testing
	 * purposes.
	 * </p>
	 * 
	 * @return the initialized {@link GIOCModuleContentsDispatcher} instance
	 */
	@Bean
	@Scope("singleton")
	public GIOCModuleContentsDispatcher<TestVirtualRemoteSystem, TestVirtualRemoteProjectEndpoint> getTestRemoteVirtualFilesystemDispatcher() {

		// Utilize the superclass's dispatcher setup
		return super.getDispatcher();
	}

}