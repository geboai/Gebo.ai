/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.uploads.content.handler.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ai.gebo.application.messaging.IGMessageBroker;
import ai.gebo.application.messaging.model.GStandardModulesConstraints;
import ai.gebo.architecture.ai.IGReadableContentsFormatHandlerRepositoryPattern;
import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.architecture.multithreading.IGEntityProcessingRunnableFactoryRepositoryPattern;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.architecture.scheduling.services.IGSchedulingTimeService;
import ai.gebo.knlowledgebase.model.systems.GContentManagementSystemType;
import ai.gebo.security.services.IGSecurityService;
import ai.gebo.systems.abstraction.layer.controllers.GAbstractSystemsArchitectureController;
import ai.gebo.uploads.content.handler.GUploadsContentManagementSystem;
import ai.gebo.uploads.content.handler.GUploadsProjectEndpoint;
import ai.gebo.uploads.content.handler.IGUploadsContentManagementSystemHandler;
import ai.gebo.uploads.content.handler.impl.GUploadsContentManagementSystemHandlerImpl.GSingletonUploadsConfigurationDao;
import ai.gebo.uploads.content.handler.service.UploadsSystemsManagementServiceImpl;

/**
 * AI generated comments
 * 
 * Controller class that handles HTTP requests related to file uploads management.
 * This class provides endpoints for managing upload systems and endpoints.
 * Only users with ADMIN role can access these endpoints.
 */
