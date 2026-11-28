/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.zammad.handler.impl;

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
import ai.gebo.zammad.handler.GZammadProjectEndpoint;
import ai.gebo.zammad.handler.GZammadSystem;
import ai.gebo.zammad.handler.IGZammadContentManagementSystemHandler;
import ai.gebo.systems.abstraction.layer.GIOCModuleContentsDispatcher;
import ai.gebo.systems.abstraction.layer.GIOCModuleContentsDispatcher.SingletonBuilder;
import ai.gebo.systems.abstraction.layer.IGContentDispatchingEvaluator;
import ai.gebo.systems.abstraction.layer.IGDocumentReferenceEnricherMapFactory;
import ai.gebo.systems.abstraction.layer.RemoteVirtualFileSystemContentConsumingSessionParam;

/**
 * The contents dispatcher of the Zammad module: runs the ingestion jobs of
 * the Zammad data sources.
 */
@Configuration
public class GZammadContentsDispatcherConfig extends
		SingletonBuilder<GZammadSystem, GZammadProjectEndpoint, RemoteVirtualFileSystemContentConsumingSessionParam> {
	public GZammadContentsDispatcherConfig(IGZammadContentManagementSystemHandler handler, IGMessageBroker broker,
			IGContentConsumerFactory consumerFactory, IGContentDispatchingEvaluator evaluator,
			IGDocumentReferenceEnricherMapFactory mapperFactory, DocumentReferenceSnapshotRepository docSnapshotRepo,
			DocumentReferenceRepository documentsRepo, VirtualFolderRepository virtualFolderRepo,
			IWorkflowRouter workflowRouter) {
		super(handler, broker, consumerFactory, evaluator, mapperFactory, docSnapshotRepo, documentsRepo,
				virtualFolderRepo, workflowRouter);
	}

	@Bean
	@Scope("singleton")
	@Qualifier("zammadContentsDispatcher")
	public GIOCModuleContentsDispatcher<GZammadSystem, GZammadProjectEndpoint, RemoteVirtualFileSystemContentConsumingSessionParam> getZammadContentsDispatcher() {
		return super.getDispatcher();
	}
}
