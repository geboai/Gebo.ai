package ai.gebo.jobs.services.model;

import java.util.Date;

import ai.gebo.knlowledgebase.model.jobs.ContentsBatchProcessed;
import lombok.Data;

@Data
public class JobWorkflowStepSummaryTimeSlotStats {
	private Date startDateTime = null, endDateTime = null;
	private long batchDocumentsInput = 0;
	private long batchDiscardedInput = 0;
	private long batchSentToNextStep = 0;
	private long chunksProcessed = 0l;
	private long tokensProcessed = 0l;
	private long batchDocumentsProcessingErrors = 0;
	private long batchDocumentsProcessed = 0;
	private long errorChunks = 0;
	private long errorTokens = 0;

	public void incrementBy(ContentsBatchProcessed x) {
		this.batchDocumentsInput += x.getBatchDocumentsInput();
		this.batchDocumentsProcessed += x.getBatchDocumentsProcessed();
		this.batchDocumentsProcessingErrors += x.getBatchDocumentsProcessingErrors();
		this.batchSentToNextStep += x.getBatchSentToNextStep();
		this.batchDiscardedInput += x.getBatchDiscardedInput();
		this.chunksProcessed += x.getChunksProcessed();
		this.tokensProcessed += x.getChunksProcessed();
		this.errorChunks += x.getErrorChunks();
		this.errorTokens += x.getErrorTokens();

	}
}
