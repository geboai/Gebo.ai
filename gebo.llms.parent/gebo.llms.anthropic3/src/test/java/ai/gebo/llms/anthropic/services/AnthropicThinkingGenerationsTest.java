/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.anthropic.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;

import ai.gebo.llms.abstraction.layer.services.AnswerFirstGenerations;

/**
 * The generations Spring AI's AnthropicChatModel (2.0.1) builds from a call's content
 * blocks: a thinking block with its "signature" property, a redacted thinking block with
 * its "data" property, then the text and tool calls as the last generation.
 */
class AnthropicThinkingGenerationsTest {

	private static Generation generation(String text, Map<String, Object> properties) {
		return new Generation(AssistantMessage.builder().content(text).properties(properties).build());
	}

	@Test
	void thinkingAndRedactedThinkingBlocksAreReasoningTheAnswerIsNot() {
		assertTrue(AnthropicChatModelConfigurationSupportService
				.isThinkingBlock(generation("let me think", Map.of("signature", "EqQBCkYIBxgC"))));
		assertTrue(AnthropicChatModelConfigurationSupportService
				.isThinkingBlock(generation(null, Map.of("data", "EmwKAhgBEgy3va3pzix"))));
		assertFalse(AnthropicChatModelConfigurationSupportService
				.isThinkingBlock(generation("the answer", Map.of())));
	}

	@Test
	void aClaudeCallWithThinkingAnswersWithItsAnswer() {
		ChatResponse response = new ChatResponse(List.of(generation("let me think", Map.of("signature", "s")),
				generation(null, Map.of("data", "d")), generation("the answer", Map.of())));

		ChatResponse reordered = AnswerFirstGenerations.answerFirst(response,
				AnthropicChatModelConfigurationSupportService::isThinkingBlock);

		assertEquals("the answer", reordered.getResult().getOutput().getText());
		assertEquals(3, reordered.getResults().size(), "the thinking blocks are kept");
	}
}
