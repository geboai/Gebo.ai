/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.openchat.pipeline;

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

/** Pins the additional rules slot of the open chat prompts, and that nothing added leaves no trace. */
class OpenChatPromptsAdditionalRulesSlotTest {
	private static final String SLOT_NAME = "additionalRulesSlot";

	private static String resource(String file) throws Exception {
		try (InputStream in = OpenChatPromptsAdditionalRulesSlotTest.class.getResourceAsStream("/open-chat-prompt-library/en/" + file)) {
			assertNotNull(in, file);
			return new String(in.readAllBytes(), StandardCharsets.UTF_8);
		}
	}

	@ParameterizedTest
	@ValueSource(strings = { "open-chat-coordinator-agent-system-prompt.txt", "open-chat-answer-writer-agent-system-prompt.txt" })
	void aPromptNamesTheSlotOnceAndRendersWithItEmpty(String file) throws Exception {
		String system = resource(file);
		assertEquals(2, system.split("\\{" + SLOT_NAME + "\\}", -1).length, file);
		GPromptTemplateConfig prompt = new GPromptTemplateConfig();
		prompt.setPromptUse("a-use");
		prompt.setSystemPromptTemplate(system);
		Map<String, Object> params = new HashMap<String, Object>();
		for (String placeholder : prompt.getPlaceholders().keySet()) {
			params.put(placeholder, "");
		}

		String rendered = new PromptTemplate(system).render(params);

		assertFalse(rendered.contains(SLOT_NAME), file);
	}

	@Test
	void theCoordinatorClosesWithTheRulesAndItsOutputContractNamesTheToolCall() throws Exception {
		String system = resource("open-chat-coordinator-agent-system-prompt.txt");

		assertTrue(system.contains("When you do not call a tool instead, your output MUST conform"));
		assertTrue(system.contains("Now call a tool if the rules below apply, otherwise produce the routing plan."));
		assertTrue(system.stripTrailing().endsWith("{" + SLOT_NAME + "}"), "what is read last counts most");
	}
}
