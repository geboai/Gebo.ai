/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.googledrive.handlers.controllers;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ai.gebo.application.messaging.IGMessageBroker;
import ai.gebo.architecture.multithreading.IGEntityProcessingRunnableFactoryRepositoryPattern;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.architecture.scheduling.services.IGSchedulingTimeService;
import ai.gebo.crypting.services.GeboCryptSecretException;
import ai.gebo.googledrive.handlers.GGoogleDriveProjectEndpoint;
import ai.gebo.googledrive.handlers.GGoogleDriveSystem;
import ai.gebo.googledrive.handlers.IGGoogleDriveSystemContentHandler;
import ai.gebo.googledrive.handlers.impl.GGoogleDriveSystemContentHandlerImpl;
import ai.gebo.googledrive.handlers.impl.GoogleDriveTestService;
import ai.gebo.googledrive.handlers.repositories.GoogleDriveProjectEndpointRepository;
import ai.gebo.knlowledgebase.model.systems.GContentManagementSystemType;
import ai.gebo.model.OperationStatus;
import ai.gebo.secrets.model.GeboGoogleJsonSecretContent;
import ai.gebo.secrets.services.IGeboSecretsAccessService;
import ai.gebo.security.services.IGSecurityService;
import ai.gebo.systems.abstraction.layer.controllers.GAbstractSystemsArchitectureController;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/**
 * REST controller for managing Google Drive systems and project endpoints.
 * AI generated comments
 * This controller handles CRUD operations for Google Drive systems and their associated project endpoints.
 * Access is restricted to users with the ADMIN role.
 */
