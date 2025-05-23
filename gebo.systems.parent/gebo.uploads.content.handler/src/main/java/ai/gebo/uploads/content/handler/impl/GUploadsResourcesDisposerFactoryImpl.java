/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.uploads.content.handler.impl;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ai.gebo.application.messaging.model.GStandardModulesConstraints;
import ai.gebo.knowledgebase.repositories.JobStatusRepository;
import ai.gebo.systems.abstraction.layer.GAbstractResourcesDisposerFactory;
import ai.gebo.systems.abstraction.layer.IGLocalPersistentFolderDiscoveryService;
import ai.gebo.uploads.content.handler.GUploadsProjectEndpoint;
import ai.gebo.uploads.content.handler.repositories.UploadsProjectEndpointRepository;

/**
 * AI generated comments
 * 
 * Implementation of the resource disposer factory for upload-related resources.
 * This singleton component handles the cleanup and disposal of resources associated
 * with upload project endpoints.
 */
@Component
@Scope("singleton")
public class GUploadsResourcesDisposerFactoryImpl
		extends GAbstractResourcesDisposerFactory<GUploadsProjectEndpoint> {

    /**
     * Constructor for the uploads resource disposer factory.
     * 
     * @param persistenceFolderDiscoverer Service to discover persistent folders
     * @param moduleHandler The content management system handler for uploads
     * @param endpointRepository Repository to access upload project endpoints
     * @param jobStatusRepo Repository to track job statuses
     */
	public GUploadsResourcesDisposerFactoryImpl(IGLocalPersistentFolderDiscoveryService persistenceFolderDiscoverer,
			GUploadsContentManagementSystemHandlerImpl moduleHandler,
			UploadsProjectEndpointRepository endpointRepository, JobStatusRepository jobStatusRepo) {
		super(persistenceFolderDiscoverer, moduleHandler, endpointRepository, jobStatusRepo);

	}

    /**
     * Returns the messaging module identifier for the uploads module.
     * 
     * @return The standard module identifier for uploads
     */
	@Override
	public String getMessagingModuleId() {

		return GStandardModulesConstraints.UPLOADS_MODULE;
	}

    /**
     * Determines if the resources associated with the given endpoint can be disposed.
     * For upload endpoints, this always returns true.
     * 
     * @param endpoint The upload project endpoint to check
     * @return Always true, indicating that upload resources can be disposed
     */
	@Override
	protected boolean isCanBeDisposedResources(GUploadsProjectEndpoint endpoint) {

		return true;
	}

    /**
     * Performs the actual disposal of resources for the given endpoint.
     * This method disposes the file system resources associated with the endpoint.
     * 
     * @param endpoint The upload project endpoint whose resources need to be disposed
     */
	@Override
	protected void disposeResources(GUploadsProjectEndpoint endpoint) {
		super.disposeFileSystem(endpoint);

	}

}