/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.deepseek.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.part.ReasoningPart;
import org.springframework.ai.chat.messages.part.TextPart;
import org.springframework.ai.chat.model.Generation;

import ai.gebo.llms.abstraction.layer.services.IGReasoningExtractor;

/**
 * DeepSeek's reasoning as Spring AI's DeepSeekChatModel (2.1) gives it: the reasoning_content
 * of each streamed chunk as a reasoning part of its message, the piece the chunk adds, the
 * content as its text part. The reading by default of every provider reads it.
 */
class DeepseekReasoningTest {

	private static Generation chunk(String reasoning, String text) {
		final AssistantMessage.Builder<?> builder = AssistantMessage.builder();
		if (reasoning != null) {
			builder.part(ReasoningPart.of(reasoning));
		}
		if (text != null) {
			builder.part(TextPart.of(text));
		}
		return new Generation(builder.build());
	}

	@Test
	void eachChunksPieceOfReasoningApartFromItsText() {
		final IGReasoningExtractor.Round round = IGReasoningExtractor.PARTS.round();
		assertEquals(new IGReasoningExtractor.Reasoning("We need ", "", true), round.of(chunk("We need ", null)));
		assertEquals(new IGReasoningExtractor.Reasoning("We need the sum.", "", true),
				round.of(chunk("We need the sum.", null)), "a piece, even one starting as the reasoning so far");
		assertEquals(new IGReasoningExtractor.Reasoning("", "It is 4.", false), round.of(chunk(null, "It is 4.")));
		assertEquals(new IGReasoningExtractor.Reasoning("", "plain", false),
				round.of(new Generation(new AssistantMessage("plain"))));
	}
}
