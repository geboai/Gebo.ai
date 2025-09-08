package ai.gebo.architecture.documents.cache.service.impl;

import java.util.List;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import ai.gebo.application.messaging.GAbstractTimedOutMessageReceiverFactory;
import ai.gebo.application.messaging.IGBatchMessagesReceiver;
import ai.gebo.application.messaging.IGTimedOutMessageReceiver;
import ai.gebo.application.messaging.SystemComponentType;
import ai.gebo.application.messaging.model.GMessageEnvelope;
import ai.gebo.application.messaging.model.GMessagesBatchPayload;
import ai.gebo.application.messaging.model.GStandardModulesConstraints;
import ai.gebo.application.messaging.workflow.IWorkflowRouter;
import ai.gebo.architecture.documents.cache.config.GeboDocumentsCacheConfig;
import ai.gebo.architecture.documents.cache.service.IChunkingMessagingComponent;
import ai.gebo.architecture.documents.cache.service.IChunkingParametersProvider;
import ai.gebo.architecture.documents.cache.service.IChunkingParametersProvider.ChunkingParams;
import ai.gebo.architecture.documents.cache.service.IDocumentsChunkService;
import ai.gebo.core.messages.GDocumentReferencePayload;

@Component

public class ChunkingMessagingComponentImpl extends GAbstractTimedOutMessageReceiverFactory
		implements IChunkingMessagingComponent {
	private final IDocumentsChunkService chunkingService;
	private final IChunkingParametersProvider parameterProvider;
	private final IWorkflowRouter workflowRouter;
	private final static Logger LOGGER = LoggerFactory.getLogger(ChunkingMessagingComponentImpl.class);
	private BatchChunkingGroupingReceiver batchReceiver = null;

	public class BatchChunkingGroupingReceiver extends GNestedBatchAggregatorMessageReceiver {

		public BatchChunkingGroupingReceiver(IGBatchMessagesReceiver nested, int flushThreshold) {
			super(nested, flushThreshold);

		}

	}

	public class BatchChunkingReceiver implements IGBatchMessagesReceiver {

		public void acceptSingleMessage(GMessageEnvelope envelope) {
			if (envelope.getPayload() instanceof GDocumentReferencePayload payload) {
				ChunkingParams params = parameterProvider.provideChunkingParams(payload.getDocumentReference());
				boolean processed = false;
				try {
					processed = chunkingService.prepareChunks(payload.getDocumentReference(), params.getSpecs(),
							params.isEnrichWithMetaData());
					if (processed) {
						workflowRouter.routeToNextSteps(envelope.getWorkflowType(), envelope.getWorkflowId(),
								envelope.getWorkflowStepId(), payload, ChunkingMessagingComponentImpl.this);
					}
				} catch (Throwable e) {
					LOGGER.error("Fail to prepare & deliver chunks =>" + processed, e);
				}
			}
		}

		@Override
		public void acceptMessages(GMessageEnvelope<GMessagesBatchPayload> messages) {
			GMessagesBatchPayload payloads = messages.getPayload();
			Stream<GMessageEnvelope> _stream=payloads.stream();
			_stream.forEach(message->{
				acceptSingleMessage(message);
			});

		}
	}

	public ChunkingMessagingComponentImpl(GeboDocumentsCacheConfig config, IDocumentsChunkService chunkingService,
			IChunkingParametersProvider parameterProvider, IWorkflowRouter workflowRouter) {
		super(config.getDocumentChunkerReceiverConfig());
		batchReceiver = new BatchChunkingGroupingReceiver(new BatchChunkingReceiver(),
				config.getDocumentChunkerReceiverConfig().getFlushThreshold());
		this.chunkingService = chunkingService;
		this.parameterProvider = parameterProvider;
		this.workflowRouter = workflowRouter;
	}

	@Override
	public List<String> getEmittedPayloadTypes() {

		return List.of(GDocumentReferencePayload.class.getName());
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

		return batchReceiver;
	}

}
