/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.git.content.handler.controllers;

/**
 * AI generated comments
 * 
 * Controller responsible for managing Git content management systems and project endpoints.
 * This controller provides REST endpoints for CRUD operations on Git systems and project endpoints,
 * as well as functionality for testing Git configurations and retrieving repository branches.
 * Requires admin role for access.
 */
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
import ai.gebo.architecture.multithreading.IGEntityProcessingRunnableFactoryRepositoryPattern;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.architecture.scheduling.services.IGSchedulingTimeService;
import ai.gebo.git.content.handler.GGitContentManagementSystem;
import ai.gebo.git.content.handler.GGitProjectEndpoint;
import ai.gebo.git.content.handler.IGBaseGitContentManagementSystemHandler;
import ai.gebo.git.content.handler.impl.GitClientService;
import ai.gebo.git.content.handler.impl.GitSystemsRuntimeConfiguratoinDao;
import ai.gebo.git.content.handler.repositories.GitEndpointRepository;
import ai.gebo.knlowledgebase.model.systems.GContentManagementSystemType;
import ai.gebo.model.OperationStatus;
import ai.gebo.security.services.IGSecurityService;
import ai.gebo.systems.abstraction.layer.controllers.GAbstractSystemsArchitectureController;

@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping(value = "api/admin/GITSystemsController")
public class GITSystemsController
		extends GAbstractSystemsArchitectureController<GGitContentManagementSystem, GGitProjectEndpoint> {
	/** List of available Git content management system handlers */
	@Autowired(required = false)
	List<IGBaseGitContentManagementSystemHandler> handlers;
	
	/** Data access object for Git system configurations */
	@Autowired
	GitSystemsRuntimeConfiguratoinDao dao;
	
	/** Repository for Git project endpoints */
	@Autowired
	GitEndpointRepository endpointRepository;
	
	/** Service for Git client operations */
	@Autowired
	GitClientService gitClientService;

	/**
	 * Inner component class responsible for emitting Git controller events
	 */
	@Component
	@Scope("singleton")
	public static class GitControllerEmitter extends ControllerNestedEmitter {

		/**
		 * Gets the messaging module identifier for Git operations
		 * @return The Git module identifier
		 */
		@Override
		public String getMessagingModuleId() {
			return GStandardModulesConstraints.GIT_MODULE;
		}
	}

	/**
	 * Constructor for the Git systems controller
	 * 
	 * @param persistentObjectManager Object manager for persistence operations
	 * @param messageBroker Broker for message communication
	 * @param controllerEmitter Emitter for controller events
	 * @param securityService Service for security operations
	 * @param schedulingService Service for scheduling operations
	 * @param entityProcessingRunnableFactory Factory for entity processing runnables
	 */
	public GITSystemsController(IGPersistentObjectManager persistentObjectManager, IGMessageBroker messageBroker,
			GitControllerEmitter controllerEmitter, IGSecurityService securityService,
			IGSchedulingTimeService schedulingService,
			IGEntityProcessingRunnableFactoryRepositoryPattern entityProcessingRunnableFactory) {
		super(persistentObjectManager, messageBroker, controllerEmitter, securityService, schedulingService,
				entityProcessingRunnableFactory);
	}

	/**
	 * Retrieves all available Git system types
	 * 
	 * @return List of Git content management system types
	 */
	@GetMapping(value = "getGitSystemTypes", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<GContentManagementSystemType> getGitSystemTypes() {
		List<GContentManagementSystemType> list = new ArrayList<GContentManagementSystemType>();
		if (handlers != null) {
			for (IGBaseGitContentManagementSystemHandler handler : handlers) {
				list.add(handler.getHandledSystemType());
			}
		}
		return list;
	}

	/**
	 * Retrieves Git systems, optionally filtered by handler code
	 * 
	 * @param handlerCode Optional filter for specific handler type
	 * @return List of Git content management systems
	 */
	@GetMapping("getGitSystems")
	public List<GGitContentManagementSystem> getGitSystems(
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
	 * Finds Git endpoints by example
	 * 
	 * @param config The example endpoint to search by
	 * @return List of matching Git project endpoints
	 * @throws GeboPersistenceException If a persistence error occurs
	 */
	@PostMapping(value = "findGitEndpointsByQbe", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public List<GGitProjectEndpoint> findGitEndpointsByQbe(@RequestBody GGitProjectEndpoint config)
			throws GeboPersistenceException {
		return findEndpointByQbe(config);
	}

	/**
	 * Finds Git endpoints by project code
	 * 
	 * @param parentProjectCode The project code to search by
	 * @return List of Git project endpoints for the specified project
	 * @throws GeboPersistenceException If a persistence error occurs
	 */
	@GetMapping("findGitEndpointsByProject")
	public List<GGitProjectEndpoint> findGitEndpointsByProject(@RequestParam("parentProjectCode") String parentProjectCode)
			throws GeboPersistenceException {
		return endpointRepository.findByParentProjectCode(parentProjectCode);
	}

	/**
	 * Updates an existing Git system
	 * 
	 * @param object The Git system to update
	 * @return The updated Git system
	 * @throws GeboPersistenceException If a persistence error occurs
	 */
	@PostMapping(value = "updateGitSystem", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public GGitContentManagementSystem updateGitSystem(@RequestBody GGitContentManagementSystem object)
			throws GeboPersistenceException {
		return updateSystem(object);
	}

	/**
	 * Inserts a new Git system
	 * 
	 * @param object The Git system to insert
	 * @return The inserted Git system
	 * @throws GeboPersistenceException If a persistence error occurs
	 */
	@PostMapping(value = "insertGitSystem", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public GGitContentManagementSystem insertGitSystem(@RequestBody GGitContentManagementSystem object)
			throws GeboPersistenceException {
		return insertSystem(object);
	}

	/**
	 * Deletes a Git system
	 * 
	 * @param object The Git system to delete
	 * @throws GeboPersistenceException If a persistence error occurs
	 */
	@PostMapping(value = "deleteGitSystem", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void deleteGitSystem(@RequestBody GGitContentManagementSystem object) throws GeboPersistenceException {
		this.deleteSystem(object);
	}

	/**
	 * Updates a Git project endpoint after testing the configuration
	 * 
	 * @param endpoint The Git endpoint to update
	 * @return Operation status containing the updated endpoint or error messages
	 * @throws GeboPersistenceException If a persistence error occurs
	 */
	@PostMapping(value = "updateGitEndpoint", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<GGitProjectEndpoint> updateGitEndpoint(@RequestBody GGitProjectEndpoint endpoint)
			throws GeboPersistenceException {
		OperationStatus<GGitProjectEndpoint> returnValue = gitClientService.testGitConfiguration(endpoint);
		if (!returnValue.isHasErrorMessages()) {
			returnValue.setResult(updateEndpoint(endpoint));
		}
		return returnValue;
	}

	/**
	 * Inserts a new Git project endpoint after testing the configuration
	 * 
	 * @param endpoint The Git endpoint to insert
	 * @return Operation status containing the inserted endpoint or error messages
	 * @throws GeboPersistenceException If a persistence error occurs
	 */
	@PostMapping(value = "insertGitEndpoint", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<GGitProjectEndpoint> insertGitEndpoint(@RequestBody GGitProjectEndpoint endpoint)
			throws GeboPersistenceException {
		OperationStatus<GGitProjectEndpoint> returnValue = gitClientService.testGitConfiguration(endpoint);
		if (!returnValue.isHasErrorMessages()) {
			returnValue.setResult(insertEndpoint(endpoint));
		}
		return returnValue;
	}

	/**
	 * Retrieves the list of branches for a Git repository
	 * 
	 * @param endpoint The Git endpoint to get branches from
	 * @return Operation status containing the list of branch names or error messages
	 */
	@PostMapping(value = "getBranchesList", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<List<String>> getBranchesList(@RequestBody GGitProjectEndpoint endpoint) {
		return gitClientService.getGitBranches(endpoint);
	}

	/**
	 * Deletes a Git project endpoint
	 * 
	 * @param endpoint The Git endpoint to delete
	 * @throws GeboPersistenceException If a persistence error occurs
	 */
	@PostMapping(value = "deleteGitEndpoint", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void deleteGitEndpoint(@RequestBody GGitProjectEndpoint endpoint) throws GeboPersistenceException {
		deleteEndpoint(endpoint);
	}
}