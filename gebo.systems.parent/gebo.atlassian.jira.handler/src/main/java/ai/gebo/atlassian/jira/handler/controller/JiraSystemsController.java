/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.atlassian.jira.handler.controller;

/**
 * AI generated comments
 * 
 * Controller for managing Jira systems and project endpoints.
 * This REST controller provides CRUD operations and additional functionality
 * for Jira systems and their endpoints with admin-level access restrictions.
 */
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
import ai.gebo.atlassian.jira.handler.GJiraProjectEndpoint;
import ai.gebo.atlassian.jira.handler.GJiraSystem;
import ai.gebo.atlassian.jira.handler.IGJiraContentManagementHandler;
import ai.gebo.atlassian.jira.handler.impl.JiraContentManagementHandlerImpl;
import ai.gebo.atlassian.jira.handler.impl.JiraSystemsTestService;
import ai.gebo.atlassian.jira.handler.repository.JiraProjectEndpointRepository;
import ai.gebo.knlowledgebase.model.systems.GContentManagementSystemType;
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
@RequestMapping(value = "api/admin/JiraSystemsController")
public class JiraSystemsController extends GAbstractSystemsArchitectureController<GJiraSystem, GJiraProjectEndpoint> {
	
    /**
     * Nested emitter component for Jira controller messaging.
     * Provides messaging module identification for the Jira subsystem.
     */
	@Component
	@Scope("singleton")
	public static class JiraControllerEmitter extends ControllerNestedEmitter {

		@Override
		public String getMessagingModuleId() {
			return GStandardModulesConstraints.ATLASSIAN_JIRA_MODULE;
		}
	}

	private final IGJiraContentManagementHandler handler;
	private final JiraProjectEndpointRepository endpointRepository;
	private final IGeboSecretsAccessService secretAccessService;
	private final RestTemplateWrapperService restTemplateWrapper;
	private final JiraSystemsTestService JiraTestService;

    /**
     * Constructor for JiraSystemsController with dependency injection.
     * 
     * @param persistentObjectManager Object manager for persistence operations
     * @param messageBroker Broker for handling messages across the system
     * @param controllerEmitter The Jira-specific controller emitter
     * @param securityService Service for security operations
     * @param handler Handler for Jira content management operations
     * @param endpointRepository Repository for accessing Jira project endpoints
     * @param secretAccessService Service for accessing secrets
     * @param schedulingService Service for scheduling operations
     * @param entityProcessingRunnableFactory Factory for entity processing runnables
     * @param restTemplateWrapper Wrapper for REST template operations
     * @param JiraTestService Service for testing Jira systems
     */
	public JiraSystemsController(IGPersistentObjectManager persistentObjectManager, IGMessageBroker messageBroker,
			JiraControllerEmitter controllerEmitter, IGSecurityService securityService,
			IGJiraContentManagementHandler handler, JiraProjectEndpointRepository endpointRepository,
			IGeboSecretsAccessService secretAccessService, IGSchedulingTimeService schedulingService,
			IGEntityProcessingRunnableFactoryRepositoryPattern entityProcessingRunnableFactory,
			RestTemplateWrapperService restTemplateWrapper, JiraSystemsTestService JiraTestService) {
		super(persistentObjectManager, messageBroker, controllerEmitter, securityService, schedulingService,
				entityProcessingRunnableFactory);
		this.handler = handler;
		this.endpointRepository = endpointRepository;
		this.secretAccessService = secretAccessService;
		this.restTemplateWrapper = restTemplateWrapper;
		this.JiraTestService = JiraTestService;
	}

    /**
     * Retrieves the content management system type for Jira.
     * 
     * @return The content management system type associated with Jira
     */
	@GetMapping(value = "getJiraSystemType", produces = MediaType.APPLICATION_JSON_VALUE)
	public GContentManagementSystemType getJiraSystemTypes() {
		return handler.getHandledSystemType();
	}

