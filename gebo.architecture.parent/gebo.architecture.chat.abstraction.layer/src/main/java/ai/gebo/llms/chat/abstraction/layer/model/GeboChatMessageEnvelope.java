/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.chat.abstraction.layer.model;

import java.io.Serializable;

/**
 * Gebo.ai comment agent
 * 
 * This class represents an envelope for chat messages in the Gebo AI system.
 * It can hold a message content of any type, along with a flag indicating
 * whether it is the last message in a sequence.
 *
 * @param <T> the type of the message content
 */
public class GeboChatMessageEnvelope<T> implements Serializable {

    /**
     * This nested static class represents the final content of a message.
     * It is marked as final and immutable.
     */
    public static class FinalMessageContent implements Serializable {
        public final boolean finalValue = true; // Always true to indicate final message content
    };

    // A constant representing a final message envelope with a FinalMessageContent
    public static final GeboChatMessageEnvelope<FinalMessageContent> FINAL_MESSAGE = new GeboChatMessageEnvelope<FinalMessageContent>(
            new FinalMessageContent(), true);

    protected T content = null; // The content of the message
    protected boolean lastMessage = false; // Flag to indicate if it is the last message

    /**
     * Default constructor.
     */
    public GeboChatMessageEnvelope() {

    }

    /**
     * Constructor to create GeboChatMessageEnvelope with specified content.
     *
     * @param c the content of the message
     */
    public GeboChatMessageEnvelope(T c) {
        this.content = c;
    }

    /**
     * Constructor to create GeboChatMessageEnvelope with specified content and lastMessage flag.
     *
     * @param c  the content of the message
     * @param lm flag indicating if it is the last message
     */
    public GeboChatMessageEnvelope(T c, boolean lm) {
        this.content = c;
        this.lastMessage = lm;
    }

    /**
     * Retrieves the content of the message.
     * 
     * @return the content of the message
     */
    public T getContent() {
        return content;
    }

    /**
     * Sets the content of the message.
     * 
     * @param content the new content of the message
     */
    public void setContent(T content) {
        this.content = content;
    }

    /**
     * Checks if this is the last message.
     * 
     * @return true if it is the last message, false otherwise
     */
    public boolean isLastMessage() {
        return lastMessage;
    }

    /**
     * Sets whether this is the last message.
     * 
     * @param lastMessage true if it is the last message, false otherwise
     */
    public void setLastMessage(boolean lastMessage) {
        this.lastMessage = lastMessage;
    }

    /**
     * Gets the type of the content object.
     * 
     * @return the simple class name of the content object, or null if content is null
     */
    public String getContentObjectType() {
        return content != null ? content.getClass().getSimpleName() : null;
    }

}