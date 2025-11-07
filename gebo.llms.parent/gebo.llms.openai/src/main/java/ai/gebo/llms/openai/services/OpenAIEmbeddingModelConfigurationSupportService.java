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

import org.springframework.ai.document.MetadataMode;
import org.springframework.ai.openai.OpenAiEmbeddingModel;
import org.springframework.ai.openai.OpenAiEmbeddingOptions;
import org.springframework.ai.openai.OpenAiEmbeddingOptions.Builder;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.ai.openai.api.OpenAiApi.EmbeddingModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;

import ai.gebo.llms.abstraction.layer.model.GEmbeddingModelType;
import ai.gebo.llms.abstraction.layer.services.GAbstractConfigurableEmbeddingModel;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableEmbeddingModel;
import ai.gebo.llms.abstraction.layer.services.IGEmbeddingModelConfigurationSupportService;
import ai.gebo.llms.abstraction.layer.services.IGLlmsServiceClientsProvider;
import ai.gebo.llms.abstraction.layer.services.IGLlmsServiceClientsProviderFactory;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.abstraction.layer.services.ModelRuntimeConfigureHandler;
import ai.gebo.llms.abstraction.layer.vectorstores.IGVectorStoreFactory;
import ai.gebo.llms.abstraction.layer.vectorstores.IGVectorStoreFactoryProvider;
import ai.gebo.llms.models.metainfos.ModelMetaInfo;
import ai.gebo.llms.openai.api.utils.IGOpenAIApiUtil;
import ai.gebo.llms.openai.model.GOpenAIEmbeddingModelChoice;
import ai.gebo.llms.openai.model.GOpenAIEmbeddingModelConfig;
import ai.gebo.model.OperationStatus;
import ai.gebo.openai.integration.client.model.OpenAIApiConfig;
import ai.gebo.secrets.model.AbstractGeboSecretContent;
import ai.gebo.secrets.model.GeboSecretType;
import ai.gebo.secrets.model.GeboTokenContent;
import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.crypting.services.GeboCryptSecretException;
import ai.gebo.secrets.services.IGeboSecretsAccessService;
import lombok.AllArgsConstructor;

/**
 * AI generated comments
 * 
 * Service responsible for configuring and managing OpenAI embedding models.
 * Only activated when openAIEnabled property is set to true.
 */
