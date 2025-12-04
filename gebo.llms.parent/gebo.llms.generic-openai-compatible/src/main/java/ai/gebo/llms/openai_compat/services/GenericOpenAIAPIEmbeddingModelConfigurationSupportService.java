/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.llms.openai_compat.services;

import java.util.List;

import org.springframework.ai.document.MetadataMode;
import org.springframework.ai.openai.OpenAiEmbeddingModel;
import org.springframework.ai.openai.OpenAiEmbeddingOptions;
import org.springframework.ai.openai.OpenAiEmbeddingOptions.Builder;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.ai.IGToolCallbackSourceRepositoryPattern;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.crypting.services.GeboCryptSecretException;
import ai.gebo.llms.abstraction.layer.model.GEmbeddingModelType;
import ai.gebo.llms.abstraction.layer.services.GAbstractConfigurableEmbeddingModel;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableEmbeddingModel;
import ai.gebo.llms.abstraction.layer.services.IGEmbeddingModelConfigurationSupportService;
import ai.gebo.llms.abstraction.layer.services.IGLlmsServiceClientsProvider;
import ai.gebo.llms.abstraction.layer.services.IGLlmsServiceClientsProviderFactory;
import ai.gebo.llms.abstraction.layer.services.ILLMTypeFiltrerRepositoryPattern;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.abstraction.layer.services.ModelRuntimeConfigureHandler;
import ai.gebo.llms.abstraction.layer.vectorstores.IGVectorStoreFactoryProvider;
import ai.gebo.llms.models.metainfos.ModelMetaInfo;
import ai.gebo.llms.openai.api.utils.IGOpenAIApiUtil;
import ai.gebo.llms.openai_compat.model.GenericOpenAIAPIChatModelChoice;
import ai.gebo.llms.openai_compat.model.GenericOpenAIAPIEmbeddingModelChoice;
import ai.gebo.llms.openai_compat.model.GenericOpenAIAPIEmbeddingModelConfig;
import ai.gebo.llms.openai_compat.model.GenericOpenAIChatModelTypeConfig;
import ai.gebo.llms.openai_compat.model.GenericOpenAIEmbeddingModelTypeConfig;
import ai.gebo.model.OperationStatus;
import ai.gebo.openai.integration.client.model.OpenAIApiConfig;
import ai.gebo.secrets.model.AbstractGeboSecretContent;
import ai.gebo.secrets.model.GeboSecretType;
import ai.gebo.secrets.model.GeboTokenContent;
import ai.gebo.secrets.services.IGeboSecretsAccessService;

/**
 * AI generated comments
 * 
 * Service to configure and support OpenAI-compatible embedding models. This
 * class implements the embedding model configuration support interface for
 * generic OpenAI API embedding models.
 */
