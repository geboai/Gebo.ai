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
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Stream;

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
import ai.gebo.llms.chat.abstraction.layer.model.GPromptConfig;

/**
 * AI generated comments
 * 
 * REST controller for managing prompt configurations.
 * This controller provides endpoints for CRUD operations on GPromptConfig objects.
 * Access is restricted to users with ADMIN role.
 */
@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping(path = "api/admin/GeboAdminPromptsController")
public class GeboAdminPromptsController {
	/**
	 * Persistence manager for database operations.
	 */
	@Autowired
	IGPersistentObjectManager persistenceManager;

	/**
	 * Default constructor for GeboAdminPromptsController.
	 */
	public GeboAdminPromptsController() {

	}

	/**
	 * Retrieves a prompt configuration by its code.
	 * 
	 * @param code The unique identifier for the prompt configuration
	 * @return The found GPromptConfig object
	 * @throws GeboPersistenceException If there's an error during the database operation
	 */
	@GetMapping(value = "findPromptConfigByCode", produces = MediaType.APPLICATION_JSON_VALUE)
	public GPromptConfig findPromptConfigByCode(@RequestParam("code") String code) throws GeboPersistenceException {
		return persistenceManager.findById(GPromptConfig.class, code);
	}

	/**
	 * Creates a new prompt configuration.
	 * 
	 * @param Config The prompt configuration to insert
	 * @return The inserted GPromptConfig object with generated ID
	 * @throws GeboPersistenceException If there's an error during the database operation
	 */
	@PostMapping(value = "insertPromptConfig", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public GPromptConfig insertPromptConfig(@RequestBody GPromptConfig Config) throws GeboPersistenceException {
		return persistenceManager.insert(Config);
	}

	/**
	 * Updates an existing prompt configuration.
	 * 
	 * @param Config The prompt configuration to update
	 * @return The updated GPromptConfig object
	 * @throws GeboPersistenceException If there's an error during the database operation
	 */
	@PostMapping(value = "updatePromptConfig", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public GPromptConfig updatePromptConfig(@RequestBody GPromptConfig Config) throws GeboPersistenceException {
		return persistenceManager.update(Config);
	}

	/**
	 * Deletes a prompt configuration.
	 * 
	 * @param Config The prompt configuration to delete
	 * @throws GeboPersistenceException If there's an error during the database operation
	 */
	@PostMapping(value = "deletePromptConfig", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void deletePromptConfig(@RequestBody GPromptConfig Config) throws GeboPersistenceException {
		persistenceManager.delete(Config);
	}

	/**
	 * Retrieves all prompt configurations with pagination.
	 * 
	 * @param page The pagination parameters
	 * @return A page of GPromptConfig objects
	 * @throws GeboPersistenceException If there's an error during the database operation
	 */
	@PostMapping(value = "getAllPromptConfig", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Page<GPromptConfig> getAllPromptConfig(@RequestBody DataPage page) throws GeboPersistenceException {
		return persistenceManager.findAll(GPromptConfig.class, page.toPageable());
	}

	/**
	 * Parameter class for QBE (Query By Example) searches on prompt configurations.
	 * Contains pagination information and a filter object.
	 */
	public static class PromptConfigByQbeParam implements Serializable {
		public DataPage page = new DataPage();
		public GPromptConfig filter = new GPromptConfig();
	};

	/**
	 * Retrieves prompt configurations that match the given example with pagination.
	 * 
	 * @param param Contains pagination information and the filter example
	 * @return A page of GPromptConfig objects matching the criteria
	 * @throws GeboPersistenceException If there's an error during the database operation
	 */
	@PostMapping(value = "getAllPromptConfigByQbe", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Page<GPromptConfig> getAllPromptConfigByQbe(@RequestBody PromptConfigByQbeParam param)
			throws GeboPersistenceException {
		return persistenceManager.findAllByQbe(param.filter, param.page.toPageable());
	}
	
	/**
	 * Retrieves all unique prompt categories from existing configurations.
	 * 
	 * @return A sorted list of all unique prompt categories
	 * @throws GeboPersistenceException If there's an error during the database operation
	 */
	@GetMapping(value="getPromptCategories",produces = MediaType.APPLICATION_JSON_VALUE)
	public List<String> getPromptCategories() throws GeboPersistenceException {
		Stream<GPromptConfig> stream = persistenceManager.findAll(GPromptConfig.class).stream();
		final TreeMap<String, Boolean> cats=new TreeMap<String, Boolean>();
		stream.forEach(x->{
			if (x.getPromptCategory()!=null) {
				cats.put(x.getPromptCategory(), true);
			}
		});
		return new ArrayList<String>(cats.keySet());
	}
}