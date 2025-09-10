package ai.gebo.application.messaging.workflow.model;

import lombok.Data;

@Data
public class ComputedWorkflowStructure {
	private String workflowType = null;
	private String workflowId = null;
	private String description = null;
	private boolean enabled = false;
	private ComputedWorkflowItem rootStep = null;

}
