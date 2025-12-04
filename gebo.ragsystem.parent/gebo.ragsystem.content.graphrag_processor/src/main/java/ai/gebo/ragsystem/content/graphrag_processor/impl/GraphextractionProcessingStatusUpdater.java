package ai.gebo.ragsystem.content.graphrag_processor.impl;

import java.util.function.Consumer;

import ai.gebo.application.messaging.IGMessageBroker;
import ai.gebo.application.messaging.SystemComponentType;
import ai.gebo.application.messaging.model.GMessageEnvelope;
import ai.gebo.application.messaging.model.GStandardModulesConstraints;
import ai.gebo.architecture.graphrag.persistence.model.KnowledgeExtractionEvent;
import ai.gebo.core.messages.GContentsProcessingStatusUpdatePayload;
import ai.gebo.ragsystem.content.graphrag_processor.IGraphRagProcessorMessagesReceiverFactoryComponent;

class GraphextractionProcessingStatusUpdater implements Consumer<KnowledgeExtractionEvent> {

	private String jobId;
	private String workflowType;
	private String workflowId;
	private String workflowStepId;
	private long howManyAccepted = 0;
	private static final long BATCHING_CYCLES_NUMBER = 5;
	private KnowledgeExtractionEvent cumulated = null;
	private boolean errorsOccurred = false;
	private final IGMessageBroker broker;
	private final IGraphRagProcessorMessagesReceiverFactoryComponent emitter;

	GraphextractionProcessingStatusUpdater(String jobId, String workflowType, String workflowId,
			String workflowStepId, IGMessageBroker broker,
			IGraphRagProcessorMessagesReceiverFactoryComponent emitter) {
		this.jobId = jobId;
		this.workflowType = workflowType;
		this.workflowId = workflowId;
		this.workflowStepId = workflowStepId;
		this.broker = broker;
		this.emitter = emitter;
	}

	@Override
	public void accept(KnowledgeExtractionEvent t) {
		howManyAccepted++;
		if (cumulated == null)
			cumulated = t;
		else {
			cumulated.incrementBy(t);
		}
		if (!errorsOccurred) {
			errorsOccurred = (cumulated.getErrorChunks() > 0l || cumulated.getErrorTokens() > 0l);
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
			payload.setErrorChunks(cumulated.getErrorChunks());
			payload.setErrorTokens(cumulated.getErrorTokens());

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

	public boolean isErrorsOccurred() {
		return errorsOccurred;
	}
}