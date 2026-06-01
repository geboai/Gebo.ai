package ai.gebo.architecture.agents.services;

public interface INotificationSink<NotificationObject> {
	public void next(NotificationObject state);
}
