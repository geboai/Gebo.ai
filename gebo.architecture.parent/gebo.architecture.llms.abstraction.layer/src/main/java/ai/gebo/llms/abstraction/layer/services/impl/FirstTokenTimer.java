package ai.gebo.llms.abstraction.layer.services.impl;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;

/**
 * Notes when the first chunk carrying generated content of a streamed chat call
 * arrives, for the time to first token: the latency of the call in the strict sense,
 * as opposed to its response time, which lasts until the whole response is complete.
 * <p>
 * A chunk counts once it carries text or a tool call: chunks with no generation, or
 * with an empty one (metadata or usage only), do not. When the call drives a tool
 * calling loop the first token is the one of the first round trip, the moment the
 * model started answering at all.
 * <p>
 * One instance per call; safe to feed from the Reactor thread delivering the chunks.
 */
final class FirstTokenTimer {
	/** 0 while no content has arrived: {@link System#nanoTime()} is never exactly 0 in practice. */
	private final AtomicLong firstTokenNanos = new AtomicLong();

	/** Feeds a streamed chunk; only the first one carrying content is timed. */
	void onChunk(ChatResponse chunk) {
		if (firstTokenNanos.get() == 0 && hasGeneratedContent(chunk)) {
			firstTokenNanos.compareAndSet(0, System.nanoTime());
		}
	}

	/** {@link System#nanoTime()} of the first chunk with content, or null if none arrived. */
	Long firstTokenNanos() {
		long value = firstTokenNanos.get();
		return value != 0 ? Long.valueOf(value) : null;
	}

	static boolean hasGeneratedContent(ChatResponse chunk) {
		if (chunk == null || chunk.getResults() == null) {
			return false;
		}
		for (Generation generation : chunk.getResults()) {
			AssistantMessage output = generation != null ? generation.getOutput() : null;
			if (output == null) {
				continue;
			}
			String text = output.getText();
			if ((text != null && !text.isEmpty()) || output.hasToolCalls()) {
				return true;
			}
		}
		return false;
	}
}
