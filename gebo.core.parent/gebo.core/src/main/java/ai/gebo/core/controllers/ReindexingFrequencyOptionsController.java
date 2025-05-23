/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.core.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ai.gebo.architecture.scheduling.model.ReindexTimeStructureMetaInfo;
import ai.gebo.architecture.scheduling.services.IGReindexTimeStructureMetaInfoDao;
import ai.gebo.architecture.scheduling.services.IGSchedulingTimeService;
import ai.gebo.knlowledgebase.model.scheduling.ReindexingFrequency;
import ai.gebo.knlowledgebase.model.scheduling.ReindexingProgrammedTable;
import jakarta.validation.constraints.NotNull;

/**
 * This controller handles HTTP requests related to Reindexing Frequency Options.
 * AI generated comments
 */
@RestController
@RequestMapping("api/users/ReindexingFrequencyOptionsController")
public class ReindexingFrequencyOptionsController {

    // Service for scheduling time-related operations
    @Autowired
    IGSchedulingTimeService schedulingTimeService;

    // DAO for accessing reindex time structure meta information
    @Autowired
    IGReindexTimeStructureMetaInfoDao metaInfosDao;

    /**
     * Retrieves all time structure meta information configurations.
     * 
     * @return a list of {@link ReindexTimeStructureMetaInfo}
     */
    @GetMapping(value = "getAllTimeStructureMetaInfos", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ReindexTimeStructureMetaInfo> getAllTimeStructureMetaInfos() {
        return metaInfosDao.getConfigurations();
    }

    /**
     * Retrieves specific time structure meta information based on the given frequency.
     * 
     * @param frequency the {@link ReindexingFrequency} to search for
     * @return the {@link ReindexTimeStructureMetaInfo} for the specified frequency
     */
    @GetMapping(value = "getTimeStructureMetaInfo", produces = MediaType.APPLICATION_JSON_VALUE)
    public ReindexTimeStructureMetaInfo getTimeStructureMetaInfo(
            @NotNull @RequestParam("frequency") ReindexingFrequency frequency) {
        return metaInfosDao.findByCode(frequency.name());
    }

    /**
     * Displays the time values for the given list of reindexing programmed tables.
     * 
     * @param programmedTable a list of {@link ReindexingProgrammedTable}
     * @return a list of string representations of display time values
     */
    @PostMapping(value = "displayTimeValues", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<String> displayTimeValues(@RequestBody @NotNull List<ReindexingProgrammedTable> programmedTable) {
        return schedulingTimeService.getDisplayTimeValues(programmedTable);
    }
}