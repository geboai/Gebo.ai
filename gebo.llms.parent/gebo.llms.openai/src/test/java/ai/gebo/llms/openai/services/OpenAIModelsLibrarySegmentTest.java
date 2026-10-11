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
	}
}
