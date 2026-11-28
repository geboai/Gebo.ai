/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.mcpclient.content.handler.impl;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.knowledgebase.repositories.JobStatusRepository;
import ai.gebo.mcpclient.content.handler.IGMCPClientContentManagementHandler;
import ai.gebo.mcpclient.content.handler.MCPClientProjectEndpoint;
import ai.gebo.mcpclient.content.handler.repository.MCPClientProjectEndpointRepository;
import ai.gebo.systems.abstraction.layer.GAbstractResourcesDisposerFactory;
import ai.gebo.systems.abstraction.layer.IGLocalPersistentFolderDiscoveryService;

/**
 * Disposes what a deleted MCP client data source leaves: its jobs, the ingestion
 * acknowledgements of its contents and the data source itself. The data source's
 * documents and stores are erased by the other receivers of the deletion; this
 * module keeps no local files of a data source, so there is nothing on disk.
 */
@Component
@Scope("singleton")
public class GMCPClientResourcesDisposerFactoryImpl extends GAbstractResourcesDisposerFactory<MCPClientProjectEndpoint> {

	public GMCPClientResourcesDisposerFactoryImpl(IGLocalPersistentFolderDiscoveryService persistenceFolderDiscoverer,
			IGMCPClientContentManagementHandler moduleHandler, MCPClientProjectEndpointRepository endpointRepository, JobStatusRepository jobStatusRepo,
			IGPersistentObjectManager persistentObjectManager) {
		super(persistenceFolderDiscoverer, moduleHandler, endpointRepository, jobStatusRepo, persistentObjectManager);
	}

	@Override
	public String getMessagingModuleId() {
		return GMCPClientRemoteVirtualFilesystemConsumingServiceImpl.MCP_CLIENT_MODULE;
	}

	@Override
	protected boolean isCanBeDisposedResources(MCPClientProjectEndpoint endpoint) {
		return false;
	}

	@Override
	protected void disposeResources(MCPClientProjectEndpoint endpoint, String contentManagementSystemCode) {
	}
}
