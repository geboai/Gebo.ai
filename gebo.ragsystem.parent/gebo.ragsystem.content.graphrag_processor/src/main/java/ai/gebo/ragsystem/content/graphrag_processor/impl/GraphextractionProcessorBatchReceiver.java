package ai.gebo.ragsystem.content.graphrag_processor.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
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
import ai.gebo.architecture.documents.cache.model.DocumentChunk;
import ai.gebo.architecture.documents.cache.model.DocumentChunkingResponse;
import ai.gebo.architecture.documents.cache.service.IDocumentsChunkService;
import ai.gebo.architecture.graphrag.extraction.services.IGraphDataExtractionService;
import ai.gebo.architecture.graphrag.persistence.model.GraphDocumentReference;
import ai.gebo.architecture.graphrag.persistence.model.KnowledgeExtractionData;
import ai.gebo.architecture.graphrag.persistence.model.KnowledgeExtractionEvent;
import ai.gebo.architecture.graphrag.services.IKnowledgeGraphPersistenceService;
import ai.gebo.core.messages.GContentsProcessingStatusUpdatePayload;
import ai.gebo.core.messages.GDocumentReferencePayload;
import ai.gebo.ragsystem.content.graphrag_processor.IGraphRagProcessorMessagesReceiverFactoryComponent;
import ai.gebo.ragsystem.content.graphrag_processor.config.GeboGraphRagProcessorConfig;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class GraphextractionProcessorBatchReceiver implements IGBatchMessagesReceiver {
	private final IDocumentsChunkService chunkingService;
	private final GeboGraphRagProcessorConfig staticConfig;

	private final IWorkflowRouter workflowRouter;
	private final IGraphRagProcessorMessagesReceiverFactoryComponent emitter;
	private final IGMessageBroker broker;
	private final IGraphDataExtractionService graphRagExtractionService;
	private final IKnowledgeGraphPersistenceService knowledgeGraphPersistenceService;
	private final GeboGraphRagProcessorConfig processorConfig;
	private final static Logger LOGGER = LoggerFactory.getLogger(GraphextractionProcessorBatchReceiver.class);
	private final GraphextractionHelper extractionHelper;

	class ProcessingStatusUpdater implements Consumer<KnowledgeExtractionEvent> {

		private String jobId;
		private String workflowType;
		private String workflowId;
		private String workflowStepId;
		private long howManyAccepted = 0;
		private static final long BATCHING_CYCLES_NUMBER = 20;
		private KnowledgeExtractionEvent cumulated = null;

		ProcessingStatusUpdater(String jobId, String workflowType, String workflowId, String workflowStepId) {
			this.jobId = jobId;
			this.workflowType = workflowType;
			this.workflowId = workflowId;
			this.workflowStepId = workflowStepId;
		}

		@Override
		public void accept(KnowledgeExtractionEvent t) {
			howManyAccepted++;
			if (cumulated == null)
				cumulated = t;
			else {
				cumulated.incrementBy(t);
			}
			if ((howManyAccepted >= BATCHING_CYCLES_NUMBER)) {
				flush();
			}
		}

		void flush() {
			if (cumulated != null) {
				GContentsProcessingStatusUpdatePayload payload = new GContentsProcessingStatusUpdatePayload();
				payload.setJobId(jobId);
				payload.setWorkflowType(workflowType);
				payload.setWorkflowId(workflowId);
				payload.setWorkflowStepId(workflowStepId);
				payload.setBatchDocumentsInput(0);
				payload.setChunksProcessed(cumulated.getProcessedSegments());
				payload.setTokensProcessed(cumulated.getProcessedTokens());
				GMessageEnvelope<GContentsProcessingStatusUpdatePayload> envelope = GMessageEnvelope
						.newMessageFrom(emitter, payload);
				envelope.setTargetModule(GStandardModulesConstraints.CORE_MODULE);
				envelope.setTargetComponent(GStandardModulesConstraints.USER_MESSAGES_CONCENTRATOR_COMPONENT);
				envelope.setTargetType(SystemComponentType.APPLICATION_COMPONENT);
				broker.accept(envelope);
				cumulated = null;
				howManyAccepted = 0;
			}

		}
	};

	public void acceptSingleMessage(GMessageEnvelope envelope) {

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin acceptSingleMessage(...)");
		}
		if (envelope.getPayload() instanceof GDocumentReferencePayload payload) {
			GContentsProcessingStatusUpdatePayload data = null;
			ProcessingStatusUpdater updatesConsumer = new ProcessingStatusUpdater(payload.getJobId(),
					envelope.getWorkflowType() != null ? envelope.getWorkflowType().name() : null,
					envelope.getWorkflowId(), envelope.getWorkflowStepId());
			try {
				data = new GContentsProcessingStatusUpdatePayload();
				data.setJobId(payload.getJobId());
				data.setWorkflowType(envelope.getWorkflowType() != null ? envelope.getWorkflowType().name() : null);
				data.setWorkflowId(envelope.getWorkflowId());
				data.setWorkflowStepId(envelope.getWorkflowStepId());
				data.setBatchDocumentsInput(1);
				// Best effort approach
				boolean discardFile = this.staticConfig.getDiscardedExtensions() != null && this.staticConfig
						.getDiscardedExtensions().contains(payload.getDocumentReference().getExtension());
				if (!discardFile) {
					final Map<String, Object> cache = new HashMap<String, Object>();
					knowledgeGraphPersistenceService.knowledgeGraphDelete(payload.getDocumentReference());
					final GraphDocumentReference graphDocumentObject = knowledgeGraphPersistenceService
							.knowledgeGraphInsertDocument(payload.getDocumentReference());
					ExecutorService ex = Executors.newFixedThreadPool(
							processorConfig.getGraphRagProcessorReceiverConfig().getConcurrentGraphExtractionWorkers());

					try {
						DocumentChunkingResponse current = chunkingService
								.getCachedChunkSet(payload.getDocumentReference());

						while (current != null && !current.isEmpty()) {
							Stream<DocumentChunk> chunks = current.getCurrentChunkSet().getChunks().stream();

							Stream<CompletableFuture<KnowledgeExtractionData>> futureStream = chunks
									.map((x) -> CompletableFuture.supplyAsync(() -> {
										try {
											long startTime = System.currentTimeMillis();
											KnowledgeExtractionData extraction = this.extractionHelper.doProcessChunk(x,
													payload.getDocumentReference(), cache);
											if (staticConfig.getGraphRagProcessorReceiverConfig()
													.getMinimumDelayBetweenRequests() > 0) {
												long elapsedTime = System.currentTimeMillis() - startTime;
												long delta = staticConfig.getGraphRagProcessorReceiverConfig()
														.getMinimumDelayBetweenRequests() - elapsedTime;
												if (delta > 0) {
													Thread.currentThread().sleep(delta);
												}
											}
											return extraction;
										} catch (Throwable th) {
											final String msg = "Error in async worker for dicument "
													+ payload.getDocumentReference().getCode();
											LOGGER.error(msg, th);
											throw new RuntimeException(msg, th);
										}
									}, ex));
							Stream<KnowledgeExtractionData> knowledgeExtractionStream = futureStream
									.map(CompletableFuture::join);
							this.knowledgeGraphPersistenceService.knowledgeGraphInsertChunks(
									payload.getDocumentReference(), graphDocumentObject, knowledgeExtractionStream,
									updatesConsumer, cache);
							if (current.getNextChunkSetId() != null) {
								current = chunkingService.getNextChunkSet(payload.getDocumentReference(),
										current.getId(), current.getNextChunkSetId());
							} else {
								current = null;
							}
						}
					} finally {
						ex.shutdown();
					}
					data.setBatchDocumentsProcessed(1);
					data.setBatchDocumentsProcessingErrors(0);
					workflowRouter.routeToNextSteps(envelope.getWorkflowType(), envelope.getWorkflowId(),
							envelope.getWorkflowStepId(), payload, emitter);
				} else {
					data.setBatchDiscardedInput(1);
				}
			} catch (Throwable th) {
				data.setBatchDocumentsProcessingErrors(1);
				LOGGER.error("Error accessing chunks for:" + payload.getDocumentReference().getCode(), th);
			} finally {
				try {
					updatesConsumer.flush();
				} catch (Throwable th) {
				}
				try {
					GMessageEnvelope<GContentsProcessingStatusUpdatePayload> sendEnvelope = GMessageEnvelope
							.newMessageFrom(emitter, data);
					sendEnvelope.setTargetModule(GStandardModulesConstraints.CORE_MODULE);
					sendEnvelope.setTargetComponent(GStandardModulesConstraints.USER_MESSAGES_CONCENTRATOR_COMPONENT);
					sendEnvelope.setTargetType(SystemComponentType.APPLICATION_COMPONENT);
					broker.accept(sendEnvelope);
				} catch (Throwable th) {
				}
			}
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("End acceptSingleMessage(...)");
		}

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
			acceptSingleMessage(message);

		});

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("End acceptMessages(...)");
		}
	}
}