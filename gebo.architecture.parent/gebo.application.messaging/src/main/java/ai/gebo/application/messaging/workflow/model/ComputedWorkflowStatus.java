package ai.gebo.application.messaging.workflow.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class ComputedWorkflowStatus {
	private String jobId = null;
	private String workflowType = null;
	private String workflowId = null;
	private String workflowStepId = null;
	private String description = null;
	private long batchDocumentsInput;
	private long batchDocumentsProcessingErrors;
	private long batchDocumentsProcessed;
	private long batchSentToNextStep;
	private long chunksProcessed = 0l;
	private long tokensProcessed = 0l;
	private boolean completed;
	private boolean hasErrors;
	private boolean startedRunning;
	private int levelId = 0;
	private boolean enabledStep;
	private List<ComputedWorkflowStatus> childs = new ArrayList<ComputedWorkflowStatus>();
}
