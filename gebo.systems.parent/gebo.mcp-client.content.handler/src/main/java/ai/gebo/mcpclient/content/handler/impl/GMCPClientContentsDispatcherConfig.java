/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.mcpclient.content.handler.impl;

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
import ai.gebo.mcpclient.content.handler.GMCPClientSystem;
import ai.gebo.mcpclient.content.handler.IGMCPClientContentManagementHandler;
import ai.gebo.mcpclient.content.handler.MCPClientProjectEndpoint;
import ai.gebo.systems.abstraction.layer.GIOCModuleContentsDispatcher;
import ai.gebo.systems.abstraction.layer.GIOCModuleContentsDispatcher.SingletonBuilder;
import ai.gebo.systems.abstraction.layer.IGContentDispatchingEvaluator;
import ai.gebo.systems.abstraction.layer.IGDocumentReferenceEnricherMapFactory;
import ai.gebo.systems.abstraction.layer.RemoteVirtualFileSystemContentConsumingSessionParam;

/**
 * Registers the IOC contents dispatcher that routes ingestion requests to the
 * MCP content management handler, mirroring the other virtual-filesystem
 * handlers (filesystem/jira). Without it the ingestion job service cannot find
 * an {@code IGIOCModuleContentsDispatcher} for the MCP module.
 */
@Configuration
public class GMCPClientContentsDispatcherConfig extends
		SingletonBuilder<GMCPClientSystem, MCPClientProjectEndpoint, RemoteVirtualFileSystemContentConsumingSessionParam> {

	/**
	 * @param handler           the MCP content management handler
	 * @param broker            message broker for communication
	 * @param consumerFactory   factory for creating content consumers
	 * @param evaluator         evaluator for content dispatching decisions
	 * @param mapperFactory     factory for document reference enrichers
	 * @param docSnapshotRepo   repository for document reference snapshots
	 * @param documentsRepo     repository for document references
	 * @param virtualFolderRepo repository for virtual folders
	 * @param workflowRouter    workflow router
	 */
	public GMCPClientContentsDispatcherConfig(IGMCPClientContentManagementHandler handler, IGMessageBroker broker,
			IGContentConsumerFactory consumerFactory, IGContentDispatchingEvaluator evaluator,
			IGDocumentReferenceEnricherMapFactory mapperFactory, DocumentReferenceSnapshotRepository docSnapshotRepo,
			DocumentReferenceRepository documentsRepo, VirtualFolderRepository virtualFolderRepo,
			IWorkflowRouter workflowRouter) {
		super(handler, broker, consumerFactory, evaluator, mapperFactory, docSnapshotRepo, documentsRepo,
				virtualFolderRepo, workflowRouter);
	}

	/**
	 * @return a configured contents dispatcher for MCP server endpoints
	 */
	@Bean
	@Scope("singleton")
	@Qualifier("mcpClientContentsDispatcher")
	public GIOCModuleContentsDispatcher<GMCPClientSystem, MCPClientProjectEndpoint, RemoteVirtualFileSystemContentConsumingSessionParam> getMCPClientContentsDispatcher() {
		return super.getDispatcher();
	}
}
