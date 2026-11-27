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

import ai.gebo.llms.abstraction.layer.model.GModelCarriedMediaTypes;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.model.tool.ToolCallingManager;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.http.okhttp.OpenAiHttpClientBuilderCustomizer;
import org.springframework.ai.openai.responses.OpenAiResponsesChatModel;
import org.springframework.ai.openai.responses.OpenAiResponsesChatOptions;
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
import ai.gebo.llms.models.metainfos.ModelParameterRules;
import ai.gebo.llms.abstraction.layer.model.GChatModelParametersSupport;
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
import ai.gebo.llms.models.metainfos.IGModelsLibraryDao;
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
		type.setProviderId("openai");
	}

	final IGeboSecretsAccessService secretService;
	final IGOpenAIApiUtil openaiApiUtil;
	final IGToolCallbackSourceRepositoryPattern functionsRepo;
	final IGLlmsServiceClientsProviderFactory serviceClientsProviderFactory;
	final ModelRuntimeConfigureHandler configureHandler;
	final IGDocumentContentRendererProvider documentContentRenderProvider;
	final IChatModelUsageAdvisorFactory usageAdvisorFactory;
	final ObservationRegistry observationRegistry;
	/** The library the parameter rules of the chat models are read from. */
	final IGModelsLibraryDao modelsLibrary;

	/**
	 * Implementation of a configurable chat model for OpenAI. This class handles
	 * the creation and configuration of OpenAI chat models.
	 */
	class OpenAIConfigurableChatModel extends GAbstractConfigurableChatModel<GOpenAIChatModelConfig, ChatModel> {

		@Override
		public GModelCarriedMediaTypes getCarriedMediaTypes() {
			return carriedMediaTypes(getConfig() != null && getConfig().getChoosedModel() != null
					? getConfig().getChoosedModel().getCode()
					: null);
		}

		public OpenAIConfigurableChatModel(IGDocumentContentRendererProvider rendererFactory,
				IGToolCallbackSourceRepositoryPattern toolCallbacksRepository,
				IChatModelUsageAdvisorFactory usageAdvisorFactory, ObservationRegistry observationRegistry, IGModelsLibraryDao modelsLibrary) {
			super(rendererFactory, toolCallbacksRepository, usageAdvisorFactory, observationRegistry, modelsLibrary);

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
		protected ChatModel configureModel(GOpenAIChatModelConfig config, GChatModelType type)
				throws LLMConfigException {

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

			if (!toolNames.isEmpty()) {
				List<ToolCallback> functions = functionsRepo.getTools((toolNames));
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
					logToolsNotRegistered(LOGGER, config.getCode(), functions);
				}
			}
			// max_completion_tokens, not max_tokens: OpenAI deprecated max_tokens in its
			// favour, and the reasoning models refuse it (the openai-java SDK documents it as
			// "not compatible with o-series models"); max_completion_tokens bounds the
			// reasoning tokens too, on every model.
			// The generation cap: the configured one, else the default sized on the model's context
			// window (see GAbstractConfigurableChatModel.maxGeneratedTokensOf), the same the budgets
			// reserve; a model left without one generates up to its own maximum.
			final Integer maxGeneratedTokens = GAbstractConfigurableChatModel.maxGeneratedTokensOf(config);
			if (maxGeneratedTokens != null) {
				builder.maxCompletionTokens(maxGeneratedTokens);
			}
			// The effort among the ones the model's parameter rules list, the nearest to the
			// thinking asked (see GAbstractConfigurableChatModel.reasoningEffortFor); none to
			// a model its metadata or the models library say does not reason, which refuses
			// the parameter.
			final String effort = Boolean.FALSE.equals(modelReasons(
					config.getChoosedModel() != null ? config.getChoosedModel().getCode() : null,
					config.getChoosedModel() != null ? config.getChoosedModel().getMetaInfos() : null)) ? null
							: reasoningEffortFor(config.getThinking());
			if (effort != null) {
				builder.reasoningEffort(effort);
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Chat model {} ({}) configured with thinking {} as reasoning effort {}, temperature {},"
						+ " max completion tokens {}", config.getCode(),
						config.getChoosedModel() != null ? config.getChoosedModel().getCode() : null,
						config.getThinking(), effort, config.getTemperature(), maxGeneratedTokens);
			}
			if (user != null) {
				builder = builder.user(user);
			}

			builder.timeout(OpenAiClientCustomizer.requestTimeout(serviceClientsProviderFactory.get(getCode())));
			OpenAiChatOptions options = builder.build();
			ToolCallingManager toolCallingManager = functionsRepo.createToolCallingManager();
			// The models of version 6 and later go through the Responses API, whose tool calls
			// Chat Completions does not carry for them, and so do the ones the models library
			// names as taking that api only (OpenAiApiRouting): Spring AI's Responses model,
			// configured as the Chat Completions one; the others stay on Spring AI's Chat
			// Completions model
			if (OpenAiApiRouting.usesResponsesApi(modelsLibrary, options.getModel())) {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Chat model {} ({}) goes through the OpenAI Responses API", config.getCode(),
							options.getModel());
				}
				return responsesModel(OpenAiResponsesOptions.of(options), toolCallingManager, observationRegistry,
						OpenAiClientCustomizer.from(serviceClientsProviderFactory.get(getCode())));
			}
			OpenAiChatModel model = OpenAiChatModel.builder()
					.options(options)
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
					observationRegistry, modelsLibrary);
		}
	};

	/**
	 * A chat model on the Responses api: Spring AI's, its HTTP client set as the Chat
	 * Completions one's (the customizer of the provider's client settings; its calls observed
	 * in the registry), the reasoning summaries dropped for an organization refused them
	 * ({@link ReasoningSummariesFallbackChatModel}). Its calls are accounted as any chat
	 * model's, by the usage advisor of the ChatClient above it.
	 */
	static ChatModel responsesModel(OpenAiResponsesChatOptions options, ToolCallingManager toolCallingManager,
			ObservationRegistry observationRegistry, OpenAiHttpClientBuilderCustomizer httpClientCustomizer) {
		final OpenAiResponsesChatModel.Builder builder = OpenAiResponsesChatModel.builder().options(options)
				.toolCallingManager(toolCallingManager)
				.observationRegistry(observationRegistry != null ? observationRegistry : ObservationRegistry.NOOP);
		if (httpClientCustomizer != null) {
			builder.httpClientBuilderCustomizer(httpClientCustomizer);
		}
		return new ReasoningSummariesFallbackChatModel(builder.build());
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
		OpenAIConfigurableChatModel model = new OpenAIConfigurableChatModel(documentContentRenderProvider,
				functionsRepo, usageAdvisorFactory, observationRegistry, modelsLibrary);
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

		OperationStatus<List<GOpenAIChatModelChoice>> result = this.openaiApiUtil.getChatModels(
				GOpenAIChatModelChoice.class, providerConfig, config, (choice) -> {
					ModelMetaInfo meta = new ModelMetaInfo();
					meta.setInformativeUrl("https://platform.openai.com/docs/models/");
					return meta;
				}, type);
		if (result != null && result.getResult() != null) {
			// by id, unless the models library knows the model as a chat model: one that listens
			// or draws carries in its id the markers of the audio and image models
			result.setResult(result.getResult().stream().filter(c -> isChatModel(c.getCode())
					|| (modelsLibrary != null && modelsLibrary.knowsAsChatModel("openai", c.getCode()))).toList());
		}
		return result;
	}

	/**
	 * Whether a model listed by GET /v1/models is a chat model. The endpoint lists
	 * every model of the account with no type, so the speech, transcription, image,
	 * moderation, realtime/audio and legacy completion models are recognised by id,
	 * as the image, speech and transcription lists recognise theirs.
	 */
	static boolean isChatModel(String modelId) {
		if (modelId == null)
			return false;
		String id = modelId.toLowerCase();
		for (String marker : List.of("tts", "whisper", "transcribe", "dall-e", "image", "moderation", "realtime",
				"audio", "embedding", "babbage", "davinci", "sora", "gpt-live", "translate")) {
			if (id.contains(marker))
				return false;
		}
		return true;
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

	/**
	 * Whether a model reasons, as the chosen model's metadata tells, else the models
	 * library's entry of the model; null when neither tells.
	 */
	Boolean modelReasons(String modelCode, ModelMetaInfo metaInfos) {
		if (metaInfos != null && metaInfos.getSupportsReasoning() != null)
			return metaInfos.getSupportsReasoning();
		final ModelMetaInfo entry = modelsLibrary != null && modelCode != null
				? modelsLibrary.findByProviderIdAndModelId(type.getProviderId(), modelCode)
				: null;
		return entry != null ? entry.getSupportsReasoning() : null;
	}

	/**
	 * The sampling parameters as the model's parameter rules let them go, with the ranges
	 * of OpenAI's api reference; the thinking as {@code reasoning_effort}, the nearest
	 * effort the model lists (see GAbstractConfigurableChatModel.reasoningEffortFor),
	 * none to a model that does not reason: what configureModel sends.
	 */
	@Override
	public GChatModelParametersSupport parametersSupport(String modelCode, ModelMetaInfo metaInfos,
			java.util.Map<String, Object> modelDetails) {
		final String providerId = type.getProviderId();
		final ModelParameterRules rules = modelsLibrary != null && modelCode != null
				? modelsLibrary.findParameterRules(providerId, modelCode)
				: null;
		final boolean reasons = !Boolean.FALSE.equals(modelReasons(modelCode, metaInfos));
		return GChatModelParametersSupport.of(providerId, modelCode, rules,
				modelsLibrary != null ? modelsLibrary.findTemperatureRange(providerId, modelCode) : null,
				modelsLibrary != null ? modelsLibrary.findTopPRange(providerId, modelCode) : null, reasons,
				option -> GAbstractConfigurableChatModel.reasoningEffortFor(option, rules));
	}

	/**
	 * What Spring AI's two OpenAI chat models carry (2.1.0-RC2), by the api the model
	 * is called through, see {@link OpenAiApiRouting}:
	 * <ul>
	 * <li>chat completions (OpenAiChatModel): pictures, PDF as a file part, audio as
	 * mp3 or wav bytes; any other type would be pasted into the prompt as base64
	 * text;</li>
	 * <li>the Responses api (OpenAiResponsesChatModel): pictures and PDF as input
	 * files; audio raises "Audio input is not supported by the OpenAI Responses
	 * API".</li>
	 * </ul>
	 */
	@Override
	public GModelCarriedMediaTypes carriedMediaTypes(String modelCode) {
		if (modelCode != null && OpenAiApiRouting.usesResponsesApi(modelsLibrary, modelCode)) {
			return GModelCarriedMediaTypes.none().images("image/*").documents("application/pdf");
		}
		return GModelCarriedMediaTypes.none().images("image/*").documents("application/pdf").audio("audio/mp3",
				"audio/mpeg", "audio/wav", "audio/x-wav", "audio/wave");
	}
}
