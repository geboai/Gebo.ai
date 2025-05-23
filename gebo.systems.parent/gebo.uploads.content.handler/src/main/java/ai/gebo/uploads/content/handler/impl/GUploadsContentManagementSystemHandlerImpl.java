/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.uploads.content.handler.impl;

import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.gebo.application.messaging.IGMessageBroker;
import ai.gebo.application.messaging.model.GStandardModulesConstraints;
import ai.gebo.architecture.buildsystems.abstraction.layer.IGBuildSystemHandlerRepositoryPattern;
import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.architecture.contenthandling.interfaces.IGContentConsumer;
import ai.gebo.architecture.contenthandling.interfaces.IGDocumentReferenceFactory;
import ai.gebo.architecture.contenthandling.interfaces.IGUserMessagesConsumer;
import ai.gebo.architecture.patterns.GAbstractRuntimeConfigurationDao;
import ai.gebo.architecture.patterns.IGDynamicConfigurationSource;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.knlowledgebase.model.contents.GVirtualFolder;
import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.knlowledgebase.model.systems.GBuildSystem;
import ai.gebo.knlowledgebase.model.systems.GContentManagementSystemType;
import ai.gebo.knlowledgebase.model.systems.GSystemRole;
import ai.gebo.system.ingestion.IGDocumentReferenceIngestionHandler;
import ai.gebo.systems.abstraction.layer.GAbstractContentManagementSystemHandler;
import ai.gebo.systems.abstraction.layer.IGContentManagementSystemConfigurationDao;
import ai.gebo.systems.abstraction.layer.IGContentsAccessErrorConsumer;
import ai.gebo.systems.abstraction.layer.IGLocalPersistentFolderDiscoveryService;
import ai.gebo.systems.abstraction.layer.IGProjectEndpointRuntimeConfigurationDao;
import ai.gebo.uploads.content.handler.GUploadsContentManagementSystem;
import ai.gebo.uploads.content.handler.GUploadsProjectEndpoint;
import ai.gebo.uploads.content.handler.IGUploadsContentManagementSystemHandler;

/**
 * AI generated comments
 * 
 * Implementation of a content management system handler for file uploads.
 * This class handles processing of file uploads as part of the Gebo AI system.
 * It extends the abstract content management system handler and implements
 * the uploads-specific interface.
 */
