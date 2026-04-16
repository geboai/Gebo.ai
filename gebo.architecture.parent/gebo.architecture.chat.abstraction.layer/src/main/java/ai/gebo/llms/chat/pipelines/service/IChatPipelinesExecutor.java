package ai.gebo.llms.chat.pipelines.service;

import java.io.IOException;
import java.util.LinkedHashMap;

import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatMessageEnvelope;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatResponse;
import ai.gebo.llms.chat.abstraction.layer.services.GeboChatException;
import ai.gebo.llms.chat.abstraction.layer.services.GeboChatSessionLifecycleException;
import reactor.core.publisher.Flux;

public interface IChatPipelinesExecutor {

	public Flux<GeboChatMessageEnvelope> streamingExecute(GeboChatRequest request, LinkedHashMap<String, Object> environment,
			IGConfigurableChatModel chatModel, IGConfigurableChatModel serviceModel, String pipelineCode)
			throws ChatPipelineException, IOException, LLMConfigException, GeboChatSessionLifecycleException, GeboChatException;

	public GeboChatResponse execute(GeboChatRequest request, LinkedHashMap<String, Object> environment,
			IGConfigurableChatModel chatModel, IGConfigurableChatModel serviceModel, String pipelineCode)
			throws ChatPipelineException, IOException, LLMConfigException, GeboChatSessionLifecycleException, GeboChatException;
}
