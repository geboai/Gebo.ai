package ai.gebo.llms.agent.chat.service;

import java.util.List;

import ai.gebo.architecture.agents.model.IGPartialOperation;
import ai.gebo.architecture.agents.services.IGReactiveAgentService;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatMessageEnvelope;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatResponse;
import ai.gebo.llms.chat.pipelines.model.ChatPipelineExecutionRuntimeData;
import reactor.core.publisher.FluxSink;

public class ChatAgentServiceNetworkAdapter extends
		ai.gebo.architecture.agents.services.AbstractReactiveAgentServiceNetworkAdapter<ChatPipelineExecutionRuntimeData, GeboChatMessageEnvelope, GeboChatMessageEnvelope> {

	public ChatAgentServiceNetworkAdapter(
			IGReactiveAgentService<ChatPipelineExecutionRuntimeData, GeboChatMessageEnvelope, GeboChatMessageEnvelope> service,
			Class<ChatPipelineExecutionRuntimeData> inputType, Class<GeboChatMessageEnvelope> outputType,
			FluxSink<IGPartialOperation<GeboChatMessageEnvelope>> sink) {
		super(service, inputType, outputType, sink);

	}

	@Override
	protected GeboChatMessageEnvelope extractResponse(List<IGPartialOperation<GeboChatMessageEnvelope>> buffered) {
		GeboChatMessageEnvelope lastValidResponse = null;
		for (IGPartialOperation<GeboChatMessageEnvelope> entry : buffered) {
			if (entry.getData() != null && entry.getData().getContent() instanceof GeboChatResponse) {
				lastValidResponse = entry.getData();
			}
		}
		return lastValidResponse;
	}

}
