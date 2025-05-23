/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.atlassian.confluence.cloud.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Data;

/**
 * AI generated comments
 * Represents a list of content items in Cloud Confluence.
 */
@Data
public class CloudConfluenceContentsList {

    /** 
     * A list to hold results of content items fetched from Cloud Confluence. 
     */
    private List<CloudConfluenceContentItem> results = new ArrayList<CloudConfluenceContentItem>();
    
    /** 
     * Pagination starting index.
     */
    private Integer start = null, 
    
    /** 
     * Maximum number of content items to be returned. 
     */
    limit = null, 
    
    /** 
     * Total size of the list. 
     */
    size = null;
    
    /** 
     * A map holding hyperlinks related to the content list, such as self or next links for pagination. 
     */
    private Map<String, Object> _links = new HashMap<String, Object>();

}