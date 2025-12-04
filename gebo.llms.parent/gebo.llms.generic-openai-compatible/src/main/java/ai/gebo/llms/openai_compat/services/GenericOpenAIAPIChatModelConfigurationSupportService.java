/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

/**
 * AI generated comments
 * Service supporting configuration for OpenAI API-compatible chat models.
 * This class implements IGChatModelConfigurationSupportService to provide configuration
 * for chat models that are compatible with the OpenAI API interface but may be provided
 * by different vendors.
 */
package ai.gebo.llms.openai_compat.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.springframework.ai.model.tool.ToolCallingManager;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.OpenAiChatOptions.Builder;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.retry.support.RetryTemplate;

import ai.gebo.architecture.ai.IGToolCallbackSourceRepositoryPattern;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.crypting.services.GeboCryptSecretException;
import ai.gebo.llms.abstraction.layer.model.GChatModelType;
import ai.gebo.llms.abstraction.layer.services.GAbstractConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.IGChatModelConfigurationSupportService;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.IGLlmsServiceClientsProvider;
import ai.gebo.llms.abstraction.layer.services.IGLlmsServiceClientsProviderFactory;
import ai.gebo.llms.abstraction.layer.services.ILLMTypeFiltrerRepositoryPattern;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.abstraction.layer.services.ModelRuntimeConfigureHandler;
import ai.gebo.llms.models.metainfos.ModelMetaInfo;
import ai.gebo.llms.openai.api.utils.IGOpenAIApiUtil;
import ai.gebo.llms.openai_compat.model.GenericOpenAIAPIChatModelChoice;
import ai.gebo.llms.openai_compat.model.GenericOpenAIAPIChatModelConfig;
import ai.gebo.llms.openai_compat.model.GenericOpenAIChatModelTypeConfig;
import ai.gebo.model.OperationStatus;
import ai.gebo.openai.integration.client.model.OpenAIApiConfig;
import ai.gebo.secrets.model.AbstractGeboSecretContent;
import ai.gebo.secrets.model.GeboSecretType;
import ai.gebo.secrets.model.GeboTokenContent;
import ai.gebo.secrets.services.IGeboSecretsAccessService;
import io.micrometer.observation.ObservationRegistry;

