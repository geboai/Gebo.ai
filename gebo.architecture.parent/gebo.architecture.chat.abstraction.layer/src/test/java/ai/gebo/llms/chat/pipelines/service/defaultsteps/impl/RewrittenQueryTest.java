/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.chat.pipelines.service.defaultsteps.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * Pins the rewritten question taken from the request understanding: a model copying
 * the prompt's placeholder instead of filling it leaves the user's own question in
 * place (seen live with a small local model: the ranker and its irrelevance filter
 * received "&lt;the rewritten user question (no explanations, no JSON, no extra
 * text)&gt;" as the question).
 */
class RewrittenQueryTest {

	@Test
	void aFilledFieldIsTheRewrittenQuestion() {
		assertEquals("How is the Qdrant API key set?",
				DefaultRoutingChatPipelineStepServiceImpl.rewrittenQuery(List.of(" How is the Qdrant API key set? ")));
	}

	@Test
	void thePromptsPlaceholderIsNoRewrittenQuestion() {
		assertNull(DefaultRoutingChatPipelineStepServiceImpl.rewrittenQuery(
				List.of("<the rewritten user question (no explanations, no JSON, no extra text)>")));
	}

	@Test
	void aMissingOrBlankFieldIsNoRewrittenQuestion() {
		assertNull(DefaultRoutingChatPipelineStepServiceImpl.rewrittenQuery(null));
		assertNull(DefaultRoutingChatPipelineStepServiceImpl.rewrittenQuery(List.of()));
		assertNull(DefaultRoutingChatPipelineStepServiceImpl.rewrittenQuery(Arrays.asList((String) null)));
		assertNull(DefaultRoutingChatPipelineStepServiceImpl.rewrittenQuery(List.of("   ")));
	}

	@Test
	void aQuestionMerelyContainingAngleBracketsIsKept() {
		assertEquals("What does List<String> mean?",
				DefaultRoutingChatPipelineStepServiceImpl.rewrittenQuery(List.of("What does List<String> mean?")));
	}
}
