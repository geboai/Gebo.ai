/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.fastsetup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ai.gebo.fastsetup.model.ComponentVectorStoreStatus;
import ai.gebo.fastsetup.model.FastVectorStoreSetupData;
import ai.gebo.fastsetup.services.GeboFastVectorStoreSetupService;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.model.OperationStatus;
import jakarta.validation.Valid;

/**
 * Controller class for handling Gebo fast vector store setup related operations.
 * Provides endpoints for retrieving vector store status and creating vector store configurations.
 * 
 * AI generated comments
 */
@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/api/admin/GeboFastVectorStoreSetupController")
public class GeboFastVectorStoreSetupController {

	// Service for handling the business logic related to fast vector store setup
	@Autowired
	GeboFastVectorStoreSetupService service;

	/**
	 * Default constructor for GeboFastVectorStoreSetupController.
	 */
	public GeboFastVectorStoreSetupController() {

	}

	/**
	 * Endpoint to get the current status of the vector store.
	 * This operation requires administrative privileges.
	 *
	 * @return a ComponentVectorStoreStatus object indicating the status of the vector store.
	 * @throws LLMConfigException if there's an issue with the LLM configuration.
	 */
	@GetMapping(value = "getVectorStoreStatus", produces = MediaType.APPLICATION_JSON_VALUE)
	public ComponentVectorStoreStatus getVectorStoreStatus() throws LLMConfigException {
		return service.getVectorStoreStatus();
	}

	/**
	 * Endpoint to create a new vector store configuration.
	 * Consumes JSON data and returns the status of the operation.
	 * Requires administrative access.
	 *
	 * @param data the setup data for the vector store, validated before processing.
	 * @return an OperationStatus object containing the ComponentVectorStoreStatus.
	 * @throws LLMConfigException if there's an issue with the LLM configuration.
	 */
	@PostMapping(value = "createVectorStoreConfiguration", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<ComponentVectorStoreStatus> createVectorStoreConfiguration(
			@Valid @RequestBody FastVectorStoreSetupData data) throws LLMConfigException {
		return service.createVectorStoreConfiguration(data);
	}
}