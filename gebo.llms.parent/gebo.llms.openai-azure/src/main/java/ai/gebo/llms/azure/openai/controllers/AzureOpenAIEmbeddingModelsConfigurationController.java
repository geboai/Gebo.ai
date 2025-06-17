/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.azure.openai.controllers;

import java.util.List;

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
import ai.gebo.llms.abstraction.layer.controllers.BaseEmbeddingModelsConfigurationController;
import ai.gebo.llms.azure.openai.model.GAzureOpenAIEmbeddingModelChoice;
import ai.gebo.llms.azure.openai.model.GAzureOpenAIEmbeddingModelConfig;
import ai.gebo.llms.azure.openai.services.AzureOpenAIEmbeddingModelConfigurationSupportService;
import ai.gebo.model.OperationStatus;

/**
 * AI generated comments
 * 
 * REST controller for managing OpenAI embedding model configurations.
 * This controller provides endpoints for CRUD operations on OpenAI embedding model configurations
 * and retrieval of available embedding models. It is only enabled when the OpenAI integration is
 * configured to be active in the application properties.
 * Access to these endpoints is restricted to users with the ADMIN role.
 */
@ConditionalOnProperty(prefix = "ai.gebo.llms.config", name = "azureOpenAIEnabled", havingValue = "true")
@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("api/admin/AzureOpenAIEmbeddingModelsConfigurationController")
public class AzureOpenAIEmbeddingModelsConfigurationController extends
		BaseEmbeddingModelsConfigurationController<GAzureOpenAIEmbeddingModelConfig, GAzureOpenAIEmbeddingModelChoice, AzureOpenAIEmbeddingModelConfigurationSupportService> {

	/**
	 * Constructor for the OpenAI embedding models configuration controller.
	 * Initializes the controller with the OpenAI embedding model configuration class.
	 */
	public AzureOpenAIEmbeddingModelsConfigurationController() {
		super(GAzureOpenAIEmbeddingModelConfig.class);
	}

	/**
	 * Inserts a new OpenAI embedding model configuration.
	 * 
	 * @param config The OpenAI embedding model configuration to insert
	 * @return Operation status containing the inserted configuration or error details
	 */
	@PostMapping(value = "insertOpenAIEmbeddingModelConfig", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<GAzureOpenAIEmbeddingModelConfig> insertOpenAIEmbeddingModelConfig(
			@RequestBody GAzureOpenAIEmbeddingModelConfig config) {
		return super.insert(config);

	}

	/**
	 * Updates an existing OpenAI embedding model configuration.
	 * 
	 * @param config The OpenAI embedding model configuration to update
	 * @return Operation status containing the updated configuration or error details
	 */
	@PostMapping(value = "updateOpenAIEmbeddingModelConfig", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<GAzureOpenAIEmbeddingModelConfig> updateOpenAIEmbeddingModelConfig(
			@RequestBody GAzureOpenAIEmbeddingModelConfig config) {

		return super.update(config);
	}

	/**
	 * Deletes an OpenAI embedding model configuration.
	 * 
	 * @param config The OpenAI embedding model configuration to delete
	 * @return Operation status indicating success or failure of the deletion
	 */
	@PostMapping(value = "deleteOpenAIEmbeddingModelConfig", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<Boolean> deleteOpenAIEmbeddingModelConfig(@RequestBody GAzureOpenAIEmbeddingModelConfig config) {

		return super.delete(config);
	}

	/**
	 * Finds an OpenAI embedding model configuration by its code.
	 * 
	 * @param code The unique code identifying the configuration
	 * @return The matching OpenAI embedding model configuration
	 * @throws GeboPersistenceException If the configuration cannot be found or retrieved
	 */
	@GetMapping(value = "findOpenAIEmbeddingModelConfigByCode", produces = MediaType.APPLICATION_JSON_VALUE)
	public GAzureOpenAIEmbeddingModelConfig findOpenAIEmbeddingModelConfigByCode(@RequestParam("code") String code)
			throws GeboPersistenceException {
		return super.findByCode(code);
	}

	/**
	 * Retrieves available OpenAI embedding models based on the provided configuration.
	 * 
	 * @param config The configuration containing necessary information to retrieve models
	 * @return Operation status containing the list of available embedding model choices
	 */
	@PostMapping(value = "getOpenAIEmbeddingModels", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<List<GAzureOpenAIEmbeddingModelChoice>> getOpenAIEmbeddingModels(
			@RequestBody GAzureOpenAIEmbeddingModelConfig config) {
		return super.getModelChoices(config);
	}
}