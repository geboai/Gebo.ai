/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.system.ingestion.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ai.gebo.system.ingestion.IGIngestionHandlerConfigDao;
import ai.gebo.system.ingestion.model.IngestionFileType;
import ai.gebo.system.ingestion.model.IngestionHandlerConfig;

/**
 * AI generated comments
 * 
 * REST Controller that provides endpoints for retrieving ingestion file types and handler configurations.
 * This controller manages access to ingestion-related data with appropriate role-based security.
 */
@RestController
@RequestMapping("api/users/IngestionFileTypesLibraryController")
public class IngestionFileTypesLibraryController {
	/**
	 * Data access object for ingestion handler configurations
	 */
	@Autowired
	IGIngestionHandlerConfigDao configsDao;

	/**
	 * Default constructor for IngestionFileTypesLibraryController
	 */
	public IngestionFileTypesLibraryController() {

	}

	/**
	 * Retrieves all ingestion reading modules/handler configurations.
	 * This endpoint is restricted to users with ADMIN role.
	 * 
	 * @return List of all available ingestion handler configurations
	 */
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping(value = "getIngestionReadingModules", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<IngestionHandlerConfig> getIngestionReadingModules() {
		return configsDao.getConfigurations();
	}

	/**
	 * Finds and returns an ingestion file type based on the provided file extension.
	 * This endpoint is accessible by users with ADMIN, USER, or APPLICATION roles.
	 * 
	 * @param extension The file extension to search for
	 * @return The matching IngestionFileType or null if no match is found
	 */
	@PreAuthorize("hasAnyRole('ADMIN','USER','APPLICATION')")
	@GetMapping(value = "getIngestionFileTypeByExtension", produces = MediaType.APPLICATION_JSON_VALUE)
	public IngestionFileType getIngestionFileTypeByExtension(@RequestParam("extension") String extension) {
		for (IngestionHandlerConfig c : configsDao.getConfigurations()) {
			for (IngestionFileType ft : c.getFileTypes()) {
				if (ft.getExtensions().contains(extension.toLowerCase())) {
					return ft;
				}
			}
		}
		return null;
	}

	/**
	 * Retrieves all ingestion file types from all available handler configurations.
	 * This endpoint is accessible by users with ADMIN, USER, or APPLICATION roles.
	 * 
	 * @return A consolidated list of all available ingestion file types
	 */
	@PreAuthorize("hasAnyRole('ADMIN','USER','APPLICATION')")
	@GetMapping(value = "getAllFileTypes", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<IngestionFileType> getAllFileTypes() {
		Stream<List<IngestionFileType>> allTypes = configsDao.getConfigurations().stream().map(x -> {
			return x.getFileTypes();
		});
		final List<IngestionFileType> all = new ArrayList<IngestionFileType>();
		allTypes.forEach(x -> {
			all.addAll(x);
		});
		return all;

	}
}