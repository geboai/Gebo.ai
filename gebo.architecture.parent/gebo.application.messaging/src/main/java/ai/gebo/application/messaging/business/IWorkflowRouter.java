package ai.gebo.application.messaging.business;

import ai.gebo.application.messaging.IGMessageEmitter;
import ai.gebo.application.messaging.IGMessagePayloadType;
import ai.gebo.application.messaging.model.GWorkflowType;

public interface IWorkflowRouter {

	public void routeToNextSteps(GWorkflowType workflowType, String currentWorkflowId, String currentWorkflowStepId,
			IGMessagePayloadType payload, IGMessageEmitter emitter);

}
