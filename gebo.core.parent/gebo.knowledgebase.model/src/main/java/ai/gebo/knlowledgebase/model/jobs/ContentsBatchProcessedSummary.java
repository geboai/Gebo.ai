package ai.gebo.knlowledgebase.model.jobs;

import java.util.Date;

public interface ContentsBatchProcessedSummary {

	public String getJobId();

	public String getWorkflowType();

	public String getWorkflowId();

	public String getWorkflowStepId();

	public boolean isLastMessage();

	public long getBatchDocumentsInput();

	public long getBatchSentToNextStep();

	public long getChunksProcessed();

	public long getTokensProcessed();

	public long getBatchDocumentsProcessingErrors();

	public long getBatchDocumentsProcessed();

	public long getBatchDiscardedInput();

	public Date getTimestampMin();

	public Date getTimestampMax();
}