package ai.gebo.jobs.services.impl;

import java.util.List;

import ai.gebo.application.messaging.IGMessageBroker;
import ai.gebo.application.messaging.IGMessageEmitter;
import ai.gebo.application.messaging.IMessageEnvelopeFactory;
import ai.gebo.application.messaging.SystemComponentType;
import ai.gebo.application.messaging.model.GMessageEnvelope;
import ai.gebo.application.messaging.model.GStandardModulesConstraints;
import ai.gebo.architecture.patterns.IGRuntimeBinder;
import ai.gebo.core.messages.GBaseWorkflowStatusPayload;
import ai.gebo.core.messages.GFinishedWorkflowPayload;
import ai.gebo.core.messages.GStartedWorkflowPayload;
import ai.gebo.knlowledgebase.model.jobs.GJobStatus;

/**
 * Base implementation broadcasting job start/finish notifications.
 *
 * <p>
 * {@code messagingSystemId} is fixed
 * ({@link GStandardModulesConstraints#JOB_STATUS_NOTIFIER}) while
 * {@code messagingModuleId} is assignable per concrete subclass - see
 * {@link AbstractJobLaunchManager} for the full rationale, which applies here
 * identically.
 * </p>
 */
public abstract class AbstractJobStatusEmitter implements IGMessageEmitter {
	private final IGRuntimeBinder runtimeBinder;
	private final IMessageEnvelopeFactory envelopeFactory;
	protected final String messagingModuleId;

	protected AbstractJobStatusEmitter(IGRuntimeBinder runtimeBinder, IMessageEnvelopeFactory envelopeFactory,
			String messagingModuleId) {
		this.runtimeBinder = runtimeBinder;
		this.envelopeFactory = envelopeFactory;
		this.messagingModuleId = messagingModuleId;
	}

	@Override
	public String getMessagingModuleId() {

		return messagingModuleId;
	}

	@Override
	public String getMessagingSystemId() {
		return GStandardModulesConstraints.JOB_STATUS_NOTIFIER;
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
		GMessageEnvelope<GBaseWorkflowStatusPayload> message = envelopeFactory.newMessageFrom(this, payload);
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
