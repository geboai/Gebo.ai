/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.openai_compat.services;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.model.NoopApiKey;
import org.springframework.ai.openai.OpenAiDecisionModel;
import org.springframework.ai.openai.OpenAiDecisionOptions;

import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.crypting.services.GeboCryptSecretException;
import ai.gebo.llms.abstraction.layer.model.GDecisionModelType;
import ai.gebo.llms.abstraction.layer.services.GAbstractConfigurableDecisionModel;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableDecisionModel;
import ai.gebo.llms.abstraction.layer.services.IGDecisionModelConfigurationSupportService;
import ai.gebo.llms.abstraction.layer.services.IGLlmsServiceClientsProvider;
import ai.gebo.llms.abstraction.layer.services.IGLlmsServiceClientsProviderFactory;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.abstraction.layer.services.ModelRuntimeConfigureHandler;
import ai.gebo.llms.models.metainfos.ModelMetaInfo;
import ai.gebo.llms.openai.api.utils.IGOpenAIApiUtil;
import ai.gebo.llms.openai.http.OpenAiClientCustomizer;
import ai.gebo.llms.openai_compat.model.GenericOpenAIAPIDecisionModelChoice;
import ai.gebo.llms.openai_compat.model.GenericOpenAIAPIDecisionModelConfig;
import ai.gebo.llms.openai_compat.modeltypes.GenericOpenAIDecisionModelTypeConfig;
import ai.gebo.model.GUserMessage;
import ai.gebo.model.OperationStatus;
import ai.gebo.openai.integration.client.model.OpenAIApiConfig;
import ai.gebo.openai.integration.client.model.OpenAIModel;
import ai.gebo.secrets.model.AbstractGeboSecretContent;
import ai.gebo.secrets.model.GeboSecretType;
import ai.gebo.secrets.model.GeboTokenContent;
import ai.gebo.secrets.services.IGeboSecretsAccessService;
import io.micrometer.observation.ObservationRegistry;

/**
 * The decision models of an OpenAI compatible provider declared in providers.yml: the
 * Spring AI {@link OpenAiDecisionModel} pointed at the provider's base url, the models
 * the provider lists (its models list provider, else its models endpoint, keeping the
 * ones named as decision models) and the provider's default decision model when it
 * declares one.
 */
