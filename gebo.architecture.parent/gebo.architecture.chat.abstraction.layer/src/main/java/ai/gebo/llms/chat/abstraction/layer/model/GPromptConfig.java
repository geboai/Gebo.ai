/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.chat.abstraction.layer.model;

import java.util.UUID;

import ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig;
import ai.gebo.model.base.GBaseObject;
import ai.gebo.model.base.GObjectRef;

/**
 * AI generated comments
 * GPromptConfig class represents the configuration for a prompt used in chat models.
 * It contains various properties that define the characteristics of the prompt.
 */
public class GPromptConfig extends GBaseObject {

    private String prompt = null;
    private String modelProvider = null;
    private String modelName = null;
    private String promptCategory = null;
    private Boolean defaultPrompt = null;
    private Boolean ragPrompt = null;
    private GObjectRef<GBaseChatModelConfig> modelConfigurationReference = null;

    /**
     * Default constructor for GPromptConfig class.
     */
    public GPromptConfig() {

    }

    /**
     * Gets the prompt string.
     * @return the prompt string.
     */
    public String getPrompt() {
        return prompt;
    }

    /**
     * Sets the prompt string.
     * @param prompt the prompt string to set.
     */
    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    /**
     * Gets the model provider.
     * @return the model provider.
     */
    public String getModelProvider() {
        return modelProvider;
    }

    /**
     * Sets the model provider.
     * @param modelProvider the model provider to set.
     */
    public void setModelProvider(String modelProvider) {
        this.modelProvider = modelProvider;
    }

    /**
     * Gets the model name.
     * @return the model name.
     */
    public String getModelName() {
        return modelName;
    }

    /**
     * Sets the model name.
     * @param modelName the model name to set.
     */
    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    /**
     * Gets the reference to the model configuration.
     * @return the model configuration reference.
     */
    public GObjectRef<GBaseChatModelConfig> getModelConfigurationReference() {
        return modelConfigurationReference;
    }

    /**
     * Sets the reference to the model configuration.
     * @param modelConfigurationReference the reference to set.
     */
    public void setModelConfigurationReference(GObjectRef<GBaseChatModelConfig> modelConfigurationReference) {
        this.modelConfigurationReference = modelConfigurationReference;
    }

    /**
     * Gets the prompt category.
     * @return the prompt category.
     */
    public String getPromptCategory() {
        return promptCategory;
    }

    /**
     * Sets the prompt category.
     * @param promptCategory the prompt category to set.
     */
    public void setPromptCategory(String promptCategory) {
        this.promptCategory = promptCategory;
    }

    /**
     * Checks if it is a RAG (Retrieve and Generate) prompt.
     * @return true if it's a RAG prompt, false otherwise.
     */
    public Boolean getRagPrompt() {
        return ragPrompt;
    }

    /**
     * Sets the RAG prompt status.
     * @param ragPrompt true if it's a RAG prompt, false otherwise.
     */
    public void setRagPrompt(Boolean ragPrompt) {
        this.ragPrompt = ragPrompt;
    }

    /**
     * Checks if it is the default prompt.
     * @return true if it's the default prompt, false otherwise.
     */
    public Boolean getDefaultPrompt() {
        return defaultPrompt;
    }

    /**
     * Sets the default prompt status.
     * @param defaultPrompt true to set as the default prompt, false otherwise.
     */
    public void setDefaultPrompt(Boolean defaultPrompt) {
        this.defaultPrompt = defaultPrompt;
    }

    /**
     * Creates a new GPromptConfig instance with a specified prompt.
     * Generates a random UUID code and assigns it to the new instance.
     * @param prompt2 the prompt to set in the new configuration.
     * @return a new instance of GPromptConfig with the specified prompt.
     */
    public static GPromptConfig of(String prompt2) {
        GPromptConfig cfg = new GPromptConfig();
        cfg.setPrompt(prompt2);
        cfg.setCode(UUID.randomUUID().toString());
        return cfg;
    }

}