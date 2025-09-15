package ai.gebo.architecture.documents.cache.service.impl;

import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ai.gebo.application.messaging.IGBatchMessagesReceiver;
import ai.gebo.application.messaging.IGMessageBroker;
import ai.gebo.application.messaging.SystemComponentType;
import ai.gebo.application.messaging.model.GMessageEnvelope;
import ai.gebo.application.messaging.model.GMessagesBatchPayload;
import ai.gebo.application.messaging.model.GStandardModulesConstraints;
import ai.gebo.application.messaging.workflow.IWorkflowRouter;
import ai.gebo.architecture.documents.cache.model.DocumentChunkingResponse;
import ai.gebo.architecture.documents.cache.service.IChunkingParametersProvider;
import ai.gebo.architecture.documents.cache.service.IChunkingParametersProvider.ChunkingParams;
import ai.gebo.architecture.documents.cache.service.IDocumentChunkingMessagesReceiverFactoryComponent;
import ai.gebo.architecture.documents.cache.service.IDocumentsChunkService;
import ai.gebo.core.messages.GContentsProcessingStatusUpdatePayload;
import ai.gebo.core.messages.GDocumentReferencePayload;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DocumentChunkingBatchReceiver implements IGBatchMessagesReceiver {
	private final IDocumentsChunkService chunkingService;
	private final IChunkingParametersProvider parameterProvider;
	private final IWorkflowRouter workflowRouter;
	private final IDocumentChunkingMessagesReceiverFactoryComponent emitter;
	private final IGMessageBroker broker;
	private final static Logger LOGGER = LoggerFactory.getLogger(DocumentChunkingBatchReceiver.class);

	public GContentsProcessingStatusUpdatePayload acceptSingleMessage(GMessageEnvelope envelope) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin acceptSingleMessage(...)");
		}
		GContentsProcessingStatusUpdatePayload data = new GContentsProcessingStatusUpdatePayload();
		data.setWorkflowType(envelope.getWorkflowType() != null ? envelope.getWorkflowType().name() : null);
		data.setWorkflowId(envelope.getWorkflowId());
		data.setWorkflowStepId(envelope.getWorkflowStepId());
		if (envelope.getPayload() instanceof GDocumentReferencePayload payload) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Treating file:" + payload.getDocumentReference().getCode());
			}
			data.setBatchDocumentsInput(1);
			data.setJobId(payload.getJobId());

			DocumentChunkingResponse processed = null;
			try {
				ChunkingParams params = parameterProvider.provideChunkingParams(payload.getDocumentReference());
				processed = chunkingService.prepareChunks(payload.getDocumentReference(), params.getSpecs(),
						params.isEnrichWithMetaData(), params.getTokensPerChunkSet());

				data.setTokensProcessed(processed.getTotalTokensSize());
				data.setChunksProcessed(processed.getTotalChunksNumber());
				if (!processed.isEmpty()) {
					
					data.setBatchDocumentsProcessed(1);
					data.setBatchSentToNextStep(1);
					workflowRouter.routeToNextSteps(envelope.getWorkflowType(), envelope.getWorkflowId(),
							envelope.getWorkflowStepId(), payload, emitter);
				}else {
					if (LOGGER.isDebugEnabled()) {
						LOGGER.debug("Empty file: " + payload.getDocumentReference().getCode());
					}
				}
			} catch (Throwable e) {
				LOGGER.error("Fail to prepare & deliver chunks =>" + processed, e);
				data.setBatchDocumentsProcessingErrors(1);
			} finally {
				try {
					GMessageEnvelope<GContentsProcessingStatusUpdatePayload> _envelope = GMessageEnvelope
							.newMessageFrom(emitter, data);
					_envelope.setTargetModule(GStandardModulesConstraints.CORE_MODULE);
					_envelope.setTargetComponent(GStandardModulesConstraints.USER_MESSAGES_CONCENTRATOR_COMPONENT);
					_envelope.setTargetType(SystemComponentType.APPLICATION_COMPONENT);
					broker.accept(_envelope);
				} catch (Throwable th) {
				}
			}
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("End acceptSingleMessage(...) => " + data.toString());
		}
		return data;
	}

	@Override
	public void acceptMessages(GMessageEnvelope<GMessagesBatchPayload> messages) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin acceptMessages(...) with msg cardinality of:" + messages.getPayload().size());
		}
		GMessagesBatchPayload payloads = messages.getPayload();
		Stream<GMessageEnvelope> _stream = payloads.stream();
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Start contents chunking loop");
		}

		_stream.forEach(message -> {
			acceptSingleMessage(message);

		});

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("End acceptMessages(...)");
		}
	}
}