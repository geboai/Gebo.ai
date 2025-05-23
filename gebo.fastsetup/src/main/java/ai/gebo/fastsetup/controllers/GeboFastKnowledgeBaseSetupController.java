/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.fastsetup.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.fastsetup.model.GeboKnowledgeBaseSetupStatus;
import ai.gebo.fastsetup.services.GeboFastKnowledgeBaseSetupStatusService;
import ai.gebo.fastsetup.services.GeboFastKnowledgeBaseSetupStatusService.GeboContentProcessRow;

/**
 * Controller for handling requests related to the fast setup status of the Gebo Knowledge Base.
 * AI generated comments
 */
@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/api/admin/GeboFastKnowledgeBaseSetupController")
public class GeboFastKnowledgeBaseSetupController {
    
    // Service to handle Gebo Knowledge Base setup status operations
    @Autowired 
    GeboFastKnowledgeBaseSetupStatusService service;

    /**
     * Default constructor.
     */
    public GeboFastKnowledgeBaseSetupController() {

    }

    /**
     * Endpoint to get the complete status of the Knowledge Base setup.
     * 
     * @return GeboKnowledgeBaseSetupStatus object containing the setup status.
     * @throws GeboPersistenceException if there is an issue with accessing persistence layer.
     */
    @GetMapping(value = "getCompleteKnowledgeBaseSetupStatus", produces = MediaType.APPLICATION_JSON_VALUE)
    public GeboKnowledgeBaseSetupStatus getCompleteKnowledgeBaseSetupStatus() throws GeboPersistenceException {
        return service.getCompleteKnowledgeBaseSetupStatus();
    }

    /**
     * Endpoint to get rows that describe the content processing state.
     * 
     * @return List of GeboContentProcessRow objects.
     * @throws GeboPersistenceException if there is an issue with accessing persistence layer.
     */
    @GetMapping(value = "getContentProcessRows", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<GeboContentProcessRow> getContentProcessRows() throws GeboPersistenceException {
        return service.getContentProcessRows();
    }
}