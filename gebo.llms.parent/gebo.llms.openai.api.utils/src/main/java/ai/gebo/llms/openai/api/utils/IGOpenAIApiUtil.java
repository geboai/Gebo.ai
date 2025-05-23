/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.openai.api.utils;

import java.util.List;
import java.util.function.Function;

import ai.gebo.llms.abstraction.layer.model.GBaseChatModelChoice;
import ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig;
import ai.gebo.llms.abstraction.layer.model.GBaseEmbeddingModelChoice;
import ai.gebo.llms.abstraction.layer.model.GBaseEmbeddingModelConfig;
import ai.gebo.llms.models.metainfos.ModelMetaInfo;
import ai.gebo.model.OperationStatus;
import ai.gebo.openai.integration.client.model.OpenAIApiConfig;
import ai.gebo.openai.integration.client.model.OpenAIModel;
import ai.gebo.restintegration.abstraction.layer.GeboRestIntegrationException;

/**
 * AI generated comments
 * 
 * Interface for OpenAI API utilities that provides methods to retrieve
 * various models from the OpenAI API. This includes raw model listings,
 * chat models, and embedding models with appropriate configurations.
 */
public interface IGOpenAIApiUtil {
    
    /**
     * Retrieves a list of all available OpenAI models using the provided configuration.
     * 
     * @param config The OpenAI API configuration
     * @return A list of OpenAI models
     * @throws OpenAIApiException If there's an error specific to the OpenAI API
     * @throws GeboRestIntegrationException If there's an error in the REST integration layer
     */
	public List<OpenAIModel> getModels(OpenAIApiConfig config) throws OpenAIApiException, GeboRestIntegrationException;

    /**
     * Retrieves a list of chat models of the specified type using the provided configuration.
     * 
     * @param <ChatModelChoiceType> The type of chat model choice
     * @param type The class of the chat model choice type
     * @param config The OpenAI API configuration
     * @param modelConfig The chat model configuration
     * @param defaultMetainfoFactory Function to create default model meta information
     * @return An operation status containing a list of chat models if successful
     */
	public <ChatModelChoiceType extends GBaseChatModelChoice> OperationStatus<List<ChatModelChoiceType>> getChatModels(
			Class<ChatModelChoiceType> type, OpenAIApiConfig config, GBaseChatModelConfig modelConfig,
			Function<ChatModelChoiceType, ModelMetaInfo> defaultMetainfoFactory);

    /**
     * Retrieves a list of embedding models of the specified type using the provided configuration.
     * 
     * @param <EmbeddingModelChoiceType> The type of embedding model choice
     * @param type The class of the embedding model choice type
     * @param config The OpenAI API configuration
     * @param modelConfig The embedding model configuration
     * @param defaultMetainfoFactory Function to create default model meta information
     * @return An operation status containing a list of embedding models if successful
     */
	public <EmbeddingModelChoiceType extends GBaseEmbeddingModelChoice> OperationStatus<List<EmbeddingModelChoiceType>> getEmbeddingModels(
			Class<EmbeddingModelChoiceType> type, OpenAIApiConfig config, GBaseEmbeddingModelConfig modelConfig,
			Function<EmbeddingModelChoiceType, ModelMetaInfo> defaultMetainfoFactory);
}