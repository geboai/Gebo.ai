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

import lombok.Data;

/**
 * AI generated comments
 * Represents a list of Confluence spaces in an on-premise Confluence instance.
 * Utilizes Lombok's @Data annotation to automatically generate getters, setters, 
 * and other utility methods.
 */
@Data
public class OnPremiseConfluenceSpacesList {
    
    /** 
     * A list containing items that represent the individual spaces' metadata.
     */
    List<OnPremiseConfluenceSpacesListItem> results = new ArrayList<OnPremiseConfluenceSpacesListItem>();
    
    /**
     * The starting index of the list of spaces for pagination.
     */
    Integer start = null;

    /**
     * The maximum number of items that can be present in the list, used for pagination.
     */
    Integer limit = null;

    /**
     * The total number of spaces available.
     */
    Integer size = null;

    /**
     * A map containing link-related metadata, possibly for pagination or related resources.
     */
    HashMap<String, Object> _links = new HashMap<String, Object>();

}
