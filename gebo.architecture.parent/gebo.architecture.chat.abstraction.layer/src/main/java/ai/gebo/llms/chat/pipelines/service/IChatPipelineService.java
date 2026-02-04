package ai.gebo.llms.chat.pipelines.service;

import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatResponse;
import ai.gebo.llms.chat.abstraction.layer.model.GeboChatMessageEnvelope;
import ai.gebo.llms.chat.abstraction.layer.services.GeboChatSessionLifecycleException;
import jakarta.validation.constraints.NotNull;
import reactor.core.publisher.Flux;

public interface IChatPipelineService {
	public GeboChatResponse chat(String pipelineCode, @NotNull GeboChatRequest request) throws ChatPipelineException, GeboChatSessionLifecycleException;

	public default GeboChatResponse chat(@NotNull GeboChatRequest request) throws ChatPipelineException, GeboChatSessionLifecycleException {
		return chat(null, request);
	}

	public Flux<GeboChatMessageEnvelope> streamingChat(String pipelineCode, @NotNull GeboChatRequest request)
			throws ChatPipelineException, GeboChatSessionLifecycleException;

	public default Flux<GeboChatMessageEnvelope> streamingChat(@NotNull GeboChatRequest request)
			throws ChatPipelineException, GeboChatSessionLifecycleException {
		return streamingChat(null, request);
	}
}
