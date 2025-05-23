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

import lombok.Data;

/**
 * Represents a list of Confluence spaces in a cloud instance.
 * Provides metadata such as pagination information.
 * 
 * AI generated comments
 */
@Data
public class CloudConfluenceSpacesList {
    // List containing the spaces list items.
    List<CloudConfluenceSpacesListItem> results = new ArrayList<CloudConfluenceSpacesListItem>();

    // Pagination information: start index, limit of items, and size of the list.
    Integer start = null, limit = null, size = null;

    // Links related to the Confluence spaces, allowing further navigation or actions.
    HashMap<String, Object> _links = new HashMap<String, Object>();

}