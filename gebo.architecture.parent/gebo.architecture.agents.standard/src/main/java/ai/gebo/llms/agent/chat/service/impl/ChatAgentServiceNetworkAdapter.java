package ai.gebo.llms.agent.chat.service.impl;

import java.util.List;

import ai.gebo.architecture.agents.model.IGPartialOperation;
import ai.gebo.architecture.agents.services.IGReactiveAgentService;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatMessageEnvelope;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatResponse;
import ai.gebo.llms.chat.pipelines.model.ChatPipelineExecutionRuntimeData;
import reactor.core.publisher.Sinks;

public class ChatAgentServiceNetworkAdapter extends
		ai.gebo.architecture.agents.services.AbstractReactiveAgentServiceNetworkAdapter<ChatPipelineExecutionRuntimeData, GeboChatMessageEnvelope, GeboChatMessageEnvelope> {
	public ChatAgentServiceNetworkAdapter(
			IGReactiveAgentService<ChatPipelineExecutionRuntimeData, GeboChatMessageEnvelope, GeboChatMessageEnvelope> service,

			Sinks.Many<IGPartialOperation<GeboChatMessageEnvelope>> sink) {
		super(service, ChatPipelineExecutionRuntimeData.class, GeboChatMessageEnvelope.class, sink);

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
