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
import lombok.Data;

@Document
@Data
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
    @HashIndexed
    private String lastestJobId=null;
}