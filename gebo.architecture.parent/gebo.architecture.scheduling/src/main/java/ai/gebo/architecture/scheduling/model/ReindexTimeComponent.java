/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.architecture.scheduling.model;

/**
 * Gebo.ai comment agent
 * 
 * Enum representing different components of time that can be used for reindexing purposes.
 */
public enum ReindexTimeComponent {

    /** Represents the hour component of time. */
    HOUR("Hour"), 

    /** Represents the minutes component of time. */
    MINUTES("Minute"), 

    /** Represents the day of the week component of time. */
    DAY_OF_WEEK("Day of week"), 

    /** Represents the week of the month component of time. */
    WEEK_OF_MONTH("Week if month"), 

    /** Represents the date component of time. */
    DATE("Date");

    /**
     * Private constructor for the enum, initializing the label for each component.
     *
     * @param label A string label representing the component of time.
     */
    private ReindexTimeComponent(String label) {
        this.label = label;
    }

    /** The label associated with the time component. */
    private final String label;

    /**
     * Gets the label associated with the time component.
     *
     * @return The string label of the time component.
     */
    public String getLabel() {
        return label;
    }
}