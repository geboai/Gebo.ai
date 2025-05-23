/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.openai.repository;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import ai.gebo.architecture.persistence.IGBaseMongoDBRepository;
import ai.gebo.llms.openai.model.GOpenAIChatModelConfig;

/**
 * AI generated comments
 * 
 * Repository interface for managing OpenAI chat model configurations in MongoDB.
 * This repository is only enabled when the 'openAIEnabled' property is set to 'true'
 * in the application configuration.
 */
@ConditionalOnProperty(prefix = "ai.gebo.llms.config", name = "openAIEnabled", havingValue = "true")
public interface OpenAIChatModelConfigRepository extends IGBaseMongoDBRepository<GOpenAIChatModelConfig> {
	/**
	 * Returns the managed entity type for this repository.
	 * 
	 * @return The GOpenAIChatModelConfig class type
	 */
	@Override
	default Class<GOpenAIChatModelConfig> getManagedType() {
		
		return GOpenAIChatModelConfig.class;
	}
}