package ai.gebo.application.messaging.workflow;

import ai.gebo.application.messaging.IGMessageEmitter;
import ai.gebo.application.messaging.IGMessagePayloadType;

public interface IWorkflowRouter {

	public void routeToNextSteps(GWorkflowType workflowType, String currentWorkflowId, String currentWorkflowStepId,
			IGMessagePayloadType payload, IGMessageEmitter emitter);

}
