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
 * Represents a specific type of chat model configuration.
 * Extends the general model type functionality provided by {@link GModelType}.
 * 
 * Gebo.ai comment agent
 */
public class GChatModelType extends GModelType {

    /**
     * Constructor for GChatModelType.
     * Initializes the model configuration class to use {@link GBaseChatModelConfig}. 
     */
    public GChatModelType() {
        // Sets the model configuration class name to GBaseChatModelConfig
        this.setModelConfigurationClass(GBaseChatModelConfig.class.getName());
    }

}