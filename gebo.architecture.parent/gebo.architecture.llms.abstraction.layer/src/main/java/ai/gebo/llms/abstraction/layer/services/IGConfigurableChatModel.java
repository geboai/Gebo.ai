/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.abstraction.layer.services;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;

import ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig;
import ai.gebo.llms.abstraction.layer.model.GChatModelType;

/**
 * Gebo.ai comment agent
 * 
 * Interface for a configurable chat model that defines the
 * methods required for configuring and interacting with 
 * chat models in the system.
 *
 * @param <ModelConfig> The type of the model configuration, extending GBaseChatModelConfig.
 */
public interface IGConfigurableChatModel<ModelConfig extends GBaseChatModelConfig>
        extends IGConfigurableModel<ModelConfig, GChatModelType> {

    /**
     * Retrieves the chat model instance.
     *
     * @return The chat model.
     */
    public ChatModel getChatModel();

    /**
     * Gets the length of the context maintained by the chat model.
     *
     * @return The context length.
     */
    public int getContextLength();

    /**
     * Checks if the chat model supports function calls.
     *
     * @return True if supports functions, false otherwise.
     */
    public default boolean isSupportsFunctionsCall() {
        return false;
    }

    /**
     * Checks if the chat model supports structured output.
     *
     * @return True if supports structured output, false otherwise.
     */
    public default boolean isSupportsStructuredOutput() {
        return false;
    }

    /**
     * Checks if the chat model supports generating transcripts.
     *
     * @return True if supports transcripts, false otherwise.
     */
    public default boolean isSupportsTranscript() {
        return false;
    }

    /**
     * Provides a model for generating transcripts.
     * 
     * @return An instance of IGConfigurableTranscriptModel.
     * @throws LLMConfigException if transcript functionalities are not implemented.
     */
    public default IGConfigurableTranscriptModel getTranscriptModel() throws LLMConfigException {
        throw new LLMConfigException("This provider does not implement transcript functionalities");
    }

    /**
     * Checks if the chat model supports speech output.
     *
     * @return True if supports speech, false otherwise.
     */
    public default boolean isSupportsSpeecht() {
        return false;
    }

    /**
     * Provides a model for text-to-speech conversion.
     * 
     * @return An instance of IGConfigurableTextToSpeechModel.
     * @throws LLMConfigException if speech functionalities are not implemented.
     */
    public default IGConfigurableTextToSpeechModel getSpeechModel() throws LLMConfigException {
        throw new LLMConfigException("This provider does not implement speech functionalities");
    }

    /**
     * Retrieves the chat client used for communication.
     *
     * @return The chat client instance.
     * @throws LLMConfigException if the chat client is not configured correctly.
     */
    public ChatClient getChatClient() throws LLMConfigException;
}