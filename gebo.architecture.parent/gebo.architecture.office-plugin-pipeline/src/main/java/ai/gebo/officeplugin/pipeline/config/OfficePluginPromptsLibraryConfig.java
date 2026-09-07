/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.officeplugin.pipeline.config;

import java.util.List;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import ai.gebo.architecture.ai.model.GPromptTemplateLibraryReference;
import ai.gebo.architecture.ai.service.IGStaticPromptUseInfoProvider;
import ai.gebo.architecture.ai.service.IGStaticPromptsProvider;
import ai.gebo.architecture.ai.service.PromptTemplateProvidersImplementation;
import ai.gebo.architecture.utils.GeboYamlPropertySourceFactory;
import ai.gebo.llms.chat.abstraction.layer.config.GeboOverriddenPromptsLibrary;
import lombok.Data;

/**
 * Prompt library for the office-assistant pipeline: the document-editor-aware
 * query rewrite, the office coordinator prompt and the office report/answer writer
 * prompt (which knows the {@code <GEBO-DOCUMENT>} escape). Mirrors
 * {@code StandardAgentsPromptsLibraryConfig}.
 */
@ConditionalOnProperty(prefix = "ai.gebo.officeplugin", name = "enabled", havingValue = "true")
@Configuration
@ConfigurationProperties(value = "ai.gebo.officeplugin.prompts")
@Data
@PropertySource(value = "classpath:/office-prompt-library/office-prompt-library.yml", factory = GeboYamlPropertySourceFactory.class)
public class OfficePluginPromptsLibraryConfig {

	public static final String OFFICE_QUERY_REWRITING_PROMPT = "office-assistant-query-rewriting-prompt";
	public static final String OFFICE_COORDINATOR_AGENT_PROMPT = "office-assistant-coordinator-agent-prompt";
	public static final String OFFICE_REPORT_WRITER_AGENT_PROMPT = "office-assistant-report-writer-agent-prompt";

	private List<GPromptTemplateLibraryReference> library = null;

	@Bean
	public IGStaticPromptsProvider officePluginPromptsProvider(GeboOverriddenPromptsLibrary overridenLibrary) {
		return new PromptTemplateProvidersImplementation(this, library, overridenLibrary.getLibrary());
	}

	@Bean
	public IGStaticPromptUseInfoProvider officePluginPromptsUseInfoProvider(
			GeboOverriddenPromptsLibrary overridenLibrary) {
		return new PromptTemplateProvidersImplementation(this, library, overridenLibrary.getLibrary());
	}
}
