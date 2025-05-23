/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.deepseek.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.ai.deepseek.DeepSeekChatModel;
import org.springframework.ai.deepseek.DeepSeekChatOptions;
import org.springframework.ai.deepseek.api.DeepSeekApi;
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
import ai.gebo.llms.deepseek.model.GDeepseekChatModelChoice;
import ai.gebo.llms.deepseek.model.GDeepseekChatModelConfig;
import ai.gebo.model.OperationStatus;
import ai.gebo.secrets.model.AbstractGeboSecretContent;
import ai.gebo.secrets.model.GeboSecretType;
import ai.gebo.secrets.model.GeboTokenContent;
import ai.gebo.secrets.services.IGeboSecretsAccessService;
import io.micrometer.observation.ObservationRegistry;
import jakarta.el.MethodNotFoundException;

/**
 * AI generated comments
 * Service for configuring and supporting DeepSeek chat models.
 * This service is only active when the 'deepseekEnabled' property is set to true.
 */
@ConditionalOnProperty(prefix = "ai.gebo.llms.config", name = "deepseekEnabled", havingValue = "true")
@Service
public class DeepseekChatModelConfigurationSupportService
		implements IGChatModelConfigurationSupportService<GDeepseekChatModelChoice, GDeepseekChatModelConfig> {
	
	/**
	 * Static model type definition for DeepSeek chat models
	 */
	static final GChatModelType type = new GChatModelType();
	/**
	 * Constant defining the code for DeepSeek chat model type
	 */
	public static final String DEEPSEEK_CHAT_MODEL_TYPE = "chat-deepseek";
	static {
		// Initialize the model type
		type.setCode(DEEPSEEK_CHAT_MODEL_TYPE);
		type.setDescription("Chat models hosted on Deepseek");
		type.setModelConfigurationClass(GDeepseekChatModelConfig.class.getName());
	}
	
	/**
	 * Service for looking up available DeepSeek models
	 */
	@Autowired
	DeepseekModelsLookupService modelsService;
	
	/**
	 * Service for accessing secrets such as API keys
	 */
	@Autowired
	IGeboSecretsAccessService secretService;
	
	/**
	 * Repository for tool callbacks/functions that can be used with the model
	 */
	@Autowired
	IGToolCallbackSourceRepositoryPattern functionsRepo;
	
	/**
	 * Factory for providing service clients
	 */
	@Autowired
	IGLlmsServiceClientsProviderFactory serviceClientsProviderFactory;

	/**
	 * Implementation of configurable chat model for DeepSeek
	 * Note: The class is incorrectly named "AnthropicConfigurableChatModel" but implements DeepSeek functionality
	 */
	class AnthropicConfigurableChatModel
			extends GAbstractConfigurableChatModel<GDeepseekChatModelConfig, DeepSeekChatModel> {

		/**
		 * Configures the DeepSeek chat model based on the provided configuration
		 * @param config The DeepSeek configuration
		 * @param type The chat model type
		 * @return Configured DeepSeek chat model
		 * @throws LLMConfigException if configuration fails
		 */
		@Override
		protected DeepSeekChatModel configureModel(GDeepseekChatModelConfig config, GChatModelType type)
				throws LLMConfigException {
			String apiKey = null;
			String user = null;
			if (config.getApiSecretCode() == null || config.getApiSecretCode().trim().length() == 0)
				throw new LLMConfigException("Deepseek api cannot work without needed api key configuration");
			try {
				AbstractGeboSecretContent secret = secretService.getSecretContentById(config.getApiSecretCode());
				if (secret.type() == GeboSecretType.TOKEN) {
					GeboTokenContent token = (GeboTokenContent) secret;
					apiKey = token.getToken();
					user = token.getUser();
				} else {
					throw new LLMConfigException("Deepseek api can work only with an api key of type TOKEN");
				}
			} catch (GeboCryptSecretException e) {
				throw new LLMConfigException("Deepseek api key configuration gone wrong ", e);
			}
			IGLlmsServiceClientsProvider clientsProvider = serviceClientsProviderFactory.get(getCode());
			org.springframework.web.client.RestClient.Builder restClient = clientsProvider.getRestClientBuilder();
			org.springframework.web.reactive.function.client.WebClient.Builder webClient = clientsProvider
					.getWebClientBuilder();
			RetryTemplate retryTemplate = clientsProvider.getRetryTemplate();
			org.springframework.ai.deepseek.api.DeepSeekApi.Builder apiBuilder = DeepSeekApi.builder();
			if (config.getBaseUrl() != null) {
				apiBuilder.baseUrl(config.getBaseUrl());
			}
			apiBuilder.apiKey(apiKey);
			apiBuilder.restClientBuilder(restClient);
			apiBuilder.webClientBuilder(webClient);

			DeepSeekApi deepseekApi = apiBuilder.build();

			org.springframework.ai.deepseek.DeepSeekChatOptions.Builder builder = DeepSeekChatOptions.builder();
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
			List<ToolCallback> functions = new ArrayList<ToolCallback>();
			if (config.getEnabledFunctions() != null && !config.getEnabledFunctions().isEmpty()) {
				// Get tools based on enabled functions
				functions = functionsRepo.getTools((config.getEnabledFunctions()));
				builder = builder.toolCallbacks(functions);
			}
			DeepSeekChatOptions deepseekChatOptions = builder.build();
			ToolCallingManager toolCallingManager = functionsRepo.createToolCallingManager();

			DeepSeekChatModel model = new DeepSeekChatModel(deepseekApi, deepseekChatOptions, toolCallingManager,
					retryTemplate, ObservationRegistry.create());
			return model;
		}
	};

	/**
	 * Default constructor
	 */
	public DeepseekChatModelConfigurationSupportService() {
	}

	/**
	 * Returns the type of chat model supported by this service
	 * @return GChatModelType for DeepSeek
	 */
	@Override
	public GChatModelType getType() {
		return type;
	}

	/**
	 * Creates a configurable chat model with the provided configuration
	 * @param config DeepSeek configuration
	 * @return Configurable chat model instance
	 * @throws LLMConfigException if configuration fails
	 */
	@Override
	public IGConfigurableChatModel<GDeepseekChatModelConfig> create(GDeepseekChatModelConfig config)
			throws LLMConfigException {
		AnthropicConfigurableChatModel model = new AnthropicConfigurableChatModel();
		model.initialize(config, type);
		return model;
	}

	/**
	 * Retrieves the available model choices for the provided configuration
	 * @param config DeepSeek configuration
	 * @return Operation status containing the list of available model choices
	 */
	@Override
	public OperationStatus<List<GDeepseekChatModelChoice>> getModelChoices(GDeepseekChatModelConfig config) {
		return modelsService.getChatModels(config);
	}

	/**
	 * Creates a base configuration for the specified preset model
	 * @param presetModel The model preset to use
	 * @return Base configuration for the model
	 * @throws MethodNotFoundException This method is not implemented for Anthropic chat provider
	 */
	@Override
	public GDeepseekChatModelConfig createBaseConfiguration(String presetModel) {
		throw new MethodNotFoundException("createBaseConfiguration() is not implemented for Anthropic chat provider");
	}
}