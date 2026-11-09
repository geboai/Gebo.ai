package ai.gebo.llms.chat.pipelines.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatMessageEnvelope;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatResponse;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.NetworkOfAgentsSuspendedState;
import ai.gebo.llms.chat.abstraction.layer.services.GeboChatException;
import ai.gebo.llms.chat.abstraction.layer.services.GeboChatSessionLifecycleException;
import ai.gebo.llms.chat.pipelines.model.ui.PipelineChatMenu;
import jakarta.validation.constraints.NotNull;
import reactor.core.publisher.Flux;

public interface IChatPipelineService {
	public GeboChatResponse chat(String pipelineCode, @NotNull GeboChatRequest request,
			LinkedHashMap<String, Object> environment)
			throws ChatPipelineException, GeboChatSessionLifecycleException, GeboChatException;

	public GeboChatResponse respawnAgenticChat(String pipelineCode, @NotNull GeboChatRequest request,
			LinkedHashMap<String, Object> environment)
			throws ChatPipelineException, GeboChatSessionLifecycleException, GeboChatException;

	public default GeboChatResponse chat(@NotNull GeboChatRequest request, LinkedHashMap<String, Object> environment)
			throws ChatPipelineException, GeboChatException {
		return chat(null, request, environment);
	}

	public default GeboChatResponse respawnAgenticChat(@NotNull GeboChatRequest request,
			LinkedHashMap<String, Object> environment) throws ChatPipelineException, GeboChatException {
		return respawnAgenticChat(null, request, environment);
	}

	public Flux<GeboChatMessageEnvelope> streamingChat(String pipelineCode, @NotNull GeboChatRequest request,
			LinkedHashMap<String, Object> environment)
			throws ChatPipelineException, GeboChatSessionLifecycleException, GeboChatException;

	public Flux<GeboChatMessageEnvelope> streamingRespawnAgenticChat(String pipelineCode,
			@NotNull GeboChatRequest request, LinkedHashMap<String, Object> environment)
			throws ChatPipelineException, GeboChatSessionLifecycleException, GeboChatException;

	public default Flux<GeboChatMessageEnvelope> streamingChat(@NotNull GeboChatRequest request,
			LinkedHashMap<String, Object> environment) throws ChatPipelineException, GeboChatException {
		return streamingChat(null, request, environment);
	}

	public default Flux<GeboChatMessageEnvelope> streamingRespawnAgenticChat(@NotNull GeboChatRequest request,
			LinkedHashMap<String, Object> environment)
			throws ChatPipelineException, GeboChatSessionLifecycleException, GeboChatException {
		return streamingRespawnAgenticChat(null, request, environment);
	}

	public List<PipelineChatMenu> getPersonalPipelinesChatMenu(String pipelineCode, String chatProfileCode)
			throws ChatPipelineException;

	public void stopPipeline(String userChatContextCode) throws ChatPipelineException;

	/**
	 * The suspended network of agents the conversation still waits for, as the
	 * client reacts to it: shown again when the conversation is opened, answered
	 * through the respawn. Empty when the conversation has no open block.
	 */
	public Optional<NetworkOfAgentsSuspendedState> pendingNetworkOfAgentsSuspension(String userChatContextCode);
}
