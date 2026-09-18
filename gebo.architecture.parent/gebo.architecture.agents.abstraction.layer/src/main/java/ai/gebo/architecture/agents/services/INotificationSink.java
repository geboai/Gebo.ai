package ai.gebo.architecture.agents.services;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ai.gebo.architecture.agents.services.INotificationSink.NotificationObject.NotificationType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public interface INotificationSink {
	Logger NOTIFICATION_LOGGER = LoggerFactory.getLogger(INotificationSink.class);

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class NotificationObject {
		public static final String DEFAULT_ICON = "p pi-comment";

		public static enum NotificationType {
			INFO, DEBUG, ERROR
		}

		String code = UUID.randomUUID().toString();
		@NotNull
		String message;
		String icon = DEFAULT_ICON;
		NotificationType notificationType = NotificationType.INFO;

	}

	/**
	 * Reports a failure together with what caused it.
	 * <p>
	 * NotificationObject carries only a message, so a sink receiving one has no way back
	 * to the exception - the provider's own complaint, which is the part that says whether
	 * a request was malformed, refused or timed out, is discarded at the call site. Sinks
	 * that can render more than a line of text override this to keep it.
	 * <p>
	 * The default keeps the previous behaviour for the sinks that only show text.
	 */
	public default void notifyFailure(String message, Throwable failure) {
		next(message, NotificationType.ERROR);
	}

	public void next(NotificationObject state);

	public default void next(String message, NotificationType type) {
		NotificationObject state = new NotificationObject();
		state.setMessage(message);
		state.setNotificationType(type);
		if (NOTIFICATION_LOGGER.isDebugEnabled()) {
			NOTIFICATION_LOGGER.debug("Notifying the user through " + getClass().getName() + " with a " + type
					+ " message of " + (message != null ? message.length() : 0) + " character(s)");
		}
		if (NOTIFICATION_LOGGER.isTraceEnabled()) {
			NOTIFICATION_LOGGER.trace("<USER_NOTIFICATION type=" + type + ">");
			NOTIFICATION_LOGGER.trace(message);
			NOTIFICATION_LOGGER.trace("</USER_NOTIFICATION>");
		}
		next(state);
	}
}
