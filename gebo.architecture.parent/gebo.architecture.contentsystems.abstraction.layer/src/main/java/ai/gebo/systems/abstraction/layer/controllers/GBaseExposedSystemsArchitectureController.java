/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.systems.abstraction.layer.controllers;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import ai.gebo.application.messaging.IGMessageBroker;
import ai.gebo.architecture.multithreading.IGEntityProcessingRunnableFactoryRepositoryPattern;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.architecture.scheduling.services.IGSchedulingTimeService;
import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.knlowledgebase.model.systems.GContentManagementSystem;
import ai.gebo.security.services.IGSecurityService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/**
 * Controller class providing a generic REST API for managing systems and endpoints.
 * 
 * @param <SystemType>   the type of the system, extends GContentManagementSystem
 * @param <EndpointType> the type of the endpoint, extends GProjectEndpoint
 * 
 * AI generated comments
 */
public class GBaseExposedSystemsArchitectureController<SystemType extends GContentManagementSystem, EndpointType extends GProjectEndpoint>
		extends GAbstractSystemsArchitectureController<SystemType, EndpointType> {

	// Represents the class type of the endpoint
	final Class<EndpointType> endpointType;
	// Represents the class type of the system
	final Class<SystemType> systemType;

	/**
	 * Constructs a GBaseExposedSystemsArchitectureController instance with specified parameters.
	 *
	 * @param systemType                      the class type of the system
	 * @param endpointType                    the class type of the endpoint
	 * @param persistentObjectManager         the persistent object manager
	 * @param messageBroker                   the message broker
	 * @param controllerEmitter               controller emitter
	 * @param securityService                 the security service
	 * @param schedulingService               the scheduling service
	 * @param entityProcessingRunnableFactory the entity processing runnable factory
	 */
	public GBaseExposedSystemsArchitectureController(Class<SystemType> systemType, Class<EndpointType> endpointType,
			IGPersistentObjectManager persistentObjectManager, IGMessageBroker messageBroker,
			ControllerNestedEmitter controllerEmitter, IGSecurityService securityService,
			IGSchedulingTimeService schedulingService,
			IGEntityProcessingRunnableFactoryRepositoryPattern entityProcessingRunnableFactory) {
		super(persistentObjectManager, messageBroker, controllerEmitter, securityService, schedulingService,
				entityProcessingRunnableFactory);
		this.systemType = systemType;
		this.endpointType = endpointType;
	}

	/**
	 * Deletes an endpoint.
	 *
	 * @param endpoint the endpoint to be deleted
	 * @throws GeboPersistenceException if there is an error in the persistence layer
	 */
	@DeleteMapping(value = "deleteEndpoint", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void deleteEndpoint(@NotNull @Valid @RequestBody EndpointType endpoint) throws GeboPersistenceException {
		super.deleteEndpoint(endpoint);
	}

	/**
	 * Deletes a system.
	 *
	 * @param system the system to be deleted
	 * @throws GeboPersistenceException if there is an error in the persistence layer
	 */
	@DeleteMapping(value = "deleteSystem", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void deleteSystem(@NotNull @Valid @RequestBody SystemType system) throws GeboPersistenceException {
		super.deleteSystem(system);
	}

	/**
	 * Finds endpoints by query by example (QBE).
	 *
	 * @param endpoint the endpoint example
	 * @return a list of endpoints matching the example
	 * @throws GeboPersistenceException if there is an error in the persistence layer
	 */
	@PostMapping(value = "findEndpointByQbe", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<EndpointType> findEndpointByQbe(@NotNull @Valid @RequestBody EndpointType endpoint)
			throws GeboPersistenceException {
		return super.findEndpointByQbe(endpoint);
	}

	/**
	 * Finds systems by query by example (QBE).
	 *
	 * @param system the system example
	 * @return a list of systems matching the example
	 * @throws GeboPersistenceException if there is an error in the persistence layer
	 */
	@PostMapping(value = "findSystemByQbe", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<SystemType> findSystemByQbe(@NotNull @Valid @RequestBody SystemType system)
			throws GeboPersistenceException {
		return super.findSystemByQbe(system);
	}

	/**
	 * Inserts a new endpoint.
	 *
	 * @param endpoint the endpoint to be inserted
	 * @return the inserted endpoint
	 * @throws GeboPersistenceException if there is an error in the persistence layer
	 */
	@PostMapping(value = "insertEndpoint", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public EndpointType insertEndpoint(@NotNull @Valid @RequestBody EndpointType endpoint)
			throws GeboPersistenceException {
		return super.insertEndpoint(endpoint);
	}

	/**
	 * Inserts a new system.
	 *
	 * @param endpoint the system to be inserted
	 * @return the inserted system
	 * @throws GeboPersistenceException if there is an error in the persistence layer
	 */
	@PostMapping(value = "insertSystem", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public SystemType insertSystem(@NotNull @Valid @RequestBody SystemType endpoint) throws GeboPersistenceException {
		return super.insertSystem(endpoint);
	}

	/**
	 * Updates an existing endpoint.
	 *
	 * @param endpoint the endpoint to be updated
	 * @return the updated endpoint
	 * @throws GeboPersistenceException if there is an error in the persistence layer
	 */
	@PostMapping(value = "updateEndpoint", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public EndpointType updateEndpoint(@NotNull @Valid @RequestBody EndpointType endpoint)
			throws GeboPersistenceException {
		return super.updateEndpoint(endpoint);
	}

	/**
	 * Updates an existing system.
	 *
	 * @param endpoint the system to be updated
	 * @return the updated system
	 * @throws GeboPersistenceException if there is an error in the persistence layer
	 */
	@PostMapping(value = "updateSystem", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public SystemType updateSystem(@NotNull @Valid @RequestBody SystemType endpoint) throws GeboPersistenceException {
		return super.updateSystem(endpoint);
	}

	/**
	 * Finds an endpoint by its code.
	 *
	 * @param code the code of the endpoint
	 * @return the found endpoint
	 * @throws GeboPersistenceException if there is an error in the persistence layer
	 */
	@GetMapping(value = "findEndpointByCode", produces = MediaType.APPLICATION_JSON_VALUE)
	public EndpointType findEndpointByCode(@RequestParam("code") String code) throws GeboPersistenceException {
		return persistentObjectManager.findById(endpointType, code);
	}

	/**
	 * Finds a system by its code.
	 *
	 * @param code the code of the system
	 * @return the found system
	 * @throws GeboPersistenceException if there is an error in the persistence layer
	 */
	@GetMapping(value = "findSystemByCode", produces = MediaType.APPLICATION_JSON_VALUE)
	public SystemType findSystemByCode(@RequestParam("code") String code) throws GeboPersistenceException {
		return persistentObjectManager.findById(systemType, code);
	}
}