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
import ai.gebo.llms.abstraction.layer.controllers.AbstractDecisionModelsConfigurationCRUDController;
import ai.gebo.llms.abstraction.layer.services.IGDecisionModelConfigurationSupportService;
import ai.gebo.llms.abstraction.layer.services.IGDecisionModelConfigurationSupportServiceRepositoryPattern;
import ai.gebo.llms.abstraction.layer.services.IGDecisionModelRuntimeConfigurationDao;
import ai.gebo.llms.openai_compat.config.GenericOpenAICompatibleProvidersConfig;
import ai.gebo.llms.openai_compat.model.GenericOpenAIAPIDecisionModelChoice;
import ai.gebo.llms.openai_compat.model.GenericOpenAIAPIDecisionModelConfig;
import ai.gebo.llms.openai_compat.modeltypes.GenericOpenAIDecisionModelTypeConfig;
import ai.gebo.model.OperationStatus;
import ai.gebo.security.services.IGSecurityAuditLoggerService;

/**
 * The admin CRUD of the decision model configurations of the OpenAI compatible
 * providers, every provider through the one controller: the configuration's model
 * type code names the provider.
 */
@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("api/admin/GenericOpenAIDecisionModelsConfigurationController")
public class GenericOpenAIDecisionModelsConfigurationController extends
		AbstractDecisionModelsConfigurationCRUDController<GenericOpenAIAPIDecisionModelConfig, GenericOpenAIAPIDecisionModelChoice> {

	private final IGDecisionModelConfigurationSupportServiceRepositoryPattern supportServiceRepoPattern;
	private final GenericOpenAICompatibleProvidersConfig config;

	public GenericOpenAIDecisionModelsConfigurationController(IGPersistentObjectManager persistentObjectManager,
			IGDecisionModelRuntimeConfigurationDao modelRuntimeConfigurationDao,
			IGDecisionModelConfigurationSupportServiceRepositoryPattern supportServiceRepoPattern,
			GenericOpenAICompatibleProvidersConfig config, IGSecurityAuditLoggerService securityAuditLoggerService) {
		super(persistentObjectManager, modelRuntimeConfigurationDao, GenericOpenAIAPIDecisionModelConfig.class,
				securityAuditLoggerService);
		this.supportServiceRepoPattern = supportServiceRepoPattern;
		this.config = config;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected OperationStatus<List<GenericOpenAIAPIDecisionModelChoice>> getModelChoices(
			GenericOpenAIAPIDecisionModelConfig cfg) {
		if (cfg.getModelTypeCode() == null)
			throw new RuntimeException("modelTypeCode cannot be null");
		IGDecisionModelConfigurationSupportService handler = supportServiceRepoPattern
				.findByCode(cfg.getModelTypeCode());
		if (handler == null)
			throw new RuntimeException(
					"modelTypeCode=>" + cfg.getModelTypeCode() + " with no corresponding model provider");
		return handler.getModelChoices(cfg);
	}

	@GetMapping(value = "getGenericOpenAIDecisionModelTypes", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<GenericOpenAIDecisionModelTypeConfig> getGenericOpenAIDecisionModelTypes() {
		return config.getDecisionModelProviders();
	}

	@GetMapping(value = "getGenericOpenAIDecisionModelConfigs", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<GenericOpenAIAPIDecisionModelConfig> getGenericOpenAIDecisionModelConfigs()
			throws GeboPersistenceException {
		return this.persistentObjectManager.findAll(GenericOpenAIAPIDecisionModelConfig.class);
	}

	@PostMapping(value = "insertGenericOpenAIAPIDecisionModelConfig", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<GenericOpenAIAPIDecisionModelConfig> insertGenericOpenAIAPIDecisionModelConfig(
			@RequestBody GenericOpenAIAPIDecisionModelConfig config) {
		return super.insert(config);
	}

	@PostMapping(value = "updateGenericOpenAIAPIDecisionModelConfig", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<GenericOpenAIAPIDecisionModelConfig> updateGenericOpenAIAPIDecisionModelConfig(
			@RequestBody GenericOpenAIAPIDecisionModelConfig config) {
		return super.update(config);
	}

	@PostMapping(value = "deleteGenericOpenAIAPIDecisionModelConfig", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<Boolean> deleteGenericOpenAIAPIDecisionModelConfig(
			@RequestBody GenericOpenAIAPIDecisionModelConfig config) {
		return super.delete(config);
	}

	@GetMapping(value = "findGenericOpenAIAPIDecisionModelConfigByCode", produces = MediaType.APPLICATION_JSON_VALUE)
	public GenericOpenAIAPIDecisionModelConfig findGenericOpenAIAPIDecisionModelConfigByCode(
			@RequestParam("code") String code) throws GeboPersistenceException {
		return super.findByCode(code);
	}

	@PostMapping(value = "getGenericOpenAIAPIDecisionModels", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<List<GenericOpenAIAPIDecisionModelChoice>> getGenericOpenAIAPIDecisionModels(
			@RequestBody GenericOpenAIAPIDecisionModelConfig config) {
		return getModelChoices(config);
	}
}
