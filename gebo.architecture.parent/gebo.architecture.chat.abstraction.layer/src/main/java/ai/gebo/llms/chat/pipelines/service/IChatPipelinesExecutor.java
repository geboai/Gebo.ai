package ai.gebo.llms.chat.pipelines.service;

import java.io.IOException;

import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.chat.abstraction.layer.model.GUserChatContext;
import ai.gebo.llms.chat.abstraction.layer.model.GeboChatMessageEnvelope;
import ai.gebo.llms.chat.abstraction.layer.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.model.GeboChatResponse;
import reactor.core.publisher.Flux;

public interface IChatPipelinesExecutor {

	public Flux<GeboChatMessageEnvelope> streamingExecute(GeboChatRequest request, GUserChatContext context,
			IGConfigurableChatModel chatModel, IGConfigurableChatModel serviceModel, String pipelineCode)
			throws ChatPipelineException, IOException;

	public GeboChatResponse execute(GeboChatRequest request, GUserChatContext context,
			IGConfigurableChatModel chatModel, IGConfigurableChatModel serviceModel, String pipelineCode)
			throws ChatPipelineException, IOException;
}
