package ai.gebo.application.messaging.workflow.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ai.gebo.application.messaging.workflow.GWorkflowType;
import ai.gebo.application.messaging.workflow.IWorkflowStepEnabledHandler;
import ai.gebo.application.messaging.workflow.IWorkflowStepEnabledHandlerRepositoryPattern;
import ai.gebo.architecture.patterns.GAbstractImplementationsRepositoryPattern;

@Component
public class WorkflowStepEnabledHandlerRepositoryPatternImpl
		extends GAbstractImplementationsRepositoryPattern<IWorkflowStepEnabledHandler>
		implements IWorkflowStepEnabledHandlerRepositoryPattern {

	public WorkflowStepEnabledHandlerRepositoryPatternImpl(
			@Autowired(required = false) List<IWorkflowStepEnabledHandler> implementations) {
		super(implementations);

	}

	@Override
	public String getCodeValue(IWorkflowStepEnabledHandler x) {
		return x.getId();
	}

	@Override
	public IWorkflowStepEnabledHandler findByWorkflowsTypeAndWorkflowIdAndWorkflowStepId(GWorkflowType workflowType,
			String workflowId, String workflowStepId) {

		return findImplementation(x -> {
			return ((workflowType != null && x.getWorkflowType() == workflowType)
					|| (workflowType == null && x.getWorkflowType() == GWorkflowType.STANDARD))
					&& x.handleWorkflowStep(workflowId, workflowStepId);
		});
	}

}
