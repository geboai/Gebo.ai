/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.openai.controllers;

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
import ai.gebo.llms.openai.model.GOpenAIChatModelChoice;
import ai.gebo.llms.openai.model.GOpenAIChatModelConfig;
import ai.gebo.llms.openai.services.OpenAIChatModelConfigurationSupportService;
import ai.gebo.model.OperationStatus;

/**
 * AI generated comments
 * 
 * Controller for managing OpenAI chat model configurations.
 * Provides REST endpoints to insert, update, delete, and query OpenAI chat model configurations.
 * This controller is only active when the 'openAIEnabled' property is set to true.
 * Access is restricted to users with ADMIN role.
 */
@ConditionalOnProperty(prefix = "ai.gebo.llms.config", name = "openAIEnabled", havingValue = "true")
@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("api/admin/OpenAIModelsConfigurationController")
public class OpenAIChatModelsConfigurationController extends
		BaseChatModelsConfigurationController<GOpenAIChatModelConfig, GOpenAIChatModelChoice, OpenAIChatModelConfigurationSupportService> {

	/**
	 * Constructor for OpenAIChatModelsConfigurationController.
	 * Initializes the parent controller with GOpenAIChatModelConfig class.
	 */
	public OpenAIChatModelsConfigurationController() {
		super(GOpenAIChatModelConfig.class);
	}

	/**
	 * Inserts a new OpenAI chat model configuration.
	 * 
	 * @param config The OpenAI chat model configuration to insert
	 * @return Operation status containing the inserted configuration
	 */
	@PostMapping(value = "insertOpenAIChatModelConfig", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<GOpenAIChatModelConfig> insertOpenAIChatModelConfig(
			@RequestBody GOpenAIChatModelConfig config) {
		return super.insert(config);

	}

	/**
	 * Updates an existing OpenAI chat model configuration.
	 * 
	 * @param config The OpenAI chat model configuration to update
	 * @return Operation status containing the updated configuration
	 */
	@PostMapping(value = "updateOpenAIChatModelConfig", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<GOpenAIChatModelConfig> updateOpenAIChatModelConfig(
			@RequestBody GOpenAIChatModelConfig config) {

		return super.update(config);
	}

	/**
	 * Deletes an OpenAI chat model configuration.
	 * 
	 * @param config The OpenAI chat model configuration to delete
	 * @return Operation status indicating success or failure
	 */
	@PostMapping(value = "deleteOpenAIChatModelConfig", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<Boolean> deleteOpenAIChatModelConfig(@RequestBody GOpenAIChatModelConfig config) {

		return super.delete(config);
	}

	/**
	 * Finds an OpenAI chat model configuration by its code.
	 * 
	 * @param code The code of the configuration to find
	 * @return The found OpenAI chat model configuration
	 * @throws GeboPersistenceException If there is an error retrieving the configuration
	 */
	@GetMapping(value = "findOpenAIChatModelConfigByCode", produces = MediaType.APPLICATION_JSON_VALUE)
	public GOpenAIChatModelConfig findOpenAIChatModelConfigByCode(@RequestParam("code") String code)
			throws GeboPersistenceException {
		return super.findByCode(code);
	}
	
	/**
	 * Retrieves a list of available OpenAI chat models based on the provided configuration.
	 * 
	 * @param config The configuration to use for retrieving models
	 * @return Operation status containing the list of available model choices
	 */
	@PostMapping(value = "getOpenAIChatModels", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<List<GOpenAIChatModelChoice>> getOpenAIChatModels(@RequestBody GOpenAIChatModelConfig config) {
		return super.getModelChoices(config);
	}
}