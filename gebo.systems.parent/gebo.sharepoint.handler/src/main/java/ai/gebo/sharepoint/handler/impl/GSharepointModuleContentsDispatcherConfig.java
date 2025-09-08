/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.sharepoint.handler.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import ai.gebo.application.messaging.IGMessageBroker;
import ai.gebo.application.messaging.business.IWorkflowRouter;
import ai.gebo.architecture.contenthandling.interfaces.IGContentConsumerFactory;
import ai.gebo.knowledgebase.repositories.DocumentReferenceRepository;
import ai.gebo.knowledgebase.repositories.DocumentReferenceSnapshotRepository;
import ai.gebo.knowledgebase.repositories.VirtualFolderRepository;
import ai.gebo.sharepoint.handler.GSharepointContentManagementSystem;
import ai.gebo.sharepoint.handler.GSharepointProjectEndpoint;
import ai.gebo.sharepoint.handler.IGSharepointContentManagementSystemHandler;
import ai.gebo.systems.abstraction.layer.GIOCModuleContentsDispatcher;
import ai.gebo.systems.abstraction.layer.GIOCModuleContentsDispatcher.SingletonBuilder;
import ai.gebo.systems.abstraction.layer.IGContentDispatchingEvaluator;
import ai.gebo.systems.abstraction.layer.IGDocumentReferenceEnricherMapFactory;

/**
 * AI generated comments
 * 
 * Configuration class that provides the Sharepoint module contents dispatcher.
 * This class extends the SingletonBuilder to build and configure a dispatcher for Sharepoint content.
 */
@Configuration
public class GSharepointModuleContentsDispatcherConfig
		extends SingletonBuilder<GSharepointContentManagementSystem, GSharepointProjectEndpoint> {

	/**
	 * Constructor that initializes the Sharepoint module contents dispatcher configuration.
	 * 
	 * @param handler The Sharepoint content management system handler
	 * @param broker The message broker for communication
	 * @param consumerFactory The factory for creating content consumers
	 * @param evaluator The evaluator for content dispatching decisions
	 * @param mapperFactory The factory for document reference enricher maps
	 * @param docSnapshotRepo The repository for document reference snapshots
	 * @param config The configuration for content systems layer
	 * @param documentsRepo The repository for document references
	 * @param virtualFolderRepo The repository for virtual folders
	 * @param workflowRouter 
	 */
	public GSharepointModuleContentsDispatcherConfig(IGSharepointContentManagementSystemHandler handler,
			IGMessageBroker broker, IGContentConsumerFactory consumerFactory, IGContentDispatchingEvaluator evaluator,
			IGDocumentReferenceEnricherMapFactory mapperFactory, DocumentReferenceSnapshotRepository docSnapshotRepo,  DocumentReferenceRepository documentsRepo, VirtualFolderRepository virtualFolderRepo, IWorkflowRouter workflowRouter) {
		super(handler, broker, consumerFactory, evaluator, mapperFactory,docSnapshotRepo,  documentsRepo, virtualFolderRepo,workflowRouter);
	}

	/**
	 * Creates and returns a singleton instance of the Sharepoint contents dispatcher.
	 * 
	 * @return A dispatcher for Sharepoint content management system and project endpoints
	 */
	@Bean
	@Scope("singleton")
	@Qualifier("sharepointContentsDispatcher")
	public GIOCModuleContentsDispatcher<GSharepointContentManagementSystem, GSharepointProjectEndpoint> getSharepointContentsDispatcher() {
		return super.getDispatcher();
	}
}