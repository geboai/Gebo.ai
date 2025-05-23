/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.core.messages;

import java.util.Date;

import ai.gebo.application.messaging.model.GBaseMessagePayload;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;

/**
 * AI generated comments
 * Represents a payload for content embedding handshake messages.
 */
public class GContentEmbeddingHandshakePayload extends GBaseMessagePayload {
    
    // Unique code identifying the content
    private String contentCode = null;
    
    // Date when the content was last modified
    private Date modificationDate = null;
    
    // Date when the content was created
    private Date creationdDate = null;
    
    // Hash value of the content for integrity verification
    private String hash = null;
    
    // Size of the file in bytes
    private Long fileSize = null;
    
    // Flag indicating whether the content has been processed
    private Boolean processed = null;
    
    // Identifier for correlating with other payloads
    private String correlatedPayloadId = null;

    /**
     * Default constructor for GContentEmbeddingHandshakePayload.
     */
    public GContentEmbeddingHandshakePayload() {

    }

    /**
     * Constructs a GContentEmbeddingHandshakePayload with specified document reference and payload.
     *
     * @param ref     Reference to the document associated with this payload.
     * @param payload The content message fragment payload containing additional details.
     */
    private GContentEmbeddingHandshakePayload(GDocumentReference ref, GAbstractContentMessageFragmentPayload payload) {
        this.contentCode = ref.getCode();
        this.creationdDate = ref.getCreationDate();
        this.modificationDate = ref.getModificationDate();
        this.hash = payload.getHash();
        this.fileSize = ref.getFileSize();
        this.processed = true;
        this.correlatedPayloadId = payload.getPayloadId();
    }

    /**
     * Returns the content code.
     *
     * @return the content code.
     */
    public String getContentCode() {
        return contentCode;
    }

    /**
     * Sets the content code.
     *
     * @param contentCode the content code to set.
     */
    public void setContentCode(String contentCode) {
        this.contentCode = contentCode;
    }

    /**
     * Returns the modification date.
     *
     * @return the modification date.
     */
    public Date getModificationDate() {
        return modificationDate;
    }

    /**
     * Sets the modification date.
     *
     * @param modificationDate the modification date to set.
     */
    public void setModificationDate(Date modificationDate) {
        this.modificationDate = modificationDate;
    }

    /**
     * Returns the creation date.
     *
     * @return the creation date.
     */
    public Date getCreationdDate() {
        return creationdDate;
    }

    /**
     * Sets the creation date.
     *
     * @param creationdDate the creation date to set.
     */
    public void setCreationdDate(Date creationdDate) {
        this.creationdDate = creationdDate;
    }

    /**
     * Returns the hash value.
     *
     * @return the hash value.
     */
    public String getHash() {
        return hash;
    }

    /**
     * Sets the hash value.
     *
     * @param hash the hash value to set.
     */
    public void setHash(String hash) {
        this.hash = hash;
    }

    /**
     * Returns whether the content has been processed.
     *
     * @return true if processed, false otherwise.
     */
    public Boolean getProcessed() {
        return processed;
    }

    /**
     * Sets the processed status.
     *
     * @param processed the processed status to set.
     */
    public void setProcessed(Boolean processed) {
        this.processed = processed;
    }

    /**
     * Creates an acknowledgment payload using the provided content message fragment payload.
     *
     * @param payload The content message fragment payload.
     * @return An acknowledgment GContentEmbeddingHandshakePayload.
     */
    public static GContentEmbeddingHandshakePayload ack(GAbstractContentMessageFragmentPayload payload) {
        GDocumentReference reference = payload.getDocumentReference();
        return new GContentEmbeddingHandshakePayload(reference, payload);
    }
}