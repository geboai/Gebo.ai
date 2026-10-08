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

import ai.gebo.llms.abstraction.layer.services.impl.UsageAdvisorFactoryImpl.GeboChatModelUsageAdvisor.AnswerShape;

/**
 * Pins how a call ended, as its DEBUG line tells it: the last finish reason, the text
 * and the reasoning streamed, a reasoning given piece by piece or grown.
 */
class AnswerShapeTest {

	private static ChatClientResponse chunk(String reasoning, String text, String finish) {
		final AssistantMessage message = AssistantMessage.builder().content(text)
				.properties(reasoning != null ? Map.of(AnswerShape.REASONING_CONTENT_METADATA, reasoning) : Map.of())
				.build();
		final ChatGenerationMetadata metadata = finish != null
				? ChatGenerationMetadata.builder().finishReason(finish).build()
				: ChatGenerationMetadata.NULL;
		return new ChatClientResponse(new ChatResponse(List.of(new Generation(message, metadata))), Map.of());
	}

	@Test
	void aCallCutWhileReasoningShowsNoTextAndTheLimit() {
		final AnswerShape shape = new AnswerShape();
		shape.add(chunk("Weighing", "", null));
		shape.add(chunk("Weighing the sources", "", null));
		shape.add(chunk("Weighing the sources...", "", "LENGTH"));

		assertEquals("finishReason=LENGTH text=0 reasoning=23", shape.toString(), "a grown reasoning counted once");
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
}
