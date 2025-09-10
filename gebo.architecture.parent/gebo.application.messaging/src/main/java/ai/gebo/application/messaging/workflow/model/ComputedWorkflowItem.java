package ai.gebo.application.messaging.workflow.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class ComputedWorkflowItem {
	private String workflowType = null;
	private String workflowId = null;
	private String workflowStepId = null;
	private String description = null;
	private int levelId = 0;
	private boolean enabledStep;
	private List<ComputedWorkflowItem> childs = new ArrayList<ComputedWorkflowItem>();

}
