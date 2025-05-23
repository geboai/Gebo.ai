/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.chat.abstraction.layer.model;

import java.util.List;

import ai.gebo.architecture.ai.model.ToolCategoriesTree;
import ai.gebo.llms.abstraction.layer.model.GBaseChatModelChoice;
import ai.gebo.model.base.GBaseObject;

/**
 * Gebo.ai comment agent
 * A class representing user information for a Gebo chat session.
 */
public class GeboChatUserInfo {
    // Indicates if the chat is a Retrieval-Augmented Generation (RAG) chat
    private final boolean ragChat;
    
    // List of knowledge bases associated with the user
    private final List<GBaseObject> knowledgeBases;
    
    // Unique identifier for the provider
    private final String providerId;
    
    // List of available functions represented by tool categories
    private final List<ToolCategoriesTree> availableFunctions;
    
    // Model choice for the chat
    private final GBaseChatModelChoice chatModelChoice;

    /**
     * Constructs a new GeboChatUserInfo object.
     *
     * @param providerId          Unique identifier for the provider
     * @param chatModelChoice     The model choice for the chat
     * @param ragChat             Boolean flag indicating if the chat is RAG
     * @param knowledgeBases      List of associated knowledge bases
     * @param availableFunctions  List of available functions
     */
    public GeboChatUserInfo(String providerId, GBaseChatModelChoice chatModelChoice, boolean ragChat, List<GBaseObject> knowledgeBases,
                            List<ToolCategoriesTree> availableFunctions) {
        this.providerId = providerId;
        this.chatModelChoice = chatModelChoice;
        this.availableFunctions = availableFunctions;
        this.knowledgeBases = knowledgeBases;
        this.ragChat = ragChat;
    }

    /**
     * Constructs a new GeboChatUserInfo object with RAG chat set to true by default.
     *
     * @param providerId          Unique identifier for the provider
     * @param chatModelChoice     The model choice for the chat
     * @param knowledgeBases      List of associated knowledge bases
     * @param availableFunctions  List of available functions
     */
    public GeboChatUserInfo(String providerId, GBaseChatModelChoice chatModelChoice, List<GBaseObject> knowledgeBases,
                            List<ToolCategoriesTree> availableFunctions) {
        this(providerId, chatModelChoice, true, knowledgeBases, availableFunctions);
    }

    /**
     * Constructs a new GeboChatUserInfo object with no knowledge bases and RAG chat set to false by default.
     *
     * @param providerId          Unique identifier for the provider
     * @param chatModelChoice     The model choice for the chat
     * @param availableFunctions  List of available functions
     */
    public GeboChatUserInfo(String providerId, GBaseChatModelChoice chatModelChoice, List<ToolCategoriesTree> availableFunctions) {
        this(providerId, chatModelChoice, false, null, availableFunctions);
    }

    /**
     * @return True if the chat is a RAG chat, false otherwise.
     */
    public boolean isRagChat() {
        return ragChat;
    }

    /**
     * @return The list of associated knowledge bases.
     */
    public List<GBaseObject> getKnowledgeBases() {
        return knowledgeBases;
    }

    /**
     * @return The list of available functions.
     */
    public List<ToolCategoriesTree> getAvailableFunctions() {
        return availableFunctions;
    }

    /**
     * @return The model choice for the chat.
     */
    public GBaseChatModelChoice getChatModelChoice() {
        return chatModelChoice;
    }

    /**
     * @return The unique provider identifier.
     */
    public String getProviderId() {
        return providerId;
    }
}