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

/**
 * Gebo.ai comment agent
 *
 * Configuration class for Gebo chat prompts. This class is used to load 
 * and manage configuration properties related to chat prompts.
 */
@Configuration
@ConfigurationProperties(value = "ai.gebo.chat")
public class GeboChatPromptsConfigs {
    // List of default prompt configurations
    private List<GPromptConfig> promptDefaults = new ArrayList<GPromptConfig>();
    
    // List of prompt template wizard configurations
    private List<GPromptConfig> promptTemplateWizardConfigs = new ArrayList<GPromptConfig>();
    
    // Default configuration for the prompt template wizard
    private GPromptConfig defaultPromptTemplateWizardConfig = new GPromptConfig();

    /**
     * Constructor for GeboChatPromptsConfigs. Initializes the default prompt
     * template wizard configuration.
     */
    public GeboChatPromptsConfigs() {
        // Initialize the default prompt template with a code and prompt message
        defaultPromptTemplateWizardConfig.setCode("prompt-template-wizard-default");
        defaultPromptTemplateWizardConfig.setPrompt(
                "Write a chat prompt to assist the user on its tasks ");
    }

    /**
     * Gets the default prompt configurations.
     * 
     * @return a list of GPromptConfig representing default prompts.
     */
    public List<GPromptConfig> getPromptDefaults() {
        return promptDefaults;
    }

    /**
     * Sets the default prompt configurations.
     * 
     * @param promptDefaults List of GPromptConfig to set as defaults.
     */
    public void setPromptDefaults(List<GPromptConfig> promptDefaults) {
        this.promptDefaults = promptDefaults;
    }

    /**
     * Gets the prompt template wizard configurations.
     * 
     * @return a list of GPromptConfig for prompt template wizards.
     */
    public List<GPromptConfig> getPromptTemplateWizardConfigs() {
        return promptTemplateWizardConfigs;
    }

    /**
     * Sets the prompt template wizard configurations.
     * 
     * @param promptTemplateWizardConfigs List of GPromptConfig to set for template wizards.
     */
    public void setPromptTemplateWizardConfigs(List<GPromptConfig> promptTemplateWizardConfigs) {
        this.promptTemplateWizardConfigs = promptTemplateWizardConfigs;
    }

    /**
     * Gets the default prompt template wizard configuration.
     * 
     * @return the default GPromptConfig for the template wizard.
     */
    public GPromptConfig getDefaultPromptTemplateWizardConfig() {
        return defaultPromptTemplateWizardConfig;
    }

    /**
     * Sets the default prompt template wizard configuration.
     * 
     * @param promptTemplateWizardConfig the GPromptConfig to set as default for template wizards.
     */
    public void setDefaultPromptTemplateWizardConfig(GPromptConfig promptTemplateWizardConfig) {
        this.defaultPromptTemplateWizardConfig = promptTemplateWizardConfig;
    }

}