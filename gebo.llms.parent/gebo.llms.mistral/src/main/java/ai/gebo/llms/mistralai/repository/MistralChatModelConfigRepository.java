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
import ai.gebo.llms.mistralai.model.GMistralChatModelConfig;

/**
 * AI generated comments
 * Repository interface for managing MistralAI chat model configurations in MongoDB.
 * This repository is only created when the 'mistralAIEnabled' property is set to 'true'
 * in the application configuration.
 */
@ConditionalOnProperty(prefix = "ai.gebo.llms.config", name = "mistralAIEnabled", havingValue = "true")
public interface MistralChatModelConfigRepository extends IGBaseMongoDBRepository<GMistralChatModelConfig> {
	
	/**
	 * Returns the class type managed by this repository.
	 * 
	 * @return The GMistralChatModelConfig class
	 */
	@Override
	default Class<GMistralChatModelConfig> getManagedType() {
		
		return GMistralChatModelConfig.class;
	}
}