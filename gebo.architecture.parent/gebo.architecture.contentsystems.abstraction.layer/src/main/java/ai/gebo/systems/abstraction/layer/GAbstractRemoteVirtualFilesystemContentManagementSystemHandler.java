/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.systems.abstraction.layer;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import ai.gebo.application.messaging.IGMessageBroker;
import ai.gebo.architecture.buildsystems.abstraction.layer.IGBuildSystemHandlerRepositoryPattern;
import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.architecture.contenthandling.interfaces.IGContentConsumer;
import ai.gebo.architecture.contenthandling.interfaces.IGDocumentReferenceFactory;
import ai.gebo.architecture.contenthandling.interfaces.IGUserMessagesConsumer;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.knlowledgebase.model.contents.GAbstractVirtualFilesystemObject;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.knlowledgebase.model.contents.GVirtualFolder;
import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.knlowledgebase.model.projects.GVirtualFilesystemProjectEndpoint;
import ai.gebo.knlowledgebase.model.systems.GBuildSystem;
import ai.gebo.knlowledgebase.model.systems.GContentManagementSystem;
import ai.gebo.system.ingestion.IGDocumentReferenceIngestionHandler;

/**
 * Abstract class providing a framework for implementing remote virtual
 * filesystem handlers in a content management system. It extends
 * GAbstractContentManagementSystemHandler to handle specific functionalities
 * related to remote virtual filesystem operations.
 * 
 * @param <SystemIntegrationType> The type of content management system
 *                                integration.
 * @param <ProjectEndpointType>   The type of project endpoint.
 * @param <ResourceReferenceType> The type of remote virtual filesystem resource
 *                                reference.
 * @param <ConsumingServiceType>  The type of consuming service to be used for
 *                                managing resources.
 * 
 *                                AI generated comments
 */
