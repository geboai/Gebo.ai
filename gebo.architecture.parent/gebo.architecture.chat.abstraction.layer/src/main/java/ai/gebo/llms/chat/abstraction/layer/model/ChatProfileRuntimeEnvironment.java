/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.chat.abstraction.layer.model;

import org.springframework.ai.chat.prompt.PromptTemplate;

import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableEmbeddingModel;

/**
 * Gebo.ai comment agent
 * Represents the runtime environment for a chat profile.
 * Holds the prompt, chat model, and embedding model necessary for chat operations.
 */
public class ChatProfileRuntimeEnvironment {
    
    // The prompt template used by the chat model
    private final PromptTemplate prompt;
    
    // The chat model that can be configured at runtime
    private final IGConfigurableChatModel chatModel;
    
    // The embedding model that can be configured at runtime
    private final IGConfigurableEmbeddingModel embeddingModel;

    /**
     * Constructs a new ChatProfileRuntimeEnvironment with the specified prompt, chat model, and embedding model.
     *
     * @param prompt the prompt template for the chat model
     * @param chatModel the configurable chat model
     * @param embeddingModel the configurable embedding model
     */
    public ChatProfileRuntimeEnvironment(PromptTemplate prompt, IGConfigurableChatModel chatModel,
            IGConfigurableEmbeddingModel embeddingModel) {
        this.prompt = prompt;
        this.chatModel = chatModel;
        this.embeddingModel = embeddingModel;
    }

    /**
     * Returns the prompt template associated with this environment.
     *
     * @return the prompt template
     */
    public PromptTemplate getPrompt() {
        return prompt;
    }

    /**
     * Returns the configurable chat model associated with this environment.
     *
     * @return the chat model
     */
    public IGConfigurableChatModel getChatModel() {
        return chatModel;
    }

    /**
     * Returns the configurable embedding model associated with this environment.
     *
     * @return the embedding model
     */
    public IGConfigurableEmbeddingModel getEmbeddingModel() {
        return embeddingModel;
    }
}