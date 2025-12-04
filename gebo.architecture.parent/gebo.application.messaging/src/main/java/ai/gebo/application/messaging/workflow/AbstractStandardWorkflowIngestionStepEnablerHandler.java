package ai.gebo.application.messaging.workflow;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class AbstractStandardWorkflowIngestionStepEnablerHandler implements IWorkflowStepEnabledHandler {
	protected final GStandardWorkflowStep handledStep;
	protected final GStandardWorkflow handledWorkflow = GStandardWorkflow.INGESTION;

	@Override
	public String getId() {

		return this.getClass().getName();
	}

	@Override
	public GWorkflowType getWorkflowType() {

		return GWorkflowType.STANDARD;
	}

	@Override
	public boolean handleWorkflowStep(String workflowId, String workflowStepId) {

		return handledWorkflow.name().equalsIgnoreCase(workflowId)
				&& handledStep.name().equalsIgnoreCase(workflowStepId);
	}

}
