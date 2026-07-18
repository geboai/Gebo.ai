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
import ai.gebo.llms.abstraction.layer.controllers.BaseTranscriptModelsConfigurationCRUDController;
import ai.gebo.llms.abstraction.layer.services.IGTranscriptModelRuntimeConfigurationDao;
import ai.gebo.llms.aws_bedrock.model.GBedrockTranscriptModelChoice;
import ai.gebo.llms.aws_bedrock.model.GBedrockTranscriptModelConfig;
import ai.gebo.llms.aws_bedrock.services.BedrockTranscriptModelConfigurationSupportService;
import ai.gebo.model.OperationStatus;

/**
 * Admin controller for AWS (Amazon Transcribe) transcript model configurations.
 */
@ConditionalOnProperty(prefix = "ai.gebo.llms.config", name = "awsBedrockEnabled", havingValue = "true")
@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("api/admin/BedrockTranscriptModelsConfigurationController")
public class BedrockTranscriptModelsConfigurationController extends
		BaseTranscriptModelsConfigurationCRUDController<GBedrockTranscriptModelConfig, GBedrockTranscriptModelChoice, BedrockTranscriptModelConfigurationSupportService> {

	public BedrockTranscriptModelsConfigurationController(IGPersistentObjectManager persistentObjectManager,
			IGTranscriptModelRuntimeConfigurationDao modelRuntimeConfigurationDao,
			BedrockTranscriptModelConfigurationSupportService ifaceType) {
		super(persistentObjectManager, modelRuntimeConfigurationDao, GBedrockTranscriptModelConfig.class, ifaceType);
	}

	@PostMapping(value = "insertBedrockTranscriptModelConfig", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<GBedrockTranscriptModelConfig> insertBedrockTranscriptModelConfig(
			@RequestBody GBedrockTranscriptModelConfig config) {
		return super.insert(config);
	}

	@PostMapping(value = "updateBedrockTranscriptModelConfig", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<GBedrockTranscriptModelConfig> updateBedrockTranscriptModelConfig(
			@RequestBody GBedrockTranscriptModelConfig config) {
		return super.update(config);
	}

	@PostMapping(value = "deleteBedrockTranscriptModelConfig", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<Boolean> deleteBedrockTranscriptModelConfig(
			@RequestBody GBedrockTranscriptModelConfig config) {
		return super.delete(config);
	}

	@GetMapping(value = "findBedrockTranscriptModelConfigByCode", produces = MediaType.APPLICATION_JSON_VALUE)
	public GBedrockTranscriptModelConfig findBedrockTranscriptModelConfigByCode(@RequestParam("code") String code)
			throws GeboPersistenceException {
		return super.findByCode(code);
	}

	@PostMapping(value = "getBedrockTranscriptModels", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<List<GBedrockTranscriptModelChoice>> getBedrockTranscriptModels(
			@RequestBody GBedrockTranscriptModelConfig config) {
		return super.getModelChoices(config);
	}
}
