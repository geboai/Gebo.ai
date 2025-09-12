package ai.gebo.application.messaging.workflow.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import ai.gebo.application.messaging.IGMessagePayloadType;
import ai.gebo.application.messaging.model.GMessagingComponentRef;
import ai.gebo.application.messaging.workflow.GStandardWorkflow;
import ai.gebo.application.messaging.workflow.GStandardWorkflowStep;
import ai.gebo.application.messaging.workflow.GWorkflowType;
import ai.gebo.application.messaging.workflow.IWorkflowMessagesRouter;
import ai.gebo.architecture.patterns.IGRuntimeBinder;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class StandardWorkflowMessagesRouterImpl implements IWorkflowMessagesRouter {
	private final IGRuntimeBinder binder;
	

	@Override
	public String getId() {

		return "STANDARD-WORKFLOWS-ROUTER";
	}

	@Override
	public GWorkflowType getWorkflowType() {

		return GWorkflowType.STANDARD;
	}

	@Override
	public boolean handleWorkflow(String workFlowId) {
		try {
			return GStandardWorkflow.valueOf(workFlowId) != null;
		} catch (Throwable th) {
			return false;
		}
	} 

	@Override
	public List<GMessagingComponentRef> onProcessedRoutes(String workflowId, String stepId,
			IGMessagePayloadType payload) {
		List<GMessagingComponentRef> outValues = List.of();
		GStandardWorkflowStep[] workflowSteps = GStandardWorkflowStep.values();
		for (GStandardWorkflowStep gStandardWorkflowStep : workflowSteps) {
			if (gStandardWorkflowStep.getWorkflow().name().equalsIgnoreCase(workflowId)
					&& gStandardWorkflowStep.name().equalsIgnoreCase(stepId)) {
				outValues = gStandardWorkflowStep.getOnProcessedForwardComponents().apply(payload, binder);
			}
		}
		return outValues;
	}

}
