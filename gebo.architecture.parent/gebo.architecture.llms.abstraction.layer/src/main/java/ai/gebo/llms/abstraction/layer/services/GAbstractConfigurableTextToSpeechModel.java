/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.abstraction.layer.services;

import ai.gebo.llms.abstraction.layer.model.GBaseTextToSpeachModelConfig;
import ai.gebo.llms.abstraction.layer.model.GTextToSpeechModelType;

/**
 * AI generated comments
 * Abstract base class for configurable text-to-speech models.
 * It provides the foundational implementation for handling configuration and model instantiation.
 *
 * @param <ModelConfig> The type of configuration used by the model, extending GBaseTextToSpeachModelConfig.
 * @param <ModelObjectType> The type of the model object that will be instantiated and used.
 */
public abstract class GAbstractConfigurableTextToSpeechModel<ModelConfig extends GBaseTextToSpeachModelConfig, ModelObjectType>
		implements IGConfigurableTextToSpeechModel<ModelConfig> {
	
	// Configuration specific to the text-to-speech model.
	protected ModelConfig config = null;
	
	// The instantiated text-to-speech model object.
	protected ModelObjectType model = null;
	
	// The type of text-to-speech model being used.
	protected GTextToSpeechModelType type = null;

	/**
	 * Default constructor for the abstract class.
	 */
	public GAbstractConfigurableTextToSpeechModel() {

	}

	/**
	 * Retrieves the code from the model configuration.
	 *
	 * @return The code as a String, or null if the configuration is not set.
	 */
	@Override
	public String getCode() {
		return config != null ? config.getCode() : null;
	}

	/**
	 * Retrieves the description from the model configuration.
	 *
	 * @return The description as a String, or null if the configuration is not set.
	 */
	@Override
	public String getDescription() {
		return config != null ? config.getDescription() : null;
	}

	/**
	 * Retrieves the type of text-to-speech model.
	 *
	 * @return The type as an instance of GTextToSpeechModelType.
	 */
	@Override
	public GTextToSpeechModelType getType() {
		return type;
	}

	/**
	 * Initializes the text-to-speech model with the provided configuration and type.
	 *
	 * @param config The configuration for the text-to-speech model.
	 * @param type The type of the text-to-speech model.
	 * @throws LLMConfigException If an error occurs during the configuration.
	 */
	@Override
	public void initialize(ModelConfig config, GTextToSpeechModelType type) throws LLMConfigException {
		this.config = config;
		this.type = type;
		this.model = configureModel(config, type);
	}

	/**
	 * Abstract method for configuring the text-to-speech model.
	 * Implementing classes must define how the model is instantiated and configured.
	 *
	 * @param config The configuration for the text-to-speech model.
	 * @param type The type of the text-to-speech model.
	 * @return The configured model object.
	 * @throws LLMConfigException If an error occurs during configuration.
	 */
	protected abstract ModelObjectType configureModel(ModelConfig config, GTextToSpeechModelType type) throws LLMConfigException;

	/**
	 * Reconfigures the model with a new configuration.
	 *
	 * @param config The new configuration for the text-to-speech model.
	 * @throws LLMConfigException If an error occurs during the reconfiguration.
	 */
	@Override
	public void reconfigure(ModelConfig config) throws LLMConfigException {
		this.initialize(config, type);
	}

	/**
	 * Retrieves the current configuration of the model.
	 *
	 * @return The current model configuration.
	 */
	@Override
	public ModelConfig getConfig() {
		return config;
	}

	/**
	 * Deletes the current configuration, if necessary.
	 *
	 * @throws LLMConfigException If an error occurs during deletion.
	 */
	@Override
	public void delete() throws LLMConfigException {

	}

	/**
	 * Retrieves the current instantiated text-to-speech model.
	 *
	 * @return The model object.
	 */
	public ModelObjectType getTextToSpeechModel() {
		return model;
	}

}