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
import org.springframework.ai.model.tool.ToolCallingManager;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import ai.gebo.llms.anthropic.http.AnthropicClientCustomizer;

import ai.gebo.architecture.ai.service.IGDocumentContentRendererProvider;
import ai.gebo.architecture.ai.service.IGToolCallbackSourceRepositoryPattern;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.crypting.services.GeboCryptSecretException;
import ai.gebo.llms.abstraction.layer.model.GBaseChatModelChoice;
import ai.gebo.llms.abstraction.layer.model.GChatModelType;
import ai.gebo.llms.abstraction.layer.services.GAbstractConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.IChatModelUsageAdvisorFactory;
import ai.gebo.llms.abstraction.layer.services.IGChatModelConfigurationSupportService;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.IGLlmsServiceClientsProvider;
import ai.gebo.llms.abstraction.layer.services.IGLlmsServiceClientsProviderFactory;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.abstraction.layer.services.ModelRuntimeConfigureHandler;
import ai.gebo.llms.anthropic.model.GAnthropicChatModelChoice;
import ai.gebo.llms.anthropic.model.GAnthropicChatModelConfig;
import ai.gebo.model.OperationStatus;
import ai.gebo.secrets.model.AbstractGeboSecretContent;
import ai.gebo.secrets.model.GeboSecretType;
import ai.gebo.secrets.model.GeboTokenContent;
import ai.gebo.secrets.services.IGeboSecretsAccessService;
import io.micrometer.observation.ObservationRegistry;
import jakarta.el.MethodNotFoundException;
import lombok.AllArgsConstructor;

/**
 * Service for configuring and creating Anthropic chat models. AI generated
 * comments This service is only enabled if the anthropicEnabled property is set
 * to true.
 */
@ConditionalOnProperty(prefix = "ai.gebo.llms.config", name = "anthropicEnabled", havingValue = "true")
@Service
@AllArgsConstructor
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
	final AnthropicModelsLookupService modelsService;

	/**
	 * List of available chat model choices
	 */
	static final List<GBaseChatModelChoice> choices = List.of();

	/**
	 * Service for accessing secrets like API keys
	 */
	final IGeboSecretsAccessService secretService;

	/**
	 * Repository for tools/functions that can be called by the models
	 */
	final IGToolCallbackSourceRepositoryPattern functionsRepo;

	/**
	 * Factory for obtaining service clients providers
	 */
	final IGLlmsServiceClientsProviderFactory serviceClientsProviderFactory;

	final ModelRuntimeConfigureHandler configureHandler;
	final IGDocumentContentRendererProvider documentContentRenderProvider;

	final IChatModelUsageAdvisorFactory usageAdvisorFactory;
	final ObservationRegistry observationRegistry;

	/**
	 * Inner class that implements the configurable chat model for Anthropic
	 */
	class AnthropicConfigurableChatModel
			extends GAbstractConfigurableChatModel<GAnthropicChatModelConfig, AnthropicChatModel> {

		public AnthropicConfigurableChatModel(IGDocumentContentRendererProvider rendererFactory,
				IGToolCallbackSourceRepositoryPattern toolCallbacksRepository,
				IChatModelUsageAdvisorFactory usageAdvisorFactory, ObservationRegistry observationRegistry) {
			super(rendererFactory, toolCallbacksRepository, usageAdvisorFactory, observationRegistry);
		}

		/**
		 * Configures an Anthropic chat model based on the provided configuration
		 * 
		 * @param config The Anthropic configuration
		 * @param type   The type of chat model
		 * @return A configured AnthropicChatModel instance
		 * @throws LLMConfigException If configuration fails
		 */
		@Override
		protected AnthropicChatModel configureModel(GAnthropicChatModelConfig config, GChatModelType type,
				ToolCallingManager toolsCallsManager) throws LLMConfigException {
			String apiKey = null;
			if (config.getApiSecretCode() == null || config.getApiSecretCode().trim().length() == 0)
				throw new LLMConfigException("Anthropic api cannot work without needed api key configuration");
			try {
				AbstractGeboSecretContent secret = secretService.getSecretContentById(config.getApiSecretCode());
				if (secret.type() == GeboSecretType.TOKEN) {
					apiKey = ((GeboTokenContent) secret).getToken();
				} else {
					throw new LLMConfigException("Anthropic api can work only with an api key of type TOKEN");
				}
			} catch (GeboCryptSecretException e) {
				throw new LLMConfigException("Anthropic api  key configuration gone wrong ", e);
			}

			// Get the client providers for making API calls
			IGLlmsServiceClientsProvider clientsProvider = serviceClientsProviderFactory.get(getCode());

			// Configure Anthropic chat options (apiKey lives in options in Spring AI 2.0)
			Builder builder = AnthropicChatOptions.builder();
			builder.apiKey(apiKey);
			if (config != null && config.getMaxGeneratedTokens() != null && config.getMaxGeneratedTokens() > 0) {
				builder.maxTokens(config.getMaxGeneratedTokens());
			}
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
			ToolCallingManager toolCallingManager = toolsCallsManager != null ? toolsCallsManager
					: functionsRepo.createToolCallingManager();

			// Create the final AnthropicChatModel using the builder pattern (Spring AI 2.0)
			AnthropicChatModel model = AnthropicChatModel.builder()
					.options(anthropicChatOptions)
					.toolCallingManager(toolCallingManager)
					.observationRegistry(observationRegistry)
					.httpClientBuilderCustomizer(AnthropicClientCustomizer.from(clientsProvider))
					.build();
			return model;
		}

		@Override
		protected IGConfigurableChatModel cloneMeWithInjection() {
			AnthropicConfigurableChatModel anthropicChatModel = new AnthropicConfigurableChatModel(rendererFactory,
					toolCallbacksRepository, usageAdvisorFactory, observationRegistry);
			return anthropicChatModel;
		}
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
		AnthropicConfigurableChatModel model = new AnthropicConfigurableChatModel(this.documentContentRenderProvider,
				functionsRepo, usageAdvisorFactory, observationRegistry);
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
		GAnthropicChatModelConfig clean = new GAnthropicChatModelConfig();
		clean.setModelTypeCode(getType().getCode());
		clean.setChoosedModel(new GAnthropicChatModelChoice());
		clean.getChoosedModel().setCode(presetModel);
		clean.getChoosedModel().setDescription("chat model " + presetModel);
		clean.setDescription("Anthropic AI chat model " + presetModel);
		clean.setModelTypeCode(getType().getCode());
		return clean;

	}

	@Override
	public OperationStatus<GAnthropicChatModelConfig> insertAndConfigure(GAnthropicChatModelConfig config)
			throws GeboPersistenceException, LLMConfigException {

		return configureHandler.insertAndConfigure(config, type);
	}
}