/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.deepseek.repository;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import ai.gebo.architecture.persistence.IGBaseMongoDBRepository;
import ai.gebo.llms.deepseek.model.GDeepseekChatModelConfig;

/**
 * AI generated comments
 * Repository interface for managing Deepseek Chat Model configurations in MongoDB.
 * This repository is only created when the 'deepseekEnabled' property is set to 'true'.
 */
@ConditionalOnProperty(prefix = "ai.gebo.llms.config", name = "deepseekEnabled", havingValue = "true")
public interface DeepseekChatModelConfigRepository extends IGBaseMongoDBRepository<GDeepseekChatModelConfig> {
	/**
	 * Provides the class type managed by this repository.
	 * 
	 * @return The GDeepseekChatModelConfig class
	 */
	@Override
	default Class<GDeepseekChatModelConfig> getManagedType() {
		
		return GDeepseekChatModelConfig.class;
	}
}