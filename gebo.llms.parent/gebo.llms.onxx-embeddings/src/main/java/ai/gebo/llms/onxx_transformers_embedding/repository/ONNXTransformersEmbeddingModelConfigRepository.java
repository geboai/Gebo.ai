/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.onxx_transformers_embedding.repository;

import ai.gebo.architecture.persistence.IGBaseMongoDBRepository;
import ai.gebo.llms.onxx_transformers_embedding.model.GONNXTransformersEmbeddingModelConfig;

/**
 * AI generated comments
 * Repository interface for managing ONNX Transformers Embedding Model configurations in MongoDB.
 * This interface extends the base MongoDB repository functionality for the specific
 * GONNXTransformersEmbeddingModelConfig entity type.
 */
public interface ONNXTransformersEmbeddingModelConfigRepository extends IGBaseMongoDBRepository<GONNXTransformersEmbeddingModelConfig> {
	/**
	 * Returns the class type being managed by this repository.
	 * This method implementation is required by the IGBaseMongoDBRepository interface.
	 *
	 * @return The GONNXTransformersEmbeddingModelConfig class
	 */
	@Override
	default Class<GONNXTransformersEmbeddingModelConfig> getManagedType() {
		
		return GONNXTransformersEmbeddingModelConfig.class;
	}
}