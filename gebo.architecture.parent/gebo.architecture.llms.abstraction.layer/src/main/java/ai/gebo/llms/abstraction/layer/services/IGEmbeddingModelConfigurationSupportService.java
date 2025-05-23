/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.abstraction.layer.services;

import ai.gebo.llms.abstraction.layer.model.GBaseEmbeddingModelChoice;
import ai.gebo.llms.abstraction.layer.model.GBaseEmbeddingModelConfig;
import ai.gebo.llms.abstraction.layer.model.GEmbeddingModelType;

/**
 * Gebo.ai comment agent
 * 
 * This interface defines the support service for configuring embedding models.
 * It extends the generic model configuration support service with additional
 * functionalities specific to embedding models.
 * 
 * @param <ModelChoice> The type of model choice extending GBaseEmbeddingModelChoice.
 * @param <ModelConfig> The type of model configuration extending GBaseEmbeddingModelConfig.
 */
public interface IGEmbeddingModelConfigurationSupportService<ModelChoice extends GBaseEmbeddingModelChoice, ModelConfig extends GBaseEmbeddingModelConfig>
        extends IGModelConfigurationSupportService<GEmbeddingModelType, ModelChoice, ModelConfig> {

    /**
     * Creates a configurable embedding model based on the provided configuration.
     *
     * @param config The model configuration to use for creating the embedding model.
     * @return The configured embedding model.
     * @throws LLMConfigException If the configuration process encounters an error.
     */
    public IGConfigurableEmbeddingModel<ModelConfig> create(ModelConfig config) throws LLMConfigException;
}