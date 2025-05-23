/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.openai_compat.repository;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import ai.gebo.architecture.persistence.IGBaseMongoDBRepository;
import ai.gebo.llms.openai_compat.model.GenericOpenAIAPIEmbeddingModelConfig;

/**
 * AI generated comments
 * 
 * Repository interface for managing GenericOpenAIAPIEmbeddingModelConfig entities in MongoDB.
 * This interface extends the base MongoDB repository to provide CRUD operations
 * for OpenAI compatible API embedding model configurations.
 */
public interface GenericOpenAIAPIEmbeddingModelConfigRepository extends IGBaseMongoDBRepository<GenericOpenAIAPIEmbeddingModelConfig> {
	/**
	 * Returns the managed entity type class.
	 * This method is required by the IGBaseMongoDBRepository interface to identify
	 * the entity type being managed by this repository.
	 * 
	 * @return The class object for GenericOpenAIAPIEmbeddingModelConfig
	 */
	@Override
	default Class<GenericOpenAIAPIEmbeddingModelConfig> getManagedType() {
		
		return GenericOpenAIAPIEmbeddingModelConfig.class;
	}
}