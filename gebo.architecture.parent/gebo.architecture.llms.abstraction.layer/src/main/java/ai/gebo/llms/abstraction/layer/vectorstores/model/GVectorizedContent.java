/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.abstraction.layer.vectorstores.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.HashIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.model.base.GObjectRef;
import jakarta.validation.constraints.NotNull;

@Document
public class GVectorizedContent {

    /**
     * Inner class to represent the ID of a vectorized content, which includes a document reference code 
     * and a vector store ID.
     * AI generated comments
     */
    public static class GVectorizedContentId {
        private String docReferenceCode = null;
        private String vectorStoreId = null;

        /**
         * Gets the document reference code.
         * @return the document reference code.
         */
        public String getDocReferenceCode() {
            return docReferenceCode;
        }

        /**
         * Sets the document reference code.
         * @param docReferenceCode the document reference code to set.
         */
        public void setDocReferenceCode(String docReferenceCode) {
            this.docReferenceCode = docReferenceCode;
        }

        /**
         * Gets the vector store ID.
         * @return the vector store ID.
         */
        public String getVectorStoreId() {
            return vectorStoreId;
        }

        /**
         * Sets the vector store ID.
         * @param vectorStoreId the vector store ID to set.
         */
        public void setVectorStoreId(String vectorStoreId) {
            this.vectorStoreId = vectorStoreId;
        }
    };

    @HashIndexed
    @NotNull
    private GObjectRef<GProjectEndpoint> projectEndpointReference = null;
    @HashIndexed
    private String parentProjectCode = null;
    @HashIndexed
    private String rootKnowledgebaseCode = null;
    @Id
    @NotNull
    private GVectorizedContentId id = null;
    private String hash = null;
    @NotNull
    private List<String> vectorsId = new ArrayList<String>();
    private Long fileSize = null;
    private Date modificationDate = null;
    private Date lastVectorizedDate = null;
    private Boolean deleted = null;

    /**
     * Default constructor.
     */
    public GVectorizedContent() {

    }

    /**
     * Gets the hash value of the vectorized content.
     * @return the hash value.
     */
    public String getHash() {
        return hash;
    }

    /**
     * Sets the hash value of the vectorized content.
     * @param hash the hash value to set.
     */
    public void setHash(String hash) {
        this.hash = hash;
    }

    /**
     * Gets the list of vector IDs.
     * @return the list of vector IDs.
     */
    public List<String> getVectorsId() {
        return vectorsId;
    }

    /**
     * Sets the list of vector IDs.
     * @param vectorsId the list of vector IDs to set.
     */
    public void setVectorsId(List<String> vectorsId) {
        this.vectorsId = vectorsId;
    }

    /**
     * Gets the ID of the vectorized content.
     * @return the vectorized content ID.
     */
    public GVectorizedContentId getId() {
        return id;
    }

    /**
     * Sets the ID of the vectorized content.
     * @param id the vectorized content ID to set.
     */
    public void setId(GVectorizedContentId id) {
        this.id = id;
    }

    /**
     * Gets the file size associated with the vectorized content.
     * @return the file size.
     */
    public Long getFileSize() {
        return fileSize;
    }

    /**
     * Sets the file size associated with the vectorized content.
     * @param fileSize the file size to set.
     */
    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    /**
     * Gets the last modification date of the content.
     * @return the modification date.
     */
    public Date getModificationDate() {
        return modificationDate;
    }

    /**
     * Sets the modification date of the content.
     * @param modificationDate the modification date to set.
     */
    public void setModificationDate(Date modificationDate) {
        this.modificationDate = modificationDate;
    }

    /**
     * Gets the deletion status of the content.
     * @return true if deleted, false otherwise.
     */
    public Boolean getDeleted() {
        return deleted;
    }

    /**
     * Sets the deletion status of the content.
     * @param deleted the deletion status to set.
     */
    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    /**
     * Gets the project endpoint reference.
     * @return the project endpoint reference.
     */
    public GObjectRef<GProjectEndpoint> getProjectEndpointReference() {
        return projectEndpointReference;
    }

    /**
     * Sets the project endpoint reference.
     * @param projectEndpointReference the project endpoint reference to set.
     */
    public void setProjectEndpointReference(GObjectRef<GProjectEndpoint> projectEndpointReference) {
        this.projectEndpointReference = projectEndpointReference;
    }

    /**
     * Gets the parent project code.
     * @return the parent project code.
     */
    public String getParentProjectCode() {
        return parentProjectCode;
    }

    /**
     * Sets the parent project code.
     * @param parentProjectCode the parent project code to set.
     */
    public void setParentProjectCode(String parentProjectCode) {
        this.parentProjectCode = parentProjectCode;
    }

    /**
     * Gets the root knowledgebase code.
     * @return the root knowledgebase code.
     */
    public String getRootKnowledgebaseCode() {
        return rootKnowledgebaseCode;
    }

    /**
     * Sets the root knowledgebase code.
     * @param rootKnowledgebaseCode the root knowledgebase code to set.
     */
    public void setRootKnowledgebaseCode(String rootKnowledgebaseCode) {
        this.rootKnowledgebaseCode = rootKnowledgebaseCode;
    }

    /**
     * Gets the last date when the content was vectorized.
     * @return the last vectorized date.
     */
    public Date getLastVectorizedDate() {
        return lastVectorizedDate;
    }

    /**
     * Sets the last date when the content was vectorized.
     * @param lastVectorizedDate the last vectorized date to set.
     */
    public void setLastVectorizedDate(Date lastVectorizedDate) {
        this.lastVectorizedDate = lastVectorizedDate;
    }
}