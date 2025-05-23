/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.chat.abstraction.layer.services.impl;

import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.chat.abstraction.layer.model.GChatProfileConfiguration;
import ai.gebo.llms.chat.abstraction.layer.services.IGChatProfileChatModel;

/**
 * Gebo.ai comment agent
 * This class represents a chat model that can be configured with a specific chat profile configuration.
 * It implements the IGChatProfileChatModel interface to provide chat model and profile management capabilities.
 */
public class GChatProfileChatModel implements IGChatProfileChatModel {
    // The chat model that can be configured
    protected IGConfigurableChatModel chatModel = null;
    // The chat profile configuration
    protected GChatProfileConfiguration chatProfile = null;

    /**
     * Constructor to initialize the chat model and chat profile.
     *
     * @param chatProfile The chat profile configuration to be used.
     * @param chatModel The configurable chat model.
     */
    public GChatProfileChatModel(GChatProfileConfiguration chatProfile, IGConfigurableChatModel chatModel) {
        this.chatModel = chatModel;
        this.chatProfile = chatProfile;
    }

    /**
     * Retrieves the configurable chat model.
     *
     * @return The chat model.
     */
    @Override
    public IGConfigurableChatModel getChatModel() {
        return chatModel;
    }

    /**
     * Retrieves the chat profile configuration.
     *
     * @return The chat profile configuration.
     */
    public GChatProfileConfiguration getChatProfile() {
        return chatProfile;
    }

    /**
     * Sets a new chat profile configuration.
     *
     * @param chatProfile The new chat profile configuration.
     */
    public void setChatProfile(GChatProfileConfiguration chatProfile) {
        this.chatProfile = chatProfile;
    }
}