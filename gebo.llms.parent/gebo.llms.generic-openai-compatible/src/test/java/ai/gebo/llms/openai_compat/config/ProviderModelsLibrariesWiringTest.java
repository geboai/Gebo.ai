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
						GModelsLibraryDaoImpl.class)
				.run(context -> {
					assertThat(context).hasNotFailed();
					IGModelsLibraryDao library = context.getBean(IGModelsLibraryDao.class);
					assertThat(context.getBean(GroqModelsLibrary.class).getParameterRules()).hasSize(2);
					assertThat(context.getBean(NvidiaModelsLibrary.class).getModelsInfo()).hasSize(10);
					assertThat(library.findParameterRules("groq", "openai/gpt-oss-20b").getReasoningEfforts())
							.containsExactly("low", "medium", "high");
					assertThat(library.findParameterRules("xai", "grok-4.3").acceptsEffort("none")).isTrue();
					assertThat(library.findParameterRules("openrouter.ai", "google/gemini-3.1-flash-tts-preview")
							.getSpeechFormat()).isEqualTo("pcm");
					assertThat(library.findByProviderIdAndModelId("nvidia", "bigcode/starcoder2-7b")).isNotNull();
				});
	}
}
