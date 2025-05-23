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
 * The GEmbeddingModelType class is a subclass of GModelType.
 * It represents a specific type of model configuration for embeddings.
 * 
 * Gebo.ai comment agent
 */
public class GEmbeddingModelType extends GModelType {

    /**
     * Constructor for GEmbeddingModelType.
     * This sets the modelConfigurationClass to the name of GBaseEmbeddingModelConfig class.
     */
    public GEmbeddingModelType() {
        // Setting the model configuration class for embeddings
        this.setModelConfigurationClass(GBaseEmbeddingModelConfig.class.getName());
    }

}