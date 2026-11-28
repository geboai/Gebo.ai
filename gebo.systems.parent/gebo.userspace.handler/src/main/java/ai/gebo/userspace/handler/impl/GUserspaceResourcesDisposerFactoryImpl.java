/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.userspace.handler.impl;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ai.gebo.application.messaging.model.GStandardModulesConstraints;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.knowledgebase.repositories.JobStatusRepository;
import ai.gebo.systems.abstraction.layer.GAbstractResourcesDisposerFactory;
import ai.gebo.systems.abstraction.layer.IGLocalPersistentFolderDiscoveryService;
import ai.gebo.userspace.handler.GUserspaceProjectEndpoint;
import ai.gebo.userspace.handler.IGUserspaceContentManagementSystemHandler;
import ai.gebo.userspace.handler.repository.UserspaceProjectEndpointRepository;

/**
 * Disposes what a deleted userspace data source leaves: its jobs, the ingestion
 * acknowledgements of its contents, the data source itself and the local folder
 * holding the files uploaded to it. A userspace knowledge base or folder deleted
 * through the core (project or knowledge base deletion) reaches every module's
 * dispose component: without this one the userspace data sources were left behind.
 * The documents and stores are erased by the other receivers of the deletion.
 */
@Component
@Scope("singleton")
public class GUserspaceResourcesDisposerFactoryImpl
		extends GAbstractResourcesDisposerFactory<GUserspaceProjectEndpoint> {

	public GUserspaceResourcesDisposerFactoryImpl(IGLocalPersistentFolderDiscoveryService persistenceFolderDiscoverer,
			IGUserspaceContentManagementSystemHandler moduleHandler,
			UserspaceProjectEndpointRepository endpointRepository, JobStatusRepository jobStatusRepo,
			IGPersistentObjectManager persistentObjectManager) {
		super(persistenceFolderDiscoverer, moduleHandler, endpointRepository, jobStatusRepo, persistentObjectManager);
	}

	@Override
	public String getMessagingModuleId() {
		return GStandardModulesConstraints.USERSPACE_MODULE;
	}

	@Override
	protected boolean isCanBeDisposedResources(GUserspaceProjectEndpoint endpoint) {
		// the uploaded files are kept in the data source's local folder
		return true;
	}

	@Override
	protected void disposeResources(GUserspaceProjectEndpoint endpoint, String contentManagementSystemCode) {
		super.disposeFileSystem(endpoint, contentManagementSystemCode);
	}
}
