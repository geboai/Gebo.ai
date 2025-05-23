/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.chat.client.rest.controllers;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
import ai.gebo.architecture.utils.DataPage;
import ai.gebo.llms.chat.abstraction.layer.model.GChatProfileConfiguration;

/**
 * AI generated comments
 * 
 * REST controller that provides administrative endpoints for managing chat profile configurations.
 * This controller handles CRUD operations for GChatProfileConfiguration objects.
 * Only users with ADMIN role can access these endpoints.
 */
@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping(path = "api/admin/GeboAdminChatProfilesConfigurationController")
public class GeboAdminChatProfilesConfigurationController {
	/** Manages persistence operations for the chat profile configurations */
	@Autowired
	IGPersistentObjectManager persistenceManager;

	/**
	 * Default constructor for the controller
	 */
	public GeboAdminChatProfilesConfigurationController() {

	}

	/**
	 * Creates a new chat profile configuration in the database
	 * 
	 * @param configuration The chat profile configuration to insert
	 * @return The inserted chat profile configuration with generated IDs
	 * @throws GeboPersistenceException If there's an error during persistence
	 */
	@PostMapping(value = "insertChatProfile", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public GChatProfileConfiguration insertChatProfile(@RequestBody GChatProfileConfiguration configuration)
			throws GeboPersistenceException {
		return persistenceManager.insert(configuration);
	}

	/**
	 * Updates an existing chat profile configuration in the database
	 * 
	 * @param configuration The chat profile configuration to update
	 * @return The updated chat profile configuration
	 * @throws GeboPersistenceException If there's an error during persistence
	 */
	@PostMapping(value = "updateChatProfile", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public GChatProfileConfiguration updateChatProfile(@RequestBody GChatProfileConfiguration configuration)
			throws GeboPersistenceException {
		return persistenceManager.update(configuration);
	}

	/**
	 * Deletes a chat profile configuration from the database
	 * 
	 * @param configuration The chat profile configuration to delete
	 * @throws GeboPersistenceException If there's an error during persistence
	 */
	@PostMapping(value = "deleteChatProfile", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void deleteChatProfile(@RequestBody GChatProfileConfiguration configuration)
			throws GeboPersistenceException {
		persistenceManager.delete(configuration);
	}

	/**
	 * Retrieves all chat profile configurations with pagination
	 * 
	 * @param page The pagination information
	 * @return A page of chat profile configurations
	 * @throws GeboPersistenceException If there's an error during persistence
	 */
	@PostMapping(value = "getAllChatProfileConfiguration", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Page<GChatProfileConfiguration> getAllChatProfileConfiguration(@RequestBody DataPage page)
			throws GeboPersistenceException {
		return persistenceManager.findAll(GChatProfileConfiguration.class, page.toPageable());
	}

	/**
	 * Parameter class for QBE (Query By Example) searches of chat profile configurations
	 * Contains pagination information and a filter object
	 */
	public static class ChatProfileConfigurationByQbeParam implements Serializable {
		public DataPage page = new DataPage();
		public GChatProfileConfiguration filter = new GChatProfileConfiguration();
	};

	/**
	 * Searches for chat profile configurations using Query By Example pattern
	 * 
	 * @param param Object containing pagination and filter criteria
	 * @return A page of chat profile configurations matching the criteria
	 * @throws GeboPersistenceException If there's an error during persistence
	 */
	@PostMapping(value = "getChatProfileConfigurationByQbe", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Page<GChatProfileConfiguration> getChatProfileConfigurationByQbe(
			@RequestBody ChatProfileConfigurationByQbeParam param) throws GeboPersistenceException {
		return persistenceManager.findAllByQbe(param.filter, param.page.toPageable());
	}
	
	/**
	 * Finds a specific chat profile configuration by its code
	 * 
	 * @param code The unique code of the chat profile configuration
	 * @return The matching chat profile configuration
	 * @throws GeboPersistenceException If there's an error during persistence
	 */
	@GetMapping(value = "findChatProfileConfigurationByCode", produces = MediaType.APPLICATION_JSON_VALUE)
	public GChatProfileConfiguration findChatProfileConfigurationByCode(@RequestParam("code") String code) throws GeboPersistenceException {
		return persistenceManager.findById(GChatProfileConfiguration.class, code);
	}
}