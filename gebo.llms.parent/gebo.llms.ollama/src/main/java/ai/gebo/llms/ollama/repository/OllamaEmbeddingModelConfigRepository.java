/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.ollama.repository;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import ai.gebo.architecture.persistence.IGBaseMongoDBRepository;
import ai.gebo.llms.ollama.model.GOllamaEmbeddingModelConfig;

/**
 * AI generated comments
 * Repository interface for Ollama embedding model configuration data.
 * This repository manages persistence operations for Ollama embedding model configurations.
 * It extends the base MongoDB repository interface and is conditionally enabled
 * when the 'ai.gebo.llms.config.ollamaEnabled' property is set to 'true'.
 */
@ConditionalOnProperty(prefix = "ai.gebo.llms.config", name = "ollamaEnabled", havingValue = "true")
public interface OllamaEmbeddingModelConfigRepository extends IGBaseMongoDBRepository<GOllamaEmbeddingModelConfig> {
	/**
	 * Provides the managed entity type for this repository.
	 * 
	 * @return The class type of GOllamaEmbeddingModelConfig that this repository manages
	 */
	@Override
	default Class<GOllamaEmbeddingModelConfig> getManagedType() {
		
		return GOllamaEmbeddingModelConfig.class;
	}
}