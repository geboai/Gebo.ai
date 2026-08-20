/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.ragsystem.content.fulltext.processor.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ai.gebo.application.messaging.GAbstractTimedOutMessageReceiverFactory;
import ai.gebo.application.messaging.IGBatchMessagesReceiver;
import ai.gebo.application.messaging.IGTimedOutMessageReceiver;
import ai.gebo.application.messaging.SystemComponentType;
import ai.gebo.application.messaging.model.DataEndpoint;
import ai.gebo.application.messaging.model.DataEndpointLocality;
import ai.gebo.application.messaging.model.DataTransformationInfo;
import ai.gebo.application.messaging.model.DataTransformationMetaInfo;
import ai.gebo.application.messaging.model.GDataFlowMetaInfos;
import ai.gebo.application.messaging.model.GMessageEnvelope;
import ai.gebo.application.messaging.model.GStandardModulesConstraints;
import ai.gebo.application.messaging.model.MetaEndpointType;
import ai.gebo.architecture.opensearch.config.OpenSearchConfig;
import ai.gebo.architecture.patterns.IGRuntimeBinder;
import ai.gebo.core.messages.GDocumentReferencePayload;
import ai.gebo.core.messages.GRawContentMessageFragmentPayload;
import ai.gebo.model.base.GeboComponentInfo;
import ai.gebo.ragsystem.content.fulltext.processor.config.GeboFullTextProcessorConfig;

/**
 * AI generated comments
 * 
 * Factory component responsible for creating content vectorization message
 * receivers. This singleton component handles the creation of message receivers
 * for vectorizing different types of content payloads within the RAG system.
 */
@ConditionalOnProperty(prefix = "ai.gebo.opensearch", name = "enabled", havingValue = "true")
@Component
@Scope("singleton")
public class GContentFullTextMessagesReceiverFactoryComponent extends GAbstractTimedOutMessageReceiverFactory {
	/** Configuration for the vectorizator component */
	final GeboFullTextProcessorConfig config;

	/**
	 * Constructor that initializes the factory with the vectorizator configuration.
	 * 
	 * @param config The configuration for the vectorizator
	 */
	/** The OpenSearch this node indexes into. */
	final OpenSearchConfig openSearchConfig;

	public GContentFullTextMessagesReceiverFactoryComponent(GeboFullTextProcessorConfig config,
			OpenSearchConfig openSearchConfig) {
		super(config.getVectorizatorReceiverConfig());
		this.config = config;
		this.openSearchConfig = openSearchConfig;
	}

	/**
	 * Reports the full-text indexing step: chunks are indexed into OpenSearch and
	 * retained there.
	 *
	 * <p>
	 * This component only exists when {@code ai.gebo.opensearch.enabled} is true
	 * (see the {@code @ConditionalOnProperty} on the class), so its presence in
	 * the register is itself the answer to "is full-text indexing configured on
	 * this installation".
	 * </p>
	 *
	 * <p>
	 * The locator is built from protocol, host and port only - the
	 * {@code username} and {@code password} that {@code OpenSearchConfig} also
	 * carries must never reach the register.
	 * </p>
	 */
	@Override
	public GDataFlowMetaInfos getDataFlowMetaInfos() {
		if (openSearchConfig == null || !openSearchConfig.isEnabled()) {
			return null;
		}
		GDataFlowMetaInfos flow = new GDataFlowMetaInfos();
		flow.setComponent(new GeboComponentInfo(getMessagingModuleId(), getMessagingSystemId()));

		DataEndpoint index = new DataEndpoint();
		index.setId("fulltext-index");
		index.setDescription("Full-text index of ingested chunks");
		index.setProduct("OpenSearch");
		index.setEndpoint(openSearchConfig.getProtocol() != null ? openSearchConfig.getProtocol().name() : null,
				openSearchConfig.getHost(), openSearchConfig.getPort(), null);
		index.setTypes(new ArrayList<MetaEndpointType>(
				List.of(MetaEndpointType.FULLTEXT_INDEX, MetaEndpointType.CHUNK)));
		index.setOutput(true);
		index.setLocality(DataEndpointLocality.hintFromLocator(index.getEndpoint()));
		// The indexed text is the ingested content itself, so it carries whatever
		// the sources carried.
		index.setPersonalData(true);
		if (openSearchConfig.getUsername() != null) {
			// Named so an auditor can see the index is credential-guarded; the
			// password itself is deliberately never carried.
			index.setSecretReference("ai.gebo.opensearch.username=" + openSearchConfig.getUsername());
		}
		flow.getDataEndpoints().add(index);

		DataTransformationMetaInfo indexer = DataTransformationMetaInfo.of("fulltext-indexer",
				"Indexes chunk text for lexical search", List.of(MetaEndpointType.CHUNK),
				List.of(MetaEndpointType.FULLTEXT_INDEX));
		flow.getEngines().add(indexer);

		flow.getTransformations().add(DataTransformationInfo.of("fulltext-indexing",
				"Chunks are indexed into OpenSearch and retained", indexer,
				GDataFlowMetaInfos.qualifiedId(
						new GeboComponentInfo(GStandardModulesConstraints.TOKENIZER_MODULE,
								GStandardModulesConstraints.TOKENIZER_COMPONENT),
						"chunk-cache"),
				flow.qualifiedId(index.getId())));
		return flow;
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

		return List.of(GRawContentMessageFragmentPayload.class.getName(), GDocumentReferencePayload.class.getName());
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

		return GStandardModulesConstraints.FULLTEXT_MODULE;
	}

	/**
	 * Returns the messaging system identifier for this factory.
	 * 
	 * @return The vectorization component ID from standard module constraints
	 */
	@Override
	public String getMessagingSystemId() {

		return GStandardModulesConstraints.FULLTEXT_INDEXING_COMPONENT;
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
	 * Nested class that implements a batch message receiver specifically for
	 * content vectorization. Extends the generic nested batch aggregator to provide
	 * content-specific processing logic.
	 */
	class BatchContentFulltextMessagesReceiver extends GNestedBatchAggregatorMessageReceiver {

		/**
		 * Constructor for the batch content full text indexer message receiver.
		 * 
		 * @param batchMessagesReceiver The batch message receiver implementation
		 * @param batchThreshold        The threshold for batching messages
		 */
		public BatchContentFulltextMessagesReceiver(IGBatchMessagesReceiver batchMessagesReceiver, int batchThreshold) {
			super(batchMessagesReceiver, batchThreshold);
		}

		/**
		 * Accepts messages for processing if they contain compatible payload types.
		 * 
		 * @param msg The message envelope to process
		 */
		@Override
		public void accept(GMessageEnvelope msg) {
			if (msg.getPayload() instanceof GDocumentReferencePayload) {
				super.accept(msg);
			}
		}

		/**
		 * Determines if a batch should be immediately processed based on cumulative
		 * content size.
		 * 
		 * @param messages The list of message envelopes in the current batch
		 * @return true if the cumulative size exceeds the configured threshold
		 */
		@Override
		protected boolean isImmediateFlushRequired(List<GMessageEnvelope> messages) {
			long cumulativeSize = 0l;
			for (GMessageEnvelope msg : messages) {
				if (msg.getPayload() instanceof GDocumentReferencePayload) {
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

		return new BatchContentFulltextMessagesReceiver(new FullTextIndexingBatchMessageReceiver(runtimeBinder),
				factoryConfig.getFlushThreshold() != null ? factoryConfig.getFlushThreshold() : 10);
	}

}