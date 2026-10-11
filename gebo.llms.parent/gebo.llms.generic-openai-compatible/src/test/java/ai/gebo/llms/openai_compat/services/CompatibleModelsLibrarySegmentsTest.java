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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import ai.gebo.llms.models.metainfos.GProviderModelsLibrary;
import ai.gebo.llms.openai_compat.config.GroqModelsLibrary;
import ai.gebo.llms.openai_compat.config.NvidiaModelsLibrary;
import ai.gebo.llms.openai_compat.config.OpenRouterModelsLibrary;
import ai.gebo.llms.openai_compat.config.XaiModelsLibrary;
import ai.gebo.llms.models.metainfos.config.ModelsLibraryConfig;
import ai.gebo.llms.models.metainfos.impl.GModelsLibraryDaoImpl;

/**
 * Pins the models library segments this module ships for the OpenAI compatible
 * providers it implements (groq, xai, nvidia, openrouter.ai), as the abstraction layer
 * merges them.
 */
class CompatibleModelsLibrarySegmentsTest {

	@Test
	void eachProviderSegmentCarriesItsRules() throws Exception {
		GModelsLibraryDaoImpl library = new GModelsLibraryDaoImpl(new ModelsLibraryConfig(),
				List.of(GProviderModelsLibrary.load(GroqModelsLibrary.YML, GroqModelsLibrary.PREFIX, GroqModelsLibrary.class),
						GProviderModelsLibrary.load(NvidiaModelsLibrary.YML, NvidiaModelsLibrary.PREFIX,
								NvidiaModelsLibrary.class),
						GProviderModelsLibrary.load(OpenRouterModelsLibrary.YML, OpenRouterModelsLibrary.PREFIX,
								OpenRouterModelsLibrary.class),
						GProviderModelsLibrary.load(XaiModelsLibrary.YML, XaiModelsLibrary.PREFIX, XaiModelsLibrary.class)));

		assertEquals(List.of("low", "medium", "high"),
				library.findParameterRules("groq", "openai/gpt-oss-20b").getReasoningEfforts());
		assertTrue(library.findParameterRules("groq", "qwen/qwen3.8-27b").acceptsEffort("none"));
		assertNull(library.findParameterRules("openrouter.ai", "openai/gpt-oss-20b"), "groq's rule is groq's only");
		assertFalse(library.findParameterRules("xai", "grok-4.7").acceptsEffort("none"));
		assertTrue(library.findParameterRules("xai", "grok-4.3").acceptsEffort("none"));
		assertTrue(library.findParameterRules("nvidia", "nvidia/nemotron-3-super-120b-a12b").acceptsEffort("none"));
		assertEquals("passage", library.findParameterRules("nvidia", "nvidia/nemotron-3-embed-1b").getDocumentInputType());
		assertNotNull(library.findByProviderIdAndModelId("nvidia", "bigcode/starcoder2-15b"), "nvidia's models info");
		assertEquals("pcm",
				library.findParameterRules("openrouter.ai", "google/gemini-3.1-flash-tts-preview").getSpeechFormat());
	}
}
