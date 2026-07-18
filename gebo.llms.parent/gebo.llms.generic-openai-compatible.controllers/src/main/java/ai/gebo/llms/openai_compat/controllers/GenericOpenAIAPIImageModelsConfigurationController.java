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
import ai.gebo.llms.abstraction.layer.controllers.AbstractImageModelsConfigurationCRUDController;
import ai.gebo.llms.abstraction.layer.services.IGImageModelConfigurationSupportService;
import ai.gebo.llms.abstraction.layer.services.IGImageModelConfigurationSupportServiceRepositoryPattern;
import ai.gebo.llms.abstraction.layer.services.IGImageModelRuntimeConfigurationDao;
import ai.gebo.llms.openai_compat.config.GenericOpenAICompatibleProvidersConfig;
import ai.gebo.llms.openai_compat.model.GenericOpenAIAPIImageModelChoice;
import ai.gebo.llms.openai_compat.model.GenericOpenAIAPIImageModelConfig;
import ai.gebo.llms.openai_compat.modeltypes.GenericOpenAIImageModelTypeConfig;
import ai.gebo.model.OperationStatus;

/**
 * AI generated comments
 * 
 * Controller class responsible for managing configurations for
 * OpenAI-compatible image models. This controller provides endpoints for CRUD
 * operations on image model configurations and is accessible only to users with
 * ADMIN role.
 */
@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("api/admin/GenericOpenAIAPIImageModelsConfigurationController")
public class GenericOpenAIAPIImageModelsConfigurationController extends
		AbstractImageModelsConfigurationCRUDController<GenericOpenAIAPIImageModelConfig, GenericOpenAIAPIImageModelChoice> {

	private final IGImageModelConfigurationSupportServiceRepositoryPattern supportServiceRepoPattern;
	private final GenericOpenAICompatibleProvidersConfig config;

	public GenericOpenAIAPIImageModelsConfigurationController(IGPersistentObjectManager persistentObjectManager,
			IGImageModelRuntimeConfigurationDao modelRuntimeConfigurationDao,
			IGImageModelConfigurationSupportServiceRepositoryPattern supportServiceRepoPattern,
			GenericOpenAICompatibleProvidersConfig config) {
		super(persistentObjectManager, modelRuntimeConfigurationDao, GenericOpenAIAPIImageModelConfig.class);
		this.supportServiceRepoPattern = supportServiceRepoPattern;
		this.config = config;
	}

	@Override
	protected OperationStatus<List<GenericOpenAIAPIImageModelChoice>> getModelChoices(
			GenericOpenAIAPIImageModelConfig cfg) {
		if (cfg.getModelTypeCode() == null)
			throw new RuntimeException("modelTypeCode cannot be null");
		IGImageModelConfigurationSupportService handler = supportServiceRepoPattern
				.findByCode(cfg.getModelTypeCode());
		if (handler == null)
			throw new RuntimeException(
					"modelTypeCode=>" + cfg.getModelTypeCode() + " with no corresponding model provider");

		return handler.getModelChoices(cfg);
	}

	@GetMapping(value = "getGenericOpenAIImageModelTypes", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<GenericOpenAIImageModelTypeConfig> getGenericOpenAIImageModelTypes() {
		return config.getImageModelProviders();
	}

	@GetMapping(value = "getGenericOpenAIImageModelConfigs", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<GenericOpenAIAPIImageModelConfig> getGenericOpenAIImageModelConfigs() throws GeboPersistenceException {
		return this.persistentObjectManager.findAll(GenericOpenAIAPIImageModelConfig.class);
	}

	@PostMapping(value = "insertGenericOpenAIAPIImageModelConfig", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<GenericOpenAIAPIImageModelConfig> insertGenericOpenAIAPIImageModelConfig(
			@RequestBody GenericOpenAIAPIImageModelConfig config) {
		return super.insert(config);

	}

	@PostMapping(value = "updateGenericOpenAIAPIImageModelConfig", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<GenericOpenAIAPIImageModelConfig> updateGenericOpenAIAPIImageModelConfig(
			@RequestBody GenericOpenAIAPIImageModelConfig config) {

		return super.update(config);
	}

	@PostMapping(value = "deleteGenericOpenAIAPIImageModelConfig", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<Boolean> deleteGenericOpenAIAPIImageModelConfig(
			@RequestBody GenericOpenAIAPIImageModelConfig config) {

		return super.delete(config);
	}

	@GetMapping(value = "findGenericOpenAIAPIImageModelConfigByCode", produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericOpenAIAPIImageModelConfig findGenericOpenAIAPIImageModelConfigByCode(
			@RequestParam("code") String code) throws GeboPersistenceException {
		return super.findByCode(code);
	}

	@PostMapping(value = "getGenericOpenAIAPIImageModels", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<List<GenericOpenAIAPIImageModelChoice>> getGenericOpenAIAPIImageModels(
			@RequestBody GenericOpenAIAPIImageModelConfig config) {
		return getModelChoices(config);
	}

}
