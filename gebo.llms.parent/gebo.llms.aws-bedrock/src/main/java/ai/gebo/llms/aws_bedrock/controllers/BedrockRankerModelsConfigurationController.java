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
import ai.gebo.llms.abstraction.layer.controllers.BaseRankerModelsConfigurationCRUDController;
import ai.gebo.llms.abstraction.layer.services.IGRankerModelRuntimeConfigurationDao;
import ai.gebo.llms.aws_bedrock.model.GBedrockRankerModelChoice;
import ai.gebo.llms.aws_bedrock.model.GBedrockRankerModelConfig;
import ai.gebo.llms.aws_bedrock.services.BedrockRankerModelConfigurationSupportService;
import ai.gebo.model.OperationStatus;

/**
 * Admin controller for AWS Bedrock reranking model configurations.
 */
@ConditionalOnProperty(prefix = "ai.gebo.llms.config", name = "awsBedrockEnabled", havingValue = "true")
@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("api/admin/BedrockRankerModelsConfigurationController")
public class BedrockRankerModelsConfigurationController extends
		BaseRankerModelsConfigurationCRUDController<GBedrockRankerModelConfig, GBedrockRankerModelChoice> {

	public BedrockRankerModelsConfigurationController(IGPersistentObjectManager persistentObjectManager,
			IGRankerModelRuntimeConfigurationDao modelRuntimeConfigurationDao,
			BedrockRankerModelConfigurationSupportService supportService) {
		super(persistentObjectManager, modelRuntimeConfigurationDao, GBedrockRankerModelConfig.class, supportService);
	}

	@PostMapping(value = "insertBedrockRankerModelConfig", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<GBedrockRankerModelConfig> insertBedrockRankerModelConfig(
			@RequestBody GBedrockRankerModelConfig config) {
		return super.insert(config);
	}

	@PostMapping(value = "updateBedrockRankerModelConfig", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<GBedrockRankerModelConfig> updateBedrockRankerModelConfig(
			@RequestBody GBedrockRankerModelConfig config) {
		return super.update(config);
	}

	@PostMapping(value = "deleteBedrockRankerModelConfig", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<Boolean> deleteBedrockRankerModelConfig(@RequestBody GBedrockRankerModelConfig config) {
		return super.delete(config);
	}

	@GetMapping(value = "findBedrockRankerModelConfigByCode", produces = MediaType.APPLICATION_JSON_VALUE)
	public GBedrockRankerModelConfig findBedrockRankerModelConfigByCode(@RequestParam("code") String code)
			throws GeboPersistenceException {
		return super.findByCode(code);
	}

	@PostMapping(value = "getBedrockRankerModels", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<List<GBedrockRankerModelChoice>> getBedrockRankerModels(
			@RequestBody GBedrockRankerModelConfig config) {
		return super.getModelChoices(config);
	}
}
