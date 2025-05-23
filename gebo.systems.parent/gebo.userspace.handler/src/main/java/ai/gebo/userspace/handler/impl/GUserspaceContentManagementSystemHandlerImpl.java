/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.userspace.handler.impl;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import ai.gebo.application.messaging.IGMessageBroker;
import ai.gebo.application.messaging.model.GStandardModulesConstraints;
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
import ai.gebo.knowledgebase.repositories.DocumentReferenceRepository;
import ai.gebo.system.ingestion.IGDocumentReferenceIngestionHandler;
import ai.gebo.systems.abstraction.layer.GAbstractContentManagementSystemHandler;
import ai.gebo.systems.abstraction.layer.IGContentManagementSystemConfigurationDao;
import ai.gebo.systems.abstraction.layer.IGContentsAccessErrorConsumer;
import ai.gebo.systems.abstraction.layer.IGLocalPersistentFolderDiscoveryService;
import ai.gebo.systems.abstraction.layer.IGProjectEndpointRuntimeConfigurationDao;
import ai.gebo.userspace.handler.GUserspaceContentManagementSystem;
import ai.gebo.userspace.handler.GUserspaceFile;
import ai.gebo.userspace.handler.GUserspaceProjectEndpoint;
import ai.gebo.userspace.handler.IGUserspaceContentManagementSystemHandler;
import ai.gebo.userspace.handler.repository.UserspaceFileRepository;
import ai.gebo.userspace.handler.repository.UserspaceProjectEndpointRepository;

/**
 * Implementation of the userspace content management system handler.
 * This class manages userspace content by extending the abstract content management system handler.
 * AI generated comments
 */