@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping(value = "api/admin/FileUploadsController")
public class FileUploadsController
		extends GAbstractSystemsArchitectureController<GUploadsContentManagementSystem, GUploadsProjectEndpoint> {
	/**
	 * List of handlers for different upload content management systems
	 */
	@Autowired(required = false)
	List<IGUploadsContentManagementSystemHandler> handlers;
	
	/**
	 * Service for managing upload systems
	 */
	@Autowired
	UploadsSystemsManagementServiceImpl uploadsService;
	
	/**
	 * Data access object for uploads configuration
	 */
	@Autowired
	GSingletonUploadsConfigurationDao dao;
	
	/**
	 * Repository for handling readable content formats
	 */
	@Autowired
	IGReadableContentsFormatHandlerRepositoryPattern formatsRepoPattern;

	/**
	 * Nested emitter component for the file uploads controller
	 * Provides messaging functionality specific to uploads module
	 */
	@Component
	@Scope("singleton")
	public static class FilesystemsControllerEmitter extends ControllerNestedEmitter {

		/**
		 * Returns the messaging module ID for the uploads module
		 * @return the ID for the uploads module
		 */
		@Override
		public String getMessagingModuleId() {

			return GStandardModulesConstraints.UPLOADS_MODULE;
		}

	}

	/**
	 * Constructor for FileUploadsController
	 * 
	 * @param persistenceManager manages persistent objects
	 * @param messageBroker for handling messaging between components
	 * @param securityService for security-related operations
	 * @param controllerEmitter emitter for messaging events
	 * @param schedulingService service for scheduling tasks
	 * @param entityProcessingRunnableFactory factory for creating entity processing runnables
	 */
	public FileUploadsController(IGPersistentObjectManager persistenceManager, IGMessageBroker messageBroker,
			IGSecurityService securityService, FilesystemsControllerEmitter controllerEmitter, IGSchedulingTimeService schedulingService, IGEntityProcessingRunnableFactoryRepositoryPattern entityProcessingRunnableFactory) {
		super(persistenceManager, messageBroker, controllerEmitter, securityService,schedulingService,entityProcessingRunnableFactory);
	}

	/**
	 * Retrieves all available file system types from registered handlers
	 * 
	 * @return list of content management system types
	 */
	@GetMapping(value = "getFileSystemSystemTypes", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<GContentManagementSystemType> getFileSystemSystemTypes() {
		List<GContentManagementSystemType> list = new ArrayList<GContentManagementSystemType>();
		if (handlers != null) {
			for (IGUploadsContentManagementSystemHandler handler : handlers) {
				list.add(handler.getHandledSystemType());

			}
		}
		return list;
	}

	/**
	 * Retrieves upload systems, optionally filtered by handler code
	 * 
	 * @param handlerCode optional filter for specific handler type
	 * @return list of upload content management systems
	 */
	@GetMapping("getUploadsSystems")
	public List<GUploadsContentManagementSystem> getUploadsSystems(
			@RequestParam(name = "handlerCode", required = false) String handlerCode) {
		if (handlerCode != null && handlerCode.trim().length() > 0) {
			return dao.findListByPredicate((x) -> {
				return x.getContentManagementSystemType() != null
						&& x.getContentManagementSystemType().equals(handlerCode);
			});
		}
		return dao.getConfigurations();
	}

	/**
	 * Finds upload endpoints using query-by-example pattern
	 * 
	 * @param config example configuration to match
	 * @return list of matching upload project endpoints
	 * @throws GeboPersistenceException if persistence operation fails
	 */
	@PostMapping(value = "findUploadsEndpointsByQbe", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public List<GUploadsProjectEndpoint> findUploadsEndpointsByQbe(@RequestBody GUploadsProjectEndpoint config)
			throws GeboPersistenceException {
		return findEndpointByQbe(config);
	}

	/**
	 * Finds upload endpoints for a specific project
	 * 
	 * @param parentProjectCode code of the parent project
	 * @return list of upload project endpoints for the specified project
	 * @throws GeboPersistenceException if persistence operation fails
	 */
	@GetMapping(value = "findUploadsEndpointsByProject", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<GUploadsProjectEndpoint> findUploadsEndpointsByProject(
			@RequestParam("parentProjectCode") String parentProjectCode) throws GeboPersistenceException {
		GUploadsProjectEndpoint config = new GUploadsProjectEndpoint();
		config.setParentProjectCode(parentProjectCode);
		return findEndpointByQbe(config);
	}

	/**
	 * Updates an existing uploads endpoint
	 * 
	 * @param endpoint the endpoint with updated information
	 * @return the updated upload project endpoint
	 * @throws GeboPersistenceException if persistence operation fails
	 * @throws GeboContentHandlerSystemException if content handler operation fails
	 * @throws IOException if I/O operation fails
	 */
	@PostMapping(value = "updateUploadsEndpoint", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public GUploadsProjectEndpoint updateUploadsEndpoint(@RequestBody GUploadsProjectEndpoint endpoint)
			throws GeboPersistenceException, GeboContentHandlerSystemException, IOException {
		endpoint=persistentObjectManager.update(endpoint);
		return uploadsService.update(endpoint);
	}

	/**
	 * Inserts a new uploads endpoint
	 * 
	 * @param endpoint the new endpoint to insert
	 * @return the inserted upload project endpoint
	 * @throws GeboPersistenceException if persistence operation fails
	 * @throws GeboContentHandlerSystemException if content handler operation fails
	 * @throws IOException if I/O operation fails
	 */
	@PostMapping(value = "insertUploadsEndpoint", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public GUploadsProjectEndpoint insertUploadsEndpoint(@RequestBody GUploadsProjectEndpoint endpoint)
			throws GeboPersistenceException, GeboContentHandlerSystemException, IOException {
		return uploadsService.insert(endpoint);
	}

	/**
	 * Deletes an uploads endpoint
	 * 
	 * @param endpoint the endpoint to delete
	 * @throws GeboPersistenceException if persistence operation fails
	 */
	@PostMapping(value = "deleteUploadsEndpoint", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void deleteUploadsEndpoint(@RequestBody GUploadsProjectEndpoint endpoint) throws GeboPersistenceException {
		deleteEndpoint(endpoint);
	}

	/**
	 * Retrieves all supported file extensions for uploads
	 * 
	 * @return list of supported file extensions
	 */
	@GetMapping(value = "getUploadableFilesExtensions", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<String> getUploadableFilesExtensions() {
		return this.formatsRepoPattern.getHandledExtensions();
	}
}