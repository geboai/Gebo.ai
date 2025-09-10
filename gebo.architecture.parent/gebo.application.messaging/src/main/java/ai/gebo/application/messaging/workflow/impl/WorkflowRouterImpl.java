package ai.gebo.application.messaging.workflow.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import ai.gebo.application.messaging.IGMessageBroker;
import ai.gebo.application.messaging.IGMessageEmitter;
import ai.gebo.application.messaging.IGMessagePayloadType;
import ai.gebo.application.messaging.model.GMessageEnvelope;
import ai.gebo.application.messaging.model.GMessagingComponentRef;
import ai.gebo.application.messaging.workflow.GWorkflowType;
import ai.gebo.application.messaging.workflow.IWorkflowMessagesRouter;
import ai.gebo.application.messaging.workflow.IWorkflowMessagesRouterRepositoryPattern;
import ai.gebo.application.messaging.workflow.IWorkflowRouter;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class WorkflowRouterImpl implements IWorkflowRouter {
	private final IGMessageBroker broker;
	private final IWorkflowMessagesRouterRepositoryPattern workflowMessagesRouterRepositoryPattern;
	private final static Logger LOGGER = LoggerFactory.getLogger(WorkflowRouterImpl.class);

	@Override
	public void routeToNextSteps(GWorkflowType workflowType, String currentWorkflowId, String currentWorkflowStepId,
			IGMessagePayloadType payload, IGMessageEmitter emitter) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin workflowRoute(....)");
		}
		List<IWorkflowMessagesRouter> routers = this.workflowMessagesRouterRepositoryPattern
				.findByWorkflowsTypeAndId(workflowType, currentWorkflowId);
		List<GMessagingComponentRef> destinations = new ArrayList<GMessagingComponentRef>();
		for (IWorkflowMessagesRouter router : routers) {
			List<GMessagingComponentRef> _destinations = router.onProcessedRoutes(currentWorkflowId,
					currentWorkflowStepId, payload);
			if (_destinations != null)
				destinations.addAll(_destinations);
		}
		for (GMessagingComponentRef target : destinations) {
			try {
				GMessageEnvelope<IGMessagePayloadType> envelope = GMessageEnvelope.newMessageFrom(emitter, payload);
				envelope.setWorkflowType(workflowType);
				envelope.setWorkflowId(target.getWorkflowId());
				envelope.setWorkflowStepId(target.getWorkflowStepId());
				envelope.setTargetModule(target.getModuleId());
				envelope.setTargetComponent(target.getComponentId());
				broker.accept(envelope);
			} catch (Throwable th) {
				LOGGER.error("Error routing message", th);
			}
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("End workflowRoute(....)");
		}
	}

}
