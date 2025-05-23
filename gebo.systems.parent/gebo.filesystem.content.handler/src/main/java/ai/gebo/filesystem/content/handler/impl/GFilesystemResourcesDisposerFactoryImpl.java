/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.filesystem.content.handler.impl;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ai.gebo.application.messaging.model.GStandardModulesConstraints;
import ai.gebo.filesystem.content.handler.GFilesystemProjectEndpoint;
import ai.gebo.filesystem.content.handler.repositories.FilesystemProjectEndpointRepository;
import ai.gebo.knowledgebase.repositories.JobStatusRepository;
import ai.gebo.systems.abstraction.layer.GAbstractResourcesDisposerFactory;
import ai.gebo.systems.abstraction.layer.IGLocalPersistentFolderDiscoveryService;

/**
 * AI generated comments
 * Implementation of a resources disposer factory for filesystem project endpoints.
 * This singleton component is responsible for managing the disposal of filesystem resources.
 */
@Component
@Scope("singleton")
public class GFilesystemResourcesDisposerFactoryImpl
		extends GAbstractResourcesDisposerFactory<GFilesystemProjectEndpoint> {

    /**
     * Constructor for the filesystem resources disposer factory.
     *
     * @param persistenceFolderDiscoverer Service for discovering local persistent folders
     * @param moduleHandler The content management system handler for filesystem operations
     * @param endpointRepository Repository for filesystem project endpoints
     * @param jobStatusRepo Repository for tracking job statuses
     */
	public GFilesystemResourcesDisposerFactoryImpl(IGLocalPersistentFolderDiscoveryService persistenceFolderDiscoverer,
			GFilesystemContentManagementSystemHandlerImpl moduleHandler,
			FilesystemProjectEndpointRepository endpointRepository, JobStatusRepository jobStatusRepo) {
		super(persistenceFolderDiscoverer, moduleHandler, endpointRepository, jobStatusRepo);

	}

	/**
	 * Gets the messaging module identifier for this disposer factory.
	 *
	 * @return The standard shared filesystem module identifier string
	 */
	@Override
	public String getMessagingModuleId() {

		return GStandardModulesConstraints.SHARED_FILESYSTEM_MODULE;
	}

	/**
	 * Determines whether the specified endpoint can have its resources disposed.
	 * Currently always returns false, indicating resources can't be disposed.
	 *
	 * @param endpoint The filesystem project endpoint to check
	 * @return Always false, indicating resources cannot be disposed
	 */
	@Override
	protected boolean isCanBeDisposedResources(GFilesystemProjectEndpoint endpoint) {

		return false;
	}

	/**
	 * Disposes resources associated with the specified endpoint.
	 * Currently this method is empty and does not perform any disposal operations.
	 *
	 * @param endpoint The filesystem project endpoint whose resources should be disposed
	 */
	@Override
	protected void disposeResources(GFilesystemProjectEndpoint endpoint) {
		

	}

	

}