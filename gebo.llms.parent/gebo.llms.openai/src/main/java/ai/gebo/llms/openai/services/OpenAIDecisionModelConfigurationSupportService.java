/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.openai.services;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.openai.OpenAiDecisionModel;
import org.springframework.ai.openai.OpenAiDecisionOptions;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.crypting.services.GeboCryptSecretException;
import ai.gebo.llms.abstraction.layer.model.GDecisionModelType;
import ai.gebo.llms.abstraction.layer.services.GAbstractConfigurableDecisionModel;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableDecisionModel;
import ai.gebo.llms.abstraction.layer.services.IGDecisionModelConfigurationSupportService;
import ai.gebo.llms.abstraction.layer.services.IGLlmsServiceClientsProviderFactory;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.abstraction.layer.services.ModelRuntimeConfigureHandler;
import ai.gebo.llms.models.metainfos.ModelMetaInfo;
import ai.gebo.llms.openai.api.utils.IGOpenAIApiUtil;
import ai.gebo.llms.openai.http.OpenAiClientCustomizer;
import ai.gebo.llms.openai.model.GOpenAIDecisionModelChoice;
import ai.gebo.llms.openai.model.GOpenAIDecisionModelConfig;
import ai.gebo.model.GUserMessage;
import ai.gebo.model.OperationStatus;
import ai.gebo.openai.integration.client.model.OpenAIApiConfig;
import ai.gebo.openai.integration.client.model.OpenAIModel;
import ai.gebo.secrets.model.AbstractGeboSecretContent;
import ai.gebo.secrets.model.GeboSecretType;
import ai.gebo.secrets.model.GeboTokenContent;
import ai.gebo.secrets.services.IGeboSecretsAccessService;
import io.micrometer.observation.ObservationRegistry;
import lombok.AllArgsConstructor;

/**
 * OpenAI's decision models, served by the Decisions API: the type, the models offered
 * and the Spring AI {@link OpenAiDecisionModel} built from a configuration.
 */
