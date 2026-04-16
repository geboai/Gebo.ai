package ai.gebo.llms.chat.pipelines.service;

import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatMessageEnvelope;

public interface ISinkUIEmitter {
	void next(GeboChatMessageEnvelope event);

	void error(Throwable error);

	void complete();
}
