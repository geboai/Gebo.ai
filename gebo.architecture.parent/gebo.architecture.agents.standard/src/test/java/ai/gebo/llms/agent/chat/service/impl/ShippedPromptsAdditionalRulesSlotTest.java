/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.agent.chat.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.ai.chat.prompt.PromptTemplate;

import ai.gebo.architecture.ai.model.GPromptTemplateConfig;

/** Pins which shipped prompts name the additional rules slot, and that nothing added leaves no trace. */
class ShippedPromptsAdditionalRulesSlotTest {
	private static final String SLOT_NAME = "additionalRulesSlot";

	private static String resource(String file) throws Exception {
		try (InputStream in = ShippedPromptsAdditionalRulesSlotTest.class
				.getResourceAsStream("/agents-prompt-library/en/" + file)) {
			assertNotNull(in, file);
			return new String(in.readAllBytes(), StandardCharsets.UTF_8);
		}
	}

	private static GPromptTemplateConfig prompt(String system) {
		GPromptTemplateConfig prompt = new GPromptTemplateConfig();
		prompt.setPromptUse("a-use");
		prompt.setSystemPromptTemplate(system);
		return prompt;
	}

	@ParameterizedTest
	@ValueSource(strings = { "controller-coordinator-agent-system-prompt.txt", "default-chat-agent-system-prompt.txt",
			"pure-chat-agent-system-prompt.txt", "report-answer-writer-agent-system-prompt.txt",
			"tool-calling-agent-system-prompt.txt" })
	void anAgentPromptNamesTheSlotOnceAndRendersWithItEmpty(String file) throws Exception {
		String system = resource(file);
		assertEquals(2, system.split("\\{" + SLOT_NAME + "\\}", -1).length, file);
		Map<String, Object> params = new HashMap<String, Object>();
		for (String placeholder : prompt(system).getPlaceholders().keySet()) {
			params.put(placeholder, "");
		}

		String rendered = new PromptTemplate(system).render(params);

		assertFalse(rendered.contains(SLOT_NAME), file);
	}

	@Test
	void theCoordinatorClosesWithTheRulesAndItsOutputContractGivesThemWay() throws Exception {
		String system = resource("controller-coordinator-agent-system-prompt.txt");

		assertFalse(system.contains("Your only output"), "the added rules could not win against it");
		assertTrue(system.contains("When you do not call a tool instead, your output MUST conform"));
		assertTrue(system.contains("Now call a tool if the rules below apply, otherwise produce the routing plan."));
		assertTrue(system.stripTrailing().endsWith("{" + SLOT_NAME + "}"), "what is read last counts most");
	}

	@Test
	void theEvidenceExtractorHasNoToolsSoItHasNoSlot() throws Exception {
		assertFalse(resource("report-evidence-extractor-system-prompt.txt").contains(SLOT_NAME));
	}

	@Test
	void aLoopPromptNamingOnlyTheSlotHasItFilled() {
		assertTrue(AgenticLoopReactiveAgentServiceImpl.namesAnAgentPlaceholder(prompt("{" + SLOT_NAME + "}")));
		assertFalse(AgenticLoopReactiveAgentServiceImpl.namesAnAgentPlaceholder(prompt("Do this.")));
	}
}