    /**
     * Retrieves all configured Jira systems.
     * 
     * @return List of all Jira system configurations
     */
	@GetMapping("getJiraSystems")
	public List<GJiraSystem> getJiraSystems() {
		return handler.getConfigurations();
	}

    /**
     * Finds Jira endpoints based on query by example.
     * 
     * @param config The example Jira project endpoint to query against
     * @return List of matching Jira project endpoints
     * @throws GeboPersistenceException If a persistence error occurs
     */
	@PostMapping(value = "findJiraEndpointsByQbe", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public List<GJiraProjectEndpoint> findJiraEndpointsByQbe(@RequestBody GJiraProjectEndpoint config)
			throws GeboPersistenceException {
		return findEndpointByQbe(config);
	}

    /**
     * Finds Jira endpoints associated with a specific project.
     * 
     * @param parentProjectCode The code of the parent project
     * @return List of Jira project endpoints for the specified project
     * @throws GeboPersistenceException If a persistence error occurs
     */
	@GetMapping("findJiraEndpointsByProject")
	public List<GJiraProjectEndpoint> findJiraEndpointsByProject(
			@RequestParam("parentProjectCode") String parentProjectCode) throws GeboPersistenceException {
		return endpointRepository.findByParentProjectCode(parentProjectCode);
	}

    /**
     * Finds a Jira endpoint by its code.
     * 
     * @param code The code of the Jira project endpoint
     * @return The matching Jira project endpoint
     * @throws GeboPersistenceException If a persistence error occurs
     */
	@GetMapping("findJiraEndpointsByCode")
	public GJiraProjectEndpoint findJiraEndpointsByCode(@RequestParam("code") String code)
			throws GeboPersistenceException {
		return persistentObjectManager.findById(GJiraProjectEndpoint.class, code);
	}

    /**
     * Finds a Jira system by its code.
     * 
     * @param code The code of the Jira system
     * @return The matching Jira system
     * @throws GeboPersistenceException If a persistence error occurs
     */
	@GetMapping("findJiraSystemByCode")
	public GJiraSystem findJiraSystemByCode(@RequestParam("code") String code) throws GeboPersistenceException {
		return persistentObjectManager.findById(GJiraSystem.class, code);
	}

    /**
     * Updates an existing Jira system after testing its connection.
     * 
     * @param object The Jira system to update
     * @return Operation status containing the updated Jira system or error information
     * @throws GeboPersistenceException If a persistence error occurs
     */
	@PostMapping(value = "updateJiraSystem", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<GJiraSystem> updateJiraSystem(@RequestBody GJiraSystem object)
			throws GeboPersistenceException {
		OperationStatus<GJiraSystem> os = JiraTestService.testJiraSystem(object);
		if (os.isHasErrorMessages()) {
			return os;
		}
		return OperationStatus.of(updateSystem(object));
	}

    /**
     * Inserts a new Jira system after testing its connection.
     * 
     * @param object The Jira system to insert
     * @return Operation status containing the inserted Jira system or error information
     * @throws GeboPersistenceException If a persistence error occurs
     */
	@PostMapping(value = "insertJiraSystem", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<GJiraSystem> insertJiraSystem(@RequestBody GJiraSystem object)
			throws GeboPersistenceException {
		OperationStatus<GJiraSystem> os = JiraTestService.testJiraSystem(object);
		if (os.isHasErrorMessages()) {
			return os;
		}

		return OperationStatus.of(insertSystem(object));
	}

    /**
     * Tests a Jira system's connection without saving it.
     * 
     * @param object The Jira system to test
     * @return Operation status indicating test success or failure
     * @throws GeboPersistenceException If a persistence error occurs
     */
	@PostMapping(value = "testJiraSystem", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<GJiraSystem> testJiraSystem(@RequestBody GJiraSystem object)
			throws GeboPersistenceException {
		return JiraTestService.testJiraSystem(object);
	}

    /**
     * Deletes a Jira system.
     * 
     * @param object The Jira system to delete
     * @throws GeboPersistenceException If a persistence error occurs
     */
	@PostMapping(value = "deleteJiraSystem", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void deleteJiraSystem(@RequestBody GJiraSystem object) throws GeboPersistenceException {
		this.deleteSystem(object);
	}

    /**
     * Updates an existing Jira project endpoint.
     * 
     * @param endpoint The Jira project endpoint to update
     * @return The updated Jira project endpoint
     * @throws GeboPersistenceException If a persistence error occurs
     */
	@PostMapping(value = "updateJiraEndpoint", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public GJiraProjectEndpoint updateJiraEndpoint(@RequestBody GJiraProjectEndpoint endpoint)
			throws GeboPersistenceException {
		return updateEndpoint(endpoint);
	}

    /**
     * Inserts a new Jira project endpoint.
     * 
     * @param endpoint The Jira project endpoint to insert
     * @return The inserted Jira project endpoint
     * @throws GeboPersistenceException If a persistence error occurs
     */
	@PostMapping(value = "insertJiraEndpoint", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public GJiraProjectEndpoint insertJiraEndpoint(@RequestBody GJiraProjectEndpoint endpoint)
			throws GeboPersistenceException {
		return insertEndpoint(endpoint);
	}

    /**
     * Deletes a Jira project endpoint.
     * 
     * @param endpoint The Jira project endpoint to delete
     * @throws GeboPersistenceException If a persistence error occurs
     */
	@PostMapping(value = "deleteJiraEndpoint", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void deleteJiraEndpoint(@RequestBody GJiraProjectEndpoint endpoint) throws GeboPersistenceException {
		deleteEndpoint(endpoint);
	}

    /**
     * Request model for quick Jira system configuration.
     * Supports either password or token-based authentication.
     */
	public static class FastJiraSystemInsertRequest {
		@NotNull
		public String baseUri = null;
		@NotNull
		public String description = null;
		@NotNull
		public String username = null;
		public String password = null;
		public String token = null;
	}

    /**
     * Creates a Jira system configuration with simplified input parameters.
     * Handles creation of appropriate secret credentials (username/password or token).
     * 
     * @param data The simplified Jira system configuration request
     * @return Operation status containing the created Jira system or error information
     */
	@PostMapping(value = "fastJiraConfig", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<GJiraSystem> fastJiraConfig(@Valid @RequestBody FastJiraSystemInsertRequest data) {
		try {
			AbstractGeboSecretContent secret = null;
			if (data.password != null) {
				GeboUsernamePasswordContent content = new GeboUsernamePasswordContent();
				content.setUsername(data.username);
				content.setPassword(data.password);
				secret = content;
			} else if (data.token != null) {
				GeboTokenContent tokenContent = new GeboTokenContent();
				tokenContent.setToken(data.token);
				tokenContent.setUser(data.username);
				secret = tokenContent;
			} else
				throw new RuntimeException("No password or token");
			String secretCode = secretAccessService.storeSecret(secret, "Jira system account", JiraContentManagementHandlerImpl.ATLASSIAN_JIRA);
			GJiraSystem system = new GJiraSystem();
			system.setSecretCode(secretCode);
			system.setBaseUri(data.baseUri);
			system.setDescription("Jira Cloud system");
			system.setContentManagementSystemType(JiraContentManagementHandlerImpl.ATLASSIAN_JIRA);
			OperationStatus<GJiraSystem> status = JiraTestService.testJiraSystem(system);
			if (status.isHasErrorMessages()) {
				return status;
			}
			system = persistentObjectManager.insert(system);
			return OperationStatus.of(system);
		} catch (Throwable th) {
			// ResourceAccessException
			LOGGER.error("Error trying inserting Jira system configuration", th);
			OperationStatus<GJiraSystem> os = new OperationStatus<GJiraSystem>();
			os.getMessages().add(GUserMessage.errorMessage("Cannot access Jira", ""));
			return os;
		}
	}
}