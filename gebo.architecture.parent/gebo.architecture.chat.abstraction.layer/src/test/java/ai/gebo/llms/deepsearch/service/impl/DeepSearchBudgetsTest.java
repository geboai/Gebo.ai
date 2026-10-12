/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.deepsearch.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.ai.document.Document;

import ai.gebo.architecture.ai.model.ContextContentRequired;
import ai.gebo.architecture.ai.model.GPromptTemplateConfig;
import ai.gebo.llms.abstraction.layer.model.IChatRequestContext;
import ai.gebo.llms.abstraction.layer.model.IChatSessionEntry;
import ai.gebo.llms.abstraction.layer.services.BaseLLMSInvokingService;

/**
 * Pins the values a deep analysis budget subtracts besides the prompt and the
 * consolidation: every value known before the call, never the documents nor the
 * consolidation (the formula takes it on its own).
 */
class DeepSearchBudgetsTest {

	private static IChatRequestContext context() {
		return IChatRequestContext.builder().actualUserRequest("Who is Fohat in the Secret Doctrine?")
				.userLanguage("English").rulesToFollow(List.of("Always answer with a table."))
				.interactions(List.of(IChatSessionEntry.builder().user("Who is Steiner?")
						.assistant("An Austrian esotericist.").build()))
				.consolidatedHistory("The user studies esoteric texts.").documents(List.of(new Document("a book")))
				.build();
	}

	@Test
	void everyValueKnownBeforeTheCallNeverTheDocumentsNorTheConsolidation() {
		final GPromptTemplateConfig prompt = GPromptTemplateConfig.of("You analyse documents.", "{question}",
				"an-analysis");
		final Map<String, Object> known = DeepSearchBudgets.knownValues(prompt,
				Map.of("agentDeliverableCompleteness", "a detailed analysis", IChatRequestContext.DOCUMENTS_PROMPT_PARAM,
						"the batch", BaseLLMSInvokingService.CONSOLIDATED_TEMPLATE_VARIABLE, "the consolidation"),
				context());

		assertEquals("a detailed analysis", known.get("agentDeliverableCompleteness"));
		assertEquals("Who is Fohat in the Secret Doctrine?", known.get(IChatRequestContext.USER_QUESTION_PROMPT_PARAM));
		assertEquals("English", known.get(IChatRequestContext.USER_LANGUAGE_PROMPT_PARAM));
		assertEquals("Always answer with a table.", known.get("rulesToFollow"));
		assertTrue(known.get("chatHistory").toString().contains("An Austrian esotericist."));
		assertEquals("The user studies esoteric texts.", known.get(IChatRequestContext.CONSOLIDATED_HISTORY_PROMPT_PARAM));
		assertFalse(known.containsKey(IChatRequestContext.DOCUMENTS_PROMPT_PARAM), "the documents: what the budget is for");
		assertFalse(known.containsKey(BaseLLMSInvokingService.CONSOLIDATED_TEMPLATE_VARIABLE),
				"the consolidation: given to the formula on its own");
	}

	@Test
	void theHistoryOnlyWhenThePromptTakesIt() {
		final GPromptTemplateConfig prompt = GPromptTemplateConfig.of("You analyse documents.", "{question}",
				"an-analysis");
		prompt.setChatHistory(ContextContentRequired.NOT_REQUIRED);

		final Map<String, Object> known = DeepSearchBudgets.knownValues(prompt, Map.of(), context());

		assertFalse(known.containsKey("chatHistory"));
		assertFalse(known.containsKey(IChatRequestContext.CONSOLIDATED_HISTORY_PROMPT_PARAM));
		assertTrue(known.containsKey(IChatRequestContext.USER_QUESTION_PROMPT_PARAM));
	}
}
