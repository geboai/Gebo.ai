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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.architecture.setup.model.ComponentSetupStatus;
import ai.gebo.fastsetup.services.GeboFastChatProfileSetupService;

/**
 * AI generated comments
 * Controller responsible for managing requests related to the setup status of Gebo Fast Chat Profiles.
 * Only accessible by users with the ADMIN role.
 */
@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/api/admin/GeboFastChatProfileStatusController")
public class GeboFastChatProfileStatusController {

	/**
	 * Service that handles the logic for retrieving chat profile setup statuses.
	 */
	@Autowired
	GeboFastChatProfileSetupService service;

	/**
	 * Endpoint to get the setup status of chat profiles.
	 * @return ComponentSetupStatus The current setup status of the chat profiles.
	 * @throws GeboPersistenceException If there is an issue with data persistence.
	 */
	@GetMapping(value = "getChatProfilesSetupStatus", produces = MediaType.APPLICATION_JSON_VALUE)
	public ComponentSetupStatus getChatProfilesSetupStatus() throws GeboPersistenceException {
		// Delegates to the service to get the chat profile setup status
		return service.getChatProfilesSetupStatus();
	}
}