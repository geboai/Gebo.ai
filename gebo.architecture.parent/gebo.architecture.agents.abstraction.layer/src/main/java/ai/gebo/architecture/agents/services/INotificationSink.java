package ai.gebo.architecture.agents.services;

import java.util.UUID;

import ai.gebo.architecture.agents.services.INotificationSink.NotificationObject.NotificationType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public interface INotificationSink {
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class NotificationObject {
		public static final String DEFAULT_ICON = "p pi-comment";

		public static enum NotificationType {
			INFO, DEBUG
		}

		String code = UUID.randomUUID().toString();
		@NotNull
		String message;
		String icon = DEFAULT_ICON;
		NotificationType notificationType = NotificationType.INFO;

	}

	public void next(NotificationObject state);

	public default void next(String message, NotificationType type) {
		NotificationObject state = new NotificationObject();
		state.setMessage(message);
		state.setNotificationType(type);
		next(state);
	}
}
