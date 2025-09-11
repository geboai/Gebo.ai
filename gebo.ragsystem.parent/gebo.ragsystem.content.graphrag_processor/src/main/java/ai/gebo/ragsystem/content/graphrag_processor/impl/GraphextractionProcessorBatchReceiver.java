package ai.gebo.ragsystem.content.graphrag_processor.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

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
import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.architecture.documents.cache.model.DocumentChunk;
import ai.gebo.architecture.documents.cache.model.DocumentChunkingResponse;
import ai.gebo.architecture.documents.cache.service.DocumentCacheAccessException;
import ai.gebo.architecture.documents.cache.service.IChunkingParametersProvider;
import ai.gebo.architecture.documents.cache.service.IChunkingParametersProvider.ChunkingParams;
import ai.gebo.architecture.documents.cache.service.IDocumentChunkingMessagesReceiverFactoryComponent;
import ai.gebo.architecture.documents.cache.service.IDocumentsChunkService;
import ai.gebo.architecture.graphrag.extraction.model.LLMExtractionResult;
import ai.gebo.architecture.graphrag.extraction.services.IGraphDataExtractionService;
import ai.gebo.architecture.graphrag.persistence.IKnowledgeGraphPersistenceService;
import ai.gebo.architecture.graphrag.persistence.model.KnowledgeExtractionData;
import ai.gebo.core.messages.GContentsProcessingStatusUpdatePayload;
import ai.gebo.core.messages.GDocumentReferencePayload;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.ragsystem.content.graphrag_processor.IGraphRagProcessorMessagesReceiverFactoryComponent;
import ai.gebo.system.ingestion.GeboIngestionException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class GraphextractionProcessorBatchReceiver implements IGBatchMessagesReceiver {
	private final IDocumentsChunkService chunkingService;

	private final IWorkflowRouter workflowRouter;
	private final IGraphRagProcessorMessagesReceiverFactoryComponent emitter;
	private final IGMessageBroker broker;
	private final IGraphDataExtractionService graphRagExtractionService;
	private final IKnowledgeGraphPersistenceService knowledgeGraphPersistenceService;
	private final static Logger LOGGER = LoggerFactory.getLogger(GraphextractionProcessorBatchReceiver.class);

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
				// Best effort approach
				Consumer<KnowledgeExtractionData> consumer = knowledgeGraphPersistenceService
						.knowledgeGraphUpdater(payload.getDocumentReference());
				KnowledgeExtractionIterator iterator = new KnowledgeExtractionIterator(payload.getDocumentReference(),
						chunkingService, graphRagExtractionService);
				Spliterator<KnowledgeExtractionData> spliterator = Spliterators.spliteratorUnknownSize(iterator,
						Spliterator.ORDERED | Spliterator.NONNULL);
				Stream<KnowledgeExtractionData> stream = StreamSupport.stream(spliterator, false);
				data.setBatchDocumentsProcessed(!iterator.isExceptionOccurred() ? 1 : 0);
				data.setBatchDocumentsProcessingErrors(iterator.isExceptionOccurred() ? 1 : 0);
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
			if (countData.getJobId() != null && countData.getWorkflowType() != null && countData.getWorkflowId() != null
					&& countData.getWorkflowStepId() != null) {
				String key = countData.getJobId() + "-" + countData.getWorkflowType() + "-" + countData.getWorkflowId()
						+ "-" + countData.getWorkflowStepId();
				if (!aggregated.containsKey(key)) {
					aggregated.put(key, countData);
				} else
					aggregated.get(key).incrementBy(countData);
			}
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