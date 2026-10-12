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
     * Resets the ingestion of the contents of a knowledge base, a project or an
     * endpoint (all contents when none is given): their ingestion
     * acknowledgements are deleted, so the next ingestion reads, chunks and
     * indexes every document again. The document references are kept: a
     * document's uniqueId lives only on its stored reference (see
     * VirtualFilesystemUniqueIds), and the next ingestion gives it back to the
     * document found.
     *
     * @param request the contents whose ingestion is reset
     * @return how many documents were reset, or that all were
     */
    @PostMapping(value = "resetContentsIngestion", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResetContentResponse resetContentsIngestion(@RequestBody ResetContentRequest request) {
        LOGGER.info("Begin resetContentsIngestion(" + request + ")");
        final ResetContentResponse r = new ResetContentResponse();

        if (request.knowledgeBaseCode == null && request.projectCode == null && request.projectEndpoint == null) {
            // no scope: every acknowledgement
            handshakeRepository.deleteAll();
            r.deletedAll = true;
        } else {
            final List<String> codes = new ArrayList<String>();
            try (Stream<GDocumentReference> stream = scope(request)) {
                stream.forEach(x -> {
                    codes.add(x.getCode());
                    r.resetEntries++;
                    // deleted 100 documents at a time
                    if (codes.size() == 100) {
                        deleteAcknowledgements(codes);
                        codes.clear();
                    }
                });
            }
            if (!codes.isEmpty()) {
                deleteAcknowledgements(codes);
            }
        }
        LOGGER.info("End resetContentsIngestion(" + request + ") =>" + r);
        return r;
    }

    /** The document references of the request's scope, the narrowest given. */
    private Stream<GDocumentReference> scope(ResetContentRequest request) {
        if (request.projectEndpoint != null) {
            return documentRepository.findByProjectEndpointReferenceClassNameAndProjectEndpointReferenceCode(
                    request.projectEndpoint.getClassName(), request.projectEndpoint.getCode());
        } else if (request.projectCode != null) {
            return documentRepository.findByParentProjectCode(request.projectCode);
        }
        return documentRepository.findByRootKnowledgebaseCode(request.knowledgeBaseCode);
    }

    private void deleteAcknowledgements(List<String> codes) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Deleting the ingestion acknowledgements of " + codes.size() + " document(s)");
        }
        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace("Ingestion acknowledgements deleted of: " + codes);
        }
        handshakeRepository.deleteByContentCodeIn(new ArrayList<String>(codes));
    }
}