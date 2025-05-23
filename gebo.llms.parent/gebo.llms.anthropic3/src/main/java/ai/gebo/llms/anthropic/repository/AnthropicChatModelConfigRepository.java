/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.anthropic.repository;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import ai.gebo.architecture.persistence.IGBaseMongoDBRepository;
import ai.gebo.llms.anthropic.model.GAnthropicChatModelConfig;

/**
 * AI generated comments
 *
 * Repository interface for managing Anthropic chat model configurations in MongoDB.
 * This interface extends the base MongoDB repository to provide CRUD operations
 * for GAnthropicChatModelConfig entities.
 * 
 * Only enabled when the 'anthropicEnabled' property is set to 'true' in the application
 * configuration under the 'ai.gebo.llms.config' prefix.
 */
@ConditionalOnProperty(prefix = "ai.gebo.llms.config", name = "anthropicEnabled", havingValue = "true")
public interface AnthropicChatModelConfigRepository extends IGBaseMongoDBRepository<GAnthropicChatModelConfig> {
	
	/**
	 * Returns the class type managed by this repository.
	 * This implementation specifies GAnthropicChatModelConfig as the managed entity type.
	 *
	 * @return The Class object representing GAnthropicChatModelConfig
	 */
	@Override
	default Class<GAnthropicChatModelConfig> getManagedType() {
		
		return GAnthropicChatModelConfig.class;
	}
}