/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.deepsearch.service;

import java.util.List;

import ai.gebo.llms.abstraction.layer.model.GChatAnswerChunk;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GThinkingEvent;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatMessageEnvelope;
import ai.gebo.llms.chat.abstraction.layer.services.impl.ThinkingStream;
import ai.gebo.llms.chat.pipelines.service.ISinkUIEmitter;
import reactor.core.publisher.Flux;

/**
 * Where the reasoning of a deep search's output goes: the model calls writing what the
 * analysis outputs (its final analysis, its running report, its answer when nothing was
 * found) give their reasoning here, the text of the output being the answer only.
 * <p>
 * The deep search answering the user is an output, and an output streams its reasoning
 * to the user's chat ({@link #toChat(ISinkUIEmitter)}); an analysis made for an agent's
 * tool is not, its reasoning left out ({@link #NONE}).
 */
public interface DeepSearchOutputThinking {

	/** The text of an output as it streams, its reasoning sent where it goes. */
	Flux<String> text(Flux<GChatAnswerChunk> chunks);

	/** The reasoning of an output written by a blocking call (a fold of the report). */
	void reasoning(String thinking);

	/** The output's text is coming: the reasoning sent so far ended. */
	default void answering(String piece) {
	}

	/** The output ended: a reasoning no text came after ends too. */
	default void ended() {
	}

	/** The reasoning left out: the text only. */
	DeepSearchOutputThinking NONE = new DeepSearchOutputThinking() {
		@Override
		public Flux<String> text(Flux<GChatAnswerChunk> chunks) {
			return chunks.map(GChatAnswerChunk::answer).filter(text -> !text.isEmpty());
		}

		@Override
		public void reasoning(String thinking) {
		}
	};

	/** The reasoning sent to the user's chat as thinking events, completed when the text starts. */
	static DeepSearchOutputThinking toChat(ISinkUIEmitter ui) {
		if (ui == null) {
			return NONE;
		}
		return new DeepSearchOutputThinking() {
			private final ThinkingStream thinking = new ThinkingStream();

			@Override
			public Flux<String> text(Flux<GChatAnswerChunk> chunks) {
				return chunks.map(chunk -> {
					reasoning(chunk.thinking());
					answering(chunk.answer());
					return chunk.answer();
				}).filter(text -> !text.isEmpty());
			}

			@Override
			public synchronized void reasoning(String piece) {
				send(thinking.delta(piece));
			}

			@Override
			public synchronized void answering(String piece) {
				if (piece != null && !piece.isBlank()) {
					send(thinking.complete());
				}
			}

			@Override
			public synchronized void ended() {
				send(thinking.complete());
			}

			private void send(List<GThinkingEvent> events) {
				try {
					for (GThinkingEvent event : events) {
						ui.next(new GeboChatMessageEnvelope<GThinkingEvent>(event));
					}
				} catch (RuntimeException e) {
					// the reasoning never fails the answer
				}
			}
		};
	}
}
