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
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.deepseek.DeepSeekAssistantMessage;

import ai.gebo.llms.abstraction.layer.services.IGReasoningExtractor;

/**
 * DeepSeek's reasoning as Spring AI's DeepSeekChatModel (2.0.1) streams it: the piece
 * each chunk adds, in the reasoning field of its DeepSeekAssistantMessage.
 */
class DeepseekReasoningTest {

	private static Generation chunk(String reasoning, String text) {
		return new Generation(new DeepSeekAssistantMessage.Builder().content(text).reasoningContent(reasoning).build());
	}

	@Test
	void eachChunksPieceOfReasoningApartFromItsText() {
		final IGReasoningExtractor.Round round = DeepseekChatModelConfigurationSupportService.REASONING.round();

		assertEquals(new IGReasoningExtractor.Reasoning("We need ", "", false), round.of(chunk("We need ", "")));
		assertEquals(new IGReasoningExtractor.Reasoning("We need the sum.", "", false),
				round.of(chunk("We need the sum.", "")), "a piece, even one starting as the reasoning so far");
		assertEquals(new IGReasoningExtractor.Reasoning("", "It is 4.", false), round.of(chunk(null, "It is 4.")));
		assertEquals(new IGReasoningExtractor.Reasoning("", "plain", false),
				round.of(new Generation(new AssistantMessage("plain"))));
	}
}
