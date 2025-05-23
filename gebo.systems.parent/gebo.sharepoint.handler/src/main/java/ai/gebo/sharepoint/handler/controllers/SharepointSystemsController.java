/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.sharepoint.handler.controllers;

/**
 * AI generated comments
 * 
 * Controller class for managing SharePoint systems and endpoints.
 * Provides REST APIs for CRUD operations on SharePoint systems and their endpoints.
 * All endpoints are secured with ADMIN role permissions.
 */
import java.util.Date;
import java.util.List;

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
import ai.gebo.architecture.multithreading.IGEntityProcessingRunnableFactoryRepositoryPattern;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.architecture.scheduling.services.IGSchedulingTimeService;
import ai.gebo.knlowledgebase.model.systems.GContentManagementSystemType;
import ai.gebo.knlowledgebase.model.systems.GSystemRole;
import ai.gebo.model.GUserMessage;
import ai.gebo.model.OperationStatus;
import ai.gebo.restintegration.abstraction.layer.RestTemplateWrapperService;
import ai.gebo.secrets.model.AbstractGeboSecretContent;
import ai.gebo.secrets.model.GeboOauth2SecretContent;
import ai.gebo.secrets.services.IGeboSecretsAccessService;
import ai.gebo.security.services.IGSecurityService;
import ai.gebo.sharepoint.handler.GSharepointContentManagementSystem;
import ai.gebo.sharepoint.handler.GSharepointProjectEndpoint;
import ai.gebo.sharepoint.handler.IGSharepointContentManagementSystemHandler;
import ai.gebo.sharepoint.handler.SharepointVersion;
import ai.gebo.sharepoint.handler.impl.SharepointSystemsTestService;
import ai.gebo.sharepoint.handler.repositories.SharepointProjectEndpointRepository;
import ai.gebo.systems.abstraction.layer.controllers.GAbstractSystemsArchitectureController;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping(value = "api/admin/SharepointSystemsController")
public class SharepointSystemsController
		extends GAbstractSystemsArchitectureController<GSharepointContentManagementSystem, GSharepointProjectEndpoint> {
	
	/**
	 * Nested emitter component responsible for message broadcasting
	 * within the SharePoint module.
	 */
	@Component
	@Scope("singleton")
	public static class SharepointControllerEmitter extends ControllerNestedEmitter {

		/**
		 * Returns the messaging module ID for SharePoint-related messages.
		 *
		 * @return The standard module ID for SharePoint
		 */
		@Override
		public String getMessagingModuleId() {

			return GStandardModulesConstraints.SHAREPOINT_MODULE;
		}

	}

	private final IGSharepointContentManagementSystemHandler handler;
	private final SharepointProjectEndpointRepository endpointRepository;
	private final IGeboSecretsAccessService secretAccessService;
	private final RestTemplateWrapperService restTemplateWrapper;
	private final SharepointSystemsTestService sharepointTestService;

	/**
	 * Constructor that initializes the SharePoint systems controller with required dependencies.
	 *
	 * @param persistentObjectManager Manages persistent objects storage
	 * @param messageBroker For message communication across modules
	 * @param controllerEmitter SharePoint-specific emitter for message broadcasting
	 * @param securityService Provides security functionality
	 * @param handler Handler for SharePoint CMS interactions
	 * @param endpointRepository Repository for SharePoint project endpoints
	 * @param secretAccessService Service for accessing secrets
	 * @param schedulingService Service for scheduling operations
	 * @param entityProcessingRunnableFactory Factory for entity processing threads
	 * @param restTemplateWrapper Wrapper for REST template operations
	 * @param SharepointTestService Service for testing SharePoint connections
	 */
	public SharepointSystemsController(IGPersistentObjectManager persistentObjectManager, IGMessageBroker messageBroker,
			SharepointControllerEmitter controllerEmitter, IGSecurityService securityService,
			IGSharepointContentManagementSystemHandler handler, SharepointProjectEndpointRepository endpointRepository,
			IGeboSecretsAccessService secretAccessService, IGSchedulingTimeService schedulingService,
			IGEntityProcessingRunnableFactoryRepositoryPattern entityProcessingRunnableFactory,
			RestTemplateWrapperService restTemplateWrapper, SharepointSystemsTestService SharepointTestService) {
		super(persistentObjectManager, messageBroker, controllerEmitter, securityService, schedulingService,
				entityProcessingRunnableFactory);
		this.handler = handler;
		this.endpointRepository = endpointRepository;
		this.secretAccessService = secretAccessService;
		this.restTemplateWrapper = restTemplateWrapper;
		this.sharepointTestService = SharepointTestService;
	}

	/**
	 * Retrieves the SharePoint system type information.
	 *
	 * @return The content management system type for SharePoint
	 */
	@GetMapping(value = "getSharepointSystemType", produces = MediaType.APPLICATION_JSON_VALUE)
	public GContentManagementSystemType getSharepointSystemTypes() {

		return handler.getHandledSystemType();
	}

	/**
	 * Gets all configured SharePoint systems.
	 *
	 * @return List of all SharePoint system configurations
	 */
	@GetMapping("getSharepointSystems")
	public List<GSharepointContentManagementSystem> getSharepointSystems() {

		return handler.getConfigurations();
	}

	/**
	 * Finds SharePoint endpoints using query-by-example pattern.
	 *
	 * @param config The example configuration to match
	 * @return List of matching SharePoint project endpoints
	 * @throws GeboPersistenceException If there's an error accessing the persistence layer
	 */
	@PostMapping(value = "findSharepointEndpointsByQbe", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public List<GSharepointProjectEndpoint> findSharepointEndpointsByQbe(@RequestBody GSharepointProjectEndpoint config)
			throws GeboPersistenceException {
		return findEndpointByQbe(config);
	}

	/**
	 * Finds SharePoint endpoints by parent project code.
	 *
	 * @param parentProjectCode The code of the parent project
	 * @return List of SharePoint project endpoints for the specified project
	 * @throws GeboPersistenceException If there's an error accessing the persistence layer
	 */
	@GetMapping("findSharepointEndpointsByProject")
	public List<GSharepointProjectEndpoint> findSharepointEndpointsByProject(
			@RequestParam("parentProjectCode") String parentProjectCode) throws GeboPersistenceException {

		return endpointRepository.findByParentProjectCode(parentProjectCode);
	}

	/**
	 * Retrieves a specific SharePoint endpoint by its code.
	 *
	 * @param code The endpoint code to find
	 * @return The matching SharePoint project endpoint
	 * @throws GeboPersistenceException If there's an error accessing the persistence layer
	 */
	@GetMapping("findSharepointEndpointsByCode")
	public GSharepointProjectEndpoint findSharepointEndpointsByCode(@RequestParam("code") String code)
			throws GeboPersistenceException {

		return persistentObjectManager.findById(GSharepointProjectEndpoint.class, code);
	}

	/**
	 * Retrieves a specific SharePoint system by its code.
	 *
	 * @param code The system code to find
	 * @return The matching SharePoint content management system
	 * @throws GeboPersistenceException If there's an error accessing the persistence layer
	 */
	@GetMapping("findSharepointSystemByCode")
	public GSharepointContentManagementSystem findSharepointSystemByCode(@RequestParam("code") String code)
			throws GeboPersistenceException {

		return persistentObjectManager.findById(GSharepointContentManagementSystem.class, code);
	}

	/**
	 * Updates an existing SharePoint system configuration after testing its connectivity.
	 *
	 * @param object The SharePoint system configuration to update
	 * @return Operation status containing the updated system or error messages
	 * @throws GeboPersistenceException If there's an error accessing the persistence layer
	 */
	@PostMapping(value = "updateSharepointSystem", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<GSharepointContentManagementSystem> updateSharepointSystem(
			@RequestBody GSharepointContentManagementSystem object) throws GeboPersistenceException {
		OperationStatus<GSharepointContentManagementSystem> os = sharepointTestService.testSharepointSystem(object);
		if (os.isHasErrorMessages()) {
			return os;
		}
		return OperationStatus.of(updateSystem(object));
	}

	/**
	 * Inserts a new SharePoint system configuration after testing its connectivity.
	 *
	 * @param object The SharePoint system configuration to insert
	 * @return Operation status containing the inserted system or error messages
	 * @throws GeboPersistenceException If there's an error accessing the persistence layer
	 */
	@PostMapping(value = "insertSharepointSystem", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<GSharepointContentManagementSystem> insertSharepointSystem(
			@RequestBody GSharepointContentManagementSystem object) throws GeboPersistenceException {
		OperationStatus<GSharepointContentManagementSystem> os = sharepointTestService.testSharepointSystem(object);
		if (os.isHasErrorMessages()) {
			return os;
		}

		return OperationStatus.of(insertSystem(object));
	}

	/**
	 * Tests a SharePoint system configuration for connectivity without saving it.
	 *
	 * @param object The SharePoint system configuration to test
	 * @return Operation status with test results
	 * @throws GeboPersistenceException If there's an error accessing the persistence layer
	 */
	@PostMapping(value = "testSharepointSystem", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<GSharepointContentManagementSystem> testSharepointSystem(
			@RequestBody GSharepointContentManagementSystem object) throws GeboPersistenceException {
		return sharepointTestService.testSharepointSystem(object);
	}

	/**
	 * Deletes a SharePoint system configuration.
	 *
	 * @param object The SharePoint system configuration to delete
	 * @throws GeboPersistenceException If there's an error accessing the persistence layer
	 */
	@PostMapping(value = "deleteSharepointSystem", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void deleteSharepointSystem(@RequestBody GSharepointContentManagementSystem object)
			throws GeboPersistenceException {
		this.deleteSystem(object);
	}

	/**
	 * Updates an existing SharePoint project endpoint.
	 *
	 * @param endpoint The SharePoint project endpoint to update
	 * @return The updated SharePoint project endpoint
	 * @throws GeboPersistenceException If there's an error accessing the persistence layer
	 */
	@PostMapping(value = "updateSharepointEndpoint", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public GSharepointProjectEndpoint updateSharepointEndpoint(@RequestBody GSharepointProjectEndpoint endpoint)
			throws GeboPersistenceException {
		return updateEndpoint(endpoint);
	}

	/**
	 * Inserts a new SharePoint project endpoint.
	 *
	 * @param endpoint The SharePoint project endpoint to insert
	 * @return The inserted SharePoint project endpoint
	 * @throws GeboPersistenceException If there's an error accessing the persistence layer
	 */
	@PostMapping(value = "insertSharepointEndpoint", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public GSharepointProjectEndpoint insertSharepointEndpoint(@RequestBody GSharepointProjectEndpoint endpoint)
			throws GeboPersistenceException {
		return insertEndpoint(endpoint);
	}

	/**
	 * Deletes a SharePoint project endpoint.
	 *
	 * @param endpoint The SharePoint project endpoint to delete
	 * @throws GeboPersistenceException If there's an error accessing the persistence layer
	 */
	@PostMapping(value = "deleteSharepointEndpoint", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void deleteSharepointEndpoint(@RequestBody GSharepointProjectEndpoint endpoint)
			throws GeboPersistenceException {
		deleteEndpoint(endpoint);
	}

	/**
	 * Data transfer object for quickly setting up a SharePoint system with minimal required information.
	 */
	public static class FastSharepointSystemInsertRequest {
		public String baseUri = null;
		@NotNull
		public String description = null;
		@NotNull
		public SharepointVersion sharepointVersion = null;
		@NotNull
		public GeboOauth2SecretContent oauth2Credentials = null;

	}

	/**
	 * Provides a simplified way to configure a SharePoint system with essential details.
	 * Creates the system config, stores credentials, and tests the connection.
	 *
	 * @param data Request containing essential SharePoint configuration
	 * @return Operation status with the created system or error messages
	 */
	@PostMapping(value = "fastSharepointConfig", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<GSharepointContentManagementSystem> fastSharepointConfig(
			@Valid @RequestBody FastSharepointSystemInsertRequest data) {
		try {
			AbstractGeboSecretContent secret = data.oauth2Credentials;

			String description = "Sharepoint " + data.baseUri + " Sharepoint system secret";
			String secretId = secretAccessService.storeSecret(secret, description,
					getSharepointSystemTypes().getCode());
			;
			GSharepointContentManagementSystem system = new GSharepointContentManagementSystem();
			system.setBaseUri(data.baseUri);
			system.setDescription(data.description);
			system.setSecretCode(secretId);
			system.setUsedCapabilities(List.of(GSystemRole.DOCUMENTS_MANAGEMENT));
			system.setCreationDate(new Date());
			system.setSharepointVersion(data.sharepointVersion);
			system.setModificationDate(new Date());
			system.setContentManagementSystemType(getSharepointSystemTypes().getCode());
			OperationStatus<GSharepointContentManagementSystem> status = sharepointTestService
					.testSharepointSystem(system);
			if (status.isHasErrorMessages()) {
				return status;
			}
			system = persistentObjectManager.insert(system);
			return OperationStatus.of(system);
		} catch (Throwable th) {
			// ResourceAccessException
			LOGGER.error("Error trying inserting Sharepoint system configuration", th);
			OperationStatus<GSharepointContentManagementSystem> os = new OperationStatus<GSharepointContentManagementSystem>();
			os.getMessages().add(GUserMessage.errorMessage("Cannot access Sharepoint", ""));
			return os;
		}
	}
}