/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.ragsystem.vectorstores.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.model.GUserMessage;
import ai.gebo.model.GUserMessage.MsgServerity;
import ai.gebo.model.OperationStatus;
import ai.gebo.ragsystem.vectorstores.model.GeboMongoVectorStoreConfig;
import ai.gebo.ragsystem.vectorstores.services.GeboVectorStoreConfigurationService;

/**
 * Controller for managing vector store configurations.
 * This REST controller handles retrieval and updating of vector store configurations.
 * Only users with ADMIN role can access these endpoints.
 * 
 * AI generated comments
 */
@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("api/admin/GeboVectorStoreConfigurationController")
public class GeboVectorStoreConfigurationController {
	/**
	 * Logger for this controller.
	 */
	private final static Logger LOGGER = LoggerFactory.getLogger(GeboVectorStoreConfigurationController.class);
	
	/**
	 * Service responsible for vector store configuration operations.
	 */
	@Autowired
	GeboVectorStoreConfigurationService service;

	/**
	 * Retrieves the current vector store configuration.
	 * 
	 * @return The current vector store configuration
	 * @throws LLMConfigException if there's an issue retrieving the configuration
	 */
	@GetMapping(value = "getActualVectorStoreConfiguration", produces = MediaType.APPLICATION_JSON_VALUE)
	public GeboMongoVectorStoreConfig getActualVectorStoreConfiguration() throws LLMConfigException {
		return service.getActualConfiguration();
	}

	/**
	 * Validates, tests, saves, and applies a new vector store configuration.
	 * 
	 * @param configuration The new vector store configuration to be applied
	 * @return An operation status with the result of the configuration update process
	 * @throws LLMConfigException if there's an issue with the configuration
	 */
	@PostMapping(value = "vectorStoreConfigurationApplyAndSave", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<GeboMongoVectorStoreConfig> vectorStoreConfigurationApplyAndSave(
			@RequestBody GeboMongoVectorStoreConfig configuration) throws LLMConfigException {
		OperationStatus<GeboMongoVectorStoreConfig> status = null;
		try {
			// Validate and test the configuration
			status = service.validateAndTestConfiguration(configuration);
			// If no error messages, save and notify about changes
			if (!status.getMessages().isEmpty() && !status.getMessages().stream().anyMatch(x -> {
				return x.getSeverity() == MsgServerity.error;
			})) {
				service.save(configuration);
				service.notifyVectorStoreConfigurationChanges();
				status.getMessages()
						.add(GUserMessage.successMessage("Configuration saved", "Configuration saved successfully"));
				
			}
			return status;
		} catch (Throwable th) {
			// Handle any unexpected errors
			status = OperationStatus.of(th);
			status.setResult(getActualVectorStoreConfiguration());
			return status;
		}
	}
}