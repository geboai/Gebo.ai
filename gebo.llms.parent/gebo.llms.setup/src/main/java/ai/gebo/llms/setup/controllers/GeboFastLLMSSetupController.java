/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.llms.setup.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ai.gebo.crypting.services.GeboCryptSecretException;
import ai.gebo.llms.abstraction.layer.model.GBaseModelChoice;
import ai.gebo.llms.setup.config.LLMSVendorsSetupConfig;
import ai.gebo.llms.setup.model.ComponentLLMSStatus;
import ai.gebo.llms.setup.model.LLMApiKeyCreationData;
import ai.gebo.llms.setup.model.LLMCredentials;
import ai.gebo.llms.setup.model.LLMSSetupConfigurationData;
import ai.gebo.llms.setup.model.LLMSSetupConfigurationModificationData;
import ai.gebo.llms.setup.model.LLMSSetupModificationResult;
import ai.gebo.llms.setup.services.GeboLLMSSetupService;
import ai.gebo.model.OperationStatus;
import ai.gebo.secrets.model.SecretInfo;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/**
 * AI generated comments
 * 
 * REST controller managing the setup and status of the Gebo Fast LLMS. Only
 * accessible by admin users.
 */
@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/api/admin/GeboFastLLMSSetupController")
public class GeboFastLLMSSetupController {

	/**
	 * Service to handle the main logic for the LLMS setup.
	 */
	@Autowired
	GeboLLMSSetupService service;

	/**
	 * Default constructor for GeboFastLLMSSetupController.
	 */
	public GeboFastLLMSSetupController() {

	}

	

	/**
	 * Endpoint to retrieve the current status of the LLMS setup.
	 *
	 * @return The status of the LLMS components.
	 */
	@GetMapping(value = "getLLMSSetupStatus", produces = MediaType.APPLICATION_JSON_VALUE)
	public ComponentLLMSStatus getLLMSSetupStatus() {
		// Fetch and return the current setup status from the service.
		return service.getLLMSSetupStatus();
	}

	@GetMapping(value = "getLLMSSetupPresets", produces = MediaType.APPLICATION_JSON_VALUE)
	public LLMSVendorsSetupConfig getLLMSSetupPresets() {
		return service.getSetupPresets();
	}

	@GetMapping(value = "getActualLLMSConfiguration", produces = MediaType.APPLICATION_JSON_VALUE)
	public LLMSSetupConfigurationData getActualLLMSConfiguration() throws GeboCryptSecretException {
		return service.getActualConfiguration();
	}

	@PostMapping(value = "applyLLMSSetupModification", produces = MediaType.APPLICATION_JSON_VALUE,consumes=MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<LLMSSetupModificationResult> applyLLMSSetupModification(
			@RequestBody LLMSSetupConfigurationModificationData modification) {
		return service.applyModification(modification);
	}
	@PostMapping(value="createLLMCredentials", produces = MediaType.APPLICATION_JSON_VALUE,consumes=MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<SecretInfo> createLLMCredentials(@RequestBody @Valid @NotNull LLMApiKeyCreationData apiKeyData) throws GeboCryptSecretException {
		return service.createLLMCredentials(apiKeyData);
	}
	@PostMapping(value="verifyCredentialsAndDownloadModels", produces = MediaType.APPLICATION_JSON_VALUE,consumes=MediaType.APPLICATION_JSON_VALUE)
	public OperationStatus<List<GBaseModelChoice>> verifyCredentialsAndDownloadModels(@RequestBody @Valid @NotNull LLMCredentials credentials) throws GeboCryptSecretException {
		return service.verifyCredentialsAndDownloadModels(credentials);
	}
}