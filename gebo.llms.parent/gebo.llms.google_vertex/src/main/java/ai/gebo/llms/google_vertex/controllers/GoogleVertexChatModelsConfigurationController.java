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
import ai.gebo.llms.abstraction.layer.controllers.BaseChatModelsConfigurationController;
import ai.gebo.llms.google_vertex.model.GGoogleVertexChatModelChoice;
import ai.gebo.llms.google_vertex.model.GGoogleVertexChatModelConfig;
import ai.gebo.llms.google_vertex.services.GoogleVertexChatModelConfigurationSupportService;
import ai.gebo.model.OperationStatus;

/**
 * AI generated comments
 * REST controller for managing Google Vertex AI chat model configurations.
 * This controller extends the base controller to provide specific endpoints for Google Vertex AI.
 * Only enabled when the 'googleVertexEnabled' property is set to true, and requires ADMIN role access.
 */
@ConditionalOnProperty(prefix = "ai.gebo.llms.config", name = "googleVertexEnabled", havingValue = "true")
@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("api/admin/GoogleVertexModelsConfigurationController")
public class GoogleVertexChatModelsConfigurationController extends
		BaseChatModelsConfigurationController<GGoogleVertexChatModelConfig, GGoogleVertexChatModelChoice, GoogleVertexChatModelConfigurationSupportService> {

	/**
	 * Constructor for the Google Vertex chat models configuration controller.
	 * Initializes the parent class with the Google Vertex chat model configuration class.
	 */
	public GoogleVertexChatModelsConfigurationController() {
		super(GGoogleVertexChatModelConfig.class);
	}

	/**
	 * Inserts a new Google Vertex chat model configuration.
	 * 
	 * @param config The configuration to insert
	 * @return OperationStatus containing the result and the inserted configuration
	 */
	@PostMapping(value = "insertGoogleVertexChatModelConfig", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<GGoogleVertexChatModelConfig> insertGoogleVertexChatModelConfig(
			@RequestBody GGoogleVertexChatModelConfig config) {
		return super.insert(config);

	}

	/**
	 * Updates an existing Google Vertex chat model configuration.
	 * 
	 * @param config The configuration to update
	 * @return OperationStatus containing the result and the updated configuration
	 */
	@PostMapping(value = "updateGoogleVertexChatModelConfig", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<GGoogleVertexChatModelConfig> updateGoogleVertexChatModelConfig(
			@RequestBody GGoogleVertexChatModelConfig config) {

		return super.update(config);
	}

	/**
	 * Deletes a Google Vertex chat model configuration.
	 * 
	 * @param config The configuration to delete
	 * @return OperationStatus containing the result of the deletion operation
	 */
	@PostMapping(value = "deleteGoogleVertexChatModelConfig", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<Boolean> deleteGoogleVertexChatModelConfig(@RequestBody GGoogleVertexChatModelConfig config) {

		return super.delete(config);
	}

	/**
	 * Finds a Google Vertex chat model configuration by its code.
	 * 
	 * @param code The code to search for
	 * @return The matching configuration
	 * @throws GeboPersistenceException If there's an error retrieving the configuration
	 */
	@GetMapping(value = "findGoogleVertexChatModelConfigByCode", produces = MediaType.APPLICATION_JSON_VALUE)
	public GGoogleVertexChatModelConfig findGoogleVertexChatModelConfigByCode(@RequestParam("code") String code)
			throws GeboPersistenceException {
		return super.findByCode(code);
	}
	
	/**
	 * Retrieves available Google Vertex chat models based on the provided configuration.
	 * 
	 * @param config The configuration to use for retrieving model choices
	 * @return OperationStatus containing the list of available model choices
	 */
	@PostMapping(value = "getGoogleVertexChatModels", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<List<GGoogleVertexChatModelChoice>> getGoogleVertexChatModels(@RequestBody GGoogleVertexChatModelConfig config) {
		return super.getModelChoices(config);
	}
}