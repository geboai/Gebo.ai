/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.openai.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import org.junit.jupiter.api.Test;

import ai.gebo.llms.abstraction.layer.model.GModelInputModality;
import ai.gebo.llms.models.metainfos.ModelParameterRules;
import ai.gebo.llms.models.metainfos.ModelParameterRules.Sampling;
import ai.gebo.llms.models.metainfos.GProviderModelsLibrary;
import ai.gebo.llms.openai.config.OpenAIModelsLibrary;
import ai.gebo.llms.models.metainfos.config.ModelsLibraryConfig;
import ai.gebo.llms.models.metainfos.impl.GModelsLibraryDaoImpl;

/**
 * Pins the OpenAI segment of the models library this module ships, as the abstraction
 * layer merges it: its models and the rules of its reasoning models.
 */
class OpenAIModelsLibrarySegmentTest {

	private static GModelsLibraryDaoImpl library() throws Exception {
		return new GModelsLibraryDaoImpl(new ModelsLibraryConfig(),
				List.of(GProviderModelsLibrary.load(OpenAIModelsLibrary.YML, OpenAIModelsLibrary.PREFIX, OpenAIModelsLibrary.class)));
	}

	@Test
	void theOpenAISegmentCarriesItsModelsAndRules() throws Exception {
		GModelsLibraryDaoImpl library = library();

		assertNotNull(library.findByProviderIdAndModelId("openai", "gpt-4.1"));
		ModelParameterRules sol = library.findParameterRules("openai", "gpt-6.1-sol");
		assertEquals(Sampling.REJECTED, sol.getSampling());
		assertEquals(List.of("low", "medium", "high", "xhigh", "max"), sol.getReasoningEfforts());
		assertEquals(Sampling.WITHOUT_REASONING, library.findParameterRules("openai", "gpt-6-luna").getSampling());
		assertEquals(List.of("minimal", "low", "medium", "high"),
				library.findParameterRules("openai", "gpt-5").getReasoningEfforts());
		assertFalse(library.findParameterRules("openai", "gpt-5-mini").knowsEfforts(), "the family rule");
		assertEquals(Sampling.REJECTED, library.findParameterRules("openai", "o4-mini").getSampling());
		assertNull(library.findParameterRules("openai", "gpt-4.1"), "gpt-4.1 takes sampling: no rule");

		// the current models' limits, which /v1/models does not give: the context is the
		// most a request may carry as input, the page's maximum of input tokens
		assertEquals(922000, library.findByProviderIdAndModelId("openai", "gpt-6.1-sol").getContextLength());
		assertEquals(922000, library.findByProviderIdAndModelId("openai", "gpt-6.2-astra").getContextLength());
		assertEquals(922000, library.findByProviderIdAndModelId("openai", "gpt-5.6-sol").getContextLength());
		assertEquals("2026-12-11", library.findByProviderIdAndModelId("openai", "o3").getRetirementDate());
		assertEquals(Boolean.TRUE, library.findByProviderIdAndModelId("openai", "gpt-6.1-sol").getSupportsFunctionCalls(),
				"tools through the Responses api Gebo calls it with");
		assertEquals(32768, library.findByProviderIdAndModelId("openai", "gpt-4.1-mini").getMaxOutputToken());
		assertEquals(Boolean.TRUE, library.findByProviderIdAndModelId("openai", "gpt-6-luna").getSupportsFunctionCalls(),
				"tools at every effort through the Responses api");
	}

	@Test
	void aVersion6ModelTheLibraryDoesNotNameSendsNoTemperature() throws Exception {
		GModelsLibraryDaoImpl library = library();

		// gpt-6.2-sol refused the temperature 0 of its configuration's test call
		for (String id : List.of("gpt-6.2-sol", "gpt-6.2-sol-2026-11-20", "gpt-6.3-luna", "gpt-6")) {
			ModelParameterRules rules = library.findParameterRules("openai", id);
			assertNotNull(rules, id + ": covered by the family rule");
			assertEquals(Sampling.REJECTED, rules.getSampling(), id + ": no temperature, no top_p");
			assertFalse(rules.samplingAllowed(false), id);
			assertFalse(rules.knowsEfforts(), id + ": its efforts are not known");
		}
		// the models with a page of their own keep their rules, read before the family one
		assertEquals(Sampling.WITHOUT_REASONING, library.findParameterRules("openai", "gpt-6-sol").getSampling());
		assertEquals(Sampling.WITHOUT_REASONING, library.findParameterRules("openai", "gpt-6-luna-2026-09-01").getSampling());
		assertEquals(List.of("low", "medium", "high", "xhigh", "max"),
				library.findParameterRules("openai", "gpt-6.1-sol").getReasoningEfforts());
	}

