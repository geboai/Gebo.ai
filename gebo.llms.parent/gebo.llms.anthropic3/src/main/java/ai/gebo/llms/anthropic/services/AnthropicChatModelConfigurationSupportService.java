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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.anthropic.AnthropicChatModel;
import org.springframework.ai.anthropic.AnthropicChatOptions;
import org.springframework.ai.anthropic.AnthropicChatOptions.Builder;
import org.springframework.ai.model.tool.ToolCallingManager;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.stereotype.Service;

import com.anthropic.models.messages.OutputConfig;

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
@Service
@AllArgsConstructor
public class AnthropicChatModelConfigurationSupportService
		implements IGChatModelConfigurationSupportService<GAnthropicChatModelChoice, GAnthropicChatModelConfig> {

	private static final Logger LOGGER = LoggerFactory.getLogger(AnthropicChatModelConfigurationSupportService.class);

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

			// Claude expresses thinking in two generations of api, and which one a model
			// takes is not negotiable:
			// - the current models let the model decide adaptively and take the depth as an
			//   effort level. They reject a token budget outright, with a 400.
			// - the older ones (4.5 and earlier, of which claude-haiku-4-5 is the one this
			//   installation presets) know neither adaptive nor effort: they want an
			//   explicit budget in tokens, which has to be at least 1024 and stay below
			//   maxTokens - a pair we cannot derive when no generation cap is configured.
			// Sending the wrong one of the two is a failed call rather than a degraded
			// answer, so only the adaptive form is sent here and a model of the older
			// generation keeps the provider default, exactly as before this was mapped.
			// Newer models default to the adaptive branch: the exclusion names the
			// generations known to need a budget instead of listing the ones that do not,
			// so a model released after this code keeps working.
			if (config.getThinking() != null) {
				String modelCode = config.getChoosedModel() != null ? config.getChoosedModel().getCode() : null;
				if (isBudgetOnlyThinkingModel(modelCode)) {
					if (LOGGER.isDebugEnabled()) {
						LOGGER.debug(
								"Chat model {} asks for thinking {} but {} belongs to the claude generation configured"
										+ " by token budget, which needs a generation cap to be expressed: the model is"
										+ " left at the provider default",
								config.getCode(), config.getThinking(), modelCode);
					}
				} else {
					switch (config.getThinking()) {
					case NO_THINKING: {
						builder = builder.thinkingDisabled();
					}
						break;
					case LOW_THINKING: {
						builder = builder.thinkingAdaptive().effort(OutputConfig.Effort.LOW);
					}
						break;
					case MEDIUM_THINKING: {
						builder = builder.thinkingAdaptive().effort(OutputConfig.Effort.MEDIUM);
					}
						break;
					case HIGH_THINKING: {
						// Our HIGH_THINKING is labelled "maximum thinking" in the admin screens,
						// so it reaches for the top of claude's scale rather than for HIGH, which
						// is merely what a request gets when it asks for nothing.
						builder = builder.thinkingAdaptive().effort(OutputConfig.Effort.MAX);
					}
						break;
					default:
						break;
					}
					if (LOGGER.isDebugEnabled()) {
						LOGGER.debug("Chat model {} ({}) configured with thinking {}", config.getCode(), modelCode,
								config.getThinking());
					}
				}
			}

			// Add any enabled functions/tools
			List<ToolCallback> functions = new ArrayList<ToolCallback>();
			if (config.getEnabledFunctions() != null && !config.getEnabledFunctions().isEmpty()) {
				functions = functionsRepo.getTools((config.getEnabledFunctions()));
				// The condition above tests the tool names that were REQUESTED. getTools filters
				// the callbacks actually available by those names, so it can return fewer - or
				// none at all, when the source that exports them failed. Configuring the model
				// from the request rather than from what resolved leaves it declaring tools it
				// will never send.
				if (functions != null && !functions.isEmpty()) {
					builder = builder.toolCallbacks(functions);
				} else {
					LOGGER.warn("Chat model " + config.getCode() + " enables "
							+ config.getEnabledFunctions().size()
							+ " tool(s) but none of them resolved to a callback, so it is configured"
							+ " without tools: " + config.getEnabledFunctions());
				}
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

	/**
	 * Whether the model configures its thinking by a token budget rather than by the
	 * adaptive mode and an effort level.
	 *
	 * <p>
	 * The claude 4.5 generation and everything before it knows neither adaptive thinking
	 * nor an effort level and answers both with a 400; the generations after it answer a
	 * token budget the same way. There is no capability flag to read this from, the
	 * model code being all that identifies the generation, so the test names the older
	 * ones: a model this code has never heard of is taken as one of the newer ones,
	 * which is the direction releases move in.
	 * </p>
	 *
	 * @param modelCode the configured model code, null when none is chosen yet
	 * @return true when the model takes a token budget
	 */
	static boolean isBudgetOnlyThinkingModel(String modelCode) {
		if (modelCode == null || modelCode.isBlank())
			return false;
		String code = modelCode.toLowerCase();
		return code.contains("-4-5") || code.contains("-4.5") || code.contains("claude-3");
	}
}