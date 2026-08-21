package ai.gebo.architecture.documents.cache.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import ai.gebo.application.messaging.model.GStandardModulesConstraints;
import ai.gebo.application.messaging.model.MetaEndpointType;
import ai.gebo.architecture.documents.cache.config.GeboDocumentsCacheConfig;
import ai.gebo.architecture.documents.cache.messaging.IDocumentChunkingMessagesReceiverFactoryComponent;
import ai.gebo.architecture.patterns.IGRuntimeBinder;
import ai.gebo.architecture.persistence.config.MongoConfig;
import ai.gebo.core.messages.GContentsProcessingStatusUpdatePayload;
import ai.gebo.core.messages.GDocumentReferencePayload;
import ai.gebo.model.base.GeboComponentInfo;

@Component

public class DocumentChunkingMessagesReceiverFactoryComponent extends GAbstractTimedOutMessageReceiverFactory
		implements IDocumentChunkingMessagesReceiverFactoryComponent {

	protected final IGRuntimeBinder runtimeBinder;
	private final static Logger LOGGER = LoggerFactory
			.getLogger(DocumentChunkingMessagesReceiverFactoryComponent.class);

	public class DocumentChunkingBatchGrouperReceiver extends GNestedBatchAggregatorMessageReceiver {

		public DocumentChunkingBatchGrouperReceiver(IGBatchMessagesReceiver nested, int flushThreshold) {
			super(nested, flushThreshold);

		}

	}

	/**
	 * The Mongo this node persists chunks into, or null when Mongo is not enabled
	 * ({@code MongoConfig} is {@code @ConditionalOnProperty}), which is why it is
	 * optional rather than a required constructor dependency.
	 */
	private final MongoConfig mongoConfig;

	public DocumentChunkingMessagesReceiverFactoryComponent(GeboDocumentsCacheConfig config,
			IGRuntimeBinder runtimeBinder, @Autowired(required = false) MongoConfig mongoConfig) {
		super(config.getDocumentChunkerReceiverConfig());
		this.runtimeBinder = runtimeBinder;
		this.mongoConfig = mongoConfig;

	}

	/**
	 * Reports the chunking step of the ingestion pipeline: source documents are
	 * split into chunks, and those chunks are retained in this node's Mongo
	 * through {@code ChunkingSessionRepository}, {@code DocumentCacheEntryRepository}
	 * and {@code DocumentChunkOperationRepository}.
	 *
	 * <p>
	 * The chunk store matters to a data-protection review in its own right: the
	 * chunks are verbatim extracts of the ingested documents, so whatever was in
	 * the source is also here until the {@code dispose-chunking-session-for-jobs}
	 * receiver removes it.
	 * </p>
	 */
	@Override
	public GDataFlowMetaInfos getDataFlowMetaInfos() {
		if (mongoConfig == null) {
			return null;
		}
		GDataFlowMetaInfos flow = new GDataFlowMetaInfos();
		flow.setComponent(new GeboComponentInfo(getMessagingModuleId(), getMessagingSystemId()));

		DataEndpoint chunkStore = DataEndpoint.of("chunk-cache", "Chunk cache and chunking sessions", "MongoDB",
				mongoConfig.getConnectionString(), MetaEndpointType.CHUNK, MetaEndpointType.DATABASE);
		chunkStore.setOutput(true);
		chunkStore.setLocality(DataEndpointLocality.hintFromLocator(chunkStore.getEndpoint()));
		// Chunks are verbatim extracts of whatever was ingested, so they inherit the
		// personal-data status of the sources rather than having one of their own.
		chunkStore.setPersonalData(true);
		chunkStore.setDisposer(new GeboComponentInfo(GStandardModulesConstraints.TOKENIZER_MODULE,
				ChunkingSessionDisposerReceiverFactory.DISPOSE_CHUNKING_SESSION_FOR_JOBS));
		flow.getDataEndpoints().add(chunkStore);

		// The chunker reports the chunk-cache store it owns, but not the
		// documents->chunk edge: that edge belongs to a specific data source and is
		// emitted by the content handler that owns the source (via the resolved
		// workflow structure), so the register connects the real source endpoint to
		// this cache instead of showing a transformation with no origin.
		return flow;
	}

	@Override
	public List<String> getEmittedPayloadTypes() {

		return List.of(GDocumentReferencePayload.class.getName(),
				GContentsProcessingStatusUpdatePayload.class.getName());
	}

	@Override
	public String getMessagingModuleId() {

		return GStandardModulesConstraints.TOKENIZER_MODULE;
	}

	@Override
	public String getMessagingSystemId() {
		return GStandardModulesConstraints.TOKENIZER_COMPONENT;
	}

	@Override
	public SystemComponentType getComponentType() {

		return SystemComponentType.APPLICATION_COMPONENT;
	}

	@Override
	public List<String> getAcceptedPayloadTypes() {

		return List.of(GDocumentReferencePayload.class.getName());
	}

	@Override
	public boolean isAcceptEveryPayloadType() {

		return false;
	}

	@Override
	public IGTimedOutMessageReceiver create() {

		return new DocumentChunkingBatchGrouperReceiver(
				runtimeBinder.getImplementationOf(DocumentChunkingBatchReceiver.class),
				factoryConfig.getFlushThreshold());
	}

}
