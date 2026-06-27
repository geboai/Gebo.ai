package ai.gebo.architecture.agents.services;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public interface INotificationSink {
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class NotificationObject {
		public static enum NotificationType {
			INFO, DEBUG
		}
		String code;
		@NotNull
		String message;
		String icon;
		NotificationType notificationType;

	}

	public void next(NotificationObject state);
}
