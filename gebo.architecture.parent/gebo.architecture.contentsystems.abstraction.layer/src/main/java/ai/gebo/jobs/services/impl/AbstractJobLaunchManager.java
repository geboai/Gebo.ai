/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.jobs.services.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ai.gebo.application.messaging.IGMessageBroker;
import ai.gebo.application.messaging.SystemComponentType;
import ai.gebo.application.messaging.model.GMessageEnvelope;
import ai.gebo.application.messaging.model.GStandardModulesConstraints;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.core.messages.GRescheduleProjectEndpointMessagePayload;
import ai.gebo.core.messages.PublishProjectEndpointAckMessagePayload;
import ai.gebo.core.messages.PublishProjectEndpointMessagePayload;
import ai.gebo.jobs.services.GeboJobServiceException;
import ai.gebo.jobs.services.IGGeboIngestionJobQueueService;
import ai.gebo.jobs.services.IGJobLaunchManager;
import ai.gebo.knlowledgebase.model.jobs.GJobStatus;
import ai.gebo.knlowledgebase.model.projects.AbstractContentConsumingSessionParam;
import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.knowledgebase.repositories.JobStatusRepository;

/**
 * Base implementation of the Job Launch Manager that handles asynchronous job
 * launching operations. Processes project endpoint publishing requests and
 * creates corresponding jobs.
 *
 * <p>
 * {@code messagingSystemId} is fixed ({@link GStandardModulesConstraints#ASYNC_PUBLISHING_JOB_COMPONENT})
 * while {@code messagingModuleId} is assignable per concrete subclass: the
 * monolith registers one instance under the shared
 * {@link GStandardModulesConstraints#ASYNC_PUBLISHING_JOB_MODULE} constant,
 * while under microservices each content-handler registers its own instance
 * under its own already-owned module id (mirroring the
 * {@code GJobStatusReplicatorService} precedent), so
 * {@link ai.gebo.microservices.topology.GeboMicroservicesTopology}'s
 * one-owner-per-module rule is never violated.
 * </p>
 */
public abstract class AbstractJobLaunchManager implements IGJobLaunchManager {
	private final static Logger LOGGER = LoggerFactory.getLogger(AbstractJobLaunchManager.class);

	/**
	 * Service for handling ingestion job queues
	 */
	private final IGGeboIngestionJobQueueService jobLauncherController;

	/**
	 * Manager for persistent object operations
	 */
	private final IGPersistentObjectManager persistentObjectManager;

	/**
	 * Message broker for inter-component communication
	 */
	private final IGMessageBroker broker;

	private final JobStatusRepository jobStatusRepositoy;

	protected final String messagingModuleId;

	/**
	 * Constructor initializing the Job Launch Manager with required dependencies
	 *
	 * @param jobLauncherController   Service for handling ingestion job queues
	 * @param persistentObjectManager Manager for persistent object operations
	 * @param broker                  Message broker for inter-component
	 *                                communication
	 * @param messagingModuleId       The messaging module id this instance
	 *                                registers under.
	 */
	protected AbstractJobLaunchManager(IGGeboIngestionJobQueueService jobLauncherController,
			IGPersistentObjectManager persistentObjectManager, IGMessageBroker broker,
			JobStatusRepository jobStatusRepositoy, String messagingModuleId) {
		this.jobLauncherController = jobLauncherController;
		this.persistentObjectManager = persistentObjectManager;
		this.broker = broker;
		this.jobStatusRepositoy = jobStatusRepositoy;
		this.messagingModuleId = messagingModuleId;

	}

	/**
	 * Returns the list of payload types that this component can emit.
	 *
	 * <p>
	 * {@code GRescheduleProjectEndpointMessagePayload} is included so
	 * {@code GAbstractSystemsArchitectureController} (and the other reschedule
	 * call sites) can use this bean itself as the "from" emitter when requesting
	 * a reschedule: since {@link #getMessagingModuleId()} is exactly wherever
	 * this bean is registered as a {@code PublishProjectEndpointMessagePayload}
	 * receiver, the resulting envelope's {@code sourceModule} is automatically
	 * the correct target for the central scheduler to dispatch the eventual
	 * publish message back to - unlike a per-handler controller emitter, which
	 * stays per-handler-scoped even on the monolith where this bean's own module
	 * id is the single shared constant.
	 * </p>
	 *
	 * @return List of emitted payload types
	 */
	@Override
	public List<String> getEmittedPayloadTypes() {

		return List.of(PublishProjectEndpointAckMessagePayload.class.getName(),
				GRescheduleProjectEndpointMessagePayload.class.getName());
	}

	/**
	 * Returns the module ID for messaging
	 *
	 * @return The module ID for this component
	 */
	@Override
	public String getMessagingModuleId() {

		return messagingModuleId;
	}

	/**
	 * Returns the system ID for messaging
	 * 
	 * @return The system ID for this component
	 */
	@Override
	public String getMessagingSystemId() {

		return GStandardModulesConstraints.ASYNC_PUBLISHING_JOB_COMPONENT;
	}

	/**
	 * Returns the component type for the messaging system
	 * 
	 * @return The component type
	 */
	@Override
	public SystemComponentType getComponentType() {

		return SystemComponentType.APPLICATION_COMPONENT;
	}

	/**
	 * Returns the list of payload types that this component can accept
	 * 
	 * @return List of accepted payload types
	 */
	@Override
	public List<String> getAcceptedPayloadTypes() {

		return List.of(PublishProjectEndpointMessagePayload.class.getName());
	}

	/**
	 * Indicates whether this component accepts all payload types
	 * 
	 * @return false - this component only accepts specific payload types
	 */
	@Override
	public boolean isAcceptEveryPayloadType() {

		return false;
	}

	/**
	 * Processes incoming message envelopes When a
	 * PublishProjectEndpointMessagePayload is received, it creates a new job and
	 * sends back an acknowledgment message
	 * 
	 * @param envelope The message envelope to process
	 */
	@Override
	public void accept(GMessageEnvelope envelope) {
		if (envelope.getPayload() instanceof PublishProjectEndpointMessagePayload) {
			PublishProjectEndpointMessagePayload payload = (PublishProjectEndpointMessagePayload) envelope.getPayload();

			try {
				// Find the endpoint from the reference
				GProjectEndpoint endpoint = persistentObjectManager.findByReference(payload.getProjectEndpoint(),
						GProjectEndpoint.class);
				AbstractContentConsumingSessionParam sessionParam = payload.getSessionParam();
				// Create a new asynchronous job for the endpoint
				GJobStatus jobStatus = jobLauncherController.createNewAsyncJob(endpoint, sessionParam,
						envelope.getWorkflowType() != null ? envelope.getWorkflowType().name() : null,
						envelope.getWorkflowId());
				// Create an acknowledgment message
				PublishProjectEndpointAckMessagePayload ackPayload = new PublishProjectEndpointAckMessagePayload();
				ackPayload.setJobId(jobStatus.getCode());
				ackPayload.setCorrelationId(payload.getCorrelationId());
				ackPayload.setProjectEndpoint(payload.getProjectEndpoint());
				// Create an envelope and set the target
				GMessageEnvelope<PublishProjectEndpointAckMessagePayload> ackEnvelope = GMessageEnvelope
						.newMessageFrom(this, ackPayload);
				ackEnvelope.setTargetModule(envelope.getSourceModule());
				ackEnvelope.setTargetComponent(envelope.getSourceComponent());
				// Send the acknowledgment
				this.broker.accept(ackEnvelope);
			} catch (GeboJobServiceException | GeboPersistenceException e) {
				LOGGER.error("Error launching job", e);
			}
		}

	}

}