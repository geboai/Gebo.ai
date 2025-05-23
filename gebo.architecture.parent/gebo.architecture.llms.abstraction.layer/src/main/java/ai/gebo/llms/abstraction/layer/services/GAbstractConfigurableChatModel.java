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

import ai.gebo.llms.abstraction.layer.model.GBaseChatModelChoice;
import ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig;
import ai.gebo.llms.abstraction.layer.model.GChatModelType;

/**
 * AI generated comments
 * An abstract class representing a configurable chat model.
 * It defines a template for setting up chat models with configuration and model type.
 * 
 * @param <ModelConfig> The type parameter for model configuration extending from GBaseChatModelConfig.
 * @param <ChatModelType> The type parameter for the chat model extending from ChatModel.
 */
public abstract class GAbstractConfigurableChatModel<ModelConfig extends GBaseChatModelConfig, ChatModelType extends ChatModel>
		implements IGConfigurableChatModel<ModelConfig> {
	
	// Configuration for the chat model
	protected ModelConfig config = null;
	// Type of the chat model
	protected GChatModelType type = null;
	// Instance of the chat model
	protected ChatModelType model = null;
	// Client for interacting with the chat model
	protected ChatClient chatClient = null;

	/**
	 * Default constructor for GAbstractConfigurableChatModel.
	 */
	public GAbstractConfigurableChatModel() {

	}

	/**
	 * Retrieves the code associated with the model configuration.
	 * 
	 * @return The code of the model configuration if available, otherwise null.
	 */
	@Override
	public String getCode() {

		return config != null ? config.getCode() : null;
	}

	/**
	 * Retrieves the description associated with the model configuration.
	 * 
	 * @return The description of the model configuration if available, otherwise null.
	 */
	@Override
	public String getDescription() {

		return config != null ? config.getDescription() : null;
	}

	/**
	 * Retrieves the type of chat model.
	 * 
	 * @return The chat model type.
	 */
	@Override
	public GChatModelType getType() {

		return type;
	}

	/**
	 * Abstract method for configuring the chat model.
	 * Implementations must define how the model is configured.
	 * 
	 * @param config The model configuration to use.
	 * @param type The type of the chat model.
	 * @return An instance of ChatModelType.
	 * @throws LLMConfigException If configuration fails.
	 */
	protected abstract ChatModelType configureModel(ModelConfig config, GChatModelType type) throws LLMConfigException;

	/**
	 * Initializes the chat model with the given configuration and type.
	 * 
	 * @param config The model configuration to use.
	 * @param type The type of the chat model.
	 * @throws LLMConfigException If initialization fails.
	 */
	@Override
	public void initialize(ModelConfig config, GChatModelType type) throws LLMConfigException {
		this.config = config;
		this.type = type;
		this.model = configureModel(config, type);
		this.chatClient = ChatClient.create(configureModel(config, type));
	}

	/**
	 * Retrieves the current model configuration.
	 * 
	 * @return The current model configuration.
	 */
	@Override
	public ModelConfig getConfig() {

		return config;
	}

	/**
	 * Placeholder method for deleting the model.
	 * 
	 * @throws LLMConfigException If deletion fails.
	 */
	@Override
	public void delete() throws LLMConfigException {

	}

	/**
	 * Reconfigures the model with a new configuration.
	 * 
	 * @param config The new configuration to use.
	 * @throws LLMConfigException If reconfiguration fails.
	 */
	@Override
	public void reconfigure(ModelConfig config) throws LLMConfigException {
		this.initialize(config, type);

	}

	/**
	 * Retrieves the chat model instance.
	 * 
	 * @return The current chat model.
	 */
	@Override
	public ChatModel getChatModel() {

		return model;
	}

	/**
	 * Retrieves metadata for the chat model choice.
	 * 
	 * @return An instance of GBaseChatModelChoice or null if unavailable.
	 */
	protected GBaseChatModelChoice getChatModelMeta() {
		return config != null && config.getChoosedModel() != null ? ((GBaseChatModelChoice) config.getChoosedModel())
				: null;
	}

	/**
	 * Checks if the model supports function calls.
	 * 
	 * @return True if function calls are supported, otherwise false.
	 */
	@Override
	public boolean isSupportsFunctionsCall() {
		GBaseChatModelChoice choice = getChatModelMeta();
		if (choice != null && choice.getSupportsFunctionCalls() != null) {
			return choice.getSupportsFunctionCalls();
		}
		return IGConfigurableChatModel.super.isSupportsFunctionsCall();
	}

	/**
	 * Checks if the model supports structured output.
	 * 
	 * @return True if structured output is supported, otherwise false.
	 */
	@Override
	public boolean isSupportsStructuredOutput() {
		GBaseChatModelChoice choice = getChatModelMeta();
		if (choice != null && choice.getSupportsStructuredOutput() != null) {
			return choice.getSupportsStructuredOutput();
		}
		return IGConfigurableChatModel.super.isSupportsStructuredOutput();
	}

	/**
	 * Retrieves the context length for the chat model.
	 * Determines the length from configuration or model choice metadata if not explicitly set.
	 * 
	 * @return The context length, defaulting to 8192 if unspecified.
	 */
	@Override
	public int getContextLength() {
		Integer contextLength = null;
		if (getConfig() != null && contextLength == null) {
			contextLength = getConfig().getContextLength();
			if (contextLength == null && getConfig().getChoosedModel() != null) {
				GBaseChatModelChoice choice = (GBaseChatModelChoice) getConfig().getChoosedModel();
				contextLength = choice.getContextLength();
				if (contextLength == null) {
					if (choice.getMetaInfos() != null) {
						contextLength = choice.getMetaInfos().getContextLength();
					}
				}
			}

		}
		if (contextLength == null)
			contextLength = 8192;
		return contextLength;
	}

	/**
	 * Retrieves the chat client associated with the model.
	 * 
	 * @return The chat client for interacting with the model.
	 */
	public ChatClient getChatClient() {
		return chatClient;
	}
}