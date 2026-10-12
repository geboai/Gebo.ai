/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.chat.abstraction.layer.services.impl;

import ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig.ChatModelThinkingOption;
import ai.gebo.model.GUserMessage;

/**
 * An answer the model was cut in, its generated tokens used up. When its reasoning took
 * them (more reasoning written than answer) the answer is written again once, with the
 * thinking level one step lower; otherwise (a model looping, or no lower level to ask)
 * the user is warned the answer is incomplete.
 */
public final class CutAnswer {
	/** The finish reasons of a cut answer: OpenAI-style providers and Ollama, Anthropic, Google. */
	private static final String[] CUT_REASONS = { "LENGTH", "MAX_TOKENS" };

	/** What parts, while it streams, the cut answer from the one written again. */
	public static final String SEPARATOR = "\n\n---\n\n";

	private CutAnswer() {
	}

	/** Whether the model stopped because its generated tokens ran out. */
	public static boolean isCut(String finishReason) {
		if (finishReason == null) {
			return false;
		}
		final String reason = finishReason.trim();
		for (String cut : CUT_REASONS) {
			if (cut.equalsIgnoreCase(reason)) {
				return true;
			}
		}
		return false;
	}

	/** Whether the reasoning took the budget: more of it written than of the answer. */
	public static boolean reasoningTookTheBudget(long reasoningChars, long answerChars) {
		return reasoningChars > answerChars;
	}

	/**
	 * The thinking level one step below the configured one, null when there is none lower
	 * that can be asked: no thinking has no portable spelling across providers.
	 */
	public static ChatModelThinkingOption lower(ChatModelThinkingOption configured) {
		if (configured == null) {
			return ChatModelThinkingOption.LOW_THINKING;
		}
		switch (configured) {
		case HIGH_THINKING:
			return ChatModelThinkingOption.MEDIUM_THINKING;
		case MEDIUM_THINKING:
		case AUTO:
			return ChatModelThinkingOption.LOW_THINKING;
		case LOW_THINKING:
		case NO_THINKING:
		default:
			return null;
		}
	}

	/** The note telling the user the answer is written again. */
	public static GUserMessage writtenAgainNote() {
		return GUserMessage.infoMessage("Answer written again",
				"The model used up the tokens it can generate reasoning, so the answer was cut: it is written again with less reasoning.");
	}

	/** The warning telling the user the answer is incomplete. */
	public static GUserMessage incompleteWarning() {
		return GUserMessage.warnMessage("Answer incomplete",
				"The model reached the maximum tokens it can generate: the answer was cut.");
	}
}
