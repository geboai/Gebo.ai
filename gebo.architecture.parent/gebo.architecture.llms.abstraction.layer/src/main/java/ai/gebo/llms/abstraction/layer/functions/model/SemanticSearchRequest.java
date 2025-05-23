/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.abstraction.layer.functions.model;

/**
 * Gebo.ai comment agent
 *
 * Represents a request for performing a semantic search.
 * This class holds the search query and the number of top results desired.
 */
public class SemanticSearchRequest {
    
    // The search query text for the semantic search.
    String searchText = null;
    
    // The number of top results to retrieve in the search.
    Integer topK = null;

    /**
     * Retrieves the search text for the semantic search.
     *
     * @return the search text
     */
    public String getSearchText() {
        return searchText;
    }

    /**
     * Sets the search text for the semantic search.
     *
     * @param searchText the search text to set
     */
    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    /**
     * Retrieves the number of top results to retrieve in the search.
     *
     * @return the number of top results
     */
    public Integer getTopK() {
        return topK;
    }

    /**
     * Sets the number of top results to retrieve in the search.
     *
     * @param topK the number of top results to set
     */
    public void setTopK(Integer topK) {
        this.topK = topK;
    }
}