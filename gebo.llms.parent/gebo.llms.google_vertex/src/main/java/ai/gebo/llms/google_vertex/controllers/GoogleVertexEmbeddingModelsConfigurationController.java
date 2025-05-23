/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.google_vertex.controllers;

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
import ai.gebo.llms.abstraction.layer.controllers.BaseEmbeddingModelsConfigurationController;
import ai.gebo.llms.google_vertex.model.GGoogleVertexEmbeddingModelChoice;
import ai.gebo.llms.google_vertex.model.GGoogleVertexEmbeddingModelConfig;
import ai.gebo.llms.google_vertex.services.GoogleVertexEmbeddingModelConfigurationSupportService;
import ai.gebo.model.OperationStatus;

/**
 * AI generated comments
 * Controller responsible for managing Google Vertex embedding model configurations.
 * This controller is only active when the 'googleVertexEnabled' property is set to 'true'.
 * Access is restricted to users with the ADMIN role.
 */
@ConditionalOnProperty(prefix = "ai.gebo.llms.config", name = "googleVertexEnabled", havingValue = "true")
@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("api/admin/GoogleVertexEmbeddingModelsConfigurationController")
public class GoogleVertexEmbeddingModelsConfigurationController extends
		BaseEmbeddingModelsConfigurationController<GGoogleVertexEmbeddingModelConfig, GGoogleVertexEmbeddingModelChoice, GoogleVertexEmbeddingModelConfigurationSupportService> {

	/**
	 * Constructor that initializes the controller with the Google Vertex embedding model configuration class.
	 */
	public GoogleVertexEmbeddingModelsConfigurationController() {
		super(GGoogleVertexEmbeddingModelConfig.class);
	}

	/**
	 * Endpoint to insert a new Google Vertex embedding model configuration.
	 * 
	 * @param config The Google Vertex embedding model configuration to insert
	 * @return Operation status containing the inserted configuration
	 */
	@PostMapping(value = "insertGoogleVertexEmbeddingModelConfig", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<GGoogleVertexEmbeddingModelConfig> insertGoogleVertexEmbeddingModelConfig(
			@RequestBody GGoogleVertexEmbeddingModelConfig config) {
		return super.insert(config);

	}

	/**
	 * Endpoint to update an existing Google Vertex embedding model configuration.
	 * 
	 * @param config The Google Vertex embedding model configuration to update
	 * @return Operation status containing the updated configuration
	 */
	@PostMapping(value = "updateGoogleVertexEmbeddingModelConfig", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<GGoogleVertexEmbeddingModelConfig> updateGoogleVertexEmbeddingModelConfig(
			@RequestBody GGoogleVertexEmbeddingModelConfig config) {

		return super.update(config);
	}

	/**
	 * Endpoint to delete a Google Vertex embedding model configuration.
	 * 
	 * @param config The Google Vertex embedding model configuration to delete
	 * @return Operation status indicating whether the deletion was successful
	 */
	@PostMapping(value = "deleteGoogleVertexEmbeddingModelConfig", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<Boolean> deleteGoogleVertexEmbeddingModelConfig(@RequestBody GGoogleVertexEmbeddingModelConfig config) {

		return super.delete(config);
	}

	/**
	 * Endpoint to find a Google Vertex embedding model configuration by its code.
	 * 
	 * @param code The code of the configuration to find
	 * @return The found Google Vertex embedding model configuration
	 * @throws GeboPersistenceException If there is an issue retrieving the configuration
	 */
	@GetMapping(value = "findGoogleVertexEmbeddingModelConfigByCode", produces = MediaType.APPLICATION_JSON_VALUE)
	public GGoogleVertexEmbeddingModelConfig findGoogleVertexEmbeddingModelConfigByCode(@RequestParam("code") String code)
			throws GeboPersistenceException {
		return super.findByCode(code);
	}

	/**
	 * Endpoint to retrieve available Google Vertex embedding models based on the provided configuration.
	 * 
	 * @param config The configuration used to determine available models
	 * @return Operation status containing a list of available embedding model choices
	 */
	@PostMapping(value = "getGoogleVertexEmbeddingModels", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<List<GGoogleVertexEmbeddingModelChoice>> getGoogleVertexEmbeddingModels(
			@RequestBody GGoogleVertexEmbeddingModelConfig config) {
		return super.getModelChoices(config);
	}
}