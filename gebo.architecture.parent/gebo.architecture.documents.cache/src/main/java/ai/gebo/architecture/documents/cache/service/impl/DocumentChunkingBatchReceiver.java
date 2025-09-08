package ai.gebo.architecture.documents.cache.service.impl;

import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ai.gebo.application.messaging.IGBatchMessagesReceiver;
import ai.gebo.application.messaging.model.GMessageEnvelope;
import ai.gebo.application.messaging.model.GMessagesBatchPayload;
import ai.gebo.application.messaging.workflow.IWorkflowRouter;
import ai.gebo.architecture.documents.cache.service.IChunkingParametersProvider;
import ai.gebo.architecture.documents.cache.service.IChunkingParametersProvider.ChunkingParams;
import ai.gebo.architecture.documents.cache.service.IDocumentChunkingMessagesReceiverFactoryComponent;
import ai.gebo.architecture.documents.cache.service.IDocumentsChunkService;
import ai.gebo.core.messages.GDocumentReferencePayload;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DocumentChunkingBatchReceiver implements IGBatchMessagesReceiver {
	private final IDocumentsChunkService chunkingService;
	private final IChunkingParametersProvider parameterProvider;
	private final IWorkflowRouter workflowRouter;
	private final IDocumentChunkingMessagesReceiverFactoryComponent emitter;
	private final static Logger LOGGER = LoggerFactory
			.getLogger(DocumentChunkingBatchReceiver.class);
	public void acceptSingleMessage(GMessageEnvelope envelope) {
		if (envelope.getPayload() instanceof GDocumentReferencePayload payload) {
			ChunkingParams params = parameterProvider.provideChunkingParams(payload.getDocumentReference());
			boolean processed = false;
			try {
				processed = chunkingService.prepareChunks(payload.getDocumentReference(), params.getSpecs(),
						params.isEnrichWithMetaData());
				if (processed) {
					workflowRouter.routeToNextSteps(envelope.getWorkflowType(), envelope.getWorkflowId(),
							envelope.getWorkflowStepId(), payload, emitter);
				}
			} catch (Throwable e) {
				LOGGER.error("Fail to prepare & deliver chunks =>" + processed, e);
			}
		}
	}

	@Override
	public void acceptMessages(GMessageEnvelope<GMessagesBatchPayload> messages) {
		GMessagesBatchPayload payloads = messages.getPayload();
		Stream<GMessageEnvelope> _stream = payloads.stream();
		_stream.forEach(message -> {
			acceptSingleMessage(message);
		});

	}
}