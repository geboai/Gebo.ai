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
 * Service for configuring and supporting Mistral AI embedding models.
 * This service implements IGEmbeddingModelConfigurationSupportService to provide
 * Mistral AI-specific embedding model configuration and creation functionality.
 * Only active when mistralAIEnabled property is set to true.
 */
package ai.gebo.llms.mistralai.services;

import java.util.List;

import org.springframework.ai.document.MetadataMode;
import org.springframework.ai.mistralai.MistralAiEmbeddingModel;
import org.springframework.ai.mistralai.MistralAiEmbeddingOptions;
import org.springframework.ai.mistralai.api.MistralAiApi;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.crypting.services.GeboCryptSecretException;
import ai.gebo.llms.abstraction.layer.model.GEmbeddingModelType;
import ai.gebo.llms.abstraction.layer.services.GAbstractConfigurableEmbeddingModel;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableEmbeddingModel;
import ai.gebo.llms.abstraction.layer.services.IGEmbeddingModelConfigurationSupportService;
import ai.gebo.llms.abstraction.layer.services.IGLlmsServiceClientsProviderFactory;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.abstraction.layer.services.ModelRuntimeConfigureHandler;
import ai.gebo.llms.abstraction.layer.vectorstores.IGVectorStoreFactoryProvider;
import ai.gebo.llms.mistralai.model.GMistralEmbeddingModelChoice;
import ai.gebo.llms.mistralai.model.GMistralEmbeddingModelConfig;
import ai.gebo.model.OperationStatus;
import ai.gebo.secrets.model.AbstractGeboSecretContent;
import ai.gebo.secrets.model.GeboSecretType;
import ai.gebo.secrets.model.GeboTokenContent;
import ai.gebo.secrets.services.IGeboSecretsAccessService;
import lombok.AllArgsConstructor;

@ConditionalOnProperty(prefix = "ai.gebo.llms.config", name = "mistralAIEnabled", havingValue = "true")
@Service
@AllArgsConstructor
public class MistralEmbeddingModelConfigurationSupportService implements
		IGEmbeddingModelConfigurationSupportService<GMistralEmbeddingModelChoice, GMistralEmbeddingModelConfig> {
	/**
	 * Static embedding model type for Mistral AI services.
	 */
	static final GEmbeddingModelType type = new GEmbeddingModelType();
	static {
		type.setCode("embedding-mistral");
		type.setDescription("embedding service hosted on Mistral AI");
		type.setModelConfigurationClass(GMistralEmbeddingModelConfig.class.getName());
	}

	

	/**
	 * Service for accessing secrets, used to retrieve API keys.
	 */
	final IGeboSecretsAccessService secretService;

	/**
	 * Provider for vector store factories.
	 */
	final IGVectorStoreFactoryProvider storeFactoryProvider;
	final MistralModelsLookupService mistralModelsLookupService;
	final IGLlmsServiceClientsProviderFactory serviceClientsProviderFactory;
	final ModelRuntimeConfigureHandler configureHandler;
	/*
	 * @Autowired IGOpenAIApiUtil openaiApiUtil;
	 */

	/**
	 * Inner class implementing a configurable embedding model for Mistral AI.
	 * Extends the abstract class with specific Mistral AI implementation.
	 */
	class MistralConfigurableEmbeddingModel
			extends GAbstractConfigurableEmbeddingModel<GMistralEmbeddingModelConfig, MistralAiEmbeddingModel> {

		/**
		 * Constructor that initializes the model with the vector store factory
		 * provider.
		 */
		public MistralConfigurableEmbeddingModel() {
			super(storeFactoryProvider);

		}

		/**
		 * Configures the Mistral AI embedding model using the provided configuration.
		 * Retrieves the API key from secrets service and sets up the model options.
		 *
		 * @param config The Mistral AI embedding model configuration
		 * @param type   The embedding model type
		 * @return Configured MistralAiEmbeddingModel instance
		 * @throws LLMConfigException if configuration fails or required API key is
		 *                            missing
		 */
		@Override
		protected MistralAiEmbeddingModel configureModel(GMistralEmbeddingModelConfig config, GEmbeddingModelType type)
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
			MistralAiApi openaiApi = new MistralAiApi(apiKey);
			org.springframework.ai.mistralai.MistralAiEmbeddingOptions.Builder builder = MistralAiEmbeddingOptions
					.builder();

			if (config.getChoosedModel() != null) {
				builder = builder.withModel(config.getChoosedModel().getCode());
			}

			MistralAiEmbeddingOptions options = builder.build();
			MetadataMode meta = MetadataMode.EMBED;
			MistralAiEmbeddingModel model = new MistralAiEmbeddingModel(openaiApi, meta, options,
					RetryTemplate.defaultInstance());
			return model;
		}

	};

	

	/**
	 * Returns the embedding model type supported by this service.
	 * 
	 * @return GEmbeddingModelType for Mistral AI
	 */
	@Override
	public GEmbeddingModelType getType() {

		return type;
	}

	/**
	 * Creates and initializes a configurable embedding model with the given
	 * configuration.
	 * 
	 * @param config The Mistral AI embedding model configuration
	 * @return Configured embedding model instance
	 * @throws LLMConfigException if the model creation or initialization fails
	 */
	@Override
	public IGConfigurableEmbeddingModel<GMistralEmbeddingModelConfig> create(GMistralEmbeddingModelConfig config)
			throws LLMConfigException {
		MistralConfigurableEmbeddingModel model = new MistralConfigurableEmbeddingModel();
		model.initialize(config, type);
		return model;
	}

	/**
	 * Retrieves the list of available model choices for Mistral AI embedding.
	 * 
	 * @param config The Mistral AI embedding model configuration
	 * @return Operation status containing the list of available model choices
	 */
	@Override
	public OperationStatus<List<GMistralEmbeddingModelChoice>> getModelChoices(GMistralEmbeddingModelConfig config) {
		
		return mistralModelsLookupService.getEmbeddingModelsChoices(config);
	}

	/**
	 * Creates a base configuration for a Mistral AI embedding model with the
	 * specified preset model.
	 * 
	 * @param presetModel The code of the preset model to use
	 * @return Basic configuration for a Mistral AI embedding model
	 */
	@Override
	public GMistralEmbeddingModelConfig createBaseConfiguration(String presetModel) {
		GMistralEmbeddingModelConfig clean = new GMistralEmbeddingModelConfig();
		clean.setModelTypeCode(getType().getCode());
		clean.setChoosedModel(new GMistralEmbeddingModelChoice());
		clean.getChoosedModel().setCode(presetModel);
		clean.getChoosedModel().setDescription("embedding model " + presetModel);
		clean.setDescription("Mistral AI embedding model " + presetModel);
		clean.setModelTypeCode(getType().getCode());
		return clean;
	}

	@Override
	public OperationStatus<GMistralEmbeddingModelConfig> insertAndConfigure(GMistralEmbeddingModelConfig config) throws GeboPersistenceException, LLMConfigException {
		
		return configureHandler.insertAndConfigure(config, type);
	}

}