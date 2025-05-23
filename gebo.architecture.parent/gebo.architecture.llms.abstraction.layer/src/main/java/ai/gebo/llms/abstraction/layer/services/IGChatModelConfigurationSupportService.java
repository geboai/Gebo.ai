/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.abstraction.layer.services;

import ai.gebo.llms.abstraction.layer.model.GBaseChatModelChoice;
import ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig;
import ai.gebo.llms.abstraction.layer.model.GChatModelType;

/**
 * Gebo.ai comment agent
 * 
 * Defines the contract for a service that supports the configuration of chat models.
 * This interface extends {@code IGModelConfigurationSupportService} with specified types
 * for chat model configurations and choices.
 *
 * @param <ModelChoice> The type of model choice extending {@code GBaseChatModelChoice}.
 * @param <ModelConfig> The type of model configuration extending {@code GBaseChatModelConfig}.
 */
public interface IGChatModelConfigurationSupportService<ModelChoice extends GBaseChatModelChoice, ModelConfig extends GBaseChatModelConfig>
		extends IGModelConfigurationSupportService<GChatModelType, ModelChoice, ModelConfig> {

	/**
	 * Creates a configurable chat model based on the provided configuration.
	 *
	 * @param config The configuration for the chat model.
	 * @return An instance of {@code IGConfigurableChatModel} based on the provided configuration.
	 * @throws LLMConfigException If there is a problem creating the chat model.
	 */
	public IGConfigurableChatModel<ModelConfig> create(ModelConfig config) throws LLMConfigException;

}