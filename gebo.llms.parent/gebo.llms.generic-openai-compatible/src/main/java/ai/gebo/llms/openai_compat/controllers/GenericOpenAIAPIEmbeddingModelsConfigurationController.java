/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.openai_compat.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.llms.abstraction.layer.controllers.AbstractBaseEmbeddingModelsConfigurationCRUDController;
import ai.gebo.llms.abstraction.layer.controllers.BaseEmbeddingModelsConfigurationController;
import ai.gebo.llms.abstraction.layer.services.IGEmbeddingModelConfigurationSupportService;
import ai.gebo.llms.abstraction.layer.services.IGEmbeddingModelConfigurationSupportServiceRepositoryPattern;
import ai.gebo.llms.openai_compat.config.GenericOpenAICompatibleProvidersConfig;
import ai.gebo.llms.openai_compat.model.GenericOpenAIAPIEmbeddingModelChoice;
import ai.gebo.llms.openai_compat.model.GenericOpenAIAPIEmbeddingModelConfig;
import ai.gebo.llms.openai_compat.model.GenericOpenAIChatModelTypeConfig;
import ai.gebo.llms.openai_compat.model.GenericOpenAIEmbeddingModelTypeConfig;
import ai.gebo.llms.openai_compat.services.GenericOpenAIAPIEmbeddingModelConfigurationSupportService;
import ai.gebo.model.OperationStatus;

/**
 * AI generated comments
 * 
 * Controller class responsible for managing configurations for OpenAI-compatible embedding models.
 * This controller provides endpoints for CRUD operations on embedding model configurations
 * and is accessible only to users with ADMIN role.
 */
@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("api/admin/GenericOpenAIAPIEmbeddingModelsConfigurationController")
public class GenericOpenAIAPIEmbeddingModelsConfigurationController extends
		AbstractBaseEmbeddingModelsConfigurationCRUDController<GenericOpenAIAPIEmbeddingModelConfig, GenericOpenAIAPIEmbeddingModelChoice> {
	
	/**
	 * Configuration for OpenAI-compatible providers
	 */
	@Autowired
	GenericOpenAICompatibleProvidersConfig config;
	
	/**
	 * Service for embedding model configuration support
	 */
	@Autowired
	IGEmbeddingModelConfigurationSupportServiceRepositoryPattern embeddingProviders;

	/**
	 * Constructor initializing the parent class with the embedding model configuration type
	 */
	public GenericOpenAIAPIEmbeddingModelsConfigurationController() {
		super(GenericOpenAIAPIEmbeddingModelConfig.class);
	}

	/**
	 * Retrieves all available OpenAI embedding model types
	 * 
	 * @return List of embedding model type configurations
	 */
	@GetMapping(value = "getGenericOpenAIEmbeddingModelTypes", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<GenericOpenAIEmbeddingModelTypeConfig> getGenericOpenAIEmbeddingModelTypes() {
		return config.getEmbeddingModelProviders();
	}

	/**
	 * Creates a new embedding model configuration
	 * 
	 * @param config The embedding model configuration to insert
	 * @return Operation status with the inserted configuration
	 */
	@PostMapping(value = "insertGenericOpenAIAPIEmbeddingModelConfig", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<GenericOpenAIAPIEmbeddingModelConfig> insertGenericOpenAIAPIEmbeddingModelConfig(
			@RequestBody GenericOpenAIAPIEmbeddingModelConfig config) {
		return super.insert(config);

	}

	/**
	 * Updates an existing embedding model configuration
	 * 
	 * @param config The embedding model configuration to update
	 * @return Operation status with the updated configuration
	 */
	@PostMapping(value = "updateGenericOpenAIAPIEmbeddingModelConfig", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<GenericOpenAIAPIEmbeddingModelConfig> updateGenericOpenAIAPIEmbeddingModelConfig(
			@RequestBody GenericOpenAIAPIEmbeddingModelConfig config) {

		return super.update(config);
	}

	/**
	 * Deletes an embedding model configuration
	 * 
	 * @param config The embedding model configuration to delete
	 * @return Operation status indicating success or failure
	 */
	@PostMapping(value = "deleteGenericOpenAIAPIEmbeddingModelConfig", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<Boolean> deleteGenericOpenAIAPIEmbeddingModelConfig(
			@RequestBody GenericOpenAIAPIEmbeddingModelConfig config) {

		return super.delete(config);
	}

	/**
	 * Finds an embedding model configuration by its code
	 * 
	 * @param code The unique code of the embedding model configuration
	 * @return The found embedding model configuration
	 * @throws GeboPersistenceException If there is an error retrieving the configuration
	 */
	@GetMapping(value = "findGenericOpenAIAPIEmbeddingModelConfigByCode", produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericOpenAIAPIEmbeddingModelConfig findGenericOpenAIAPIEmbeddingModelConfigByCode(
			@RequestParam("code") String code) throws GeboPersistenceException {
		return super.findByCode(code);
	}

	/**
	 * Retrieves available embedding models for a specific configuration
	 * 
	 * @param config The embedding model configuration to query models for
	 * @return Operation status with a list of available model choices
	 */
	@PostMapping(value = "getGenericOpenAIAPIEmbeddingModels", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<List<GenericOpenAIAPIEmbeddingModelChoice>> getGenericOpenAIAPIEmbeddingModels(
			@RequestBody GenericOpenAIAPIEmbeddingModelConfig config) {
		return getModelChoices(config);
	}

	/**
	 * Retrieves model choices for a given configuration
	 * 
	 * @param type The embedding model configuration to get choices for
	 * @return Operation status with a list of model choices
	 * @throws RuntimeException If the model type code is null or no provider is found for the model type
	 */
	@Override
	protected OperationStatus<List<GenericOpenAIAPIEmbeddingModelChoice>> getModelChoices(
			GenericOpenAIAPIEmbeddingModelConfig type) {
		if (type.getModelTypeCode() == null)
			throw new RuntimeException("modelTypeCode cannot be null");
		IGEmbeddingModelConfigurationSupportService handler = embeddingProviders.findByCode(type.getModelTypeCode());
		if (handler == null)
			throw new RuntimeException(
					"modelTypeCode=>" + type.getModelTypeCode() + " with no corresponding model provider");

		return handler.getModelChoices(type);
	}
}