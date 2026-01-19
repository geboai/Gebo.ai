package ai.gebo.ragsystem.content.fulltext.processor.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;

import ai.gebo.application.messaging.IGBatchMessagesReceiver;
import ai.gebo.application.messaging.IGMessagePayloadType;
import ai.gebo.application.messaging.SystemComponentType;
import ai.gebo.application.messaging.model.GMessageEnvelope;
import ai.gebo.application.messaging.model.GMessagesBatchPayload;
import ai.gebo.application.messaging.model.GStandardModulesConstraints;
import ai.gebo.application.messaging.workflow.GStandardWorkflow;
import ai.gebo.application.messaging.workflow.GStandardWorkflowStep;
import ai.gebo.application.messaging.workflow.GWorkflowType;
import ai.gebo.architecture.documents.cache.model.DocumentChunk;
import ai.gebo.architecture.documents.cache.model.DocumentChunkingResponse;
import ai.gebo.architecture.documents.cache.service.IDocumentsChunkService;
import ai.gebo.architecture.fulltext.model.FullTextChunk;
import ai.gebo.architecture.fulltext.model.FullTextDocument;
import ai.gebo.architecture.fulltext.service.IGFullTextIngestionService;
import ai.gebo.architecture.patterns.IGRuntimeBinder;
import ai.gebo.core.messages.GContentsProcessingStatusUpdatePayload;
import ai.gebo.core.messages.GDocumentReferencePayload;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FullTextIndexingBatchMessageReceiver implements IGBatchMessagesReceiver {
	protected final IGRuntimeBinder runtimeBinder;
	protected static final Logger LOGGER = LoggerFactory.getLogger(FullTextIndexingBatchMessageReceiver.class);

	@Override
	public void acceptMessages(GMessageEnvelope<GMessagesBatchPayload> messages) {
		GContentFullTextEmitterComponent emitter = runtimeBinder
				.getImplementationOf(GContentFullTextEmitterComponent.class);
		IDocumentsChunkService chunkingService = runtimeBinder.getImplementationOf(IDocumentsChunkService.class);
		IGFullTextIngestionService ingestionService = runtimeBinder
				.getImplementationOf(IGFullTextIngestionService.class);

		GMessagesBatchPayload payloads = messages.getPayload();

		for (int i = 0; i < payloads.size(); i++) {

			IGMessagePayloadType payload = (IGMessagePayloadType) payloads.get(i);
			if (payload instanceof GDocumentReferencePayload p) {
				GContentsProcessingStatusUpdatePayload status = new GContentsProcessingStatusUpdatePayload();
				status.setBatchDocumentsInput(1l);
				status.setJobId(p.getJobId());
				status.setWorkflowType(GWorkflowType.STANDARD.name());
				status.setWorkflowId(GStandardWorkflow.INGESTION.name());
				status.setWorkflowStepId(GStandardWorkflowStep.FULLTEXT_INDEXING.name());
				GDocumentReference docref = p.getDocumentReference();
				long tokensTotal = 0;
				try {
					String chunkingSession = chunkingService.retrieveChunkingSession("job:" + p.getJobId());
					DocumentChunkingResponse chunkResponse = chunkingService.getCachedChunkSet(docref, chunkingSession);
					FullTextDocument ftDoc = new FullTextDocument();
					ftDoc.setCode(docref.getCode());
					ingestionService.deleteDocuments(List.of(ftDoc));
					while (chunkResponse != null && !chunkResponse.isEmpty()) {
						List<DocumentChunk> fragmentsSet = chunkResponse.getCurrentChunkSet().getChunks();
						for (DocumentChunk f : fragmentsSet) {
							tokensTotal += f.getTokensSize();
						}
						List<FullTextChunk> fragments = translateFragments(fragmentsSet, docref);

						ingestionService.upsert(fragments);
						if (chunkResponse.getNextChunkSetId() != null) {
							chunkResponse = chunkingService.getNextChunkSet(docref, chunkResponse.getId(),
									chunkResponse.getNextChunkSetId(), chunkingSession);
						} else
							chunkResponse = null;
					}
				} catch (Throwable th) {
					LOGGER.error("Exception in acceptMessages(..)", th);
					status.setBatchDocumentsProcessingErrors(1);
				} finally {
					status.setTokensProcessed(tokensTotal);
					status.setBatchDocumentsProcessed(1);
					GMessageEnvelope<GContentsProcessingStatusUpdatePayload> envelope = GMessageEnvelope
							.newMessageFrom(emitter, status);
					envelope.setTargetModule(GStandardModulesConstraints.CORE_MODULE);
					envelope.setTargetComponent(GStandardModulesConstraints.USER_MESSAGES_CONCENTRATOR_COMPONENT);
					envelope.setTargetType(SystemComponentType.APPLICATION_COMPONENT);
					try {
						emitter.send(envelope);
					} catch (Throwable th) {
						LOGGER.error("Exception sending processing ack", th);
					}
				}
			}
		}

	}

	private List<FullTextChunk> translateFragments(List<DocumentChunk> fragmentsSet, GDocumentReference docref) {
		// TODO Auto-generated method stub
		return null;
	}

}
