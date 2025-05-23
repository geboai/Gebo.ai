/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.atlassian.jira.handler.impl.model;

/**
 * This enum represents the different types of nodes that can exist in a Jira path structure.
 * It's used to categorize and differentiate between various elements in the Jira hierarchy.
 * 
 * AI generated comments
 */
public enum JiraPathNodeType {
    /** Represents a Jira project node */
    PROJECT, 
    
    /** Represents a standard Jira ticket/issue node */
    TICKET, 
    
    /** Represents an attachment within a Jira ticket */
    ATTACHMENT, 
    
    /** Represents a comment within a Jira ticket */
    COMMENT, 
    
    /** Represents a container ticket that may hold other tickets */
    CONTAINER_TICKET
}