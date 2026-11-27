/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.redmine.handler.impl;

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
import ai.gebo.redmine.handler.GRedmineProjectEndpoint;
import ai.gebo.redmine.handler.GRedmineSystem;
import ai.gebo.redmine.handler.IGRedmineContentManagementSystemHandler;
import ai.gebo.systems.abstraction.layer.GIOCModuleContentsDispatcher;
import ai.gebo.systems.abstraction.layer.GIOCModuleContentsDispatcher.SingletonBuilder;
import ai.gebo.systems.abstraction.layer.IGContentDispatchingEvaluator;
import ai.gebo.systems.abstraction.layer.IGDocumentReferenceEnricherMapFactory;
import ai.gebo.systems.abstraction.layer.RemoteVirtualFileSystemContentConsumingSessionParam;

/**
 * The contents dispatcher of the Redmine module: runs the ingestion jobs of
 * the Redmine data sources.
 */
@Configuration
public class GRedmineContentsDispatcherConfig extends
		SingletonBuilder<GRedmineSystem, GRedmineProjectEndpoint, RemoteVirtualFileSystemContentConsumingSessionParam> {
	public GRedmineContentsDispatcherConfig(IGRedmineContentManagementSystemHandler handler, IGMessageBroker broker,
			IGContentConsumerFactory consumerFactory, IGContentDispatchingEvaluator evaluator,
			IGDocumentReferenceEnricherMapFactory mapperFactory, DocumentReferenceSnapshotRepository docSnapshotRepo,
			DocumentReferenceRepository documentsRepo, VirtualFolderRepository virtualFolderRepo,
			IWorkflowRouter workflowRouter) {
		super(handler, broker, consumerFactory, evaluator, mapperFactory, docSnapshotRepo, documentsRepo,
				virtualFolderRepo, workflowRouter);
	}

	@Bean
	@Scope("singleton")
	@Qualifier("redmineContentsDispatcher")
	public GIOCModuleContentsDispatcher<GRedmineSystem, GRedmineProjectEndpoint, RemoteVirtualFileSystemContentConsumingSessionParam> getRedmineContentsDispatcher() {
		return super.getDispatcher();
	}
}
