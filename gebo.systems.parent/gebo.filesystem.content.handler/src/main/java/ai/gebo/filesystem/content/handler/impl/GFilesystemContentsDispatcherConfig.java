/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.filesystem.content.handler.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import ai.gebo.application.messaging.IGMessageBroker;
import ai.gebo.application.messaging.workflow.IWorkflowRouter;
import ai.gebo.architecture.contenthandling.interfaces.IGContentConsumerFactory;
import ai.gebo.filesystem.content.handler.GFilesystemContentManagementSystem;
import ai.gebo.filesystem.content.handler.GFilesystemProjectEndpoint;
import ai.gebo.filesystem.content.handler.IGFilesystemContentManagementSystemHandler;
import ai.gebo.knowledgebase.repositories.DocumentReferenceRepository;
import ai.gebo.knowledgebase.repositories.DocumentReferenceSnapshotRepository;
import ai.gebo.knowledgebase.repositories.VirtualFolderRepository;
import ai.gebo.systems.abstraction.layer.GIOCModuleContentsDispatcher;
import ai.gebo.systems.abstraction.layer.GIOCModuleContentsDispatcher.SingletonBuilder;
import ai.gebo.systems.abstraction.layer.IGContentDispatchingEvaluator;
import ai.gebo.systems.abstraction.layer.IGDocumentReferenceEnricherMapFactory;

/**
 * AI generated comments
 * Configuration class for the Filesystem Contents Dispatcher.
 * This class extends SingletonBuilder to create and configure a GIOCModuleContentsDispatcher
 * specific to filesystem content management.
 */
@Configuration
public class GFilesystemContentsDispatcherConfig
		extends SingletonBuilder<GFilesystemContentManagementSystem, GFilesystemProjectEndpoint> {

	/**
	 * Constructor for GFilesystemContentsDispatcherConfig.
	 * Initializes the parent SingletonBuilder with all necessary dependencies
	 * for handling filesystem content.
	 *
	 * @param handler The filesystem content management system handler
	 * @param broker Message broker for communication
	 * @param consumerFactory Factory for creating content consumers
	 * @param evaluator Evaluator for content dispatching decisions
	 * @param mapperFactory Factory for document reference enrichers
	 * @param docSnapshotRepo Repository for document reference snapshots
	 * @param config Configuration for content systems layer
	 * @param documentsRepo Repository for document references
	 * @param virtualFolderRepo Repository for virtual folders
	 * @param  workflowRouter 
	 */
	public GFilesystemContentsDispatcherConfig(IGFilesystemContentManagementSystemHandler handler,
			IGMessageBroker broker, IGContentConsumerFactory consumerFactory, IGContentDispatchingEvaluator evaluator,
			IGDocumentReferenceEnricherMapFactory mapperFactory, DocumentReferenceSnapshotRepository docSnapshotRepo,
			 DocumentReferenceRepository documentsRepo,
			VirtualFolderRepository virtualFolderRepo, IWorkflowRouter  workflowRouter) {
		super(handler, broker, consumerFactory, evaluator, mapperFactory, docSnapshotRepo,  documentsRepo,
				virtualFolderRepo, workflowRouter);

	}

	/**
	 * Creates a singleton bean for the filesystem contents dispatcher.
	 * 
	 * @return A configured GIOCModuleContentsDispatcher for handling filesystem content
	 */
	@Bean
	@Scope("singleton")
	@Qualifier("filesystemContentsDispatcher")
	public GIOCModuleContentsDispatcher<GFilesystemContentManagementSystem, GFilesystemProjectEndpoint> getFilesystemContentsDispatcher() {
		return super.getDispatcher();
	}
}