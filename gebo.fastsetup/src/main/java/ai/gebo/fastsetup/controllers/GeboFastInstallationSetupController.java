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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ai.gebo.config.GeboConfig;
import ai.gebo.fastsetup.model.FastInstallationSetupData;
import ai.gebo.fastsetup.services.GeboFastInstallationSetupService;
import ai.gebo.model.OperationStatus;
import jakarta.validation.Valid;

/**
 * GeboFastInstallationSetupController is responsible for handling HTTP requests related to the Gebo fast installation setup.
 * It provides endpoints to get the installation status and create a setup.
 * AI generated comments
 */
@RestController
@RequestMapping("/public/GeboFastSetupController")
public class GeboFastInstallationSetupController {

	/** Service to handle fast installation setup logic */
	@Autowired
	GeboFastInstallationSetupService setupService;

	/** Configuration settings for Gebo */
	@Autowired
	GeboConfig geboConfig;

	/**
	 * Default constructor for GeboFastInstallationSetupController.
	 */
	public GeboFastInstallationSetupController() {

	}

	/**
	 * Handles GET requests to check the installation status.
	 * If the setup is not configured, it indicates installation is required.
	 * Otherwise, it retrieves the actual installation status from the service.
	 * 
	 * @return OperationStatus indicating if installation is needed (Boolean.TRUE) or actual status.
	 */
	@GetMapping(value = "getInstallationStatus", produces = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<Boolean> getInstallationStatus() {
		if (geboConfig.getSetup() == null || geboConfig.getSetup() == false) {
			return OperationStatus.of(Boolean.TRUE);
		} else {
			return setupService.getInstallationStatus();
		}

	}

	/**
	 * Handles POST requests to create a new setup.
	 * It validates the incoming data and verifies if the setup is enabled in the configuration.
	 * If the setup feature is not enabled, it throws an exception.
	 * 
	 * @param data FastInstallationSetupData containing the setup information
	 * @return OperationStatus indicating the success or failure of the creation process.
	 * @throws IllegalStateException if the setup feature is disabled.
	 */
	@PostMapping(value = "createSetup", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<Boolean> createSetup(@RequestBody @Valid FastInstallationSetupData data) {
		if (geboConfig.getSetup() == null || geboConfig.getSetup() == false) {
			throw new IllegalStateException(
					"Gebo.ai has ai.gebo.config.setup false or unset so fast setup is disabled");
		}
		return setupService.createSetup(data);
	}

}