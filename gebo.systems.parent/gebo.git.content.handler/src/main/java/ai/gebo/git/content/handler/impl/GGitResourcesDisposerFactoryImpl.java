/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.git.content.handler.impl;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ai.gebo.application.messaging.model.GStandardModulesConstraints;
import ai.gebo.git.content.handler.GGitProjectEndpoint;
import ai.gebo.git.content.handler.repositories.GitEndpointRepository;
import ai.gebo.knowledgebase.repositories.JobStatusRepository;
import ai.gebo.systems.abstraction.layer.GAbstractResourcesDisposerFactory;
import ai.gebo.systems.abstraction.layer.IGLocalPersistentFolderDiscoveryService;

/**
 * AI generated comments
 * 
 * Implementation of the resource disposer factory for Git project endpoints.
 * This singleton component is responsible for managing the lifecycle and disposal 
 * of Git project resources to prevent resource leaks.
 */
@Component
@Scope("singleton")
public class GGitResourcesDisposerFactoryImpl extends GAbstractResourcesDisposerFactory<GGitProjectEndpoint> {

	/**
	 * Constructor for the Git resources disposer factory.
	 * 
	 * @param persistenceFolderDiscoverer Service to discover local persistent folders
	 * @param moduleHandler Handler for Git content management system
	 * @param endpointRepository Repository for Git endpoints
	 * @param jobStatusRepo Repository for tracking job statuses
	 */
	public GGitResourcesDisposerFactoryImpl(IGLocalPersistentFolderDiscoveryService persistenceFolderDiscoverer,
			GDefaultGitContentManagementSystemHandler moduleHandler, GitEndpointRepository endpointRepository, JobStatusRepository jobStatusRepo) {
		super(persistenceFolderDiscoverer, moduleHandler, endpointRepository, jobStatusRepo);

	}

	/**
	 * Returns the module identifier for the Git module.
	 * 
	 * @return The standard Git module identifier
	 */
	@Override
	public String getMessagingModuleId() {

		return GStandardModulesConstraints.GIT_MODULE;
	}

	/**
	 * Determines if the given Git project endpoint can be disposed.
	 * In this implementation, all Git project endpoints are considered disposable.
	 * 
	 * @param endpoint The Git project endpoint to check
	 * @return Always returns true, indicating all endpoints can be disposed
	 */
	@Override
	protected boolean isCanBeDisposedResources(GGitProjectEndpoint endpoint) {

		return true;
	}

	/**
	 * Performs the actual disposal of resources for a Git project endpoint.
	 * Calls the parent class's file system disposal method.
	 * 
	 * @param endpoint The Git project endpoint whose resources need to be disposed
	 */
	@Override
	protected void disposeResources(GGitProjectEndpoint endpoint) {
		super.disposeFileSystem(endpoint);

	}

}