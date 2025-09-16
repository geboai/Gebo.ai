package ai.gebo.application.messaging.workflow;

import java.util.ArrayList;
import java.util.List;

import ai.gebo.application.messaging.workflow.model.ComputedWorkflowItem;
import ai.gebo.application.messaging.workflow.model.ComputedWorkflowResult;
import ai.gebo.application.messaging.workflow.model.ComputedWorkflowStatus;
import ai.gebo.application.messaging.workflow.model.ComputedWorkflowStructure;

public interface IWorkflowStatusHandler {

	public String getId();

	public GWorkflowType getWorkflowType();

	public boolean handleWorkflow(String workFlowId);

	public ComputedWorkflowResult computeWorkflowStatus(String jobId, String workflowType, String workflowId);

	public ComputedWorkflowStructure getWorkflowStructure(String workflowType, String workflowId);

	public default List<ComputedWorkflowItem> items(ComputedWorkflowItem rootStep) {
		List<ComputedWorkflowItem> data = new ArrayList<ComputedWorkflowItem>();
		if (rootStep != null) {
			data.add(rootStep);
			rootStep.getChilds().forEach(child -> {
				data.addAll(items(child));
			});
		}
		return data.stream().filter(x -> x.isEnabledStep()).toList();
	}

	public default List<ComputedWorkflowStatus> items(ComputedWorkflowStatus rootStep) {
		List<ComputedWorkflowStatus> data = new ArrayList<ComputedWorkflowStatus>();
		if (rootStep != null) {
			data.add(rootStep);
			rootStep.getChilds().forEach(child -> {
				data.addAll(items(child));
			});
		}
		return data.stream().filter(x -> x.isEnabledStep()).toList();
	}
}
