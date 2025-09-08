/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.atlassian.jira.handler.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import ai.gebo.application.messaging.IGMessageBroker;
import ai.gebo.application.messaging.business.IWorkflowRouter;
import ai.gebo.architecture.contenthandling.interfaces.IGContentConsumerFactory;
import ai.gebo.atlassian.jira.handler.GJiraProjectEndpoint;
import ai.gebo.atlassian.jira.handler.GJiraSystem;
import ai.gebo.atlassian.jira.handler.IGJiraContentManagementHandler;
import ai.gebo.knowledgebase.repositories.DocumentReferenceRepository;
import ai.gebo.knowledgebase.repositories.DocumentReferenceSnapshotRepository;
import ai.gebo.knowledgebase.repositories.VirtualFolderRepository;
import ai.gebo.systems.abstraction.layer.GIOCModuleContentsDispatcher;
import ai.gebo.systems.abstraction.layer.GIOCModuleContentsDispatcher.SingletonBuilder;
import ai.gebo.systems.abstraction.layer.IGContentDispatchingEvaluator;
import ai.gebo.systems.abstraction.layer.IGDocumentReferenceEnricherMapFactory;

/**
 * AI generated comments
 * 
 * Configuration class for the Jira contents dispatcher.
 * This class extends the SingletonBuilder to create and configure
 * a dispatcher for handling Jira system content with project endpoints.
 */
@Configuration
public class GJiraContentsDispatcherConfig extends SingletonBuilder<GJiraSystem, GJiraProjectEndpoint> {

	/**
	 * Constructor that initializes the configuration with all required dependencies.
	 * 
	 * @param handler The Jira content management handler
	 * @param broker Message broker for communication
	 * @param consumerFactory Factory for creating content consumers
	 * @param evaluator Evaluator for content dispatching decisions
	 * @param mapperFactory Factory for document reference enricher maps
	 * @param docSnapshotRepo Repository for document reference snapshots
	 * @param config Configuration for the content systems layer
	 * @param documentsRepo Repository for document references
	 * @param virtualFolderRepo Repository for virtual folders
	 * @param workflowRouter 
	 */
	public GJiraContentsDispatcherConfig(IGJiraContentManagementHandler handler, IGMessageBroker broker,
			IGContentConsumerFactory consumerFactory, IGContentDispatchingEvaluator evaluator,
			IGDocumentReferenceEnricherMapFactory mapperFactory, DocumentReferenceSnapshotRepository docSnapshotRepo,
			DocumentReferenceRepository documentsRepo,
			VirtualFolderRepository virtualFolderRepo, IWorkflowRouter workflowRouter) {
		super(handler, broker, consumerFactory, evaluator, mapperFactory, docSnapshotRepo,  documentsRepo,
				virtualFolderRepo,workflowRouter);

	}

	/**
	 * Creates and returns a singleton instance of the Jira contents dispatcher.
	 * 
	 * @return A configured GIOCModuleContentsDispatcher for Jira systems
	 */
	@Bean
	@Scope("singleton")
	@Qualifier("jiraContentsDispatcher")
	public GIOCModuleContentsDispatcher<GJiraSystem, GJiraProjectEndpoint> getJiraContentsDispatcher() {
		return super.getDispatcher();
	}
}