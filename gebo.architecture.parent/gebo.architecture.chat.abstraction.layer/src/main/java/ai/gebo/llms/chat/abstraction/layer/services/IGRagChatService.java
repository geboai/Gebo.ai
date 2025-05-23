/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.chat.abstraction.layer.services;

import java.util.List;

import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.llms.abstraction.layer.model.GBaseChatModelChoice;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.chat.abstraction.layer.model.GChatProfileConfiguration;
import ai.gebo.llms.chat.abstraction.layer.model.GeboChatUserInfo;

/**
 * Gebo.ai comment agent
 *
 * Interface representing a RagChat service providing functionalities related to chat profiles.
 * Extends the IGGenericalChatService to inherit basic chat-related functionalities.
 */
public interface IGRagChatService extends IGGenericalChatService {

    /**
     * Retrieves a list of chat profile configurations.
     *
     * @return List of GChatProfileConfiguration objects representing chat profiles.
     */
    public List<GChatProfileConfiguration> getChatProfiles();

    /**
     * Retrieves metadata information for a specific chat profile model based on the chat profile code.
     *
     * @param chatProfileCode Unique code identifying the chat profile.
     * @return GBaseChatModelChoice representing metadata information for the model associated with the chat profile.
     */
    public GBaseChatModelChoice getChatProfileModelMetaInfos(String chatProfileCode);

    /**
     * Retrieves the model provider capabilities for a specific model code.
     *
     * @param modelCode Code identifying the model whose provider capabilities need to be retrieved.
     * @return ModelProviderCapabilities describing the capabilities of the model provider.
     * @throws LLMConfigException if there is an issue with the model configuration.
     */
    public ModelProviderCapabilities getProfileProviderModelCapabilities(String modelCode) throws LLMConfigException;
    
    /**
     * Retrieves user information for a chat model based on a given chat profile code.
     *
     * @param chatProfileCode Code identifying the chat profile.
     * @return GeboChatUserInfo containing user information related to the specific chat model.
     * @throws GeboPersistenceException if there is an issue with data persistence.
     * @throws LLMConfigException if there is an issue with the model configuration.
     */
    public GeboChatUserInfo getChatModelUserInfoByChatProfileCode(String chatProfileCode) throws GeboPersistenceException, LLMConfigException;
}