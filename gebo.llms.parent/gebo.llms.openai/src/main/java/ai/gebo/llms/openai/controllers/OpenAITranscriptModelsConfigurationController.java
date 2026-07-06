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
import ai.gebo.architecture.persistence.IGPersistentObjectManager;
import ai.gebo.llms.abstraction.layer.controllers.BaseTranscriptModelsConfigurationCRUDController;
import ai.gebo.llms.abstraction.layer.services.IGTranscriptModelRuntimeConfigurationDao;
import ai.gebo.llms.openai.model.GOpenAITranscriptModelChoice;
import ai.gebo.llms.openai.model.GOpenAITranscriptModelConfig;
import ai.gebo.llms.openai.services.OpenAITranscriptModelConfigurationSupportService;
import ai.gebo.model.OperationStatus;

/**
 * AI generated comments
 * 
 * Controller for managing OpenAI transcript model configurations. Provides REST
 * endpoints to insert, update, delete, and query OpenAI transcript model
 * configurations. This controller is only active when the 'openAIEnabled'
 * property is set to true. Access is restricted to users with ADMIN role.
 */
@ConditionalOnProperty(prefix = "ai.gebo.llms.config", name = "openAIEnabled", havingValue = "true")
@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("api/admin/OpenAITranscriptModelsConfigurationController")
public class OpenAITranscriptModelsConfigurationController extends
		BaseTranscriptModelsConfigurationCRUDController<GOpenAITranscriptModelConfig, GOpenAITranscriptModelChoice, OpenAITranscriptModelConfigurationSupportService> {

	public OpenAITranscriptModelsConfigurationController(IGPersistentObjectManager persistentObjectManager,
			IGTranscriptModelRuntimeConfigurationDao modelRuntimeConfigurationDao,
			OpenAITranscriptModelConfigurationSupportService ifaceType) {
		super(persistentObjectManager, modelRuntimeConfigurationDao, GOpenAITranscriptModelConfig.class, ifaceType);

	}

	/**
	 * Inserts a new OpenAI transcript model configuration.
	 * 
	 * @param config The OpenAI transcript model configuration to insert
	 * @return Operation status containing the inserted configuration
	 */
	@PostMapping(value = "insertOpenAITranscriptModelConfig", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<GOpenAITranscriptModelConfig> insertOpenAITranscriptModelConfig(
			@RequestBody GOpenAITranscriptModelConfig config) {
		return super.insert(config);

	}

	/**
	 * Updates an existing OpenAI transcript model configuration.
	 * 
	 * @param config The OpenAI transcript model configuration to update
	 * @return Operation status containing the updated configuration
	 */
	@PostMapping(value = "updateOpenAITranscriptModelConfig", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<GOpenAITranscriptModelConfig> updateOpenAITranscriptModelConfig(
			@RequestBody GOpenAITranscriptModelConfig config) {

		return super.update(config);
	}

	/**
	 * Deletes an OpenAI transcript model configuration.
	 * 
	 * @param config The OpenAI transcript model configuration to delete
	 * @return Operation status indicating success or failure
	 */
	@PostMapping(value = "deleteOpenAITranscriptModelConfig", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<Boolean> deleteOpenAITranscriptModelConfig(
			@RequestBody GOpenAITranscriptModelConfig config) {

		return super.delete(config);
	}

	/**
	 * Finds an OpenAI transcript model configuration by its code.
	 * 
	 * @param code The code of the configuration to find
	 * @return The found OpenAI transcript model configuration
	 * @throws GeboPersistenceException If there is an error retrieving the
	 *                                  configuration
	 */
	@GetMapping(value = "findOpenAITranscriptModelConfigByCode", produces = MediaType.APPLICATION_JSON_VALUE)
	public GOpenAITranscriptModelConfig findOpenAITranscriptModelConfigByCode(@RequestParam("code") String code)
			throws GeboPersistenceException {
		return super.findByCode(code);
	}

	/**
	 * Retrieves a list of available OpenAI transcript models based on the provided
	 * configuration.
	 * 
	 * @param config The configuration to use for retrieving models
	 * @return Operation status containing the list of available model choices
	 */
	@PostMapping(value = "getOpenAITranscriptModels", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<List<GOpenAITranscriptModelChoice>> getOpenAITranscriptModels(
			@RequestBody GOpenAITranscriptModelConfig config) {
		return super.getModelChoices(config);
	}
}
