package ai.gebo.llms.chat.pipelines.service;

import java.util.UUID;

import ai.gebo.architecture.agents.services.INotificationSink;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.ChatNotificationContent.NotificationType;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatMessageEnvelope;
import ai.gebo.model.GUserMessage;

public interface ISinkUIEmitter extends INotificationSink {
	@Override
	default void next(NotificationObject state) {
		NotificationType notificationType = NotificationType.INFO;
		try {

			notificationType = NotificationType
					.valueOf(state.getNotificationType() == null ? "INFO" : state.getNotificationType().name());
		} catch (Throwable th) {

		}
		String code = state.getCode() != null ? state.getCode() : UUID.randomUUID().toString();
		notifyUser(code, state.getMessage(), state.getIcon(), 3000l, notificationType);

	}

	void notifyUser(String code, String message, String icon, Long duration, NotificationType notificationType);

	default void notifyLLMProblems() {
		next(new GeboChatMessageEnvelope(
				GUserMessage.errorMessage("Problems with llm provider", "Received an error from the llms provider")));
	}

	void next(GeboChatMessageEnvelope event);

	void error(Throwable error);

	void complete();
}
