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
import ai.gebo.llms.abstraction.layer.controllers.BaseChatModelsConfigurationController;
import ai.gebo.llms.mistralai.model.GMistralChatModelChoice;
import ai.gebo.llms.mistralai.model.GMistralChatModelConfig;
import ai.gebo.llms.mistralai.services.MistralChatModelConfigurationSupportService;
import ai.gebo.model.OperationStatus;

/**
 * AI generated comments
 * 
 * REST controller for managing MistralAI chat model configurations.
 * This controller is only enabled when the 'mistralAIEnabled' property is set to true.
 * It requires admin privileges to access its endpoints.
 */
@ConditionalOnProperty(prefix = "ai.gebo.llms.config", name = "mistralAIEnabled", havingValue = "true")
@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("api/admin/MistralAIChatModelsConfigurationController")
public class MistralAIChatModelsConfigurationController extends
		BaseChatModelsConfigurationController<GMistralChatModelConfig, GMistralChatModelChoice, MistralChatModelConfigurationSupportService> {

	/**
	 * Constructor for the MistralAI chat models configuration controller.
	 * Initializes the parent class with the GMistralChatModelConfig class.
	 */
	public MistralAIChatModelsConfigurationController() {
		super(GMistralChatModelConfig.class);
	}

	/**
	 * Endpoint to insert a new MistralAI chat model configuration.
	 * 
	 * @param config The MistralAI chat model configuration to insert
	 * @return The operation status containing the inserted configuration
	 */
	@PostMapping(value = "insertMistralAIChatModelConfig", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<GMistralChatModelConfig> insertMistralAIChatModelConfig(
			@RequestBody GMistralChatModelConfig config) {
		return super.insert(config);

	}

	/**
	 * Endpoint to update an existing MistralAI chat model configuration.
	 * 
	 * @param config The MistralAI chat model configuration to update
	 * @return The operation status containing the updated configuration
	 */
	@PostMapping(value = "updateMistralAIChatModelConfig", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<GMistralChatModelConfig> updateMistralAIChatModelConfig(
			@RequestBody GMistralChatModelConfig config) {

		return super.update(config);
	}

	/**
	 * Endpoint to delete a MistralAI chat model configuration.
	 * 
	 * @param config The MistralAI chat model configuration to delete
	 * @return The operation status indicating the success of the delete operation
	 */
	@PostMapping(value = "deleteMistralAIChatModelConfig", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<Boolean> deleteMistralAIChatModelConfig(@RequestBody GMistralChatModelConfig config) {

		return super.delete(config);
	}

	/**
	 * Endpoint to find a MistralAI chat model configuration by its code.
	 * 
	 * @param code The code of the configuration to find
	 * @return The matching MistralAI chat model configuration
	 * @throws GeboPersistenceException If an error occurs during the database operation
	 */
	@GetMapping(value = "findMistralAIChatModelConfigByCode", produces = MediaType.APPLICATION_JSON_VALUE)
	public GMistralChatModelConfig findMistralAIChatModelConfigByCode(@RequestParam("code") String code)
			throws GeboPersistenceException {
		return super.findByCode(code);
	}
	
	/**
	 * Endpoint to retrieve available MistralAI chat models based on the provided configuration.
	 * 
	 * @param config The configuration used to retrieve the model choices
	 * @return The operation status containing the list of MistralAI chat model choices
	 */
	@PostMapping(value = "getMistralAIChatModels", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<List<GMistralChatModelChoice>> getMistralAIChatModels(@RequestBody GMistralChatModelConfig config) {
		return super.getModelChoices(config);
	}
}