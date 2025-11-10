/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.llms.mistralai.services;

import java.util.HashSet;
import java.util.List;

import org.springframework.ai.mistralai.MistralAiChatModel;
import org.springframework.ai.mistralai.MistralAiChatOptions;
import org.springframework.ai.mistralai.api.MistralAiApi;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.ai.IGToolCallbackSourceRepositoryPattern;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.crypting.services.GeboCryptSecretException;
import ai.gebo.llms.abstraction.layer.model.GChatModelType;
import ai.gebo.llms.abstraction.layer.services.GAbstractConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.IGChatModelConfigurationSupportService;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.IGLlmsServiceClientsProvider;
import ai.gebo.llms.abstraction.layer.services.IGLlmsServiceClientsProviderFactory;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.abstraction.layer.services.ModelRuntimeConfigureHandler;
import ai.gebo.llms.mistralai.model.GMistralChatModelChoice;
import ai.gebo.llms.mistralai.model.GMistralChatModelConfig;
import ai.gebo.model.OperationStatus;
import ai.gebo.secrets.model.AbstractGeboSecretContent;
import ai.gebo.secrets.model.GeboSecretType;
import ai.gebo.secrets.model.GeboTokenContent;
import ai.gebo.secrets.services.IGeboSecretsAccessService;
import io.micrometer.observation.ObservationRegistry;
import lombok.AllArgsConstructor;

/**
 * AI generated comments Service class responsible for configuring and creating
 * Mistral AI chat models. This service is conditional on the
 * 'ai.gebo.llms.config.mistralAIEnabled' property being set to 'true'.
 */
