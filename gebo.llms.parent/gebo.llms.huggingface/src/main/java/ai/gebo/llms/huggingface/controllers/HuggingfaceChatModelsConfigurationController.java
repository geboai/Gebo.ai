/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.huggingface.controllers;

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
import ai.gebo.llms.huggingface.model.GHuggingfaceChatModelChoice;
import ai.gebo.llms.huggingface.model.GHuggingfaceChatModelConfig;
import ai.gebo.llms.huggingface.services.HuggingfaceChatModelConfigurationSupportService;
import ai.gebo.model.OperationStatus;

/**
 * REST controller for managing Huggingface chat model configurations.
 * This controller is only enabled when the huggingfaceEnabled property is set to true.
 * Access is restricted to users with the ADMIN role.
 * 
 * AI generated comments
 */
@ConditionalOnProperty(prefix = "ai.gebo.llms.config", name = "huggingfaceEnabled", havingValue = "true")
@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("api/admin/HuggingfaceChatModelsConfigurationController")
public class HuggingfaceChatModelsConfigurationController extends
		BaseChatModelsConfigurationController<GHuggingfaceChatModelConfig, GHuggingfaceChatModelChoice, HuggingfaceChatModelConfigurationSupportService> {
	
	/**
	 * Constructor that initializes the controller with the GHuggingfaceChatModelConfig class.
	 */
	public HuggingfaceChatModelsConfigurationController() {
		super(GHuggingfaceChatModelConfig.class);
	}

	/**
	 * Creates a new Huggingface chat model configuration.
	 * 
	 * @param config The Huggingface chat model configuration to insert
	 * @return Operation status with the inserted configuration
	 */
	@PostMapping(value = "insertHuggingfaceChatModelConfig", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<GHuggingfaceChatModelConfig> insertHuggingfaceChatModelConfig(
			@RequestBody GHuggingfaceChatModelConfig config) {
		return super.insert(config);

	}

	/**
	 * Updates an existing Huggingface chat model configuration.
	 * 
	 * @param config The Huggingface chat model configuration to update
	 * @return Operation status with the updated configuration
	 */
	@PostMapping(value = "updateHuggingfaceChatModelConfig", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<GHuggingfaceChatModelConfig> updateHuggingfaceChatModelConfig(
			@RequestBody GHuggingfaceChatModelConfig config) {

		return super.update(config);
	}

	/**
	 * Deletes a Huggingface chat model configuration.
	 * 
	 * @param config The Huggingface chat model configuration to delete
	 * @return Operation status indicating success or failure
	 */
	@PostMapping(value = "deleteHuggingfaceChatModelConfig", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<Boolean> deleteHuggingfaceChatModelConfig(@RequestBody GHuggingfaceChatModelConfig config) {

		return super.delete(config);
	}

	/**
	 * Retrieves a Huggingface chat model configuration by its code.
	 * 
	 * @param code The unique code of the configuration to find
	 * @return The found Huggingface chat model configuration
	 * @throws GeboPersistenceException If there's an error retrieving the configuration
	 */
	@GetMapping(value = "findHuggingfaceChatModelConfigByCode", produces = MediaType.APPLICATION_JSON_VALUE)
	public GHuggingfaceChatModelConfig findHuggingfaceChatModelConfigByCode(@RequestParam("code") String code)
			throws GeboPersistenceException {
		return super.findByCode(code);
	}

	/**
	 * Retrieves a list of available Huggingface chat models based on the provided configuration.
	 * 
	 * @param config Configuration parameters for retrieving model choices
	 * @return Operation status with the list of available Huggingface chat models
	 */
	@PostMapping(value = "getHuggingfaceModels", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<List<GHuggingfaceChatModelChoice>> getHuggingfaceChatModels(@RequestBody GHuggingfaceChatModelConfig config) {
		return super.getModelChoices(config);
	}
	
}