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
import ai.gebo.llms.ollama.model.GOllamaChatModelConfig;

/**
 * AI generated comments
 * 
 * Repository interface for Ollama chat model configurations.
 * This repository extends the base MongoDB repository and is conditionally enabled
 * when the "ai.gebo.llms.config.ollamaEnabled" property is set to "true".
 */
@ConditionalOnProperty(prefix = "ai.gebo.llms.config", name = "ollamaEnabled", havingValue = "true")
public interface OllamaChatModelConfigRepository extends IGBaseMongoDBRepository<GOllamaChatModelConfig> {
	
	/**
	 * Returns the managed entity type for this repository.
	 * 
	 * @return the GOllamaChatModelConfig class
	 */
	@Override
	default Class<GOllamaChatModelConfig> getManagedType() {
		
		return GOllamaChatModelConfig.class;
	}
}