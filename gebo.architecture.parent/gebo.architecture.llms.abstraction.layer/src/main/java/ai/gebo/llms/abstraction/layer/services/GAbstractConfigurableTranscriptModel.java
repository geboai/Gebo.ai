/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.abstraction.layer.services;

import ai.gebo.llms.abstraction.layer.model.GBaseTranscriptModelConfig;
import ai.gebo.llms.abstraction.layer.model.GTranscriptModelType;

/**
 * AI generated comments
 * An abstract class defining a configurable transcript model.
 * 
 * @param <ModelConfig> The configuration class extending GBaseTranscriptModelConfig.
 * @param <ModelObjectType> The model object type that this class will return.
 */
public abstract class GAbstractConfigurableTranscriptModel<ModelConfig extends GBaseTranscriptModelConfig, ModelObjectType>
        implements IGConfigurableTranscriptModel<ModelConfig> {

    /** The configuration object for the model. */
    protected ModelConfig config = null;

    /** The type of transcript model. */
    protected GTranscriptModelType type = null;

    /** The instantiated model object. */
    protected ModelObjectType model = null;

    /** Default constructor. */
    public GAbstractConfigurableTranscriptModel() {

    }

    /**
     * Retrieve the code associated with the model configuration.
     * 
     * @return The code string, or null if the config is not set.
     */
    @Override
    public String getCode() {
        return config != null ? config.getCode() : null;
    }

    /**
     * Retrieve the description from the model configuration.
     * 
     * @return The description, or null if the config is not set.
     */
    @Override
    public String getDescription() {
        return config != null ? config.getDescription() : null;
    }

    /**
     * Get the type of the transcript model.
     * 
     * @return The type of the transcript model.
     */
    @Override
    public GTranscriptModelType getType() {
        return type;
    }

    /**
     * Initialize the model with a given configuration and type.
     * 
     * @param config The configuration object for the model.
     * @param type The type of transcript model.
     * @throws LLMConfigException Thrown if there is a problem during initialization.
     */
    @Override
    public void initialize(ModelConfig config, GTranscriptModelType type) throws LLMConfigException {
        this.config = config;
        this.type = type;
        this.model = configureModel(config, type);
    }

    /**
     * Configure the model using the provided configuration and type.
     * This method is abstract and must be implemented by subclasses.
     * 
     * @param config The configuration object.
     * @param type The type of the model.
     * @return The configured model object.
     * @throws LLMConfigException Thrown if there is a configuration error.
     */
    protected abstract ModelObjectType configureModel(ModelConfig config, GTranscriptModelType type)
            throws LLMConfigException;

    /**
     * Reconfigure the model with a new configuration.
     * 
     * @param config The new configuration to apply.
     * @throws LLMConfigException Thrown if reconfiguration fails.
     */
    @Override
    public void reconfigure(ModelConfig config) throws LLMConfigException {
        this.initialize(config, type);
    }

    /**
     * Provide access to the current model configuration.
     * 
     * @return The model configuration.
     */
    @Override
    public ModelConfig getConfig() {
        return config;
    }

    /**
     * Delete the model, performing any necessary cleanup.
     * 
     * @throws LLMConfigException Thrown if the delete operation fails.
     */
    @Override
    public void delete() throws LLMConfigException {

    }

    /**
     * Retrieve the underlying transcript model object.
     * 
     * @return The model object.
     */
    public ModelObjectType getTranscriptModel() {
        return model;
    }

}