/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.userspace.handler.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import ai.gebo.application.messaging.IGMessageBroker;
import ai.gebo.application.messaging.workflow.IWorkflowRouter;
import ai.gebo.architecture.contenthandling.interfaces.IGContentConsumerFactory;
import ai.gebo.knowledgebase.repositories.DocumentReferenceRepository;
import ai.gebo.knowledgebase.repositories.DocumentReferenceSnapshotRepository;
import ai.gebo.knowledgebase.repositories.VirtualFolderRepository;
import ai.gebo.systems.abstraction.layer.GIOCModuleContentsDispatcher;
import ai.gebo.systems.abstraction.layer.GIOCModuleContentsDispatcher.SingletonBuilder;
import ai.gebo.systems.abstraction.layer.IGContentDispatchingEvaluator;
import ai.gebo.systems.abstraction.layer.IGDocumentReferenceEnricherMapFactory;
import ai.gebo.userspace.handler.GUserspaceContentManagementSystem;
import ai.gebo.userspace.handler.GUserspaceProjectEndpoint;
import ai.gebo.userspace.handler.IGUserspaceContentManagementSystemHandler;

/**
 * Configuration class for the userspace module contents dispatcher.
 * This class extends the SingletonBuilder to create a dispatcher for userspace content management.
 * It handles configuration for dispatching content between the userspace content management system
 * and project endpoints.
 * 
 * AI generated comments
 */
@Configuration
public class GUserspaceModuleContentsDispatcherConfig
		extends SingletonBuilder<GUserspaceContentManagementSystem, GUserspaceProjectEndpoint> {

	/**
	 * Constructor for GUserspaceModuleContentsDispatcherConfig.
	 * Initializes the dispatcher with all necessary dependencies for content management
	 * in the userspace module.
	 * 
	 * @param handler The userspace content management system handler
	 * @param broker The message broker for communication
	 * @param consumerFactory Factory for creating content consumers
	 * @param evaluator Evaluator for content dispatching decisions
	 * @param mapperFactory Factory for document reference enricher maps
	 * @param docSnapshotRepo Repository for document reference snapshots
	 * @param config Configuration for the content systems layer
	 * @param documentsRepo Repository for document references
	 * @param virtualFolderRepo Repository for virtual folders
	 * @param workflowRouter 
	 */
	public GUserspaceModuleContentsDispatcherConfig(IGUserspaceContentManagementSystemHandler handler,
			IGMessageBroker broker, IGContentConsumerFactory consumerFactory, IGContentDispatchingEvaluator evaluator,
			IGDocumentReferenceEnricherMapFactory mapperFactory, DocumentReferenceSnapshotRepository docSnapshotRepo, DocumentReferenceRepository documentsRepo, VirtualFolderRepository virtualFolderRepo, IWorkflowRouter workflowRouter) {
		super(handler, broker, consumerFactory, evaluator, mapperFactory,docSnapshotRepo, documentsRepo, virtualFolderRepo,workflowRouter);
	}

	/**
	 * Creates a singleton bean for the userspace contents dispatcher.
	 * This dispatcher handles the routing of content between the userspace content management
	 * system and the project endpoints.
	 * 
	 * @return A configured GIOCModuleContentsDispatcher for userspace content
	 */
	@Bean
	@Scope("singleton")
	@Qualifier("userspaceContentsDispatcher")
	public GIOCModuleContentsDispatcher<GUserspaceContentManagementSystem, GUserspaceProjectEndpoint> getUserspaceContentsDispatcher() {
		return super.getDispatcher();
	}
}