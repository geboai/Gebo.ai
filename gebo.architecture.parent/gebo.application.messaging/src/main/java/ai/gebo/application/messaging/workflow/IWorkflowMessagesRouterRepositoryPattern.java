package ai.gebo.application.messaging.workflow;

import java.util.List;

import ai.gebo.architecture.patterns.IGImplementationsRepositoryPattern;

public interface IWorkflowMessagesRouterRepositoryPattern
		extends IGImplementationsRepositoryPattern<IWorkflowMessagesRouter> {
	public List<IWorkflowMessagesRouter> findByWorkflowsTypeAndId(GWorkflowType workflowType, String workflowId);
}