@ConditionalOnProperty(prefix = "ai.gebo.llms.config", name = "mistralAIEnabled", havingValue = "true")
@Service
@AllArgsConstructor
public class MistralChatModelConfigurationSupportService
		implements IGChatModelConfigurationSupportService<GMistralChatModelChoice, GMistralChatModelConfig> {
	/**
	 * Static definition of the chat model type with code and description.
	 */
	static final GChatModelType type = new GChatModelType();
	static {
		type.setCode("chatmodel-mistral");
		type.setDescription("chat model service hosted on Mistral AI");
		type.setModelConfigurationClass(GMistralChatModelConfig.class.getName());
	}

	/**
	 * Service to access secret credentials for API authentication.
	 */
	final IGeboSecretsAccessService secretService;
	/*
	 * @Autowired IGOpenAIApiUtil openaiApiUtil;
	 */

	/**
	 * Repository providing access to function call definitions.
	 */
	final IGToolCallbackSourceRepositoryPattern functionsRepo;
	final MistralModelsLookupService mistralModelsLookupService;
	final IGLlmsServiceClientsProviderFactory serviceClientsProviderFactory;
	final ModelRuntimeConfigureHandler configureHandler;

	/**
	 * Inner class that implements the configuration and creation of Mistral AI chat
	 * models.
	 */
	class MistralConfigurableChatModel
			extends GAbstractConfigurableChatModel<GMistralChatModelConfig, MistralAiChatModel> {

		/**
		 * Configures a MistralAiChatModel instance based on the provided configuration.
		 * 
		 * @param config The Mistral AI configuration
		 * @param type   The chat model type
		 * @return A configured MistralAiChatModel instance
		 * @throws LLMConfigException If there are errors in configuration or
		 *                            credentials
		 */
		@Override
		protected MistralAiChatModel configureModel(GMistralChatModelConfig config, GChatModelType type)
				throws LLMConfigException {
			String apiKey = null;
			String user = null;
			if (config.getApiSecretCode() == null || config.getApiSecretCode().trim().length() == 0)
				throw new LLMConfigException("Mistral AI api cannot work without needed api key configuration");
			try {
				AbstractGeboSecretContent secret = secretService.getSecretContentById(config.getApiSecretCode());
				if (secret.type() == GeboSecretType.TOKEN) {
					GeboTokenContent token = (GeboTokenContent) secret;
					apiKey = token.getToken();
					user = token.getUser();
				} else {
					throw new LLMConfigException("Mistral AI api can work only with an api key of type TOKEN");
				}
			} catch (GeboCryptSecretException e) {
				throw new LLMConfigException("Mistral AI api  key configuration gone wrong ", e);
			}
			MistralAiApi openaiApi = new MistralAiApi(apiKey);
			org.springframework.ai.mistralai.MistralAiChatOptions.Builder builder = MistralAiChatOptions.builder();
			IGLlmsServiceClientsProvider clientsProvider = serviceClientsProviderFactory.get(getCode());
			org.springframework.web.client.RestClient.Builder restClient = clientsProvider.getRestClientBuilder();
			org.springframework.web.reactive.function.client.WebClient.Builder webClient = clientsProvider
					.getWebClientBuilder();
			RetryTemplate retryTemplate = clientsProvider.getRetryTemplate();
			
			if (config.getChoosedModel() != null) {
				builder = builder.model(config.getChoosedModel().getCode());
			}
			if (config.getTemperature() != null && config.getTemperature() > 0) {
				builder = builder.temperature(config.getTemperature());
			}
			if (config.getTopP() != null && config.getTopP() > 0) {
				builder = builder.topP(config.getTopP());
			}

			if (config.getEnabledFunctions() != null && !config.getEnabledFunctions().isEmpty()) {
				List<ToolCallback> functions = functionsRepo.getTools((config.getEnabledFunctions()));

				builder = builder.toolCallbacks(functions);
				List<String> names = functions.stream().map(x -> {
					return x.getToolDefinition().name();
				}).toList();
				builder = builder.toolNames(new HashSet<String>(names));

			}

			MistralAiChatOptions options = builder.build();
			MistralAiChatModel model = new MistralAiChatModel(openaiApi, options,
					functionsRepo.createToolCallingManager(), retryTemplate,
					ObservationRegistry.NOOP);
			return model;
		}

		/**
		 * Creates a base configuration for Mistral AI chat model.
		 * 
		 * @return A new GMistralChatModelConfig with the model type code set
		 */
		public GMistralChatModelConfig createBaseConfiguration() {
			GMistralChatModelConfig config = new GMistralChatModelConfig();
			config.setModelTypeCode(getCode());
			return config;
		}

		/**
		 * Indicates whether this model supports function calls.
		 * 
		 * @return true as Mistral AI supports function calls
		 */
		@Override
		public boolean isSupportsFunctionsCall() {
			return true;
		}
	};

	/**
	 * Returns the type of this chat model service.
	 * 
	 * @return The GChatModelType for Mistral AI
	 */
	@Override
	public GChatModelType getType() {
		return type;
	}

	/**
	 * Creates a configured chat model based on the provided configuration.
	 * 
	 * @param config The Mistral AI configuration
	 * @return A configured chat model instance
	 * @throws LLMConfigException If there are errors in configuration
	 */
	@Override
	public IGConfigurableChatModel<GMistralChatModelConfig> create(GMistralChatModelConfig config)
			throws LLMConfigException {
		MistralConfigurableChatModel model = new MistralConfigurableChatModel();
		model.initialize(config, type);
		return model;
	}

	/**
	 * Returns a list of available model choices for Mistral AI.
	 * 
	 * @param config The Mistral AI configuration
	 * @return An OperationStatus containing the list of available model choices
	 */
	@Override
	public OperationStatus<List<GMistralChatModelChoice>> getModelChoices(GMistralChatModelConfig config) {

		return mistralModelsLookupService.getChatModelChoices(config);
	}

	/**
	 * Creates a base configuration with a preset model.
	 * 
	 * @param presetModel The model identifier to use as preset
	 * @return A new GMistralChatModelConfig with the preset model configured
	 */
	@Override
	public GMistralChatModelConfig createBaseConfiguration(String presetModel) {
		GMistralChatModelConfig clean = new GMistralChatModelConfig();
		clean.setChoosedModel(new GMistralChatModelChoice());
		clean.getChoosedModel().setCode(presetModel);
		clean.getChoosedModel().setDescription("chat model " + presetModel);
		clean.setDescription("Mistral AI chat model " + presetModel);
		clean.setModelTypeCode(getType().getCode());
		return clean;
	}

	@Override
	public OperationStatus<GMistralChatModelConfig> insertAndConfigure(GMistralChatModelConfig config) throws GeboPersistenceException, LLMConfigException {
		
		return configureHandler.insertAndConfigure(config, type);
	}
}