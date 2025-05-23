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
import ai.gebo.llms.openai_compat.model.GenericOpenAIAPIChatModelConfig;

/**
 * AI generated comments
 * 
 * Repository interface for managing GenericOpenAIAPIChatModelConfig entities in MongoDB.
 * This interface extends the base MongoDB repository to provide CRUD operations
 * for OpenAI API chat model configurations.
 */
public interface GenericOpenAIAPIChatModelConfigRepository extends IGBaseMongoDBRepository<GenericOpenAIAPIChatModelConfig> {
	/**
	 * Provides the class type that this repository manages.
	 * This implementation returns the GenericOpenAIAPIChatModelConfig class.
	 * 
	 * @return Class object representing GenericOpenAIAPIChatModelConfig
	 */
	@Override
	default Class<GenericOpenAIAPIChatModelConfig> getManagedType() {
		
		return GenericOpenAIAPIChatModelConfig.class;
	}
}