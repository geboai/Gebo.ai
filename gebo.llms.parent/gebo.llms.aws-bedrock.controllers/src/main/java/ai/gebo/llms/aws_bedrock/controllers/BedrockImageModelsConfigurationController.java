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
import ai.gebo.llms.abstraction.layer.controllers.BaseImageModelsConfigurationCRUDController;
import ai.gebo.llms.abstraction.layer.services.IGImageModelRuntimeConfigurationDao;
import ai.gebo.llms.aws_bedrock.model.GBedrockImageModelChoice;
import ai.gebo.llms.aws_bedrock.model.GBedrockImageModelConfig;
import ai.gebo.llms.aws_bedrock.services.BedrockImageModelConfigurationSupportService;
import ai.gebo.model.OperationStatus;

/**
 * Admin controller for AWS Bedrock image model configurations.
 */
@ConditionalOnProperty(prefix = "ai.gebo.llms.config", name = "awsBedrockEnabled", havingValue = "true")
@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("api/admin/BedrockImageModelsConfigurationController")
public class BedrockImageModelsConfigurationController extends
		BaseImageModelsConfigurationCRUDController<GBedrockImageModelConfig, GBedrockImageModelChoice, BedrockImageModelConfigurationSupportService> {

	public BedrockImageModelsConfigurationController(IGPersistentObjectManager persistentObjectManager,
			IGImageModelRuntimeConfigurationDao modelRuntimeConfigurationDao,
			BedrockImageModelConfigurationSupportService ifaceType) {
		super(persistentObjectManager, modelRuntimeConfigurationDao, GBedrockImageModelConfig.class, ifaceType);
	}

	@PostMapping(value = "insertBedrockImageModelConfig", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<GBedrockImageModelConfig> insertBedrockImageModelConfig(
			@RequestBody GBedrockImageModelConfig config) {
		return super.insert(config);
	}

	@PostMapping(value = "updateBedrockImageModelConfig", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<GBedrockImageModelConfig> updateBedrockImageModelConfig(
			@RequestBody GBedrockImageModelConfig config) {
		return super.update(config);
	}

	@PostMapping(value = "deleteBedrockImageModelConfig", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<Boolean> deleteBedrockImageModelConfig(@RequestBody GBedrockImageModelConfig config) {
		return super.delete(config);
	}

	@GetMapping(value = "findBedrockImageModelConfigByCode", produces = MediaType.APPLICATION_JSON_VALUE)
	public GBedrockImageModelConfig findBedrockImageModelConfigByCode(@RequestParam("code") String code)
			throws GeboPersistenceException {
		return super.findByCode(code);
	}

	@PostMapping(value = "getBedrockImageModels", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<List<GBedrockImageModelChoice>> getBedrockImageModels(
			@RequestBody GBedrockImageModelConfig config) {
		return super.getModelChoices(config);
	}
}
