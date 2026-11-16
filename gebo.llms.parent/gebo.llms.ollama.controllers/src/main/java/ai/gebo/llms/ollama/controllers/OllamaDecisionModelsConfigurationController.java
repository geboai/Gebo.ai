/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.ollama.controllers;

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
import ai.gebo.llms.ollama.model.GOllamaDecisionModelChoice;
import ai.gebo.llms.ollama.model.GOllamaDecisionModelConfig;
import ai.gebo.llms.ollama.services.OllamaDecisionModelConfigurationSupportService;
import ai.gebo.model.OperationStatus;
import ai.gebo.security.services.IGSecurityAuditLoggerService;

/** The admin CRUD of the Ollama decision model configurations. */
@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("api/admin/OllamaDecisionModelsConfigurationController")
public class OllamaDecisionModelsConfigurationController
		extends BaseDecisionModelsConfigurationCRUDController<GOllamaDecisionModelConfig, GOllamaDecisionModelChoice> {

	public OllamaDecisionModelsConfigurationController(IGPersistentObjectManager persistentObjectManager,
			IGDecisionModelRuntimeConfigurationDao modelRuntimeConfigurationDao,
			OllamaDecisionModelConfigurationSupportService supportService,
			IGSecurityAuditLoggerService securityAuditLoggerService) {
		super(persistentObjectManager, modelRuntimeConfigurationDao, GOllamaDecisionModelConfig.class, supportService,
				securityAuditLoggerService);
	}

	@PostMapping(value = "insertOllamaDecisionModelConfig", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<GOllamaDecisionModelConfig> insertOllamaDecisionModelConfig(
			@RequestBody GOllamaDecisionModelConfig config) {
		return super.insert(config);
	}

	@PostMapping(value = "updateOllamaDecisionModelConfig", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<GOllamaDecisionModelConfig> updateOllamaDecisionModelConfig(
			@RequestBody GOllamaDecisionModelConfig config) {
		return super.update(config);
	}

	@PostMapping(value = "deleteOllamaDecisionModelConfig", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<Boolean> deleteOllamaDecisionModelConfig(@RequestBody GOllamaDecisionModelConfig config) {
		return super.delete(config);
	}

	@GetMapping(value = "findOllamaDecisionModelConfigByCode", produces = MediaType.APPLICATION_JSON_VALUE)
	public GOllamaDecisionModelConfig findOllamaDecisionModelConfigByCode(@RequestParam("code") String code)
			throws GeboPersistenceException {
		return super.findByCode(code);
	}

	@PostMapping(value = "getOllamaDecisionModels", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<List<GOllamaDecisionModelChoice>> getOllamaDecisionModels(
			@RequestBody GOllamaDecisionModelConfig config) {
		return super.getModelChoices(config);
	}
}
