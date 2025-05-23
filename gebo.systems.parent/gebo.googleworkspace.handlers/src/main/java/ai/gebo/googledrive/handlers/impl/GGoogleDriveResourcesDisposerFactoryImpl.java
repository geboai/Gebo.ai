/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.googledrive.handlers.impl;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ai.gebo.application.messaging.model.GStandardModulesConstraints;
import ai.gebo.googledrive.handlers.GGoogleDriveProjectEndpoint;
import ai.gebo.googledrive.handlers.repositories.GoogleDriveProjectEndpointRepository;
import ai.gebo.knowledgebase.repositories.JobStatusRepository;
import ai.gebo.systems.abstraction.layer.GAbstractResourcesDisposerFactory;
import ai.gebo.systems.abstraction.layer.IGLocalPersistentFolderDiscoveryService;

/**
 * AI generated comments
 * 
 * Implementation of the resource disposer factory for Google Drive resources.
 * This singleton component extends the abstract resource disposer factory
 * to manage the lifecycle of Google Drive project endpoint resources.
 */
@Component
@Scope("singleton")
public class GGoogleDriveResourcesDisposerFactoryImpl
		extends GAbstractResourcesDisposerFactory<GGoogleDriveProjectEndpoint> {

	/**
	 * Constructor for the Google Drive resources disposer factory.
	 * 
	 * @param persistenceFolderDiscoverer Service for discovering local persistent folders
	 * @param moduleHandler Handler for Google Drive system content
	 * @param endpointRepository Repository for Google Drive project endpoints
	 * @param jobStatusRepo Repository for job status tracking
	 */
	public GGoogleDriveResourcesDisposerFactoryImpl(IGLocalPersistentFolderDiscoveryService persistenceFolderDiscoverer,
			GGoogleDriveSystemContentHandlerImpl moduleHandler,
			GoogleDriveProjectEndpointRepository endpointRepository, JobStatusRepository jobStatusRepo) {
		super(persistenceFolderDiscoverer, moduleHandler, endpointRepository, jobStatusRepo);

	}

	/**
	 * Returns the messaging module ID for Google Drive.
	 * 
	 * @return The standard module ID for Google Drive
	 */
	@Override
	public String getMessagingModuleId() {

		return GStandardModulesConstraints.GOOGLE_DRIVE_MODULE;
	}

	/**
	 * Determines if the given endpoint's resources can be disposed.
	 * Currently always returns false, indicating resources should not be disposed.
	 * 
	 * @param endpoint The Google Drive project endpoint to check
	 * @return false, indicating resources cannot be disposed
	 */
	@Override
	protected boolean isCanBeDisposedResources(GGoogleDriveProjectEndpoint endpoint) {

		return false;
	}

	/**
	 * Disposes resources associated with the given endpoint.
	 * This method is currently empty as no disposal actions are implemented.
	 * 
	 * @param endpoint The Google Drive project endpoint whose resources should be disposed
	 */
	@Override
	protected void disposeResources(GGoogleDriveProjectEndpoint endpoint) {
		

	}

}