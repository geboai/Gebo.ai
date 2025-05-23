/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.chat.abstraction.layer.config;

/**
 * Gebo.ai comment agent
 * 
 * The ContextWindowLengthRangeSettings class is used for configuring 
 * various percentage limits and strategies related to the context 
 * window's length for a chat application. It allows setting minimum 
 * context window length, limits on documents and history percentage, 
 * as well as specifying a history strategy.
 */
public class ContextWindowLengthRangeSettings {

    /**
     * Default constructor initializing the context window length
     * range settings with default values.
     */
    public ContextWindowLengthRangeSettings() {

    }

    /**
     * Parameterized constructor for ContextWindowLengthRangeSettings.
     *
     * @param minimumContextWindowLength Minimum length for the context window.
     * @param documentsLimitPercent Maximum percentage limit for documents in the context.
     * @param historyLimitPercent Maximum percentage limit for history in the context.
     * @param minimumToolAvailablePercent Minimum percentage of tool availability required.
     * @param historyStrategy Strategy to use for managing history.
     */
    public ContextWindowLengthRangeSettings(long minimumContextWindowLength, double documentsLimitPercent,
                                            double historyLimitPercent, double minimumToolAvailablePercent, HistoryStrategy historyStrategy) {
        this.minimumContextWindowLength = minimumContextWindowLength;
        this.minimumToolAvailablePercent = minimumToolAvailablePercent;
        this.documentsLimitPercent = documentsLimitPercent;
        this.historyLimitPercent = historyLimitPercent;
        this.historyStrategy = historyStrategy;
    }

    /** Minimum length for the context window. */
    public long minimumContextWindowLength = 0;

    /** Minimum percentage of tool availability required. */
    public double minimumToolAvailablePercent = 0.0;

    /** Maximum percentage limit for documents in the context. */
    public double documentsLimitPercent = 0.0;

    /** Maximum percentage limit for history in the context. */
    public double historyLimitPercent = 0.0;

    /** Strategy to use for managing history. */
    public HistoryStrategy historyStrategy = HistoryStrategy.SHORTENQUEUE;
}