public abstract class GAbstractRemoteVirtualFilesystemContentManagementSystemHandler<SystemIntegrationType extends GContentManagementSystem, ProjectEndpointType extends GVirtualFilesystemProjectEndpoint, ResourceReferenceType extends IGRemoteVirtualFilesystemResourceReference, ConsumingServiceType extends IGRemoteVirtualFilesystemConsumingService<SystemIntegrationType, ProjectEndpointType, ResourceReferenceType>>
		extends GAbstractContentManagementSystemHandler<SystemIntegrationType, ProjectEndpointType> {

	// The consuming service responsible for managing interaction with the remote
	// virtual filesystem.
	protected final ConsumingServiceType consumingService;

	/**
	 * Constructor initializing handler with required services and resources.
	 *
	 * @param buildSystemHandlerRepository Repository pattern for build system
	 *                                     handlers.
	 * @param contentHandler               Factory for document reference creation.
	 * @param configurationsDao            Data Access Object for system
	 *                                     configuration.
	 * @param endpointsDao                 Data Access Object for project endpoint
	 *                                     configuration.
	 * @param localFolderDiscoveryService  Service for discovering local persistent
	 *                                     folders.
	 * @param persistentObjectManager      Manager for persistence of objects.
	 * @param messageBroker                Broker for messaging within the system.
	 * @param consumingService             Service handling consumption of remote
	 *                                     filesystem resources.
	 * @param ingestionHandler             Handler for document reference ingestion.
	 */
	public GAbstractRemoteVirtualFilesystemContentManagementSystemHandler(
			IGBuildSystemHandlerRepositoryPattern buildSystemHandlerRepository,
			IGDocumentReferenceFactory contentHandler,
			IGContentManagementSystemConfigurationDao<SystemIntegrationType> configurationsDao,
			IGProjectEndpointRuntimeConfigurationDao<ProjectEndpointType> endpointsDao,
			IGLocalPersistentFolderDiscoveryService localFolderDiscoveryService,
			IGPersistentObjectManager persistentObjectManager, IGMessageBroker messageBroker,

			ConsumingServiceType consumingService, IGDocumentReferenceIngestionHandler ingestionHandler) {
		super(buildSystemHandlerRepository, contentHandler, configurationsDao, endpointsDao,
				localFolderDiscoveryService, persistentObjectManager, messageBroker, ingestionHandler);

		this.consumingService = consumingService;
	}

	/**
	 * Implementation of the abstract method consumeImplementation. Consumes content
	 * from the system, creating a root folder and passing it to the consumer.
	 *
	 * @param contentManagementConfig Configuration for the content management
	 *                                system.
	 * @param buildSystems            List of build systems involved.
	 * @param endpoint                Project endpoint to be used.
	 * @param consumer                Consumer for the content.
	 * @param messagesConsumer        Consumer for any user messages.
	 * @param errorConsumer           Consumer for handling content access errors.
	 * @throws GeboContentHandlerSystemException In case of system handling issues.
	 */
	@Override
	protected void consumeImplementation(SystemIntegrationType contentManagementConfig, List<GBuildSystem> buildSystems,
			ProjectEndpointType endpoint, IGContentConsumer consumer, IGUserMessagesConsumer messagesConsumer,
			IGContentsAccessErrorConsumer errorConsumer) throws GeboContentHandlerSystemException {

		// Create a root item for the virtual folder structure and consume it
		GVirtualFolder root = createRootItem(contentManagementConfig, endpoint);
		consumer.accept(root);

		// Delegate further consumption to the consuming service
		consumingService.consumeAll(contentManagementConfig, endpoint, root, consumer, messagesConsumer, errorConsumer);
	}

	// Constant keys used for caching
	private static final String PROJECT_ENDPOINT_REFERENCE = "PROJECT_ENDPOINT_REFERENCE";
	private static final String SYSTEM_REFERENCE = "SYSTEM_REFERENCE";

	/**
	 * Streams content associated with a document reference, leveraging caching
	 * mechanisms for performance optimization and managing remote resources.
	 *
	 * @param reference The reference of the document to be streamed.
	 * @param cache     Cache containing context-specific values.
	 * @return InputStream The stream of the content.
	 * @throws GeboContentHandlerSystemException For handler related exceptions.
	 * @throws IOException                       If IO operation fails.
	 */
	@Override
	public InputStream streamContent(GDocumentReference reference, Map<String, Object> cache)
			throws GeboContentHandlerSystemException, IOException {

		// Check if the reference is null
		if (reference == null)
			throw new RuntimeException("Cannot receive a null reference here");

		// Retrieve or resolve the project endpoint from the cache or the persistent
		// object manager
		ProjectEndpointType endpoint = (ProjectEndpointType) cache.get(PROJECT_ENDPOINT_REFERENCE);
		if (endpoint == null) {
			try {
				if (reference.getProjectEndpointReference() == null)
					throw new GeboContentHandlerSystemException("The document reference=>" + reference.getCode()
							+ " does not have an endpoint reference  ");
				endpoint = (ProjectEndpointType) persistentObjectManager
						.findByReference(reference.getProjectEndpointReference(), GProjectEndpoint.class);
				cache.put(PROJECT_ENDPOINT_REFERENCE, endpoint);
			} catch (GeboPersistenceException e) {
				throw new GeboContentHandlerSystemException("The reference=>"
						+ reference.getProjectEndpointReference().toString() + " cannot be retrieved ", e);
			}
		}

		// Retrieve or resolve the system from the cache for content management
		SystemIntegrationType system = (SystemIntegrationType) cache.get(SYSTEM_REFERENCE);
		if (system == null) {
			system = getSystem(endpoint);
			cache.put(SYSTEM_REFERENCE, system);
		}

		// Debugging log for developers to track the streaming process
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Obtaining remote handle for:[" + reference.getUri() + "] code:" + reference.getCode()
					+ " with meta resources map=>" + reference.getCustomMetaInfos());
		}

		// Obtain the resource handle from the consuming service
		ResourceReferenceType remoteReference = consumingService.getResourceHandle(system, endpoint, reference, cache);
		if (remoteReference == null)
			throw new RuntimeException("Cannot receive a null remote native reference here");

		// Further debugging statement for the streaming process
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Getting stream for [" + reference.getUri() + "] native remote reference=> " + remoteReference
					+ " document code:" + reference.getCode() + reference.getCustomMetaInfos());
		}
		return consumingService.streamResource(system, endpoint, remoteReference, cache);
	}

	/**
	 * Checks for any updates or deletions of virtual filesystem objects since the
	 * last check, leveraging the consuming service to determine such changes.
	 *
	 * @param endpoint      The project endpoint being examined.
	 * @param itemsToCheck  Stream of virtual filesystem objects to check for
	 *                      changes.
	 * @param errorConsumer Consumer for error handling during access checks.
	 * @return Stream of virtual filesystem objects that have been updated or
	 *         deleted.
	 * @throws GeboContentHandlerSystemException If errors occur during the check
	 *                                           process.
	 */
	@Override
	public Stream<GAbstractVirtualFilesystemObject> checkUpdatedOrDeleted(ProjectEndpointType endpoint,
			Stream<GAbstractVirtualFilesystemObject> itemsToCheck, IGContentsAccessErrorConsumer errorConsumer)
			throws GeboContentHandlerSystemException {

		// Retrieve system integration information for the endpoint
		SystemIntegrationType system = getSystem(endpoint);

		// Use the consuming service to check for updates or deletions
		return consumingService.checkUpdatedOrDeleted(system, endpoint, errorConsumer, itemsToCheck);
	}

	@Override
	public boolean isContentsOnLocalFilesystem() {

		return false;
	}
}