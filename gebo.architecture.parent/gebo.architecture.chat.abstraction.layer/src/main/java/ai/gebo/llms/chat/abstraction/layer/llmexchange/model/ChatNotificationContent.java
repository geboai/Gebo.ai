package ai.gebo.llms.chat.abstraction.layer.llmexchange.model;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatNotificationContent {
	public static enum NotificationType {
		INFO, DEBUG, ERROR
	}

	@NotNull
	private String code = null;
	@NotNull
	private String message = null;
	private String icon = null;
	private Long duration = null;
	private NotificationType notificationType = NotificationType.INFO;
}
