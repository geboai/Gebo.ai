/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.onxx_transformers_embedding.controllers;

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
import ai.gebo.llms.abstraction.layer.controllers.BaseEmbeddingModelsConfigurationController;
import ai.gebo.llms.onxx_transformers_embedding.model.GONNXTransformersEmbeddingModelChoice;
import ai.gebo.llms.onxx_transformers_embedding.model.GONNXTransformersEmbeddingModelConfig;
import ai.gebo.llms.onxx_transformers_embedding.services.ONNXTransformersEmbeddingModelConfigurationSupportService;
import ai.gebo.model.OperationStatus;

/**
 * AI generated comments
 * 
 * REST controller for managing ONNX Transformers embedding model configurations.
 * Provides endpoints for CRUD operations on embedding model configurations.
 * Access is restricted to users with ADMIN role.
 */
@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("api/admin/ONNXTransformersEmbeddingModelsConfigurationController")
public class ONNXTransformersEmbeddingModelsConfigurationController extends
		BaseEmbeddingModelsConfigurationController<ai.gebo.llms.onxx_transformers_embedding.model.GONNXTransformersEmbeddingModelConfig, GONNXTransformersEmbeddingModelChoice, ONNXTransformersEmbeddingModelConfigurationSupportService> {

	/**
	 * Constructor that initializes the controller with the ONNX Transformers embedding model config class.
	 */
	public ONNXTransformersEmbeddingModelsConfigurationController() {
		super(GONNXTransformersEmbeddingModelConfig.class);
	}

	/**
	 * Creates a new ONNX Transformers embedding model configuration.
	 * 
	 * @param config The configuration to be inserted
	 * @return Operation status with the inserted configuration
	 */
	@PostMapping(value = "insertONNXTransformersEmbeddingModelConfig", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<GONNXTransformersEmbeddingModelConfig> insertONNXTransformersEmbeddingModelConfig(
			@RequestBody GONNXTransformersEmbeddingModelConfig config) {
		return super.insert(config);

	}

	/**
	 * Updates an existing ONNX Transformers embedding model configuration.
	 * 
	 * @param config The configuration to be updated
	 * @return Operation status with the updated configuration
	 */
	@PostMapping(value = "updateONNXTransformersEmbeddingModelConfig", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<GONNXTransformersEmbeddingModelConfig> updateONNXTransformersEmbeddingModelConfig(
			@RequestBody GONNXTransformersEmbeddingModelConfig config) {

		return super.update(config);
	}

	/**
	 * Deletes an ONNX Transformers embedding model configuration.
	 * 
	 * @param config The configuration to be deleted
	 * @return Operation status indicating success or failure of the deletion
	 */
	@PostMapping(value = "deleteONNXTransformersEmbeddingModelConfig", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<Boolean> deleteONNXTransformersEmbeddingModelConfig(
			@RequestBody GONNXTransformersEmbeddingModelConfig config) {

		return super.delete(config);
	}

	/**
	 * Retrieves an ONNX Transformers embedding model configuration by its code.
	 * 
	 * @param code The unique code identifying the configuration
	 * @return The matching configuration
	 * @throws GeboPersistenceException If an error occurs during retrieval from persistence layer
	 */
	@GetMapping(value = "findONNXTransformersEmbeddingModelConfigByCode", produces = MediaType.APPLICATION_JSON_VALUE)
	public GONNXTransformersEmbeddingModelConfig findONNXTransformersEmbeddingModelConfigByCode(
			@RequestParam("code") String code) throws GeboPersistenceException {
		return super.findByCode(code);
	}

	/**
	 * Retrieves a list of available ONNX Transformers embedding models based on the provided configuration.
	 * 
	 * @param config The configuration used to filter model choices
	 * @return Operation status with the list of available model choices
	 */
	@PostMapping(value = "getONNXTransformersEmbeddingModels", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<List<GONNXTransformersEmbeddingModelChoice>> getONNXTransformersEmbeddingModels(
			@RequestBody GONNXTransformersEmbeddingModelConfig config) {
		return super.getModelChoices(config);
	}
}