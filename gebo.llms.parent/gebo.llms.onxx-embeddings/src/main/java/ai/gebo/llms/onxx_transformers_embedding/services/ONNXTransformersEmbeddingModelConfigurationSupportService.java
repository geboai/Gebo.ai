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
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.ai.document.MetadataMode;
import org.springframework.ai.transformers.TransformersEmbeddingModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.gebo.llms.abstraction.layer.model.GEmbeddingModelType;
import ai.gebo.llms.abstraction.layer.services.GAbstractConfigurableEmbeddingModel;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableEmbeddingModel;
import ai.gebo.llms.abstraction.layer.services.IGEmbeddingModelConfigurationSupportService;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.abstraction.layer.vectorstores.IGVectorStoreFactory;
import ai.gebo.llms.abstraction.layer.vectorstores.IGVectorStoreFactoryProvider;
import ai.gebo.llms.onxx_transformers_embedding.model.GONNXTransformersEmbeddingModelChoice;
import ai.gebo.llms.onxx_transformers_embedding.model.GONNXTransformersEmbeddingModelConfig;
import ai.gebo.model.OperationStatus;
import ai.gebo.secrets.services.IGeboSecretsAccessService;
import jakarta.el.MethodNotFoundException;

/**
 * AI generated comments
 * Service for configuring and managing ONNX Transformers embedding models.
 * This class provides support for creating and configuring embedding models
 * that use the ONNX Transformers backend.
 */
@Service
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
	}
	
	/**
	 * Service for looking up available ONNX Transformers models
	 */
	@Autowired
	ONNXTransformersModelsLookupService modelsService;
	
	/**
	 * Provider for vector store factories
	 */
	@Autowired
	IGVectorStoreFactoryProvider storeFactoryProvider;
	
	/**
	 * Service for accessing secrets
	 */
	@Autowired
	IGeboSecretsAccessService secretService;

	/**
	 * Inner class that implements the configurable embedding model for ONNX Transformers
	 */
	class OllamaConfigurableEmbeddingModel extends
			GAbstractConfigurableEmbeddingModel<GONNXTransformersEmbeddingModelConfig, TransformersEmbeddingModel> {

		/**
		 * Constructor that initializes the model with a vector store factory provider
		 */
		public OllamaConfigurableEmbeddingModel() {
			super(storeFactoryProvider);
		}

		/**
		 * Configures the underlying TransformersEmbeddingModel based on the provided configuration
		 * 
		 * @param config The ONNX Transformers embedding model configuration
		 * @param type The embedding model type
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
		 * Determines the tokenization threshold based on the model configuration
		 * 
		 * @return The tokenization threshold value
		 */
		@Override
		public int getTokenizationThreshold() {
			int defaultValue = 512;
			if (config.getChoosedModel() != null) {
				if (config.getChoosedModel().getOptimalTokenizationParam() != null) {
					defaultValue = config.getChoosedModel().getOptimalTokenizationParam();
				} else {
					Integer value = findParamEndingWith(config.getChoosedModel(), "embedding_length");
					if (value != null) {
						defaultValue = value;
					}
				}
			}
			return defaultValue;
		}

		/**
		 * Finds a parameter in the model details where the key ends with the specified name
		 * 
		 * @param choosedModel The chosen model configuration
		 * @param endingKeyName The ending of the key to look for
		 * @return The parameter value as an Integer, or null if not found
		 */
		private Integer findParamEndingWith(GONNXTransformersEmbeddingModelChoice choosedModel, String endingKeyName) {
			Map<String, Object> details = choosedModel.getModelDetails();
			return findParamEndingWith(details, endingKeyName);
		}

		/**
		 * Recursively searches a map for a key ending with the specified string
		 * 
		 * @param details The map to search
		 * @param endingKeyName The ending of the key to look for
		 * @return The parameter value as an Integer, or null if not found
		 */
		private Integer findParamEndingWith(Map<String, Object> details, String endingKeyName) {
			Set<Entry<String, Object>> set = details.entrySet();
			for (Entry<String, Object> entry : set) {
				String key = entry.getKey();
				if (key.toLowerCase().endsWith(endingKeyName.toLowerCase())) {
					Object value = entry.getValue();
					if (value instanceof Number) {
						return new Integer(((Number) value).intValue());
					}
				}
				if (entry.getValue() instanceof Map) {
					return findParamEndingWith(((Map) entry.getValue()), endingKeyName);
				}
			}
			return null;
		}
	};

	/**
	 * Default constructor
	 */
	public ONNXTransformersEmbeddingModelConfigurationSupportService() {
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
	 * @throws MethodNotFoundException This method is not implemented for this provider
	 */
	@Override
	public GONNXTransformersEmbeddingModelConfig createBaseConfiguration(String presetModel) {
		throw new MethodNotFoundException("createBaseConfiguration() is not implemented for ollama embedding provider");
	}
}