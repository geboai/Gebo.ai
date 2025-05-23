/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.ragsystem.content.vectorizator.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ai.gebo.application.messaging.GAbstractTimedOutMessageReceiverFactory;
import ai.gebo.application.messaging.IGBatchMessagesReceiver;
import ai.gebo.application.messaging.IGTimedOutMessageReceiver;
import ai.gebo.application.messaging.SystemComponentType;
import ai.gebo.application.messaging.model.GMessageEnvelope;
import ai.gebo.application.messaging.model.GStandardModulesConstraints;
import ai.gebo.architecture.patterns.IGRuntimeBinder;
import ai.gebo.core.messages.GDocumentMessageFragmentPayload;
import ai.gebo.core.messages.GDocumentReferencePayload;
import ai.gebo.core.messages.GRawContentMessageFragmentPayload;
import ai.gebo.ragsystem.content.vectorizator.IGEmbeddingRouter;
import ai.gebo.ragsystem.content.vectorizator.config.GeboVectorizatorConfig;

/**
 * AI generated comments
 * 
 * Factory component responsible for creating content vectorization message receivers.
 * This singleton component handles the creation of message receivers for vectorizing
 * different types of content payloads within the RAG system.
 */
@Component
@Scope("singleton")
public class GContentVectorizationMessagesReceiverFactoryComponent extends GAbstractTimedOutMessageReceiverFactory {
	/** Configuration for the vectorizator component */
	final GeboVectorizatorConfig config;

	/**
	 * Constructor that initializes the factory with the vectorizator configuration.
	 * 
	 * @param config The configuration for the vectorizator
	 */
	public GContentVectorizationMessagesReceiverFactoryComponent(GeboVectorizatorConfig config) {
		super(config.getVectorizatorReceiverConfig());
		this.config = config;
	}

	/** Runtime binder for dependency injection */
	@Autowired
	protected IGRuntimeBinder runtimeBinder;

	/**
	 * Specifies which payload types this factory's receivers can process.
	 * 
	 * @return A list of accepted payload type class names
	 */
	@Override
	public List<String> getAcceptedPayloadTypes() {

		return List.of(GDocumentMessageFragmentPayload.class.getName(),
				GRawContentMessageFragmentPayload.class.getName(), GDocumentReferencePayload.class.getName());
	}

	/**
	 * Indicates whether the factory accepts all payload types.
	 * 
	 * @return false, as this factory only accepts specific payload types
	 */
	@Override
	public boolean isAcceptEveryPayloadType() {

		return false;
	}

	/**
	 * Returns the messaging module identifier for this factory.
	 * 
	 * @return The vectorizator module ID from standard module constraints
	 */
	@Override
	public String getMessagingModuleId() {

		return GStandardModulesConstraints.VECTORIZATOR_MODULE;
	}

	/**
	 * Returns the messaging system identifier for this factory.
	 * 
	 * @return The vectorization component ID from standard module constraints
	 */
	@Override
	public String getMessagingSystemId() {

		return GStandardModulesConstraints.VECTORIZATION_COMPONENT;
	}

	/**
	 * Specifies the component type of this factory.
	 * 
	 * @return APPLICATION_COMPONENT as the system component type
	 */
	@Override
	public SystemComponentType getComponentType() {

		return SystemComponentType.APPLICATION_COMPONENT;
	}

	/**
	 * Nested class that implements a batch message receiver specifically for content vectorization.
	 * Extends the generic nested batch aggregator to provide content-specific processing logic.
	 */
	class BatchContentVectorizationMessagesReceiver extends GNestedBatchAggregatorMessageReceiver {

		/**
		 * Constructor for the batch content vectorization message receiver.
		 * 
		 * @param batchMessagesReceiver The batch message receiver implementation
		 * @param batchThreshold The threshold for batching messages
		 */
		public BatchContentVectorizationMessagesReceiver(IGBatchMessagesReceiver batchMessagesReceiver,
				int batchThreshold) {
			super(batchMessagesReceiver, batchThreshold);
		}

		/**
		 * Accepts messages for processing if they contain compatible payload types.
		 * 
		 * @param msg The message envelope to process
		 */
		@Override
		public void accept(GMessageEnvelope msg) {
			if (msg.getPayload() instanceof GDocumentMessageFragmentPayload
					|| msg.getPayload() instanceof GDocumentReferencePayload) {
				super.accept(msg);
			}
		}

		/**
		 * Determines if a batch should be immediately processed based on cumulative content size.
		 * 
		 * @param messages The list of message envelopes in the current batch
		 * @return true if the cumulative size exceeds the configured threshold
		 */
		@Override
		protected boolean isImmediateFlushRequired(List<GMessageEnvelope> messages) {
			long cumulativeSize = 0l;
			for (GMessageEnvelope msg : messages) {
				if (msg.getPayload() instanceof GDocumentMessageFragmentPayload) {
					GDocumentMessageFragmentPayload pl = (GDocumentMessageFragmentPayload) msg.getPayload();
					if (pl.getCumulativeLength() != null) {
						cumulativeSize += pl.getCumulativeLength().longValue();
					}
				} else if (msg.getPayload() instanceof GDocumentReferencePayload) {
					GDocumentReferencePayload payload = (GDocumentReferencePayload) msg.getPayload();
					if (payload.getDocumentReference() != null
							&& payload.getDocumentReference().getFileSize() != null) {
						cumulativeSize += payload.getDocumentReference().getFileSize().longValue();
					}
				}
			}
			return cumulativeSize >= config.getMaximumMessagesCumulatedBytesThreshold();
		}
	};

	/**
	 * Creates a new message receiver instance for content vectorization.
	 * 
	 * @return A timed-out message receiver configured for content vectorization
	 */
	@Override
	public IGTimedOutMessageReceiver create() {
		IGEmbeddingRouter router = runtimeBinder.getImplementationOf(IGEmbeddingRouter.class);
		return new BatchContentVectorizationMessagesReceiver(router,
				factoryConfig.getFlushThreshold() != null ? factoryConfig.getFlushThreshold() : 10);
	}

}