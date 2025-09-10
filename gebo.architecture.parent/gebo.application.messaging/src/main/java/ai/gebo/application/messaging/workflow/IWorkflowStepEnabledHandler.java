package ai.gebo.application.messaging.workflow;

public interface IWorkflowStepEnabledHandler {
	public String getId();

	public GWorkflowType getWorkflowType();

	public boolean handleWorkflowStep(String workflowId, String workflowStepId);

	public boolean isEnabled(String workflowId, String workflowStepId);
}
