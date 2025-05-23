/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.systems.abstraction.layer.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.knowledgebase.repositories.DocumentReferenceRepository;
import ai.gebo.model.base.GObjectRef;
import ai.gebo.systems.abstraction.layer.impl.repository.ContentHandshakeDataRepository;

/**
 * AI generated comments
 * Controller for handling content reset operations through admin API.
 */
@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("api/admin/ContentsResetController")
public class ContentsResetController {
    private final static Logger LOGGER = org.slf4j.LoggerFactory.getLogger(ContentsResetController.class);

    // Repository for accessing document references
    @Autowired
    DocumentReferenceRepository documentRepository;

    // Repository for managing content handshake data
    @Autowired
    ContentHandshakeDataRepository handshakeRepository;

    /**
     * Default constructor for ContentsResetController.
     */
    public ContentsResetController() {

    }

    /**
     * Request class for resetting content.
     */
    public static class ResetContentRequest {
        // Knowledge base code for the reset request
        public String knowledgeBaseCode = null;
        // Project code for the reset request
        public String projectCode = null;
        // Reference to the project endpoint involved in the reset
        public GObjectRef<GProjectEndpoint> projectEndpoint = null;

        @Override
        public String toString() {
            String out = "{";
            if (knowledgeBaseCode != null) {
                out+="knowledgeBaseCode:\""+knowledgeBaseCode+"\" ";
            }
            if (projectCode != null) {
                out+="projectCode:\""+projectCode+"\" ";
            }
            if (projectEndpoint != null) {
                out+="projectEndpoint:"+projectEndpoint+" ";
            }
            out += "}";
            return out;
        }
    }

    /**
     * Response class for resetting content.
     */
    public static class ResetContentResponse {
        // Number of entries reset
        public int resetEntries = 0;
        // Flag indicating if all entries were deleted
        public boolean deletedAll = false;

        @Override
        public String toString() {
            return "{resetEntries:"+resetEntries+" , deletedAll:" +deletedAll+"}";
        }
    }

    /**
     * Endpoint to reset content ingestion based on the provided request.
     *
     * @param request The request containing information for resetting content.
     * @return A response object containing the result of the reset operation.
     */
    @PostMapping(value = "resetContentsIngestion", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResetContentResponse resetContentsIngestion(@RequestBody ResetContentRequest request) {
        LOGGER.info("Begin resetContentsIngestion(" + request + ")");
        final ResetContentResponse r = new ResetContentResponse();

        // Check if any identifying information is provided in the request
        if (request.knowledgeBaseCode == null && request.projectCode == null && request.projectEndpoint == null) {
            // If none, delete all entries from both repositories
            handshakeRepository.deleteAll();
            documentRepository.deleteAll();
            r.deletedAll = true;
        } else {
            Stream<GDocumentReference> stream = null;
            // Determine the stream based on the available request data
            if (request.projectEndpoint != null) {
                stream = documentRepository.findByProjectEndpointReferenceClassNameAndProjectEndpointReferenceCode(
                        request.projectEndpoint.getClassName(), request.projectEndpoint.getCode());
            } else if (request.projectCode != null) {
                stream = documentRepository.findByParentProjectCode(request.projectCode);
            } else if (request.knowledgeBaseCode != null) {
                stream = documentRepository.findByRootKnowledgebaseCode(request.knowledgeBaseCode);
            }

            List<String> ids = new ArrayList<String>();

            // Process each document reference, collecting their IDs for reset
            stream.forEach(x -> {
                ids.add(x.getCode());
                r.resetEntries++;
                // Perform batch deletion every 100 entries
                if ((ids.size() % 100) == 0) {
                    handshakeRepository.deleteByContentCodeIn(ids);
                    ids.clear();
                }
            });

            // Delete any remaining entries
            if (!ids.isEmpty()) {
                handshakeRepository.deleteByContentCodeIn(ids);
            }

            // Remove document references based on the provided request data
            if (request.projectEndpoint != null) {
                documentRepository.deleteByProjectEndpointReferenceClassNameAndProjectEndpointReferenceCode(
                        request.projectEndpoint.getClassName(), request.projectEndpoint.getCode());
            } else if (request.projectCode != null) {
                documentRepository.deleteByParentProjectCode(request.projectCode);
            } else if (request.knowledgeBaseCode != null) {
                documentRepository.deleteByRootKnowledgebaseCode(request.knowledgeBaseCode);
            }
        }
        LOGGER.info("End resetContentsIngestion(" + request + ") =>" + r);
        return r;
    }
}