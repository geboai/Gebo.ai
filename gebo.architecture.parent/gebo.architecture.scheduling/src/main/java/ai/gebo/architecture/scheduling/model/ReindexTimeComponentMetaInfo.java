/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.architecture.scheduling.model;

import java.util.List;

/**
 * AI generated comments
 * Represents the metadata information for the time components used in scheduling.
 */
public class ReindexTimeComponentMetaInfo {

    /** List of all days of the week. */
    public static final List<String> DAY_OF_WEEK_LIST = List.of("Monday", "Tuesday", "Wednesday", "Thursday", "Friday",
            "Saturday", "Sunday");

    /** The time unit for this component. */
    final ReindexTimeComponent timeUnity;

    /** The list of options available for this time component. */
    final List<String> choosableOptions;

    /** The maximum value this time component can have. */
    final int maxValue;

    /**
     * Constructs a ReindexTimeComponentMetaInfo with the specified time component and its maximum value.
     *
     * @param t The time component.
     * @param m The maximum value for the time component.
     */
    private ReindexTimeComponentMetaInfo(ReindexTimeComponent t, int m) {
        this.maxValue = m;
        this.timeUnity = t;
        this.choosableOptions = null;
    }

    /**
     * Constructs a ReindexTimeComponentMetaInfo with the specified time component, its maximum value, and the choosable options.
     *
     * @param t The time component.
     * @param m The maximum value for the time component.
     * @param choosableOptions The list of options available for this time component.
     */
    private ReindexTimeComponentMetaInfo(ReindexTimeComponent t, int m, List<String> choosableOptions) {
        this.maxValue = m;
        this.timeUnity = t;
        this.choosableOptions = choosableOptions;
    }

    /**
     * Gets the time unit of this component.
     * 
     * @return The time unit.
     */
    public ReindexTimeComponent getTimeUnity() {
        return timeUnity;
    }

    /**
     * Gets the maximum value of this component.
     * 
     * @return The maximum value.
     */
    public int getMaxValue() {
        return maxValue;
    }

    /**
     * Gets the label of the time unit if available.
     * 
     * @return The label, or null if time unit is not set.
     */
    public String getLabel() {
        return timeUnity != null ? timeUnity.getLabel() : null;
    }

    /** Time component for minutes with a max value of 60. */
    public static final ReindexTimeComponentMetaInfo MINUTES_COMPONENT = new ReindexTimeComponentMetaInfo(
            ReindexTimeComponent.MINUTES, 60);
    
    /** Time component for hours with a max value of 24. */
    public static final ReindexTimeComponentMetaInfo HOUR_COMPONENT = new ReindexTimeComponentMetaInfo(
            ReindexTimeComponent.HOUR, 24);
    
    /** Time component for days of the week with specified list of days. */
    public static final ReindexTimeComponentMetaInfo DAY_OF_WEEK_COMPONENT = new ReindexTimeComponentMetaInfo(
            ReindexTimeComponent.DAY_OF_WEEK, 6, DAY_OF_WEEK_LIST);
    
    /** Time component for weeks of the month with a max value of 4. */
    public static final ReindexTimeComponentMetaInfo WEEK_OF_MONTH_COMPONENT = new ReindexTimeComponentMetaInfo(
            ReindexTimeComponent.WEEK_OF_MONTH, 4);
    
    /** Time component for date with unspecified maximum value. */
    public static final ReindexTimeComponentMetaInfo DATE_COMPONENT = new ReindexTimeComponentMetaInfo(
            ReindexTimeComponent.DATE, 0);

    /** List of all time component meta information instances. */
    public static final List<ReindexTimeComponentMetaInfo> ALL_COMPONENTS = List.of(DATE_COMPONENT,
            DAY_OF_WEEK_COMPONENT, WEEK_OF_MONTH_COMPONENT, HOUR_COMPONENT, MINUTES_COMPONENT);

    /**
     * Gets the list of options available for this time component.
     * 
     * @return The list of choosable options.
     */
    public List<String> getChoosableOptions() {
        return choosableOptions;
    }
}