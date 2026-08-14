/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.llms.openai_compat.controllers;

import java.util.List;

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
import ai.gebo.llms.abstraction.layer.controllers.AbstractTranscriptModelsConfigurationCRUDController;
import ai.gebo.llms.abstraction.layer.services.IGTranscriptModelConfigurationSupportService;
import ai.gebo.llms.abstraction.layer.services.IGTranscriptModelConfigurationSupportServiceRepositoryPattern;
import ai.gebo.llms.abstraction.layer.services.IGTranscriptModelRuntimeConfigurationDao;
import ai.gebo.llms.openai_compat.config.GenericOpenAICompatibleProvidersConfig;
import ai.gebo.llms.openai_compat.model.GenericOpenAIAPITranscriptModelChoice;
import ai.gebo.llms.openai_compat.model.GenericOpenAIAPITranscriptModelConfig;
import ai.gebo.llms.openai_compat.modeltypes.GenericOpenAITranscriptModelType;
import ai.gebo.model.OperationStatus;
import ai.gebo.security.services.IGSecurityAuditLoggerService;

/**
 * AI generated comments
 * 
 * Controller class responsible for managing configurations for
 * OpenAI-compatible transcript models. This controller provides endpoints for
 * CRUD operations on transcript model configurations and is accessible only to
 * users with ADMIN role.
 */
@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("api/admin/GenericOpenAIAPITranscriptModelsConfigurationController")
public class GenericOpenAIAPITranscriptModelsConfigurationController extends
		AbstractTranscriptModelsConfigurationCRUDController<GenericOpenAIAPITranscriptModelConfig, GenericOpenAIAPITranscriptModelChoice> {

	private final IGTranscriptModelConfigurationSupportServiceRepositoryPattern supportServiceRepoPattern;
	private final GenericOpenAICompatibleProvidersConfig config;

	public GenericOpenAIAPITranscriptModelsConfigurationController(IGPersistentObjectManager persistentObjectManager,
			IGTranscriptModelRuntimeConfigurationDao modelRuntimeConfigurationDao,
			IGTranscriptModelConfigurationSupportServiceRepositoryPattern supportServiceRepoPattern,
			GenericOpenAICompatibleProvidersConfig config,
			IGSecurityAuditLoggerService securityAuditLoggerService) {
		super(persistentObjectManager, modelRuntimeConfigurationDao, GenericOpenAIAPITranscriptModelConfig.class, securityAuditLoggerService);
		this.supportServiceRepoPattern = supportServiceRepoPattern;
		this.config = config;
	}

	@Override
	protected OperationStatus<List<GenericOpenAIAPITranscriptModelChoice>> getModelChoices(
			GenericOpenAIAPITranscriptModelConfig cfg) {
		if (cfg.getModelTypeCode() == null)
			throw new RuntimeException("modelTypeCode cannot be null");
		IGTranscriptModelConfigurationSupportService handler = supportServiceRepoPattern
				.findByCode(cfg.getModelTypeCode());
		if (handler == null)
			throw new RuntimeException(
					"modelTypeCode=>" + cfg.getModelTypeCode() + " with no corresponding model provider");

		return handler.getModelChoices(cfg);
	}

	/**
	 * Retrieves the list of available OpenAI-compatible transcript model types
	 * 
	 * @return List of transcript model type configurations
	 */
	@GetMapping(value = "getGenericOpenAITranscriptModelTypes", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<GenericOpenAITranscriptModelType> getGenericOpenAITranscriptModelTypes() {
		return config.getTranscriptModelProviders();
	}

	/**
	 * Retrieves all persisted OpenAI-compatible transcript model configurations
	 * 
	 * @return List of transcript model configurations
	 * @throws GeboPersistenceException If there's an issue retrieving the
	 *                                  configurations
	 */
	@GetMapping(value = "getGenericOpenAITranscriptModelConfigs", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<GenericOpenAIAPITranscriptModelConfig> getGenericOpenAITranscriptModelConfigs()
			throws GeboPersistenceException {
		return this.persistentObjectManager.findAll(GenericOpenAIAPITranscriptModelConfig.class);
	}

	/**
	 * Creates a new OpenAI-compatible transcript model configuration
	 * 
	 * @param config The configuration to insert
	 * @return Operation status with the inserted configuration
	 */
	@PostMapping(value = "insertGenericOpenAIAPITranscriptModelConfig", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<GenericOpenAIAPITranscriptModelConfig> insertGenericOpenAIAPITranscriptModelConfig(
			@RequestBody GenericOpenAIAPITranscriptModelConfig config) {
		return super.insert(config);

	}

	/**
	 * Updates an existing OpenAI-compatible transcript model configuration
	 * 
	 * @param config The configuration to update
	 * @return Operation status with the updated configuration
	 */
	@PostMapping(value = "updateGenericOpenAIAPITranscriptModelConfig", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<GenericOpenAIAPITranscriptModelConfig> updateGenericOpenAIAPITranscriptModelConfig(
			@RequestBody GenericOpenAIAPITranscriptModelConfig config) {

		return super.update(config);
	}

	/**
	 * Deletes an OpenAI-compatible transcript model configuration
	 * 
	 * @param config The configuration to delete
	 * @return Operation status indicating success or failure
	 */
	@PostMapping(value = "deleteGenericOpenAIAPITranscriptModelConfig", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<Boolean> deleteGenericOpenAIAPITranscriptModelConfig(
			@RequestBody GenericOpenAIAPITranscriptModelConfig config) {

		return super.delete(config);
	}

	/**
	 * Finds an OpenAI-compatible transcript model configuration by its code
	 * 
	 * @param code The unique code identifier for the configuration
	 * @return The matching configuration if found
	 * @throws GeboPersistenceException If there's an issue retrieving the
	 *                                  configuration
	 */
	@GetMapping(value = "findGenericOpenAIAPITranscriptModelConfigByCode", produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericOpenAIAPITranscriptModelConfig findGenericOpenAIAPITranscriptModelConfigByCode(
			@RequestParam("code") String code) throws GeboPersistenceException {
		return super.findByCode(code);
	}

	/**
	 * Retrieves available transcript models based on the provided configuration
	 * 
	 * @param config The configuration to use as filter
	 * @return Operation status with list of available transcript model choices
	 */
	@PostMapping(value = "getGenericOpenAIAPITranscriptModels", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<List<GenericOpenAIAPITranscriptModelChoice>> getGenericOpenAIAPITranscriptModels(
			@RequestBody GenericOpenAIAPITranscriptModelConfig config) {
		return getModelChoices(config);
	}

}
