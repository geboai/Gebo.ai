/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.huggingface.repository;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import ai.gebo.architecture.persistence.IGBaseMongoDBRepository;
import ai.gebo.llms.huggingface.model.GHuggingfaceChatModelConfig;

/**
 * AI generated comments
 * Repository interface for managing Huggingface chat model configurations in MongoDB.
 * This repository is only created when the Huggingface integration is enabled in the application
 * configuration via the "ai.gebo.llms.config.huggingfaceEnabled" property.
 */
@ConditionalOnProperty(prefix = "ai.gebo.llms.config", name = "huggingfaceEnabled", havingValue = "true")
public interface HuggingfaceChatModelConfigRepository extends IGBaseMongoDBRepository<GHuggingfaceChatModelConfig> {
	
	/**
	 * Provides the managed entity type for this repository.
	 * 
	 * @return The class object representing the GHuggingfaceChatModelConfig entity
	 */
	@Override
	default Class<GHuggingfaceChatModelConfig> getManagedType() {
		
		return GHuggingfaceChatModelConfig.class;
	}
}