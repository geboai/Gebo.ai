/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.chat.abstraction.layer.services;

import java.io.InputStream;
import java.util.List;

import ai.gebo.llms.abstraction.layer.model.GBaseChatModelChoice;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.chat.abstraction.layer.model.GShortModelInfo;

/**
 * Gebo.ai comment agent
 * 
 * The IGChatService interface defines the operations for managing
 * chat models and handling speech-related functionalities. It extends
 * the IGGenericalChatService interface, enriching it with specific methods
 * for chat model configurations and speech processing.
 */
public interface IGChatService extends IGGenericalChatService {

    /**
     * Retrieves a list of short model information containing configuration
     * details for available chat models.
     *
     * @return a List of GShortModelInfo objects representing model configurations.
     */
    public List<GShortModelInfo> getModelsConfigurationList();

    /**
     * Provides metadata information for a specific chat model based on the given model code.
     *
     * @param modelCode a unique code identifying the chat model.
     * @return a GBaseChatModelChoice object containing meta information about the chat model.
     */
    public GBaseChatModelChoice getChatModelMetaInfo(String modelCode);
    
    /**
     * Retrieves the provider-specific capabilities or features for a given chat model.
     *
     * @param modelCode a unique code identifying the chat model.
     * @return a ModelProviderCapabilities object that contains the capabilities of the model provider.
     */
    public ModelProviderCapabilities getProviderCapabilities(String modelCode);

    /**
     * Transcribes speech from an InputStream using a specific model identified
     * by its code. It may throw a configuration exception if the model configuration is invalid.
     *
     * @param is an InputStream containing the audio to transcribe.
     * @param modelCode a unique code identifying the model to use for transcription.
     * @throws LLMConfigException if there is an issue with the model configuration.
     * @return a String containing the transcribed text.
     */
    public String transcript(InputStream is, String modelCode) throws LLMConfigException;

    /**
     * Converts text to speech using a specific model identified by its code.
     * It may throw a configuration exception if the model configuration is invalid.
     *
     * @param text the text to be converted to speech.
     * @param modelCode a unique code identifying the model to use for speech synthesis.
     * @throws LLMConfigException if there is an issue with the model configuration.
     * @return an InputStream with the resulting audio.
     */
    public InputStream speech(String text, String modelCode) throws LLMConfigException;
}