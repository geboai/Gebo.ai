/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.llms.google_vertex.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.google.genai.GoogleGenAiChatModel;
import org.springframework.ai.google.genai.GoogleGenAiChatModel.ChatModel;
import org.springframework.ai.google.genai.GoogleGenAiChatOptions;
import org.springframework.ai.google.genai.common.GoogleGenAiThinkingLevel;
import org.springframework.ai.model.tool.ToolCallingManager;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.stereotype.Service;

import com.google.genai.Client;

import ai.gebo.architecture.ai.service.IGDocumentContentRendererProvider;
import ai.gebo.architecture.ai.service.IGToolCallbackSourceRepositoryPattern;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.llms.abstraction.layer.services.IGLlmsServiceClientsProviderFactory;
import ai.gebo.llms.abstraction.layer.model.GBaseModelChoice;
import ai.gebo.llms.abstraction.layer.model.GChatModelType;
import ai.gebo.llms.abstraction.layer.services.GAbstractConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.IChatModelUsageAdvisorFactory;
import ai.gebo.llms.abstraction.layer.services.IGChatModelConfigurationSupportService;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.ILLMTypeFiltrerRepositoryPattern;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.abstraction.layer.services.ModelRuntimeConfigureHandler;
import ai.gebo.llms.google_vertex.model.GGoogleVertexChatModelChoice;
import ai.gebo.llms.google_vertex.model.GGoogleVertexChatModelConfig;
import ai.gebo.model.OperationStatus;
import io.micrometer.observation.ObservationRegistry;
import lombok.AllArgsConstructor;

/**
 * AI generated comments
 * 
 * Service responsible for configuring and managing Google Vertex AI chat
 * models. This service is only active when googleVertexEnabled property is set
 * to true.
 */
