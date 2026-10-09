/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.abstraction.layer.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.metadata.ChatGenerationMetadata;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;

import ai.gebo.llms.abstraction.layer.model.GChatAnswerChunk;
import ai.gebo.llms.abstraction.layer.services.impl.UsageAdvisorFactoryImpl.GeboChatModelUsageAdvisor.AnswerShape;

/**
 * Pins how a call ended, as its DEBUG line tells it: the last finish reason, the text
 * and the reasoning streamed, read from the normalized chunks every provider alike.
 */
class AnswerShapeTest {

	/** A normalized chunk: the reasoning it adds, its text, why the model stopped. */
	private static ChatClientResponse chunk(String reasoning, String text, String finish) {
		final ChatGenerationMetadata metadata = finish != null
				? ChatGenerationMetadata.builder().finishReason(finish).build()
				: ChatGenerationMetadata.NULL;
		final ChatResponse.Builder chat = ChatResponse.builder()
				.generations(List.of(new Generation(AssistantMessage.builder().content(text).build(), metadata)));
		if (reasoning != null) {
			chat.metadata(GChatAnswerChunk.THINKING_DELTA_METADATA, reasoning);
		}
		return new ChatClientResponse(chat.build(), Map.of());
	}

	@Test
	void aCallCutWhileReasoningShowsNoTextAndTheLimit() {
		final AnswerShape shape = new AnswerShape();
		shape.add(chunk("Weighing", "", null));
		shape.add(chunk(" the sources", "", null));
		shape.add(chunk("...", "", "LENGTH"));

		assertEquals("finishReason=LENGTH text=0 reasoning=23", shape.toString());
	}

	@Test
	void aReasoningInPiecesAndTheTextAreSummed() {
		final AnswerShape shape = new AnswerShape();
		shape.add(chunk("First ", null, null));
		shape.add(chunk("second.", "The ", null));
		shape.add(chunk(null, "answer.", "STOP"));
		shape.add(chunk(null, null, null));

		assertEquals("finishReason=STOP text=11 reasoning=13", shape.toString());
		assertEquals("finishReason=null text=0 reasoning=0", new AnswerShape().toString(), "a failed call");
	}

	@Test
	void aBlockingCallsWholeReasoningIsCounted() {
		final AnswerShape shape = AnswerShape.of(new ChatClientResponse(ChatResponse.builder()
				.generations(List.of(new Generation(new AssistantMessage("The answer."),
						ChatGenerationMetadata.builder().finishReason("STOP").build())))
				.metadata(GChatAnswerChunk.THINKING_METADATA, "Let me think.").build(), Map.of()));

		assertEquals("finishReason=STOP text=11 reasoning=13", shape.toString());
	}
}
