package ai.gebo.llms.chat.pipelines.service;

import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.ChatNotificationContent.NotificationType;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatMessageEnvelope;
import ai.gebo.model.GUserMessage;

public interface ISinkUIEmitter {
	void notifyUser(String code, String message, String icon, Long duration, NotificationType notificationType);

	default void notifyLLMProblems() {
		next(new GeboChatMessageEnvelope(
				GUserMessage.errorMessage("Problems with llm provider", "Received an error from the llms provider")));
	}

	void next(GeboChatMessageEnvelope event);

	void error(Throwable error);

	void complete();
}
