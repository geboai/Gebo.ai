package ai.gebo.llms.chat.pipelines.service;

import java.io.IOException;

import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatMessageEnvelope;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatResponse;
import ai.gebo.llms.chat.abstraction.layer.services.GeboChatSessionLifecycleException;
import reactor.core.publisher.Flux;

public interface IChatPipelinesExecutor {

	public Flux<GeboChatMessageEnvelope> streamingExecute(GeboChatRequest request, IGConfigurableChatModel chatModel,
			IGConfigurableChatModel serviceModel, String pipelineCode)
			throws ChatPipelineException, IOException, LLMConfigException, GeboChatSessionLifecycleException;

	public GeboChatResponse execute(GeboChatRequest request, IGConfigurableChatModel chatModel,
			IGConfigurableChatModel serviceModel, String pipelineCode)
			throws ChatPipelineException, IOException, LLMConfigException, GeboChatSessionLifecycleException;
}
