/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.abstraction.layer.services;

import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.VectorStore;

import ai.gebo.llms.abstraction.layer.model.GBaseEmbeddingModelConfig;
import ai.gebo.llms.abstraction.layer.model.GEmbeddingModelType;
import ai.gebo.llms.abstraction.layer.vectorstores.model.EmbeddingTrafficInfo;

/**
 * Gebo.ai comment agent
 * IGConfigurableEmbeddingModel represents an interface for a configurable embedding model
 * with a specific configuration type and embedding model type.
 * 
 * @param <ModelConfig> The type of the embedding model configuration that extends GBaseEmbeddingModelConfig.
 */
public interface IGConfigurableEmbeddingModel<ModelConfig extends GBaseEmbeddingModelConfig>
		extends IGConfigurableModel<ModelConfig, GEmbeddingModelType> {
	
	/**
	 * Retrieves the EmbeddingModel instance associated with this configuration.
	 * 
	 * @return An instance of EmbeddingModel.
	 */
	public EmbeddingModel getEmbeddingModel();

	/**
	 * Provides a default tokenization threshold which is used to indicate the
	 * maximum number of tokens that can be processed.
	 * 
	 * @return The default tokenization threshold value, set to 4096.
	 */
	public default int getTokenizationThreshold() {
		return 4096;
	}

	/**
	 * Retrieves the VectorStore associated with the embedding model.
	 * 
	 * @return An instance of VectorStore.
	 */
	public VectorStore getVectorStore();

	/**
	 * Obtains the sampled bytes of traffic information related to the embedding process.
	 * 
	 * @return An instance of EmbeddingTrafficInfo containing traffic data.
	 */
	public EmbeddingTrafficInfo getSampledBytesOfTraffic();
}