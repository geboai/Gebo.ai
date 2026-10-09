/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.mistralai.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.model.Generation;

import ai.gebo.llms.abstraction.layer.services.IGReasoningExtractor;

/**
 * Mistral's reasoning as Spring AI's MistralAiChatModel (2.0.1) streams it: the piece
 * each chunk adds, in the thinking_content metadata of its message.
 */
class MistralReasoningTest {

	private static Generation chunk(String thinking, String text) {
		return new Generation(AssistantMessage.builder().content(text)
				.properties(thinking != null ? Map.of("thinking_content", thinking) : Map.of()).build());
	}

	@Test
	void eachChunksPieceOfReasoningApartFromItsText() {
		final IGReasoningExtractor.Round round = MistralChatModelConfigurationSupportService.REASONING.round();

		assertEquals(new IGReasoningExtractor.Reasoning("Okay, ", "", false), round.of(chunk("Okay, ", "")));
		assertEquals(new IGReasoningExtractor.Reasoning("Okay, the sum.", "", false), round.of(chunk("Okay, the sum.", "")));
		assertEquals(new IGReasoningExtractor.Reasoning("", "It is 4.", false), round.of(chunk(null, "It is 4.")));
	}
}
