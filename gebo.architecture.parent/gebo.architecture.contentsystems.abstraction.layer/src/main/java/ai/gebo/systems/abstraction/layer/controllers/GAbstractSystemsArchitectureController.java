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
import ai.gebo.application.messaging.workflow.GStandardWorkflow;
import ai.gebo.application.messaging.workflow.GWorkflowType;
import ai.gebo.architecture.multithreading.IGEntityProcessingRunnableFactoryRepositoryPattern;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.architecture.replicator.service.IEntityReplicationService;
import ai.gebo.architecture.scheduling.services.IGSchedulingTimeService;
import ai.gebo.core.messages.GDeletedProjectEndpointPayload;
import ai.gebo.jobs.services.IGGeboIngestionJobQueueService;
import ai.gebo.knlowledgebase.model.contents.ObjectSpaceType;
import ai.gebo.knlowledgebase.model.jobs.GJobStatus;
import ai.gebo.knlowledgebase.model.projects.GCentralizedProjectEndpoint;
import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.knlowledgebase.model.systems.GContentManagementSystem;
import ai.gebo.model.OperationStatus;
import ai.gebo.security.services.IGSecurityService;
import ai.gebo.systems.abstraction.layer.NoContentConsumingSessionParam;

/**
 * AI generated comments Abstract controller for system architectures,
 * responsible for managing operations related to systems and endpoints.
 * 
 * @param <SystemType>   The type of the content management system.
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
	protected final IGGeboIngestionJobQueueService jobQueueService;
	protected final IEntityReplicationService replicationService;

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
	 * @param persistentObjectManager         Manager for persistent objects.
	 * @param messageBroker                   Broker for messaging.
	 * @param controllerEmitter               Emitter for controller messages.
	 * @param securityService                 Service for security operations.
	 * @param schedulingService               Service for scheduling operations.
	 * @param entityProcessingRunnableFactory Factory for processing entities.
	 */
public GAbstractSystemsArchitectureController(IGPersistentObjectManager persistentObjectManager,
			IGMessageBroker messageBroker, ControllerNestedEmitter controllerEmitter, IGSecurityService securityService,
			IGSchedulingTimeService schedulingService,
			IGEntityProcessingRunnableFactoryRepositoryPattern entityProcessingRunnableFactory,
			IGGeboIngestionJobQueueService jobQueueService,
			IEntityReplicationService replicationService) {
		this.persistentObjectManager = persistentObjectManager;
		this.messageBroker = messageBroker;
		this.controllerEmitter = controllerEmitter;
		this.securityService = securityService;
		this.schedulingService = schedulingService;
		this.entityProcessingRunnableFactory = entityProcessingRunnableFactory;
		this.jobQueueService = jobQueueService;
		this.replicationService = replicationService;
	}
	/**
	 * Deletes the given endpoint after performing security checks and sends
	 * appropriate messages.
	 * 
	 * @param endpoint The endpoint to be deleted.
	 * @throws GeboPersistenceException If the current user is not an admin or if
	 *                                  deletion fails.
	 */
	protected void deleteEndpoint(EndpointType endpoint) throws GeboPersistenceException {
		if (!securityService.isCurrentUserAdmin())
			throw new GeboPersistenceException("User without ADMIN role cannot delete contents and endpoints");
		String userid = securityService.getCurrentUser().getUsername();

		// Flatten the concrete endpoint into a shareable centralized view BEFORE
		// deletion so the
		// dispatched messages can be deserialized in every module. The originating
		// content management
		// system code is resolved while the concrete endpoint is still available, since
		// it can no
		// longer be reconstructed once the object is removed from persistence.
		GCentralizedProjectEndpoint centralized = GCentralizedProjectEndpoint.of(endpoint);
		String contentManagementSystemCode = resolveContentManagementSystemCode(endpoint);
		replicationService.replicate(centralized, true);

		// persistentObjectManager.delete(endpoint);

		// Create and send messages to indicate deletion
		GMessageEnvelope<GDeletedProjectEndpointPayload> message = GMessageEnvelope.newMessageFrom(controllerEmitter,
				newDeletedEndpointPayload(centralized, contentManagementSystemCode), userid);
		message.setTargetModule(controllerEmitter.getMessagingModuleId());
		message.setTargetComponent(GStandardModulesConstraints.RESOURCES_DISPOSE_COMPONENT);
		messageBroker.accept(message);

		message = GMessageEnvelope.newMessageFrom(controllerEmitter,
				newDeletedEndpointPayload(centralized, contentManagementSystemCode), userid);
		message.setTargetModule(GStandardModulesConstraints.CORE_MODULE);
		message.setTargetComponent(GStandardModulesConstraints.MONGO_DISPOSE_DOCUMENTS_COMPONENT);
		messageBroker.accept(message);

		message = GMessageEnvelope.newMessageFrom(controllerEmitter,
				newDeletedEndpointPayload(centralized, contentManagementSystemCode), userid);
		message.setTargetModule(GStandardModulesConstraints.VECTORIZATOR_MODULE);
		message.setTargetComponent(GStandardModulesConstraints.VECTORIZATION_DISPOSE_COMPONENT);
		messageBroker.accept(message);
	}

	/**
	 * Builds a deletion payload carrying the shareable centralized endpoint and the
	 * originating content management system code.
	 *
	 * @param centralized                 the centralized endpoint view.
	 * @param contentManagementSystemCode the originating content management system
	 *                                    code (may be null).
	 * @return a new deletion payload.
	 */
	private GDeletedProjectEndpointPayload newDeletedEndpointPayload(GCentralizedProjectEndpoint centralized,
			String contentManagementSystemCode) {
		GDeletedProjectEndpointPayload payload = new GDeletedProjectEndpointPayload(centralized);
		payload.setContentManagementSystemCode(contentManagementSystemCode);
		return payload;
	}

	/**
	 * Resolves the content management system code for the given concrete endpoint.
	 * The base implementation returns {@code null}; concrete controllers whose
	 * endpoints reference a specific content management system (e.g. Git) should
	 * override this so disposal can resolve the system without the concrete
	 * endpoint being available.
	 *
	 * @param endpoint the concrete endpoint being deleted.
	 * @return the content management system code, or {@code null} when not
	 *         applicable.
	 */
	protected String resolveContentManagementSystemCode(EndpointType endpoint) {
		return null;
	}

	/**
	 * Inserts the given endpoint into the persistent storage and reschedules if
	 * necessary.
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
		GCentralizedProjectEndpoint centralized = GCentralizedProjectEndpoint.of(outdata);
		replicationService.replicate(centralized);
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
	 * Updates the given endpoint in the persistent storage and reschedules if
	 * necessary.
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
		GCentralizedProjectEndpoint centralized = GCentralizedProjectEndpoint.of(outdata);
		replicationService.replicate(centralized);
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

	protected OperationStatus<GJobStatus> publish(EndpointType endpoint) {
		try {
			if (endpoint.getObjectSpaceType() == null) {
				endpoint.setObjectSpaceType(ObjectSpaceType.COMPANY);
			}
			EndpointType outdata = persistentObjectManager.update(endpoint);
			processReschedule(endpoint);
			GJobStatus job = jobQueueService.createNewAsyncJob(outdata, new NoContentConsumingSessionParam(),
					GWorkflowType.STANDARD.name(), GStandardWorkflow.INGESTION.name());
			return OperationStatus.of(job);
		} catch (Throwable exc) {
			LOGGER.error("Error publishing", exc);
			return OperationStatus.of(exc);
		}
	}

}