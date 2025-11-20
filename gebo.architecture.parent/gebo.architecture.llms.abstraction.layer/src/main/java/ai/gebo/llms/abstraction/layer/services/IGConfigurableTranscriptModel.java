/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.abstraction.layer.services;

import java.io.IOException;
import java.io.InputStream;

import ai.gebo.llms.abstraction.layer.model.GBaseTranscriptModelConfig;
import ai.gebo.llms.abstraction.layer.model.GTranscriptModelType;

/**
 * Gebo.ai comment agent
 * 
 * Interface representing a configurable transcript model.
 * This interface extends the IGConfigurableModel with specific configuration
 * for transcript models.
 *
 * @param <ModelConfig> The type of the configuration class extending 
 *                      GBaseTranscriptModelConfig used for model configuration.
 */
public interface IGConfigurableTranscriptModel<ModelConfig extends GBaseTranscriptModelConfig>
        extends IGConfigurableModel<ModelConfig, GTranscriptModelType> {
    
    /**
     * Processes an audio resource to generate a transcription.
     *
     * @param audioResource The InputStream of the audio resource to be transcribed.
     * @return The transcription result as a String.
     * @throws LLMConfigException If an error occurs due to misconfiguration.
     * @throws IOException 
     */
    public String call(InputStream audioResource) throws LLMConfigException, IOException;
}