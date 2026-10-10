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
    /** The ids of the vectors of the document's contents, in document order. */
    @NotNull
    private List<String> vectorsId = new ArrayList<String>();
    /**
     * The ids of the vectors of the document's file name (see
     * {@link ai.gebo.model.EmbedType#FILE_NAME}); null when the document was vectorized
     * before they existed.
     */
    private List<String> fileNameVectorsId = null;
    /**
     * The ids of the vectors of the document's title (see
     * {@link ai.gebo.model.EmbedType#TITLE}), none when it has no title; null when the
     * document was vectorized before they existed.
     */
    private List<String> titleVectorsId = null;
    /**
     * The ids of the vectors of the document's author (see
     * {@link ai.gebo.model.EmbedType#AUTHOR}), none when it has no author; null when
     * the document was vectorized before they existed.
     */
    private List<String> authorVectorsId = null;
    /**
     * The document's title, as its contents tell it (the text of its title vector);
     * null when it has none, or was vectorized before it was kept. Kept here, and not
     * on the document reference, because each publication replaces the reference
     * with the one its content source gives, while this record changes only when the
     * document is vectorized again.
     */
    private String title = null;
    /**
     * The document's author, as its contents tell it (the text of its author
     * vector); null when it has none, or was vectorized before it was kept.
     */
    private String author = null;
    private Long fileSize = null;
    private Date modificationDate = null;
    private Date lastVectorizedDate = null;
    private Boolean deleted = null;
    @HashIndexed
    private String lastestJobId=null;

    /**
     * Every vector of the document in its vector store: its contents', its file
     * name's, its title's and its author's; what deleting the document deletes.
     */
    public List<String> allVectorsId() {
        final List<String> all = new ArrayList<String>();
        if (vectorsId != null) {
            all.addAll(vectorsId);
        }
        if (fileNameVectorsId != null) {
            all.addAll(fileNameVectorsId);
        }
        if (titleVectorsId != null) {
            all.addAll(titleVectorsId);
        }
        if (authorVectorsId != null) {
            all.addAll(authorVectorsId);
        }
        return all;
    }
}