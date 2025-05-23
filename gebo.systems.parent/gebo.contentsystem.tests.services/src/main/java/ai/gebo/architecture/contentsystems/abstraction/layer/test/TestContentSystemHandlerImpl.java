/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.architecture.contentsystems.abstraction.layer.test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import ai.gebo.application.messaging.IGMessageBroker;
import ai.gebo.architecture.buildsystems.abstraction.layer.IGBuildSystemHandlerRepositoryPattern;
import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.architecture.contenthandling.interfaces.IGContentConsumer;
import ai.gebo.architecture.contenthandling.interfaces.IGDocumentReferenceFactory;
import ai.gebo.architecture.contenthandling.interfaces.IGUserMessagesConsumer;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.knlowledgebase.model.contents.GVirtualFolder;
import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.knlowledgebase.model.systems.GBuildSystem;
import ai.gebo.knlowledgebase.model.systems.GContentManagementSystemType;
import ai.gebo.model.GUserMessage;
import ai.gebo.system.ingestion.IGDocumentReferenceIngestionHandler;
import ai.gebo.systems.abstraction.layer.GAbstractContentManagementSystemHandler;
import ai.gebo.systems.abstraction.layer.IGContentManagementSystemConfigurationDao;
import ai.gebo.systems.abstraction.layer.IGContentsAccessErrorConsumer;
import ai.gebo.systems.abstraction.layer.IGLocalPersistentFolderDiscoveryService;
import ai.gebo.systems.abstraction.layer.IGProjectEndpointRuntimeConfigurationDao;

/**
 * AI generated comments
 * 
 * Implementation of a test content system handler that extends the abstract content management system handler.
 * This handler is used for testing purposes and supports different test types for content consumption.
 */
