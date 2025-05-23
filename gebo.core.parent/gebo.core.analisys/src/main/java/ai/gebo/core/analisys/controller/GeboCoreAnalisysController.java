/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.core.analisys.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ai.gebo.core.analisys.model.GStatsHolder;
import ai.gebo.core.analisys.services.IGAnalisysDimensionsSource;

/**
 * AI generated comments
 * Controller class for handling requests related to the analysis of Gebo core dimensions.
 */
@RestController
@RequestMapping(value = "api/admin/GeboCoreAnalisysController")
public class GeboCoreAnalisysController {

    /**
     * The service used for interacting with analysis dimensions.
     */
    @Autowired
    IGAnalisysDimensionsSource source;

    /**
     * Default constructor for GeboCoreAnalisysController.
     */
    public GeboCoreAnalisysController() {

    }

    /**
     * Retrieves the top-level category of the knowledge base.
     *
     * @return a GStatsHolder containing statistics of the top-level knowledge base category.
     */
    @GetMapping(value = "getTopLevelKnowledgeBaseCategory", produces = MediaType.APPLICATION_JSON_VALUE)
    public GStatsHolder getTopLevelKnowledgeBaseCategory() {
        return source.topLevelKnowledgeBaseCategory();
    }

    /**
     * Performs a drill-down operation on a given GStatsHolder object.
     *
     * @param holder the GStatsHolder object to be drilled down.
     * @return a list of GStatsHolder objects representing drilled down statistics.
     */
    @PostMapping(value = "drillDown", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<GStatsHolder> drillDown(@RequestBody GStatsHolder holder) {
        return source.drillDown(holder);
    }

}