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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ai.gebo.application.messaging.IGMessageBroker;
import ai.gebo.application.messaging.IGMessageEmitter;
import ai.gebo.application.messaging.SystemComponentType;
import ai.gebo.application.messaging.model.GMessageEnvelope;
import ai.gebo.application.messaging.model.GStandardModulesConstraints;
import ai.gebo.architecture.multithreading.IGEntityProcessingRunnableFactory;
import ai.gebo.architecture.multithreading.IGEntityProcessingRunnableFactoryRepositoryPattern;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.architecture.scheduling.services.IGSchedulingTimeService;
import ai.gebo.core.messages.GDeletedProjectEndpointPayload;
import ai.gebo.knlowledgebase.model.contents.ObjectSpaceType;
import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.knlowledgebase.model.systems.GContentManagementSystem;
import ai.gebo.security.services.IGSecurityService;

/**
 * AI generated comments
 * Abstract controller for system architectures, responsible for managing
 * operations related to systems and endpoints.
 * 
 * @param <SystemType>  The type of the content management system.
 * @param <EndpointType> The type of the project endpoint.
 */
public class GAbstractSystemsArchitectureController<SystemType extends GContentManagementSystem, EndpointType extends GProjectEndpoint> {
    // Logger for logging messages
    protected Logger LOGGER = LoggerFactory.getLogger(getClass());
    // Manager for handling persistence of objects
    protected final IGPersistentObjectManager persistentObjectManager;
    // Broker for handling messaging
    protected final IGMessageBroker messageBroker;
    // Emitter for handling nested messaging
    protected final ControllerNestedEmitter controllerEmitter;
    // Service for handling security checks
    protected final IGSecurityService securityService;
    // Service for handling scheduling
    protected final IGSchedulingTimeService schedulingService;
    // Factory for runnable processing of entities
    protected final IGEntityProcessingRunnableFactoryRepositoryPattern entityProcessingRunnableFactory;

    /**
     * Nested emitter class for handling messaging from the controller.
     */
    public static abstract class ControllerNestedEmitter implements IGMessageEmitter {
        @Override
        public String getMessagingSystemId() {
            return GStandardModulesConstraints.SYSTEM_SETTINGS_CONTROLLER_COMPONENT;
        }

        @Override
        public SystemComponentType getComponentType() {
            return SystemComponentType.APPLICATION_COMPONENT;
        }

        @Override
        public List<String> getEmittedPayloadTypes() {
            return List.of(GDeletedProjectEndpointPayload.class.getName());
        }
    }

    /**
     * Constructor to initialize the controller with necessary dependencies.
     * 
     * @param persistentObjectManager Manager for persistent objects.
     * @param messageBroker           Broker for messaging.
     * @param controllerEmitter       Emitter for controller messages.
     * @param securityService         Service for security operations.
     * @param schedulingService       Service for scheduling operations.
     * @param entityProcessingRunnableFactory Factory for processing entities.
     */
    public GAbstractSystemsArchitectureController(IGPersistentObjectManager persistentObjectManager,
            IGMessageBroker messageBroker, ControllerNestedEmitter controllerEmitter, IGSecurityService securityService,
            IGSchedulingTimeService schedulingService,
            IGEntityProcessingRunnableFactoryRepositoryPattern entityProcessingRunnableFactory) {
        this.persistentObjectManager = persistentObjectManager;
        this.messageBroker = messageBroker;
        this.controllerEmitter = controllerEmitter;
        this.securityService = securityService;
        this.schedulingService = schedulingService;
        this.entityProcessingRunnableFactory = entityProcessingRunnableFactory;
    }

