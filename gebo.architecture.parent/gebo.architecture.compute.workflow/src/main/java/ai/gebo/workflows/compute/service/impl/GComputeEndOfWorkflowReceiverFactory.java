package ai.gebo.workflows.compute.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ai.gebo.application.messaging.GAbstractMessageReceiverFactory;
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
					// no status: the job was deleted while its contents were in flight; a job
					// that found only deleted documents did its work too
					if (status != null && status.isFinished() && (status.getRootStatus().getBatchDocumentsInput() > 0
							|| status.getRootStatus().getBatchDeletedDocuments() > 0)) {
						// the job is closed here, as soon as it is done: the status daemon finds it
						// closed and does not broadcast its end a second time
						if (runtimeBinder.getImplementationOf(GWorkflowJobCloser.class)
								.closeAndBroadcastEnded(payload.getJobId())) {
							LOGGER.info("Closed the job and broadcast its end for " + payload);
						}
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
