/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.architecture.workflows.concentrator.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ai.gebo.application.messaging.GAbstractTimedOutMessageReceiverFactory;
import ai.gebo.application.messaging.IGBatchMessagesReceiver;
import ai.gebo.application.messaging.IGMessageBroker;
import ai.gebo.application.messaging.IGMessageEmitter;
import ai.gebo.application.messaging.IGTimedOutMessageReceiver;
import ai.gebo.application.messaging.SystemComponentType;
import ai.gebo.application.messaging.model.GMessageEnvelope;
import ai.gebo.application.messaging.model.GMessagesBatchPayload;
import ai.gebo.application.messaging.model.GStandardModulesConstraints;
import ai.gebo.architecture.patterns.IGRuntimeBinder;
import ai.gebo.architecture.workflows.concentrator.config.GeboWorkflowsConcentratorConfig;
import ai.gebo.core.impl.GComputeEndOfWorkflowReceiverFactory;
import ai.gebo.core.messages.GContentsProcessingStatusUpdatePayload;
import ai.gebo.core.messages.GUserMessagePayload;
import ai.gebo.core.model.ComputeWorkflowEndPayload;
import ai.gebo.knlowledgebase.model.jobs.ContentsBatchProcessed;
import ai.gebo.knlowledgebase.model.jobs.GJobStatus;
import ai.gebo.knowledgebase.repositories.ContentsBatchProcessedRepository;
import ai.gebo.knowledgebase.repositories.JobStatusRepository;
import ai.gebo.knowledgebase.repositories.UserMessageRepository;
import ai.gebo.model.GUserMessage;

/**
 * Factory class to create message receivers for handling user messages,
 * contents processing statuses, and vectorization status updates. Externalized
 * out of {@code gebo.core} (formerly {@code GCoreUserMessagesReceiverFactory},
 * hosted on {@code core-module}) into its own deployable, hosted on
 * {@link GStandardModulesConstraints#JOBS_MASTER}. Also implements
 * {@link IGMessageEmitter} itself now, rather than borrowing core-module's
 * {@code GCoreMessagesEmitterImpl} identity, so the {@code ComputeWorkflowEndPayload}
 * relay to core-module's {@code end-of-workflow-compute-service} is correctly
 * tagged as coming from {@code jobs-master-module}.
 */
