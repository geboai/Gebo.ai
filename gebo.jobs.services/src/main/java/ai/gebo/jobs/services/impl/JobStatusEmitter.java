package ai.gebo.jobs.services.impl;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ai.gebo.application.messaging.IGMessageBroker;
import ai.gebo.application.messaging.IGMessageEmitter;
import ai.gebo.application.messaging.SystemComponentType;
import ai.gebo.application.messaging.model.GMessageEnvelope;
import ai.gebo.application.messaging.model.GStandardModulesConstraints;
import ai.gebo.architecture.patterns.IGRuntimeBinder;
import ai.gebo.core.messages.GBaseWorkflowStatusPayload;
import ai.gebo.core.messages.GFinishedWorkflowPayload;
import ai.gebo.core.messages.GStartedWorkflowPayload;
import ai.gebo.knlowledgebase.model.jobs.GJobStatus;
import lombok.AllArgsConstructor;

@Component
@Scope("singleton")
@AllArgsConstructor
public class JobStatusEmitter implements IGMessageEmitter {
	private final IGRuntimeBinder runtimeBinder;

	@Override
	public String getMessagingModuleId() {

		return GStandardModulesConstraints.ASYNC_PUBLISHING_JOB_MODULE;
	}

	@Override
	public String getMessagingSystemId() {
		return "job-status-notifier";
	}

	@Override
	public SystemComponentType getComponentType() {
		return SystemComponentType.APPLICATION_COMPONENT;
	}

	@Override
	public List<String> getEmittedPayloadTypes() {
		return List.of(GStartedWorkflowPayload.class.getName(), GFinishedWorkflowPayload.class.getName());
	}

	private void _broadcast(GBaseWorkflowStatusPayload payload) {
		GMessageEnvelope<GBaseWorkflowStatusPayload> message = GMessageEnvelope.newMessageFrom(this, payload);
		IGMessageBroker broker = runtimeBinder.getImplementationOf(IGMessageBroker.class);
		broker.broadcast(message);
	}

	void broadcastStarted(GJobStatus status) {
		GStartedWorkflowPayload startedPayload = new GStartedWorkflowPayload();
		startedPayload.setJobId(status.getCode());
		startedPayload.setWorkflowId(status.getWorkflowId());
		startedPayload.setWorkflowType(status.getWorkflowType());
		broadcast(startedPayload);
	}

	void broadcastEnded(GJobStatus status) {
		GFinishedWorkflowPayload finishedPayload = new GFinishedWorkflowPayload();
		finishedPayload.setJobId(status.getCode());
		finishedPayload.setWorkflowId(status.getWorkflowId());
		finishedPayload.setWorkflowType(status.getWorkflowType());
		broadcast(finishedPayload);
	}

	void broadcast(GFinishedWorkflowPayload payload) {
		this._broadcast(payload);
	}

	void broadcast(GStartedWorkflowPayload payload) {
		this._broadcast(payload);
	}

}
