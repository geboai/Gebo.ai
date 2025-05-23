/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.atlassian.confluence.handler;

/**
 * AI generated comments
 * 
 * Enumeration representing different versions of Atlassian Confluence.
 * This enum is used to distinguish between on-premise and cloud deployments
 * of Confluence for version-specific handling in the application.
 */
public enum ConfluenceVersion {
    /** Represents Confluence Server/Data Center version 7.x */
    ONPREMISE7X, 
    
    /** Represents Confluence Cloud version */
    CLOUD
}