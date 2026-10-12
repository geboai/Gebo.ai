/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.abstraction.layer.model;

import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;

/**
 * The answer of a blocking call, every provider alike: its text without reasoning, the
 * reasoning of the call, and why the model stopped. Read from a response the
 * {@code ThinkingNormalizationAdvisor} normalized and whose answer generation was put
 * first.
 *
 * @param answer       the answer text, never null
 * @param thinking     the reasoning of the call's last model round, never null
 * @param finishReason why the model stopped writing
 * @param source       the response it was read from
 */
public record GChatAnswer(String answer, String thinking, String finishReason, ChatResponse source) {

	public GChatAnswer {
		answer = answer != null ? answer : "";
		thinking = thinking != null ? thinking : "";
	}

	/** The answer of a normalized response. */
	public static GChatAnswer of(ChatResponse response) {
		if (response == null) {
			return new GChatAnswer("", "", null, null);
		}
		final Generation result = response.getResult();
		final String answer = result != null && result.getOutput() != null ? result.getOutput().getText() : null;
		final String finishReason = result != null && result.getMetadata() != null
				? result.getMetadata().getFinishReason()
				: null;
		final Object thinking = response.getMetadata() != null
				? response.getMetadata().get(GChatAnswerChunk.THINKING_METADATA)
				: null;
		return new GChatAnswer(answer, thinking instanceof String text ? text : "", finishReason, response);
	}
}
