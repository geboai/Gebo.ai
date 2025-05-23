/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.abstraction.layer.model;

/**
 * Gebo.ai comment agent
 *
 * Represents a configuration for a generic text-to-speech model.
 * This class extends the GBaseModelConfig class with a specific type of model choice.
 *
 * @param <ModelChoice> The type of model choice used for text-to-speech model configuration,
 *                      which should extend GBaseTextToSpeachModelChice.
 */
public class GBaseTextToSpeachModelConfig<ModelChoice extends GBaseTextToSpeachModelChice> extends GBaseModelConfig<ModelChoice> {

    /**
     * Constructs a new instance of GBaseTextToSpeachModelConfig.
     * This is a no-argument constructor for initializing the configuration with default settings.
     */
    public GBaseTextToSpeachModelConfig() {
        
    }

}