/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.abstraction.layer.model;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import org.springframework.ai.document.Document;
import org.springframework.ai.tokenizer.JTokkitTokenCountEstimator;
import org.springframework.ai.tokenizer.TokenCountEstimator;

import ai.gebo.model.base.GBaseObject;

/**
 * RagDocumentCacheItem class represents a cache item for a document in a retrieval-augmented generation system.
 * It extends GBaseObject and is annotated for MongoDB document storage.
 * It stores the document's text, token size, byte size, and last modification time.
 * AI generated comments
 */
@org.springframework.data.mongodb.core.mapping.Document
public class RagDocumentCacheItem extends GBaseObject {
    // Stores the actual textual content of the document
    private String text = null;
	
    // Stores the estimated count of tokens in the text
    private Long tokenSize = null;
    
    // Stores the size of the document in bytes
    private Long bytesSize = null;
    
    // Stores the timestamp of when the document was last modified
    private Long lastModified = null;

    // Metadata associated with the document
    private Map<String, Object> metaData = new HashMap<String, Object>();

    /**
     * Default constructor for RagDocumentCacheItem.
     */
    public RagDocumentCacheItem() {

    }

    /**
     * Gets the text of the document.
     *
     * @return the text of the document
     */
    public String getText() {
        return text;
    }

    /**
     * Sets the text of the document.
     *
     * @param text the text of the document to set
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Gets the metadata map associated with the document.
     *
     * @return the metadata map
     */
    public Map<String, Object> getMetaData() {
        return metaData;
    }

    /**
     * Sets the metadata map associated with the document.
     *
     * @param metaData the metadata map to set
     */
    public void setMetaData(Map<String, Object> metaData) {
        this.metaData = metaData;
    }

    /**
     * Gets the token size of the document text.
     *
     * @return the token size
     */
    public Long getTokenSize() {
        return tokenSize;
    }

    /**
     * Sets the token size for the document text.
     *
     * @param tokenSize the token size to set
     */
    public void setTokenSize(Long tokenSize) {
        this.tokenSize = tokenSize;
    }

    /**
     * Gets the byte size of the document text.
     *
     * @return the byte size
     */
    public Long getBytesSize() {
        return bytesSize;
    }

    /**
     * Sets the byte size for the document text.
     *
     * @param bytesSize the byte size to set
     */
    public void setBytesSize(Long bytesSize) {
        this.bytesSize = bytesSize;
    }

    /**
     * Gets the last modified timestamp of the document.
     *
     * @return the last modified timestamp
     */
    public Long getLastModified() {
        return lastModified;
    }

    /**
     * Sets the last modified timestamp for the document.
     *
     * @param lastModified the timestamp to set
     */
    public void setLastModified(Long lastModified) {
        this.lastModified = lastModified;
    }

    /**
     * Factory method to create a RagDocumentCacheItem from a stream of Document objects.
     * Aggregates the text from each Document and calculates token and byte sizes.
     *
     * @param documents a stream of Document objects
     * @return a populated RagDocumentCacheItem instance
     */
    public static RagDocumentCacheItem of(Stream<Document> documents) {
        final RagDocumentCacheItem item = new RagDocumentCacheItem();
        final StringBuffer buffer = new StringBuffer();
        documents.forEach(x -> {
            if (item.metaData.isEmpty()) {
                item.metaData.putAll(x.getMetadata()); // Populates metadata if not already populated
            }
            String text = x.getText();
            if (text != null) {
                buffer.append(text); // Appends text to the buffer
                buffer.append("\n");
            }
        });
        item.text = buffer.toString();
        TokenCountEstimator estimator = new JTokkitTokenCountEstimator();
        int tokenCount = estimator.estimate(item.text); // Estimates the token count
        item.tokenSize = Long.valueOf(tokenCount);
        item.bytesSize = Long.valueOf(item.text.length() * 2); // Estimates the byte size assuming 2 bytes per character
        return item;
    }
}