package ai.gebo.llms.chat.pipelines.service;

import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.ChatNotificationContent;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.ChatNotificationContent.NotificationType;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatMessageEnvelope;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Sinks;

@AllArgsConstructor
public class SinkUIEmitterImpl implements ISinkUIEmitter {

	private static final Logger LOGGER = LoggerFactory.getLogger(SinkUIEmitterImpl.class);

	private final Sinks.Many<GeboChatMessageEnvelope> sink;

	@Override
	public void next(GeboChatMessageEnvelope event) {
		sink.emitNext(event, Sinks.EmitFailureHandler.FAIL_FAST);

	}

	@Override
	public void error(Throwable error) {
		LOGGER.error("Emitting error to the chat UI sink", error);
		sink.emitError(error, Sinks.EmitFailureHandler.FAIL_FAST);

	}

	@Override
	public void complete() {
		sink.emitComplete(Sinks.EmitFailureHandler.FAIL_FAST);

	}

	@Override
	public void notifyUser(String code, String message, String icon, Long duration, NotificationType notificationType) {
		ChatNotificationContent content = new ChatNotificationContent(code, message, icon, duration, notificationType);
		GeboChatMessageEnvelope envelope = new GeboChatMessageEnvelope(content);
		this.next(envelope);

	}

}
