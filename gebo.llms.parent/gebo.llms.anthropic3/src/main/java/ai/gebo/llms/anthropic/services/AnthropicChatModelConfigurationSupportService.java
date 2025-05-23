/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.anthropic.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.ai.anthropic.AnthropicChatModel;
import org.springframework.ai.anthropic.AnthropicChatOptions;
import org.springframework.ai.anthropic.AnthropicChatOptions.Builder;
import org.springframework.ai.anthropic.api.AnthropicApi;
import org.springframework.ai.model.tool.ToolCallingManager;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.ai.IGToolCallbackSourceRepositoryPattern;
import ai.gebo.crypting.services.GeboCryptSecretException;
import ai.gebo.llms.abstraction.layer.model.GBaseChatModelChoice;
import ai.gebo.llms.abstraction.layer.model.GChatModelType;
import ai.gebo.llms.abstraction.layer.services.GAbstractConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.IGChatModelConfigurationSupportService;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.IGLlmsServiceClientsProvider;
import ai.gebo.llms.abstraction.layer.services.IGLlmsServiceClientsProviderFactory;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.anthropic.model.GAnthropicChatModelChoice;
import ai.gebo.llms.anthropic.model.GAnthropicChatModelConfig;
import ai.gebo.model.OperationStatus;
import ai.gebo.secrets.model.AbstractGeboSecretContent;
import ai.gebo.secrets.model.GeboSecretType;
import ai.gebo.secrets.model.GeboTokenContent;
import ai.gebo.secrets.services.IGeboSecretsAccessService;
import io.micrometer.observation.ObservationRegistry;
import jakarta.el.MethodNotFoundException;

/**
 * Service for configuring and creating Anthropic chat models.
 * AI generated comments
 * This service is only enabled if the anthropicEnabled property is set to true.
 */
