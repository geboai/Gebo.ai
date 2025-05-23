/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.architecture.scheduling.services;

import java.util.Date;
import java.util.List;

import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.knlowledgebase.model.scheduling.ReindexingProgrammedTable;
import jakarta.validation.constraints.NotNull;

/**
 * Gebo.ai comment agent
 * 
 * Interface for managing scheduling services related to time management.
 */
public interface IGSchedulingTimeService {
    
    /**
     * Retrieves a list of display time values based on the supplied list of `ReindexingProgrammedTable`.
     * 
     * @param programmedTable a list of `ReindexingProgrammedTable` objects, cannot be null
     * @return a list of display time values as strings
     */
    public List<String> getDisplayTimeValues(@NotNull List<ReindexingProgrammedTable> programmedTable);

    /**
     * Manages the publish scheduling for the given project endpoint.
     * 
     * @param endpoint the project endpoint that needs scheduling management
     */
    public void managePublishScheduling(GProjectEndpoint endpoint);

    /**
     * Executes a scheduled tick which could be used to trigger periodic actions.
     */
    public void scheduleTick();
}