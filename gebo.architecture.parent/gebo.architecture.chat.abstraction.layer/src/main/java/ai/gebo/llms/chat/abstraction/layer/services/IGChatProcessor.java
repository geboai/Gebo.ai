package ai.gebo.llms.chat.abstraction.layer.services;

import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatResponse;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMChatRequestResources;
import ai.gebo.llms.chat.abstraction.layer.model.GeboChatMessageEnvelope;
import reactor.core.publisher.Flux;

public interface IGChatProcessor {
	public GeboChatResponse execute(String ovveriddenPrompt, LLMChatRequestResources requestResources, IGConfigurableChatModel chatModel,
			IGConfigurableChatModel serviceModel);

	public Flux<GeboChatMessageEnvelope> streamingExecute(String ovveriddenPrompt, LLMChatRequestResources requestResources,
			IGConfigurableChatModel chatModel, IGConfigurableChatModel serviceModel);
}
