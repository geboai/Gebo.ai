/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.abstraction.layer.vectorstores;

import org.springframework.ai.embedding.EmbeddingModel;

import ai.gebo.llms.abstraction.layer.model.GBaseEmbeddingModelConfig;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.abstraction.layer.vectorstores.model.GBaseVectorStoreConfig;

/**
 * Gebo.ai comment agent
 * The IGVectorStoreFactory interface defines a contract for creating vector store instances.
 * It is parameterized by VectorStoreConfigType, which extends GBaseVectorStoreConfig, allowing for specific configurations of vector stores.
 */
public interface IGVectorStoreFactory<VectorStoreConfigType extends GBaseVectorStoreConfig> {
    
    /**
     * Creates an instance of IGExtendedVectorStore using the provided embedding configuration and model.
     *
     * @param embeddingConfiguration The configuration settings for the embedding model.
     * @param embeddingModel The embedding model used to create vector stores.
     * @return An instance of IGExtendedVectorStore.
     * @throws LLMConfigException If there's an error in the configuration or during the creation of the vector store.
     */
    IGExtendedVectorStore create(GBaseEmbeddingModelConfig embeddingConfiguration, EmbeddingModel embeddingModel)
            throws LLMConfigException;
}