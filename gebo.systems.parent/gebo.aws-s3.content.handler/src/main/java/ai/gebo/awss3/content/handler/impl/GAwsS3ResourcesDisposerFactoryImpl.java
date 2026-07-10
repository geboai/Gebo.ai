/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.awss3.content.handler.impl;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ai.gebo.application.messaging.model.GStandardModulesConstraints;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.awss3.content.handler.GAwsS3ProjectEndpoint;
import ai.gebo.awss3.content.handler.repositories.AwsS3ProjectEndpointRepository;
import ai.gebo.knowledgebase.repositories.JobStatusRepository;
import ai.gebo.systems.abstraction.layer.GAbstractResourcesDisposerFactory;
import ai.gebo.systems.abstraction.layer.IGLocalPersistentFolderDiscoveryService;

@Component
@Scope("singleton")
public class GAwsS3ResourcesDisposerFactoryImpl extends GAbstractResourcesDisposerFactory<GAwsS3ProjectEndpoint> {

	public GAwsS3ResourcesDisposerFactoryImpl(IGLocalPersistentFolderDiscoveryService persistenceFolderDiscoverer,
			GAwsS3SystemContentHandlerImpl moduleHandler,
			AwsS3ProjectEndpointRepository endpointRepository, JobStatusRepository jobStatusRepo,
			IGPersistentObjectManager persistentObjectManager) {
		super(persistenceFolderDiscoverer, moduleHandler, endpointRepository, jobStatusRepo, persistentObjectManager);
	}

	@Override
	public String getMessagingModuleId() {
		return GStandardModulesConstraints.AWS_S3_MODULE;
	}

	@Override
	protected boolean isCanBeDisposedResources(GAwsS3ProjectEndpoint endpoint) {
		return false;
	}

	@Override
	protected void disposeResources(GAwsS3ProjectEndpoint endpoint, String contentManagementSystemCode) {
	}
}