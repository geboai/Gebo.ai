/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.knlowledgebase.model.contents;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

import ai.gebo.model.base.GBaseObject;

/**
 * AI generated comments
 * Represents a snapshot of a document reference in the system, storing basic metadata about the document.
 */
@Document
public class GDocumentReferenceSnapshot extends GBaseObject {
    // The count of tokens in the document
    private Integer tokensCount = null;
    // The hash code of the document
    private String hash = null;
    // The size of the document file in bytes
    private Long fileSize = null;
    // The date the document was last modified
    private Date modifiedDate = null;
    // Flag indicating whether the document is deleted
    private Boolean deleted = null;

    /**
     * Default constructor for GDocumentReferenceSnapshot.
     */
    public GDocumentReferenceSnapshot() {

    }

    /**
     * Gets the count of tokens.
     * 
     * @return the tokens count
     */
    public Integer getTokensCount() {
        return tokensCount;
    }

    /**
     * Sets the count of tokens.
     * 
     * @param tokensCount
     *            the new tokens count
     */
    public void setTokensCount(Integer tokensCount) {
        this.tokensCount = tokensCount;
    }

    /**
     * Gets the hash code.
     * 
     * @return the hash
     */
    public String getHash() {
        return hash;
    }

    /**
     * Sets the hash code.
     * 
     * @param hash
     *            the new hash
     */
    public void setHash(String hash) {
        this.hash = hash;
    }

    /**
     * Gets the file size.
     * 
     * @return the file size in bytes
     */
    public Long getFileSize() {
        return fileSize;
    }

    /**
     * Sets the file size.
     * 
     * @param fileSize
     *            the new file size in bytes
     */
    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    /**
     * Gets the modified date.
     * 
     * @return the date the document was last modified
     */
    public Date getModifiedDate() {
        return modifiedDate;
    }

    /**
     * Sets the modified date.
     * 
     * @param modifiedDate
     *            the new modified date
     */
    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    /**
     * Gets the deleted status.
     * 
     * @return true if the document is marked as deleted, otherwise false
     */
    public Boolean getDeleted() {
        return deleted;
    }

    /**
     * Sets the deleted status.
     * 
     * @param deleted
     *            the new deleted status
     */
    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

}