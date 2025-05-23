/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.abstraction.layer.services;

import ai.gebo.llms.abstraction.layer.model.GBaseModelConfig;
import ai.gebo.llms.abstraction.layer.model.GModelType;

/**
 * Gebo.ai comment agent
 * 
 * An interface for configurable models that outlines the essential methods required
 * for managing model configuration and lifecycle actions such as initialization,
 * reconfiguration, and deletion.
 * 
 * @param <ModelConfig> the type of the model configuration
 * @param <ModelType> the type of the model
 */
public interface IGConfigurableModel<ModelConfig extends GBaseModelConfig, ModelType extends GModelType> {

    /**
     * Retrieves the unique code associated with the model.
     * 
     * @return the model's code
     */
    public String getCode();

    /**
     * Provides a description of the model.
     * 
     * @return a descriptive text for the model
     */
    public String getDescription();

    /**
     * Obtains the type of the model.
     * 
     * @return the model type
     */
    public ModelType getType();

    /**
     * Initializes the model with the given configuration settings and type.
     * 
     * @param config the configuration settings for the model
     * @param type the type of the model
     * @throws LLMConfigException if an error occurs during initialization
     */
    public void initialize(ModelConfig config, ModelType type) throws LLMConfigException;

    /**
     * Reconfigures the model using the provided configuration settings.
     * 
     * @param config the new configuration settings for the model
     * @throws LLMConfigException if an error occurs during reconfiguration
     */
    public void reconfigure(ModelConfig config) throws LLMConfigException;

    /**
     * Retrieves the current configuration of the model.
     * 
     * @return the model's configuration
     */
    public ModelConfig getConfig();

    /**
     * Deletes or cleans up resources associated with the model.
     * 
     * @throws LLMConfigException if an error occurs during deletion
     */
    public void delete() throws LLMConfigException;
}