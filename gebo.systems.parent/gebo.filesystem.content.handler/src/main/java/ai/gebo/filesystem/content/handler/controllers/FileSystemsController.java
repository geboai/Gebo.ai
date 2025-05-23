/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.filesystem.content.handler.controllers;

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
import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.architecture.multithreading.IGEntityProcessingRunnableFactoryRepositoryPattern;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.architecture.scheduling.services.IGSchedulingTimeService;
import ai.gebo.filesystem.content.handler.GFilesystemContentManagementSystem;
import ai.gebo.filesystem.content.handler.GFilesystemProjectEndpoint;
import ai.gebo.filesystem.content.handler.IGFilesystemContentManagementSystemHandler;
import ai.gebo.filesystem.content.handler.impl.GFilesystemChangesHandlingService;
import ai.gebo.filesystem.content.handler.impl.GFilesystemConfigurationDao;
import ai.gebo.filesystem.content.handler.service.FileSystemsManagementService;
import ai.gebo.knlowledgebase.model.systems.GContentManagementSystemType;
import ai.gebo.security.services.IGSecurityService;
import ai.gebo.systems.abstraction.layer.controllers.GAbstractSystemsArchitectureController;

/**
 * AI generated comments
 * 
 * REST controller that provides endpoints for administering file system configurations.
 * This controller extends GAbstractSystemsArchitectureController and is secured for admin access only.
 */
@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping(value = "api/admin/FileSystemsController")
public class FileSystemsController
		extends GAbstractSystemsArchitectureController<GFilesystemContentManagementSystem, GFilesystemProjectEndpoint> {
	/**
	 * List of filesystem content management system handlers
	 */
	@Autowired(required = false)
	List<IGFilesystemContentManagementSystemHandler> handlers;
	
	/**
	 * Service for managing file systems
	 */
	@Autowired
	FileSystemsManagementService fileSystemsService;
	
	/**
	 * Service for handling file system changes
	 */
	@Autowired
	GFilesystemChangesHandlingService changesHandlingService;
	
	/**
	 * Data access object for file system configurations
	 */
	@Autowired
	GFilesystemConfigurationDao dao;

	/**
	 * Nested component that handles messaging for the controller
	 */
	@Component
	@Scope("singleton")
	public static class FilesystemsControllerEmitter extends ControllerNestedEmitter {

		/**
		 * Gets the messaging module identifier
		 * @return The standard module ID for shared filesystem
		 */
		@Override
		public String getMessagingModuleId() {
			return GStandardModulesConstraints.SHARED_FILESYSTEM_MODULE;
		}
	}

	/**
	 * Constructor for FileSystemsController
	 * @param persistenceManager Manager for persistent objects
	 * @param messageBroker Broker for handling messages
	 * @param securityService Service for security operations
	 * @param controllerEmitter Emitter for controller events
	 * @param schedulingService Service for scheduling operations
	 * @param entityProcessingRunnableFactory Factory for creating entity processing runnables
	 */
	public FileSystemsController(IGPersistentObjectManager persistenceManager, IGMessageBroker messageBroker,
			IGSecurityService securityService, FilesystemsControllerEmitter controllerEmitter,
			IGSchedulingTimeService schedulingService,
			IGEntityProcessingRunnableFactoryRepositoryPattern entityProcessingRunnableFactory) {
		super(persistenceManager, messageBroker, controllerEmitter, securityService, schedulingService,
				entityProcessingRunnableFactory);
	}

	/**
	 * Retrieves all available file system types
	 * @return List of content management system types
	 */
	@GetMapping(value = "getFileSystemSystemTypes", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<GContentManagementSystemType> getFileSystemSystemTypes() {
		List<GContentManagementSystemType> list = new ArrayList<GContentManagementSystemType>();
		if (handlers != null) {
			for (IGFilesystemContentManagementSystemHandler handler : handlers) {
				list.add(handler.getHandledSystemType());
			}
		}
		return list;
	}

	/**
	 * Gets file system configurations, optionally filtered by handler code
	 * @param handlerCode Optional code to filter by specific handler type
	 * @return List of file system configurations
	 */
	@GetMapping("getFileSystemSystems")
	public List<GFilesystemContentManagementSystem> getFileSystemSystems(
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
	 * Finds file system endpoints using query by example
	 * @param config The example endpoint to match
	 * @return List of matching file system endpoints
	 * @throws GeboPersistenceException If there's an error during persistence operations
	 */
	@PostMapping(value = "findFileSystemEndpointsByQbe", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public List<GFilesystemProjectEndpoint> findFileSystemEndpointsByQbe(@RequestBody GFilesystemProjectEndpoint config)
			throws GeboPersistenceException {
		return findEndpointByQbe(config);
	}

	/**
	 * Finds file system endpoints by parent project code
	 * @param parentProjectCode The code of the parent project
	 * @return List of file system endpoints associated with the parent project
	 * @throws GeboPersistenceException If there's an error during persistence operations
	 */
	@GetMapping("findFileSystemEndpointsByProject")
	public List<GFilesystemProjectEndpoint> findFileSystemEndpointsByProject(
			@RequestParam("parentProjectCode") String parentProjectCode) throws GeboPersistenceException {
		GFilesystemProjectEndpoint config = new GFilesystemProjectEndpoint();
		config.setParentProjectCode(parentProjectCode);
		return findEndpointByQbe(config);
	}

	/**
	 * Updates an existing file system endpoint
	 * @param endpoint The endpoint with updated data
	 * @return The updated file system endpoint
	 * @throws GeboPersistenceException If there's an error during persistence operations
	 * @throws GeboContentHandlerSystemException If there's an error in the content handler system
	 * @throws IOException If there's an I/O error
	 */
	@PostMapping(value = "updateFilesystemEndpoint", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public GFilesystemProjectEndpoint updateFilesystemEndpoint(@RequestBody GFilesystemProjectEndpoint endpoint)
			throws GeboPersistenceException, GeboContentHandlerSystemException, IOException {
		return updateEndpoint(endpoint);
	}

	/**
	 * Inserts a new file system endpoint
	 * @param endpoint The new endpoint to insert
	 * @return The inserted file system endpoint
	 * @throws GeboPersistenceException If there's an error during persistence operations
	 * @throws GeboContentHandlerSystemException If there's an error in the content handler system
	 * @throws IOException If there's an I/O error
	 */
	@PostMapping(value = "insertFilesystemEndpoint", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public GFilesystemProjectEndpoint insertFilesystemEndpoint(@RequestBody GFilesystemProjectEndpoint endpoint)
			throws GeboPersistenceException, GeboContentHandlerSystemException, IOException {
		endpoint = insertEndpoint(endpoint);
		changesHandlingService.addHandling(endpoint);
		return endpoint;
	}

	/**
	 * Deletes a file system endpoint
	 * @param endpoint The endpoint to delete
	 * @throws GeboPersistenceException If there's an error during persistence operations
	 */
	@PostMapping(value = "deleteFilesystemEndpoint", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void deleteFilesystemEndpoint(@RequestBody GFilesystemProjectEndpoint endpoint)
			throws GeboPersistenceException {
		changesHandlingService.removeHandling(endpoint);
		deleteEndpoint(endpoint);
	}
}