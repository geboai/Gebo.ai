package ai.gebo.application.messaging.workflow;

import ai.gebo.architecture.patterns.IGImplementationsRepositoryPattern;

public interface IWorkflowStepEnabledHandlerRepositoryPattern
		extends IGImplementationsRepositoryPattern<IWorkflowStepEnabledHandler> {
	public IWorkflowStepEnabledHandler findByWorkflowsTypeAndWorkflowIdAndWorkflowStepId(final GWorkflowType workflowType, String workflowId, String workflowStepId);
}
