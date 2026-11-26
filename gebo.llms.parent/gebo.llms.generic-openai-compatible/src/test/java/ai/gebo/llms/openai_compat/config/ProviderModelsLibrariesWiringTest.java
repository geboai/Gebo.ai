/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.openai_compat.config;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.Configuration;

import ai.gebo.llms.models.metainfos.IGModelsLibraryDao;
import ai.gebo.llms.models.metainfos.config.ModelsLibraryConfig;
import ai.gebo.llms.models.metainfos.impl.GModelsLibraryDaoImpl;

/**
 * Pins the Spring wiring of the provider libraries: each bean binds its own yml under its
 * own prefix, and the models library DAO collects all of them.
 */
class ProviderModelsLibrariesWiringTest {

	@Configuration
	@EnableConfigurationProperties
	static class Enabling {
	}

	@Test
	void theDaoCollectsEveryProviderLibraryBean() {
		new ApplicationContextRunner()
				.withUserConfiguration(Enabling.class, ModelsLibraryConfig.class, NvidiaModelsLibrary.class,
						GroqModelsLibrary.class, XaiModelsLibrary.class, OpenRouterModelsLibrary.class,
						RegoloModelsLibrary.class, GenericOpenAIModelsLibrary.class, VllmModelsLibrary.class,
						SubconsciousModelsLibrary.class, AwanllmModelsLibrary.class, LlmaxModelsLibrary.class,
						CheapestInferenceModelsLibrary.class, ProfundoaiModelsLibrary.class, GModelsLibraryDaoImpl.class)
				.run(context -> {
					assertThat(context).hasNotFailed();
					// regolo.ai has models info and no rule; vllm's yml is comments only, its library empty
					assertThat(context.getBean(RegoloModelsLibrary.class).getParameterRules()).isEmpty();
					assertThat(context.getBean(VllmModelsLibrary.class).getModelsInfo()).isEmpty();
					IGModelsLibraryDao library = context.getBean(IGModelsLibraryDao.class);
					assertThat(context.getBean(GroqModelsLibrary.class).getParameterRules()).hasSize(3);
					// Groq's api tells nothing of the inputs: its vision models are declared by rule,
					// one of them a rule stating the input modalities only
					assertThat(library.findInputModalities("groq", "qwen/qwen3.8-27b")).containsExactly(
							ai.gebo.llms.abstraction.layer.model.GModelInputModality.TEXT,
							ai.gebo.llms.abstraction.layer.model.GModelInputModality.IMAGE);
					assertThat(library.findInputModalities("groq", "meta-models/muse-spark-open")).containsExactly(
							ai.gebo.llms.abstraction.layer.model.GModelInputModality.TEXT,
							ai.gebo.llms.abstraction.layer.model.GModelInputModality.IMAGE);
					assertThat(library.findParameterRules("groq", "meta-models/muse-spark-open")).isNull();
					assertThat(library.findInputModalities("groq", "openai/gpt-oss-20b")).isNull();
					assertThat(library.findByProviderIdAndModelId("xai", "grok-4.5").getInputModalities())
							.containsExactly(ai.gebo.llms.abstraction.layer.model.GModelInputModality.TEXT,
									ai.gebo.llms.abstraction.layer.model.GModelInputModality.IMAGE);
					// nvidia's api gives no context: its library does
					assertThat(library.findByProviderIdAndModelId("nvidia", "nvidia/nemotron-3.5-super-vl-120b-a12b")
							.getContextLength()).isEqualTo(1000000);
					assertThat(context.getBean(NvidiaModelsLibrary.class).getModelsInfo()).hasSize(9);
					assertThat(library.findParameterRules("groq", "openai/gpt-oss-20b").getReasoningEfforts())
							.containsExactly("low", "medium", "high");
					assertThat(library.findParameterRules("xai", "grok-4.3").acceptsEffort("none")).isTrue();
					assertThat(library.findParameterRules("openrouter.ai", "google/gemini-3.1-flash-tts-preview")
							.getSpeechFormat()).isEqualTo("pcm");
					assertThat(library.findByProviderIdAndModelId("nvidia", "bigcode/starcoder2-15b")).isNotNull();
				});
	}
}
