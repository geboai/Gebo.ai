/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.abstraction.layer.functions.model;

import java.util.List;

import org.springframework.ai.document.Document;

/**
 * Gebo.ai comment agent
 * 
 * Represents a response from a semantic search operation. This class encapsulates
 * a list of {@link Document} objects that match the search criteria.
 */
public class SemanticSearchResponse {

    // A list of Document objects representing the results of the semantic search.
    // Initialized to an empty list.
    List<Document> documents = List.of();

    /**
     * Retrieves the list of documents from the semantic search response.
     * 
     * @return A list of {@link Document} objects.
     */
    public List<Document> getDocuments() {
        return documents;
    }

    /**
     * Sets the list of documents in the semantic search response.
     * 
     * @param documents A list of {@link Document} objects to be set.
     */
    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }
}