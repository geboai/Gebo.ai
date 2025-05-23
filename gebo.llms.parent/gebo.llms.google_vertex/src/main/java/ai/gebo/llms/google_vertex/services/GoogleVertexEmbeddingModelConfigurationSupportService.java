/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.google_vertex.services;

import java.util.List;

import org.springframework.ai.vertexai.embedding.VertexAiEmbeddingConnectionDetails;
import org.springframework.ai.vertexai.embedding.text.VertexAiTextEmbeddingModel;
import org.springframework.ai.vertexai.embedding.text.VertexAiTextEmbeddingModelName;
import org.springframework.ai.vertexai.embedding.text.VertexAiTextEmbeddingOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import ai.gebo.llms.abstraction.layer.model.GBaseModelChoice;
import ai.gebo.llms.abstraction.layer.model.GEmbeddingModelType;
import ai.gebo.llms.abstraction.layer.services.GAbstractConfigurableEmbeddingModel;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableEmbeddingModel;
import ai.gebo.llms.abstraction.layer.services.IGEmbeddingModelConfigurationSupportService;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.abstraction.layer.vectorstores.IGVectorStoreFactoryProvider;
import ai.gebo.llms.google_vertex.model.GGoogleVertexEmbeddingModelChoice;
import ai.gebo.llms.google_vertex.model.GGoogleVertexEmbeddingModelConfig;
import ai.gebo.model.OperationStatus;
import ai.gebo.secrets.model.AbstractGeboSecretContent;
import ai.gebo.secrets.model.GeboTokenContent;
import ai.gebo.crypting.services.GeboCryptSecretException;
import ai.gebo.secrets.services.IGeboSecretsAccessService;

/**
 * AI generated comments
 * 
 * Service responsible for configuring and creating Google Vertex AI embedding models.
 * This service is conditionally enabled when the property ai.gebo.llms.config.googleVertexEnabled is set to true.
 */
@ConditionalOnProperty(prefix = "ai.gebo.llms.config", name = "googleVertexEnabled", havingValue = "true")
@Service
public class GoogleVertexEmbeddingModelConfigurationSupportService implements
		IGEmbeddingModelConfigurationSupportService<GGoogleVertexEmbeddingModelChoice, GGoogleVertexEmbeddingModelConfig> {
	/**
	 * Static definition of the embedding model type with code and description.
	 */
	static final GEmbeddingModelType type = new GEmbeddingModelType();
	static {
		type.setCode("embedding-google-vertex");
		type.setDescription("Google vertex embedding models");
		type.setModelConfigurationClass(GGoogleVertexEmbeddingModelConfig.class.getName());
	}
	
	/**
	 * List of available Google Vertex embedding model choices.
	 */
	static final List<GGoogleVertexEmbeddingModelChoice> choices = GBaseModelChoice
			.of(GGoogleVertexEmbeddingModelChoice.class, VertexAiTextEmbeddingModelName.values());
	
	/**
	 * Provider for vector store factories.
	 */
	@Autowired
	IGVectorStoreFactoryProvider vectorStoreFactoryProvider;
	
	/**
	 * Utility for configuring Vertex AI connections.
	 */
	@Autowired
	VertexAIConfigurator configurator;

	/**
	 * Inner class that implements a configurable Google Vertex embedding model.
	 */
	class GoogleVertexConfigurableEmbeddingModel
			extends GAbstractConfigurableEmbeddingModel<GGoogleVertexEmbeddingModelConfig, VertexAiTextEmbeddingModel> {

		/**
		 * Constructor for the embedding model.
		 * 
		 * @throws LLMConfigException if there's an issue with the model configuration
		 */
		public GoogleVertexConfigurableEmbeddingModel() throws LLMConfigException {
			super(GoogleVertexEmbeddingModelConfigurationSupportService.this.vectorStoreFactoryProvider);

		}

		/**
		 * Configures the Vertex AI text embedding model based on the provided configuration.
		 * 
		 * @param config the configuration for the model
		 * @param type the embedding model type
		 * @return a configured VertexAiTextEmbeddingModel
		 * @throws LLMConfigException if there's an issue with the model configuration
		 */
		@Override
		protected VertexAiTextEmbeddingModel configureModel(GGoogleVertexEmbeddingModelConfig config,
				GEmbeddingModelType type) throws LLMConfigException {

			org.springframework.ai.vertexai.embedding.text.VertexAiTextEmbeddingOptions.Builder oBuilder = VertexAiTextEmbeddingOptions
					.builder();
			if (config.getChoosedModel() != null) {
				oBuilder = oBuilder.model(config.getChoosedModel().getCode());
			}
			VertexAiEmbeddingConnectionDetails connectionDetails = configurator
					.createVertexAiEmbeddingConnectionDetails(config.getApiSecretCode(), config.getBaseUrl());
			VertexAiTextEmbeddingOptions options = oBuilder.build();
			VertexAiTextEmbeddingModel model = new VertexAiTextEmbeddingModel(connectionDetails, options);
			return model;
		}

	};

	/**
	 * Default constructor.
	 */
	public GoogleVertexEmbeddingModelConfigurationSupportService() {

	}

	/**
	 * Returns the embedding model type for Google Vertex.
	 * 
	 * @return the embedding model type
	 */
	@Override
	public GEmbeddingModelType getType() {

		return type;
	}

	/**
	 * Creates a new configurable embedding model with the given configuration.
	 * 
	 * @param config the configuration for the model
	 * @return a configured embedding model
	 * @throws LLMConfigException if there's an issue with the model configuration
	 */
	@Override
	public IGConfigurableEmbeddingModel<GGoogleVertexEmbeddingModelConfig> create(
			GGoogleVertexEmbeddingModelConfig config) throws LLMConfigException {
		GoogleVertexConfigurableEmbeddingModel model = new GoogleVertexConfigurableEmbeddingModel();
		model.initialize(config, type);
		return model;
	}

	/**
	 * Returns the list of available model choices for Google Vertex embedding.
	 * 
	 * @param config the current configuration
	 * @return operation status containing the list of model choices
	 */
	@Override
	public OperationStatus<List<GGoogleVertexEmbeddingModelChoice>> getModelChoices(
			GGoogleVertexEmbeddingModelConfig config) {

		return OperationStatus.of(choices);
	}

	/**
	 * Creates a base configuration for a Google Vertex embedding model with the specified model name.
	 * 
	 * @param presetModel the name of the model to use
	 * @return a basic configuration for the specified model
	 */
	@Override
	public GGoogleVertexEmbeddingModelConfig createBaseConfiguration(String presetModel) {
		GGoogleVertexEmbeddingModelConfig clean = new GGoogleVertexEmbeddingModelConfig();
		clean.setModelTypeCode(getType().getCode());
		clean.setChoosedModel(new GGoogleVertexEmbeddingModelChoice());
		clean.getChoosedModel().setCode(presetModel);
		clean.getChoosedModel().setDescription("embedding model " + presetModel);
		clean.setDescription("Google vertex  embedding model " + presetModel);
		clean.setModelTypeCode(getType().getCode());
		return clean;
	}

}