@Service
public class GUploadsContentManagementSystemHandlerImpl
		extends GAbstractContentManagementSystemHandler<GUploadsContentManagementSystem, GUploadsProjectEndpoint>
		implements IGUploadsContentManagementSystemHandler {

	/**
	 * Constructor for the uploads content management system handler.
	 * 
	 * @param buildSystemHandlerRepository Repository for build system handlers
	 * @param contentHandler Factory for document references
	 * @param configurationsDao Data access object for content management system configurations
	 * @param endpointsDao Data access object for project endpoints
	 * @param localFolderDiscoveryService Service to discover local folders
	 * @param persistentObjectManager Manager for persistent objects
	 * @param messageBroker Broker for system messaging
	 * @param ingestionHandler Handler for document reference ingestion
	 */
	public GUploadsContentManagementSystemHandlerImpl(
			IGBuildSystemHandlerRepositoryPattern buildSystemHandlerRepository,
			IGDocumentReferenceFactory contentHandler,

			IGContentManagementSystemConfigurationDao<GUploadsContentManagementSystem> configurationsDao,
			IGProjectEndpointRuntimeConfigurationDao<GUploadsProjectEndpoint> endpointsDao,
			IGLocalPersistentFolderDiscoveryService localFolderDiscoveryService,
			IGPersistentObjectManager persistentObjectManager, IGMessageBroker messageBroker,
			IGDocumentReferenceIngestionHandler ingestionHandler) {
		super(buildSystemHandlerRepository, contentHandler, configurationsDao, endpointsDao,
				localFolderDiscoveryService, persistentObjectManager, messageBroker, ingestionHandler);

	}

	/**
	 * Inner class that provides a singleton configuration data access object for uploads.
	 * Extends the abstract runtime configuration DAO and implements the content management
	 * system configuration DAO interface.
	 */
	@Service
	public static class GSingletonUploadsConfigurationDao
			extends GAbstractRuntimeConfigurationDao<GUploadsContentManagementSystem>
			implements IGContentManagementSystemConfigurationDao<GUploadsContentManagementSystem> {

		/**
		 * Constructor for the singleton uploads configuration DAO.
		 * 
		 * @param dynamic Optional dynamic configuration source
		 */
		public GSingletonUploadsConfigurationDao(
				@Autowired(required = false) IGDynamicConfigurationSource<GUploadsContentManagementSystem> dynamic) {
			super(List.of(system), dynamic);

		}

	}

	// Static configuration for the uploads content management system type
	private static final GContentManagementSystemType handledSystemType = new GContentManagementSystemType();
	private static final GUploadsContentManagementSystem system = new GUploadsContentManagementSystem();
	
	/**
	 * Static initializer to set up the system type and configuration
	 */
	static {
		handledSystemType.setCode("DEFAULT.UPLOADS.CONTENT.HANDLER");
		handledSystemType.setDescription("Generical uploads handler");
		handledSystemType.setCapabilities(List.of(GSystemRole.SOURCE_MANAGEMENT, GSystemRole.DOCUMENTS_MANAGEMENT));
		system.setContentManagementSystemType(handledSystemType.getCode());
		system.setCode("UPLOADS.HANDLER");
		system.setDescription("File uploads handler");

	}

	/**
	 * Returns the handled system type for this content handler.
	 * 
	 * @return The content management system type handled by this implementation
	 */
	@Override
	public GContentManagementSystemType getHandledSystemType() {

		return handledSystemType;
	}

	/**
	 * Implementation of the content consumption process.
	 * This method processes content from the specified endpoint and passes it to the consumer.
	 * 
	 * @param contentManagementConfig Configuration of the content management system
	 * @param buildSystems List of build systems
	 * @param endpoint The project endpoint to consume content from
	 * @param consumer Consumer that will receive content
	 * @param messagesConsumer Consumer for user messages
	 * @param errorConsumer Consumer for error messages
	 * @throws GeboContentHandlerSystemException If there's an error during content consumption
	 */
	@Override
	protected void consumeImplementation(GUploadsContentManagementSystem contentManagementConfig,
			List<GBuildSystem> buildSystems, GUploadsProjectEndpoint endpoint, IGContentConsumer consumer,
			IGUserMessagesConsumer messagesConsumer, IGContentsAccessErrorConsumer errorConsumer)
			throws GeboContentHandlerSystemException {

		GVirtualFolder rootItem = createRootItem(contentManagementConfig, endpoint);
		consumer.accept(rootItem);
		Path file = null;

		file = Path.of(this.localFolderDiscoveryService.getLocalPersistentFolder(contentManagementConfig, endpoint));

		// Predicate that accepts all paths
		Predicate<Path> predicate = new Predicate<Path>() {

			@Override
			public boolean test(Path t) {

				return true;
			}
		};
		
		// Verify the path exists before consuming
		if (Files.exists(file, LinkOption.NOFOLLOW_LINKS)) {
			this.consume(rootItem, contentManagementConfig, buildSystems, endpoint, file, predicate, consumer,
					messagesConsumer, errorConsumer);
		} else
			throw new GeboContentHandlerSystemException("The configured endpoint must lead to an existent directory");
	}

	/**
	 * Finds a project endpoint by system code and endpoint code.
	 * 
	 * @param systemCode The system code
	 * @param projectEndpointCode The project endpoint code
	 * @return The uploads project endpoint
	 * @throws GeboContentHandlerSystemException If the endpoint can't be found
	 */
	@Override
	public GUploadsProjectEndpoint findProjectEndPoint(String systemCode, String projectEndpointCode)
			throws GeboContentHandlerSystemException {

		return this.endpointsDao.findByCode(projectEndpointCode);
	}

	/**
	 * Checks if the given endpoint is managed by this handler.
	 * 
	 * @param endpoint The project endpoint to check
	 * @return true if this handler manages the endpoint, false otherwise
	 */
	@Override
	public boolean isManagedEndpoint(GProjectEndpoint endpoint) {

		return endpoint instanceof GUploadsProjectEndpoint;
	}

	/**
	 * Gets the content management system for a given project endpoint.
	 * 
	 * @param projectEndPoint The project endpoint
	 * @return The uploads content management system
	 * @throws GeboContentHandlerSystemException If the system can't be found
	 */
	@Override
	public GUploadsContentManagementSystem getSystem(GUploadsProjectEndpoint projectEndPoint)
			throws GeboContentHandlerSystemException {
		List<GUploadsContentManagementSystem> configs = this.configurationsDao.getConfigurations();
		return configs.isEmpty() ? null : configs.get(0);
	}

	/**
	 * Gets the messaging module ID for this handler.
	 * 
	 * @return The uploads module ID from standard modules constraints
	 */
	@Override
	public String getMessagingModuleId() {

		return GStandardModulesConstraints.UPLOADS_MODULE;
	}

}