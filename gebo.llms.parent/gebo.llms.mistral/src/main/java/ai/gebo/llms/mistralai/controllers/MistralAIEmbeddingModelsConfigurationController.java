/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.mistralai.controllers;

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
import ai.gebo.llms.mistralai.model.GMistralEmbeddingModelChoice;
import ai.gebo.llms.mistralai.model.GMistralEmbeddingModelConfig;
import ai.gebo.llms.mistralai.services.MistralEmbeddingModelConfigurationSupportService;
import ai.gebo.model.OperationStatus;

/**
 * AI generated comments
 * Controller responsible for managing MistralAI embedding model configurations.
 * This controller provides endpoints for CRUD operations on MistralAI embedding model configurations.
 * It is only activated when the MistralAI integration is enabled in the application properties.
 * Access to these endpoints is restricted to users with the ADMIN role.
 */
@ConditionalOnProperty(prefix = "ai.gebo.llms.config", name = "mistralAIEnabled", havingValue = "true")
@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("api/admin/MistralAIEmbeddingModelsConfigurationController")
public class MistralAIEmbeddingModelsConfigurationController extends
		BaseEmbeddingModelsConfigurationController<GMistralEmbeddingModelConfig, GMistralEmbeddingModelChoice, MistralEmbeddingModelConfigurationSupportService> {

	/**
	 * Constructor that initializes the controller with GMistralEmbeddingModelConfig class.
	 */
	public MistralAIEmbeddingModelsConfigurationController() {
		super(GMistralEmbeddingModelConfig.class);
	}

	/**
	 * Endpoint to insert a new MistralAI embedding model configuration.
	 * 
	 * @param config The MistralAI embedding model configuration to insert
	 * @return An OperationStatus containing the result of the insertion operation
	 */
	@PostMapping(value = "insertMistralAIEmbeddingModelConfig", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<GMistralEmbeddingModelConfig> insertMistralAIEmbeddingModelConfig(
			@RequestBody GMistralEmbeddingModelConfig config) {
		return super.insert(config);

	}

	/**
	 * Endpoint to update an existing MistralAI embedding model configuration.
	 * 
	 * @param config The MistralAI embedding model configuration to update
	 * @return An OperationStatus containing the result of the update operation
	 */
	@PostMapping(value = "updateMistralAIEmbeddingModelConfig", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<GMistralEmbeddingModelConfig> updateMistralAIEmbeddingModelConfig(
			@RequestBody GMistralEmbeddingModelConfig config) {

		return super.update(config);
	}

	/**
	 * Endpoint to delete a MistralAI embedding model configuration.
	 * 
	 * @param config The MistralAI embedding model configuration to delete
	 * @return An OperationStatus containing a Boolean indicating success or failure
	 */
	@PostMapping(value = "deleteMistralAIEmbeddingModelConfig", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<Boolean> deleteMistralAIEmbeddingModelConfig(@RequestBody GMistralEmbeddingModelConfig config) {

		return super.delete(config);
	}

	/**
	 * Endpoint to find a MistralAI embedding model configuration by its code.
	 * 
	 * @param code The code of the configuration to find
	 * @return The matching MistralAI embedding model configuration
	 * @throws GeboPersistenceException If the configuration cannot be found or another persistence error occurs
	 */
	@GetMapping(value = "findMistralAIEmbeddingModelConfigByCode", produces = MediaType.APPLICATION_JSON_VALUE)
	public GMistralEmbeddingModelConfig findMistralAIEmbeddingModelConfigByCode(@RequestParam("code") String code)
			throws GeboPersistenceException {
		return super.findByCode(code);
	}

	/**
	 * Endpoint to retrieve available MistralAI embedding models based on a configuration.
	 * 
	 * @param config The configuration used to filter available embedding models
	 * @return An OperationStatus containing a list of available MistralAI embedding model choices
	 */
	@PostMapping(value = "getMistralAIEmbeddingModels", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<List<GMistralEmbeddingModelChoice>> getMistralAIEmbeddingModels(
			@RequestBody GMistralEmbeddingModelConfig config) {
		return super.getModelChoices(config);
	}
}