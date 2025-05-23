/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.abstraction.layer.services;

import ai.gebo.llms.abstraction.layer.model.GBaseTextToSpeachModelChice;
import ai.gebo.llms.abstraction.layer.model.GBaseTextToSpeachModelConfig;
import ai.gebo.llms.abstraction.layer.model.GTextToSpeechModelType;

/**
 * Gebo.ai comment agent
 * 
 * This interface defines the contract for configuring text-to-speech models.
 * It extends a generic model configuration support service interface,
 * providing additional methods specific to text-to-speech models.
 *
 * @param <ModelChoice> A choice of model extending GBaseTextToSpeachModelChice
 * @param <ModelConfig> The configuration type extending GBaseTextToSpeachModelConfig
 */
public interface IGTextToSpeechModelConfigurationSupportService<ModelChoice extends GBaseTextToSpeachModelChice, ModelConfig extends GBaseTextToSpeachModelConfig<ModelChoice>>
        extends IGModelConfigurationSupportService<GTextToSpeechModelType, ModelChoice, ModelConfig> {

    /**
     * Creates an instance of a configurable text-to-speech model
     * based on the provided configuration.
     *
     * @param config The model configuration
     * @return An instance of IGConfigurableTextToSpeechModel
     * @throws LLMConfigException if configuration fails
     */
    public IGConfigurableTextToSpeechModel<ModelConfig> create(ModelConfig config) throws LLMConfigException;
}