@Service
@AllArgsConstructor
public class OpenAIDecisionModelConfigurationSupportService
		implements IGDecisionModelConfigurationSupportService<GOpenAIDecisionModelChoice, GOpenAIDecisionModelConfig> {

	private static final Logger LOGGER = LoggerFactory.getLogger(OpenAIDecisionModelConfigurationSupportService.class);
	static final String INFORMATIVE_URL = "https://developers.openai.com/api/reference/resources/decisions";

	static final GDecisionModelType type = new GDecisionModelType();
	static {
		type.setCode("decision-OpenAI");
		type.setDescription("decision service hosted on OpenAI");
		type.setModelConfigurationClass(GOpenAIDecisionModelConfig.class.getName());
		type.setProviderId("openai");
	}

	final IGeboSecretsAccessService secretService;
	final IGOpenAIApiUtil openaiApiUtil;
	final IGLlmsServiceClientsProviderFactory serviceClientsProviderFactory;
	final ModelRuntimeConfigureHandler configureHandler;
	final ObservationRegistry observationRegistry;

	class OpenAIConfigurableDecisionModel
			extends GAbstractConfigurableDecisionModel<GOpenAIDecisionModelConfig, OpenAiDecisionModel> {

		public OpenAIConfigurableDecisionModel() {
			super(OpenAIDecisionModelConfigurationSupportService.this.observationRegistry);
		}

		@Override
		protected OpenAiDecisionModel configureModel(GOpenAIDecisionModelConfig config, GDecisionModelType type)
				throws LLMConfigException {
			final String apiKey = apiKeyOf(config);
			final OpenAiDecisionOptions.Builder builder = OpenAiDecisionOptions.builder().apiKey(apiKey);
			if (config.getBaseUrl() != null && !config.getBaseUrl().isBlank()) {
				builder.baseUrl(config.getBaseUrl());
			}
			if (config.getChoosedModel() != null && config.getChoosedModel().getCode() != null) {
				builder.model(config.getChoosedModel().getCode());
			}
			builder.timeout(OpenAiClientCustomizer.requestTimeout(serviceClientsProviderFactory.get(getCode())));
			return OpenAiDecisionModel.builder().options(builder.build()).observationRegistry(observationRegistry)
					.httpClientBuilderCustomizer(
							OpenAiClientCustomizer.from(serviceClientsProviderFactory.get(getCode())))
					.build();
		}
	}

	/** The token of the configuration's secret; the Decisions API has no anonymous use. */
	String apiKeyOf(GOpenAIDecisionModelConfig config) throws LLMConfigException {
		if (config.getApiSecretCode() == null || config.getApiSecretCode().trim().length() == 0)
			throw new LLMConfigException("OpenAI api cannot work without needed api key configuration");
		try {
			AbstractGeboSecretContent secret = secretService.getSecretContentById(config.getApiSecretCode());
			if (secret.type() == GeboSecretType.TOKEN) {
				return ((GeboTokenContent) secret).getToken();
			}
			throw new LLMConfigException("OpenAI api can work only with an api key of type TOKEN");
		} catch (GeboCryptSecretException e) {
			throw new LLMConfigException("OpenAI api  key configuration gone wrong ", e);
		}
	}

	@Override
	public GDecisionModelType getType() {
		return type;
	}

	@Override
	public IGConfigurableDecisionModel<GOpenAIDecisionModelConfig> create(GOpenAIDecisionModelConfig config)
			throws LLMConfigException {
		OpenAIConfigurableDecisionModel model = new OpenAIConfigurableDecisionModel();
		model.initialize(config, type);
		return model;
	}

	/**
	 * The decision models of the account: the ones the models endpoint lists as such,
	 * and the model Spring AI uses by default for the Decisions API, which the endpoint
	 * may not list. A key the endpoint refuses is reported as the lookup's error.
	 */
	@Override
	public OperationStatus<List<GOpenAIDecisionModelChoice>> getModelChoices(GOpenAIDecisionModelConfig config) {
		final List<GOpenAIDecisionModelChoice> choices = new ArrayList<>();
		final OperationStatus<List<GOpenAIDecisionModelChoice>> status = OperationStatus.of(choices);
		try {
			final OpenAIApiConfig apiconfig = new OpenAIApiConfig();
			apiconfig.setProviderId(type.getProviderId());
			apiconfig.setApiKey(apiKeyOf(config));
			if (config.getBaseUrl() != null && !config.getBaseUrl().isBlank()) {
				apiconfig.setBasePath(config.getBaseUrl());
			}
			for (OpenAIModel model : openaiApiUtil.getModels(apiconfig)) {
				if (Boolean.FALSE.equals(model.getActive()) || !isDecisionModel(model.getId()))
					continue;
				choices.add(choice(model.getId()));
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("OpenAI lists " + choices.size() + " decision model(s)");
			}
		} catch (LLMConfigException e) {
			status.getMessages().add(GUserMessage.errorMessage("OpenAI api key", e.getMessage()));
			return status;
		} catch (Throwable e) {
			LOGGER.warn("Cannot list the OpenAI models for the decision models lookup: " + e.getMessage());
			status.getMessages().add(GUserMessage.errorMessage("Cannot access the OpenAI models list",
					e.getMessage() != null ? e.getMessage() : e.getClass().getSimpleName()));
			return status;
		}
		if (choices.stream().noneMatch(c -> OpenAiDecisionOptions.DEFAULT_DECISION_MODEL.equals(c.getCode()))) {
			choices.add(choice(OpenAiDecisionOptions.DEFAULT_DECISION_MODEL));
		}
		return status;
	}

	/** OpenAI names its decision models after the Decisions API's model family. */
	static boolean isDecisionModel(String modelId) {
		if (modelId == null)
			return false;
		final String id = modelId.toLowerCase();
		return id.contains("luna") || id.contains("decision");
	}

	private static GOpenAIDecisionModelChoice choice(String modelId) {
		GOpenAIDecisionModelChoice choice = new GOpenAIDecisionModelChoice();
		choice.setCode(modelId);
		choice.setDescription(modelId);
		ModelMetaInfo meta = new ModelMetaInfo();
		meta.setProviderId(type.getProviderId());
		meta.setModelId(modelId);
		meta.setInformativeUrl(INFORMATIVE_URL);
		choice.setMetaInfos(meta);
		choice.setInformativeUrl(INFORMATIVE_URL);
		return choice;
	}

	@Override
	public GOpenAIDecisionModelConfig createBaseConfiguration(String presetModel) {
		GOpenAIDecisionModelConfig clean = new GOpenAIDecisionModelConfig();
		clean.setModelTypeCode(getType().getCode());
		clean.setChoosedModel(new GOpenAIDecisionModelChoice());
		clean.getChoosedModel().setCode(presetModel);
		clean.getChoosedModel().setDescription("decision model " + presetModel);
		clean.setDescription("OpenAI decision model " + presetModel);
		return clean;
	}

	@Override
	public OperationStatus<GOpenAIDecisionModelConfig> insertAndConfigure(GOpenAIDecisionModelConfig config)
			throws GeboPersistenceException, LLMConfigException {
		return configureHandler.insertAndConfigure(config, type);
	}
}
