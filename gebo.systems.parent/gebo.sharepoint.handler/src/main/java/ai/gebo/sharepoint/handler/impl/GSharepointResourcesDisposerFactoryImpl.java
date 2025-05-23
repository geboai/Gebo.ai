/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.sharepoint.handler.impl;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ai.gebo.application.messaging.model.GStandardModulesConstraints;
import ai.gebo.knowledgebase.repositories.JobStatusRepository;
import ai.gebo.sharepoint.handler.GSharepointProjectEndpoint;
import ai.gebo.sharepoint.handler.repositories.SharepointProjectEndpointRepository;
import ai.gebo.systems.abstraction.layer.GAbstractResourcesDisposerFactory;
import ai.gebo.systems.abstraction.layer.IGLocalPersistentFolderDiscoveryService;

/**
 * AI generated comments
 * 
 * This class is responsible for disposing resources associated with SharePoint project endpoints.
 * It extends the abstract resource disposer factory and specializes in managing SharePoint-specific
 * resources cleanup.
 */
@Component
@Scope("singleton")
public class GSharepointResourcesDisposerFactoryImpl
		extends GAbstractResourcesDisposerFactory<GSharepointProjectEndpoint> {

	/**
	 * Constructor for the SharePoint resources disposer factory.
	 * 
	 * @param persistenceFolderDiscoverer Service to discover local persistent folders
	 * @param moduleHandler The SharePoint content management system handler
	 * @param endpointRepository Repository for SharePoint project endpoints
	 * @param jobStatusRepo Repository for job status information
	 */
	public GSharepointResourcesDisposerFactoryImpl(IGLocalPersistentFolderDiscoveryService persistenceFolderDiscoverer,
			GSharepointContentManagementSystemHandlerImpl moduleHandler,
			SharepointProjectEndpointRepository endpointRepository, JobStatusRepository jobStatusRepo) {
		super(persistenceFolderDiscoverer, moduleHandler, endpointRepository, jobStatusRepo);

	}

	/**
	 * Returns the messaging module identifier for SharePoint.
	 * 
	 * @return The SharePoint module identifier from standard module constraints
	 */
	@Override
	public String getMessagingModuleId() {

		return GStandardModulesConstraints.SHAREPOINT_MODULE;
	}

	/**
	 * Determines if the resources associated with the given endpoint can be disposed.
	 * 
	 * @param endpoint The SharePoint project endpoint to check
	 * @return Always returns true, indicating all SharePoint resources can be disposed
	 */
	@Override
	protected boolean isCanBeDisposedResources(GSharepointProjectEndpoint endpoint) {

		return true;
	}

	/**
	 * Disposes resources associated with the given SharePoint project endpoint.
	 * Calls the parent's file system disposal method.
	 * 
	 * @param endpoint The SharePoint project endpoint whose resources need to be disposed
	 */
	@Override
	protected void disposeResources(GSharepointProjectEndpoint endpoint) {
		super.disposeFileSystem(endpoint);

	}

}