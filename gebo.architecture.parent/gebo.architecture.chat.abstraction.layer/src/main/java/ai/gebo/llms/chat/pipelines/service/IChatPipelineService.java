package ai.gebo.llms.chat.pipelines.service;

import java.util.LinkedHashMap;
import java.util.List;

import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatMessageEnvelope;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatResponse;
import ai.gebo.llms.chat.abstraction.layer.services.GeboChatSessionLifecycleException;
import ai.gebo.llms.chat.pipelines.model.ui.PipelineChatMenu;
import jakarta.validation.constraints.NotNull;
import reactor.core.publisher.Flux;

public interface IChatPipelineService {
	public GeboChatResponse chat(String pipelineCode, @NotNull GeboChatRequest request,
			LinkedHashMap<String, Object> environment) throws ChatPipelineException, GeboChatSessionLifecycleException;

	public default GeboChatResponse chat(@NotNull GeboChatRequest request, LinkedHashMap<String, Object> environment)
			throws ChatPipelineException, GeboChatSessionLifecycleException {
		return chat(null, request, environment);
	}

	public Flux<GeboChatMessageEnvelope> streamingChat(String pipelineCode, @NotNull GeboChatRequest request,
			LinkedHashMap<String, Object> environment) throws ChatPipelineException, GeboChatSessionLifecycleException;

	public default Flux<GeboChatMessageEnvelope> streamingChat(@NotNull GeboChatRequest request,
			LinkedHashMap<String, Object> environment) throws ChatPipelineException, GeboChatSessionLifecycleException {
		return streamingChat(null, request, environment);
	}

	public List<PipelineChatMenu> getPersonalPipelinesChatMenu(String pipelineCode, String chatProfileCode) throws ChatPipelineException;
}
