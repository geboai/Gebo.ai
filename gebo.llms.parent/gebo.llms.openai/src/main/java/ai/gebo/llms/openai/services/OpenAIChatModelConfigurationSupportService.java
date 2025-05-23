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

import java.util.HashSet;
import java.util.List;

import org.springframework.ai.model.tool.ToolCallingManager;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.OpenAiChatOptions.Builder;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.ai.openai.api.OpenAiAudioApi.WhisperModel;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.ai.IGToolCallbackSourceRepositoryPattern;
import ai.gebo.crypting.services.GeboCryptSecretException;
import ai.gebo.llms.abstraction.layer.model.GBaseModelChoice;
import ai.gebo.llms.abstraction.layer.model.GChatModelType;
import ai.gebo.llms.abstraction.layer.services.GAbstractConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.IGChatModelConfigurationSupportService;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableTextToSpeechModel;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableTranscriptModel;
import ai.gebo.llms.abstraction.layer.services.IGLlmsServiceClientsProvider;
import ai.gebo.llms.abstraction.layer.services.IGLlmsServiceClientsProviderFactory;
import ai.gebo.llms.abstraction.layer.services.IGTextToSpeechModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.IGTranscriptModelRuntimeConfigurationDao;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.models.metainfos.ModelMetaInfo;
import ai.gebo.llms.openai.api.utils.IGOpenAIApiUtil;
import ai.gebo.llms.openai.model.GOpenAIChatModelChoice;
import ai.gebo.llms.openai.model.GOpenAIChatModelConfig;
import ai.gebo.llms.openai.model.GOpenAITextToSpeechModelConfig;
import ai.gebo.llms.openai.model.GOpenAITranscriptModelConfig;
import ai.gebo.model.OperationStatus;
import ai.gebo.openai.integration.client.model.OpenAIApiConfig;
import ai.gebo.secrets.model.AbstractGeboSecretContent;
import ai.gebo.secrets.model.GeboSecretType;
import ai.gebo.secrets.model.GeboTokenContent;
import ai.gebo.secrets.services.IGeboSecretsAccessService;
import io.micrometer.observation.ObservationRegistry;