public class GenericOpenAIAPIChatModelConfigurationSupportService implements
		IGChatModelConfigurationSupportService<GenericOpenAIAPIChatModelChoice, GenericOpenAIAPIChatModelConfig> {
	/**
	 * Configuration for the OpenAI-compatible model type
	 */
	final GenericOpenAIChatModelTypeConfig type;

	/**
	 * Service for accessing secrets like API keys
	 */
	final IGeboSecretsAccessService secretService;

	/**
	 * Utility for working with OpenAI API
	 */
	final IGOpenAIApiUtil openaiApiUtil;

	/**
	 * Repository for tool/function callbacks
	 */
	final IGToolCallbackSourceRepositoryPattern functionsRepo;

	/**
	 * Service for retrieving model lists from providers
	 */
	final ModelsListProviderProxyService modelsListProxyService;

	/**
	 * Factory for creating service clients
	 */
	final IGLlmsServiceClientsProviderFactory serviceClientsProviderFactory;
	final ModelRuntimeConfigureHandler configureHandler;
	final ILLMTypeFiltrerRepositoryPattern llmTypeFiltrerRepoPattern;

	/**
	 * Constructor that initializes all required dependencies
	 * 
	 * @param type                          The configuration for the
	 *                                      OpenAI-compatible model type
	 * @param secretService                 Service for accessing API keys and other
	 *                                      secrets
	 * @param openaiApiUtil                 Utility for OpenAI API operations
	 * @param functionsRepo                 Repository for tool callbacks/functions
	 * @param modelsListProxyService        Service to get available models
	 * @param serviceClientsProviderFactory Factory for HTTP clients
	 */
	public GenericOpenAIAPIChatModelConfigurationSupportService(GenericOpenAIChatModelTypeConfig type,
			IGeboSecretsAccessService secretService, IGOpenAIApiUtil openaiApiUtil,
			IGToolCallbackSourceRepositoryPattern functionsRepo, ModelsListProviderProxyService modelsListProxyService,
			IGLlmsServiceClientsProviderFactory serviceClientsProviderFactory,
			ModelRuntimeConfigureHandler configureHandler, ILLMTypeFiltrerRepositoryPattern llmTypeFiltrerRepoPattern) {
		this.type = type;
		this.secretService = secretService;
		this.functionsRepo = functionsRepo;
		this.openaiApiUtil = openaiApiUtil;
		this.modelsListProxyService = modelsListProxyService;
		this.serviceClientsProviderFactory = serviceClientsProviderFactory;
		this.configureHandler = configureHandler;
		this.llmTypeFiltrerRepoPattern = llmTypeFiltrerRepoPattern;

	}

	/**
	 * Inner class that implements the configurable chat model for OpenAI-compatible
	 * APIs
	 */
	class GenericOpenAIConfigurableChatModel
			extends GAbstractConfigurableChatModel<GenericOpenAIAPIChatModelConfig, OpenAiChatModel> {

		/**
		 * Configures the OpenAI-compatible chat model with the provided configuration
		 * 
		 * @param config The configuration to apply to the model
		 * @param type   The type of chat model
		 * @return Configured OpenAiChatModel instance
		 * @throws LLMConfigException If configuration fails
		 */
		@Override
		protected OpenAiChatModel configureModel(GenericOpenAIAPIChatModelConfig config, GChatModelType type)
				throws LLMConfigException {
			String apiKey = null;
			String user = null;
			if (config.getApiSecretCode() != null && config.getApiSecretCode().trim().length() > 0) {
				try {
					// Retrieve API key from secrets service
					AbstractGeboSecretContent secret = secretService.getSecretContentById(config.getApiSecretCode());
					if (secret.type() == GeboSecretType.TOKEN) {
						GeboTokenContent token = (GeboTokenContent) secret;
						apiKey = token.getToken();
						user = token.getUser();
					} else {
						throw new LLMConfigException(
								type.getDescription() + " api can work only with an api key of type TOKEN");
					}
				} catch (GeboCryptSecretException e) {
					throw new LLMConfigException(type.getDescription() + " api  key configuration gone wrong ", e);
				}
			}
			// Determine base URL, using config override if provided
			String baseUrl = GenericOpenAIAPIChatModelConfigurationSupportService.this.type.getBaseUrl();
			if (config.getBaseUrl() != null) {
				baseUrl = config.getBaseUrl();
			}
			org.springframework.ai.openai.api.OpenAiApi.Builder apiBuilder = OpenAiApi.builder();
			IGLlmsServiceClientsProvider clientsProvider = serviceClientsProviderFactory.get(getCode());
			org.springframework.web.client.RestClient.Builder restClient = clientsProvider.getRestClientBuilder();
			org.springframework.web.reactive.function.client.WebClient.Builder webClient = clientsProvider
					.getWebClientBuilder();
			RetryTemplate retryTemplate = clientsProvider.getRetryTemplate();
			apiBuilder.restClientBuilder(restClient);
			apiBuilder.webClientBuilder(webClient);
			OpenAiApi openaiApi = apiBuilder.apiKey(apiKey).baseUrl(baseUrl).build();

			// Configure model options
			Builder builder = OpenAiChatOptions.builder();
			if (config.getChoosedModel() != null) {
				builder = builder.model(config.getChoosedModel().getCode());
			}
			if (config.getTemperature() != null && config.getTemperature() > 0) {
				builder = builder.temperature(config.getTemperature());
			}
			if (config.getTopP() != null && config.getTopP() > 0) {
				builder = builder.topP(config.getTopP());
			}

			// Configure tool callbacks (functions)
			List<ToolCallback> functionCallbacks = new ArrayList<ToolCallback>();
			if (config.getEnabledFunctions() != null && !config.getEnabledFunctions().isEmpty()) {
				List<ToolCallback> functions = functionsRepo.getTools((config.getEnabledFunctions()));
				functionCallbacks.addAll(functions);
				builder = builder.toolCallbacks(functions);
				List<String> names = functions.stream().map(x -> {
					return x.getToolDefinition().name();
				}).toList();
				builder = builder.toolNames(new HashSet<String>(names));

			}
			if (user != null) {
				builder = builder.user(user);
			}

			OpenAiChatOptions options = builder.build();

			ToolCallingManager toolCallingManager = functionsRepo.createToolCallingManager();
			OpenAiChatModel model = new OpenAiChatModel(openaiApi, options, toolCallingManager, retryTemplate,
					ObservationRegistry.create());
			return model;
		}

		/**
		 * Creates a basic configuration for the OpenAI-compatible model
		 * 
		 * @return A new configuration with the model type code set
		 */
		public GenericOpenAIAPIChatModelConfig createBaseConfiguration() {
			GenericOpenAIAPIChatModelConfig config = new GenericOpenAIAPIChatModelConfig();
			config.setModelTypeCode(getCode());
			return config;
		}

		/**
		 * Indicates whether this model supports function calling
		 * 
		 * @return true because OpenAI-compatible models support function calling
		 */
		@Override
		public boolean isSupportsFunctionsCall() {
			return true;
		}

	};

	/**
	 * Returns the model type this service supports
	 * 
	 * @return The chat model type
	 */
	@Override
	public GChatModelType getType() {
		return type;
	}

	/**
	 * Creates a new configured model instance with the provided configuration
	 * 
	 * @param config The model configuration
	 * @return A configured chat model instance
	 * @throws LLMConfigException If configuration fails
	 */
	@Override
	public IGConfigurableChatModel<GenericOpenAIAPIChatModelConfig> create(GenericOpenAIAPIChatModelConfig config)
			throws LLMConfigException {
		GenericOpenAIConfigurableChatModel model = new GenericOpenAIConfigurableChatModel();
		model.initialize(config, type);
		return model;
	}

	/**
	 * Retrieves available model choices for the given configuration
	 * 
	 * @param config The configuration to use for querying available models
	 * @return Operation status containing a list of available model choices
	 */
	@Override
	public OperationStatus<List<GenericOpenAIAPIChatModelChoice>> getModelChoices(
			GenericOpenAIAPIChatModelConfig config) {
		OperationStatus<List<GenericOpenAIAPIChatModelChoice>> result = null;
		OpenAIApiConfig providerConfig = OpenAIApiConfig.of(config, false);
		providerConfig.setProviderId(type.getProviderId());
		if (providerConfig.getBasePath() == null)
			providerConfig.setBasePath(type.getBaseUrl());
		if (type.getModelsListProvider() != null && type.getModelsListProvider().trim().length() > 0) {
			result = this.modelsListProxyService.geModels(type.getModelsListProvider(), config,
					GenericOpenAIAPIChatModelChoice.class, type);
		} else
			result = this.openaiApiUtil.getChatModels(GenericOpenAIAPIChatModelChoice.class, providerConfig, config,
					(choice) -> {
						return new ModelMetaInfo();
					}, type);

		return llmTypeFiltrerRepoPattern.filterChatModels(type, result);
	}

	/**
	 * Creates a base configuration with a preset model
	 * 
	 * @param presetModel The model identifier to use
	 * @return A new configuration with the preset model specified
	 */
	@Override
	public GenericOpenAIAPIChatModelConfig createBaseConfiguration(String presetModel) {
		GenericOpenAIAPIChatModelConfig clean = new GenericOpenAIAPIChatModelConfig();
		clean.setChoosedModel(new GenericOpenAIAPIChatModelChoice());
		clean.getChoosedModel().setCode(presetModel);
		clean.getChoosedModel().setDescription("chat model " + presetModel);
		clean.setDescription(type.getDescription() + " " + presetModel);
		clean.setModelTypeCode(getType().getCode());
		return clean;
	}

	@Override
	public OperationStatus<GenericOpenAIAPIChatModelConfig> insertAndConfigure(GenericOpenAIAPIChatModelConfig config)
			throws GeboPersistenceException, LLMConfigException {

		return configureHandler.insertAndConfigure(config, type);
	}

}