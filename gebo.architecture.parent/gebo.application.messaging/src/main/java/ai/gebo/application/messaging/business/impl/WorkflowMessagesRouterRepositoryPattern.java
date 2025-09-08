package ai.gebo.application.messaging.business.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ai.gebo.application.messaging.business.IWorkflowMessagesRouter;
import ai.gebo.application.messaging.business.IWorkflowMessagesRouterRepositoryPattern;
import ai.gebo.application.messaging.model.GWorkflowType;
import ai.gebo.architecture.patterns.GAbstractImplementationsRepositoryPattern;

@Component
public class WorkflowMessagesRouterRepositoryPattern
		extends GAbstractImplementationsRepositoryPattern<IWorkflowMessagesRouter>
		implements IWorkflowMessagesRouterRepositoryPattern {

	public WorkflowMessagesRouterRepositoryPattern(
			@Autowired(required = false) List<IWorkflowMessagesRouter> implementations) {
		super(implementations);

	}

	@Override
	public String getCodeValue(IWorkflowMessagesRouter x) {

		return x.getId();
	}

	@Override
	public List<IWorkflowMessagesRouter> findByWorkflowsTypeAndId(final GWorkflowType workflowType, String workflowId) {

		return findImplementations(x -> {
			return ((workflowType != null && x.getWorkflowType() == workflowType)
					|| (workflowType == null && x.getWorkflowType() == GWorkflowType.STANDARD))
					&& x.handleWorkflow(workflowId);
		});
	}

}
