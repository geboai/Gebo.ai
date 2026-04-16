package ai.gebo.llms.chat.pipelines.service;

import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatMessageEnvelope;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Sinks;

@AllArgsConstructor
public class SinkUIEmitterImpl implements ISinkUIEmitter {

	private final Sinks.Many<GeboChatMessageEnvelope> sink;

	@Override
	public void next(GeboChatMessageEnvelope event) {
		sink.emitNext(event, Sinks.EmitFailureHandler.FAIL_FAST);

	}

	@Override
	public void error(Throwable error) {
		sink.emitError(error, Sinks.EmitFailureHandler.FAIL_FAST);

	}

	@Override
	public void complete() {
		sink.emitComplete(Sinks.EmitFailureHandler.FAIL_FAST);

	}

}
