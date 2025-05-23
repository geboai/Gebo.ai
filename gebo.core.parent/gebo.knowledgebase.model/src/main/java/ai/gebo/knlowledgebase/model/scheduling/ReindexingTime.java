/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.knlowledgebase.model.scheduling;

import java.util.List;

/**
 * Represents the reindexing time with a created timestamp and a list of time components.
 * <p>
 * AI generated comments
 */
public class ReindexingTime {

    // Timestamp representing the creation time of the reindexing record.
    private Long createdTime = null;

    // List of time components associated with reindexing.
    private List<Long> timeComponent = null;

    /**
     * Retrieves the list of time components for reindexing.
     * @return a list of time components.
     */
    public List<Long> getTimeComponent() {
        return timeComponent;
    }

    /**
     * Sets the list of time components for reindexing.
     * @param timeComponent a list of time components to be set.
     */
    public void setTimeComponent(List<Long> timeComponent) {
        this.timeComponent = timeComponent;
    }

    /**
     * Retrieves the creation timestamp of the reindexing record.
     * @return the creation timestamp.
     */
    public Long getCreatedTime() {
        return createdTime;
    }

    /**
     * Sets the creation timestamp for the reindexing record.
     * @param createdTime the timestamp to be set as the creation time.
     */
    public void setCreatedTime(Long createdTime) {
        this.createdTime = createdTime;
    }
}