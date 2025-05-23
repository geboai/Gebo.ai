/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.abstraction.layer.services;

import java.io.InputStream;

import ai.gebo.llms.abstraction.layer.model.GBaseTextToSpeachModelConfig;
import ai.gebo.llms.abstraction.layer.model.GTextToSpeechModelType;

/**
 * Gebo.ai comment agent
 * 
 * Represents a configurable text-to-speech model interface.
 * ModelConfig is a generic type that must extend GBaseTextToSpeachModelConfig.
 * This interface extends IGConfigurableModel, which provides the configuration capabilities.
 *
 * @param <ModelConfig> The type of model configuration used by the text-to-speech model.
 */
public interface IGConfigurableTextToSpeechModel<ModelConfig extends GBaseTextToSpeachModelConfig>
        extends IGConfigurableModel<ModelConfig, GTextToSpeechModelType> {

    /**
     * Converts the provided text to speech and returns it as an InputStream.
     *
     * @param text The input text to be converted to speech.
     * @return An InputStream of the speech audio data.
     */
    public InputStream call(String text);
}