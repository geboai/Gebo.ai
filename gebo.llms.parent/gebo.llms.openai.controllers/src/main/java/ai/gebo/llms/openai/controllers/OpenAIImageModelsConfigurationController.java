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
import ai.gebo.llms.abstraction.layer.controllers.BaseImageModelsConfigurationCRUDController;
import ai.gebo.llms.abstraction.layer.services.IGImageModelRuntimeConfigurationDao;
import ai.gebo.llms.openai.model.GOpenAIImageModelChoice;
import ai.gebo.llms.openai.model.GOpenAIImageModelConfig;
import ai.gebo.llms.openai.services.OpenAIImageModelConfigurationSupportService;
import ai.gebo.model.OperationStatus;
import ai.gebo.security.services.IGSecurityAuditLoggerService;

/**
 * AI generated comments
 * 
 * Controller for managing OpenAI image model configurations. Provides REST
 * endpoints to insert, update, delete, and query OpenAI image model
 * configurations. This controller is only active when the 'openAIEnabled'
 * property is set to true. Access is restricted to users with ADMIN role.
 */
@ConditionalOnProperty(prefix = "ai.gebo.llms.config", name = "openAIEnabled", havingValue = "true")
@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("api/admin/OpenAIImageModelsConfigurationController")
public class OpenAIImageModelsConfigurationController extends
		BaseImageModelsConfigurationCRUDController<GOpenAIImageModelConfig, GOpenAIImageModelChoice, OpenAIImageModelConfigurationSupportService> {

	public OpenAIImageModelsConfigurationController(IGPersistentObjectManager persistentObjectManager,
			IGImageModelRuntimeConfigurationDao modelRuntimeConfigurationDao,
			OpenAIImageModelConfigurationSupportService ifaceType,
			IGSecurityAuditLoggerService securityAuditLoggerService) {
		super(persistentObjectManager, modelRuntimeConfigurationDao, GOpenAIImageModelConfig.class, ifaceType,
				securityAuditLoggerService);

	}

	@PostMapping(value = "insertOpenAIImageModelConfig", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<GOpenAIImageModelConfig> insertOpenAIImageModelConfig(
			@RequestBody GOpenAIImageModelConfig config) {
		return super.insert(config);

	}

	@PostMapping(value = "updateOpenAIImageModelConfig", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<GOpenAIImageModelConfig> updateOpenAIImageModelConfig(
			@RequestBody GOpenAIImageModelConfig config) {

		return super.update(config);
	}

	@PostMapping(value = "deleteOpenAIImageModelConfig", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<Boolean> deleteOpenAIImageModelConfig(@RequestBody GOpenAIImageModelConfig config) {

		return super.delete(config);
	}

	@GetMapping(value = "findOpenAIImageModelConfigByCode", produces = MediaType.APPLICATION_JSON_VALUE)
	public GOpenAIImageModelConfig findOpenAIImageModelConfigByCode(@RequestParam("code") String code)
			throws GeboPersistenceException {
		return super.findByCode(code);
	}

	@PostMapping(value = "getOpenAIImageModels", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<List<GOpenAIImageModelChoice>> getOpenAIImageModels(
			@RequestBody GOpenAIImageModelConfig config) {
		return super.getModelChoices(config);
	}
}
