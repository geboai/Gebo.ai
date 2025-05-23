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

import ai.gebo.fastsetup.model.ComponentEnabledStatus;
import ai.gebo.fastsetup.model.FastWorkDirectorySetupData;
import ai.gebo.fastsetup.model.WorkFolderSetupStatus;
import ai.gebo.fastsetup.services.GeboFastWorkFolderSetupService;
import ai.gebo.model.OperationStatus;
import jakarta.validation.Valid;

/**
 * Controller for handling API requests related to the setup of a fast work directory.
 * Accessible only to users with the ADMIN role.
 * AI generated comments
 */
@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/api/admin/GeboFastWorkFolderSetupController")
public class GeboFastWorkFolderSetupController {

	/**
	 * Service layer for fast work folder setup operations.
	 */
	@Autowired
	GeboFastWorkFolderSetupService service;

	/**
	 * Constructor for GeboFastWorkFolderSetupController.
	 */
	public GeboFastWorkFolderSetupController() {

	}

	/**
	 * API endpoint to check if the work directory setup is enabled.
	 * 
	 * @return ComponentEnabledStatus indicating whether the setup is enabled.
	 */
	@GetMapping(value = "getWorkDirectorySetupEnabled", produces = MediaType.APPLICATION_JSON_VALUE)
	public ComponentEnabledStatus getWorkDirectorySetupEnabled() {
		return service.getWorkDirectorySetupEnabled();
	}

	/**
	 * API endpoint to retrieve the current status of the work directory setup.
	 * 
	 * @return WorkFolderSetupStatus representing the current setup status.
	 */
	@GetMapping(value = "getWorkDirectorySetupStatus", produces = MediaType.APPLICATION_JSON_VALUE)
	public WorkFolderSetupStatus getWorkDirectorySetupStatus() {
		return service.getWorkDirectorySetupStatus();
	}

	/**
	 * API endpoint to configure the work directory based on the provided setup data.
	 * 
	 * @param data FastWorkDirectorySetupData containing the configuration details.
	 * @return OperationStatus containing the results of the configuration operation,
	 *         including the setup status.
	 */
	@PostMapping(value = "configureWorkDirectory", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<WorkFolderSetupStatus> configureWorkDirectory(@RequestBody @Valid FastWorkDirectorySetupData data) {
		return service.configureWorkDirectory(data);
	}
}