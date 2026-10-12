/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.openchat.pipeline.config;

import java.util.List;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
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
import ai.gebo.llms.chat.abstraction.layer.config.GeboOverriddenPromptsLibrary;
import lombok.Data;

/**
 * Prompt library for the open-chat pipeline: the open-chat coordinator prompt and
 * the open-chat free-response answer-writer prompt. Mirrors
 * {@code StandardAgentsPromptsLibraryConfig} (and {@code OfficePluginPromptsLibraryConfig},
 * which now lives in the pro platform).
 * <p>
 * The two prompts deliberately DIVERGE from the standard network's prompts: the
 * writer answers freely and conversationally on any topic and does not force
 * evidence organization, citations, traceability or a deliverable-format template;
 * the coordinator dispatches the external searchers only when the question actually
 * needs fresh external information, and states there is no internal company
 * knowledge base.
 */
@ConditionalOnProperty(prefix = "ai.gebo.openchat", name = "enabled", havingValue = "true", matchIfMissing = true)
@Configuration
@ConfigurationProperties(value = "ai.gebo.openchat.prompts")
@Data
@PropertySource(value = "classpath:/open-chat-prompt-library/open-chat-prompt-library.yml", factory = GeboYamlPropertySourceFactory.class)
public class OpenChatPromptsLibraryConfig {

	public static final String OPEN_CHAT_COORDINATOR_AGENT_PROMPT = "open-chat-coordinator-agent-prompt";
	public static final String OPEN_CHAT_ANSWER_WRITER_AGENT_PROMPT = "open-chat-answer-writer-agent-prompt";

	private List<GPromptTemplateLibraryReference> library = null;
	private List<GPromptUseInfo> uses = null;

	@Bean
	public IGStaticPromptsProvider openChatPromptsProvider(GeboOverriddenPromptsLibrary overridenLibrary) {
		return new PromptTemplateProvidersImplementation(this, library, overridenLibrary.getLibrary(), uses);
	}

	@Bean
	public IGStaticPromptUseInfoProvider openChatPromptsUseInfoProvider(GeboOverriddenPromptsLibrary overridenLibrary) {
		return new PromptTemplateProvidersImplementation(this, library, overridenLibrary.getLibrary(), uses);
	}
}
