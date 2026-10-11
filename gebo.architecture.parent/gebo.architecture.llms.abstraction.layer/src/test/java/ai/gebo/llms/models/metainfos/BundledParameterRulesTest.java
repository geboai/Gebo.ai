/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.models.metainfos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.EncodedResource;

import ai.gebo.architecture.utils.GeboYamlPropertySourceFactory;
import ai.gebo.llms.models.metainfos.ModelParameterRules.Sampling;
import ai.gebo.llms.models.metainfos.config.ModelsLibraryBundleConfig;
import ai.gebo.llms.models.metainfos.config.ModelsLibraryConfig;
import ai.gebo.llms.models.metainfos.impl.GModelsLibraryDaoImpl;

/**
 * Pins the parameter rules of the bundled {@code models.yml} as Spring binds them
 * through the same YAML property source the library is loaded with: the enum, the
 * lists, and which rule each model of the presets gets.
 */
class BundledParameterRulesTest {

	private static GModelsLibraryDaoImpl bundledLibrary() throws Exception {
		StandardEnvironment environment = new StandardEnvironment();
		environment.getPropertySources().addFirst(new GeboYamlPropertySourceFactory().createPropertySource(null,
				new EncodedResource(new ClassPathResource("bundle-models/models.yml"))));
		ModelsLibraryBundleConfig bundle = Binder.get(environment)
				.bind("ai.gebo.models.bundle.library", ModelsLibraryBundleConfig.class).get();
		return new GModelsLibraryDaoImpl(new ModelsLibraryConfig(), bundle);
	}

	@Test
	void theBundledRulesBindAndCoverThePresetModels() throws Exception {
		GModelsLibraryDaoImpl library = bundledLibrary();

		ModelParameterRules sol = library.findParameterRules("openai", "gpt-6.1-sol");
		assertNotNull(sol);
		assertEquals(Sampling.REJECTED, sol.getSampling());
		assertEquals(List.of("low", "medium", "high", "xhigh", "max"), sol.getReasoningEfforts());

		ModelParameterRules luna = library.findParameterRules("openai", "gpt-6-luna");
		assertEquals(Sampling.WITHOUT_REASONING, luna.getSampling());
		assertTrue(luna.acceptsEffort("none"), "none is a string, not a YAML null");

		assertEquals(List.of("minimal", "low", "medium", "high"),
				library.findParameterRules("openai", "gpt-5").getReasoningEfforts());
		assertFalse(library.findParameterRules("openai", "gpt-5-mini").knowsEfforts(), "the family rule");
		assertEquals(Sampling.REJECTED, library.findParameterRules("openai", "o4-mini").getSampling());
		assertNull(library.findParameterRules("openai", "gpt-4.1"), "gpt-4.1 takes sampling: no rule");

		assertEquals(List.of("low", "medium", "high"),
				library.findParameterRules("groq", "openai/gpt-oss-20b").getReasoningEfforts());
		assertTrue(library.findParameterRules("groq", "qwen/qwen3.8-27b").acceptsEffort("none"));
		assertNull(library.findParameterRules("openrouter.ai", "openai/gpt-oss-20b"),
				"the groq rule is groq's only");
		assertFalse(library.findParameterRules("openai", "gpt-6.1-sol").getSource().isBlank());

		assertFalse(library.findParameterRules("xai", "grok-4.7").acceptsEffort("none"),
				"grok reasoning cannot be disabled");
		assertTrue(library.findParameterRules("xai", "grok-4.3").acceptsEffort("none"), "grok-4.3 can stop reasoning");
		assertFalse(library.findParameterRules("mistralai", "zai-glm-5-3").acceptsEffort("none"));
		assertTrue(library.findParameterRules("mistralai", "mistral-small-latest").acceptsEffort("high"));
		assertNull(library.findParameterRules("mistralai", "ministral-8b-latest"), "undocumented: no rule");
		assertTrue(library.findParameterRules("nvidia", "nvidia/nemotron-3-super-120b-a12b").acceptsEffort("none"));
		assertEquals("passage", library.findParameterRules("nvidia", "nvidia/nemotron-3-embed-1b").getDocumentInputType());
		assertEquals("pcm",
				library.findParameterRules("openrouter.ai", "google/gemini-3.1-flash-tts-preview").getSpeechFormat());
	}
}
