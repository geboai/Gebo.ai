/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.ollama.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springaicommunity.typesafe.TypeSafeClient;
import org.springframework.ai.ollama.OllamaDecisionModel;
import org.springframework.ai.ollama.api.OllamaApi;
import org.springframework.ai.ollama.decision.OllamaDecisionOptions;
import org.springframework.ai.ollama.management.ModelManagementOptions;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

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
import ai.gebo.llms.ollama.model.GOllamaDecisionModelChoice;
import ai.gebo.llms.ollama.model.GOllamaDecisionModelConfig;
import ai.gebo.model.OperationStatus;
import ai.gebo.secrets.model.AbstractGeboSecretContent;
import ai.gebo.secrets.model.GeboSecretType;
import ai.gebo.secrets.model.GeboTokenContent;
import ai.gebo.secrets.services.IGeboSecretsAccessService;
import io.micrometer.observation.ObservationRegistry;
import lombok.AllArgsConstructor;

/**
 * The decision models of an Ollama server (its models with the "decision" capability,
 * such as d1-omni), served by the server's System One endpoint: the type, the models
 * the server offers and the Spring AI {@link OllamaDecisionModel} built from a
 * configuration.
 */
@Service
@AllArgsConstructor
public class OllamaDecisionModelConfigurationSupportService
		implements IGDecisionModelConfigurationSupportService<GOllamaDecisionModelChoice, GOllamaDecisionModelConfig> {

	private static final Logger LOGGER = LoggerFactory.getLogger(OllamaDecisionModelConfigurationSupportService.class);

	/**
	 * The System One SDK requires a key, which Ollama ignores: the one Spring AI's own
	 * Ollama configuration sends, when the server has no token of its own.
	 */
	static final String PLACEHOLDER_API_KEY = "ollama";

	static final GDecisionModelType type = new GDecisionModelType();
	static {
		type.setCode("decision-ollama");
		type.setDescription("decision service hosted local Ollama server");
		type.setModelConfigurationClass(GOllamaDecisionModelConfig.class.getName());
		type.setProviderId("ollama");
	}

	final OllamaModelsLookupService modelsService;
	final IGeboSecretsAccessService secretService;
	final IGLlmsServiceClientsProviderFactory serviceClientsProviderFactory;
	final ModelRuntimeConfigureHandler configureHandler;
	final ObservationRegistry observationRegistry;

	class OllamaConfigurableDecisionModel
			extends GAbstractConfigurableDecisionModel<GOllamaDecisionModelConfig, OllamaDecisionModel> {

		public OllamaConfigurableDecisionModel() {
			super(OllamaDecisionModelConfigurationSupportService.this.observationRegistry);
		}

		@Override
		protected OllamaDecisionModel configureModel(GOllamaDecisionModelConfig config, GDecisionModelType type)
				throws LLMConfigException {
			if (config.getBaseUrl() == null || config.getBaseUrl().isBlank()) {
				throw new LLMConfigException("The Ollama decision model needs the server base url");
			}
			final IGLlmsServiceClientsProvider clientsProvider = serviceClientsProviderFactory.get(getCode());
			final RestClient.Builder restClientBuilder = clientsProvider.getRestClientBuilder();
			final OllamaApi ollamaApi = OllamaApi.builder().baseUrl(config.getBaseUrl())
					.restClientBuilder(restClientBuilder).webClientBuilder(clientsProvider.getWebClientBuilder())
					.build();
			// The System One client talks to the same Ollama server as the Ollama API
			final TypeSafeClient systemOneClient = TypeSafeClient.builder().baseUrl(config.getBaseUrl())
					.apiKey(apiKeyOf(config)).restClientBuilder(restClientBuilder).build();
			final OllamaDecisionOptions.Builder options = OllamaDecisionOptions.builder();
			if (config.getChoosedModel() != null && config.getChoosedModel().getCode() != null) {
				options.model(config.getChoosedModel().getCode());
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Ollama decision model code=" + config.getCode() + " on " + config.getBaseUrl()
						+ " with model " + safeGetModelCode());
			}
			// The model is never pulled here: the admin chooses among the models the server
			// already serves, see getModelChoices
			return OllamaDecisionModel.builder().ollamaApi(ollamaApi).systemOneClient(systemOneClient)
					.decisionOptions(options.build()).modelManagementOptions(ModelManagementOptions.defaults())
					.observationRegistry(observationRegistry).build();
		}
	}

	/** The server's token when the configuration names one, else the placeholder key. */
	String apiKeyOf(GOllamaDecisionModelConfig config) throws LLMConfigException {
		if (config.getApiSecretCode() == null || config.getApiSecretCode().isBlank()) {
			return PLACEHOLDER_API_KEY;
		}
		try {
			AbstractGeboSecretContent secret = secretService.getSecretContentById(config.getApiSecretCode());
			if (secret.type() == GeboSecretType.TOKEN) {
				final String token = ((GeboTokenContent) secret).getToken();
				return token != null && !token.isBlank() ? token : PLACEHOLDER_API_KEY;
			}
			throw new LLMConfigException("The Ollama decision model can work only with a secret of type TOKEN");
		} catch (GeboCryptSecretException e) {
			throw new LLMConfigException("Ollama token configuration gone wrong ", e);
		}
	}

	@Override
	public GDecisionModelType getType() {
		return type;
	}

	@Override
	public IGConfigurableDecisionModel<GOllamaDecisionModelConfig> create(GOllamaDecisionModelConfig config)
			throws LLMConfigException {
		OllamaConfigurableDecisionModel model = new OllamaConfigurableDecisionModel();
		model.initialize(config, type);
		return model;
	}

	@Override
	public OperationStatus<List<GOllamaDecisionModelChoice>> getModelChoices(GOllamaDecisionModelConfig config) {
		return modelsService.getDecisionModels(config);
	}

	@Override
	public GOllamaDecisionModelConfig createBaseConfiguration(String presetModel) {
		GOllamaDecisionModelConfig clean = new GOllamaDecisionModelConfig();
		clean.setModelTypeCode(getType().getCode());
		clean.setChoosedModel(new GOllamaDecisionModelChoice());
		clean.getChoosedModel().setCode(presetModel);
		clean.getChoosedModel().setDescription("decision model " + presetModel);
		clean.setDescription("Ollama decision model " + presetModel);
		return clean;
	}

	@Override
	public OperationStatus<GOllamaDecisionModelConfig> insertAndConfigure(GOllamaDecisionModelConfig config)
			throws GeboPersistenceException, LLMConfigException {
		return configureHandler.insertAndConfigure(config, type);
	}
}
