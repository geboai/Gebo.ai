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
import ai.gebo.llms.abstraction.layer.controllers.BaseChatModelsConfigurationController;
import ai.gebo.llms.ollama.model.GOllamaChatModelChoice;
import ai.gebo.llms.ollama.model.GOllamaChatModelConfig;
import ai.gebo.llms.ollama.services.OllamaChatModelConfigurationSupportService;
import ai.gebo.model.OperationStatus;

/**
 * REST controller that provides endpoints for managing Ollama chat model configurations.
 * This controller is only active when the 'ollamaEnabled' property is set to 'true'.
 * Access to these endpoints is restricted to users with ADMIN role.
 * 
 * AI generated comments
 */
@ConditionalOnProperty(prefix = "ai.gebo.llms.config", name = "ollamaEnabled", havingValue = "true")
@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("api/admin/OllamaChatModelsConfigurationController")
public class OllamaChatModelsConfigurationController extends
		BaseChatModelsConfigurationController<GOllamaChatModelConfig, GOllamaChatModelChoice, OllamaChatModelConfigurationSupportService> {
	
	/**
	 * Constructor that initializes the controller with the Ollama chat model configuration class.
	 */
	public OllamaChatModelsConfigurationController() {
		super(GOllamaChatModelConfig.class);
	}

	/**
	 * Endpoint to insert a new Ollama chat model configuration.
	 * 
	 * @param config The Ollama chat model configuration to insert
	 * @return OperationStatus containing the result of the insertion operation
	 */
	@PostMapping(value = "insertOllamaChatModelConfig", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<GOllamaChatModelConfig> insertOllamaChatModelConfig(
			@RequestBody GOllamaChatModelConfig config) {
		return super.insert(config);

	}

	/**
	 * Endpoint to update an existing Ollama chat model configuration.
	 * 
	 * @param config The Ollama chat model configuration to update
	 * @return OperationStatus containing the result of the update operation
	 */
	@PostMapping(value = "updateOllamaChatModelConfig", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<GOllamaChatModelConfig> updateOllamaChatModelConfig(
			@RequestBody GOllamaChatModelConfig config) {

		return super.update(config);
	}

	/**
	 * Endpoint to delete an Ollama chat model configuration.
	 * 
	 * @param config The Ollama chat model configuration to delete
	 * @return OperationStatus with Boolean value indicating success or failure
	 */
	@PostMapping(value = "deleteOllamaChatModelConfig", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<Boolean> deleteOllamaChatModelConfig(@RequestBody GOllamaChatModelConfig config) {

		return super.delete(config);
	}

	/**
	 * Endpoint to find an Ollama chat model configuration by its code.
	 * 
	 * @param code The code to search for
	 * @return The matching Ollama chat model configuration
	 * @throws GeboPersistenceException If an error occurs during the retrieval process
	 */
	@GetMapping(value = "findOllamaChatModelConfigByCode", produces = MediaType.APPLICATION_JSON_VALUE)
	public GOllamaChatModelConfig findOllamaChatModelConfigByCode(@RequestParam("code") String code)
			throws GeboPersistenceException {
		return super.findByCode(code);
	}

	/**
	 * Endpoint to retrieve all available Ollama chat models based on the provided configuration.
	 * 
	 * @param config The configuration used to filter available models
	 * @return OperationStatus containing a list of available Ollama chat model choices
	 */
	@PostMapping(value = "getOllamaModels", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<List<GOllamaChatModelChoice>> getOllamaChatModels(@RequestBody GOllamaChatModelConfig config) {
		return super.getModelChoices(config);
	}
	
}