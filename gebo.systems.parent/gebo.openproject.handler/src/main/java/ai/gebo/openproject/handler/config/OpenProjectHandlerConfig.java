/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.openproject.handler.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import ai.gebo.architecture.ai.model.GPromptTemplateLibraryReference;
import ai.gebo.architecture.ai.model.GPromptUseInfo;
import ai.gebo.architecture.ai.service.IGStaticPromptUseInfoProvider;
import ai.gebo.architecture.ai.service.IGStaticPromptsProvider;
import ai.gebo.architecture.ai.service.PromptTemplateProvidersImplementation;
import ai.gebo.architecture.utils.GeboYamlPropertySourceFactory;
import lombok.Data;

/**
 * The OpenProject prompts library: the prompts the OpenProject search service uses to
 * turn a question into OpenProject searches.
 */
@Configuration
@ConfigurationProperties(value = "ai.gebo.openproject")
@PropertySource(value = "classpath:/openproject-prompts-library/openproject-library.yml", factory = GeboYamlPropertySourceFactory.class)
@Data
public class OpenProjectHandlerConfig {
	private List<GPromptTemplateLibraryReference> library = new ArrayList<GPromptTemplateLibraryReference>();
	private List<GPromptUseInfo> uses = new ArrayList<GPromptUseInfo>();

	@Bean
	protected IGStaticPromptsProvider openprojectPromptsProvider() {
		return new PromptTemplateProvidersImplementation(this, library, List.of(), uses);
	}

	@Bean
	protected IGStaticPromptUseInfoProvider openprojectPromptsUseInfoProvider() {
		return new PromptTemplateProvidersImplementation(this, library, List.of(), uses);
	}
}
