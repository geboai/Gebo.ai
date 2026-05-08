package ai.gebo.llms.chat.pipelines.service;

import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatMessageEnvelope;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.ChatNotificationContent.NotificationType;

public interface ISinkUIEmitter {
	void notifyUser(String code, String message, String icon, Long duration, NotificationType notificationType);

	void next(GeboChatMessageEnvelope event);

	void error(Throwable error);

	void complete();
}
