/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.atlassian.confluence.cloud.model;

import lombok.Data;

/**
 * AI generated comments
 * Represents an item in a list of Confluence cloud content.
 * This class contains metadata about a Confluence item, such as its ID, type, status, and more.
 */
@Data
public class CloudConfluenceListItem {
    
    /** The unique identifier of the Confluence item. */
    String id = null;
    
    /** The type of the Confluence item, e.g., page or blog. */
    String type = null;
    
    /** The status of the Confluence item, e.g., current or archived. */
    String status = null;
    
    /** The title of the Confluence item. */
    String title = null;
    
    /** The version information of the Confluence item. */
    CloudConfluenceVersion version = null;
    
    /** Links related to the Confluence item for navigation or additional actions. */
    CloudConfluenceContentLinks _links = null;  
    
}