public class GenericOpenAIAPIDecisionModelConfigurationSupportService implements
		IGDecisionModelConfigurationSupportService<GenericOpenAIAPIDecisionModelChoice, GenericOpenAIAPIDecisionModelConfig> {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(GenericOpenAIAPIDecisionModelConfigurationSupportService.class);

	final GenericOpenAIDecisionModelTypeConfig type;
	final IGeboSecretsAccessService secretService;
	final IGOpenAIApiUtil openaiApiUtil;
	final ModelsListProviderProxyService modelsListProxyService;
	final IGLlmsServiceClientsProviderFactory serviceClientsProviderFactory;
	final ModelRuntimeConfigureHandler configureHandler;
	final ObservationRegistry observationRegistry;

	public GenericOpenAIAPIDecisionModelConfigurationSupportService(GenericOpenAIDecisionModelTypeConfig type,
			IGeboSecretsAccessService secretService, IGOpenAIApiUtil openaiApiUtil,
			ModelsListProviderProxyService modelsListProxyService,
			IGLlmsServiceClientsProviderFactory serviceClientsProviderFactory,
			ModelRuntimeConfigureHandler configureHandler, ObservationRegistry observationRegistry) {
		this.type = type;
		this.secretService = secretService;
		this.openaiApiUtil = openaiApiUtil;
		this.modelsListProxyService = modelsListProxyService;
		this.serviceClientsProviderFactory = serviceClientsProviderFactory;
		this.configureHandler = configureHandler;
		this.observationRegistry = observationRegistry;
	}

	class GenericOpenAIConfigurableDecisionModel
			extends GAbstractConfigurableDecisionModel<GenericOpenAIAPIDecisionModelConfig, OpenAiDecisionModel> {

		public GenericOpenAIConfigurableDecisionModel() {
			super(GenericOpenAIAPIDecisionModelConfigurationSupportService.this.observationRegistry);
		}

		@Override
		protected OpenAiDecisionModel configureModel(GenericOpenAIAPIDecisionModelConfig config,
				GDecisionModelType type) throws LLMConfigException {
			final String apiKey = apiKeyOf(config);
			final String baseUrl = baseUrlOf(config);
			if (baseUrl == null) {
				throw new LLMConfigException("The decision model provider " + type.getCode()
						+ " declares no base url and the configuration gives none");
			}
			final IGLlmsServiceClientsProvider clientsProvider = serviceClientsProviderFactory.get(getCode());
			final OpenAiDecisionOptions.Builder builder = OpenAiDecisionOptions.builder().baseUrl(baseUrl);
			if (apiKey != null) {
				builder.apiKey(apiKey);
			} else {
				builder.apiKey(new NoopApiKey());
			}
			if (config.getChoosedModel() != null && config.getChoosedModel().getCode() != null) {
				builder.model(config.getChoosedModel().getCode());
			} else if (GenericOpenAIAPIDecisionModelConfigurationSupportService.this.type.getDefaultModel() != null) {
				builder.model(GenericOpenAIAPIDecisionModelConfigurationSupportService.this.type.getDefaultModel());
			}
			builder.timeout(OpenAiClientCustomizer.requestTimeout(clientsProvider));
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Decision model code=" + config.getCode() + " of provider " + type.getCode() + " on "
						+ baseUrl + " with model " + safeGetModelCode());
			}
			return OpenAiDecisionModel.builder().options(builder.build()).observationRegistry(observationRegistry)
					.httpClientBuilderCustomizer(OpenAiClientCustomizer.from(clientsProvider)).build();
		}
	}

	/** The configured base url, else the provider's own. */
	String baseUrlOf(GenericOpenAIAPIDecisionModelConfig config) {
		if (config.getBaseUrl() != null && !config.getBaseUrl().isBlank()) {
			return config.getBaseUrl();
		}
		return type.getBaseUrl() != null && !type.getBaseUrl().isBlank() ? type.getBaseUrl() : null;
	}

	/**
	 * The token of the configuration's secret; null when the configuration names none
	 * and the provider accepts anonymous calls.
	 */
	String apiKeyOf(GenericOpenAIAPIDecisionModelConfig config) throws LLMConfigException {
		if (config.getApiSecretCode() == null || config.getApiSecretCode().isBlank()) {
			if (!type.isOptionalAuthentication()) {
				throw new LLMConfigException(type.getDescription() + " api cannot work without an api key");
			}
			return null;
		}
		try {
			AbstractGeboSecretContent secret = secretService.getSecretContentById(config.getApiSecretCode());
			if (secret.type() == GeboSecretType.TOKEN) {
				return ((GeboTokenContent) secret).getToken();
			}
			throw new LLMConfigException(type.getDescription() + " api can work only with an api key of type TOKEN");
		} catch (GeboCryptSecretException e) {
			throw new LLMConfigException(type.getDescription() + " api  key configuration gone wrong ", e);
		}
	}

	@Override
	public GDecisionModelType getType() {
		return type;
	}

	@Override
	public String getId() {
		return type.getCode();
	}

	@Override
	public IGConfigurableDecisionModel create(GenericOpenAIAPIDecisionModelConfig config) throws LLMConfigException {
		GenericOpenAIConfigurableDecisionModel model = new GenericOpenAIConfigurableDecisionModel();
		model.initialize(config, type);
		return model;
	}

	/**
	 * The decision models the provider offers: through its models list provider when it
	 * has one, else the ids of its models endpoint named as decision models; the
	 * provider's default decision model is always among them.
	 */
	@Override
	public OperationStatus<List<GenericOpenAIAPIDecisionModelChoice>> getModelChoices(
			GenericOpenAIAPIDecisionModelConfig config) {
		OperationStatus<List<GenericOpenAIAPIDecisionModelChoice>> result;
		if (type.getModelsListProvider() != null && !type.getModelsListProvider().isBlank()) {
			result = this.modelsListProxyService.geModels(type.getModelsListProvider(), config,
					GenericOpenAIAPIDecisionModelChoice.class, type);
		} else {
			result = listFromModelsEndpoint(config);
		}
		if (result.getResult() == null) {
			result.setResult(new ArrayList<>());
		}
		final String defaultModel = type.getDefaultModel();
		if (defaultModel != null && !defaultModel.isBlank()
				&& result.getResult().stream().noneMatch(c -> defaultModel.equals(c.getCode()))) {
			result.getResult().add(choice(defaultModel));
		}
		return result;
	}

	private OperationStatus<List<GenericOpenAIAPIDecisionModelChoice>> listFromModelsEndpoint(
			GenericOpenAIAPIDecisionModelConfig config) {
		final List<GenericOpenAIAPIDecisionModelChoice> choices = new ArrayList<>();
		final OperationStatus<List<GenericOpenAIAPIDecisionModelChoice>> status = OperationStatus.of(choices);
		try {
			final OpenAIApiConfig apiconfig = ProviderModelsLookup.apiConfigOf(config, type.getProviderId(),
					type.getBaseUrl());
			apiconfig.setApiKey(apiKeyOf(config));
			apiconfig.setApiKeyMandatory(!type.isOptionalAuthentication());
			for (OpenAIModel model : openaiApiUtil.getModels(apiconfig)) {
				if (Boolean.FALSE.equals(model.getActive()) || !isDecisionModel(model.getId()))
					continue;
				choices.add(choice(model.getId()));
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Provider " + type.getCode() + " lists " + choices.size() + " decision model(s)");
			}
		} catch (LLMConfigException e) {
			status.getMessages().add(GUserMessage.errorMessage(type.getDescription() + " api key", e.getMessage()));
		} catch (Throwable e) {
			LOGGER.warn("Cannot list the models of " + type.getCode() + " for the decision models lookup: "
					+ e.getMessage());
			status.getMessages().add(GUserMessage.errorMessage("Cannot access the models list of " + type.getCode(),
					e.getMessage() != null ? e.getMessage() : e.getClass().getSimpleName()));
		}
		return status;
	}

	/** The models endpoint names its decision models after the API's model family. */
	static boolean isDecisionModel(String modelId) {
		if (modelId == null)
			return false;
		final String id = modelId.toLowerCase();
		return id.contains("luna") || id.contains("decision") || id.contains("d1-omni");
	}

	private GenericOpenAIAPIDecisionModelChoice choice(String modelId) {
		GenericOpenAIAPIDecisionModelChoice choice = new GenericOpenAIAPIDecisionModelChoice();
		choice.setCode(modelId);
		choice.setDescription(modelId);
		ModelMetaInfo meta = new ModelMetaInfo();
		meta.setProviderId(type.getProviderId());
		meta.setModelId(modelId);
		choice.setMetaInfos(meta);
		return choice;
	}

	@Override
	public GenericOpenAIAPIDecisionModelConfig createBaseConfiguration(String presetModel) {
		GenericOpenAIAPIDecisionModelConfig clean = new GenericOpenAIAPIDecisionModelConfig();
		clean.setModelTypeCode(getType().getCode());
		clean.setChoosedModel(new GenericOpenAIAPIDecisionModelChoice());
		clean.getChoosedModel().setCode(presetModel);
		clean.getChoosedModel().setDescription("decision model " + presetModel);
		clean.setDescription(type.getDescription() + " " + presetModel);
		return clean;
	}

	@Override
	public OperationStatus<GenericOpenAIAPIDecisionModelConfig> insertAndConfigure(
			GenericOpenAIAPIDecisionModelConfig config) throws GeboPersistenceException, LLMConfigException {
		return configureHandler.insertAndConfigure(config, type);
	}
}
