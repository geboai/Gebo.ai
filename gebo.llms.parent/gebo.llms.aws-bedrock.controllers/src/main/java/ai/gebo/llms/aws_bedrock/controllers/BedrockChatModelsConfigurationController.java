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
import ai.gebo.llms.abstraction.layer.controllers.BaseChatModelsConfigurationController;
import ai.gebo.llms.abstraction.layer.services.IGChatModelRuntimeConfigurationDao;
import ai.gebo.llms.aws_bedrock.model.GBedrockChatModelChoice;
import ai.gebo.llms.aws_bedrock.model.GBedrockChatModelConfig;
import ai.gebo.llms.aws_bedrock.services.BedrockChatModelConfigurationSupportService;
import ai.gebo.model.OperationStatus;
import ai.gebo.security.services.IGSecurityAuditLoggerService;

/**
 * Admin controller for AWS Bedrock chat model configurations. Only active when
 * {@code ai.gebo.llms.config.awsBedrockEnabled} is {@code true}; restricted to
 * ADMIN users.
 */
@ConditionalOnProperty(prefix = "ai.gebo.llms.config", name = "awsBedrockEnabled", havingValue = "true")
@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("api/admin/BedrockChatModelsConfigurationController")
public class BedrockChatModelsConfigurationController extends
		BaseChatModelsConfigurationController<GBedrockChatModelConfig, GBedrockChatModelChoice, BedrockChatModelConfigurationSupportService> {

	public BedrockChatModelsConfigurationController(IGPersistentObjectManager persistentObjectManager,
			IGChatModelRuntimeConfigurationDao modelRuntimeConfigurationDao,
			BedrockChatModelConfigurationSupportService ifaceType,
			IGSecurityAuditLoggerService securityAuditLoggerService) {
		super(persistentObjectManager, modelRuntimeConfigurationDao, GBedrockChatModelConfig.class, ifaceType,
				securityAuditLoggerService);
	}

	@PostMapping(value = "insertBedrockChatModelConfig", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<GBedrockChatModelConfig> insertBedrockChatModelConfig(
			@RequestBody GBedrockChatModelConfig config) {
		return super.insert(config);
	}

	@PostMapping(value = "updateBedrockChatModelConfig", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<GBedrockChatModelConfig> updateBedrockChatModelConfig(
			@RequestBody GBedrockChatModelConfig config) {
		return super.update(config);
	}

	@PostMapping(value = "deleteBedrockChatModelConfig", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<Boolean> deleteBedrockChatModelConfig(@RequestBody GBedrockChatModelConfig config) {
		return super.delete(config);
	}

	@GetMapping(value = "findBedrockChatModelConfigByCode", produces = MediaType.APPLICATION_JSON_VALUE)
	public GBedrockChatModelConfig findBedrockChatModelConfigByCode(@RequestParam("code") String code)
			throws GeboPersistenceException {
		return super.findByCode(code);
	}

	@PostMapping(value = "getBedrockChatModels", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<List<GBedrockChatModelChoice>> getBedrockChatModels(
			@RequestBody GBedrockChatModelConfig config) {
		return super.getModelChoices(config);
	}
}
