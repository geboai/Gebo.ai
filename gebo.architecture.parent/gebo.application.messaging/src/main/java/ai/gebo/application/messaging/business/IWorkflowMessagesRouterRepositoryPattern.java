package ai.gebo.application.messaging.business;

import java.util.List;

import ai.gebo.application.messaging.model.GWorkflowType;
import ai.gebo.architecture.patterns.IGImplementationsRepositoryPattern;

public interface IWorkflowMessagesRouterRepositoryPattern
		extends IGImplementationsRepositoryPattern<IWorkflowMessagesRouter> {
	public List<IWorkflowMessagesRouter> findByWorkflowsTypeAndId(GWorkflowType workflowType, String workflowId);
}
