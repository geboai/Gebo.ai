/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.google_vertex.services;

import java.util.HashSet;
import java.util.List;

import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.vertexai.gemini.VertexAiGeminiChatModel;
import org.springframework.ai.vertexai.gemini.VertexAiGeminiChatModel.ChatModel;
import org.springframework.ai.vertexai.gemini.VertexAiGeminiChatOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;

import com.google.cloud.vertexai.VertexAI;

import ai.gebo.architecture.ai.IGToolCallbackSourceRepositoryPattern;
import ai.gebo.llms.abstraction.layer.model.GBaseModelChoice;
import ai.gebo.llms.abstraction.layer.model.GChatModelType;
import ai.gebo.llms.abstraction.layer.services.GAbstractConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.IGChatModelConfigurationSupportService;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.google_vertex.model.GGoogleVertexChatModelChoice;
import ai.gebo.llms.google_vertex.model.GGoogleVertexChatModelConfig;
import ai.gebo.model.OperationStatus;
import io.micrometer.observation.ObservationRegistry;

/**
 * AI generated comments
 * 
 * Service responsible for configuring and managing Google Vertex AI chat models.
 * This service is only active when googleVertexEnabled property is set to true.
 */
@ConditionalOnProperty(prefix = "ai.gebo.llms.config", name = "googleVertexEnabled", havingValue = "true")
@Service
public class GoogleVertexChatModelConfigurationSupportService
		implements IGChatModelConfigurationSupportService<GGoogleVertexChatModelChoice, GGoogleVertexChatModelConfig> {
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
	 * List of available Google Vertex chat model choices based on ChatModel enum values
	 */
	static final List<GGoogleVertexChatModelChoice> choices = GBaseModelChoice.of(GGoogleVertexChatModelChoice.class,
			ChatModel.values());
			
	/**
	 * Repository for tool callbacks used by the models
	 */
	@Autowired
	IGToolCallbackSourceRepositoryPattern functionsRepo;
	
	/**
	 * Helper service to configure VertexAI instances
	 */
	@Autowired
	VertexAIConfigurator configurator;

	/**
	 * Inner class that handles the configuration and initialization of Google Vertex chat models
	 */
	class GoogleVertexConfigurableChatModel
			extends GAbstractConfigurableChatModel<GGoogleVertexChatModelConfig, VertexAiGeminiChatModel> {

		/**
		 * Configures a VertexAiGeminiChatModel based on the provided configuration
		 * 
		 * @param config The Google Vertex chat model configuration
		 * @param type The chat model type
		 * @return A configured VertexAiGeminiChatModel instance
		 * @throws LLMConfigException if there's an error during configuration
		 */
		@Override
		protected VertexAiGeminiChatModel configureModel(GGoogleVertexChatModelConfig config, GChatModelType type)
				throws LLMConfigException {

			VertexAI vertexAI = configurator.createVertexAI(config.getApiSecretCode(), config.getBaseUrl());
			VertexAiGeminiChatOptions options = null;
			VertexAiGeminiChatOptions.Builder builder = new VertexAiGeminiChatOptions.Builder();
			
			// Configure model selection if specified
			if (config.getChoosedModel() != null) {
				builder = builder.model(config.getChoosedModel().getCode());
			}
			
			// Configure temperature if specified and valid
			if (config.getTemperature() != null && config.getTemperature() > 0) {
				builder = builder.temperature(config.getTemperature());
			}
			
			// Configure topP if specified and valid
			if (config.getTopP() != null && config.getTopP() > 0) {
				builder = builder.topP(config.getTopP());
			}

			// Configure enabled functions if specified
			if (config.getEnabledFunctions() != null && !config.getEnabledFunctions().isEmpty()) {
				List<ToolCallback> functions = functionsRepo.getTools((config.getEnabledFunctions()));

				builder = builder.toolCallbacks(functions);
				List<String> names = functions.stream().map(x -> {
					return x.getToolDefinition().name();
				}).toList();
				builder = builder.toolNames(new HashSet<String>(names));
			}
			
			options = builder.build();
			VertexAiGeminiChatModel model = new VertexAiGeminiChatModel(vertexAI, options,
					functionsRepo.createToolCallingManager(), RetryTemplate.defaultInstance(),
					ObservationRegistry.create());
			return model;
		}
	};

	/**
	 * Default constructor
	 */
	public GoogleVertexChatModelConfigurationSupportService() {
	}

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
		GoogleVertexConfigurableChatModel model = new GoogleVertexConfigurableChatModel();
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
	 * Creates a base configuration for a Google Vertex chat model with the specified preset model
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
}