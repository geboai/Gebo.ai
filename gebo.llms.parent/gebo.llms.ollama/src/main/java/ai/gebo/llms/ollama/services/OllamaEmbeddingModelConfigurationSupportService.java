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
 * Service for configuring and creating Ollama embedding models.
 * This service is only active when the property 'ai.gebo.llms.config.ollamaEnabled' is set to true.
 */
package ai.gebo.llms.ollama.services;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.ai.document.MetadataMode;
import org.springframework.ai.ollama.OllamaEmbeddingModel;
import org.springframework.ai.ollama.api.OllamaApi;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.ai.ollama.api.OllamaOptions.Builder;
import org.springframework.ai.ollama.management.ModelManagementOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.llms.abstraction.layer.model.GEmbeddingModelType;
import ai.gebo.llms.abstraction.layer.services.GAbstractConfigurableEmbeddingModel;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableEmbeddingModel;
import ai.gebo.llms.abstraction.layer.services.IGEmbeddingModelConfigurationSupportService;
import ai.gebo.llms.abstraction.layer.services.IGLlmsServiceClientsProvider;
import ai.gebo.llms.abstraction.layer.services.IGLlmsServiceClientsProviderFactory;
import ai.gebo.llms.abstraction.layer.services.IGModelChoiceMetaInfoEnricherService;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.abstraction.layer.services.ModelRuntimeConfigureHandler;
import ai.gebo.llms.abstraction.layer.vectorstores.IGVectorStoreFactoryProvider;
import ai.gebo.llms.models.metainfos.ModelMetaInfo;
import ai.gebo.llms.ollama.model.GOllamaChatModelChoice;
import ai.gebo.llms.ollama.model.GOllamaChatModelConfig;
import ai.gebo.llms.ollama.model.GOllamaEmbeddingModelChoice;
import ai.gebo.llms.ollama.model.GOllamaEmbeddingModelConfig;
import ai.gebo.model.OperationStatus;
import ai.gebo.secrets.services.IGeboSecretsAccessService;
import io.micrometer.observation.ObservationRegistry;
import jakarta.el.MethodNotFoundException;
import lombok.AllArgsConstructor;

