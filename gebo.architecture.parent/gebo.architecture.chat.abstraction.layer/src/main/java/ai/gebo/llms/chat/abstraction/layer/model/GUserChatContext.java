/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.chat.abstraction.layer.model;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.core.index.HashIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig;
import ai.gebo.model.annotations.GObjectReference;
import ai.gebo.model.base.GBaseObject;
import ai.gebo.model.base.GObjectRef;

/**
 * AI generated comments
 * Represents a user chat context within the chat abstraction layer model.
 * Stores details about chat interactions and configurations for a user.
 */
@Document
public class GUserChatContext extends GBaseObject {

    /**
     * Inner class to represent individual interactions within a chat session.
     * Captures both the request and response aspects of a chat interaction.
     */
    public static class ChatInteractions {
        public GeboChatRequest request = null; // Stores the chat request
        public Integer requestNTokens = null; // Number of tokens in the request
        public GeboTemplatedChatResponse response = null; // Stores the chat response
        public Integer responseNTokens = null; // Number of tokens in the response
    };

    private Date chatCreationDateTime = null; // Timestamp for chat creation
    @HashIndexed
    private String username = null; // Username for the chat context
    @GObjectReference(referencedType = GChatProfileConfiguration.class)
    private String chatProfileCode = null; // Chat profile configuration code

    private GObjectRef<GBaseChatModelConfig> modelReference = null; // Reference to the chat model configuration
    private Boolean ragChat = null; // Indicates if the chat supports Retrieval-Augmented Generation
    private String chatMemoryId = null; // Identifier for chat memory
    private List<ChatInteractions> interactions = null; // List of chat interactions
    private String chatModelCode = null; // Code for the chat model used
    private List<String> choosedKnowledgeBases = null; // List of chosen knowledge bases for the chat
    private String summarizedHistory = null; // A summarized history of the chat interactions

    /** Default constructor for GUserChatContext */
    public GUserChatContext() {

    }

    /** 
     * Gets the username associated with the chat context.
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /** 
     * Sets the username for the chat context.
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /** 
     * Gets the chat profile configuration code.
     * @return chatProfileCode
     */
    public String getChatProfileCode() {
        return chatProfileCode;
    }

    /** 
     * Sets the chat profile configuration code.
     * @param chatProfileCode the chat profile code to set
     */
    public void setChatProfileCode(String chatProfileCode) {
        this.chatProfileCode = chatProfileCode;
    }

    /** 
     * Checks if the chat supports Retrieval-Augmented Generation.
     * @return ragChat
     */
    public Boolean getRagChat() {
        return ragChat;
    }

    /** 
     * Sets whether the chat supports Retrieval-Augmented Generation.
     * @param ragChat the RAG status to set
     */
    public void setRagChat(Boolean ragChat) {
        this.ragChat = ragChat;
    }

    /** 
     * Gets the reference to the chat model configuration.
     * @return modelReference
     */
    public GObjectRef<GBaseChatModelConfig> getModelReference() {
        return modelReference;
    }

    /**
     * Sets the reference to the chat model configuration.
     * @param modelReference the model reference to set
     */
    public void setModelReference(GObjectRef<GBaseChatModelConfig> modelReference) {
        this.modelReference = modelReference;
    }

    /** 
     * Gets the chat memory ID.
     * @return chatMemoryId
     */
    public String getChatMemoryId() {
        return chatMemoryId;
    }

    /** 
     * Sets the chat memory ID.
     * @param chatMemoryId the chat memory ID to set
     */
    public void setChatMemoryId(String chatMemoryId) {
        this.chatMemoryId = chatMemoryId;
    }

    /** 
     * Gets the list of chat interactions.
     * @return interactions
     */
    public List<ChatInteractions> getInteractions() {
        return interactions;
    }

    /** 
     * Sets the list of chat interactions.
     * @param interactions the interactions to set
     */
    public void setInteractions(List<ChatInteractions> interactions) {
        this.interactions = interactions;
    }

    /** 
     * Gets the date and time when the chat was created.
     * @return chatCreationDateTime
     */
    public Date getChatCreationDateTime() {
        return chatCreationDateTime;
    }

    /** 
     * Sets the date and time when the chat was created.
     * @param chatCreationDateTime the chat creation date and time to set
     */
    public void setChatCreationDateTime(Date chatCreationDateTime) {
        this.chatCreationDateTime = chatCreationDateTime;
    }

    /** 
     * Gets the code for the chat model used.
     * @return chatModelCode
     */
    public String getChatModelCode() {
        return chatModelCode;
    }

    /** 
     * Sets the code for the chat model used.
     * @param chatModelCode the chat model code to set
     */
    public void setChatModelCode(String chatModelCode) {
        this.chatModelCode = chatModelCode;
    }

    /** 
     * Gets the list of chosen knowledge bases for the chat.
     * @return choosedKnowledgeBases
     */
    public List<String> getChoosedKnowledgeBases() {
        return choosedKnowledgeBases;
    }

    /** 
     * Sets the list of chosen knowledge bases for the chat.
     * @param choosedKnowledgeBases the knowledge bases to set
     */
    public void setChoosedKnowledgeBases(List<String> choosedKnowledgeBases) {
        this.choosedKnowledgeBases = choosedKnowledgeBases;
    }

    /** 
     * Gets the summarized history of the chat interactions.
     * @return summarizedHistory
     */
    public String getSummarizedHistory() {
        return summarizedHistory;
    }

    /** 
     * Sets the summarized history of the chat interactions.
     * @param summarizedHistory the summarized history to set
     */
    public void setSummarizedHistory(String summarizedHistory) {
        this.summarizedHistory = summarizedHistory;
    }
}