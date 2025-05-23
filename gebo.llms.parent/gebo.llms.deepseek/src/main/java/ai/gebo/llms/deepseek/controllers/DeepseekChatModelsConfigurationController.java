/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.deepseek.controllers;

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
import ai.gebo.llms.deepseek.model.GDeepseekChatModelConfig;
import ai.gebo.model.OperationStatus;

/**
 * Controller for managing Deepseek chat model configurations.
 * This controller provides REST endpoints for CRUD operations on Deepseek model configurations.
 * Only enabled when the property ai.gebo.llms.config.deepseekEnabled is set to true.
 * Restricted to users with ADMIN role.
 * 
 * AI generated comments
 */
@ConditionalOnProperty(prefix = "ai.gebo.llms.config", name = "deepseekEnabled", havingValue = "true")
@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("api/admin/DeepseekChatModelsConfigurationController")
public class DeepseekChatModelsConfigurationController extends
		BaseChatModelsConfigurationController<ai.gebo.llms.deepseek.model.GDeepseekChatModelConfig, ai.gebo.llms.deepseek.model.GDeepseekChatModelChoice, ai.gebo.llms.deepseek.services.DeepseekChatModelConfigurationSupportService> {

	/**
	 * Constructor that initializes the controller with the Deepseek chat model configuration class.
	 */
	public DeepseekChatModelsConfigurationController() {
		super(ai.gebo.llms.deepseek.model.GDeepseekChatModelConfig.class);
	}

	/**
	 * Inserts a new Deepseek chat model configuration.
	 * 
	 * @param config The Deepseek chat model configuration to insert
	 * @return An operation status containing the inserted configuration
	 */
	@PostMapping(value = "insertDeepseekChatModelConfig", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<GDeepseekChatModelConfig> insertDeepseekChatModelConfig(
			@RequestBody GDeepseekChatModelConfig config) {
		return super.insert(config);

	}

	/**
	 * Updates an existing Deepseek chat model configuration.
	 * 
	 * @param config The Deepseek chat model configuration to update
	 * @return An operation status containing the updated configuration
	 */
	@PostMapping(value = "updateDeepseekChatModelConfig", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<GDeepseekChatModelConfig> updateDeepseekChatModelConfig(
			@RequestBody GDeepseekChatModelConfig config) {

		return super.update(config);
	}

	/**
	 * Deletes a Deepseek chat model configuration.
	 * 
	 * @param config The Deepseek chat model configuration to delete
	 * @return An operation status indicating whether the deletion was successful
	 */
	@PostMapping(value = "deleteDeepseekChatModelConfig", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<Boolean> deleteDeepseekChatModelConfig(@RequestBody GDeepseekChatModelConfig config) {

		return super.delete(config);
	}

	/**
	 * Finds a Deepseek chat model configuration by its code.
	 * 
	 * @param code The code of the configuration to find
	 * @return The found Deepseek chat model configuration
	 * @throws GeboPersistenceException If there is an error during the database operation
	 */
	@GetMapping(value = "findDeepseekChatModelConfigByCode", produces = MediaType.APPLICATION_JSON_VALUE)
	public GDeepseekChatModelConfig findDeepseekChatModelConfigByCode(@RequestParam("code") String code)
			throws GeboPersistenceException {
		return super.findByCode(code);
	}

	/**
	 * Retrieves all available Deepseek chat models based on the provided configuration.
	 * 
	 * @param config The configuration used to filter available models
	 * @return An operation status containing a list of available Deepseek chat model choices
	 */
	@PostMapping(value = "getDeepseekModels", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<List<ai.gebo.llms.deepseek.model.GDeepseekChatModelChoice>> getDeepseekChatModels(
			@RequestBody ai.gebo.llms.deepseek.model.GDeepseekChatModelConfig config) {
		return super.getModelChoices(config);
	}

}