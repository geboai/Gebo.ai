package ai.gebo.ragsystem.content.graphrag_processor.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.stereotype.Service;

import ai.gebo.application.messaging.IGBatchMessagesReceiver;
import ai.gebo.application.messaging.IGMessageBroker;
import ai.gebo.application.messaging.SystemComponentType;
import ai.gebo.application.messaging.model.GMessageEnvelope;
import ai.gebo.application.messaging.model.GMessagesBatchPayload;
import ai.gebo.application.messaging.model.GStandardModulesConstraints;
import ai.gebo.application.messaging.workflow.IWorkflowRouter;
import ai.gebo.architecture.documents.cache.model.DocumentChunk;
import ai.gebo.architecture.documents.cache.model.DocumentChunkingResponse;
import ai.gebo.architecture.documents.cache.service.IChunkingParametersProvider;
import ai.gebo.architecture.documents.cache.service.IChunkingParametersProvider.ChunkingParams;
import ai.gebo.architecture.documents.cache.service.IDocumentChunkingMessagesReceiverFactoryComponent;
import ai.gebo.architecture.documents.cache.service.IDocumentsChunkService;
import ai.gebo.architecture.graphrag.extraction.model.LLMExtractionResult;
import ai.gebo.architecture.graphrag.extraction.services.IGraphDataExtractionService;
import ai.gebo.core.messages.GContentsProcessingStatusUpdatePayload;
import ai.gebo.core.messages.GDocumentReferencePayload;
import ai.gebo.ragsystem.content.graphrag_processor.IGraphRagProcessorMessagesReceiverFactoryComponent;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class GraphRagProcessorBatchReceiver implements IGBatchMessagesReceiver {
	private final IDocumentsChunkService chunkingService;

	private final IWorkflowRouter workflowRouter;
	private final IGraphRagProcessorMessagesReceiverFactoryComponent emitter;
	private final IGMessageBroker broker;
	private final IGraphDataExtractionService graphRagExtractionService;
	private final static Logger LOGGER = LoggerFactory.getLogger(GraphRagProcessorBatchReceiver.class);

	public GContentsProcessingStatusUpdatePayload acceptSingleMessage(GMessageEnvelope envelope) {
		GContentsProcessingStatusUpdatePayload data = null;

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin acceptSingleMessage(...)");
		}
		if (envelope.getPayload() instanceof GDocumentReferencePayload payload) {
			try {
				data = new GContentsProcessingStatusUpdatePayload();
				data.setJobId(payload.getJobId());
				data.setWorkflowType(envelope.getWorkflowType() != null ? envelope.getWorkflowType().name() : null);
				data.setWorkflowId(envelope.getWorkflowId());
				data.setWorkflowStepId(envelope.getWorkflowStepId());
				data.setBatchDocumentsInput(1);
				DocumentChunkingResponse chunkingResponse = chunkingService
						.getCachedChunk(payload.getDocumentReference());
				if (!chunkingResponse.isEmpty()) {
					do {
						DocumentChunk chunk = chunkingResponse.getCurrentChunk();
						Document document = new Document(chunk.getId(), chunk.getChunkData(), chunk.getMetaData());
						LLMExtractionResult extraction = graphRagExtractionService.extract(document,
								payload.getDocumentReference());
						if (chunkingResponse.getNextChunkId() != null) {
							chunkingResponse = chunkingService.getNextChunk(payload.getDocumentReference(),
									chunkingResponse.getId(), chunkingResponse.getNextChunkId());
						} else {
							chunkingResponse = null;
						}
					} while (chunkingResponse != null && !chunkingResponse.isEmpty());
				}
				data.setBatchDocumentsProcessed(1);
				workflowRouter.routeToNextSteps(envelope.getWorkflowType(), envelope.getWorkflowId(),
						envelope.getWorkflowStepId(), payload, emitter);
			} catch (Throwable th) {
				data.setBatchDocumentsProcessingErrors(1);
				LOGGER.error("Error accessing chunks for:" + payload.getDocumentReference().getCode(), th);
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
			if (feedback != null)
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