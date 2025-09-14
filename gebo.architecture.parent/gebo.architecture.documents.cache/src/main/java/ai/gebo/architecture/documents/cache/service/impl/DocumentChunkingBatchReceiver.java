package ai.gebo.architecture.documents.cache.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
			data.setJobId(payload.getJobId());
			ChunkingParams params = parameterProvider.provideChunkingParams(payload.getDocumentReference());

			DocumentChunkingResponse processed = null;
			try {
				data.setBatchDocumentsInput(1);
				processed = chunkingService.prepareChunks(payload.getDocumentReference(), params.getSpecs(),
						params.isEnrichWithMetaData());
				if (!processed.isEmpty()) {
					workflowRouter.routeToNextSteps(envelope.getWorkflowType(), envelope.getWorkflowId(),
							envelope.getWorkflowStepId(), payload, emitter);

				}
				data.setTokensProcessed(processed.getTotalTokensSize());
				data.setChunksProcessed(processed.getTotalChunksNumber());
				data.setBatchDocumentsProcessed(1);
				data.setBatchSentToNextStep(1);
			} catch (Throwable e) {
				LOGGER.error("Fail to prepare & deliver chunks =>" + processed, e);
				data.setBatchDocumentsProcessingErrors(1);
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
		final List<GContentsProcessingStatusUpdatePayload> feedbakcs = new ArrayList<GContentsProcessingStatusUpdatePayload>();
		_stream.forEach(message -> {
			GContentsProcessingStatusUpdatePayload feedback = acceptSingleMessage(message);
			feedbakcs.add(feedback);
		});
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("End contents chunking loop with cardinality=>" + feedbakcs.size());
		}
		final Map<String, GContentsProcessingStatusUpdatePayload> aggregated = new HashMap<String, GContentsProcessingStatusUpdatePayload>();
		feedbakcs.stream().forEach(countData -> {
			String key = countData.getJobId() + "-" + countData.getWorkflowType() + "-" + countData.getWorkflowId()
					+ "-" + countData.getWorkflowStepId();
			if (!aggregated.containsKey(key)) {
				aggregated.put(key, countData);
			} else
				aggregated.get(key).incrementBy(countData);
		});
		aggregated.values().stream().forEach(payload -> {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Notifying concentrator component with:" + payload.toString());
			}
			GMessageEnvelope<GContentsProcessingStatusUpdatePayload> envelope = GMessageEnvelope.newMessageFrom(emitter,
					payload);
			envelope.setTargetModule(GStandardModulesConstraints.CORE_MODULE);
			envelope.setTargetComponent(GStandardModulesConstraints.USER_MESSAGES_CONCENTRATOR_COMPONENT);
			envelope.setTargetType(SystemComponentType.APPLICATION_COMPONENT);
			broker.accept(envelope);
		});
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("End acceptMessages(...)");
		}
	}
}