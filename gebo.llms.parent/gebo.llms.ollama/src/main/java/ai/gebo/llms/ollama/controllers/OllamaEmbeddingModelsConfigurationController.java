/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.ollama.controllers;

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
import ai.gebo.llms.ollama.model.GOllamaEmbeddingModelChoice;
import ai.gebo.llms.ollama.model.GOllamaEmbeddingModelConfig;
import ai.gebo.llms.ollama.services.OllamaEmbeddingModelConfigurationSupportService;
import ai.gebo.model.OperationStatus;

/**
 * AI generated comments
 * Controller for managing Ollama embedding model configurations.
 * This controller provides REST endpoints for CRUD operations on Ollama embedding model configurations.
 * It is only activated when the Ollama integration is enabled in the application properties.
 * Access to these endpoints is restricted to users with the ADMIN role.
 */
@ConditionalOnProperty(prefix = "ai.gebo.llms.config", name = "ollamaEnabled", havingValue = "true")
@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("api/admin/OllamaEmbeddingModelsConfigurationController")
public class OllamaEmbeddingModelsConfigurationController extends
		BaseEmbeddingModelsConfigurationController<GOllamaEmbeddingModelConfig, GOllamaEmbeddingModelChoice, OllamaEmbeddingModelConfigurationSupportService> {

    /**
     * Constructor for OllamaEmbeddingModelsConfigurationController.
     * Initializes the controller with the GOllamaEmbeddingModelConfig class.
     */
	public OllamaEmbeddingModelsConfigurationController() {
		super(GOllamaEmbeddingModelConfig.class);
	}

    /**
     * Inserts a new Ollama embedding model configuration.
     * 
     * @param config The Ollama embedding model configuration to insert
     * @return OperationStatus containing the result of the operation and the inserted configuration
     */
	@PostMapping(value = "insertOllamaEmbeddingModelConfig", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<GOllamaEmbeddingModelConfig> insertOllamaEmbeddingModelConfig(
			@RequestBody GOllamaEmbeddingModelConfig config) {
		return super.insert(config);

	}

    /**
     * Updates an existing Ollama embedding model configuration.
     * 
     * @param config The Ollama embedding model configuration to update
     * @return OperationStatus containing the result of the operation and the updated configuration
     */
	@PostMapping(value = "updateOllamaEmbeddingModelConfig", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<GOllamaEmbeddingModelConfig> updateOllamaEmbeddingModelConfig(
			@RequestBody GOllamaEmbeddingModelConfig config) {

		return super.update(config);
	}

    /**
     * Deletes an Ollama embedding model configuration.
     * 
     * @param config The Ollama embedding model configuration to delete
     * @return OperationStatus containing the result of the operation
     */
	@PostMapping(value = "deleteOllamaEmbeddingModelConfig", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<Boolean> deleteOllamaEmbeddingModelConfig(@RequestBody GOllamaEmbeddingModelConfig config) {

		return super.delete(config);
	}

    /**
     * Finds an Ollama embedding model configuration by its code.
     * 
     * @param code The code of the Ollama embedding model configuration to find
     * @return The found Ollama embedding model configuration
     * @throws GeboPersistenceException If there is an error retrieving the configuration
     */
	@GetMapping(value = "findOllamaEmbeddingModelConfigByCode", produces = MediaType.APPLICATION_JSON_VALUE)
	public GOllamaEmbeddingModelConfig findOllamaEmbeddingModelConfigByCode(@RequestParam("code") String code)
			throws GeboPersistenceException {
		return super.findByCode(code);
	}

    /**
     * Retrieves a list of available Ollama embedding models based on the provided configuration.
     * 
     * @param config The configuration used to filter available models
     * @return OperationStatus containing the list of available Ollama embedding model choices
     */
	@PostMapping(value = "getOllamaEmbeddingModels", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<List<GOllamaEmbeddingModelChoice>> getOllamaEmbeddingModels(
			@RequestBody GOllamaEmbeddingModelConfig config) {
		return super.getModelChoices(config);
	}
}