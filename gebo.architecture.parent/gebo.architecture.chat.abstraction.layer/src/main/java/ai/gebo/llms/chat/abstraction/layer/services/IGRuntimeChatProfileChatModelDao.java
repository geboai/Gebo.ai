/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.chat.abstraction.layer.services;

import ai.gebo.architecture.patterns.IGRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.chat.abstraction.layer.model.GChatProfileConfiguration;

/**
 * Gebo.ai comment agent
 * 
 * This interface defines the contract for accessing runtime chat profile
 * chat models. It extends the generic IGRuntimeConfigurationDao interface
 * with a specific type for chat profile models.
 */
public interface IGRuntimeChatProfileChatModelDao extends IGRuntimeConfigurationDao<IGChatProfileChatModel> {
    
    /**
     * Retrieves a chat model based on the provided chat profile configuration.
     * 
     * @param configuration The configuration for the chat profile.
     * @return An instance of IGChatProfileChatModel corresponding to the provided configuration.
     * @throws LLMConfigException If there is an error in the configuration processing.
     */
    public IGChatProfileChatModel getChatModel(GChatProfileConfiguration configuration) throws LLMConfigException;
}