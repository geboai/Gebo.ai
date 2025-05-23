/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.ai.app.virtualremotefs.tests.architecture;

import org.springframework.stereotype.Service;
import ai.gebo.application.messaging.IGMessageBroker;
import ai.gebo.architecture.buildsystems.abstraction.layer.IGBuildSystemHandlerRepositoryPattern;
import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.architecture.contenthandling.interfaces.IGDocumentReferenceFactory;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.knlowledgebase.model.systems.GContentManagementSystemType;
import ai.gebo.system.ingestion.IGDocumentReferenceIngestionHandler;
import ai.gebo.systems.abstraction.layer.GAbstractRemoteVirtualFilesystemContentManagementSystemHandler;
import ai.gebo.systems.abstraction.layer.IGContentManagementSystemConfigurationDao;
import ai.gebo.systems.abstraction.layer.IGLocalPersistentFolderDiscoveryService;
import ai.gebo.systems.abstraction.layer.IGProjectEndpointRuntimeConfigurationDao;

/**
 * AI generated comments
 * Test implementation of a virtual filesystem content handler for managing
 * test modules and endpoints within a simulated environment.
 * Extends from {@link GAbstractRemoteVirtualFilesystemContentManagementSystemHandler}
 * to provide specific handling for Test Virtual Remote systems.
 */
@Service
public class TestVirtualFilesystemContentHandlerImpl extends
		GAbstractRemoteVirtualFilesystemContentManagementSystemHandler<TestVirtualRemoteSystem, TestVirtualRemoteProjectEndpoint, TestVirtualFilesystemRemoteReference, IGTestVirtualFilesystemRemoteConsumerService> {

	/** Constant for defining the test module name. */
	public static final String TEST_MODULE = "TEST-MODULE";
	
	/** Representation of the content management system type for this test handler. */
	private static final GContentManagementSystemType type = new GContentManagementSystemType();
	
	/** Representation instance of the test virtual remote system. */
	private static final TestVirtualRemoteSystem system = new TestVirtualRemoteSystem();
	
	static {
		type.setCode(TEST_MODULE);
		type.setDescription("Test module");
		system.setContentManagementSystemType(type.getCode());
		system.setCode("TEST-MODULE-SYSTEM");
		system.setDescription("Test module system instance");
	}

	/**
	 * Constructs the TestVirtualFilesystemContentHandlerImpl with the required dependencies.
	 *
	 * @param buildSystemHandlerRepository Manages build system handlers
	 * @param contentHandler Factory for document reference creation
	 * @param endpointsRepo Repository for project endpoints
	 * @param localFolderDiscoveryService Discovers local persistent folders
	 * @param persistentObjectManager Manages persistent objects
	 * @param messageBroker Handles messaging
	 * @param consumingService Service for remote consumer operations
	 * @param ingestionHandler Handles document reference ingestion
	 */
	public TestVirtualFilesystemContentHandlerImpl(IGBuildSystemHandlerRepositoryPattern buildSystemHandlerRepository,
			IGDocumentReferenceFactory contentHandler, TestVirtualFilesystemProjectEndpointRepository endpointsRepo,
			IGLocalPersistentFolderDiscoveryService localFolderDiscoveryService,
			IGPersistentObjectManager persistentObjectManager, IGMessageBroker messageBroker,
			IGTestVirtualFilesystemRemoteConsumerService consumingService,
			IGDocumentReferenceIngestionHandler ingestionHandler) {
		super(buildSystemHandlerRepository, contentHandler, IGContentManagementSystemConfigurationDao.of(system),
				IGProjectEndpointRuntimeConfigurationDao.of(endpointsRepo), localFolderDiscoveryService,
				persistentObjectManager, messageBroker, consumingService, ingestionHandler);
	}

	/**
	 * Retrieves the content management system type that this handler manages.
	 *
	 * @return The {@link GContentManagementSystemType} being handled
	 */
	@Override
	public GContentManagementSystemType getHandledSystemType() {
		return type;
	}

	/**
	 * Finds and retrieves a project endpoint based on the specified system and endpoint codes.
	 *
	 * @param systemCode The code identifying the system
	 * @param projectEndpointCode The code identifying the project endpoint
	 * @return The matching {@link TestVirtualRemoteProjectEndpoint} if found
	 * @throws GeboContentHandlerSystemException If there is an error during retrieval
	 */
	@Override
	public TestVirtualRemoteProjectEndpoint findProjectEndPoint(String systemCode, String projectEndpointCode)
			throws GeboContentHandlerSystemException {
		return this.endpointsDao.findByCode(projectEndpointCode);
	}

	/**
	 * Determines if the provided endpoint is managed by this handler.
	 *
	 * @param endpoint The project endpoint to evaluate
	 * @return True if the endpoint is an instance of {@link TestVirtualRemoteProjectEndpoint}, otherwise false
	 */
	@Override
	public boolean isManagedEndpoint(GProjectEndpoint endpoint) {
		return endpoint instanceof TestVirtualRemoteProjectEndpoint;
	}

	/**
	 * Provides the messaging module identifier for this handler.
	 *
	 * @return The identifier for the messaging module
	 */
	@Override
	public String getMessagingModuleId() {
		return TEST_MODULE;
	}

	/**
	 * Retrieves the virtual remote system related to the specified project endpoint.
	 *
	 * @param projectEndPoint The project endpoint of interest
	 * @return The associated {@link TestVirtualRemoteSystem}
	 * @throws GeboContentHandlerSystemException If there is an error in system retrieval
	 */
	@Override
	public TestVirtualRemoteSystem getSystem(TestVirtualRemoteProjectEndpoint projectEndPoint)
			throws GeboContentHandlerSystemException {
		return system;
	}
}