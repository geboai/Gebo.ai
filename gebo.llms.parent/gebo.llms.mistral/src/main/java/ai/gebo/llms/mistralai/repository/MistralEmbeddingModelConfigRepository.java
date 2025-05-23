/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.mistralai.repository;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import ai.gebo.architecture.persistence.IGBaseMongoDBRepository;
import ai.gebo.llms.mistralai.model.GMistralEmbeddingModelConfig;

/**
 * AI generated comments
 * Repository interface for managing MistralAI embedding model configurations in MongoDB.
 * This repository is only created when the 'mistralAIEnabled' property is set to 'true'.
 * It extends the base MongoDB repository interface to provide CRUD operations for
 * MistralAI embedding model configurations.
 */
@ConditionalOnProperty(prefix = "ai.gebo.llms.config", name = "mistralAIEnabled", havingValue = "true")
public interface MistralEmbeddingModelConfigRepository extends IGBaseMongoDBRepository<GMistralEmbeddingModelConfig> {
	
	/**
	 * Returns the class type managed by this repository.
	 * This implementation specifies GMistralEmbeddingModelConfig as the managed entity type.
	 * 
	 * @return Class object representing the GMistralEmbeddingModelConfig type
	 */
	@Override
	default Class<GMistralEmbeddingModelConfig> getManagedType() {
		
		return GMistralEmbeddingModelConfig.class;
	}
}