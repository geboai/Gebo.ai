/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.anthropic.controllers;

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
import ai.gebo.llms.anthropic.model.GAnthropicChatModelChoice;
import ai.gebo.llms.anthropic.model.GAnthropicChatModelConfig;
import ai.gebo.llms.anthropic.services.AnthropicChatModelConfigurationSupportService;
import ai.gebo.model.OperationStatus;

/**
 * AI generated comments
 * 
 * Controller responsible for managing Anthropic chat model configurations.
 * This REST controller provides endpoints for CRUD operations on Anthropic chat model configurations.
 * It is only enabled when the property 'ai.gebo.llms.config.anthropicEnabled' is set to 'true'.
 * Access to these endpoints is restricted to users with ADMIN role.
 */
@ConditionalOnProperty(prefix = "ai.gebo.llms.config", name = "anthropicEnabled", havingValue = "true")
@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("api/admin/AnthropicChatModelsConfigurationController")
public class AnthropicChatModelsConfigurationController extends
		BaseChatModelsConfigurationController<GAnthropicChatModelConfig, GAnthropicChatModelChoice, AnthropicChatModelConfigurationSupportService> {
	
	/**
	 * Constructor for AnthropicChatModelsConfigurationController.
	 * Initializes the controller with GAnthropicChatModelConfig class.
	 */
	public AnthropicChatModelsConfigurationController() {
		super(GAnthropicChatModelConfig.class);
	}

	/**
	 * Endpoint to insert a new Anthropic chat model configuration.
	 * 
	 * @param config The Anthropic chat model configuration to insert
	 * @return Operation status with the inserted configuration
	 */
	@PostMapping(value = "insertAnthropicChatModelConfig", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<GAnthropicChatModelConfig> insertAnthropicChatModelConfig(
			@RequestBody GAnthropicChatModelConfig config) {
		return super.insert(config);

	}

	/**
	 * Endpoint to update an existing Anthropic chat model configuration.
	 * 
	 * @param config The Anthropic chat model configuration to update
	 * @return Operation status with the updated configuration
	 */
	@PostMapping(value = "updateAnthropicChatModelConfig", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<GAnthropicChatModelConfig> updateAnthropicChatModelConfig(
			@RequestBody GAnthropicChatModelConfig config) {

		return super.update(config);
	}

	/**
	 * Endpoint to delete an Anthropic chat model configuration.
	 * 
	 * @param config The Anthropic chat model configuration to delete
	 * @return Operation status indicating success or failure of the deletion
	 */
	@PostMapping(value = "deleteAnthropicChatModelConfig", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<Boolean> deleteAnthropicChatModelConfig(@RequestBody GAnthropicChatModelConfig config) {

		return super.delete(config);
	}

	/**
	 * Endpoint to find an Anthropic chat model configuration by its code.
	 * 
	 * @param code The code of the configuration to find
	 * @return The matching Anthropic chat model configuration
	 * @throws GeboPersistenceException If there's an error retrieving the configuration
	 */
	@GetMapping(value = "findAnthropicChatModelConfigByCode", produces = MediaType.APPLICATION_JSON_VALUE)
	public GAnthropicChatModelConfig findAnthropicChatModelConfigByCode(@RequestParam("code") String code)
			throws GeboPersistenceException {
		return super.findByCode(code);
	}

	/**
	 * Endpoint to retrieve available Anthropic chat models based on a configuration.
	 * 
	 * @param config The configuration to use for retrieving available models
	 * @return Operation status with a list of available Anthropic chat model choices
	 */
	@PostMapping(value = "getAnthropicModels", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<List<GAnthropicChatModelChoice>> getAnthropicChatModels(@RequestBody GAnthropicChatModelConfig config) {
		return super.getModelChoices(config);
	}
	
}