    /**
     * Deletes the given endpoint after performing security checks and sends appropriate messages.
     * 
     * @param endpoint The endpoint to be deleted.
     * @throws GeboPersistenceException If the current user is not an admin or if deletion fails.
     */
    protected void deleteEndpoint(EndpointType endpoint) throws GeboPersistenceException {
        if (!securityService.isCurrentUserAdmin())
            throw new GeboPersistenceException("User without ADMIN role cannot delete contents and endpoints");
        String userid = securityService.getCurrentUser().getUsername();
        persistentObjectManager.delete(endpoint);

        // Create and send messages to indicate deletion
        GMessageEnvelope<GDeletedProjectEndpointPayload> message = GMessageEnvelope.newMessageFrom(controllerEmitter,
                new GDeletedProjectEndpointPayload(endpoint), userid);
        message.setTargetModule(controllerEmitter.getMessagingModuleId());
        message.setTargetComponent(GStandardModulesConstraints.RESOURCES_DISPOSE_COMPONENT);
        messageBroker.accept(message);
        
        message = GMessageEnvelope.newMessageFrom(controllerEmitter, new GDeletedProjectEndpointPayload(endpoint),
                userid);
        message.setTargetModule(GStandardModulesConstraints.CORE_MODULE);
        message.setTargetComponent(GStandardModulesConstraints.MONGO_DISPOSE_DOCUMENTS_COMPONENT);
        messageBroker.accept(message);
        
        message = GMessageEnvelope.newMessageFrom(controllerEmitter, new GDeletedProjectEndpointPayload(endpoint),
                userid);
        message.setTargetModule(GStandardModulesConstraints.VECTORIZATOR_MODULE);
        message.setTargetComponent(GStandardModulesConstraints.VECTORIZATION_DISPOSE_COMPONENT);
        messageBroker.accept(message);
    }

    /**
     * Inserts the given endpoint into the persistent storage and reschedules if necessary.
     * 
     * @param endpoint The endpoint to be inserted.
     * @return The inserted endpoint.
     * @throws GeboPersistenceException If insertion fails.
     */
    protected EndpointType insertEndpoint(EndpointType endpoint) throws GeboPersistenceException {
        if (endpoint.getObjectSpaceType() == null) {
            endpoint.setObjectSpaceType(ObjectSpaceType.COMPANY);
        }
        EndpointType outdata = persistentObjectManager.insert(endpoint);
        processReschedule(endpoint);
        return outdata;
    }

    /**
     * Processes the rescheduling of jobs related to the given endpoint.
     * 
     * @param endpoint The endpoint whose scheduling needs to be managed.
     */
    private void processReschedule(EndpointType endpoint) {
        schedulingService.managePublishScheduling(endpoint);
    }

    /**
     * Updates the given endpoint in the persistent storage and reschedules if necessary.
     * 
     * @param endpoint The endpoint to be updated.
     * @return The updated endpoint.
     * @throws GeboPersistenceException If update fails.
     */
    protected EndpointType updateEndpoint(EndpointType endpoint) throws GeboPersistenceException {
        if (endpoint.getObjectSpaceType() == null) {
            endpoint.setObjectSpaceType(ObjectSpaceType.COMPANY);
        }
        EndpointType outdata = persistentObjectManager.update(endpoint);
        processReschedule(endpoint);
        return outdata;
    }

    /**
     * Deletes the given system from the persistent storage.
     * 
     * @param system The system to be deleted.
     * @throws GeboPersistenceException If deletion fails.
     */
    protected void deleteSystem(SystemType system) throws GeboPersistenceException {
        persistentObjectManager.delete(system);
    }

    /**
     * Inserts the given system into the persistent storage.
     * 
     * @param endpoint The system to be inserted.
     * @return The inserted system.
     * @throws GeboPersistenceException If insertion fails.
     */
    protected SystemType insertSystem(SystemType endpoint) throws GeboPersistenceException {
        return persistentObjectManager.insert(endpoint);
    }

    /**
     * Updates the given system in the persistent storage.
     * 
     * @param endpoint The system to be updated.
     * @return The updated system.
     * @throws GeboPersistenceException If update fails.
     */
    protected SystemType updateSystem(SystemType endpoint) throws GeboPersistenceException {
        return persistentObjectManager.update(endpoint);
    }

    /**
     * Finds systems by query-by-example (QBE) matching.
     * 
     * @param system The system example to match.
     * @return A list of systems matching the query example.
     * @throws GeboPersistenceException If querying fails.
     */
    protected List<SystemType> findSystemByQbe(SystemType system) throws GeboPersistenceException {
        return persistentObjectManager.findByQbe(system);
    }

    /**
     * Finds endpoints by query-by-example (QBE) matching.
     * 
     * @param endpoint The endpoint example to match.
     * @return A list of endpoints matching the query example.
     * @throws GeboPersistenceException If querying fails.
     */
    protected List<EndpointType> findEndpointByQbe(EndpointType endpoint) throws GeboPersistenceException {
        return persistentObjectManager.findByQbe(endpoint);
    }

}