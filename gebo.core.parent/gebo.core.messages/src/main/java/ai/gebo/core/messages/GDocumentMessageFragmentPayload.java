/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.core.messages;

import java.util.ArrayList;
import java.util.List;

import org.springframework.ai.document.Document;

/**
 * AI generated comments
 * Represents a payload for message fragments that include documents.
 * Manages a list of Document objects and calculates their cumulative length.
 */
public class GDocumentMessageFragmentPayload extends GAbstractContentMessageFragmentPayload {

    // A list to store Document objects associated with the message fragment.
    private List<Document> documents = new ArrayList<Document>();
    
    // Stores the cumulative character length of all documents.
    private Long cumulativeLength = null;

    /**
     * Default constructor for GDocumentMessageFragmentPayload.
     */
    public GDocumentMessageFragmentPayload() {

    }

    /**
     * Retrieves the list of documents.
     * 
     * @return A list of Document objects.
     */
    public List<Document> getDocuments() {
        return documents;
    }

    /**
     * Sets the list of documents and calculates the cumulative length of their text.
     * 
     * @param documents A list of Document objects to be associated with the message fragment.
     */
    public void setDocuments(List<Document> documents) {
        if (documents != null) {
            // Calculate the cumulative length of the text in all documents.
            long length = 0l;
            for (Document d : documents) {
                if (d.getText() != null) {
                    length += d.getText().length() * 2;  // Each character accounts for 2 units in length calculation.
                }
            }
            this.cumulativeLength = length;
        }
        this.documents = documents;
    }

    /**
     * Retrieves the cumulative length of all documents' text.
     * 
     * @return The cumulative length as a Long.
     */
    public Long getCumulativeLength() {
        return cumulativeLength;
    }

    /**
     * Sets the cumulative length of all documents' text.
     * 
     * @param cumulativeLength The total length to be set for the documents' text.
     */
    public void setCumulativeLength(Long cumulativeLength) {
        this.cumulativeLength = cumulativeLength;
    }

}