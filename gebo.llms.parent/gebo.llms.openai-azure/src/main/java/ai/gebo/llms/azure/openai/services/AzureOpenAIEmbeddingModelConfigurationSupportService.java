/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.llms.azure.openai.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.ai.azure.openai.AzureOpenAiEmbeddingModel;
import org.springframework.ai.azure.openai.AzureOpenAiEmbeddingOptions;
import org.springframework.ai.azure.openai.AzureOpenAiEmbeddingOptions.Builder;
import org.springframework.ai.document.MetadataMode;
import org.springframework.ai.openai.api.OpenAiApi.EmbeddingModel;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import com.azure.ai.openai.OpenAIClient;
import com.azure.ai.openai.OpenAIClientBuilder;
import com.azure.core.credential.AzureKeyCredential;

import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.llms.abstraction.layer.model.GEmbeddingModelType;
import ai.gebo.llms.abstraction.layer.services.GAbstractConfigurableEmbeddingModel;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableEmbeddingModel;
import ai.gebo.llms.abstraction.layer.services.IGEmbeddingModelConfigurationSupportService;
import ai.gebo.llms.abstraction.layer.services.IGLlmsServiceClientsProviderFactory;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.abstraction.layer.services.ModelRuntimeConfigureHandler;
import ai.gebo.llms.abstraction.layer.vectorstores.IGVectorStoreFactoryProvider;
import ai.gebo.llms.azure.openai.model.GAzureOpenAIEmbeddingModelChoice;
import ai.gebo.llms.azure.openai.model.GAzureOpenAIEmbeddingModelConfig;
import ai.gebo.llms.azure.openai.services.AzureOpenAIConfigFactory.AzureOpenAIBaseConfig;
import ai.gebo.llms.models.metainfos.ModelMetaInfo;
import ai.gebo.llms.openai.api.utils.IGOpenAIApiUtil;
import ai.gebo.model.OperationStatus;
import ai.gebo.openai.integration.client.model.OpenAIApiConfig;
import ai.gebo.secrets.services.IGeboSecretsAccessService;
import lombok.AllArgsConstructor;

/**
 * AI generated comments
 * 
 * Service responsible for configuring and managing Azure/OpenAI embedding
 * models. Only activated when openAIEnabled property is set to true.
 */
@ConditionalOnProperty(prefix = "ai.gebo.llms.config", name = "azureOpenAIEnabled", havingValue = "true")
@Service
@AllArgsConstructor
public class AzureOpenAIEmbeddingModelConfigurationSupportService implements
		IGEmbeddingModelConfigurationSupportService<GAzureOpenAIEmbeddingModelChoice, GAzureOpenAIEmbeddingModelConfig> {

	/**
	 * Static embedding model type definition for OpenAI
	 */
	static final GEmbeddingModelType type = new GEmbeddingModelType();
	static {
		type.setCode("azure-embedding-OpenAI");
		type.setDescription("embedding service hosted on Azure/OpenAI");
		type.setModelConfigurationClass(GAzureOpenAIEmbeddingModelConfig.class.getName());
	}

	/**
	 * List of available OpenAI embedding model choices Populated from OpenAI's
	 * EmbeddingModel enum values
	 */
	static final List<GAzureOpenAIEmbeddingModelChoice> choices = new ArrayList<GAzureOpenAIEmbeddingModelChoice>();
	static {
		EmbeddingModel[] models = EmbeddingModel.values();
		for (EmbeddingModel embeddingModel : models) {
			GAzureOpenAIEmbeddingModelChoice choice = new GAzureOpenAIEmbeddingModelChoice();
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
	final AzureOpenAIConfigFactory azureClientBuilderFactory;
	final ModelRuntimeConfigureHandler configureHandler;

	/**
	 * Inner class that implements the configurable embedding model for OpenAI
	 */
	class AzureOpenAIConfigurableEmbeddingModel
			extends GAbstractConfigurableEmbeddingModel<GAzureOpenAIEmbeddingModelConfig, AzureOpenAiEmbeddingModel> {

		/**
		 * Constructor for OpenAI configurable embedding model
		 */
		public AzureOpenAIConfigurableEmbeddingModel() {
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
		protected AzureOpenAiEmbeddingModel configureModel(GAzureOpenAIEmbeddingModelConfig config,
				GEmbeddingModelType type) throws LLMConfigException {

			AzureOpenAIBaseConfig coords = azureClientBuilderFactory.createClientBulder(config);
			OpenAIClientBuilder clientBuilder = new OpenAIClientBuilder();
			clientBuilder.credential(new AzureKeyCredential(coords.getApiKey()));
			clientBuilder.endpoint(coords.getEndpoint());
			Builder embeddingBuilder = AzureOpenAiEmbeddingOptions.builder();
			embeddingBuilder.user(coords.getUser());
			embeddingBuilder.deploymentName(coords.getModel());
			MetadataMode meta = MetadataMode.EMBED;
			OpenAIClient openaiClient = clientBuilder.buildClient();
			AzureOpenAiEmbeddingOptions options = embeddingBuilder.build();
			AzureOpenAiEmbeddingModel model = new AzureOpenAiEmbeddingModel(openaiClient, meta, options);
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
	public IGConfigurableEmbeddingModel<GAzureOpenAIEmbeddingModelConfig> create(
			GAzureOpenAIEmbeddingModelConfig config) throws LLMConfigException {
		AzureOpenAIConfigurableEmbeddingModel model = new AzureOpenAIConfigurableEmbeddingModel();
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
	public OperationStatus<List<GAzureOpenAIEmbeddingModelChoice>> getModelChoices(
			GAzureOpenAIEmbeddingModelConfig config) {
		OpenAIApiConfig providerConfig = new OpenAIApiConfig();
		providerConfig.setProviderId("openai");

		return this.openaiApiUtil.getEmbeddingModels(GAzureOpenAIEmbeddingModelChoice.class, providerConfig, config,
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
	public GAzureOpenAIEmbeddingModelConfig createBaseConfiguration(String presetModel) {
		GAzureOpenAIEmbeddingModelConfig clean = new GAzureOpenAIEmbeddingModelConfig();
		clean.setModelTypeCode(getType().getCode());
		clean.setChoosedModel(new GAzureOpenAIEmbeddingModelChoice());
		clean.getChoosedModel().setCode(presetModel);
		clean.getChoosedModel().setDescription("embedding model " + presetModel);
		clean.setDescription("OpenAI embedding model " + presetModel);
		clean.setModelTypeCode(getType().getCode());
		return clean;
	}

	@Override
	public OperationStatus<GAzureOpenAIEmbeddingModelConfig> insertAndConfigure(GAzureOpenAIEmbeddingModelConfig config)
			throws GeboPersistenceException, LLMConfigException {

		return configureHandler.insertAndConfigure(config, type);
	}
}