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

import jakarta.validation.constraints.NotNull;

/**
 * AI generated comments
 * Represents a table for scheduled reindexing programs.
 * Contains the frequency and specific times for reindexing.
 */
public class ReindexingProgrammedTable {
    // The frequency at which reindexing occurs; must not be null
    private @NotNull ReindexingFrequency frequency = null;
    
    // A list of times at which reindexing should occur
    private List<ReindexingTime> times = null;

    /**
     * Retrieves the reindexing frequency.
     *
     * @return the frequency of reindexing
     */
    public ReindexingFrequency getFrequency() {
        return frequency;
    }

    /**
     * Sets the reindexing frequency.
     *
     * @param frequency the frequency to set for reindexing
     */
    public void setFrequency(ReindexingFrequency frequency) {
        this.frequency = frequency;
    }

    /**
     * Retrieves the list of reindexing times.
     *
     * @return the list of times for reindexing
     */
    public List<ReindexingTime> getTimes() {
        return times;
    }

    /**
     * Sets the list of reindexing times.
     *
     * @param times the list of times to set for reindexing
     */
    public void setTimes(List<ReindexingTime> times) {
        this.times = times;
    }
}