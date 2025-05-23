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

/**
 * Gebo.ai comment agent
 *
 * Interface for configuring the runtime of a text-to-speech model.
 * Extends the generic runtime model configuration DAO interface
 * to provide specific methods for text-to-speech model configurations.
 */
public interface IGTextToSpeechModelRuntimeConfigurationDao
        extends IGRuntimeModelConfigurationDao<IGConfigurableTextToSpeechModel> {

    /**
     * Adds a runtime configuration by using a base text-to-speech model configuration.
     *
     * @param config The base configuration for the text-to-speech model.
     * @throws LLMConfigException If there is an error in the configuration process.
     */
    void addRuntimeByConfig(GBaseTextToSpeachModelConfig config) throws LLMConfigException;

}