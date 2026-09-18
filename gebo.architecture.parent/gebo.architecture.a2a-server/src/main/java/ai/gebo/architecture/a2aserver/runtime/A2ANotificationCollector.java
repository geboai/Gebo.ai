package ai.gebo.architecture.a2aserver.runtime;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import ai.gebo.architecture.agents.services.INotificationSink;

/**
 * A minimal {@link INotificationSink} that simply collects the agent-network
 * progress notifications produced during a synchronous A2A {@code message/send}
 * run, so they can be attached to the resulting task (or logged). The streaming
 * {@code message/stream} path uses a different, SSE-backed sink.
 */
public class A2ANotificationCollector implements INotificationSink {

	private final List<NotificationObject> notifications = new CopyOnWriteArrayList<>();

	@Override
	public void next(NotificationObject state) {
		if (state != null) {
			notifications.add(state);
		}
	}

	public List<NotificationObject> getNotifications() {
		return notifications;
	}
}
