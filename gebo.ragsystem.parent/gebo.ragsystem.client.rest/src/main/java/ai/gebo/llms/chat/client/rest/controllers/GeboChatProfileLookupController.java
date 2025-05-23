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
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
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
import ai.gebo.model.base.GLookupEntry;

/**
 * AI generated comments
 * 
 * Controller for handling Chat Profile lookup operations via REST endpoints.
 * Provides functionality to fetch and search for chat profile configurations.
 */
@RestController
@RequestMapping(path = "api/users/GeboChatProfileLookupController")
public class GeboChatProfileLookupController {
	/**
	 * Persistence manager injected by Spring to handle database operations
	 */
	@Autowired
	IGPersistentObjectManager persistenceManager;

	/**
	 * Default constructor
	 */
	public GeboChatProfileLookupController() {

	}

	/**
	 * Retrieves all chat profile configurations as lookup entries
	 * 
	 * @param _page Pagination information
	 * @return Page of GLookupEntry objects containing chat profile configurations
	 * @throws GeboPersistenceException If there's an error retrieving data from persistence layer
	 */
	@PostMapping(value = "getAllChatProfileConfigurationLoookup", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Page<GLookupEntry> getAllChatProfileConfigurationLoookup(@RequestBody DataPage _page)
			throws GeboPersistenceException {
		// Retrieve all chat profile configurations with pagination
		Page<GChatProfileConfiguration> page = persistenceManager.findAll(GChatProfileConfiguration.class,
				_page.toPageable());
		// Convert to GLookupEntry objects
		Page<GLookupEntry> newpage = new PageImpl<GLookupEntry>(GLookupEntry.of(page.getContent()));
		return newpage;
	}

	/**
	 * Parameter class for Query By Example lookup operations
	 * Contains pagination information and filter criteria
	 */
	public static class ChatProfileConfigurationLookupByQbeParam implements Serializable {
		public DataPage page = new DataPage();
		public GChatProfileConfiguration filter = new GChatProfileConfiguration();
	};

	/**
	 * Retrieves chat profile configurations matching the provided filter criteria
	 * 
	 * @param param Contains pagination information and filter criteria
	 * @return Page of GLookupEntry objects containing filtered chat profile configurations
	 * @throws GeboPersistenceException If there's an error retrieving data from persistence layer
	 */
	@PostMapping(value = "getChatProfileConfigurationLookupByQbe", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Page<GLookupEntry> getChatProfileConfigurationLookupByQbe(
			@RequestBody ChatProfileConfigurationLookupByQbeParam param) throws GeboPersistenceException {
		// Find configurations matching the filter criteria with pagination
		Page<GChatProfileConfiguration> page = persistenceManager.findAllByQbe(param.filter, param.page.toPageable());
		// Convert to GLookupEntry objects
		Page<GLookupEntry> newpage = new PageImpl<GLookupEntry>(GLookupEntry.of(page.getContent()));
		return newpage;
	}

	/**
	 * Retrieves a specific chat profile configuration by its code
	 * 
	 * @param code The unique identifier for the chat profile configuration
	 * @return The matching GChatProfileConfiguration object
	 * @throws GeboPersistenceException If there's an error retrieving data from persistence layer
	 */
	@GetMapping(value = "findChatProfileConfigurationLookupByCode", produces = MediaType.APPLICATION_JSON_VALUE)
	public GChatProfileConfiguration findChatProfileConfigurationLookupByCode(@RequestParam("code") String code)
			throws GeboPersistenceException {
		return (persistenceManager.findById(GChatProfileConfiguration.class, code));
	}
}