@ConditionalOnProperty(prefix = "ai.gebo.llms.config", name = "openAIEnabled", havingValue = "true")
@Service
@AllArgsConstructor
public class OpenAIEmbeddingModelConfigurationSupportService implements
		IGEmbeddingModelConfigurationSupportService<GOpenAIEmbeddingModelChoice, GOpenAIEmbeddingModelConfig> {

	/**
	 * Static embedding model type definition for OpenAI
	 */
	static final GEmbeddingModelType type = new GEmbeddingModelType();
	static {
		type.setCode("embedding-OpenAI");
		type.setDescription("embedding service hosted on OpenAI");
		type.setModelConfigurationClass(GOpenAIEmbeddingModelConfig.class.getName());
	}

	/**
	 * List of available OpenAI embedding model choices Populated from OpenAI's
	 * EmbeddingModel enum values
	 */
	static final List<GOpenAIEmbeddingModelChoice> choices = new ArrayList<GOpenAIEmbeddingModelChoice>();
	static {
		EmbeddingModel[] models = EmbeddingModel.values();
		for (EmbeddingModel embeddingModel : models) {
			GOpenAIEmbeddingModelChoice choice = new GOpenAIEmbeddingModelChoice();
			choice.setCode(embeddingModel.getValue());
			choice.setDescription(embeddingModel.getValue());
			choices.add(choice);
		}
	}

	/**
	 * Service for accessing secrets like API keys
	 */
	final IGeboSecretsAccessService secretService;

	/**
	 * Provider for vector store factories
	 */
	final IGVectorStoreFactoryProvider storeFactoryProvider;

	/**
	 * Utility for OpenAI API operations
	 */
	final IGOpenAIApiUtil openaiApiUtil;

	/**
	 * Factory for LLM service clients
	 */
	final IGLlmsServiceClientsProviderFactory serviceClientsProviderFactory;
	final ModelRuntimeConfigureHandler configureHandler;

	/**
	 * Inner class that implements the configurable embedding model for OpenAI
	 */
	class OpenAIConfigurableEmbeddingModel
			extends GAbstractConfigurableEmbeddingModel<GOpenAIEmbeddingModelConfig, OpenAiEmbeddingModel> {

		/**
		 * Constructor for OpenAI configurable embedding model
		 */
		public OpenAIConfigurableEmbeddingModel() {
			super(storeFactoryProvider);
		}

		/**
		 * Configures and creates an OpenAI embedding model based on the provided
		 * configuration
		 * 
		 * @param config The OpenAI embedding model configuration
		 * @param type   The embedding model type
		 * @return Configured OpenAiEmbeddingModel instance
		 * @throws LLMConfigException If configuration fails
		 */
		@Override
		protected OpenAiEmbeddingModel configureModel(GOpenAIEmbeddingModelConfig config, GEmbeddingModelType type)
				throws LLMConfigException {
			String apiKey = null;
			String user = null;
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
			IGLlmsServiceClientsProvider clientsProvider = serviceClientsProviderFactory.get(getCode());
			org.springframework.web.client.RestClient.Builder restClient = clientsProvider.getRestClientBuilder();
			org.springframework.web.reactive.function.client.WebClient.Builder webClient = clientsProvider
					.getWebClientBuilder();
			RetryTemplate retryTemplate = clientsProvider.getRetryTemplate();
			org.springframework.ai.openai.api.OpenAiApi.Builder apiBuilder = OpenAiApi.builder();
			apiBuilder.webClientBuilder(webClient);
			apiBuilder.restClientBuilder(restClient);
			OpenAiApi openaiApi = apiBuilder.apiKey(apiKey).build();

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
	 * Returns the embedding model type supported by this service
	 * 
	 * @return The OpenAI embedding model type
	 */
	@Override
	public GEmbeddingModelType getType() {
		return type;
	}

	/**
	 * Creates a new configurable embedding model with the given configuration
	 * 
	 * @param config The OpenAI embedding model configuration
	 * @return A configured embedding model instance
	 * @throws LLMConfigException If configuration fails
	 */
	@Override
	public IGConfigurableEmbeddingModel<GOpenAIEmbeddingModelConfig> create(GOpenAIEmbeddingModelConfig config)
			throws LLMConfigException {
		OpenAIConfigurableEmbeddingModel model = new OpenAIConfigurableEmbeddingModel();
		model.initialize(config, type);
		return model;
	}

	/**
	 * Retrieves available model choices for the given configuration
	 * 
	 * @param config The OpenAI embedding model configuration
	 * @return Operation status containing list of available model choices
	 */
	@Override
	public OperationStatus<List<GOpenAIEmbeddingModelChoice>> getModelChoices(GOpenAIEmbeddingModelConfig config) {
		OpenAIApiConfig providerConfig = new OpenAIApiConfig();
		providerConfig.setProviderId("openai");

		return this.openaiApiUtil.getEmbeddingModels(GOpenAIEmbeddingModelChoice.class, providerConfig, config,
				(choice) -> {
					ModelMetaInfo meta = new ModelMetaInfo();
					meta.setInformativeUrl("https://platform.openai.com/docs/guides/embeddings");
					return meta;
				});
	}

	/**
	 * Creates a base configuration for the embedding model with a preset model
	 * 
	 * @param presetModel The model name to preset
	 * @return A new OpenAI embedding model configuration
	 */
	@Override
	public GOpenAIEmbeddingModelConfig createBaseConfiguration(String presetModel) {
		GOpenAIEmbeddingModelConfig clean = new GOpenAIEmbeddingModelConfig();
		clean.setModelTypeCode(getType().getCode());
		clean.setChoosedModel(new GOpenAIEmbeddingModelChoice());
		clean.getChoosedModel().setCode(presetModel);
		clean.getChoosedModel().setDescription("embedding model " + presetModel);
		clean.setDescription("OpenAI embedding model " + presetModel);
		clean.setModelTypeCode(getType().getCode());
		return clean;
	}

	@Override
	public OperationStatus<GOpenAIEmbeddingModelConfig> insertAndConfigure(GOpenAIEmbeddingModelConfig config)
			throws GeboPersistenceException, LLMConfigException {

		return configureHandler.insertAndConfigure(config, type);
	}
}