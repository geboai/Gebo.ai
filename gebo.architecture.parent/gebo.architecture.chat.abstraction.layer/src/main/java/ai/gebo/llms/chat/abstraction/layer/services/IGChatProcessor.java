package ai.gebo.llms.chat.abstraction.layer.services;

import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.chat.abstraction.layer.model.GUserChatContext;
import ai.gebo.llms.chat.abstraction.layer.model.GeboChatMessageEnvelope;
import ai.gebo.llms.chat.abstraction.layer.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.model.GeboChatResponse;
import ai.gebo.llms.chat.abstraction.layer.model.session.ChatSessionState;
import reactor.core.publisher.Flux;

public interface IGChatProcessor {
	public GeboChatResponse execute(String ovveriddenPrompt, GeboChatRequest request, GUserChatContext context,
			ChatSessionState sessionState, IGConfigurableChatModel chatModel, IGConfigurableChatModel serviceModel);

	public Flux<GeboChatMessageEnvelope> streamingExecute(String ovveriddenPrompt, GeboChatRequest request,
			GUserChatContext context, ChatSessionState sessionState, IGConfigurableChatModel chatModel,
			IGConfigurableChatModel serviceModel);
}
