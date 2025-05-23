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

/**
 * Represents the results containing search keywords and corresponding result items.
 * Gebo.ai comment agent
 */
public class SearchKeywordResults {

    /**
     * Represents a single result item containing an ID, content code, and content.
     */
    public static class ResultItem {
        private String id = null; // Unique identifier for the result item
        private String contentCode = null; // Code associated with the content
        private String content = null; // Actual content of the result item

        /**
         * Gets the content of the result item.
         *
         * @return the content
         */
        public String getContent() {
            return content;
        }

        /**
         * Sets the content of the result item.
         *
         * @param content the content to set
         */
        public void setContent(String content) {
            this.content = content;
        }

        /**
         * Gets the content code associated with the result item.
         *
         * @return the content code
         */
        public String getContentCode() {
            return contentCode;
        }

        /**
         * Sets the content code for the result item.
         *
         * @param contentCode the content code to set
         */
        public void setContentCode(String contentCode) {
            this.contentCode = contentCode;
        }

        /**
         * Gets the unique identifier of the result item.
         *
         * @return the id
         */
        public String getId() {
            return id;
        }

        /**
         * Sets the unique identifier for the result item.
         *
         * @param id the id to set
         */
        public void setId(String id) {
            this.id = id;
        }
    }

    private List<ResultItem> results = new ArrayList<SearchKeywordResults.ResultItem>(); // List to hold result items

    /**
     * Gets the list of result items.
     *
     * @return the list of result items
     */
    public List<ResultItem> getResults() {
        return results;
    }

    /**
     * Sets the list of result items.
     *
     * @param results the list of result items to set
     */
    public void setResults(List<ResultItem> results) {
        this.results = results;
    }
}