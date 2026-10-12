/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.openai_compat.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import ai.gebo.llms.abstraction.layer.model.GBaseChatModelChoice;
import ai.gebo.llms.models.metainfos.ModelParameterRules;
import ai.gebo.llms.models.metainfos.ModelParameterRules.Sampling;
import ai.gebo.openrouter.client.model.ModelReasoning;
import ai.gebo.openrouter.client.model.OpenRouterModel;

/**
 * Fed with the shapes openrouter.ai/api/v1/models answered on 2026-10-11:
 * google/gemini-3.8-flash (mandatory reasoning, efforts high, medium, low), its
 * :batch variant (no temperature among its parameters), z-ai/glm-5.3-flash.
 */
class OpenRouterLiveParameterRulesTest {

	static final List<String> GEMINI_PARAMETERS = List.of("include_reasoning", "max_tokens", "reasoning",
			"reasoning_effort", "response_format", "seed", "stop", "structured_outputs", "temperature", "tool_choice",
			"tools", "top_p");

	@Test
	void aMandatoryReasoningModelTakesItsEffortsButNone() {
		OpenRouterModel gemini = new OpenRouterModel();
		gemini.setId("google/gemini-3.8-flash");
		gemini.setSupportedParameters(GEMINI_PARAMETERS);
		ModelReasoning reasoning = new ModelReasoning();
		reasoning.setMandatory(true);
		reasoning.setSupportedEfforts(List.of("high", "medium", "low"));
		gemini.setReasoning(reasoning);

		ModelParameterRules rules = OpenRouterLiveParameterRules.of(gemini, "openrouter.ai");
		assertEquals(List.of("high", "medium", "low"), rules.getReasoningEfforts());
		assertFalse(rules.acceptsEffort(ModelParameterRules.NO_REASONING_EFFORT));
		assertNull(rules.getSampling(), "it takes a temperature: the library decides");
		assertTrue(rules.covers("openrouter.ai", "google/gemini-3.8-flash"));
	}

	@Test
	void aSavedChoiceIsReadBackFromItsJsonShape() {
		Map<String, Object> batch = Map.of("id", "google/gemini-3.8-flash:batch", "supported_parameters",
				List.of("include_reasoning", "max_tokens", "reasoning", "reasoning_effort", "tools"), "reasoning",
				Map.of("mandatory", true, "supported_efforts", List.of("high", "medium", "low")));
		ModelParameterRules rules = OpenRouterLiveParameterRules.of(batch, "openrouter.ai");
		assertEquals(Sampling.REJECTED, rules.getSampling());
		assertEquals(List.of("high", "medium", "low"), rules.getReasoningEfforts());

		Map<String, Object> optional = Map.of("id", "some/model", "supportedParameters", GEMINI_PARAMETERS,
				"reasoning", Map.of("mandatory", false, "supportedEfforts", List.of("high", "low")));
		assertEquals(List.of("high", "low", "none"),
				OpenRouterLiveParameterRules.of(optional, "openrouter.ai").getReasoningEfforts(),
				"turned off with none when not mandatory");
	}

	@Test
	void otherNativeMetaInfosTellNothing() {
		assertNull(OpenRouterLiveParameterRules.of(null, "openrouter.ai"));
		assertNull(OpenRouterLiveParameterRules.of("text", "openrouter.ai"));
		assertNull(OpenRouterLiveParameterRules.of(Map.of("id", "x", "supported_openai_params", List.of("tools")),
				"regolo.ai"), "a LiteLLM card is no OpenRouter model");
		assertNull(OpenRouterLiveParameterRules.of(new GBaseChatModelChoice(), "openrouter.ai"));
	}

	@Test
	void theNonChatModelsStayOutOfTheChatList() {
		for (String id : List.of("whisper-large-v3-turbo", "canopylabs/orpheus-v1-english",
				"meta-llama/llama-prompt-guard-2-86m", "openai/gpt-oss-safeguard-20b", "meta/llama-guard-4-12b",
				"nvidia/llama-3.1-nemoguard-8b-content-safety", "nvidia/nemotron-3.5-content-safety",
				"nvidia/nemotron-4-340b-reward", "nvidia/nemotron-parse-2.0", "nvidia/nvclip",
				"nvidia/ai-synthetic-video-detector", "nvidia/nv-embedqa-mistral-7b-v2")) {
			assertFalse(NonChatModelsTypeFiltrer.chats(id), id);
		}
		for (String id : List.of("qwen/qwen3.8-27b", "llama-3.3-70b-versatile", "minimaxai/minimax-m2.7",
				"nvidia/nemotron-3-super-120b-a12b", "z-ai/glm-5.3", "moonshotai/kimi-k3",
				"nvidia/riva-translate-4b-instruct-v2", "meta/llama-3.2-90b-vision-instruct")) {
			assertTrue(NonChatModelsTypeFiltrer.chats(id), id);
		}
		NonChatModelsTypeFiltrer filter = new NonChatModelsTypeFiltrer();
		assertTrue(filter.isHandlerSupported("chatmodel-groq"));
		assertTrue(filter.isHandlerSupported("chatmodel-nvidia"));
		assertFalse(filter.isHandlerSupported("chatmodel-openrouter"));
	}
}
