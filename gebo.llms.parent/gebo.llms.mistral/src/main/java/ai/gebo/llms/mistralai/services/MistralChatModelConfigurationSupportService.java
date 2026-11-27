/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.llms.mistralai.services;

import ai.gebo.llms.abstraction.layer.model.GModelCarriedMediaTypes;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.mistralai.MistralAiChatModel;
import org.springframework.ai.mistralai.MistralAiChatOptions;
import org.springframework.ai.mistralai.api.MistralAiApi;
import org.springframework.ai.mistralai.api.MistralAiApi.Builder;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.ai.service.IGDocumentContentRendererProvider;
import ai.gebo.architecture.ai.service.IGToolCallbackSourceRepositoryPattern;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.crypting.services.GeboCryptSecretException;
import ai.gebo.llms.abstraction.layer.model.GChatModelType;
import ai.gebo.llms.abstraction.layer.services.GAbstractConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.IChatModelUsageAdvisorFactory;
import ai.gebo.llms.abstraction.layer.services.IGChatModelConfigurationSupportService;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableChatModel;
import ai.gebo.llms.abstraction.layer.services.IGLlmsServiceClientsProvider;
import ai.gebo.llms.abstraction.layer.services.IGLlmsServiceClientsProviderFactory;
import ai.gebo.llms.abstraction.layer.services.ILLMTypeFiltrerRepositoryPattern;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.abstraction.layer.services.ModelRuntimeConfigureHandler;
import ai.gebo.llms.mistralai.model.GMistralChatModelChoice;
import ai.gebo.llms.mistralai.model.GMistralChatModelConfig;
import ai.gebo.model.OperationStatus;
import ai.gebo.secrets.model.AbstractGeboSecretContent;
import ai.gebo.secrets.model.GeboSecretType;
import ai.gebo.secrets.model.GeboTokenContent;
import ai.gebo.secrets.services.IGeboSecretsAccessService;
import ai.gebo.llms.models.metainfos.IGModelsLibraryDao;
import ai.gebo.llms.models.metainfos.ModelMetaInfo;
import ai.gebo.llms.models.metainfos.ModelParameterRules;
import ai.gebo.llms.abstraction.layer.model.GChatModelParametersSupport;
import ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig.ChatModelThinkingOption;
import io.micrometer.observation.ObservationRegistry;
import lombok.AllArgsConstructor;

/**
 * AI generated comments Service class responsible for configuring and creating
 * Mistral AI chat models.
 */
