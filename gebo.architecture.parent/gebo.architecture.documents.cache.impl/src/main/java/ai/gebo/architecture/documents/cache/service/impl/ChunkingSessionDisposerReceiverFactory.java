package ai.gebo.architecture.documents.cache.service.impl;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ai.gebo.application.messaging.GAbstractMessageReceiverFactory;
import ai.gebo.application.messaging.IGMessageReceiver;
import ai.gebo.application.messaging.SystemComponentType;
import ai.gebo.application.messaging.model.GMessageEnvelope;
import ai.gebo.application.messaging.model.GStandardModulesConstraints;
import ai.gebo.architecture.documents.cache.service.IDocumentsChunkService;
import ai.gebo.architecture.patterns.IGRuntimeBinder;
import ai.gebo.core.messages.GFinishedWorkflowPayload;
import lombok.AllArgsConstructor;

@Component
@Scope("singleton")

public class ChunkingSessionDisposerReceiverFactory extends GAbstractMessageReceiverFactory {
	public static final String DISPOSE_CHUNKING_SESSION_FOR_JOBS = "dispose-chunking-session-for-jobs";
	static final MessageReceiverFactoryConfig factoryConfig = new MessageReceiverFactoryConfig();
	static {
		factoryConfig.setPoolCardinality(1);
		factoryConfig.setUseSenderThread(false);

	}
	final IGRuntimeBinder runtimeBinder;

	@AllArgsConstructor
	class DisposeChunkingSessionForJobReceiver extends GNestedMessageReceiver {
		final IDocumentsChunkService chunkingService;

		@Override
		public void accept(GMessageEnvelope msg) {
			if (msg.getPayload() instanceof GFinishedWorkflowPayload finishedWorkFlowPayload) {
				chunkingService.disposeChunkingSession("job:" + finishedWorkFlowPayload.getJobId());
			}
		}

	}

	public ChunkingSessionDisposerReceiverFactory(IGRuntimeBinder runtimeBinder) {
		super(factoryConfig);
		this.runtimeBinder = runtimeBinder;

	}

	@Override
	public List<String> getAcceptedPayloadTypes() {

		return List.of(GFinishedWorkflowPayload.class.getName());
	}

	@Override
	public boolean isAcceptEveryPayloadType() {

		return false;
	}

	@Override
	public IGMessageReceiver create() {

		return new DisposeChunkingSessionForJobReceiver(
				runtimeBinder.getImplementationOf(IDocumentsChunkService.class));
	}

	@Override
	public String getMessagingModuleId() {

		return GStandardModulesConstraints.TOKENIZER_MODULE;
	}

	@Override
	public String getMessagingSystemId() {

		return DISPOSE_CHUNKING_SESSION_FOR_JOBS;
	}

	@Override
	public SystemComponentType getComponentType() {
		return SystemComponentType.APPLICATION_COMPONENT;
	}

}