@Service
public class TestContentSystemHandlerImpl
		extends GAbstractContentManagementSystemHandler<TestContentManagementSystem, TestProjectEndpoint>
		implements ITestContentSystemHandler {
	/** Prefix for classpath resources */
	public static final String CLASSPATH_RESOURCE_PREFIX = "classpath:";
	/** The type of content management system this handler supports */
	private static final GContentManagementSystemType type = new GContentManagementSystemType();
	/** The singleton instance of the test content management system */
	private static final TestContentManagementSystem singleSystem = new TestContentManagementSystem();

	/**
	 * Static initializer to set up the content management system type and description
	 */
	static {
		type.setCode("TEST-CONTENT-SYSTEM");
		type.setDescription("Test content system handler");
		singleSystem.setContentManagementSystemType(type.getCode());
		singleSystem.setDescription("Test content system handler service");
	}

	/**
	 * Constructor for the test content system handler
	 * 
	 * @param buildSystemHandlerRepository Repository for build system handlers
	 * @param contentHandler Factory for document references
	 * @param localFolderDiscoveryService Service for discovering local folders
	 * @param persistentObjectManager Manager for persistent objects
	 * @param messageBroker Broker for messages
	 * @param endpointRepo Repository for test project endpoints
	 * @param ingestionHandler Handler for document reference ingestion
	 */
	public TestContentSystemHandlerImpl(IGBuildSystemHandlerRepositoryPattern buildSystemHandlerRepository,
			IGDocumentReferenceFactory contentHandler,
			IGLocalPersistentFolderDiscoveryService localFolderDiscoveryService,
			IGPersistentObjectManager persistentObjectManager, IGMessageBroker messageBroker,
			TestProjectEndpointRepository endpointRepo, IGDocumentReferenceIngestionHandler ingestionHandler) {
		super(buildSystemHandlerRepository, contentHandler, IGContentManagementSystemConfigurationDao.of(singleSystem),
				IGProjectEndpointRuntimeConfigurationDao.of(endpointRepo), localFolderDiscoveryService,
				persistentObjectManager, messageBroker, ingestionHandler);

	}

	/**
	 * Returns the type of content management system this handler supports
	 * 
	 * @return The content management system type
	 */
	@Override
	public GContentManagementSystemType getHandledSystemType() {

		return type;
	}

	/**
	 * Finds a project endpoint by system code and project endpoint code
	 * 
	 * @param systemCode The system code
	 * @param projectEndpointCode The project endpoint code
	 * @return The found test project endpoint
	 * @throws GeboContentHandlerSystemException If an error occurs
	 */
	@Override
	public TestProjectEndpoint findProjectEndPoint(String systemCode, String projectEndpointCode)
			throws GeboContentHandlerSystemException {

		return endpointsDao.findByCode(projectEndpointCode);
	}

	/**
	 * Checks if an endpoint is managed by this handler
	 * 
	 * @param endpoint The endpoint to check
	 * @return True if the endpoint is managed by this handler, false otherwise
	 */
	@Override
	public boolean isManagedEndpoint(GProjectEndpoint endpoint) {

		return endpoint != null && endpoint instanceof TestProjectEndpoint;
	}

	/**
	 * Returns the messaging module ID for this handler
	 * 
	 * @return The messaging module ID
	 */
	@Override
	public String getMessagingModuleId() {

		return TestConstants.TEST_CONTENT_SYSTEM_MODULE;
	}

	/**
	 * Returns the content management system for a project endpoint
	 * 
	 * @param projectEndPoint The project endpoint
	 * @return The content management system
	 * @throws GeboContentHandlerSystemException If an error occurs
	 */
	@Override
	public TestContentManagementSystem getSystem(TestProjectEndpoint projectEndPoint)
			throws GeboContentHandlerSystemException {

		return singleSystem;
	}

	/**
	 * Implements the content consumption logic based on the test type of the endpoint
	 * 
	 * @param contentManagementConfig The content management configuration
	 * @param buildSystems The build systems
	 * @param endpoint The test project endpoint
	 * @param consumer The content consumer
	 * @param messagesConsumer The user messages consumer
	 * @param errorConsumer The error consumer
	 * @throws GeboContentHandlerSystemException If an error occurs
	 */
	@Override
	protected void consumeImplementation(TestContentManagementSystem contentManagementConfig,
			List<GBuildSystem> buildSystems, TestProjectEndpoint endpoint, IGContentConsumer consumer,
			IGUserMessagesConsumer messagesConsumer, IGContentsAccessErrorConsumer errorConsumer)
			throws GeboContentHandlerSystemException {
		if (endpoint.getTestType() == null)
			throw new GeboContentHandlerSystemException("Test type unknown");
		switch (endpoint.getTestType()) {
		case CONTAINED_CONTENTS: {
			// Process document references, virtual folders, and user messages contained in the endpoint
			for (GDocumentReference cn : endpoint.getTestDocumentReferences()) {
				consumer.accept(cn);
			}
			for (GVirtualFolder vf : endpoint.getTestVirtualFolders()) {
				consumer.accept(vf);
			}
			for (GUserMessage msg : endpoint.getTestUserMessages()) {
				messagesConsumer.accept(msg);
			}
		}
			break;
		case PATH_CONTENTS: {
			// Process content from filesystem paths
			GVirtualFolder root = createRootItem(contentManagementConfig, endpoint);
			for (String path : endpoint.getTestFilesystemPaths()) {
				Path _path = Path.of(path);
				if (Files.isDirectory(_path))
					super.consume(root, contentManagementConfig, buildSystems, endpoint, Path.of(path), (x) -> true,
							consumer, messagesConsumer, errorConsumer);
				else
					super.consumeDocument(endpoint, root, _path, consumer, messagesConsumer, errorConsumer);
			}
		}
			break;
		}
	}

	/**
	 * Streams content from a document reference
	 * 
	 * @param reference The document reference
	 * @param cache The cache map
	 * @return An input stream for the content
	 * @throws GeboContentHandlerSystemException If an error occurs with the content system
	 * @throws IOException If an I/O error occurs
	 */
	@Override
	public InputStream streamContent(GDocumentReference reference, Map<String, Object> cache)
			throws GeboContentHandlerSystemException, IOException {
		if (reference.getUri() != null && reference.getUri().startsWith(CLASSPATH_RESOURCE_PREFIX)) {
			// Handle classpath resources
			String _resource = reference.getUri().substring(CLASSPATH_RESOURCE_PREFIX.length());
			InputStream is = getClass().getClassLoader().getResourceAsStream(_resource);
			if (is == null) {
				throw new GeboContentHandlerSystemException(
						"Test error: the resource " + reference.getUri() + " is not in the classpath");

			}
			return is;
		} else
			return super.streamContent(reference, cache);
	}
}