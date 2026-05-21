package ai.gebo.llms.chat.pipelines.service;

import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.ChatNotificationContent.NotificationType;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatMessageEnvelope;

public interface ISinkUIEmitter {
	void notifyUser(String code, String message, String icon, Long duration, NotificationType notificationType);

	void next(GeboChatMessageEnvelope event);

	void error(Throwable error);

	void complete();
}