public class GenericOpenAIAPIEmbeddingModelConfigurationSupportService implements
		IGEmbeddingModelConfigurationSupportService<GenericOpenAIAPIEmbeddingModelChoice, GenericOpenAIAPIEmbeddingModelConfig> {
	/** The embedding model type configuration */
	final GenericOpenAIEmbeddingModelTypeConfig type;
	/** Service to access secrets for API authentication */
	final IGeboSecretsAccessService secretService;
	/** Utility for OpenAI API interactions */
	final IGOpenAIApiUtil openaiApiUtil;
	/** Repository for tool callbacks */
	final IGToolCallbackSourceRepositoryPattern functionsRepo;
	/** Provider for vector store factories */
	final IGVectorStoreFactoryProvider storeFactoryProvider;
	/** Service to get available models */
	final ModelsListProviderProxyService modelsListProxyService;
	/** Factory for LLM service clients */
	final IGLlmsServiceClientsProviderFactory serviceClientsProviderFactory;
	final ModelRuntimeConfigureHandler configureHandler;
	final ILLMTypeFiltrerRepositoryPattern llmTypeFiltrerRepoPattern;

	/**
	 * Constructor initializing the service with required dependencies.
	 *
	 * @param type                          The embedding model type configuration
	 * @param secretService                 Service for accessing API keys
	 * @param openaiApiUtil                 Utility for OpenAI API operations
	 * @param functionsRepo                 Repository for tool callbacks
	 * @param storeFactoryProvider          Provider for vector store factories
	 * @param modelsListProxyService        Service to retrieve models list
	 * @param serviceClientsProviderFactory Factory for client providers
	 */
	public GenericOpenAIAPIEmbeddingModelConfigurationSupportService(GenericOpenAIEmbeddingModelTypeConfig type,
			IGeboSecretsAccessService secretService, IGOpenAIApiUtil openaiApiUtil,
			IGToolCallbackSourceRepositoryPattern functionsRepo, IGVectorStoreFactoryProvider storeFactoryProvider,
			ModelsListProviderProxyService modelsListProxyService,
			IGLlmsServiceClientsProviderFactory serviceClientsProviderFactory,
			ModelRuntimeConfigureHandler configureHandler, ILLMTypeFiltrerRepositoryPattern llmTypeFiltrerRepoPattern) {
		this.type = type;
		this.secretService = secretService;
		this.functionsRepo = functionsRepo;
		this.openaiApiUtil = openaiApiUtil;
		this.storeFactoryProvider = storeFactoryProvider;
		this.modelsListProxyService = modelsListProxyService;
		this.serviceClientsProviderFactory = serviceClientsProviderFactory;
		this.configureHandler = configureHandler;
		this.llmTypeFiltrerRepoPattern = llmTypeFiltrerRepoPattern;
	}

	/**
	 * Inner class that implements the configurable embedding model for OpenAI API.
	 * Extends the abstract configurable embedding model with OpenAI-specific
	 * implementation.
	 */
	class GenericOpenAIConfigurableEmbeddingModel
			extends GAbstractConfigurableEmbeddingModel<GenericOpenAIAPIEmbeddingModelConfig, OpenAiEmbeddingModel> {

		/**
		 * Constructor initializing the embedding model with a vector store factory
		 * provider.
		 */
		public GenericOpenAIConfigurableEmbeddingModel() {
			super(storeFactoryProvider);

		}

		/**
		 * Configures the OpenAI embedding model with the provided configuration.
		 *
		 * @param config The configuration for the embedding model
		 * @param type   The type of embedding model
		 * @return Configured OpenAiEmbeddingModel
		 * @throws LLMConfigException If configuration fails
		 */
		@Override
		protected OpenAiEmbeddingModel configureModel(GenericOpenAIAPIEmbeddingModelConfig config,
				GEmbeddingModelType type) throws LLMConfigException {
			String apiKey = null;
			String user = null;

			if (config.getApiSecretCode() != null && config.getApiSecretCode().trim().length() > 0) {
				try {
					AbstractGeboSecretContent secret = secretService.getSecretContentById(config.getApiSecretCode());
					if (secret.type() == GeboSecretType.TOKEN) {
						GeboTokenContent token = (GeboTokenContent) secret;
						apiKey = token.getToken();
						user = token.getUser();
					} else {
						throw new LLMConfigException(
								type.getDescription() + " api can work only with an api key of type TOKEN");
					}
				} catch (GeboCryptSecretException e) {
					throw new LLMConfigException(type.getDescription() + " api  key configuration gone wrong ", e);
				}
			}
			String baseUrl = GenericOpenAIAPIEmbeddingModelConfigurationSupportService.this.type.getBaseUrl();
			if (config.getBaseUrl() != null) {
				baseUrl = config.getBaseUrl();
			}
			org.springframework.ai.openai.api.OpenAiApi.Builder apiBuilder = OpenAiApi.builder();
			IGLlmsServiceClientsProvider clientsProvider = serviceClientsProviderFactory.get(getCode());
			org.springframework.web.client.RestClient.Builder restClient = clientsProvider.getRestClientBuilder();
			org.springframework.web.reactive.function.client.WebClient.Builder webClient = clientsProvider
					.getWebClientBuilder();
			RetryTemplate retryTemplate = clientsProvider.getRetryTemplate();
			apiBuilder.restClientBuilder(restClient);
			apiBuilder.webClientBuilder(webClient);
			OpenAiApi openaiApi = apiBuilder.apiKey(apiKey).baseUrl(baseUrl).build();
			Builder builder = OpenAiEmbeddingOptions.builder();

			if (config.getChoosedModel() != null) {
				builder = builder.model(config.getChoosedModel().getCode());
			}
			if (user != null) {
				builder = builder.user(user);
			}
			OpenAiEmbeddingOptions options = builder.build();
			MetadataMode meta = MetadataMode.EMBED;
			OpenAiEmbeddingModel model = new OpenAiEmbeddingModel(openaiApi, meta, options);
			return model;
		}

	};

	/**
	 * Gets the embedding model type.
	 *
	 * @return The embedding model type
	 */
	@Override
	public GEmbeddingModelType getType() {
		return type;
	}

	/**
	 * Creates a new configurable embedding model with the given configuration.
	 *
	 * @param config The configuration for the embedding model
	 * @return A new configurable embedding model
	 * @throws LLMConfigException If creation fails
	 */
	@Override
	public IGConfigurableEmbeddingModel<GenericOpenAIAPIEmbeddingModelConfig> create(
			GenericOpenAIAPIEmbeddingModelConfig config) throws LLMConfigException {
		GenericOpenAIConfigurableEmbeddingModel model = new GenericOpenAIConfigurableEmbeddingModel();
		model.initialize(config, type);
		return model;
	}

	/**
	 * Retrieves the available model choices for embedding models.
	 *
	 * @param config The configuration to use for retrieving model choices
	 * @return OperationStatus with a list of model choices
	 */
	@Override
	public OperationStatus<List<GenericOpenAIAPIEmbeddingModelChoice>> getModelChoices(
			GenericOpenAIAPIEmbeddingModelConfig config) {
		OperationStatus<List<GenericOpenAIAPIEmbeddingModelChoice>> result = null;
		OpenAIApiConfig providerConfig = OpenAIApiConfig.of(config, false);
		providerConfig.setProviderId(type.getProviderId());
		if (providerConfig.getBasePath() == null)
			providerConfig.setBasePath(type.getBaseUrl());
		if (type.getModelsListProvider() != null && type.getModelsListProvider().trim().length() > 0) {
			result = this.modelsListProxyService.geModels(type.getModelsListProvider(), config,
					GenericOpenAIAPIEmbeddingModelChoice.class, type);
		} else
			result = this.openaiApiUtil.getEmbeddingModels(GenericOpenAIAPIEmbeddingModelChoice.class, providerConfig,
					config, (choice) -> {
						return new ModelMetaInfo();
					}, type);
		return llmTypeFiltrerRepoPattern.filterEmbeddingModels(type, result);
	}

	/**
	 * Creates a base configuration for an embedding model with a preset model.
	 *
	 * @param presetModel The code for the preset model
	 * @return A new embedding model configuration
	 */
	@Override
	public GenericOpenAIAPIEmbeddingModelConfig createBaseConfiguration(String presetModel) {
		GenericOpenAIAPIEmbeddingModelConfig clean = new GenericOpenAIAPIEmbeddingModelConfig();
		clean.setModelTypeCode(getType().getCode());
		clean.setChoosedModel(new GenericOpenAIAPIEmbeddingModelChoice());
		clean.getChoosedModel().setCode(presetModel);
		clean.getChoosedModel().setDescription("embedding model " + presetModel);
		clean.setDescription(type.getDescription() + " " + presetModel);
		clean.setModelTypeCode(getType().getCode());
		return clean;
	}

	@Override
	public OperationStatus<GenericOpenAIAPIEmbeddingModelConfig> insertAndConfigure(
			GenericOpenAIAPIEmbeddingModelConfig config) throws GeboPersistenceException, LLMConfigException {

		return configureHandler.insertAndConfigure(config, type);
	}

}