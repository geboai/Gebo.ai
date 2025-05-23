/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.atlassian.confluence.onpremise.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Data;

/**
 * AI generated comments
 *
 * This class represents a list of Confluence content items specifically for 
 * an on-premise Confluence instance. It includes metadata for pagination 
 * and links for navigating the contents.
 */
@Data
public class OnPremiseConfluenceContentsList {

    /** 
     * A list holding content items retrieved from Confluence.
     */
    List<OnPremiseConfluenceContentItem> results = new ArrayList<OnPremiseConfluenceContentItem>();

    /** 
     * Pagination start index.
     */
    Integer start = null;

    /** 
     * Maximum number of items per page.
     */
    Integer limit = null;

    /** 
     * Total number of content items.
     */
    Integer size = null;

    /** 
     * Map containing various link relations for navigation purposes.
     */
    Map<String, Object> _links = new HashMap<String, Object>();
}