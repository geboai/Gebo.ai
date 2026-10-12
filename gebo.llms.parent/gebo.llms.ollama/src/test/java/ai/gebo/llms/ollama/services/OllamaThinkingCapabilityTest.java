/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.ollama.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

/**
 * Pins how an Ollama model's /api/show answer decides the think sent: none to a model
 * whose capabilities lack thinking, a named level only when the model lists it.
 */
class OllamaThinkingCapabilityTest {

	@Test
	void theCapabilitiesSayWhetherTheModelThinks() {
		assertTrue(OllamaChatModelConfigurationSupportService
				.thinkingCapability(Map.of("capabilities", List.of("completion", "tools", "thinking"))));
		assertFalse(OllamaChatModelConfigurationSupportService
				.thinkingCapability(Map.of("capabilities", List.of("completion", "vision"))));
		assertNull(OllamaChatModelConfigurationSupportService.thinkingCapability(Map.of()), "unknown");
		assertNull(OllamaChatModelConfigurationSupportService.thinkingCapability(null));
	}

	@Test
	void onlyTheNamedLevelsAreLevels() {
		assertEquals(List.of("low", "medium", "high"), OllamaChatModelConfigurationSupportService
				.thinkingLevels(Map.of("thinking", Map.of("values", List.of("low", "medium", "high")))));
		assertEquals(List.of(), OllamaChatModelConfigurationSupportService
				.thinkingLevels(Map.of("thinking", Map.of("values", List.of(true, false)))), "booleans are no levels");
		assertEquals(List.of(), OllamaChatModelConfigurationSupportService.thinkingLevels(Map.of()));
	}
}
