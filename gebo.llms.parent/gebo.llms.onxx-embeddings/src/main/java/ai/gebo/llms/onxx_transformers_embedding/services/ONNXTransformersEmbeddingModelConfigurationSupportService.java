/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.llms.onxx_transformers_embedding.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.MetadataMode;
import org.springframework.ai.transformers.TransformersEmbeddingModel;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.llms.abstraction.layer.model.GEmbeddingModelType;
import ai.gebo.llms.abstraction.layer.services.GAbstractConfigurableEmbeddingModel;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableEmbeddingModel;
import ai.gebo.llms.abstraction.layer.services.IGEmbeddingModelConfigurationSupportService;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.abstraction.layer.services.ModelRuntimeConfigureHandler;
import ai.gebo.llms.abstraction.layer.vectorstores.IGVectorStoreFactoryProvider;
import ai.gebo.llms.onxx_transformers_embedding.model.GONNXTransformersEmbeddingModelChoice;
import ai.gebo.llms.onxx_transformers_embedding.model.GONNXTransformersEmbeddingModelConfig;
import ai.gebo.model.OperationStatus;
import ai.gebo.secrets.services.IGeboSecretsAccessService;
import io.micrometer.observation.ObservationRegistry;
import jakarta.el.MethodNotFoundException;
import lombok.AllArgsConstructor;

/**
 * AI generated comments Service for configuring and managing ONNX Transformers
 * embedding models. This class provides support for creating and configuring
 * embedding models that use the ONNX Transformers backend.
 */
@Service
@AllArgsConstructor
public class ONNXTransformersEmbeddingModelConfigurationSupportService implements
		IGEmbeddingModelConfigurationSupportService<GONNXTransformersEmbeddingModelChoice, GONNXTransformersEmbeddingModelConfig> {
	/**
	 * Static model type definition for ONNX Transformers embedding models
	 */
	static final GEmbeddingModelType type = new GEmbeddingModelType();
	static {
		type.setCode("embedding-onnx-transformers");
		type.setDescription("embedding service hosted local ONNX Transformers system");
		type.setModelConfigurationClass(GONNXTransformersEmbeddingModelConfig.class.getName());
		type.setProviderId("onnx-transformers");
	}

	/**
	 * Service for looking up available ONNX Transformers models
	 */
	final ONNXTransformersModelsLookupService modelsService;

	/**
	 * Provider for vector store factories
	 */
	final IGVectorStoreFactoryProvider storeFactoryProvider;

	/**
	 * Service for accessing secrets
	 */
	final IGeboSecretsAccessService secretService;
	final ModelRuntimeConfigureHandler configureHandler;
	final ObservationRegistry observationRegistry;

	/**
	 * Inner class that implements the configurable embedding model for ONNX
	 * Transformers
	 */
	class OllamaConfigurableEmbeddingModel extends
			GAbstractConfigurableEmbeddingModel<GONNXTransformersEmbeddingModelConfig, TransformersEmbeddingModel> {
		private final Logger LOGGER = LoggerFactory.getLogger(getClass());

		/**
		 * Constructor that initializes the model with a vector store factory provider
		 */
		public OllamaConfigurableEmbeddingModel() {
			super(storeFactoryProvider,
					ONNXTransformersEmbeddingModelConfigurationSupportService.this.observationRegistry);
		}

		/**
		 * Configures the underlying TransformersEmbeddingModel based on the provided
		 * configuration
		 * 
		 * @param config The ONNX Transformers embedding model configuration
		 * @param type   The embedding model type
		 * @return Configured TransformersEmbeddingModel instance
		 * @throws LLMConfigException If there's an error in configuration
		 */
		@Override
		protected TransformersEmbeddingModel configureModel(GONNXTransformersEmbeddingModelConfig config,
				GEmbeddingModelType type) throws LLMConfigException {

			TransformersEmbeddingModel model = new TransformersEmbeddingModel(MetadataMode.EMBED);
			return model;
		}

		/**
		 * Determines the tokenization threshold: the optimal tokenization parameter of
		 * the chosen model when set, else its context length; the model this provider
		 * runs is the bundled one, so a choice telling neither (saved before the models
		 * library knew it) gets the bundled model's input limit. The
		 * "embedding_length" it used to look for is no input size, and no ONNX choice
		 * carries it.
		 *
		 * @return The tokenization threshold value
		 */
		@Override
		public int getTokenizationThreshold() {
			int threshold = tokenizationThreshold(config.getChoosedModel());
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Embedding model {} tokenization threshold {}", config.getCode(), threshold);
			}
			return threshold;
		}
	};

	/**
	 * The input limit of the bundled all-MiniLM-L6-v2: its model card says "input text
	 * longer than 256 word pieces is truncated".
	 */
	static final int BUNDLED_MODEL_INPUT_LIMIT = 256;

	/** The tokenization threshold a choice tells, the bundled model's input limit otherwise. */
	static int tokenizationThreshold(GONNXTransformersEmbeddingModelChoice choice) {
		if (choice != null) {
			if (choice.getOptimalTokenizationParam() != null)
				return choice.getOptimalTokenizationParam();
			if (choice.getContextLength() != null)
				return choice.getContextLength();
		}
		return BUNDLED_MODEL_INPUT_LIMIT;
	}

	/**
	 * Gets the model type for ONNX Transformers embedding
	 * 
	 * @return The embedding model type
	 */
	@Override
	public GEmbeddingModelType getType() {
		return type;
	}

	/**
	 * Creates a configured embedding model based on the provided configuration
	 * 
	 * @param config The ONNX Transformers embedding model configuration
	 * @return A configured embedding model
	 * @throws LLMConfigException If there's an error in configuration
	 */
	@Override
	public IGConfigurableEmbeddingModel<GONNXTransformersEmbeddingModelConfig> create(
			GONNXTransformersEmbeddingModelConfig config) throws LLMConfigException {
		OllamaConfigurableEmbeddingModel model = new OllamaConfigurableEmbeddingModel();
		model.initialize(config, type);
		return model;
	}

	/**
	 * Gets available model choices based on the configuration
	 * 
	 * @param config The ONNX Transformers embedding model configuration
	 * @return Operation status containing a list of available model choices
	 */
	@Override
	public OperationStatus<List<GONNXTransformersEmbeddingModelChoice>> getModelChoices(
			GONNXTransformersEmbeddingModelConfig config) {
		return modelsService.getEmbeddingModels(config);
	}

	/**
	 * Creates a base configuration for a specified preset model
	 * 
	 * @param presetModel The name of the preset model
	 * @return A base configuration
	 * @throws MethodNotFoundException This method is not implemented for this
	 *                                 provider
	 */
	@Override
	public GONNXTransformersEmbeddingModelConfig createBaseConfiguration(String presetModel) {
		GONNXTransformersEmbeddingModelConfig clean = new GONNXTransformersEmbeddingModelConfig();
		clean.setModelTypeCode(getType().getCode());
		clean.setChoosedModel(new GONNXTransformersEmbeddingModelChoice());
		clean.getChoosedModel().setCode(presetModel);
		clean.getChoosedModel().setDescription("chat model " + presetModel);
		clean.setDescription("ONNX embedding model " + presetModel);
		clean.setModelTypeCode(getType().getCode());
		return clean;
	}

	@Override
	public OperationStatus<GONNXTransformersEmbeddingModelConfig> insertAndConfigure(
			GONNXTransformersEmbeddingModelConfig config) throws GeboPersistenceException, LLMConfigException {

		return configureHandler.insertAndConfigure(config, type);
	}
}