@ConditionalOnProperty(prefix = "ai.gebo.llms.config", name = "openAIEnabled", havingValue = "true")
@Service
public class OpenAIChatModelConfigurationSupportService
		implements IGChatModelConfigurationSupportService<GOpenAIChatModelChoice, GOpenAIChatModelConfig> {
	/**
	 * Static model type definition for the OpenAI chat model.
	 */
	static final GChatModelType type = new GChatModelType();
	static {
		type.setCode("chatgpt-OpenAI");
		type.setDescription("chatgpt service hosted on OpenAI");
		type.setModelConfigurationClass(GOpenAIChatModelConfig.class.getName());
	}
	
	/**
	 * Available model choices for OpenAI chat models.
	 */
	static final List<GOpenAIChatModelChoice> choices = GBaseModelChoice.of(GOpenAIChatModelChoice.class,
			OpenAiApi.ChatModel.values());

	@Autowired
	IGeboSecretsAccessService secretService;
	@Autowired
	IGOpenAIApiUtil openaiApiUtil;
	@Autowired
	IGToolCallbackSourceRepositoryPattern functionsRepo;
	@Autowired
	OpenAITextToSpeechModelConfigurationSupportService ttsOpenAISupportService;
	@Autowired
	OpenAITranscriptModelConfigurationSupportService transcriptOpenAISupportService;
	@Autowired
	IGTextToSpeechModelRuntimeConfigurationDao ttsDao;
	@Autowired
	IGTranscriptModelRuntimeConfigurationDao transcriptDao;
	@Autowired
	IGLlmsServiceClientsProviderFactory serviceClientsProviderFactory;

	/**
	 * Implementation of a configurable chat model for OpenAI.
	 * This class handles the creation and configuration of OpenAI chat models.
	 */
	class OpenAIConfigurableChatModel extends GAbstractConfigurableChatModel<GOpenAIChatModelConfig, OpenAiChatModel> {

		IGConfigurableTranscriptModel transcriptModel = null;
		IGConfigurableTextToSpeechModel ttsModel = null;
		String apiKey = null;
		String user = null;

		/**
		 * Configures the OpenAI chat model based on the provided configuration.
		 * 
		 * @param config The OpenAI chat model configuration
		 * @param type The chat model type
		 * @return The configured OpenAI chat model
		 * @throws LLMConfigException if there is an error in configuration
		 */
		@Override
		protected OpenAiChatModel configureModel(GOpenAIChatModelConfig config, GChatModelType type)
				throws LLMConfigException {

			this.transcriptModel = null;
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
			org.springframework.ai.openai.api.OpenAiApi.Builder apiBuilder = OpenAiApi.builder();
			IGLlmsServiceClientsProvider clientsProvider = serviceClientsProviderFactory.get(getCode());
			org.springframework.web.client.RestClient.Builder restClient = clientsProvider.getRestClientBuilder();
			org.springframework.web.reactive.function.client.WebClient.Builder webClient = clientsProvider
					.getWebClientBuilder();
			RetryTemplate retryTemplate = clientsProvider.getRetryTemplate();

			apiBuilder.restClientBuilder(restClient);
			apiBuilder.webClientBuilder(webClient);

			OpenAiApi openaiApi = apiBuilder.apiKey(apiKey).build();

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

			if (config.getEnabledFunctions() != null && !config.getEnabledFunctions().isEmpty()) {
				List<ToolCallback> functions = functionsRepo.getTools((config.getEnabledFunctions()));
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
					ObservationRegistry.NOOP);

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

		/**
		 * Indicates whether the model supports text-to-speech.
		 * 
		 * @return true as OpenAI models support text-to-speech
		 */
		@Override
		public boolean isSupportsSpeecht() {
			return true;
		}

		/**
		 * Indicates whether the model supports transcript generation.
		 * 
		 * @return true as OpenAI models support transcript generation
		 */
		@Override
		public boolean isSupportsTranscript() {
			return true;
		}

		/**
		 * Gets the speech model for text-to-speech functionality.
		 * Creates a new one if not available.
		 * 
		 * @return A configurable text-to-speech model
		 * @throws LLMConfigException if there is an error creating the model
		 */
		@Override
		public IGConfigurableTextToSpeechModel getSpeechModel() throws LLMConfigException {
			if (this.ttsModel == null) {
				ttsModel = ttsDao.defaultHandler();
				if (ttsModel == null) {
					GOpenAITextToSpeechModelConfig configuration = ttsOpenAISupportService
							.createBaseConfiguration("tts-1");
					configuration.setApiSecretCode(this.config.getApiSecretCode());
					ttsModel = ttsOpenAISupportService.create(configuration);
				}
			}
			return ttsModel;
		}

		/**
		 * Gets the transcript model for speech-to-text functionality.
		 * Creates a new one if not available.
		 * 
		 * @return A configurable transcript model
		 * @throws LLMConfigException if there is an error creating the model
		 */
		@Override
		public IGConfigurableTranscriptModel getTranscriptModel() throws LLMConfigException {
			if (transcriptModel == null) {
				transcriptModel = transcriptDao.defaultHandler();
				if (transcriptModel == null) {
					GOpenAITranscriptModelConfig baseConfig = transcriptOpenAISupportService
							.createBaseConfiguration(WhisperModel.WHISPER_1.value);
					baseConfig.setApiSecretCode(this.config.getApiSecretCode());
					transcriptModel = transcriptOpenAISupportService.create(baseConfig);
				}
			}
			return transcriptModel;
		}
	};

	/**
	 * Default constructor for the OpenAIChatModelConfigurationSupportService.
	 */
	public OpenAIChatModelConfigurationSupportService() {
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
	public IGConfigurableChatModel<GOpenAIChatModelConfig> create(GOpenAIChatModelConfig config)
			throws LLMConfigException {
		OpenAIConfigurableChatModel model = new OpenAIConfigurableChatModel();
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
		});
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
}