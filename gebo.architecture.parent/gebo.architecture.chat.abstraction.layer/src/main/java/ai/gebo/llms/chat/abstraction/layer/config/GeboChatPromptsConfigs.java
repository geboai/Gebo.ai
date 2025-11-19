/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.llms.chat.abstraction.layer.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import ai.gebo.llms.chat.abstraction.layer.model.GPromptConfig;
import lombok.Data;

/**
 * Gebo.ai comment agent
 *
 * Configuration class for Gebo chat prompts. This class is used to load and
 * manage configuration properties related to chat prompts.
 */
@Configuration
@ConfigurationProperties(value = "ai.gebo.chat")
@Data
public class GeboChatPromptsConfigs {
	// List of default prompt configurations
	private List<GPromptConfig> promptDefaults = new ArrayList<GPromptConfig>();

	// List of prompt template wizard configurations
	private List<GPromptConfig> promptTemplateWizardConfigs = new ArrayList<GPromptConfig>();

	// Default configuration for the prompt template wizard
	private GPromptConfig defaultPromptTemplateWizardConfig = new GPromptConfig();
	// Summarize chat description prompt
	private GPromptConfig summarizeChatDescriptionPrompt = new GPromptConfig();

	/**
	 * Constructor for GeboChatPromptsConfigs. Initializes the default prompt
	 * template wizard configuration.
	 */
	public GeboChatPromptsConfigs() {
		// Initialize the default prompt template with a code and prompt message
		defaultPromptTemplateWizardConfig.setCode("prompt-template-wizard-default");
		defaultPromptTemplateWizardConfig.setPrompt("Write a chat prompt to assist the user on its tasks ");
		summarizeChatDescriptionPrompt.setCode("summarize-chat-description");
		summarizeChatDescriptionPrompt.setPrompt("You are a summarization assistant.\n"
				+ "Given a single user request sent to a chatbot, generate a short, meaningful description of the chat.\n"
				+ "The description must capture the core intent of the user request, without adding details that are not explicitly stated.\n"
				+ "Write it in one short sentence, in a neutral and professional tone.\n"
				+ "Avoid questions: produce a concise descriptive label.\n"
				+ "Do not include any system or assistant messages.\n"
				+ "Output only the final description, nothing else.");
	}

	

}