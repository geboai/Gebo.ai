package ai.gebo.application.messaging.workflow;

import java.util.List;

import ai.gebo.architecture.patterns.IGImplementationsRepositoryPattern;

public interface IWorkflowStatusHandlerRepositoryPattern
		extends IGImplementationsRepositoryPattern<IWorkflowStatusHandler> {
	public List<IWorkflowStatusHandler> findByWorkflowsTypeAndWorkflowId(GWorkflowType workflowType, String workflowId);

	public default List<IWorkflowStatusHandler> findByWorkflowsTypeAndWorkflowId(String workflowType,
			String workflowId) {
		GWorkflowType wt = GWorkflowType.STANDARD;
		try {
			wt = GWorkflowType.valueOf(workflowType);
		} catch (Throwable t) {
		}
		return findByWorkflowsTypeAndWorkflowId(wt, workflowId);
	}
}
