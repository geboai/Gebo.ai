package ai.gebo.application.messaging.workflow.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.gebo.application.messaging.workflow.GWorkflowType;
import ai.gebo.application.messaging.workflow.IWorkflowMessagesRouter;
import ai.gebo.application.messaging.workflow.IWorkflowStatusHandler;
import ai.gebo.application.messaging.workflow.IWorkflowStatusHandlerRepositoryPattern;
import ai.gebo.architecture.patterns.GAbstractImplementationsRepositoryPattern;

@Service
public class WorkflowStatusHandlerRepositoryPatternImpl
		extends GAbstractImplementationsRepositoryPattern<IWorkflowStatusHandler>
		implements IWorkflowStatusHandlerRepositoryPattern {

	public WorkflowStatusHandlerRepositoryPatternImpl(
			@Autowired(required = false) List<IWorkflowStatusHandler> implementations) {
		super(implementations);

	}

	@Override
	public String getCodeValue(IWorkflowStatusHandler x) {

		return x.getId();
	}

	@Override
	public List<IWorkflowStatusHandler> findByWorkflowsTypeAndId(final GWorkflowType workflowType, String workflowId) {

		return findImplementations(x -> {
			return ((workflowType != null && x.getWorkflowType() == workflowType)
					|| (workflowType == null && x.getWorkflowType() == GWorkflowType.STANDARD))
					&& x.handleWorkflow(workflowId);
		});
	}
}
