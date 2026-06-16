package ai.gebo.llms.agent.chat.service.impl;

import org.springframework.stereotype.Service;

import ai.gebo.architecture.agents.model.IGPartialOperation;
import ai.gebo.architecture.agents.services.IGReactiveAgentService;
import ai.gebo.architecture.agents.services.IGReactiveToNetworkAgentAdapterFactory;
import ai.gebo.llms.agent.chat.service.IChatAgentService;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatMessageEnvelope;
import ai.gebo.llms.chat.pipelines.model.ChatPipelineExecutionRuntimeData;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Service
public final class ChatAgentServiceNetworkAdapterFactory implements
		IGReactiveToNetworkAgentAdapterFactory<ChatPipelineExecutionRuntimeData, GeboChatMessageEnvelope, GeboChatMessageEnvelope> {

	@Override
	public boolean canBeAdapted(
			IGReactiveAgentService<ChatPipelineExecutionRuntimeData, GeboChatMessageEnvelope, GeboChatMessageEnvelope> service) {

		return service instanceof IChatAgentService;
	}

	@Override
	public AdapterWithFlux<ChatPipelineExecutionRuntimeData, GeboChatMessageEnvelope> create(
			IGReactiveAgentService<ChatPipelineExecutionRuntimeData, GeboChatMessageEnvelope, GeboChatMessageEnvelope> service) {
		Sinks.Many<IGPartialOperation<GeboChatMessageEnvelope>> sink = Sinks.many().unicast().onBackpressureBuffer();
		ChatAgentServiceNetworkAdapter adapter = new ChatAgentServiceNetworkAdapter(service, sink);
		Flux<GeboChatMessageEnvelope> flux = sink.asFlux().map(IGPartialOperation::getData);
		return new AdapterWithFlux<>(adapter, flux, sink);
	}
}