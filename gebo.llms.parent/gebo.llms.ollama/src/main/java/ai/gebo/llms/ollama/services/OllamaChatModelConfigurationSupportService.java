/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.llms.ollama.services;

import java.util.List;

import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.api.OllamaApi;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.ai.ollama.api.OllamaOptions.Builder;
import org.springframework.ai.ollama.management.ModelManagementOptions;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.ai.IGToolCallbackSourceRepositoryPattern;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.llms.abstraction.layer.model.GChatModelType;
import ai.gebo.llms.abstraction.layer.services.GAbstractConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.IGChatModelConfigurationSupportService;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.IGLlmsServiceClientsProvider;
import ai.gebo.llms.abstraction.layer.services.IGLlmsServiceClientsProviderFactory;
import ai.gebo.llms.abstraction.layer.services.IGModelChoiceMetaInfoEnricherService;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.abstraction.layer.services.ModelRuntimeConfigureHandler;
import ai.gebo.llms.models.metainfos.ModelMetaInfo;
import ai.gebo.llms.ollama.model.GOllamaChatModelChoice;
import ai.gebo.llms.ollama.model.GOllamaChatModelConfig;
import ai.gebo.model.OperationStatus;
import ai.gebo.secrets.services.IGeboSecretsAccessService;
import io.micrometer.observation.ObservationRegistry;
import jakarta.el.MethodNotFoundException;
import lombok.AllArgsConstructor;

/**
 * AI generated comments
 * 
 * This service provides configuration support for Ollama chat models. It
 * enables the creation and configuration of Ollama chat models based on
 * provided configurations. The service is only active when the 'ollamaEnabled'
 * property is set to true.
 */
