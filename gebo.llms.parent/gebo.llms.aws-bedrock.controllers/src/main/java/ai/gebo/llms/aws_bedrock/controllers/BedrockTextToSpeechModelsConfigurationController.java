/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.aws_bedrock.controllers;

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
import ai.gebo.llms.abstraction.layer.controllers.BaseTextToSpeechModelsConfigurationCRUDController;
import ai.gebo.llms.abstraction.layer.services.IGTextToSpeechModelRuntimeConfigurationDao;
import ai.gebo.llms.aws_bedrock.model.GBedrockTextToSpeechModelChoice;
import ai.gebo.llms.aws_bedrock.model.GBedrockTextToSpeechModelConfig;
import ai.gebo.llms.aws_bedrock.services.BedrockTextToSpeechModelConfigurationSupportService;
import ai.gebo.model.OperationStatus;
import ai.gebo.security.services.IGSecurityAuditLoggerService;

/**
 * Admin controller for AWS (Amazon Polly) text-to-speech model configurations.
 */
@ConditionalOnProperty(prefix = "ai.gebo.llms.config", name = "awsBedrockEnabled", havingValue = "true")
@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("api/admin/BedrockTextToSpeechModelsConfigurationController")
public class BedrockTextToSpeechModelsConfigurationController extends
		BaseTextToSpeechModelsConfigurationCRUDController<GBedrockTextToSpeechModelConfig, GBedrockTextToSpeechModelChoice, BedrockTextToSpeechModelConfigurationSupportService> {

	public BedrockTextToSpeechModelsConfigurationController(IGPersistentObjectManager persistentObjectManager,
			IGTextToSpeechModelRuntimeConfigurationDao modelRuntimeConfigurationDao,
			BedrockTextToSpeechModelConfigurationSupportService ifaceType,
			IGSecurityAuditLoggerService securityAuditLoggerService) {
		super(persistentObjectManager, modelRuntimeConfigurationDao, GBedrockTextToSpeechModelConfig.class, ifaceType,
				securityAuditLoggerService);
	}

	@PostMapping(value = "insertBedrockTextToSpeechModelConfig", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<GBedrockTextToSpeechModelConfig> insertBedrockTextToSpeechModelConfig(
			@RequestBody GBedrockTextToSpeechModelConfig config) {
		return super.insert(config);
	}

	@PostMapping(value = "updateBedrockTextToSpeechModelConfig", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<GBedrockTextToSpeechModelConfig> updateBedrockTextToSpeechModelConfig(
			@RequestBody GBedrockTextToSpeechModelConfig config) {
		return super.update(config);
	}

	@PostMapping(value = "deleteBedrockTextToSpeechModelConfig", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<Boolean> deleteBedrockTextToSpeechModelConfig(
			@RequestBody GBedrockTextToSpeechModelConfig config) {
		return super.delete(config);
	}

	@GetMapping(value = "findBedrockTextToSpeechModelConfigByCode", produces = MediaType.APPLICATION_JSON_VALUE)
	public GBedrockTextToSpeechModelConfig findBedrockTextToSpeechModelConfigByCode(@RequestParam("code") String code)
			throws GeboPersistenceException {
		return super.findByCode(code);
	}

	@PostMapping(value = "getBedrockTextToSpeechModels", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<List<GBedrockTextToSpeechModelChoice>> getBedrockTextToSpeechModels(
			@RequestBody GBedrockTextToSpeechModelConfig config) {
		return super.getModelChoices(config);
	}
}
