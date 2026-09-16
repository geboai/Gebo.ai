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
 * This service provides OpenAI Chat model configuration support for the Gebo AI platform.
 * It implements the IGChatModelConfigurationSupportService interface for OpenAI-specific model configurations.
 * The service is conditionally enabled when the openAIEnabled property is set to true.
 */
package ai.gebo.llms.openai.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.model.tool.ToolCallingManager;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.OpenAiChatOptions.Builder;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.ai.service.IGDocumentContentRendererProvider;
import ai.gebo.architecture.ai.service.IGToolCallbackSourceRepositoryPattern;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.crypting.services.GeboCryptSecretException;
import ai.gebo.llms.abstraction.layer.model.GBaseModelChoice;
import ai.gebo.llms.abstraction.layer.model.GChatModelType;
import ai.gebo.llms.abstraction.layer.services.GAbstractConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.IChatModelUsageAdvisorFactory;
import ai.gebo.llms.abstraction.layer.services.IGChatModelConfigurationSupportService;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.IGLlmsServiceClientsProvider;
import ai.gebo.llms.abstraction.layer.services.IGLlmsServiceClientsProviderFactory;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.abstraction.layer.services.ModelRuntimeConfigureHandler;
import ai.gebo.llms.models.metainfos.ModelMetaInfo;
import ai.gebo.llms.openai.api.utils.IGOpenAIApiUtil;
import ai.gebo.llms.openai.http.OpenAiClientCustomizer;
import ai.gebo.llms.openai.model.GOpenAIChatModelChoice;
import ai.gebo.llms.openai.model.GOpenAIChatModelConfig;
import ai.gebo.model.OperationStatus;
import ai.gebo.openai.integration.client.model.OpenAIApiConfig;
import ai.gebo.secrets.model.AbstractGeboSecretContent;
import ai.gebo.secrets.model.GeboSecretType;
import ai.gebo.secrets.model.GeboTokenContent;
import ai.gebo.secrets.services.IGeboSecretsAccessService;
import io.micrometer.observation.ObservationRegistry;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class OpenAIChatModelConfigurationSupportService
		implements IGChatModelConfigurationSupportService<GOpenAIChatModelChoice, GOpenAIChatModelConfig> {

	private static final Logger LOGGER = LoggerFactory.getLogger(OpenAIChatModelConfigurationSupportService.class);
	/**
	 * Static model type definition for the OpenAI chat model.
	 */
	static final GChatModelType type = new GChatModelType();
	static {
		type.setCode("chatgpt-OpenAI");
		type.setDescription("chatgpt service hosted on OpenAI");
		type.setModelConfigurationClass(GOpenAIChatModelConfig.class.getName());
	}

	final IGeboSecretsAccessService secretService;
	final IGOpenAIApiUtil openaiApiUtil;
	final IGToolCallbackSourceRepositoryPattern functionsRepo;
	final IGLlmsServiceClientsProviderFactory serviceClientsProviderFactory;
	final ModelRuntimeConfigureHandler configureHandler;
	final IGDocumentContentRendererProvider documentContentRenderProvider;
	final IChatModelUsageAdvisorFactory usageAdvisorFactory;
	final ObservationRegistry observationRegistry;

	/**
	 * Implementation of a configurable chat model for OpenAI. This class handles
	 * the creation and configuration of OpenAI chat models.
	 */
	class OpenAIConfigurableChatModel extends GAbstractConfigurableChatModel<GOpenAIChatModelConfig, OpenAiChatModel> {

		public OpenAIConfigurableChatModel(IGDocumentContentRendererProvider rendererFactory,
				IGToolCallbackSourceRepositoryPattern toolCallbacksRepository,
				IChatModelUsageAdvisorFactory usageAdvisorFactory, ObservationRegistry observationRegistry) {
			super(rendererFactory, toolCallbacksRepository, usageAdvisorFactory, observationRegistry);

		}

		String apiKey = null;
		String user = null;

		/**
		 * Configures the OpenAI chat model based on the provided configuration.
		 *
		 * @param config The OpenAI chat model configuration
		 * @param type   The chat model type
		 * @return The configured OpenAI chat model
		 * @throws LLMConfigException if there is an error in configuration
		 */
		@Override
		protected OpenAiChatModel configureModel(GOpenAIChatModelConfig config, GChatModelType type,
				ToolCallingManager toolsCallsManager) throws LLMConfigException {

			if (config.getApiSecretCode() == null || config.getApiSecretCode().trim().length() == 0)
				throw new LLMConfigException("OpenAI api cannot work without needed api key configuration");
			try {
				AbstractGeboSecretContent secret = secretService.getSecretContentById(config.getApiSecretCode());
				if (secret.type() == GeboSecretType.TOKEN) {
					GeboTokenContent token = (GeboTokenContent) secret;
					apiKey = token.getToken();
					user = token.getUser();
				} else {
					throw new LLMConfigException("OpenAI api can work only with an api key of type TOKEN");
				}
			} catch (GeboCryptSecretException e) {
				throw new LLMConfigException("OpenAI api  key configuration gone wrong ", e);
			}
			Builder builder = OpenAiChatOptions.builder().apiKey(apiKey);

			if (config.getChoosedModel() != null) {
				builder = builder.model(config.getChoosedModel().getCode());
			}
			if (config.getTemperature() != null) {
				builder = builder.temperature(config.getTemperature());
			}
			if (config.getTopP() != null && config.getTopP() > 0) {
				builder = builder.topP(config.getTopP());
			}

			if (config.getEnabledFunctions() != null && !config.getEnabledFunctions().isEmpty()) {
				List<ToolCallback> functions = functionsRepo.getTools((config.getEnabledFunctions()));
				// The condition above tests the tool names that were REQUESTED. getTools filters
				// the callbacks actually available by those names, so it can return fewer - or
				// none at all, when the source that exports them failed. Configuring the model
				// from the request rather than from what resolved leaves it declaring tools it
				// will never send.
				// On the OpenAI wire format that combination is not merely useless, it is
				// rejected: parallel_tool_calls without tools returns
				//   400 Invalid value for 'parallel_tool_calls': 'parallel_tool_calls' is only
				//   allowed when 'tools' are specified.
				// which failed every routing agent call, a routing plan being a structured
				// response that carries no tools of its own. Compatible endpoints tolerated it,
				// so it only surfaced once a model was pointed at api.openai.com.
				if (functions != null && !functions.isEmpty()) {
					builder = builder.toolCallbacks(functions);
					builder.parallelToolCalls(true);
				} else {
					LOGGER.warn("Chat model " + config.getCode() + " enables "
							+ config.getEnabledFunctions().size()
							+ " tool(s) but none of them resolved to a callback, so it is configured"
							+ " without tools: " + config.getEnabledFunctions());
				}
			}
			if (config != null && config.getMaxGeneratedTokens() != null && config.getMaxGeneratedTokens() > 0) {
				builder.maxTokens(config.getMaxGeneratedTokens());
			}
			if (config.getThinking() != null) {
				switch (config.getThinking()) {
				case LOW_THINKING: {
					builder.reasoningEffort("low");
				}
					break;
				case MEDIUM_THINKING: {
					builder.reasoningEffort("medium");
				}
					break;
				case HIGH_THINKING: {
					builder.reasoningEffort("high");
				}
					break;
				}
			}
			if (user != null) {
				builder = builder.user(user);
			}

			builder.timeout(OpenAiClientCustomizer.requestTimeout(serviceClientsProviderFactory.get(getCode())));
			OpenAiChatOptions options = builder.build();
			ToolCallingManager toolCallingManager = toolsCallsManager != null ? toolsCallsManager
					: functionsRepo.createToolCallingManager();
			OpenAiChatModel model = OpenAiChatModel.builder()
					.options(options)
					.toolCallingManager(toolCallingManager)
					.observationRegistry(observationRegistry)
					.httpClientBuilderCustomizer(OpenAiClientCustomizer.from(serviceClientsProviderFactory.get(getCode())))
					.build();

			return model;
		}

		/**
		 * Creates a base configuration for the OpenAI chat model.
		 * 
		 * @return A new OpenAI chat model configuration
		 */
		public GOpenAIChatModelConfig createBaseConfiguration() {
			GOpenAIChatModelConfig config = new GOpenAIChatModelConfig();
			config.setModelTypeCode(getCode());
			return config;
		}

		/**
		 * Indicates whether the model supports function calls.
		 * 
		 * @return true as OpenAI models support function calls
		 */
		@Override
		public boolean isSupportsFunctionsCall() {
			return true;
		}

		@Override
		protected IGConfigurableChatModel cloneMeWithInjection() {

			return new OpenAIConfigurableChatModel(rendererFactory, toolCallbacksRepository, usageAdvisorFactory,
					observationRegistry);
		}
	};

	/**
	 * Gets the model type supported by this service.
	 * 
	 * @return The OpenAI chat model type
	 */
	@Override
	public GChatModelType getType() {
		return type;
	}

	/**
	 * Creates a new configurable chat model with the given configuration.
	 * 
	 * @param config The OpenAI chat model configuration
	 * @return A configurable chat model
	 * @throws LLMConfigException if there is an error creating the model
	 */
	@Override
	public IGConfigurableChatModel<GOpenAIChatModelConfig> create(GOpenAIChatModelConfig config)
			throws LLMConfigException {
		OpenAIConfigurableChatModel model = new OpenAIConfigurableChatModel(documentContentRenderProvider,
				functionsRepo, usageAdvisorFactory, observationRegistry);
		model.initialize(config, type);
		return model;
	}

	/**
	 * Retrieves available model choices for OpenAI chat models.
	 * 
	 * @param config The OpenAI chat model configuration
	 * @return Operation status containing available model choices
	 */
	@Override
	public OperationStatus<List<GOpenAIChatModelChoice>> getModelChoices(GOpenAIChatModelConfig config) {
		OpenAIApiConfig providerConfig = new OpenAIApiConfig();
		providerConfig.setProviderId("openai");

		return this.openaiApiUtil.getChatModels(GOpenAIChatModelChoice.class, providerConfig, config, (choice) -> {
			ModelMetaInfo meta = new ModelMetaInfo();
			meta.setInformativeUrl("https://platform.openai.com/docs/models/");
			return meta;
		}, type);
	}

	/**
	 * Creates a base configuration for an OpenAI chat model with a preset model.
	 * 
	 * @param presetModel The model identifier to preset
	 * @return A base OpenAI chat model configuration
	 */
	@Override
	public GOpenAIChatModelConfig createBaseConfiguration(String presetModel) {
		GOpenAIChatModelConfig clean = new GOpenAIChatModelConfig();
		clean.setChoosedModel(new GOpenAIChatModelChoice());
		clean.getChoosedModel().setCode(presetModel);
		clean.getChoosedModel().setDescription("chat model " + presetModel);
		clean.setDescription("OpenAI chat model " + presetModel);
		clean.setModelTypeCode(getType().getCode());
		return clean;
	}

	@Override
	public OperationStatus<GOpenAIChatModelConfig> insertAndConfigure(GOpenAIChatModelConfig config)
			throws GeboPersistenceException, LLMConfigException {

		return configureHandler.insertAndConfigure(config, type);
	}
}