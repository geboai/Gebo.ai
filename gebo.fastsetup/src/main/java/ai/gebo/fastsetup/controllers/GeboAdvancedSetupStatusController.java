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

import ai.gebo.fastsetup.model.ComponentSetupStatus;
import ai.gebo.fastsetup.services.GeboAdvancedSetupStatusService;

/**
 * AI generated comments
 * Controller for managing advanced setup status of components within the Gebo framework.
 * This class provides RESTful endpoints to retrieve the setup status of various components.
 */
@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/api/admin/GeboAdvancedSetupStatusController")
public class GeboAdvancedSetupStatusController {

    // Service for retrieving the status of component setups.
    @Autowired
    GeboAdvancedSetupStatusService service;

    /**
     * Default constructor for GeboAdvancedSetupStatusController.
     */
    public GeboAdvancedSetupStatusController() {
    }

    /**
     * Retrieves the setup status of the first knowledge base.
     * 
     * @return ComponentSetupStatus object indicating the setup status of the first knowledge base.
     */
    @GetMapping(value = "getFirstKnowledgeBaseSetupStatus", produces = MediaType.APPLICATION_JSON_VALUE)
    public ComponentSetupStatus getFirstKnowledgeBaseSetupStatus() {
        return service.getFirstKnowledgeBaseSetupStatus();
    }

    /**
     * Retrieves the setup status of the minimal contents.
     * 
     * @return ComponentSetupStatus object indicating the setup status of the minimal contents.
     */
    @GetMapping(value = "getMinimalContentsSetupStatus", produces = MediaType.APPLICATION_JSON_VALUE)
    public ComponentSetupStatus getMinimalContentsSetupStatus() {
        return service.getMinimalContentsSetupStatus();
    }
}