@ConditionalOnProperty(prefix = "ai.gebo.llms.config", name = "ollamaEnabled", havingValue = "true")
@Service
@AllArgsConstructor
public class OllamaEmbeddingModelConfigurationSupportService implements
		IGEmbeddingModelConfigurationSupportService<GOllamaEmbeddingModelChoice, GOllamaEmbeddingModelConfig> {
	/**
	 * Static embedding model type information for Ollama models.
	 */
	static final GEmbeddingModelType type = new GEmbeddingModelType();
	static {
		type.setCode("embedding-ollama");
		type.setDescription("embedding service hosted local Ollama server");
		type.setModelConfigurationClass(GOllamaEmbeddingModelConfig.class.getName());
	}

	final OllamaModelsLookupService modelsService;

	final IGVectorStoreFactoryProvider storeFactoryProvider;

	final IGeboSecretsAccessService secretService;

	final IGModelChoiceMetaInfoEnricherService choiceEnricher;

	final IGLlmsServiceClientsProviderFactory serviceClientsProviderFactory;
	final ModelRuntimeConfigureHandler configureHandler;

	/**
	 * Inner class that implements configurable embedding model for Ollama. Handles
	 * the configuration and initialization of Ollama embedding models.
	 */
	class OllamaConfigurableEmbeddingModel
			extends GAbstractConfigurableEmbeddingModel<GOllamaEmbeddingModelConfig, OllamaEmbeddingModel> {

		/**
		 * Constructor for the Ollama configurable embedding model.
		 */
		public OllamaConfigurableEmbeddingModel() {
			super(storeFactoryProvider);
		}

		/**
		 * Configures the Ollama embedding model based on the provided configuration.
		 *
		 * @param config The Ollama-specific embedding model configuration
		 * @param type   The embedding model type information
		 * @return Configured OllamaEmbeddingModel instance
		 * @throws LLMConfigException If configuration fails
		 */
		@Override
		protected OllamaEmbeddingModel configureModel(GOllamaEmbeddingModelConfig config, GEmbeddingModelType type)
				throws LLMConfigException {

			org.springframework.ai.ollama.api.OllamaApi.Builder apiBuilder = OllamaApi.builder();
			apiBuilder.baseUrl(config.getBaseUrl());
			IGLlmsServiceClientsProvider clientsProvider = serviceClientsProviderFactory.get(getCode());
			org.springframework.web.client.RestClient.Builder restClient = clientsProvider.getRestClientBuilder();
			org.springframework.web.reactive.function.client.WebClient.Builder webClient = clientsProvider
					.getWebClientBuilder();
			RetryTemplate retryTemplate = clientsProvider.getRetryTemplate();
			apiBuilder.restClientBuilder(restClient);
			apiBuilder.webClientBuilder(webClient);
			OllamaApi ollamaapi = apiBuilder.build();
			Builder builder = OllamaOptions.builder();

			if (config.getChoosedModel() != null) {
				builder = builder.model(config.getChoosedModel().getCode());
			}

			OllamaOptions options = builder.build();
			MetadataMode meta = MetadataMode.EMBED;
			ObservationRegistry observationRegistry = ObservationRegistry.create();
			ModelManagementOptions modelManagementOptions = ModelManagementOptions.defaults();
			OllamaEmbeddingModel model = new OllamaEmbeddingModel(ollamaapi, options, observationRegistry,
					modelManagementOptions);
			return model;
		}

		/**
		 * Returns the tokenization threshold for the configured model. Uses the optimal
		 * tokenization parameter from the model if available, otherwise tries to find a
		 * parameter ending with "embedding_length". Falls back to a default value of
		 * 512 if no specific value is found.
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
		 * Helper method to find a parameter ending with the specified name in the model
		 * details.
		 *
		 * @param choosedModel  The model choice containing details to search
		 * @param endingKeyName The suffix to match in parameter names
		 * @return The parameter value as Integer, or null if not found
		 */
		private Integer findParamEndingWith(GOllamaEmbeddingModelChoice choosedModel, String endingKeyName) {
			Map<String, Object> details = choosedModel.getModelDetails();
			return findParamEndingWith(details, endingKeyName);
		}

		/**
		 * Recursively searches a map for a key ending with the specified name.
		 *
		 * @param details       The map to search
		 * @param endingKeyName The suffix to match in key names
		 * @return The parameter value as Integer, or null if not found
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
	 * Returns the embedding model type for Ollama.
	 *
	 * @return The Ollama embedding model type
	 */
	@Override
	public GEmbeddingModelType getType() {
		return type;
	}

	/**
	 * Creates and initializes a configurable embedding model with the provided
	 * configuration.
	 *
	 * @param config The Ollama embedding model configuration
	 * @return Configured embedding model instance
	 * @throws LLMConfigException If model creation fails
	 */
	@Override
	public IGConfigurableEmbeddingModel<GOllamaEmbeddingModelConfig> create(GOllamaEmbeddingModelConfig config)
			throws LLMConfigException {
		OllamaConfigurableEmbeddingModel model = new OllamaConfigurableEmbeddingModel();
		model.initialize(config, type);
		return model;
	}

	/**
	 * Retrieves available Ollama embedding model choices and enriches them with
	 * metadata.
	 *
	 * @param config The configuration to use for retrieving model choices
	 * @return Operation status containing the list of available model choices
	 */
	@Override
	public OperationStatus<List<GOllamaEmbeddingModelChoice>> getModelChoices(GOllamaEmbeddingModelConfig config) {
		OperationStatus<List<GOllamaEmbeddingModelChoice>> rv = modelsService.getEmbeddingModels(config);
		if (rv.getResult() != null)
			this.choiceEnricher.enrichEmbeddingModelMetaInfos("ollama", rv.getResult(),
					(choice) -> new ModelMetaInfo());
		return rv;
	}

	/**
	 * Not implemented for Ollama embedding provider.
	 *
	 * @param presetModel The preset model name
	 * @return Never returns as this method throws an exception
	 * @throws MethodNotFoundException Always thrown as this method is not
	 *                                 implemented
	 */
	@Override
	public GOllamaEmbeddingModelConfig createBaseConfiguration(String presetModel) {
		GOllamaEmbeddingModelConfig clean = new GOllamaEmbeddingModelConfig();
		clean.setModelTypeCode(getType().getCode());
		clean.setChoosedModel(new GOllamaEmbeddingModelChoice());
		clean.getChoosedModel().setCode(presetModel);
		clean.getChoosedModel().setDescription("embedding model " + presetModel);
		clean.setDescription("Ollama embedding model " + presetModel);
		clean.setModelTypeCode(getType().getCode());
		return clean;
	}

	@Override
	public OperationStatus<GOllamaEmbeddingModelConfig> insertAndConfigure(GOllamaEmbeddingModelConfig config)
			throws GeboPersistenceException, LLMConfigException {

		return configureHandler.insertAndConfigure(config, type);
	}
}