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
import ai.gebo.llms.abstraction.layer.controllers.BaseEmbeddingModelsConfigurationController;
import ai.gebo.llms.abstraction.layer.services.IGEmbeddingModelRuntimeConfigurationDao;
import ai.gebo.llms.aws_bedrock.model.GBedrockEmbeddingModelChoice;
import ai.gebo.llms.aws_bedrock.model.GBedrockEmbeddingModelConfig;
import ai.gebo.llms.aws_bedrock.services.BedrockEmbeddingModelConfigurationSupportService;
import ai.gebo.model.OperationStatus;

/**
 * Admin controller for AWS Bedrock embedding model configurations.
 */
@ConditionalOnProperty(prefix = "ai.gebo.llms.config", name = "awsBedrockEnabled", havingValue = "true")
@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("api/admin/BedrockEmbeddingModelsConfigurationController")
public class BedrockEmbeddingModelsConfigurationController extends
		BaseEmbeddingModelsConfigurationController<GBedrockEmbeddingModelConfig, GBedrockEmbeddingModelChoice, BedrockEmbeddingModelConfigurationSupportService> {

	public BedrockEmbeddingModelsConfigurationController(IGPersistentObjectManager persistentObjectManager,
			IGEmbeddingModelRuntimeConfigurationDao modelRuntimeConfigurationDao,
			BedrockEmbeddingModelConfigurationSupportService ifaceType) {
		super(persistentObjectManager, modelRuntimeConfigurationDao, GBedrockEmbeddingModelConfig.class, ifaceType);
	}

	@PostMapping(value = "insertBedrockEmbeddingModelConfig", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<GBedrockEmbeddingModelConfig> insertBedrockEmbeddingModelConfig(
			@RequestBody GBedrockEmbeddingModelConfig config) {
		return super.insert(config);
	}

	@PostMapping(value = "updateBedrockEmbeddingModelConfig", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<GBedrockEmbeddingModelConfig> updateBedrockEmbeddingModelConfig(
			@RequestBody GBedrockEmbeddingModelConfig config) {
		return super.update(config);
	}

	@PostMapping(value = "deleteBedrockEmbeddingModelConfig", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<Boolean> deleteBedrockEmbeddingModelConfig(
			@RequestBody GBedrockEmbeddingModelConfig config) {
		return super.delete(config);
	}

	@GetMapping(value = "findBedrockEmbeddingModelConfigByCode", produces = MediaType.APPLICATION_JSON_VALUE)
	public GBedrockEmbeddingModelConfig findBedrockEmbeddingModelConfigByCode(@RequestParam("code") String code)
			throws GeboPersistenceException {
		return super.findByCode(code);
	}

	@PostMapping(value = "getBedrockEmbeddingModels", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<List<GBedrockEmbeddingModelChoice>> getBedrockEmbeddingModels(
			@RequestBody GBedrockEmbeddingModelConfig config) {
		return super.getModelChoices(config);
	}
}
