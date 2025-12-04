package ai.gebo.application.messaging.workflow;

import java.util.List;

import ai.gebo.architecture.patterns.IGImplementationsRepositoryPattern;

public interface IWorkflowStatusHandlerRepositoryPattern
		extends IGImplementationsRepositoryPattern<IWorkflowStatusHandler> {
	public List<IWorkflowStatusHandler> findByWorkflowsTypeAndWorkflowId(GWorkflowType workflowType, String workflowId);
}
