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
 * This service provides Azure/OpenAI Chat model configuration support for the Gebo AI platform.
 * It implements the IGChatModelConfigurationSupportService interface for OpenAI-specific model configurations.
 * The service is conditionally enabled when the openAIEnabled property is set to true.
 */
package ai.gebo.llms.azure.openai.services;

import java.util.HashSet;
import java.util.List;

import org.springframework.ai.azure.openai.AzureOpenAiChatModel;
import org.springframework.ai.azure.openai.AzureOpenAiChatOptions;
import org.springframework.ai.model.tool.ToolCallingManager;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import com.azure.ai.openai.OpenAIClientBuilder;
import com.azure.core.credential.AzureKeyCredential;

import ai.gebo.architecture.ai.IGToolCallbackSourceRepositoryPattern;
import ai.gebo.llms.abstraction.layer.model.GBaseModelChoice;
import ai.gebo.llms.abstraction.layer.model.GChatModelType;
import ai.gebo.llms.abstraction.layer.services.GAbstractConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.IGChatModelConfigurationSupportService;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.IGLlmsServiceClientsProviderFactory;
import ai.gebo.llms.abstraction.layer.services.IGTextToSpeechModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGTranscriptModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.azure.openai.model.GAzureOpenAIChatModelChoice;
import ai.gebo.llms.azure.openai.model.GAzureOpenAIChatModelConfig;
import ai.gebo.llms.azure.openai.services.AzureOpenAIConfigFactory.AzureOpenAIBaseConfig;
import ai.gebo.llms.models.metainfos.ModelMetaInfo;
import ai.gebo.llms.openai.api.utils.IGOpenAIApiUtil;
import ai.gebo.model.OperationStatus;
import ai.gebo.openai.integration.client.model.OpenAIApiConfig;
import ai.gebo.secrets.services.IGeboSecretsAccessService;
import io.micrometer.observation.ObservationRegistry;

@ConditionalOnProperty(prefix = "ai.gebo.llms.config", name = "azureOpenAIEnabled", havingValue = "true")
@Service
public class AzureOpenAIChatModelConfigurationSupportService
		implements IGChatModelConfigurationSupportService<GAzureOpenAIChatModelChoice, GAzureOpenAIChatModelConfig> {
	/**
	 * Static model type definition for the OpenAI chat model.
	 */
	static final GChatModelType type = new GChatModelType();
	static {
		type.setCode("azure-chatgpt-OpenAI");
		type.setDescription("chatgpt service hosted on Azure/OpenAI");
		type.setModelConfigurationClass(GAzureOpenAIChatModelConfig.class.getName());
	}

	/**
	 * Available model choices for OpenAI chat models.
	 */
	static final List<GAzureOpenAIChatModelChoice> choices = GBaseModelChoice.of(GAzureOpenAIChatModelChoice.class,
			OpenAiApi.ChatModel.values());

	@Autowired
	IGeboSecretsAccessService secretService;
	@Autowired
	IGOpenAIApiUtil openaiApiUtil;
	@Autowired
	IGToolCallbackSourceRepositoryPattern functionsRepo;

	@Autowired
	IGTextToSpeechModelRuntimeConfigurationDao ttsDao;
	@Autowired
	IGTranscriptModelRuntimeConfigurationDao transcriptDao;
	@Autowired
	IGLlmsServiceClientsProviderFactory serviceClientsProviderFactory;
	@Autowired
	AzureOpenAIConfigFactory azureClientBuilderFactory;

	/**
	 * Implementation of a configurable chat model for OpenAI. This class handles
	 * the creation and configuration of OpenAI chat models.
	 */
	class AzureOpenAIConfigurableChatModel
			extends GAbstractConfigurableChatModel<GAzureOpenAIChatModelConfig, AzureOpenAiChatModel> {

		/**
		 * Configures the OpenAI chat model based on the provided configuration.
		 * 
		 * @param config The OpenAI chat model configuration
		 * @param type   The chat model type
		 * @return The configured OpenAI chat model
		 * @throws LLMConfigException if there is an error in configuration
		 */
		@Override
		protected AzureOpenAiChatModel configureModel(GAzureOpenAIChatModelConfig config, GChatModelType type)
				throws LLMConfigException {

			AzureOpenAIBaseConfig coords = azureClientBuilderFactory.createClientBulder(config);
			OpenAIClientBuilder clientBuilder = new OpenAIClientBuilder();
			clientBuilder.credential(new AzureKeyCredential(coords.getApiKey()));
			clientBuilder.endpoint(coords.getEndpoint());
			org.springframework.ai.azure.openai.AzureOpenAiChatOptions.Builder builder = AzureOpenAiChatOptions
					.builder();
			builder.user(coords.getUser());
			builder.deploymentName(coords.getModel());
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
			AzureOpenAiChatOptions options = builder.build();
			ToolCallingManager toolCallingManager = functionsRepo.createToolCallingManager();

			AzureOpenAiChatModel model = new AzureOpenAiChatModel(clientBuilder, options, toolCallingManager,
					ObservationRegistry.NOOP);

			return model;
		}

		/**
		 * Creates a base configuration for the OpenAI chat model.
		 * 
		 * @return A new OpenAI chat model configuration
		 */
		public GAzureOpenAIChatModelConfig createBaseConfiguration() {
			GAzureOpenAIChatModelConfig config = new GAzureOpenAIChatModelConfig();
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

		/**
		 * Indicates whether the model supports text-to-speech.
		 * 
		 * @return true as OpenAI models support text-to-speech
		 */
		@Override
		public boolean isSupportsSpeech() {
			return false;
		}

		/**
		 * Indicates whether the model supports transcript generation.
		 * 
		 * @return true as OpenAI models support transcript generation
		 */
		@Override
		public boolean isSupportsTranscript() {
			return false;
		}

	};

	/**
	 * Default constructor for the OpenAIChatModelConfigurationSupportService.
	 */
	public AzureOpenAIChatModelConfigurationSupportService() {
	}

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
	public IGConfigurableChatModel<GAzureOpenAIChatModelConfig> create(GAzureOpenAIChatModelConfig config)
			throws LLMConfigException {
		AzureOpenAIConfigurableChatModel model = new AzureOpenAIConfigurableChatModel();
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
	public OperationStatus<List<GAzureOpenAIChatModelChoice>> getModelChoices(GAzureOpenAIChatModelConfig config) {
		OpenAIApiConfig providerConfig = new OpenAIApiConfig();
		providerConfig.setProviderId("openai");

		return this.openaiApiUtil.getChatModels(GAzureOpenAIChatModelChoice.class, providerConfig, config, (choice) -> {
			ModelMetaInfo meta = new ModelMetaInfo();
			meta.setInformativeUrl("https://platform.openai.com/docs/models/");
			return meta;
		});
	}

	/**
	 * Creates a base configuration for an OpenAI chat model with a preset model.
	 * 
	 * @param presetModel The model identifier to preset
	 * @return A base OpenAI chat model configuration
	 */
	@Override
	public GAzureOpenAIChatModelConfig createBaseConfiguration(String presetModel) {
		GAzureOpenAIChatModelConfig clean = new GAzureOpenAIChatModelConfig();
		clean.setChoosedModel(new GAzureOpenAIChatModelChoice());
		clean.getChoosedModel().setCode(presetModel);
		clean.getChoosedModel().setDescription("chat model " + presetModel);
		clean.setDescription("OpenAI chat model " + presetModel);
		clean.setModelTypeCode(getType().getCode());
		return clean;
	}
}