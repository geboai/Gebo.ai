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
import ai.gebo.llms.abstraction.layer.controllers.AbstractTextToSpeechModelsConfigurationCRUDController;
import ai.gebo.llms.abstraction.layer.services.IGTextToSpeechModelConfigurationSupportService;
import ai.gebo.llms.abstraction.layer.services.IGTextToSpeechModelConfigurationSupportServiceRepositoryPattern;
import ai.gebo.llms.abstraction.layer.services.IGTextToSpeechModelRuntimeConfigurationDao;
import ai.gebo.llms.openai_compat.config.GenericOpenAICompatibleProvidersConfig;
import ai.gebo.llms.openai_compat.model.GenericOpenAIAPITextToSpeechModelChoice;
import ai.gebo.llms.openai_compat.model.GenericOpenAIAPITextToSpeechModelConfig;
import ai.gebo.llms.openai_compat.modeltypes.GenericOpenAITextToSpeechModelType;
import ai.gebo.model.OperationStatus;

/**
 * AI generated comments
 * 
 * Controller class responsible for managing configurations for
 * OpenAI-compatible text to speech models. This controller provides endpoints
 * for CRUD operations on text to speech model configurations and is accessible
 * only to users with ADMIN role.
 */
@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("api/admin/GenericOpenAIAPITextToSpeechModelsConfigurationController")
public class GenericOpenAIAPITextToSpeechModelsConfigurationController extends
		AbstractTextToSpeechModelsConfigurationCRUDController<GenericOpenAIAPITextToSpeechModelConfig, GenericOpenAIAPITextToSpeechModelChoice> {

	private final IGTextToSpeechModelConfigurationSupportServiceRepositoryPattern supportServiceRepoPattern;
	private final GenericOpenAICompatibleProvidersConfig config;

	public GenericOpenAIAPITextToSpeechModelsConfigurationController(IGPersistentObjectManager persistentObjectManager,
			IGTextToSpeechModelRuntimeConfigurationDao modelRuntimeConfigurationDao,
			IGTextToSpeechModelConfigurationSupportServiceRepositoryPattern supportServiceRepoPattern,
			GenericOpenAICompatibleProvidersConfig config) {
		super(persistentObjectManager, modelRuntimeConfigurationDao, GenericOpenAIAPITextToSpeechModelConfig.class);
		this.supportServiceRepoPattern = supportServiceRepoPattern;
		this.config = config;
	}

	@Override
	protected OperationStatus<List<GenericOpenAIAPITextToSpeechModelChoice>> getModelChoices(
			GenericOpenAIAPITextToSpeechModelConfig cfg) {
		if (cfg.getModelTypeCode() == null)
			throw new RuntimeException("modelTypeCode cannot be null");
		IGTextToSpeechModelConfigurationSupportService handler = supportServiceRepoPattern
				.findByCode(cfg.getModelTypeCode());
		if (handler == null)
			throw new RuntimeException(
					"modelTypeCode=>" + cfg.getModelTypeCode() + " with no corresponding model provider");

		return handler.getModelChoices(cfg);
	}

	/**
	 * Retrieves the list of available OpenAI-compatible text to speech model types
	 * 
	 * @return List of text to speech model type configurations
	 */
	@GetMapping(value = "getGenericOpenAITextToSpeechModelTypes", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<GenericOpenAITextToSpeechModelType> getGenericOpenAITextToSpeechModelTypes() {
		return config.getTextToSpeechModelProviders();
	}

	/**
	 * Retrieves all persisted OpenAI-compatible text to speech model configurations
	 * 
	 * @return List of text to speech model configurations
	 * @throws GeboPersistenceException If there's an issue retrieving the
	 *                                   configurations
	 */
	@GetMapping(value = "getGenericOpenAITextToSpeechModelConfigs", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<GenericOpenAIAPITextToSpeechModelConfig> getGenericOpenAITextToSpeechModelConfigs()
			throws GeboPersistenceException {
		return this.persistentObjectManager.findAll(GenericOpenAIAPITextToSpeechModelConfig.class);
	}

	/**
	 * Creates a new OpenAI-compatible text to speech model configuration
	 * 
	 * @param config The configuration to insert
	 * @return Operation status with the inserted configuration
	 */
	@PostMapping(value = "insertGenericOpenAIAPITextToSpeechModelConfig", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<GenericOpenAIAPITextToSpeechModelConfig> insertGenericOpenAIAPITextToSpeechModelConfig(
			@RequestBody GenericOpenAIAPITextToSpeechModelConfig config) {
		return super.insert(config);

	}

	/**
	 * Updates an existing OpenAI-compatible text to speech model configuration
	 * 
	 * @param config The configuration to update
	 * @return Operation status with the updated configuration
	 */
	@PostMapping(value = "updateGenericOpenAIAPITextToSpeechModelConfig", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<GenericOpenAIAPITextToSpeechModelConfig> updateGenericOpenAIAPITextToSpeechModelConfig(
			@RequestBody GenericOpenAIAPITextToSpeechModelConfig config) {

		return super.update(config);
	}

	/**
	 * Deletes an OpenAI-compatible text to speech model configuration
	 * 
	 * @param config The configuration to delete
	 * @return Operation status indicating success or failure
	 */
	@PostMapping(value = "deleteGenericOpenAIAPITextToSpeechModelConfig", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<Boolean> deleteGenericOpenAIAPITextToSpeechModelConfig(
			@RequestBody GenericOpenAIAPITextToSpeechModelConfig config) {

		return super.delete(config);
	}

	/**
	 * Finds an OpenAI-compatible text to speech model configuration by its code
	 * 
	 * @param code The unique code identifier for the configuration
	 * @return The matching configuration if found
	 * @throws GeboPersistenceException If there's an issue retrieving the
	 *                                  configuration
	 */
	@GetMapping(value = "findGenericOpenAIAPITextToSpeechModelConfigByCode", produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericOpenAIAPITextToSpeechModelConfig findGenericOpenAIAPITextToSpeechModelConfigByCode(
			@RequestParam("code") String code) throws GeboPersistenceException {
		return super.findByCode(code);
	}

	/**
	 * Retrieves available text to speech models based on the provided configuration
	 * 
	 * @param config The configuration to use as filter
	 * @return Operation status with list of available text to speech model choices
	 */
	@PostMapping(value = "getGenericOpenAIAPITextToSpeechModels", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<List<GenericOpenAIAPITextToSpeechModelChoice>> getGenericOpenAIAPITextToSpeechModels(
			@RequestBody GenericOpenAIAPITextToSpeechModelConfig config) {
		return getModelChoices(config);
	}

}
