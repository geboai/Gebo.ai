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
 * Represents the configuration for a base embedding model. This class extends
 * the functionality of the GBaseModelConfig to include settings specific to
 * embedding models.
 * 
 * @param <ModelChoice> the type parameter indicating the model choice type
 */
public class GBaseEmbeddingModelConfig<ModelChoice extends GBaseEmbeddingModelChoice> extends GBaseModelConfig<ModelChoice> {
    
    /**
     * A threshold for tokenization; can be null if not set.
     */
    private Integer tokenizationThreshold = null;
    
    /**
     * Default constructor that initializes a new instance of 
     * GBaseEmbeddingModelConfig with default values.
     */
    public GBaseEmbeddingModelConfig() {

    }

    /**
     * Retrieves the tokenization threshold.
     * 
     * @return the current tokenization threshold
     */
    public Integer getTokenizationThreshold() {
        return tokenizationThreshold;
    }

    /**
     * Sets the tokenization threshold.
     * 
     * @param tokenizationThreshold the threshold value for tokenization to set
     */
    public void setTokenizationThreshold(Integer tokenizationThreshold) {
        this.tokenizationThreshold = tokenizationThreshold;
    }

}