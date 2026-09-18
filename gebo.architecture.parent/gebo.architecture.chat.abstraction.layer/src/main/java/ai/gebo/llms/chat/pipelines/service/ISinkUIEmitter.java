package ai.gebo.llms.chat.pipelines.service;

import java.util.UUID;

import ai.gebo.architecture.agents.services.INotificationSink;
import ai.gebo.architecture.agents.services.INotificationSink.NotificationObject;
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
		if (notificationType == NotificationType.ERROR) {
			// notifyUser puts the text on screen for three seconds and then drops it, which
			// is right for progress but wrong for a failure: the notification is the only
			// account of what went wrong, and whoever reads the conversation afterwards -
			// or reads it at all, having looked away - finds an answer that is simply
			// missing, with nothing saying why. Errors therefore also go into the chat
			// stream as a GUserMessage, which is what the UI renders inside the
			// conversation and what a session keeps.
			next(new GeboChatMessageEnvelope(GUserMessage.errorMessage(
					state.getMessage() != null ? state.getMessage() : "Agent failure",
					"The agents network reported an error while producing this answer")));
		}

	}

	/**
	 * A failure reported with its cause: the stack trace goes into the conversation.
	 * <p>
	 * It reads as cryptic to anyone who is not debugging, but it is the only place the
	 * provider's own words survive - "parallel_tool_calls is only allowed when tools are
	 * specified" tells whoever is looking exactly what to change, where a bare "an agent
	 * failed" sends them to the server logs, if they have them.
	 */
	@Override
	default void notifyFailure(String message, Throwable failure) {
		String code = UUID.randomUUID().toString();
		notifyUser(code, message, NotificationObject.DEFAULT_ICON, 3000l, NotificationType.ERROR);
		next(new GeboChatMessageEnvelope(failure != null ? GUserMessage.errorMessage(message, failure)
				: GUserMessage.errorMessage(message,
						"The agents network reported an error while producing this answer")));
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