@ConditionalOnProperty(prefix = "ai.gebo.llms.config", name = "anthropicEnabled", havingValue = "true")
@Service
public class AnthropicChatModelConfigurationSupportService
		implements IGChatModelConfigurationSupportService<GAnthropicChatModelChoice, GAnthropicChatModelConfig> {
	
	/**
	 * Static definition of the Anthropic model type
	 */
	static final GChatModelType type = new GChatModelType();
	static {
		type.setCode("chat-anthropic");
		type.setDescription("Chat models hosted on Anthropic");
		type.setModelConfigurationClass(GAnthropicChatModelConfig.class.getName());
	}
	
	/**
	 * Service for looking up available Anthropic models
	 */
	@Autowired
	AnthropicModelsLookupService modelsService;
	
	/**
	 * List of available chat model choices
	 */
	static final List<GBaseChatModelChoice> choices = List.of();

	/**
	 * Service for accessing secrets like API keys
	 */
	@Autowired
	IGeboSecretsAccessService secretService;
	
	/**
	 * Repository for tools/functions that can be called by the models
	 */
	@Autowired
	IGToolCallbackSourceRepositoryPattern functionsRepo;
	
	/**
	 * Factory for obtaining service clients providers
	 */
	@Autowired
	IGLlmsServiceClientsProviderFactory serviceClientsProviderFactory;

	/**
	 * Inner class that implements the configurable chat model for Anthropic
	 */
	class AnthropicConfigurableChatModel
			extends GAbstractConfigurableChatModel<GAnthropicChatModelConfig, AnthropicChatModel> {

		/**
		 * Configures an Anthropic chat model based on the provided configuration
		 * 
		 * @param config The Anthropic configuration
		 * @param type The type of chat model
		 * @return A configured AnthropicChatModel instance
		 * @throws LLMConfigException If configuration fails
		 */
		@Override
		protected AnthropicChatModel configureModel(GAnthropicChatModelConfig config, GChatModelType type)
				throws LLMConfigException {
			String apiKey = null;
			String user = null;
			if (config.getApiSecretCode() == null || config.getApiSecretCode().trim().length() == 0)
				throw new LLMConfigException("Anthropic api cannot work without needed api key configuration");
			try {
				// Retrieve API key from secrets service
				AbstractGeboSecretContent secret = secretService.getSecretContentById(config.getApiSecretCode());
				if (secret.type() == GeboSecretType.TOKEN) {
					GeboTokenContent token = (GeboTokenContent) secret;
					apiKey = token.getToken();
					user = token.getUser();
				} else {
					throw new LLMConfigException("Anthropic api can work only with an api key of type TOKEN");
				}
			} catch (GeboCryptSecretException e) {
				throw new LLMConfigException("Anthropic api  key configuration gone wrong ", e);
			}
			
			// Get the client providers for making API calls
			IGLlmsServiceClientsProvider clientsProvider = serviceClientsProviderFactory.get(getCode());
			org.springframework.web.client.RestClient.Builder restClient = clientsProvider.getRestClientBuilder();
			org.springframework.web.reactive.function.client.WebClient.Builder webClient = clientsProvider
					.getWebClientBuilder();
			RetryTemplate retryTemplate = clientsProvider.getRetryTemplate();
			
			// Build the Anthropic API configuration
			org.springframework.ai.anthropic.api.AnthropicApi.Builder apiBuilder = AnthropicApi.builder();
			if (config.getBaseUrl() != null) {
				apiBuilder.baseUrl(config.getBaseUrl());
			}
			apiBuilder.apiKey(apiKey);
			apiBuilder.restClientBuilder(restClient);
			apiBuilder.webClientBuilder(webClient);
			
			AnthropicApi anthropicApi = apiBuilder.build();
			
			// Configure Anthropic chat options
			Builder builder = AnthropicChatOptions.builder();
			builder.maxTokens(16000);
			if (config.getChoosedModel() != null) {
				builder = builder.model(config.getChoosedModel().getCode());
			}
			if (config.getTemperature() != null) {
				builder = builder.temperature(config.getTemperature());
			}
			if (config.getTopP() != null) {
				builder = builder.topP(config.getTopP());
			}
			
			// Add any enabled functions/tools
			List<ToolCallback> functions = new ArrayList<ToolCallback>();
			if (config.getEnabledFunctions() != null && !config.getEnabledFunctions().isEmpty()) {
				functions = functionsRepo.getTools((config.getEnabledFunctions()));
				builder = builder.toolCallbacks(functions);
			}
			AnthropicChatOptions anthropicChatOptions = builder.build();
			ToolCallingManager toolCallingManager = functionsRepo.createToolCallingManager();

			// Create the final AnthropicChatModel
			AnthropicChatModel model = new AnthropicChatModel(anthropicApi, anthropicChatOptions, toolCallingManager,
					retryTemplate, ObservationRegistry.create());
			return model;
		}
	}

	/**
	 * Default constructor
	 */
	public AnthropicChatModelConfigurationSupportService() {
	}

	/**
	 * Returns the type of chat model this service supports
	 * 
	 * @return The Anthropic chat model type
	 */
	@Override
	public GChatModelType getType() {
		return type;
	}

	/**
	 * Creates a configurable chat model using the provided configuration
	 * 
	 * @param config The Anthropic configuration
	 * @return A configurable chat model instance
	 * @throws LLMConfigException If creation fails
	 */
	@Override
	public IGConfigurableChatModel<GAnthropicChatModelConfig> create(GAnthropicChatModelConfig config)
			throws LLMConfigException {
		AnthropicConfigurableChatModel model = new AnthropicConfigurableChatModel();
		model.initialize(config, type);
		return model;
	}

	/**
	 * Retrieves available model choices for Anthropic
	 * 
	 * @param config The configuration to use for lookup
	 * @return Operation status containing list of available model choices
	 */
	@Override
	public OperationStatus<List<GAnthropicChatModelChoice>> getModelChoices(GAnthropicChatModelConfig config) {
		return modelsService.getChatModels(config);
	}

	/**
	 * Creates a base configuration for the specified preset model
	 * 
	 * @param presetModel The preset model identifier
	 * @return Base configuration
	 * @throws MethodNotFoundException This method is not implemented for Anthropic
	 */
	@Override
	public GAnthropicChatModelConfig createBaseConfiguration(String presetModel) {
		throw new MethodNotFoundException("createBaseConfiguration() is not implemented for Anthropic chat provider");
	}
}