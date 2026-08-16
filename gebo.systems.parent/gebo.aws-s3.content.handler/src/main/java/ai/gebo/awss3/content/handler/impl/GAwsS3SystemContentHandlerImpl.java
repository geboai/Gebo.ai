/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.awss3.content.handler.impl;


import ai.gebo.application.messaging.IGMessageBroker;
import ai.gebo.application.messaging.model.GStandardModulesConstraints;
import ai.gebo.architecture.buildsystems.abstraction.layer.IGBuildSystemHandlerRepositoryPattern;
import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.architecture.contenthandling.interfaces.IGDocumentReferenceFactory;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.awss3.content.handler.GAwsS3ProjectEndpoint;
import ai.gebo.awss3.content.handler.GAwsS3System;
import ai.gebo.awss3.content.handler.IGAwsS3SystemContentHandler;
import ai.gebo.awss3.content.handler.IGAwsS3VirtualFilesystemConsumingService;
import ai.gebo.awss3.content.handler.impl.model.AwsS3ResourceReference;
import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.knlowledgebase.model.systems.GContentManagementSystemType;
import ai.gebo.system.ingestion.IGDocumentReferenceIngestionHandler;
import ai.gebo.systems.abstraction.layer.GAbstractRemoteVirtualFilesystemContentManagementSystemHandler;
import ai.gebo.systems.abstraction.layer.IGLocalPersistentFolderDiscoveryService;

public class GAwsS3SystemContentHandlerImpl extends
		GAbstractRemoteVirtualFilesystemContentManagementSystemHandler<GAwsS3System, GAwsS3ProjectEndpoint, AwsS3ResourceReference, IGAwsS3VirtualFilesystemConsumingService>
		implements IGAwsS3SystemContentHandler {

	public static final String AWS_S3_HANDLER = "aws-s3-handler";

	static GContentManagementSystemType systemType = new GContentManagementSystemType();
	static {
		systemType.setCode(AWS_S3_HANDLER);
		systemType.setDescription("AWS S3 content handler");
	}

	public GAwsS3SystemContentHandlerImpl(IGBuildSystemHandlerRepositoryPattern buildSystemHandlerRepository,
			IGDocumentReferenceFactory contentHandler, AwsS3SystemsDao configurationsDao,
			AwsS3ProjectEndpointDao endpointsDao,
			IGLocalPersistentFolderDiscoveryService localFolderDiscoveryService,
			IGPersistentObjectManager persistentObjectManager, IGMessageBroker messageBroker,
			IGAwsS3VirtualFilesystemConsumingService consumingService,
			IGDocumentReferenceIngestionHandler ingestionHandler) {
		super(buildSystemHandlerRepository, contentHandler, configurationsDao, endpointsDao,
				localFolderDiscoveryService, persistentObjectManager, messageBroker, consumingService, ingestionHandler);
	}

	@Override
	public GContentManagementSystemType getHandledSystemType() {
		return systemType;
	}

	@Override
	public GAwsS3ProjectEndpoint findProjectEndPoint(String systemCode, String projectEndpointCode)
			throws GeboContentHandlerSystemException {
		return endpointsDao.findByCode(projectEndpointCode);
	}

	@Override
	public boolean isManagedEndpoint(GProjectEndpoint endpoint) {
		return endpoint instanceof GAwsS3ProjectEndpoint;
	}

	@Override
	public GAwsS3System getSystem(GAwsS3ProjectEndpoint projectEndPoint)
			throws GeboContentHandlerSystemException {
		return configurationsDao.findByCode(projectEndPoint.getS3SystemCode());
	}

	@Override
	public String getMessagingModuleId() {
		return GStandardModulesConstraints.AWS_S3_MODULE;
	}
}