@ConditionalOnProperty(prefix = "ai.gebo.llms.config", name = "ollamaEnabled", havingValue = "true")
@Service
@AllArgsConstructor
public class OllamaChatModelConfigurationSupportService
		implements IGChatModelConfigurationSupportService<GOllamaChatModelChoice, GOllamaChatModelConfig> {
	/**
	 * Static definition of the Ollama chat model type with its metadata
	 */
	static final GChatModelType type = new GChatModelType();
	static {
		type.setCode("chat-ollama");
		type.setDescription("Chat models hosted on local Ollama server");
		type.setModelConfigurationClass(GOllamaChatModelConfig.class.getName());
	}

	/**
	 * Service for looking up available Ollama models
	 */
	final OllamaModelsLookupService modelsService;

	/**
	 * Repository for accessing tool/function callbacks
	 */
	final IGToolCallbackSourceRepositoryPattern functionsRepo;

	/**
	 * Service for enriching model choice metadata
	 */
	final IGModelChoiceMetaInfoEnricherService choiceEnricher;

	/**
	 * Service for accessing secrets
	 */
	final IGeboSecretsAccessService secretService;

	/**
	 * Factory for creating LLM service clients
	 */
	final IGLlmsServiceClientsProviderFactory serviceClientsProviderFactory;
	final ModelRuntimeConfigureHandler configureHandler;

	/**
	 * Inner class that implements the configurable chat model for Ollama
	 */
	class OllamaConfigurableChatModel extends GAbstractConfigurableChatModel<GOllamaChatModelConfig, OllamaChatModel> {

		/**
		 * Configures and creates an Ollama chat model based on the provided
		 * configuration
		 * 
		 * @param config The configuration for the Ollama chat model
		 * @param type   The type of chat model
		 * @return The configured Ollama chat model
		 * @throws LLMConfigException If configuration fails
		 */
		@Override
		protected OllamaChatModel configureModel(GOllamaChatModelConfig config, GChatModelType type)
				throws LLMConfigException {
			org.springframework.ai.ollama.api.OllamaApi.Builder apiBuilder = OllamaApi.builder();
			apiBuilder.baseUrl(config.getBaseUrl());
			IGLlmsServiceClientsProvider clientsProvider = serviceClientsProviderFactory.get(getCode());
			org.springframework.web.client.RestClient.Builder restClient = clientsProvider.getRestClientBuilder();
			org.springframework.web.reactive.function.client.WebClient.Builder webClient = clientsProvider
					.getWebClientBuilder();
			RetryTemplate retryTemplate = clientsProvider.getRetryTemplate();
			apiBuilder.restClientBuilder(restClient);
			apiBuilder.webClientBuilder(webClient);
			OllamaApi ollamaapi = apiBuilder.build();
			Builder builder = OllamaOptions.builder();

			// Set the model if specified
			if (config.getChoosedModel() != null) {
				builder = builder.model(config.getChoosedModel().getCode());
			}

			// Configure temperature if specified
			if (config.getTemperature() != null && config.getTemperature() > 0) {
				builder = builder.temperature(config.getTemperature());
			}

			// Configure topP if specified
			if (config.getTopP() != null && config.getTopP() > 0) {
				builder = builder.topP(config.getTopP());
			}

			// Configure tool callbacks (functions) if enabled
			if (config.getEnabledFunctions() != null && !config.getEnabledFunctions().isEmpty()) {
				List<ToolCallback> functions = functionsRepo.getTools((config.getEnabledFunctions()));
				builder = builder.toolCallbacks(functions);
			}
			OllamaOptions options = builder.build();
			OllamaChatModel model = new OllamaChatModel(ollamaapi, options, functionsRepo.createToolCallingManager(),
					ObservationRegistry.create(), ModelManagementOptions.defaults());
			return model;
		}

		/**
		 * Indicates that this model supports function/tool calling
		 * 
		 * @return true, as Ollama models support function calls
		 */
		@Override
		public boolean isSupportsFunctionsCall() {
			return true;
		}
	};

	/**
	 * Returns the type of chat model this service supports
	 * 
	 * @return The Ollama chat model type
	 */
	@Override
	public GChatModelType getType() {
		return type;
	}

	/**
	 * Creates and initializes a configurable chat model based on the provided
	 * configuration
	 * 
	 * @param config The configuration for the Ollama chat model
	 * @return A configured chat model
	 * @throws LLMConfigException If configuration fails
	 */
	@Override
	public IGConfigurableChatModel<GOllamaChatModelConfig> create(GOllamaChatModelConfig config)
			throws LLMConfigException {
		OllamaConfigurableChatModel model = new OllamaConfigurableChatModel();
		model.initialize(config, type);
		return model;
	}

	/**
	 * Retrieves the available model choices for Ollama and enriches them with
	 * metadata
	 * 
	 * @param config The configuration to use for retrieving model choices
	 * @return An operation status containing the list of model choices
	 */
	@Override
	public OperationStatus<List<GOllamaChatModelChoice>> getModelChoices(GOllamaChatModelConfig config) {
		OperationStatus<List<GOllamaChatModelChoice>> rv = modelsService.getChatModels(config);
		if (rv.getResult() != null)
			this.choiceEnricher.enrichChatModelMetaInfos("ollama", rv.getResult(), (choice) -> new ModelMetaInfo());
		return rv;
	}

	/**
	 * Creates a base configuration for Ollama with a preset model
	 * 
	 * @param presetModel The preset model to use
	 * @return A base configuration
	 * @throws MethodNotFoundException This method is not implemented for Ollama
	 */
	@Override
	public GOllamaChatModelConfig createBaseConfiguration(String presetModel) {
		GOllamaChatModelConfig clean = new GOllamaChatModelConfig();
		clean.setModelTypeCode(getType().getCode());
		clean.setChoosedModel(new GOllamaChatModelChoice());
		clean.getChoosedModel().setCode(presetModel);
		clean.getChoosedModel().setDescription("chat model " + presetModel);
		clean.setDescription("Ollama chat model " + presetModel);
		clean.setModelTypeCode(getType().getCode());
		return clean;
	}

	@Override
	public OperationStatus<GOllamaChatModelConfig> insertAndConfigure(GOllamaChatModelConfig config)
			throws GeboPersistenceException, LLMConfigException {

		return configureHandler.insertAndConfigure(config, type);
	}
}