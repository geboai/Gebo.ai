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

import ai.gebo.fastsetup.model.ComponentLLMSStatus;
import ai.gebo.fastsetup.model.FastLLMSSetupData;
import ai.gebo.fastsetup.services.GeboFastLLMSSetupService;
import ai.gebo.model.OperationStatus;
import jakarta.validation.Valid;

/**
 * AI generated comments
 * 
 * REST controller managing the setup and status of the Gebo Fast LLMS.
 * Only accessible by admin users.
 */
@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/api/admin/GeboFastLLMSSetupController")
public class GeboFastLLMSSetupController {

    /**
     * Service to handle the main logic for the LLMS setup.
     */
    @Autowired
    GeboFastLLMSSetupService service;

    /**
     * Default constructor for GeboFastLLMSSetupController.
     */
    public GeboFastLLMSSetupController() {

    }

    /**
     * Endpoint to create and configure an LLMS setup.
     *
     * @param llmsSetupData The data required to setup LLMS, validated for correctness.
     * @return The status of the operation indicating success or failure.
     */
    @PostMapping(value = "createLLMSSetup", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public OperationStatus<Boolean> createLLMSSetup(@Valid @RequestBody FastLLMSSetupData llmsSetupData) {
        // Delegate to the service to handle the setup configuration logic.
        return service.configureLLMS(llmsSetupData);
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
}