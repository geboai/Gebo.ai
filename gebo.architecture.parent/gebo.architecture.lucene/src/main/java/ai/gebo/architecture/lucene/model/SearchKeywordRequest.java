/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.architecture.lucene.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.validation.constraints.NotNull;

/**
 * Gebo.ai comment agent
 * Represents a search keyword request, consisting of an operator
 * and a list of keywords to be used in a search query.
 */
public class SearchKeywordRequest {

    /**
     * Enum representing the possible operators for combining search keywords.
     */
    public static enum SearchKeywordRequestOperator {
        OR, AND
    };

    // The operator used to combine the search keywords. Must not be null.
    private @NotNull SearchKeywordRequestOperator operator;
    
    // A list of keywords included in the search request.
    private List<String> keywords = new ArrayList<String>();

    /**
     * Returns the current operator used for keyword combination.
     * 
     * @return the operator of the search query
     */
    public SearchKeywordRequestOperator getOperator() {
        return operator;
    }

    /**
     * Sets the operator for combining keywords in the search query.
     * 
     * @param operator the operator to set
     */
    public void setOperator(SearchKeywordRequestOperator operator) {
        this.operator = operator;
    }

    /**
     * Retrieves the list of keywords in the search request.
     * 
     * @return a list of keywords
     */
    public List<String> getKeywords() {
        return keywords;
    }

    /**
     * Sets the list of keywords for the search request.
     * 
     * @param keywords a list of keywords to set
     */
    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }
}