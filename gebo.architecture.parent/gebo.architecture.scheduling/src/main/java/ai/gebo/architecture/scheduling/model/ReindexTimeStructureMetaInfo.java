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

import ai.gebo.knlowledgebase.model.scheduling.ReindexingFrequency;

/**
 * AI generated comments
 * This class represents the meta information for reindexing time structures.
 * It defines various time organizations such as hourly, daily, weekly, monthly, and by specific dates.
 */
public class ReindexTimeStructureMetaInfo {
    // Frequency at which reindexing occurs
    private final ReindexingFrequency frequency;
    
    // Components of the period for reindexing
    private final ReindexTimeComponentMetaInfo periodComponents[];

    // Predefined structure for hourly organization
    public static final ReindexTimeStructureMetaInfo HOUR_ORGANIZATION = of(ReindexingFrequency.HOURLY,
            new ReindexTimeComponentMetaInfo[] { ReindexTimeComponentMetaInfo.MINUTES_COMPONENT });

    // Predefined structure for daily organization
    public static final ReindexTimeStructureMetaInfo DAY_ORGANIZATION = of(ReindexingFrequency.DAILY,
            new ReindexTimeComponentMetaInfo[] { ReindexTimeComponentMetaInfo.HOUR_COMPONENT,
                    ReindexTimeComponentMetaInfo.MINUTES_COMPONENT });

    // Predefined structure for weekly organization
    public static final ReindexTimeStructureMetaInfo WEEK_ORGANIZATION = of(ReindexingFrequency.WEEKLY,
            new ReindexTimeComponentMetaInfo[] { ReindexTimeComponentMetaInfo.DAY_OF_WEEK_COMPONENT,
                    ReindexTimeComponentMetaInfo.HOUR_COMPONENT, ReindexTimeComponentMetaInfo.MINUTES_COMPONENT });

    // Predefined structure for monthly organization
    public static final ReindexTimeStructureMetaInfo MONTH_ORGANIZATION = of(ReindexingFrequency.MONTHLY,
            new ReindexTimeComponentMetaInfo[] { ReindexTimeComponentMetaInfo.WEEK_OF_MONTH_COMPONENT,
                    ReindexTimeComponentMetaInfo.DAY_OF_WEEK_COMPONENT, ReindexTimeComponentMetaInfo.HOUR_COMPONENT,
                    ReindexTimeComponentMetaInfo.MINUTES_COMPONENT });

    // Predefined structure for specific date organization
    public static final ReindexTimeStructureMetaInfo DATE_ORGANIZATION = new ReindexTimeStructureMetaInfo(
            ReindexingFrequency.DATES, new ReindexTimeComponentMetaInfo[] { ReindexTimeComponentMetaInfo.DATE_COMPONENT });

    // List of all predefined reindexing time structures
    public static final List<ReindexTimeStructureMetaInfo> ALL = List.of(HOUR_ORGANIZATION, DAY_ORGANIZATION, WEEK_ORGANIZATION, MONTH_ORGANIZATION, DATE_ORGANIZATION); 

    /**
     * Constructs a ReindexTimeStructureMetaInfo with specified frequency and period components.
     *
     * @param frequency        the frequency of reindexing
     * @param periodComponents the components defining the period
     */
    private ReindexTimeStructureMetaInfo(ReindexingFrequency frequency, ReindexTimeComponentMetaInfo periodComponents[]) {
        this.frequency = frequency;
        this.periodComponents = periodComponents;
    }

    /**
     * Retrieves the frequency of reindexing.
     *
     * @return the reindexing frequency
     */
    public ReindexingFrequency getFrequency() {
        return frequency;
    }

    /**
     * Retrieves the components of the reindexing period.
     *
     * @return an array of period components
     */
    public ReindexTimeComponentMetaInfo[] getPeriodComponents() {
        return periodComponents;
    }

    /**
     * Creates a new instance of ReindexTimeStructureMetaInfo with specified frequency and components.
     *
     * @param f                 the frequency of reindexing
     * @param periodComponents  the components defining the period
     * @return a new ReindexTimeStructureMetaInfo instance
     */
    private static final ReindexTimeStructureMetaInfo of(ReindexingFrequency f,
            ReindexTimeComponentMetaInfo periodComponents[]) {
        return new ReindexTimeStructureMetaInfo(f, periodComponents);
    }
}