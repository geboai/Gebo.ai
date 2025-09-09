package ai.gebo.application.messaging.workflow;

import lombok.AllArgsConstructor;
import lombok.Getter;

public interface IWorkflowStatusHandler {
	@AllArgsConstructor
	@Getter
	public static class ComputedWorkflowStatus {
		private final boolean completed;
		private final boolean hasErrors;
		private final long totalDocuments;
		private final long totalDocumentsWithErrors;
		private final long totalDocumentsSuccessfull;
	}
	public String getId();

	public GWorkflowType getWorkflowType();

	public boolean handleWorkflow(String workFlowId);

	public ComputedWorkflowStatus computeWorkflowStatus(String jobId, String workflowType, String workflowId);
}
