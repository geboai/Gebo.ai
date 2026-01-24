package ai.gebo.llms.chat.pipelines.service;

import java.io.IOException;

import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatResponse;
import ai.gebo.llms.chat.abstraction.layer.model.GUserChatContext;
import ai.gebo.llms.chat.abstraction.layer.model.GeboChatMessageEnvelope;
import reactor.core.publisher.Flux;

public interface IChatPipelinesExecutor {

	public Flux<GeboChatMessageEnvelope> streamingExecute(GeboChatRequest request, GUserChatContext context,
			IGConfigurableChatModel chatModel, IGConfigurableChatModel serviceModel, String pipelineCode)
			throws ChatPipelineException, IOException;

	public GeboChatResponse execute(GeboChatRequest request, GUserChatContext context,
			IGConfigurableChatModel chatModel, IGConfigurableChatModel serviceModel, String pipelineCode)
			throws ChatPipelineException, IOException;
}
