/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.chat.abstraction.layer.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig.ChatModelThinkingOption;

/**
 * Pins when a cut answer is written again: cut by its generated tokens, its reasoning
 * having taken them, and a lower thinking level to ask.
 */
class CutAnswerTest {

	@Test
	void theProvidersFinishReasonsOfACutAnswer() {
		assertTrue(CutAnswer.isCut("LENGTH"));
		assertTrue(CutAnswer.isCut("length"));
		assertTrue(CutAnswer.isCut("MAX_TOKENS"));
		assertTrue(CutAnswer.isCut("max_tokens "));
		assertFalse(CutAnswer.isCut("STOP"));
		assertFalse(CutAnswer.isCut("TOOL_CALLS"));
		assertFalse(CutAnswer.isCut(null));
		assertFalse(CutAnswer.isCut(""));
	}

	@Test
	void writtenAgainOnlyWhenTheReasoningTookTheBudget() {
		assertTrue(CutAnswer.reasoningTookTheBudget(9000, 292), "the reasoning case");
		assertFalse(CutAnswer.reasoningTookTheBudget(0, 20000), "a model looping writes no reasoning");
		assertFalse(CutAnswer.reasoningTookTheBudget(3000, 12000), "mostly answer: warned, not written again");
		assertFalse(CutAnswer.reasoningTookTheBudget(0, 0));
	}

	@Test
	void oneStepLowerThinking() {
		assertEquals(ChatModelThinkingOption.MEDIUM_THINKING, CutAnswer.lower(ChatModelThinkingOption.HIGH_THINKING));
		assertEquals(ChatModelThinkingOption.LOW_THINKING, CutAnswer.lower(ChatModelThinkingOption.MEDIUM_THINKING));
		assertEquals(ChatModelThinkingOption.LOW_THINKING, CutAnswer.lower(ChatModelThinkingOption.AUTO));
		assertEquals(ChatModelThinkingOption.LOW_THINKING, CutAnswer.lower(null));
	}

	@Test
	void noLowerLevelNoRetry() {
		assertNull(CutAnswer.lower(ChatModelThinkingOption.LOW_THINKING));
		assertNull(CutAnswer.lower(ChatModelThinkingOption.NO_THINKING), "no portable way to ask for no thinking");
	}

	@Test
	void theMessagesKeepTheirIdsForTheirTranslations() {
		assertEquals(CutAnswer.writtenAgainNote().getId(), CutAnswer.writtenAgainNote().getId());
		assertEquals(CutAnswer.incompleteWarning().getId(), CutAnswer.incompleteWarning().getId());
		assertNotEquals(CutAnswer.writtenAgainNote().getId(), CutAnswer.incompleteWarning().getId());
	}
}
