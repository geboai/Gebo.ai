package ai.gebo.workflows.compute.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ai.gebo.application.messaging.GAbstractMessageReceiverFactory;
import ai.gebo.application.messaging.IGMessageBroker;
import ai.gebo.application.messaging.IGMessageEmitter;
import ai.gebo.application.messaging.IGMessageReceiver;
import ai.gebo.application.messaging.IMessageEnvelopeFactory;
import ai.gebo.application.messaging.SystemComponentType;
import ai.gebo.application.messaging.model.GMessageEnvelope;
import ai.gebo.application.messaging.model.GStandardModulesConstraints;
import ai.gebo.application.messaging.workflow.IWorkflowStatusHandler;
import ai.gebo.application.messaging.workflow.IWorkflowStatusHandlerRepositoryPattern;
import ai.gebo.application.messaging.workflow.model.ComputedWorkflowResult;
import ai.gebo.architecture.patterns.IGRuntimeBinder;
import ai.gebo.core.messages.ComputeWorkflowEndPayload;
import ai.gebo.core.messages.GFinishedWorkflowPayload;

@Component
@Scope("singleton")

public class GComputeEndOfWorkflowReceiverFactory extends GAbstractMessageReceiverFactory {
	private final IGRuntimeBinder runtimeBinder;
	private final IMessageEnvelopeFactory envelopeFactory;
	public static final MessageReceiverFactoryConfig factoryConfig = new MessageReceiverFactoryConfig();
	private static final Logger LOGGER = LoggerFactory.getLogger(GComputeEndOfWorkflowReceiverFactory.class);

	static {
		factoryConfig.setUseSenderThread(false);
		factoryConfig.setPoolCardinality(1);
	}

	class ComputeEndOfWorkNestedReceiver extends GNestedMessageReceiver {

		@Override
		public void accept(GMessageEnvelope t) {
			if (t.getPayload() instanceof ComputeWorkflowEndPayload payload) {
				IWorkflowStatusHandlerRepositoryPattern workflowStatusHandler = runtimeBinder
						.getImplementationOf(IWorkflowStatusHandlerRepositoryPattern.class);
				List<IWorkflowStatusHandler> handlers = workflowStatusHandler
						.findByWorkflowsTypeAndWorkflowId(payload.getWorkflowType(), payload.getWorkflowId());
				if (!handlers.isEmpty()) {
					ComputedWorkflowResult status = handlers.get(0).computeWorkflowStatus(payload.getJobId(),
							payload.getWorkflowType(), payload.getWorkflowId());
					if (status.isFinished() && status.getRootStatus().getBatchDocumentsInput() > 0) {
						LOGGER.info("Sending the finished work broadcast message for" + payload);
						GFinishedWorkflowPayload finishedWorkflow = new GFinishedWorkflowPayload();
						finishedWorkflow.setJobId(payload.getJobId());
						finishedWorkflow.setWorkflowType(payload.getWorkflowType());
						finishedWorkflow.setWorkflowId(payload.getWorkflowId());
						IGMessageEmitter emitter = runtimeBinder.getImplementationOf(GWorkflowsConcentratorMessagesEmitterImpl.class);
						IGMessageBroker broker = runtimeBinder.getImplementationOf(IGMessageBroker.class);
						GMessageEnvelope<GFinishedWorkflowPayload> envelope = envelopeFactory.newMessageFrom(emitter,
								finishedWorkflow);
						broker.broadcast(envelope);
					}
				}
			}
		}

	}

	public GComputeEndOfWorkflowReceiverFactory(IGRuntimeBinder runtimeBinder, IMessageEnvelopeFactory envelopeFactory) {
		super(factoryConfig);
		this.runtimeBinder = runtimeBinder;
		this.envelopeFactory = envelopeFactory;
	}

	@Override
	public List<String> getAcceptedPayloadTypes() {

		return List.of(ComputeWorkflowEndPayload.class.getName());
	}

	@Override
	public boolean isAcceptEveryPayloadType() {

		return false;
	}

	@Override
	public IGMessageReceiver create() {

		return new ComputeEndOfWorkNestedReceiver();
	}

	@Override
	public String getMessagingModuleId() {

		return GStandardModulesConstraints.JOBS_MASTER;
	}

	@Override
	public String getMessagingSystemId() {
		return GStandardModulesConstraints.END_OF_WORKFLOW_COMPUTE_SERVICE;
	}

	@Override
	public SystemComponentType getComponentType() {
		return SystemComponentType.APPLICATION_COMPONENT;
	}

}
