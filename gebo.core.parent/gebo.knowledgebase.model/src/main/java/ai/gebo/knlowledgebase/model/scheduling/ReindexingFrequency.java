/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.knlowledgebase.model.scheduling;

/**
 * AI generated comments
 * Enum representing the different frequencies at which reindexing operations can be scheduled.
 */
public enum ReindexingFrequency {
    /** Indicates a reindexing operation that occurs daily. */
    DAILY,
    
    /** Indicates a reindexing operation that occurs monthly. */
    MONTHLY,
    
    /** Indicates a reindexing operation that occurs weekly. */
    WEEKLY,
    
    /** Indicates a reindexing operation that occurs hourly. */
    HOURLY,
    
    /** Indicates a reindexing operation that occurs yearly. */
    YEARLY,
    
    /** Indicates a reindexing operation that occurs whenever changes are detected. */
    ON_CHANGES,
    
    /** Indicates a reindexing operation that occurs on specific dates. */
    DATES
}