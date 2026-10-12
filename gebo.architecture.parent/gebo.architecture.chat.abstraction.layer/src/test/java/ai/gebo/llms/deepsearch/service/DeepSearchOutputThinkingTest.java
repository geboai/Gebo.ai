/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.deepsearch.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import ai.gebo.llms.abstraction.layer.model.GChatAnswerChunk;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GThinkingEvent;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatMessageEnvelope;
import ai.gebo.llms.chat.pipelines.service.ISinkUIEmitter;
import reactor.core.publisher.Flux;

/**
 * The deep search answering the user is an output: the reasoning of what it writes goes
 * to the chat before its text, streamed or folded by blocking calls; an analysis made
 * for a tool keeps the text only.
 */
class DeepSearchOutputThinkingTest {

	/** A chat recording what it is sent, in order: "T:" reasoning, "C" completed. */
	private static final class Chat {
		final List<String> sent = new ArrayList<>();
		final ISinkUIEmitter emitter = mock(ISinkUIEmitter.class);

		Chat() {
			doAnswer(invocation -> {
				if (invocation.getArgument(0) instanceof GeboChatMessageEnvelope envelope
						&& envelope.getContent() instanceof GThinkingEvent event) {
					sent.add(event.isCompleted() ? "C" : "T:" + event.getText());
				}
				return null;
			}).when(emitter).next(any(GeboChatMessageEnvelope.class));
		}
	}

	private static GChatAnswerChunk chunk(String thinking, String answer) {
		return new GChatAnswerChunk(answer, thinking, !thinking.isEmpty(), null, List.of(), null);
	}

	@Test
	void aStreamedOutputsReasoningComesBeforeItsText() {
		final Chat chat = new Chat();
		final DeepSearchOutputThinking thinking = DeepSearchOutputThinking.toChat(chat.emitter);

		final List<String> text = thinking
				.text(Flux.just(chunk("Weighing the sources.\n", ""), chunk("", "The analysis."), chunk("", " More.")))
				.doOnNext(piece -> chat.sent.add("A:" + piece)).collectList().block();

		assertEquals(List.of("The analysis.", " More."), text);
		assertEquals(List.of("T:Weighing the sources.\n", "C", "A:The analysis.", "A: More."), chat.sent);
	}

	@Test
	void aFoldedOutputsReasoningEndsWhenItsTextComes() {
		final Chat chat = new Chat();
		final DeepSearchOutputThinking thinking = DeepSearchOutputThinking.toChat(chat.emitter);

		thinking.reasoning("Fold one.\n");
		thinking.reasoning("Fold two.\n");
		thinking.answering("The report");
		thinking.ended();

		assertEquals(List.of("T:Fold one.\n", "T:Fold two.\n", "C"), chat.sent, "completed once");
	}

	@Test
	void anAnalysisForAToolKeepsTheTextOnly() {
		assertEquals(List.of("Text."), DeepSearchOutputThinking.NONE
				.text(Flux.just(chunk("Reasoning.\n", ""), chunk("", "Text."))).collectList().block());
		assertTrue(DeepSearchOutputThinking.toChat(null) == DeepSearchOutputThinking.NONE);
	}
}
