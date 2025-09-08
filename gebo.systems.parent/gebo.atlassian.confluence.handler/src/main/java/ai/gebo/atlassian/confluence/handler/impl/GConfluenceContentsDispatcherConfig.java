/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.atlassian.confluence.handler.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import ai.gebo.application.messaging.IGMessageBroker;
import ai.gebo.application.messaging.business.IWorkflowRouter;
import ai.gebo.architecture.contenthandling.interfaces.IGContentConsumerFactory;
import ai.gebo.atlassian.confluence.handler.GConfluenceProjectEndpoint;
import ai.gebo.atlassian.confluence.handler.GConfluenceSystem;
import ai.gebo.atlassian.confluence.handler.IGConfluenceContentManagementHandler;
import ai.gebo.knowledgebase.repositories.DocumentReferenceRepository;
import ai.gebo.knowledgebase.repositories.DocumentReferenceSnapshotRepository;
import ai.gebo.knowledgebase.repositories.VirtualFolderRepository;
import ai.gebo.systems.abstraction.layer.GIOCModuleContentsDispatcher;
import ai.gebo.systems.abstraction.layer.GIOCModuleContentsDispatcher.SingletonBuilder;
import ai.gebo.systems.abstraction.layer.IGContentDispatchingEvaluator;
import ai.gebo.systems.abstraction.layer.IGDocumentReferenceEnricherMapFactory;

/**
 * AI generated comments
 * Configuration class for Confluence content dispatcher.
 * This class extends the SingletonBuilder to provide a content dispatcher
 * specific to Confluence systems and endpoints.
 */
@Configuration
public class GConfluenceContentsDispatcherConfig
		extends SingletonBuilder<GConfluenceSystem, GConfluenceProjectEndpoint> {

	/**
	 * Constructor for the GConfluenceContentsDispatcherConfig.
	 * 
	 * @param handler The Confluence content management handler
	 * @param broker Message broker for communication
	 * @param consumerFactory Factory for creating content consumers
	 * @param evaluator Evaluator for content dispatching decisions
	 * @param mapperFactory Factory for document reference enricher maps
	 * @param docSnapshotRepo Repository for document reference snapshots
	 * @param config Configuration for content systems layer
	 * @param documentsRepo Repository for document references
	 * @param virtualFolderRepo Repository for virtual folders
	 * @param workflowRouter 
	 */
	public GConfluenceContentsDispatcherConfig(IGConfluenceContentManagementHandler handler,
			IGMessageBroker broker, IGContentConsumerFactory consumerFactory, IGContentDispatchingEvaluator evaluator,
			IGDocumentReferenceEnricherMapFactory mapperFactory, DocumentReferenceSnapshotRepository docSnapshotRepo,  DocumentReferenceRepository documentsRepo, VirtualFolderRepository virtualFolderRepo, IWorkflowRouter workflowRouter) {
		super(handler, broker, consumerFactory, evaluator, mapperFactory, docSnapshotRepo, documentsRepo,
				virtualFolderRepo, workflowRouter);

	}

	/**
	 * Creates and provides a singleton bean for the Confluence contents dispatcher.
	 * This dispatcher handles content operations between Confluence systems and project endpoints.
	 * 
	 * @return A configured GIOCModuleContentsDispatcher for Confluence
	 */
	@Bean
	@Scope("singleton")
	@Qualifier("confluenceContentsDispatcher")
	public GIOCModuleContentsDispatcher<GConfluenceSystem, GConfluenceProjectEndpoint> getConfluenceContentsDispatcher() {
		return super.getDispatcher();
	}
}