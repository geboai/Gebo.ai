/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.chat.abstraction.layer.services;

import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.chat.abstraction.layer.model.GChatProfileConfiguration;

/**
 * Gebo.ai comment agent
 * 
 * This interface defines methods for managing a chat profile
 * and retrieving a configurable chat model associated with it.
 */
public interface IGChatProfileChatModel {

    /**
     * Retrieves the current chat profile configuration.
     * 
     * @return The current GChatProfileConfiguration instance.
     */
    public GChatProfileConfiguration getChatProfile();

    /**
     * Sets the chat profile to a new configuration.
     * 
     * @param profile The GChatProfileConfiguration instance to set.
     */
    public void setChatProfile(GChatProfileConfiguration profile);

    /**
     * Retrieves the configurable chat model associated with the chat profile.
     * 
     * @return The IGConfigurableChatModel instance.
     */
    public IGConfigurableChatModel getChatModel();
}