@Service
@AllArgsConstructor
public class MistralChatModelConfigurationSupportService
		implements IGChatModelConfigurationSupportService<GMistralChatModelChoice, GMistralChatModelConfig> {

	private static final Logger LOGGER = LoggerFactory.getLogger(MistralChatModelConfigurationSupportService.class);
	/**
	 * Static definition of the chat model type with code and description.
	 */
	static final GChatModelType type = new GChatModelType();
	static {
		type.setCode("chatmodel-mistral");
		type.setDescription("chat model service hosted on Mistral AI");
		type.setModelConfigurationClass(GMistralChatModelConfig.class.getName());
		type.setProviderId("mistralai");
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
	final IGDocumentContentRendererProvider documentContentRenderProvider;
	final IChatModelUsageAdvisorFactory usageAdvisorFactory;
	final ObservationRegistry observationRegistry;
	/** The library the parameter rules of the chat models are read from. */
	final IGModelsLibraryDao modelsLibrary;

	/**
	 * Inner class that implements the configuration and creation of Mistral AI chat
	 * models.
	 */
	class MistralConfigurableChatModel
			extends GAbstractConfigurableChatModel<GMistralChatModelConfig, MistralAiChatModel> {

		@Override
		public GModelCarriedMediaTypes getCarriedMediaTypes() {
			return carriedMediaTypes(getConfig() != null && getConfig().getChoosedModel() != null
					? getConfig().getChoosedModel().getCode()
					: null);
		}

		public MistralConfigurableChatModel(IGDocumentContentRendererProvider rendererFactory,
				IGToolCallbackSourceRepositoryPattern toolCallbacksRepository,
				IChatModelUsageAdvisorFactory usageAdvisorFactory, ObservationRegistry observationRegistry, IGModelsLibraryDao modelsLibrary) {
			super(rendererFactory, toolCallbacksRepository, usageAdvisorFactory, observationRegistry, modelsLibrary);

		}

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

			org.springframework.ai.mistralai.MistralAiChatOptions.Builder builder = MistralAiChatOptions.builder();
			IGLlmsServiceClientsProvider clientsProvider = serviceClientsProviderFactory.get(getCode());
			org.springframework.web.client.RestClient.Builder restClient = clientsProvider.getRestClientBuilder();
			org.springframework.web.reactive.function.client.WebClient.Builder webClient = clientsProvider
					.getWebClientBuilder();
			Builder apiBuilder = MistralAiApi.builder();
			apiBuilder.apiKey(apiKey);
			apiBuilder.restClientBuilder(restClient);

			MistralAiApi mistralApi = apiBuilder.build();
			org.springframework.core.retry.RetryTemplate retryTemplate = clientsProvider.getCoreRetryTemplate();

			if (config.getChoosedModel() != null) {
				builder = builder.model(config.getChoosedModel().getCode());
			}
			if (config.getTemperature() != null) {
				builder = builder.temperature(config.getTemperature());
			}
			if (config.getTopP() != null && config.getTopP() > 0) {
				builder = builder.topP(config.getTopP());
			}
			// The generation cap: the configured one, else the default sized on the model's context
			// window (see GAbstractConfigurableChatModel.maxGeneratedTokensOf), the same the budgets
			// reserve; a model left without one generates up to its own maximum.
			final Integer maxGeneratedTokens = GAbstractConfigurableChatModel.maxGeneratedTokensOf(config);
			if (maxGeneratedTokens != null) {
				builder.maxTokens(maxGeneratedTokens);
			}
			// Mistral's adjustable reasoning is a switch rather than a scale: its
			// reasoning_effort offers HIGH and NONE and nothing between them, so the three
			// levels above zero all ask for HIGH - asking for less is not something the
			// api can express. NONE, on the other hand, it can, which makes this one of
			// the few providers where NO_THINKING is honoured rather than approximated.
			// AUTO sends nothing: it means leaving the provider default alone.
			// Only a model whose card says it reasons (capabilities.reasoning of Mistral's
			// models api) gets a reasoning_effort: the others do not take the parameter. And
			// where the model's parameter rules list its efforts, NONE and HIGH are sent only
			// when listed (zai-glm-5-3 takes low, high and max, no none).
			if (config.getThinking() != null) {
				final Boolean reasons = config.getChoosedModel() != null
						&& config.getChoosedModel().getMetaInfos() != null
								? config.getChoosedModel().getMetaInfos().getSupportsReasoning()
								: null;
				final MistralAiApi.ChatCompletionRequest.ReasoningEffort effort = reasoningEffortOf(config.getThinking(),
						reasons, parameterRules);
				if (effort != null) {
					builder = builder.reasoningEffort(effort);
				}
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Chat model {} configured with thinking {} as reasoning effort {} (model reasons: {})",
							config.getCode(), config.getThinking(), effort, reasons);
				}
			}
			if (!toolNames.isEmpty()) {
				List<ToolCallback> functions = functionsRepo.getTools((toolNames));
				// The condition above tests the tool names that were REQUESTED. getTools filters
				// the callbacks actually available by those names, so it can return fewer - or
				// none at all, when the source that exports them failed. Configuring the model
				// from the request rather than from what resolved leaves it declaring tools it
				// will never send.
				if (functions != null && !functions.isEmpty()) {
					builder = builder.toolCallbacks(functions);
				} else {
					logToolsNotRegistered(LOGGER, config.getCode(), functions);
				}
			}

			MistralAiChatOptions options = builder.build();
			MistralAiChatModel model = MistralAiChatModel.builder()
					.mistralAiApi(mistralApi)
					.options(options)
					.retryTemplate(retryTemplate)
					.observationRegistry(observationRegistry)
					.build();
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

		@Override
		protected IGConfigurableChatModel cloneMeWithInjection() {

			return new MistralConfigurableChatModel(rendererFactory, toolCallbacksRepository, usageAdvisorFactory,
					observationRegistry, modelsLibrary);
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
		MistralConfigurableChatModel model = new MistralConfigurableChatModel(documentContentRenderProvider,
				functionsRepo, usageAdvisorFactory, observationRegistry, modelsLibrary);
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
	public OperationStatus<GMistralChatModelConfig> insertAndConfigure(GMistralChatModelConfig config)
			throws GeboPersistenceException, LLMConfigException {

		return configureHandler.insertAndConfigure(config, type);
	}

	/**
	 * Spring AI's Mistral chat model sends every media of a user message as an image
	 * url chunk, whatever its type (MistralAiChatModel, 2.1.0-RC2): pictures only, an
	 * audio file or a PDF would reach Mistral as a picture.
	 */
	@Override
	public GModelCarriedMediaTypes carriedMediaTypes(String modelCode) {
		return GModelCarriedMediaTypes.none().images("image/*");
	}

	/**
	 * The reasoning_effort a request sends for a thinking option: none to a model whose
	 * card says it does not reason; NONE for no thinking and HIGH for the three levels,
	 * each only where the model's parameter rules list it (or list no effort); nothing for
	 * Automatic. What configureModel sends, and what the editors are told.
	 *
	 * @param reasons whether the model reasons, as its card tells; null when unknown
	 * @param rules   the model's parameter rules, may be null
	 */
	static MistralAiApi.ChatCompletionRequest.ReasoningEffort reasoningEffortOf(ChatModelThinkingOption option,
			Boolean reasons, ModelParameterRules rules) {
		if (option == null || Boolean.FALSE.equals(reasons))
			return null;
		switch (option) {
		case NO_THINKING:
			return accepts(rules, "none") ? MistralAiApi.ChatCompletionRequest.ReasoningEffort.NONE : null;
		case LOW_THINKING:
		case MEDIUM_THINKING:
		case HIGH_THINKING:
			return accepts(rules, "high") ? MistralAiApi.ChatCompletionRequest.ReasoningEffort.HIGH : null;
		default:
			return null;
		}
	}

	/** As GAbstractConfigurableChatModel.acceptsEffort: true unless the rules list efforts without it. */
	private static boolean accepts(ModelParameterRules rules, String effort) {
		return rules == null || !rules.knowsEfforts() || rules.acceptsEffort(effort);
	}

	/**
	 * The sampling parameters as the model's parameter rules let them go; the thinking as
	 * {@link #reasoningEffortOf} sends it, by the reasoning capability the chosen model's
	 * card carries.
	 */
	@Override
	public GChatModelParametersSupport parametersSupport(String modelCode, ModelMetaInfo metaInfos,
			java.util.Map<String, Object> modelDetails) {
		final String providerId = type.getProviderId();
		final ModelParameterRules rules = modelsLibrary != null && modelCode != null
				? modelsLibrary.findParameterRules(providerId, modelCode)
				: null;
		final Boolean reasons = metaInfos != null ? metaInfos.getSupportsReasoning() : null;
		return GChatModelParametersSupport.of(providerId, modelCode, rules,
				modelsLibrary != null ? modelsLibrary.findTemperatureRange(providerId, modelCode) : null,
				modelsLibrary != null ? modelsLibrary.findTopPRange(providerId, modelCode) : null,
				!Boolean.FALSE.equals(reasons), option -> {
					final MistralAiApi.ChatCompletionRequest.ReasoningEffort effort = reasoningEffortOf(option,
							reasons, rules);
					return effort != null ? effort.name().toLowerCase(java.util.Locale.ROOT) : null;
				});
	}
}
