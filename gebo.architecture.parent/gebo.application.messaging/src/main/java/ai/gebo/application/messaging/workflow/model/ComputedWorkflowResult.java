package ai.gebo.application.messaging.workflow.model;

import lombok.Data;

@Data
public class ComputedWorkflowResult {
	private String workflowType = null;
	private String workflowId = null;
	private String description = null;
	private boolean finished = false;
	private boolean hasErrors = false;
	private ComputedWorkflowStatus rootStatus = null;

}
