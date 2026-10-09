package ai.gebo.architecture.documents.cache.service.impl;

import java.util.Date;
import java.util.List;
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
import ai.gebo.application.messaging.workflow.model.WorkflowContext;
import ai.gebo.application.messaging.workflow.model.WorkflowMessageContext;
import ai.gebo.architecture.documents.cache.messaging.IDocumentChunkingMessagesReceiverFactoryComponent;
import ai.gebo.architecture.documents.cache.model.ChunkingParams;
import ai.gebo.architecture.documents.cache.model.DocumentChunkingResponse;
import ai.gebo.architecture.documents.cache.service.IChunkingParametersProvider;
import ai.gebo.architecture.documents.cache.service.IDocumentsChunkService;
import ai.gebo.core.messages.GContentsProcessingStatusUpdatePayload;
import ai.gebo.core.messages.GDocumentReferencePayload;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
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
			if (unchangedSinceLastIngestion(payload)) {
				// same date and size as when last ingested: not read again
				data.setBatchDiscardedInput(1);
				data.setTimestamp(new Date());
				sendStatus(data);
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("End acceptSingleMessage(...) => " + data.toString());
				}
				return data;
			}
			try {
				// the job's session, disposed or not (a late batch reopens it), created by the
				// first batch; one created meanwhile by a concurrent batch is taken
				String chunkingSessionId = chunkingService.retrieveChunkingSession("job:" + payload.getJobId());
				if (chunkingSessionId == null) {
					try {
						chunkingSessionId = chunkingService.createChunkingSession("job:" + payload.getJobId());
					} catch (IllegalStateException e) {
						chunkingSessionId = chunkingService.retrieveChunkingSession("job:" + payload.getJobId());
						if (chunkingSessionId == null) {
							throw e;
						}
						if (LOGGER.isDebugEnabled()) {
							LOGGER.debug("The chunking session of job:" + payload.getJobId()
									+ " was created by a concurrent batch: " + chunkingSessionId);
						}
					}
				}

				ChunkingParams params = parameterProvider.provideChunkingParams(payload.getDocumentReference());
				processed = chunkingService.prepareChunks(payload.getDocumentReference(), params,
						chunkingSessionId);

				data.setTokensProcessed(processed.getTotalTokensSize());
				data.setChunksProcessed(processed.getTotalChunksNumber());
				data.setTimestamp(new Date());
				if (!processed.isEmpty() && sameTextAsLastIngested(payload, processed.getContentHash())) {
					// read again, the same text as when last ingested: its indexes stay as they are
					data.setBatchDiscardedInput(1);
				} else if (!processed.isEmpty()) {
					// the hash of what is indexed now, acknowledged back to the content handler
					payload.setHash(processed.getContentHash());
					data.setBatchDocumentsProcessed(1);
					data.setBatchSentToNextStep(1);

					WorkflowContext workflowContext = new WorkflowContext(payload.getKnowledgeBase().getCode(),
							payload.getProject().getCode(), payload.getEndPoint().getRemoteProjectReference());
					WorkflowMessageContext messageContext = new WorkflowMessageContext(workflowContext, payload);
					workflowRouter.routeToNextSteps(envelope.getWorkflowType(), envelope.getWorkflowId(),
							envelope.getWorkflowStepId(), messageContext, emitter);
				} else {
					data.setBatchDiscardedInput(1);
					if (LOGGER.isDebugEnabled()) {
						LOGGER.debug("Empty file: " + payload.getDocumentReference().getCode());
					}
				}
			} catch (Throwable e) {
				LOGGER.error("Fail to prepare & deliver chunks =>" + processed, e);
				data.setBatchDocumentsProcessingErrors(1);
			} finally {
				sendStatus(data);
			}
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("End acceptSingleMessage(...) => " + data.toString());
		}
		return data;
	}

	private void sendStatus(GContentsProcessingStatusUpdatePayload data) {
		try {
			GMessageEnvelope<GContentsProcessingStatusUpdatePayload> _envelope = GMessageEnvelope
					.newMessageFrom(emitter, data);
			_envelope.setTargetModule(GStandardModulesConstraints.JOBS_MASTER);
			_envelope.setTargetComponent(GStandardModulesConstraints.USER_MESSAGES_CONCENTRATOR_COMPONENT);
			_envelope.setTargetType(SystemComponentType.APPLICATION_COMPONENT);
			broker.accept(_envelope);
		} catch (Throwable th) {
		}
	}

	/**
	 * Whether the document is as it was when last ingested, by what its content
	 * handler tells without reading it: a last ingestion known, with the same
	 * modification date and the same size. A date or a size unknown on either side
	 * tells nothing: the document is read.
	 */
	static boolean unchangedSinceLastIngestion(GDocumentReferencePayload payload) {
		final GDocumentReference document = payload.getDocumentReference();
		final boolean unchanged = payload.getLastIngestedHash() != null && document != null
				&& document.getModificationDate() != null && payload.getLastIngestedModificationDate() != null
				&& document.getModificationDate().getTime() == payload.getLastIngestedModificationDate().getTime()
				&& document.getFileSize() != null && payload.getLastIngestedFileSize() != null
				&& document.getFileSize().longValue() == payload.getLastIngestedFileSize().longValue();
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Document:" + (document != null ? document.getCode() : null) + " modificationDate:"
					+ (document != null ? document.getModificationDate() : null) + " fileSize:"
					+ (document != null ? document.getFileSize() : null) + " last ingested hash:"
					+ payload.getLastIngestedHash() + " modificationDate:" + payload.getLastIngestedModificationDate()
					+ " fileSize:" + payload.getLastIngestedFileSize() + " => "
					+ (unchanged ? "unchanged, not read again" : "read"));
		}
		return unchanged;
	}

	/**
	 * Whether the text read is the one last ingested: its hash equal to the last
	 * ingested one, both known.
	 */
	static boolean sameTextAsLastIngested(GDocumentReferencePayload payload, String contentHash) {
		final boolean same = contentHash != null && contentHash.equals(payload.getLastIngestedHash());
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Document:"
					+ (payload.getDocumentReference() != null ? payload.getDocumentReference().getCode() : null)
					+ " text hash:" + contentHash + " last ingested:" + payload.getLastIngestedHash() + " => "
					+ (same ? "same text, discarded" : "changed, sent on"));
		}
		return same;
	}

	@Override
	public void acceptMessages(GMessageEnvelope<GMessagesBatchPayload> messages) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin acceptMessages(...) with msg cardinality of:" + messages.getPayload().size());
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Start contents chunking loop");
		}
		List<GMessageEnvelope> envelopes = messages.getPayload();
		for (GMessageEnvelope message : envelopes) {
			acceptSingleMessage(message);
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("End acceptMessages(...)");
		}
	}
}