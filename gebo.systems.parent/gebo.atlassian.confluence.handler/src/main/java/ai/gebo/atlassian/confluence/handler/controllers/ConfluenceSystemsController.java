/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.atlassian.confluence.handler.controllers;

/**
 * AI generated comments
 * 
 * Controller handling Confluence system configurations and operations within the Gebo AI platform.
 * This controller provides endpoints for managing Confluence systems and project endpoints,
 * allowing administrators to configure, test, and interact with Confluence instances.
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
import ai.gebo.atlassian.confluence.cloud.client.CloudConfluenceConnection;
import ai.gebo.atlassian.confluence.cloud.client.CloudConfluenceSpaceApi;
import ai.gebo.atlassian.confluence.handler.ConfluenceVersion;
import ai.gebo.atlassian.confluence.handler.GConfluenceProjectEndpoint;
import ai.gebo.atlassian.confluence.handler.GConfluenceSystem;
import ai.gebo.atlassian.confluence.handler.IGConfluenceContentManagementHandler;
import ai.gebo.atlassian.confluence.handler.impl.ConfluenceContentManagementHandlerImpl;
import ai.gebo.atlassian.confluence.handler.impl.ConfluenceSystemsTestService;
import ai.gebo.atlassian.confluence.handler.repositories.ConfluenceProjectEndpointRepository;
import ai.gebo.atlassian.confluence.onpremise.client.OnPremiseConfluenceConnection;
import ai.gebo.atlassian.confluence.onpremise.client.OnPremiseConfluenceSpaceApi;
import ai.gebo.knlowledgebase.model.systems.GContentManagementSystemType;
import ai.gebo.knlowledgebase.model.systems.GSystemRole;
import ai.gebo.model.GUserMessage;
import ai.gebo.model.OperationStatus;
import ai.gebo.restintegration.abstraction.layer.RestTemplateWrapperService;
import ai.gebo.secrets.model.AbstractGeboSecretContent;
import ai.gebo.secrets.model.GeboTokenContent;
import ai.gebo.secrets.model.GeboUsernamePasswordContent;
import ai.gebo.secrets.services.IGeboSecretsAccessService;
import ai.gebo.security.services.IGSecurityService;
import ai.gebo.systems.abstraction.layer.controllers.GAbstractSystemsArchitectureController;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping(value = "api/admin/ConfluenceSystemsController")
public class ConfluenceSystemsController
		extends GAbstractSystemsArchitectureController<GConfluenceSystem, GConfluenceProjectEndpoint> {
	
	/**
	 * Nested emitter component for Confluence controller messaging.
	 * This singleton component defines the messaging module ID for Confluence operations.
	 */
	@Component
	@Scope("singleton")
	public static class ConfluenceControllerEmitter extends ControllerNestedEmitter {

		@Override
		public String getMessagingModuleId() {
			return GStandardModulesConstraints.ATLASSIAN_CONFLUENCE_MODULE;
		}

	}

	private final IGConfluenceContentManagementHandler handler;
	private final ConfluenceProjectEndpointRepository endpointRepository;
	private final IGeboSecretsAccessService secretAccessService;
	private final RestTemplateWrapperService restTemplateWrapper;
	private final ConfluenceSystemsTestService confluenceTestService;

	/**
	 * Constructor for ConfluenceSystemsController with dependency injection.
	 * 
	 * @param persistentObjectManager Database access manager
	 * @param messageBroker Messaging service for system events
	 * @param controllerEmitter Confluence-specific message emitter
	 * @param securityService Security service for authorization
	 * @param handler Confluence content management handler
	 * @param endpointRepository Repository for Confluence endpoints
	 * @param secretAccessService Service for managing credentials
	 * @param schedulingService Service for scheduling operations
	 * @param entityProcessingRunnableFactory Factory for processing runnables
	 * @param restTemplateWrapper HTTP client wrapper
	 * @param confluenceTestService Service for testing Confluence connections
	 */
	public ConfluenceSystemsController(IGPersistentObjectManager persistentObjectManager, IGMessageBroker messageBroker,
			ConfluenceControllerEmitter controllerEmitter, IGSecurityService securityService,
			IGConfluenceContentManagementHandler handler, ConfluenceProjectEndpointRepository endpointRepository,
			IGeboSecretsAccessService secretAccessService, IGSchedulingTimeService schedulingService,
			IGEntityProcessingRunnableFactoryRepositoryPattern entityProcessingRunnableFactory,
			RestTemplateWrapperService restTemplateWrapper, ConfluenceSystemsTestService confluenceTestService) {
		super(persistentObjectManager, messageBroker, controllerEmitter, securityService, schedulingService,
				entityProcessingRunnableFactory);
		this.handler = handler;
		this.endpointRepository = endpointRepository;
		this.secretAccessService = secretAccessService;
		this.restTemplateWrapper = restTemplateWrapper;
		this.confluenceTestService = confluenceTestService;
	}

	/**
	 * Retrieves the Confluence system type details.
	 * 
	 * @return The content management system type for Confluence
	 */
	@GetMapping(value = "getConfluenceSystemType", produces = MediaType.APPLICATION_JSON_VALUE)
	public GContentManagementSystemType getConfluenceSystemTypes() {
		return handler.getHandledSystemType();
	}

	/**
	 * Retrieves all configured Confluence systems.
	 * 
	 * @return List of all Confluence system configurations
	 */
	@GetMapping("getConfluenceSystems")
	public List<GConfluenceSystem> getConfluenceSystems() {
		return handler.getConfigurations();
	}

	/**
	 * Finds Confluence endpoints by query-by-example method.
	 * 
	 * @param config The example endpoint configuration to match
	 * @return List of matching Confluence endpoints
	 * @throws GeboPersistenceException If a database error occurs
	 */
	@PostMapping(value = "findConfluenceEndpointsByQbe", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public List<GConfluenceProjectEndpoint> findConfluenceEndpointsByQbe(@RequestBody GConfluenceProjectEndpoint config)
			throws GeboPersistenceException {
		return findEndpointByQbe(config);
	}

	/**
	 * Finds Confluence endpoints associated with a specific project.
	 * 
	 * @param parentProjectCode The project code to search for
	 * @return List of Confluence endpoints for the specified project
	 * @throws GeboPersistenceException If a database error occurs
	 */
	@GetMapping("findConfluenceEndpointsByProject")
	public List<GConfluenceProjectEndpoint> findConfluenceEndpointsByProject(
			@RequestParam("parentProjectCode") String parentProjectCode) throws GeboPersistenceException {
		return endpointRepository.findByParentProjectCode(parentProjectCode);
	}

	/**
	 * Finds a Confluence endpoint by its unique code.
	 * 
	 * @param code The endpoint code to search for
	 * @return The matching Confluence endpoint
	 * @throws GeboPersistenceException If a database error occurs
	 */
	@GetMapping("findConfluenceEndpointsByCode")
	public GConfluenceProjectEndpoint findConfluenceEndpointsByCode(@RequestParam("code") String code)
			throws GeboPersistenceException {
		return persistentObjectManager.findById(GConfluenceProjectEndpoint.class, code);
	}

	/**
	 * Finds a Confluence system by its unique code.
	 * 
	 * @param code The system code to search for
	 * @return The matching Confluence system
	 * @throws GeboPersistenceException If a database error occurs
	 */
	@GetMapping("findConfluenceSystemByCode")
	public GConfluenceSystem findConfluenceSystemByCode(@RequestParam("code") String code)
			throws GeboPersistenceException {
		return persistentObjectManager.findById(GConfluenceSystem.class, code);
	}

	/**
	 * Updates an existing Confluence system configuration after testing it.
	 * 
	 * @param object The Confluence system to update
	 * @return Operation status with the updated system or error messages
	 * @throws GeboPersistenceException If a database error occurs
	 */
	@PostMapping(value = "updateConfluenceSystem", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<GConfluenceSystem> updateConfluenceSystem(@RequestBody GConfluenceSystem object)
			throws GeboPersistenceException {
		OperationStatus<GConfluenceSystem> os = confluenceTestService.testConfluenceSystem(object);
		if (os.isHasErrorMessages()) {
			return os;
		}
		return OperationStatus.of(updateSystem(object));
	}

	/**
	 * Inserts a new Confluence system configuration after testing it.
	 * 
	 * @param object The Confluence system to insert
	 * @return Operation status with the inserted system or error messages
	 * @throws GeboPersistenceException If a database error occurs
	 */
	@PostMapping(value = "insertConfluenceSystem", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<GConfluenceSystem> insertConfluenceSystem(@RequestBody GConfluenceSystem object)
			throws GeboPersistenceException {
		OperationStatus<GConfluenceSystem> os = confluenceTestService.testConfluenceSystem(object);
		if (os.isHasErrorMessages()) {
			return os;
		}

		return OperationStatus.of(insertSystem(object));
	}

	/**
	 * Tests a Confluence system configuration without persisting it.
	 * 
	 * @param object The Confluence system to test
	 * @return Operation status with test results
	 * @throws GeboPersistenceException If a database error occurs
	 */
	@PostMapping(value = "testConfluenceSystem", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<GConfluenceSystem> testConfluenceSystem(@RequestBody GConfluenceSystem object)
			throws GeboPersistenceException {
		return confluenceTestService.testConfluenceSystem(object);
	}

	/**
	 * Deletes a Confluence system configuration.
	 * 
	 * @param object The Confluence system to delete
	 * @throws GeboPersistenceException If a database error occurs
	 */
	@PostMapping(value = "deleteConfluenceSystem", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void deleteConfluenceSystem(@RequestBody GConfluenceSystem object) throws GeboPersistenceException {
		this.deleteSystem(object);
	}

	/**
	 * Updates an existing Confluence project endpoint.
	 * 
	 * @param endpoint The Confluence endpoint to update
	 * @return The updated Confluence endpoint
	 * @throws GeboPersistenceException If a database error occurs
	 */
	@PostMapping(value = "updateConfluenceEndpoint", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public GConfluenceProjectEndpoint updateConfluenceEndpoint(@RequestBody GConfluenceProjectEndpoint endpoint)
			throws GeboPersistenceException {
		return updateEndpoint(endpoint);
	}

	/**
	 * Inserts a new Confluence project endpoint.
	 * 
	 * @param endpoint The Confluence endpoint to insert
	 * @return The inserted Confluence endpoint
	 * @throws GeboPersistenceException If a database error occurs
	 */
	@PostMapping(value = "insertConfluenceEndpoint", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public GConfluenceProjectEndpoint insertConfluenceEndpoint(@RequestBody GConfluenceProjectEndpoint endpoint)
			throws GeboPersistenceException {
		return insertEndpoint(endpoint);
	}

	/**
	 * Deletes a Confluence project endpoint.
	 * 
	 * @param endpoint The Confluence endpoint to delete
	 * @throws GeboPersistenceException If a database error occurs
	 */
	@PostMapping(value = "deleteConfluenceEndpoint", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void deleteConfluenceEndpoint(@RequestBody GConfluenceProjectEndpoint endpoint)
			throws GeboPersistenceException {
		deleteEndpoint(endpoint);
	}

	/**
	 * Data class for simplified Confluence system configuration.
	 * Contains the basic information needed to create a Confluence system.
	 */
	public static class FastConfluenceSystemInsertRequest {
		@NotNull
		public String baseUri = null;
		@NotNull
		public String description = null;
		@NotNull
		public String username = null;
		public String password = null;
		public String token = null;
		@NotNull
		public ConfluenceVersion confluenceVersion = null;
	}

	/**
	 * Creates a Confluence system configuration with simplified input.
	 * This endpoint tests connection and creates both secret and system configuration.
	 * 
	 * @param data The simplified configuration data
	 * @return Operation status with the created system or error messages
	 */
	@PostMapping(value = "fastConfluenceConfig", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<GConfluenceSystem> fastConfluenceConfig(
			@Valid @RequestBody FastConfluenceSystemInsertRequest data) {
		try {
			AbstractGeboSecretContent secret = null;
			switch (data.confluenceVersion) {
			case ONPREMISE7X: {
				// Test on-premise Confluence connection before creating system
				OnPremiseConfluenceConnection api = new OnPremiseConfluenceConnection(restTemplateWrapper);
				api.setBaseUrl(data.baseUri);
				api.setUsername(data.username);
				api.setPassword(data.password);
				OnPremiseConfluenceSpaceApi browser = new OnPremiseConfluenceSpaceApi(api);
				browser.getAllSpaces(1, 10, "global");
				GeboUsernamePasswordContent up = new GeboUsernamePasswordContent();
				up.setUsername(data.username);
				up.setPassword(data.password);
				secret = up;
			}
				break;
			case CLOUD: {
				// Test cloud Confluence connection before creating system
				CloudConfluenceConnection api = new CloudConfluenceConnection(restTemplateWrapper);
				api.setBaseUrl(data.baseUri);
				api.setUsername(data.username);
				api.setPassword(data.token);
				CloudConfluenceSpaceApi spaceApi = new CloudConfluenceSpaceApi(api);
				spaceApi.getAllSpaces(1, 10, "global");
				GeboTokenContent token = new GeboTokenContent();
				token.setUser(data.username);
				token.setToken(data.token);
				secret = token;
			}
				break;
			}

			// Create and store the secret for authentication
			String description = data.confluenceVersion.name() + " confluence system secret";
			String secretId = secretAccessService.storeSecret(secret, description,
					ConfluenceContentManagementHandlerImpl.ATLASSIAN_CONFLUENCE);
			
			// Create the Confluence system configuration
			GConfluenceSystem system = new GConfluenceSystem();
			system.setBaseUri(data.baseUri);
			system.setDescription(data.description);
			system.setSecretCode(secretId);
			system.setConfluenceVersion(data.confluenceVersion);
			system.setUsedCapabilities(List.of(GSystemRole.DOCUMENTS_MANAGEMENT));
			system.setCreationDate(new Date());
			system.setModificationDate(new Date());
			system.setContentManagementSystemType(ConfluenceContentManagementHandlerImpl.ATLASSIAN_CONFLUENCE);
			
			// Test the system before persisting
			OperationStatus<GConfluenceSystem> status = confluenceTestService.testConfluenceSystem(system);
			if (status.isHasErrorMessages()) {
				return status;
			}
			system = persistentObjectManager.insert(system);
			return OperationStatus.of(system);
		} catch (Throwable th) {
			// Handle any connection or configuration errors
			LOGGER.error("Error trying inserting confluence system configuration", th);
			OperationStatus<GConfluenceSystem> os = new OperationStatus<GConfluenceSystem>();
			os.getMessages().add(GUserMessage.errorMessage("Cannot access confluence", ""));
			return os;
		}
	}
}