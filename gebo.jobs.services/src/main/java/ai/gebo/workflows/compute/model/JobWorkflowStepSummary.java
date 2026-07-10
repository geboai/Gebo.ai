package ai.gebo.workflows.compute.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class JobWorkflowStepSummary {
	private String workflowType = null;
	private String workflowId = null;
	private String workflowStepId = null;
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
	private List<JobWorkflowStepSummaryTimeSlotStats> timesamples = new ArrayList<JobWorkflowStepSummaryTimeSlotStats>();

	private void incrementBy(JobWorkflowStepSummaryTimeSlotStats x) {
		this.batchDocumentsInput += x.getBatchDocumentsInput();
		this.batchDocumentsProcessed += x.getBatchDocumentsProcessed();
		this.batchDocumentsProcessingErrors += x.getBatchDocumentsProcessingErrors();
		this.batchSentToNextStep += x.getBatchSentToNextStep();
		this.batchDiscardedInput += x.getBatchDiscardedInput();
		this.chunksProcessed += x.getChunksProcessed();
		this.tokensProcessed += x.getTokensProcessed();
		this.errorChunks += x.getErrorChunks();
		this.errorTokens += x.getErrorTokens();

	}

	public void add(JobWorkflowStepSummaryTimeSlotStats slot) {
		this.incrementBy(slot);
		timesamples.add(slot);

	}
}
