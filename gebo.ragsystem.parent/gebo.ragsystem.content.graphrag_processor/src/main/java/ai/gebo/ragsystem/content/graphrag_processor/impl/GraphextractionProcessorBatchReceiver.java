package ai.gebo.ragsystem.content.graphrag_processor.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Predicate;
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
import ai.gebo.application.messaging.workflow.model.WorkflowContext;
import ai.gebo.application.messaging.workflow.model.WorkflowMessageContext;
import ai.gebo.architecture.documents.cache.model.DocumentChunk;
import ai.gebo.architecture.documents.cache.model.DocumentChunkingResponse;
import ai.gebo.architecture.documents.cache.service.IDocumentsChunkService;
import ai.gebo.architecture.graphrag.extraction.model.LLMExtractionResult;
import ai.gebo.architecture.graphrag.extraction.services.IGraphDataExtractionService;
import ai.gebo.architecture.graphrag.persistence.model.GraphDocumentReference;
import ai.gebo.architecture.graphrag.persistence.model.KnowledgeExtractionData;
import ai.gebo.architecture.graphrag.persistence.model.KnowledgeExtractionEvent;
import ai.gebo.architecture.graphrag.services.IKnowledgeGraphPersistenceService;
import ai.gebo.core.messages.GContentsProcessingStatusUpdatePayload;
import ai.gebo.core.messages.GDocumentReferencePayload;
import ai.gebo.model.DocumentMetaInfos;
import ai.gebo.model.base.GObjectRef;
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

	public void acceptSingleMessage(GMessageEnvelope envelope) {

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin acceptSingleMessage(...)");
		}
		if (envelope.getPayload() instanceof GDocumentReferencePayload payload) {
			GContentsProcessingStatusUpdatePayload data = null;
			GraphextractionProcessingStatusUpdater updatesConsumer = new GraphextractionProcessingStatusUpdater(
					payload.getJobId(), envelope.getWorkflowType() != null ? envelope.getWorkflowType().name() : null,
					envelope.getWorkflowId(), envelope.getWorkflowStepId(), broker, emitter);
			try {
				final Map<String, Object> cache = new HashMap<String, Object>();
				data = new GContentsProcessingStatusUpdatePayload();
				data.setJobId(payload.getJobId());
				data.setWorkflowType(envelope.getWorkflowType() != null ? envelope.getWorkflowType().name() : null);
				data.setWorkflowId(envelope.getWorkflowId());
				data.setWorkflowStepId(envelope.getWorkflowStepId());
				data.setBatchDocumentsInput(1);
				// Best effort approach
				boolean discardFile = this.staticConfig.getDiscardedExtensions() != null && this.staticConfig
						.getDiscardedExtensions().contains(payload.getDocumentReference().getExtension());
				if (!discardFile
						&& graphRagExtractionService.isTreatedDocument(payload.getDocumentReference(), cache)) {
					// delete the document and related data from the neo4j database
					knowledgeGraphPersistenceService.knowledgeGraphDelete(payload.getDocumentReference());
					// insert the document in the neo4j database
					final GraphDocumentReference graphDocumentObject = knowledgeGraphPersistenceService
							.knowledgeGraphInsertDocument(payload.getDocumentReference());
					// initialize a cache for process speedup

					// get a predicate to cluster multiple chunks on a single llm request if is
					// feasible
					final Predicate<List<Document>> splitPredicate = graphRagExtractionService
							.getBatchingPredicate(payload.getDocumentReference(), cache);
					// initializing an executor for parallel threads
					final ExecutorService ex = Executors.newFixedThreadPool(
							processorConfig.getGraphRagProcessorReceiverConfig().getConcurrentGraphExtractionWorkers());

					try {
						// get the first cached chunks group
						DocumentChunkingResponse current = chunkingService
								.getCachedChunkSet(payload.getDocumentReference());

						while (current != null && !current.isEmpty()) {
							// Stream the chunks in this group
							Stream<DocumentChunk> chunks = current.getCurrentChunkSet().getChunks().stream();
							// Transform to Documents
							Stream<Document> documentsStream = chunks
									.map(x -> new Document(x.getId(), x.getChunkData(), x.getMetaData()));
							// Batch documents according to grouping predicate (context window sizing)
							Stream<List<Document>> documentBatches = PredicateGrouping
									.groupGreedyDelayInvalidByPredicate(documentsStream, splitPredicate);
							// Setup an array of executions of batched contents for information extraction
							Stream<CompletableFuture<KnowledgeExtractionData>> futureStream = documentBatches
									.map((List<Document> x) -> CompletableFuture.supplyAsync(() -> {
										long startTime = System.currentTimeMillis();
										KnowledgeExtractionData extraction = null;
										try {
											if (LOGGER.isDebugEnabled()) {
												LOGGER.debug("doc:" + payload.getDocumentReference().getCode()
														+ " thread -> " + Thread.currentThread().getName()
														+ " docs cardinality=> " + x.size());
											}
											LLMExtractionResult extractData = this.graphRagExtractionService.extract(x,
													payload.getDocumentReference(), cache);
											extraction = new KnowledgeExtractionData(extractData, x, false);
											updatesConsumer.accept(createExtractionEvent(extraction));
										} catch (Throwable th) {
											final String msg = "Error in async worker for document "
													+ payload.getDocumentReference().getCode();
											LOGGER.error(msg, th);
											extraction = new KnowledgeExtractionData(new LLMExtractionResult(), x,
													true);
											updatesConsumer.accept(createExtractionEvent(extraction));
										}
										if (staticConfig.getGraphRagProcessorReceiverConfig()
												.getMinimumDelayBetweenRequests() > 0) {
											long elapsedTime = System.currentTimeMillis() - startTime;
											long delta = staticConfig.getGraphRagProcessorReceiverConfig()
													.getMinimumDelayBetweenRequests() - elapsedTime;
											if (delta > 0) {
												try {
													Thread.currentThread().sleep(delta);
												} catch (Throwable th) {
													final String msg = "Error in internal sleep(..) on async worker for document "
															+ payload.getDocumentReference().getCode();
													LOGGER.error(msg, th);
												}
											}
										}
										return extraction;
									}, ex));
							// Run the executions (in parallel)
							Stream<KnowledgeExtractionData> knowledgeExtractionStream = futureStream
									.map(CompletableFuture::join);
							// Save the results
							this.knowledgeGraphPersistenceService.knowledgeGraphInsertChunks(
									payload.getDocumentReference(), graphDocumentObject, knowledgeExtractionStream,
									cache);
							// Fetch next group of chunks
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
					data.setBatchDocumentsProcessed(updatesConsumer.isErrorsOccurred() ? 0 : 1);
					data.setBatchDocumentsProcessingErrors(updatesConsumer.isErrorsOccurred() ? 1 : 0);
					WorkflowContext workflowContext = new WorkflowContext(payload.getKnowledgeBase().getCode(),
							payload.getProject().getCode(), GObjectRef.of(payload.getEndPoint()));
					WorkflowMessageContext messageContext = new WorkflowMessageContext(workflowContext, payload);
					workflowRouter.routeToNextSteps(envelope.getWorkflowType(), envelope.getWorkflowId(),
							envelope.getWorkflowStepId(), messageContext, emitter);
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

	private KnowledgeExtractionEvent createExtractionEvent(KnowledgeExtractionData extraction) {
		long processedTokens = 0l;
		long processedBytes = 0l;
		long processedSegments = 0l;
		long errorChunks = 0l;
		long errorTokens = 0l;
		boolean error = extraction.isErrorProcessing();
		long totalSegments = 0;
		long totalTokens = 0;
		long totalBytes = 0;
		if (extraction.getDocumentChunks() != null) {
			totalSegments = extraction.getDocumentChunks().size();
			for (Document doc : extraction.getDocumentChunks()) {
				if (doc.getMetadata() != null && doc.getMetadata().containsKey(DocumentMetaInfos.GEBO_TOKEN_LENGTH)
						&& doc.getMetadata().get(DocumentMetaInfos.GEBO_TOKEN_LENGTH) instanceof Number tokens) {
					totalTokens += tokens.longValue();
				}
				if (doc.getMetadata() != null && doc.getMetadata().containsKey(DocumentMetaInfos.GEBO_BYTES_LENGTH)
						&& doc.getMetadata().get(DocumentMetaInfos.GEBO_BYTES_LENGTH) instanceof Number bytesLength) {
					totalBytes += bytesLength.longValue();
				}
			}
		}
		if (!error) {
			processedTokens = totalTokens;
			processedSegments = totalSegments;
			processedBytes = totalBytes;
		} else {
			errorTokens = totalTokens;
			errorChunks = totalSegments;
		}
		KnowledgeExtractionEvent evt = new KnowledgeExtractionEvent(processedTokens, processedBytes, processedSegments,
				errorChunks, errorTokens, error);
		return evt;
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