@Component
@Scope("singleton")
public class GWorkflowsConcentratorMessagesReceiverFactory extends GAbstractTimedOutMessageReceiverFactory
		implements IGMessageEmitter {
	/**
	 * Constructor initializing the user message receiver configuration.
	 *
	 * @param config The configuration object containing user message receiver
	 *               settings.
	 */
	public GWorkflowsConcentratorMessagesReceiverFactory(GeboWorkflowsConcentratorConfig config) {
		super(config.getReceiverConfig());
	}

	@Autowired
	IGRuntimeBinder binder;
	@Autowired
	JobStatusRepository jobsRepo;

	/**
	 * Returns the list of accepted payload types that this factory can handle.
	 *
	 * @return a list of payload type names.
	 */
	@Override
	public List<String> getAcceptedPayloadTypes() {
		return List.of(GUserMessagePayload.class.getName(), GContentsProcessingStatusUpdatePayload.class.getName());
	}

	/**
	 * Indicates whether every payload type is accepted by this receiver.
	 *
	 * @return false, indicating that not every payload type is accepted.
	 */
	@Override
	public boolean isAcceptEveryPayloadType() {
		return false;
	}

	/**
	 * Gets the ID of the messaging module used.
	 *
	 * @return the JOBS_MASTER ID defined in standard module constraints.
	 */
	@Override
	public String getMessagingModuleId() {
		return GStandardModulesConstraints.JOBS_MASTER;
	}

	/**
	 * Gets the ID of the messaging system component used.
	 *
	 * @return the USER_MESSAGES_CONCENTRATOR_COMPONENT ID defined in standard
	 *         module constraints.
	 */
	@Override
	public String getMessagingSystemId() {
		return GStandardModulesConstraints.USER_MESSAGES_CONCENTRATOR_COMPONENT;
	}

	/**
	 * Gets the type of the component.
	 *
	 * @return APPLICATION_COMPONENT indicating the component type.
	 */
	@Override
	public SystemComponentType getComponentType() {
		return SystemComponentType.APPLICATION_COMPONENT;
	}

	/**
	 * The only payload type this factory emits itself: the relay to core-module's
	 * end-of-workflow-compute-service once a workflow step's inputs are all
	 * accounted for. Everything else it handles is inbound (see
	 * {@link #getAcceptedPayloadTypes()}).
	 *
	 * @return a list containing only {@code ComputeWorkflowEndPayload}.
	 */
	@Override
	public List<String> getEmittedPayloadTypes() {
		return List.of(ComputeWorkflowEndPayload.class.getName());
	}

	/**
	 * Retrieves the job status based on the job ID.
	 *
	 * @param id The ID of the job.
	 * @return The job status if found, null otherwise.
	 */
	private GJobStatus retrieveJS(String id) {
		Optional<GJobStatus> job = jobsRepo.findById(id);
		if (job.isPresent())
			return job.get();
		else
			return null;
	}

	/**
	 * Inner class for handling nested user messages with batch aggregation.
	 */
	class NestedUserMessagesAggregatorMessageReceiver extends GNestedBatchAggregatorMessageReceiver {

		/**
		 * Constructor initializing nested batch aggregator message receiver.
		 *
		 * @param nested         The nested batch messages receiver.
		 * @param flushThreshold The threshold for flushing messages.
		 */
		public NestedUserMessagesAggregatorMessageReceiver(IGBatchMessagesReceiver nested, int flushThreshold) {
			super(nested, flushThreshold);
		}

		private static boolean isMoreThanZero(Number n) {
			return n != null && n.longValue() > 0l;
		}

		/**
		 * Accepts a message envelope and processes payloads accordingly.
		 *
		 * @param t The message envelope.
		 */
		@Override
		public void accept(GMessageEnvelope t) {
			if (t.getPayload() instanceof GUserMessagePayload) {
				// Pass user message payloads to the super class for processing
				super.accept(t);
			} else if (t.getPayload() instanceof GContentsProcessingStatusUpdatePayload payload) {

				ContentsBatchProcessed processed = new ContentsBatchProcessed();
				processed.setJobId(payload.getJobId());
				processed.setWorkflowType(payload.getWorkflowType());
				processed.setWorkflowId(payload.getWorkflowId());
				processed.setWorkflowStepId(payload.getWorkflowStepId());
				processed.setBatchDocumentsInput(payload.getBatchDocumentsInput());
				processed.setBatchDocumentsProcessingErrors(payload.getBatchDocumentsProcessingErrors());
				processed.setBatchDocumentsProcessed(payload.getBatchDocumentsProcessed());
				processed.setBatchDiscardedInput(payload.getBatchDiscardedInput());
				processed.setBatchSentToNextStep(payload.getBatchSentToNextStep());
				processed.setChunksProcessed(payload.getChunksProcessed());
				processed.setTokensProcessed(payload.getTokensProcessed());
				processed.setLastMessage(payload.getLastMessage());
				processed.setTimestamp(t.getTimestamp());
				processed.setId(UUID.randomUUID().toString());
				ContentsBatchProcessedRepository repo = binder
						.getImplementationOf(ContentsBatchProcessedRepository.class);
				repo.insert(processed);
				boolean stepAdvancement = (payload.getLastMessage() != null && payload.getLastMessage())
						|| isMoreThanZero(payload.getBatchDocumentsProcessed())
						|| isMoreThanZero(payload.getBatchDocumentsProcessingErrors());
				if (stepAdvancement) {
					ComputeWorkflowEndPayload compute = new ComputeWorkflowEndPayload();
					compute.setJobId(payload.getJobId());
					compute.setWorkflowType(payload.getWorkflowType());
					compute.setWorkflowId(payload.getWorkflowId());
					IGMessageBroker broker = binder.getImplementationOf(IGMessageBroker.class);
					GMessageEnvelope<ComputeWorkflowEndPayload> envelope = GMessageEnvelope
							.newMessageFrom(GWorkflowsConcentratorMessagesReceiverFactory.this, compute);
					envelope.setTargetModule(GStandardModulesConstraints.CORE_MODULE);
					envelope.setTargetComponent(GComputeEndOfWorkflowReceiverFactory.END_OF_WORKFLOW_COMPUTE_SERVICE);
					envelope.setTargetType(SystemComponentType.APPLICATION_COMPONENT);
					broker.accept(envelope);
				}
			} else {
				throw new IllegalStateException("This receiver cannot handle payload type:" + t.getPayloadType());
			}
		}
	}

	/**
	 * Inner class for receiving messages in batches related to user messages.
	 */
	class NestedBatchUserMessagesReceiver implements IGBatchMessagesReceiver {
		UserMessageRepository umrepo;

		/**
		 * Constructor to initialize the user message repository.
		 *
		 * @param umrepo The UserMessageRepository object.
		 */
		NestedBatchUserMessagesReceiver(UserMessageRepository umrepo) {
			this.umrepo = umrepo;
		}

		/**
		 * Processes and accepts a batch of user messages and persists them.
		 *
		 * @param messages The batch of messages to accept and process.
		 */
		@Override
		public void acceptMessages(GMessageEnvelope<GMessagesBatchPayload> messages) {
			GMessagesBatchPayload payload = messages.getPayload();
			Stream<GMessageEnvelope<GUserMessagePayload>> stream = payload.stream();
			List<GUserMessage> usermessages = stream.map(x -> {
				return x.getPayload().getUserMessage();
			}).toList();
			if (!usermessages.isEmpty())
				umrepo.saveAll(usermessages);
		}
	}

	/**
	 * Creates and returns a new instance of IGTimedOutMessageReceiver.
	 *
	 * @return An instance of IGTimedOutMessageReceiver.
	 */
	@Override
	public IGTimedOutMessageReceiver create() {
		return new NestedUserMessagesAggregatorMessageReceiver(
				new NestedBatchUserMessagesReceiver(binder.getImplementationOf(UserMessageRepository.class)),
				factoryConfig.getFlushThreshold() != null ? factoryConfig.getFlushThreshold() : 10);
	}
}
