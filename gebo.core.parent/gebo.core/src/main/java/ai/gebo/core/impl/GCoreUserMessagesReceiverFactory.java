/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.core.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ai.gebo.application.messaging.GAbstractTimedOutMessageReceiverFactory;
import ai.gebo.application.messaging.IGBatchMessagesReceiver;
import ai.gebo.application.messaging.IGTimedOutMessageReceiver;
import ai.gebo.application.messaging.SystemComponentType;
import ai.gebo.application.messaging.model.GMessageEnvelope;
import ai.gebo.application.messaging.model.GMessagesBatchPayload;
import ai.gebo.application.messaging.model.GStandardModulesConstraints;
import ai.gebo.architecture.patterns.IGRuntimeBinder;
import ai.gebo.core.config.GeboCoreConfig;
import ai.gebo.core.messages.GContentsProcessingStatusUpdatePayload;
import ai.gebo.core.messages.GUserMessagePayload;
import ai.gebo.knlowledgebase.model.jobs.ContentsBatchProcessed;
import ai.gebo.knlowledgebase.model.jobs.GJobStatus;
import ai.gebo.knowledgebase.repositories.ContentsBatchProcessedRepository;
import ai.gebo.knowledgebase.repositories.JobStatusRepository;
import ai.gebo.knowledgebase.repositories.UserMessageRepository;
import ai.gebo.model.GUserMessage;

/**
 * Factory class to create message receivers for handling user messages,
 * contents processing statuses, and vectorization status updates within the
 * Gebo system. AI generated comments
 */
@Component
@Scope("singleton")
public class GCoreUserMessagesReceiverFactory extends GAbstractTimedOutMessageReceiverFactory {
	/**
	 * Constructor initializing the user message receiver configuration.
	 * 
	 * @param config The configuration object containing user message receiver
	 *               settings.
	 */
	public GCoreUserMessagesReceiverFactory(GeboCoreConfig config) {
		super(config.getUserMessagesReceiverConfig());
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
	 * @return the CORE_MODULE ID defined in standard module constraints.
	 */
	@Override
	public String getMessagingModuleId() {
		return GStandardModulesConstraints.CORE_MODULE;
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
			} else if (t.getPayload() instanceof GContentsProcessingStatusUpdatePayload) {
				GContentsProcessingStatusUpdatePayload payload = (GContentsProcessingStatusUpdatePayload) t
						.getPayload();
				ContentsBatchProcessed processed = new ContentsBatchProcessed();
				processed.setJobId(payload.getJobId());
				processed.setBatchDocumentsInput(payload.getBatchDocumentsInput());
				processed.setBatchDocumentsProcessingErrors(payload.getBatchDocumentsProcessingErrors());
				processed.setBatchDocumentsProcessed(payload.getBatchDocumentsProcessed());
				processed.setBatchSentToNextStep(payload.getBatchSentToNextStep());
				processed.setProcessedChunks(payload.getProcessedChunks());
				processed.setProcessedTokens(payload.getProcessedTokens());
				processed.setLastMessage(payload.getLastMessage());
				processed.setTimestamp(payload.getTimestamp());
				processed.setId(UUID.randomUUID().toString());
				processed.setWorkflowType(payload.getWorkflowType());
				processed.setWorkflowId(payload.getWorkflowId());
				processed.setWorkflowStepId(payload.getWorkflowStepId());
				ContentsBatchProcessedRepository repo = binder
						.getImplementationOf(ContentsBatchProcessedRepository.class);
				repo.insert(processed);
				Optional<GJobStatus> optstatus = jobsRepo.findById(payload.getJobId());
				if (optstatus.isPresent()) {
					GJobStatus status = optstatus.get();
					status.updateWith(processed);
					jobsRepo.save(status);
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