package ai.gebo.architecture.rag_threasholds_autotune.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ai.gebo.application.messaging.IGMessageReceiver;
import ai.gebo.application.messaging.IGMessageReceiverFactory;
import ai.gebo.application.messaging.SystemComponentType;
import ai.gebo.application.messaging.model.GMessageEnvelope;
import ai.gebo.architecture.rag_threasholds_autotune.service.IRagThreasholdAutotuneService;
import ai.gebo.core.messages.GFinishedWorkflowPayload;
import lombok.AllArgsConstructor;

@Component
@Scope("singleton")
@AllArgsConstructor
public class RagThreasholdAutotuneFinishedWorkflowReceiver implements IGMessageReceiverFactory {
	private static final String RAG_THREASHOLD_AUTOTUNE_COMPONENT = "rag-threashold-autotune-component";
	private static final String RAG_THREASHOLD_AUTOTUNE_MODULE = "rag-threashold-autotune-module";
	private static final Logger LOGGER = LoggerFactory.getLogger(RagThreasholdAutotuneFinishedWorkflowReceiver.class);
	final RagThreasholdAutotuneServiceImpl autotuneService;

	@Override
	public IGMessageReceiver create() {

		return new IGMessageReceiver() {

			@Override
			public void accept(GMessageEnvelope t) {
				LOGGER.info("Threashold autotune system trigger message received");
				if (!autotuneService.isRunning()) {
					autotuneService.onTick();
				}
			}

			@Override
			public String getMessagingSystemId() {

				return RagThreasholdAutotuneFinishedWorkflowReceiver.this.getMessagingSystemId();
			}

			@Override
			public String getMessagingModuleId() {
				return RagThreasholdAutotuneFinishedWorkflowReceiver.this.getMessagingModuleId();
			}

			@Override
			public SystemComponentType getComponentType() {

				return RagThreasholdAutotuneFinishedWorkflowReceiver.this.getComponentType();
			}

			@Override
			public boolean isAcceptEveryPayloadType() {

				return false;
			}

			@Override
			public List<String> getAcceptedPayloadTypes() {

				return RagThreasholdAutotuneFinishedWorkflowReceiver.this.getAcceptedPayloadTypes();
			}
		};
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
	public String getMessagingModuleId() {
		return RAG_THREASHOLD_AUTOTUNE_MODULE;
	}

	@Override
	public String getMessagingSystemId() {

		return RAG_THREASHOLD_AUTOTUNE_COMPONENT;
	}

	@Override
	public SystemComponentType getComponentType() {
		return SystemComponentType.APPLICATION_COMPONENT;
	}

	@Override
	public int getPoolCardinality() {

		return 1;
	}

	@Override
	public boolean useSenderThread() {
		return false;
	}

}