@Service
@AllArgsConstructor
public class GoogleVertexChatModelConfigurationSupportService
		implements IGChatModelConfigurationSupportService<GGoogleVertexChatModelChoice, GGoogleVertexChatModelConfig> {

	private static final Logger LOGGER = LoggerFactory.getLogger(GoogleVertexChatModelConfigurationSupportService.class);
	/**
	 * Static model type definition for Google Vertex chat models
	 */
	static final GChatModelType type = new GChatModelType();
	static {
		type.setCode("chatmodel-google-vertex");
		type.setDescription("Google vertex (Gemini models)");
		type.setModelConfigurationClass(GGoogleVertexChatModelConfig.class.getName());
	}

	/**
	 * List of available Google Vertex chat model choices based on ChatModel enum
	 * values
	 */
	static final List<GGoogleVertexChatModelChoice> choices = GBaseModelChoice.of(GGoogleVertexChatModelChoice.class,
			ChatModel.values());

	/**
	 * Repository for tool callbacks used by the models
	 */
	final IGToolCallbackSourceRepositoryPattern functionsRepo;

	/**
	 * Helper service to configure VertexAI instances
	 */
	final VertexAIConfigurator configurator;
	/**
	 * Supplies the configured retry budget for the model builder; the timeouts ride
	 * on the GenAI client built by {@link VertexAIConfigurator}.
	 */
	final IGLlmsServiceClientsProviderFactory serviceClientsProviderFactory;
	final ModelRuntimeConfigureHandler configureHandler;
	final ILLMTypeFiltrerRepositoryPattern llmTypeFiltrerRepoPattern;
	final IGDocumentContentRendererProvider documentContentRenderProvider;
	final IChatModelUsageAdvisorFactory usageAdvisorFactory;
	final ObservationRegistry observationRegistry;

	/**
	 * Inner class that handles the configuration and initialization of Google
	 * Vertex chat models
	 */
	class GoogleVertexConfigurableChatModel
			extends GAbstractConfigurableChatModel<GGoogleVertexChatModelConfig, GoogleGenAiChatModel> {

		public GoogleVertexConfigurableChatModel(IGDocumentContentRendererProvider rendererFactory,
				IGToolCallbackSourceRepositoryPattern toolCallbacksRepository,
				IChatModelUsageAdvisorFactory usageAdvisorFactory, ObservationRegistry observationRegistry) {
			super(rendererFactory, toolCallbacksRepository, usageAdvisorFactory, observationRegistry);

		}

		/**
		 * Configures a VertexAiGeminiChatModel based on the provided configuration
		 * 
		 * @param config The Google Vertex chat model configuration
		 * @param type   The chat model type
		 * @return A configured VertexAiGeminiChatModel instance
		 * @throws LLMConfigException if there's an error during configuration
		 */
		@Override
		protected GoogleGenAiChatModel configureModel(GGoogleVertexChatModelConfig config, GChatModelType type,
				ToolCallingManager toolsCallsManager) throws LLMConfigException {

			Client genAiClient = configurator.createGenAiClient(config.getApiSecretCode(), config.getBaseUrl());
			GoogleGenAiChatOptions.Builder builder = GoogleGenAiChatOptions.builder();

			// Configure model selection if specified
			if (config.getChoosedModel() != null) {
				builder = builder.model(config.getChoosedModel().getCode());
			}

			// Configure temperature if specified and valid
			if (config.getTemperature() != null) {
				builder = builder.temperature(config.getTemperature());
			}

			// Configure topP if specified and valid
			if (config.getTopP() != null && config.getTopP() > 0) {
				builder = builder.topP(config.getTopP());
			}

			// The generation cap is maxOutputTokens here, not maxTokens as on the other
			// providers, which is why this one was left unconfigured: a model asked to keep
			// its answers short answered at whatever length vertex defaults to.
			if (config.getMaxGeneratedTokens() != null && config.getMaxGeneratedTokens() > 0) {
				builder = builder.maxOutputTokens(config.getMaxGeneratedTokens());
			}

			// Gemini takes a thinking level, a scale that lines up with ours except at the
			// bottom: there is no way to switch reasoning off, only to ask for the least of
			// it, so NO_THINKING becomes MINIMAL rather than pretending to disable it.
			// AUTO sends nothing, leaving the provider default alone - which is also what
			// THINKING_LEVEL_UNSPECIFIED would mean, spelled without a request.
			if (config.getThinking() != null) {
				switch (config.getThinking()) {
				case NO_THINKING: {
					builder = builder.thinkingLevel(GoogleGenAiThinkingLevel.MINIMAL);
				}
					break;
				case LOW_THINKING: {
					builder = builder.thinkingLevel(GoogleGenAiThinkingLevel.LOW);
				}
					break;
				case MEDIUM_THINKING: {
					builder = builder.thinkingLevel(GoogleGenAiThinkingLevel.MEDIUM);
				}
					break;
				case HIGH_THINKING: {
					builder = builder.thinkingLevel(GoogleGenAiThinkingLevel.HIGH);
				}
					break;
				default:
					break;
				}
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Chat model {} configured with thinking {}", config.getCode(), config.getThinking());
				}
			}

			// Configure enabled functions if specified
			if (config.getEnabledFunctions() != null && !config.getEnabledFunctions().isEmpty()) {
				List<ToolCallback> functions = functionsRepo.getTools((config.getEnabledFunctions()));
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

			GoogleGenAiChatOptions options = builder.build();
			GoogleGenAiChatModel model = GoogleGenAiChatModel.builder()
					.genAiClient(genAiClient)
					.retryTemplate(serviceClientsProviderFactory.get(getType().getCode()).getCoreRetryTemplate())
					.options(options)
					.toolCallingManager(
							toolsCallsManager != null ? toolsCallsManager : functionsRepo.createToolCallingManager())
					.observationRegistry(observationRegistry)
					.build();
			return model;
		}

		@Override
		protected IGConfigurableChatModel cloneMeWithInjection() {

			return new GoogleVertexConfigurableChatModel(rendererFactory, toolCallbacksRepository, usageAdvisorFactory,
					observationRegistry);
		}
	};

	/**
	 * Returns the model type for Google Vertex chat models
	 * 
	 * @return GChatModelType for Google Vertex models
	 */
	@Override
	public GChatModelType getType() {
		return type;
	}

	/**
	 * Creates a configured chat model instance based on the provided configuration
	 * 
	 * @param config The Google Vertex chat model configuration
	 * @return A configured chat model instance
	 * @throws LLMConfigException if there's an error during configuration
	 */
	@Override
	public IGConfigurableChatModel<GGoogleVertexChatModelConfig> create(GGoogleVertexChatModelConfig config)
			throws LLMConfigException {
		GoogleVertexConfigurableChatModel model = new GoogleVertexConfigurableChatModel(documentContentRenderProvider,
				functionsRepo, usageAdvisorFactory, observationRegistry);
		model.initialize(config, type);
		return model;
	}

	/**
	 * Returns the list of available model choices for Google Vertex
	 * 
	 * @param config The Google Vertex chat model configuration
	 * @return An operation status containing the list of model choices
	 */
	@Override
	public OperationStatus<List<GGoogleVertexChatModelChoice>> getModelChoices(GGoogleVertexChatModelConfig config) {
		return OperationStatus.of(choices);
	}

	/**
	 * Creates a base configuration for a Google Vertex chat model with the
	 * specified preset model
	 * 
	 * @param presetModel The code of the preset model to use
	 * @return A base configuration for a Google Vertex chat model
	 */
	@Override
	public GGoogleVertexChatModelConfig createBaseConfiguration(String presetModel) {
		GGoogleVertexChatModelConfig clean = new GGoogleVertexChatModelConfig();
		clean.setChoosedModel(new GGoogleVertexChatModelChoice());
		clean.getChoosedModel().setCode(presetModel);
		clean.getChoosedModel().setDescription("chat model " + presetModel);
		clean.setDescription("Google vertex  chat model " + presetModel);
		clean.setModelTypeCode(getType().getCode());
		return clean;
	}

	@Override
	public OperationStatus<GGoogleVertexChatModelConfig> insertAndConfigure(GGoogleVertexChatModelConfig config)
			throws GeboPersistenceException, LLMConfigException {

		return configureHandler.insertAndConfigure(config, type);
	}
}