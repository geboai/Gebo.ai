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
import ai.gebo.jobs.services.controllers.JobLauncherController;
import ai.gebo.knlowledgebase.model.jobs.GJobStatus;
import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ai.gebo.application.messaging.IGMessageBroker;
import ai.gebo.application.messaging.SystemComponentType;
import ai.gebo.application.messaging.model.GMessageEnvelope;
import ai.gebo.application.messaging.model.GStandardModulesConstraints;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.core.messages.PublishProjectEndpointAckMessagePayload;
import ai.gebo.core.messages.PublishProjectEndpointMessagePayload;
import ai.gebo.jobs.services.GeboJobServiceException;
import ai.gebo.jobs.services.IGGeboIngestionJobQueueService;
import ai.gebo.jobs.services.IGJobLaunchManager;

/**
 * AI generated comments Implementation of the Job Launch Manager that handles
 * asynchronous job launching operations. This class is responsible for
 * processing project endpoint publishing requests and creating corresponding
 * jobs.
 */
@Component
@Scope("singleton")
public class JobLaunchManagerImpl implements IGJobLaunchManager {
	private final static Logger LOGGER = LoggerFactory.getLogger(JobLaunchManagerImpl.class);

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

	/**
	 * Constructor initializing the Job Launch Manager with required dependencies
	 * 
	 * @param jobLauncherController   Service for handling ingestion job queues
	 * @param persistentObjectManager Manager for persistent object operations
	 * @param broker                  Message broker for inter-component
	 *                                communication
	 */
	public JobLaunchManagerImpl(IGGeboIngestionJobQueueService jobLauncherController,
			IGPersistentObjectManager persistentObjectManager, IGMessageBroker broker) {
		this.jobLauncherController = jobLauncherController;
		this.persistentObjectManager = persistentObjectManager;
		this.broker = broker;

	}

	/**
	 * Returns the list of payload types that this component can emit
	 * 
	 * @return List of emitted payload types
	 */
	@Override
	public List<String> getEmittedPayloadTypes() {

		return List.of(PublishProjectEndpointAckMessagePayload.class.getName());
	}

	/**
	 * Returns the module ID for messaging
	 * 
	 * @return The module ID for this component
	 */
	@Override
	public String getMessagingModuleId() {

		return GStandardModulesConstraints.ASYNC_PUBLISHING_JOB_MODULE;
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
				// Create a new asynchronous job for the endpoint
				GJobStatus jobStatus = jobLauncherController.createNewAsyncJob(endpoint,
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