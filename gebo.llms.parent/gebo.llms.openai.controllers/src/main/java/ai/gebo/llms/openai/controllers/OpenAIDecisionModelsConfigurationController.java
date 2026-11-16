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
import ai.gebo.llms.abstraction.layer.controllers.BaseDecisionModelsConfigurationCRUDController;
import ai.gebo.llms.abstraction.layer.services.IGDecisionModelRuntimeConfigurationDao;
import ai.gebo.llms.openai.model.GOpenAIDecisionModelChoice;
import ai.gebo.llms.openai.model.GOpenAIDecisionModelConfig;
import ai.gebo.llms.openai.services.OpenAIDecisionModelConfigurationSupportService;
import ai.gebo.model.OperationStatus;
import ai.gebo.security.services.IGSecurityAuditLoggerService;

/** The admin CRUD of the OpenAI decision model configurations. */
@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("api/admin/OpenAIDecisionModelsConfigurationController")
public class OpenAIDecisionModelsConfigurationController
		extends BaseDecisionModelsConfigurationCRUDController<GOpenAIDecisionModelConfig, GOpenAIDecisionModelChoice> {

	public OpenAIDecisionModelsConfigurationController(IGPersistentObjectManager persistentObjectManager,
			IGDecisionModelRuntimeConfigurationDao modelRuntimeConfigurationDao,
			OpenAIDecisionModelConfigurationSupportService supportService,
			IGSecurityAuditLoggerService securityAuditLoggerService) {
		super(persistentObjectManager, modelRuntimeConfigurationDao, GOpenAIDecisionModelConfig.class, supportService,
				securityAuditLoggerService);
	}

	@PostMapping(value = "insertOpenAIDecisionModelConfig", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<GOpenAIDecisionModelConfig> insertOpenAIDecisionModelConfig(
			@RequestBody GOpenAIDecisionModelConfig config) {
		return super.insert(config);
	}

	@PostMapping(value = "updateOpenAIDecisionModelConfig", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<GOpenAIDecisionModelConfig> updateOpenAIDecisionModelConfig(
			@RequestBody GOpenAIDecisionModelConfig config) {
		return super.update(config);
	}

	@PostMapping(value = "deleteOpenAIDecisionModelConfig", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<Boolean> deleteOpenAIDecisionModelConfig(@RequestBody GOpenAIDecisionModelConfig config) {
		return super.delete(config);
	}

	@GetMapping(value = "findOpenAIDecisionModelConfigByCode", produces = MediaType.APPLICATION_JSON_VALUE)
	public GOpenAIDecisionModelConfig findOpenAIDecisionModelConfigByCode(@RequestParam("code") String code)
			throws GeboPersistenceException {
		return super.findByCode(code);
	}

	@PostMapping(value = "getOpenAIDecisionModels", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<List<GOpenAIDecisionModelChoice>> getOpenAIDecisionModels(
			@RequestBody GOpenAIDecisionModelConfig config) {
		return super.getModelChoices(config);
	}
}