@Service
public class GUserspaceContentManagementSystemHandlerImpl
		extends GAbstractContentManagementSystemHandler<GUserspaceContentManagementSystem, GUserspaceProjectEndpoint>
		implements IGUserspaceContentManagementSystemHandler {

	/**
	 * The constant content management system type for userspace.
	 */
	private static final GContentManagementSystemType type = new GContentManagementSystemType();
	
	/**
	 * The constant content management system for userspace.
	 */
	private static final GUserspaceContentManagementSystem system = new GUserspaceContentManagementSystem();
	
	/**
	 * Repository for userspace project endpoints.
	 */
	final UserspaceProjectEndpointRepository endpointsRepository;
	
	/**
	 * Repository for document references.
	 */
	final DocumentReferenceRepository documentRepository;
	
	/**
	 * Repository for userspace files.
	 */
	final UserspaceFileRepository filesRepository;
	
	/**
	 * Static initializer to set up the system type and system properties.
	 */
	static {
		type.setCode("USERSPACE-CONTENTSYSTEM-TYPE");
		type.setDescription("Userspace contents system");
		system.setCode("USERSPACE-CONTENTSYSTEM");
		system.setDescription("Userspace contents system");
		system.setContentManagementSystemType(type.getCode());
	}

	/**
	 * Constructor for the userspace content management system handler.
	 * 
	 * @param buildSystemHandlerRepository Repository for build system handlers
	 * @param contentHandler Factory for creating document references
	 * @param endpointsRepository Repository for userspace project endpoints
	 * @param localFolderDiscoveryService Service for discovering local folders
	 * @param persistentObjectManager Manager for persistent objects
	 * @param messageBroker Broker for messaging
	 * @param filesRepository Repository for userspace files
	 * @param documentRepository Repository for document references
	 * @param ingestionHandler Handler for document reference ingestion
	 */
	public GUserspaceContentManagementSystemHandlerImpl(
			IGBuildSystemHandlerRepositoryPattern buildSystemHandlerRepository,
			IGDocumentReferenceFactory contentHandler, UserspaceProjectEndpointRepository endpointsRepository,
			IGLocalPersistentFolderDiscoveryService localFolderDiscoveryService,
			IGPersistentObjectManager persistentObjectManager, IGMessageBroker messageBroker,
			UserspaceFileRepository filesRepository, DocumentReferenceRepository documentRepository, IGDocumentReferenceIngestionHandler ingestionHandler) {
		super(buildSystemHandlerRepository, contentHandler, IGContentManagementSystemConfigurationDao.of(system),
				IGProjectEndpointRuntimeConfigurationDao.of(endpointsRepository), localFolderDiscoveryService,
				persistentObjectManager, messageBroker, ingestionHandler);

		this.endpointsRepository = endpointsRepository;
		this.filesRepository = filesRepository;
		this.documentRepository = documentRepository;
	}

	/**
	 * Returns the content management system type that this handler deals with.
	 * 
	 * @return The content management system type
	 */
	@Override
	public GContentManagementSystemType getHandledSystemType() {
		return type;
	}

	/**
	 * Finds a project endpoint by system code and project endpoint code.
	 * 
	 * @param systemCode The system code
	 * @param projectEndpointCode The project endpoint code
	 * @return The userspace project endpoint
	 * @throws GeboContentHandlerSystemException If there's an error finding the endpoint
	 */
	@Override
	public GUserspaceProjectEndpoint findProjectEndPoint(String systemCode, String projectEndpointCode)
			throws GeboContentHandlerSystemException {
		return endpointsDao.findByCode(projectEndpointCode);
	}

	/**
	 * Checks if an endpoint is managed by this handler.
	 * 
	 * @param endpoint The project endpoint to check
	 * @return true if the endpoint is a GUserspaceProjectEndpoint, false otherwise
	 */
	@Override
	public boolean isManagedEndpoint(GProjectEndpoint endpoint) {
		return endpoint instanceof GUserspaceProjectEndpoint;
	}

	/**
	 * Gets the messaging module ID for this handler.
	 * 
	 * @return The userspace module ID from standard modules constraints
	 */
	@Override
	public String getMessagingModuleId() {
		return GStandardModulesConstraints.USERSPACE_MODULE;
	}

	/**
	 * Gets the content management system for a given project endpoint.
	 * 
	 * @param projectEndPoint The userspace project endpoint
	 * @return The userspace content management system
	 * @throws GeboContentHandlerSystemException If there's an error getting the system
	 */
	@Override
	public GUserspaceContentManagementSystem getSystem(GUserspaceProjectEndpoint projectEndPoint)
			throws GeboContentHandlerSystemException {
		return system;
	}

	/**
	 * Implements the content consumption for userspace content management system.
	 * This method handles the consumption of content from the userspace, creating folders if needed
	 * and processing deleted files.
	 * 
	 * @param contentManagementConfig The content management configuration
	 * @param buildSystems The list of build systems
	 * @param endpoint The userspace project endpoint
	 * @param consumer The content consumer
	 * @param messagesConsumer The user messages consumer
	 * @param errorConsumer The error consumer for content access errors
	 * @throws GeboContentHandlerSystemException If there's an error during consumption
	 */
	@Override
	protected void consumeImplementation(GUserspaceContentManagementSystem contentManagementConfig,
			List<GBuildSystem> buildSystems, GUserspaceProjectEndpoint endpoint, IGContentConsumer consumer,
			IGUserMessagesConsumer messagesConsumer,IGContentsAccessErrorConsumer errorConsumer) throws GeboContentHandlerSystemException {
		String folder = localFolderDiscoveryService.getLocalPersistentFolder(contentManagementConfig, endpoint);
		Path rootPath = Path.of(folder);
		if (!Files.exists(rootPath)) {
			File file = rootPath.toFile();
			file.mkdirs();
			file.mkdir();
		}
		GVirtualFolder root = createRootItem(contentManagementConfig, endpoint);
		consumer.accept(root);
		// here we have a look on deleted files
		List<GUserspaceFile> files = filesRepository.findByUserspaceEndpointCodeAndDeletedIsTrue(endpoint.getCode());
		if (!files.isEmpty()) {
			List<String> names = files.stream().map(x -> x.getName()).toList();
			Stream<GDocumentReference> docStream = documentRepository.findByParentVirtualFolderCodeAndNameIn(root.getCode(),names);
			List<GDocumentReference> toDelete = docStream.filter(x->(x.getDeleted()==null || !x.getDeleted())).toList();
			if (!toDelete.isEmpty()) {
				toDelete.stream().map(x->{
					x.setDeleted(true);
					return x;
				}).forEach(consumer);
			}
		}
		consume(root, contentManagementConfig, buildSystems, endpoint, rootPath, (t) -> true, consumer,
				messagesConsumer, errorConsumer);
	}
}