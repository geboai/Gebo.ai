package ai.gebo.application.messaging.workflow;

import java.util.List;

import ai.gebo.application.messaging.IGMessagePayloadType;
import ai.gebo.application.messaging.model.GMessagingComponentRef;

public interface IWorkflowMessagesRouter {
	public String getId();

	public GWorkflowType getWorkflowType();

	public boolean handleWorkflow(String workFlowId);

	public List<GMessagingComponentRef> onProcessedRoutes(String workflowId, String stepId,
			IGMessagePayloadType payload);
}