@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping(value = "api/admin/GoogleDriveSystemsController")
public class GoogleDriveSystemsController
		extends GAbstractSystemsArchitectureController<GGoogleDriveSystem, GGoogleDriveProjectEndpoint> {
	/**
	 * Context identifier for Google Workspace secrets
	 */
	public static final String GOOGLE_WORKSPACE_CONTEXT = "GOOGLE_WORKSPACE_CONTEXT";
	
	/**
	 * Handler for Google Drive system content operations
	 */
	final IGGoogleDriveSystemContentHandler googleDriveHandler;
	
	/**
	 * Repository for Google Drive project endpoints
	 */
	final GoogleDriveProjectEndpointRepository endpointRepository;
	
	/**
	 * Service for testing Google Drive configurations
	 */
	final GoogleDriveTestService testService;
	
	/**
	 * Service for accessing Gebo secrets
	 */
	final IGeboSecretsAccessService secretAccessService;

	/**
	 * Constructor initializing all required dependencies
	 * 
	 * @param persistentObjectManager Object manager for persistence operations
	 * @param messageBroker Message broker for application messaging
	 * @param controllerEmitter Emitter for controller events
	 * @param securityService Security service for authentication/authorization
	 * @param googleDriveHandler Handler for Google Drive operations
	 * @param endpointRepository Repository for project endpoints
	 * @param schedulingService Service for scheduling operations
	 * @param entityProcessingRunnableFactory Factory for entity processing runnables
	 * @param testService Service for testing configurations
	 * @param secretAccessService Service for accessing secrets
	 */
	public GoogleDriveSystemsController(IGPersistentObjectManager persistentObjectManager,
			IGMessageBroker messageBroker, GoogleDriveSystemsNestedEmitter controllerEmitter,
			IGSecurityService securityService, IGGoogleDriveSystemContentHandler googleDriveHandler,
			GoogleDriveProjectEndpointRepository endpointRepository, IGSchedulingTimeService schedulingService,
			IGEntityProcessingRunnableFactoryRepositoryPattern entityProcessingRunnableFactory,
			GoogleDriveTestService testService, IGeboSecretsAccessService secretAccessService) {
		super(persistentObjectManager, messageBroker, controllerEmitter, securityService, schedulingService,
				entityProcessingRunnableFactory);
		this.googleDriveHandler = googleDriveHandler;
		this.endpointRepository = endpointRepository;
		this.testService = testService;
		this.secretAccessService = secretAccessService;

	}

	/**
	 * Retrieves the Google Drive system type
	 * 
	 * @return The content management system type for Google Drive
	 */
	@GetMapping(value = "getGoogleDriveSystemType", produces = MediaType.APPLICATION_JSON_VALUE)
	public GContentManagementSystemType getGoogleDriveSystemType() {

		return googleDriveHandler.getHandledSystemType();
	}

	/**
	 * Retrieves all configured Google Drive systems
	 * 
	 * @return List of Google Drive system configurations
	 */
	@GetMapping("getGoogleDriveSystems")
	public List<GGoogleDriveSystem> getGoogleDriveSystems() {

		return googleDriveHandler.getConfigurations();
	}

	/**
	 * Finds a Google Drive system by its unique code
	 * 
	 * @param code The unique identifier for the system
	 * @return The matching Google Drive system
	 * @throws GeboPersistenceException If there's an error retrieving the system
	 */
	@GetMapping(value = "findGoogleDriveSystemByCode", produces = MediaType.APPLICATION_JSON_VALUE)
	public GGoogleDriveSystem findGoogleDriveSystemByCode(@RequestParam("code") String code)
			throws GeboPersistenceException {
		return super.persistentObjectManager.findById(GGoogleDriveSystem.class, code);
	}

	/**
	 * Finds a Google Drive project endpoint by its unique code
	 * 
	 * @param code The unique identifier for the project endpoint
	 * @return The matching Google Drive project endpoint
	 * @throws GeboPersistenceException If there's an error retrieving the endpoint
	 */
	@GetMapping(value = "findGoogleDriveProjectEndpointByCode", produces = MediaType.APPLICATION_JSON_VALUE)
	public GGoogleDriveProjectEndpoint findGoogleDriveProjectEndpointByCode(@RequestParam("code") String code)
			throws GeboPersistenceException {
		return super.persistentObjectManager.findById(GGoogleDriveProjectEndpoint.class, code);
	}

	/**
	 * Finds Google Drive endpoints by example (query by example)
	 * 
	 * @param config The example configuration to match
	 * @return A list of matching Google Drive project endpoints
	 * @throws GeboPersistenceException If there's an error during the query
	 */
	@PostMapping(value = "findGoogleDriveEndpointsByQbe", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public List<GGoogleDriveProjectEndpoint> findGoogleDriveEndpointsByQbe(
			@RequestBody GGoogleDriveProjectEndpoint config) throws GeboPersistenceException {
		return findEndpointByQbe(config);
	}

	/**
	 * Finds Google Drive endpoints associated with a specific project
	 * 
	 * @param parentProjectCode The code of the parent project
	 * @return A list of Google Drive project endpoints for the specified project
	 * @throws GeboPersistenceException If there's an error during retrieval
	 */
	@GetMapping("findGoogleDriveEndpointsByProject")
	public List<GGoogleDriveProjectEndpoint> findGoogleDriveEndpointsByProject(
			@RequestParam("parentProjectCode") String parentProjectCode) throws GeboPersistenceException {

		return endpointRepository.findByParentProjectCode(parentProjectCode);
	}

	/**
	 * Updates an existing Google Drive system configuration after testing it
	 * 
	 * @param object The Google Drive system to update
	 * @return Operation status with the updated system or error messages
	 * @throws GeboPersistenceException If there's an error during the update
	 */
	@PostMapping(value = "updateGoogleDriveSystem", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<GGoogleDriveSystem> updateGoogleDriveSystem(@RequestBody GGoogleDriveSystem object)
			throws GeboPersistenceException {
		OperationStatus<GGoogleDriveSystem> status = testService.test(object);
		if (!status.isHasErrorMessages()) {
			status.setResult(updateSystem(object));
		}
		return status;
	}

	/**
	 * Inserts a new Google Drive system configuration after testing it
	 * 
	 * @param object The Google Drive system to insert
	 * @return Operation status with the inserted system or error messages
	 * @throws GeboPersistenceException If there's an error during insertion
	 */
	@PostMapping(value = "insertGoogleDriveSystem", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<GGoogleDriveSystem> insertGoogleDriveSystem(@RequestBody GGoogleDriveSystem object)
			throws GeboPersistenceException {
		OperationStatus<GGoogleDriveSystem> status = testService.test(object);
		if (!status.isHasErrorMessages()) {
			status.setResult(insertSystem(object));
		}
		return status;
	}

	/**
	 * Deletes a Google Drive system configuration
	 * 
	 * @param object The Google Drive system to delete
	 * @throws GeboPersistenceException If there's an error during deletion
	 */
	@PostMapping(value = "deleteGoogleDriveSystem", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void deleteGoogleDriveSystem(@RequestBody GGoogleDriveSystem object) throws GeboPersistenceException {
		this.deleteSystem(object);
	}

	/**
	 * Updates an existing Google Drive project endpoint
	 * 
	 * @param endpoint The project endpoint to update
	 * @return The updated Google Drive project endpoint
	 * @throws GeboPersistenceException If there's an error during the update
	 */
	@PostMapping(value = "updateGoogleDriveProjectEndpoint", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public GGoogleDriveProjectEndpoint updateGoogleDriveProjectEndpoint(
			@RequestBody GGoogleDriveProjectEndpoint endpoint) throws GeboPersistenceException {
		return updateEndpoint(endpoint);
	}

	/**
	 * Inserts a new Google Drive project endpoint
	 * 
	 * @param endpoint The project endpoint to insert
	 * @return The inserted Google Drive project endpoint
	 * @throws GeboPersistenceException If there's an error during insertion
	 */
	@PostMapping(value = "insertGoogleDriveProjectEndpoint", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public GGoogleDriveProjectEndpoint insertGoogleDriveProjectEndpoint(
			@RequestBody GGoogleDriveProjectEndpoint endpoint) throws GeboPersistenceException {
		return insertEndpoint(endpoint);
	}

	/**
	 * Deletes a Google Drive project endpoint
	 * 
	 * @param endpoint The project endpoint to delete
	 * @throws GeboPersistenceException If there's an error during deletion
	 */
	@PostMapping(value = "deleteGoogleDriveProjectEndpoint", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void deleteGoogleDriveProjectEndpoint(@RequestBody GGoogleDriveProjectEndpoint endpoint)
			throws GeboPersistenceException {
		deleteEndpoint(endpoint);
	}

	/**
	 * Data class for fast creation of a Google Drive system with minimal required information
	 */
	public static class FastGoogleDriveSystemInsert {
		@NotNull
		public String description = null;
		@NotNull
		public GeboGoogleJsonSecretContent googleJsonCredentials = null;
	}

	/**
	 * Creates a Google Drive configuration quickly with minimal input
	 * 
	 * @param data The minimal required data for creating a Google Drive system
	 * @return Operation status with the created system or error messages
	 * @throws GeboCryptSecretException If there's an error storing the credentials
	 * @throws GeboPersistenceException If there's an error persisting the system
	 */
	@PostMapping(value = "fastGoogleDriveConfig", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<GGoogleDriveSystem> fastGoogleDriveConfig(
			@NotNull @Valid @RequestBody FastGoogleDriveSystemInsert data)
			throws GeboCryptSecretException, GeboPersistenceException {
		OperationStatus<GGoogleDriveSystem> status = null;
		String secretCode = secretAccessService.storeSecret(data.googleJsonCredentials, "Google drive account",
				GOOGLE_WORKSPACE_CONTEXT);
		GGoogleDriveSystem system = new GGoogleDriveSystem();
		system.setDescription(data.description);
		system.setContentManagementSystemType(GGoogleDriveSystemContentHandlerImpl.GOOGLE_DRIVE_HANDLER);
		system.setDriveAccessSecret(secretCode);
		status = testService.test(system);
		if (!status.isHasErrorMessages()) {
			system = insertSystem(system);
			status.setResult(system);
		}
		return status;
	}
}