	@Test
	void theFastSetupPresetsTakeTheirOwnRulesBeforeTheFamilyOne() throws Exception {
		GModelsLibraryDaoImpl library = library();

		for (String id : List.of("gpt-5.2", "gpt-5.2-2025-12-11")) {
			ModelParameterRules rules = library.findParameterRules("openai", id);
			assertEquals(Sampling.WITHOUT_REASONING, rules.getSampling(), id + ": temperature at effort none only");
			assertEquals(List.of("none", "low", "medium", "high", "xhigh"), rules.getReasoningEfforts(), id);
		}
		for (String id : List.of("gpt-5.4-mini", "gpt-5.4-mini-2026-03-17")) {
			ModelParameterRules rules = library.findParameterRules("openai", id);
			assertEquals(Sampling.REJECTED, rules.getSampling(), id + ": not named by the guide's sampling rule");
			assertEquals(List.of("none", "low", "medium", "high", "xhigh"), rules.getReasoningEfforts(), id);
		}
		// the other variants stay on the family rule: no effort known, no sampling
		for (String id : List.of("gpt-5.2-pro", "gpt-5.4-nano", "gpt-5.4")) {
			ModelParameterRules rules = library.findParameterRules("openai", id);
			assertEquals(Sampling.REJECTED, rules.getSampling(), id);
			assertFalse(rules.knowsEfforts(), id + ": the family rule");
		}

		// gpt-5.2's page states no maximum of input tokens, gpt-5.4-mini's states 272,000;
		// a snapshot is known as its model is
		assertEquals(400000, library.findByProviderIdAndModelId("openai", "gpt-5.2").getContextLength());
		assertEquals(272000, library.findByProviderIdAndModelId("openai", "gpt-5.4-mini").getContextLength());
		assertEquals(272000,
				library.findByProviderIdAndModelId("openai", "gpt-5.4-mini-2026-03-17").getContextLength());
		for (String id : List.of("gpt-5.2", "gpt-5.4-mini")) {
			assertEquals(128000, library.findByProviderIdAndModelId("openai", id).getMaxOutputToken(), id);
			assertEquals(Boolean.TRUE, library.findByProviderIdAndModelId("openai", id).getSupportsFunctionCalls(), id);
		}
		assertFalse(OpenAiApiRouting.usesResponsesApi("gpt-5.2"), "Chat Completions");
		assertFalse(OpenAiApiRouting.usesResponsesApi("gpt-5.4-mini"), "Chat Completions");
	}

	@Test
	void theModelObjectTellsItsShutdownAndTheWindowACompatibleServerReports() {
		ai.gebo.openai.integration.client.model.OpenAIModel shutting = new ai.gebo.openai.integration.client.model.OpenAIModel();
		shutting.setId("gpt-4o-mini-tts");
		shutting.setShutdown_date("2027-01-06");
		ai.gebo.llms.models.metainfos.ModelMetaInfo meta = ai.gebo.llms.openai.api.utils.impl.GOpenAIApiUtilImpl
				.liveMetaInfos(shutting, "openai");
		assertEquals(Boolean.TRUE, meta.getDeprecated());
		assertEquals("2027-01-06", meta.getRetirementDate());

		ai.gebo.openai.integration.client.model.OpenAIModel vllm = new ai.gebo.openai.integration.client.model.OpenAIModel();
		vllm.setId("Qwen/Qwen3.8-27B");
		vllm.setMax_model_len(131072);
		assertEquals(131072, ai.gebo.llms.openai.api.utils.impl.GOpenAIApiUtilImpl.liveMetaInfos(vllm, "vllm")
				.getContextLength(), "vLLM's max_model_len");

		ai.gebo.openai.integration.client.model.OpenAIModel bare = new ai.gebo.openai.integration.client.model.OpenAIModel();
		bare.setId("gpt-4.1");
		assertNull(ai.gebo.llms.openai.api.utils.impl.GOpenAIApiUtilImpl.liveMetaInfos(bare, "openai"));
	}

	@Test
	void theInputModalitiesOfTheModelsWhoseProviderGivesNoneComeFromTheLibrary() throws Exception {
		GModelsLibraryDaoImpl library = library();
		for (String id : List.of("gpt-6.1-sol", "gpt-6-luna", "gpt-6-sol", "gpt-6-astra", "gpt-5.5", "gpt-5.2",
				"gpt-5.4-mini", "gpt-5", "gpt-4.1", "gpt-4o")) {
			// images from the model's page; PDF files from the file inputs guide, which
			// gives them to the models that take images
			assertEquals(List.of(GModelInputModality.TEXT, GModelInputModality.IMAGE, GModelInputModality.DOCUMENT),
					library.findByProviderIdAndModelId("openai", id).getInputModalities(), id);
		}
	}

	@Test
	void aDatedVariantTakesItsModelsInputModalitiesFromARuleAndKeepsItsParameterRules() throws Exception {
		GModelsLibraryDaoImpl library = library();
		// a snapshot its model's page lists has an entry of its own, a copy of its model's
		assertEquals(400000,
				library.findByProviderIdAndModelId("openai", "gpt-5.2-2025-12-11").getContextLength());
		// a later one the library does not list yet takes its modalities from the rule
		assertNull(library.findByProviderIdAndModelId("openai", "gpt-5.2-2026-12-01"), "no entry of its own");
		assertEquals(List.of(GModelInputModality.TEXT, GModelInputModality.IMAGE, GModelInputModality.DOCUMENT),
				library.findInputModalities("openai", "gpt-5.2-2026-12-01"));
		assertEquals(List.of(GModelInputModality.TEXT, GModelInputModality.IMAGE, GModelInputModality.DOCUMENT),
				library.findInputModalities("openai", "gpt-5.2-2025-12-11"));
		assertEquals(List.of(GModelInputModality.TEXT, GModelInputModality.IMAGE, GModelInputModality.DOCUMENT),
				library.findInputModalities("openai", "gpt-4o-2024-11-20"));
		// the rule telling the modalities never stands in for the parameter rules
		assertEquals(library.findParameterRules("openai", "gpt-5.2").getSampling(),
				library.findParameterRules("openai", "gpt-5.2-2025-12-11").getSampling());
		assertNull(library.findInputModalities("openai", "text-embedding-3-large"));
		assertNull(library.findInputModalities("openai", "gpt-5.2-pro"), "another model, not a dated variant");
	}
}
