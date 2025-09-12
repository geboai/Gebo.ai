package ai.gebo.application.messaging.workflow;

import ai.gebo.application.messaging.workflow.model.ComputedWorkflowResult;
import ai.gebo.application.messaging.workflow.model.ComputedWorkflowStructure;

public interface IWorkflowStatusHandler {

	public String getId();

	public GWorkflowType getWorkflowType();

	public boolean handleWorkflow(String workFlowId);

	public ComputedWorkflowResult computeWorkflowStatus(String jobId, String workflowType, String workflowId);

	public ComputedWorkflowStructure getWorkflowStructure(String workflowType, String workflowId);
}
