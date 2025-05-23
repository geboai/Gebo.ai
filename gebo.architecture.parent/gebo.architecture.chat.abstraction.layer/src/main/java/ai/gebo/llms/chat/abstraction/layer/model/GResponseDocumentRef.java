/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.chat.abstraction.layer.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.ai.document.Document;

import ai.gebo.llms.abstraction.layer.model.RagDocumentsCachedDaoResult;
import ai.gebo.model.DocumentMetaInfos;

/**
 * Represents a reference to a document along with its metadata and internal references.
 * Implements Serializable interface for object serialization.
 * AI generated comments
 */
public class GResponseDocumentRef implements Serializable {

    /**
     * Represents an internal reference to a document, including its ID and page.
     * Implements Serializable interface.
     */
    public static class DocInternalRef implements Serializable {
        private String id = null;  // ID of the document reference
        private String page = null;  // Page of the document reference

        /**
         * Gets the ID of the document reference.
         * @return the document ID
         */
        public String getId() {
            return id;
        }

        /**
         * Sets the ID of the document reference.
         * @param id the document ID to set
         */
        public void setId(String id) {
            this.id = id;
        }

        /**
         * Gets the page of the document reference.
         * @return the document page
         */
        public String getPage() {
            return page;
        }

        /**
         * Sets the page of the document reference.
         * @param page the document page to set
         */
        public void setPage(String page) {
            this.page = page;
        }
    }

    private String uuid = null;  // Unique identifier for the document
    private String documentCode = null;  // Code that identifies the document
    private String description = null;  // Description of the document
    private String contentType = null;  // Type of the document content
    private String extension = null;  // File extension of the document
    private String knowledgeBaseCode = null;  // Code associated with the knowledge base
    private String projectCode = null;  // Code associated with the project
    private String geboTreatAs = null;  // Treatment type of the document in the system
    private String geboFileTypeDescription = null;  // Description of the file type
    private String geboFileTypeId = null;  // File type identifier
    private String name = null;  // Name of the document
    private long NTokensRelevant = 0l;  // Number of relevant tokens in the document
    private long NTotalContentTokens = 0l;  // Total number of content tokens in the document
    private double loadPercentage = 0.0;  // Load percentage of tokens relevant to total content
    private long NBytesRelevant = 0l;  // Number of relevant bytes in the document
    private List<DocInternalRef> references = new ArrayList<GResponseDocumentRef.DocInternalRef>();  // List of document internal references

    /**
     * Retrieves a metadata value from a map using a constant key.
     * @param metadata the metadata map
     * @param constant the key to retrieve the metadata
     * @return the metadata value as a string, or null if not available
     */
    private String get(Map<String, Object> metadata, String constant) {
        return metadata != null && metadata.containsKey(constant) ? metadata.get(constant).toString() : null;
    }

    /**
     * Constructs a GResponseDocumentRef from a Document object.
     * Initializes document metadata fields.
     * @param document the Document object to reference
     */
    public GResponseDocumentRef(Document document) {
        Map<String, Object> metadata = document.getMetadata();
        this.documentCode = get(metadata, DocumentMetaInfos.CONTENT_CODE);
        this.description = get(metadata, DocumentMetaInfos.CONTENT_DESCRIPTION);
        this.contentType = get(metadata, DocumentMetaInfos.CONTENT_TYPE);
        this.extension = get(metadata, DocumentMetaInfos.CONTENT_EXTENSION);
        this.knowledgeBaseCode = get(metadata, DocumentMetaInfos.KNOWLEDGEBASE_CODE);
        this.projectCode = get(metadata, DocumentMetaInfos.PROJECT_CODE);
        this.geboTreatAs = get(metadata, DocumentMetaInfos.GEBO_FILE_TREAT_AS);
        this.geboFileTypeDescription = get(metadata, DocumentMetaInfos.GEBO_FILE_TYPE_DESCRIPTION);
        this.geboFileTypeId = get(metadata, DocumentMetaInfos.GEBO_FILE_TYPE_ID);
        this.name = get(metadata, DocumentMetaInfos.GEBO_FILE_NAME);
        this.uuid = document.getId();
    }

    /**
     * Default constructor for GResponseDocumentRef.
     */
    public GResponseDocumentRef() {

    }

    /**
     * Gets the unique identifier of the document.
     * @return the document UUID
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * Sets the unique identifier of the document.
     * @param uuid the document UUID to set
     */
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    /**
     * Gets the document code.
     * @return the document code
     */
    public String getDocumentCode() {
        return documentCode;
    }

    /**
     * Sets the document code.
     * @param documentCode the document code to set
     */
    public void setDocumentCode(String documentCode) {
        this.documentCode = documentCode;
    }

    /**
     * Gets the short code derived from the document code.
     * Extracts the substring after the last '/' character.
     * @return the short code or null if not available
     */
    public String getShortCode() {
        String shortCode = null;
        if (documentCode != null) {
            int idx = documentCode.lastIndexOf("/");
            if (idx < documentCode.length() - 1) {
                shortCode = documentCode.substring(idx + 1);
            } else
                shortCode = documentCode.substring(documentCode.lastIndexOf("/"));
        }
        return shortCode;
    }

    /**
     * Gets the document description.
     * @return the document description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the document description.
     * @param description the document description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the content type of the document.
     * @return the content type
     */
    public String getContentType() {
        return contentType;
    }

    /**
     * Sets the content type of the document.
     * @param contentType the content type to set
     */
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    /**
     * Gets the file extension of the document.
     * @return the file extension
     */
    public String getExtension() {
        return extension;
    }

    /**
     * Sets the file extension of the document.
     * @param extension the file extension to set
     */
    public void setExtension(String extension) {
        this.extension = extension;
    }

    /**
     * Gets the knowledge base code associated with the document.
     * @return the knowledge base code
     */
    public String getKnowledgeBaseCode() {
        return knowledgeBaseCode;
    }

    /**
     * Sets the knowledge base code associated with the document.
     * @param knowledgeBaseCode the knowledge base code to set
     */
    public void setKnowledgeBaseCode(String knowledgeBaseCode) {
        this.knowledgeBaseCode = knowledgeBaseCode;
    }

    /**
     * Gets the project code associated with the document.
     * @return the project code
     */
    public String getProjectCode() {
        return projectCode;
    }

    /**
     * Sets the project code associated with the document.
     * @param projectCode the project code to set
     */
    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    /**
     * Gets the treatment type of the document in the system.
     * @return the treatment type
     */
    public String getGeboTreatAs() {
        return geboTreatAs;
    }

    /**
     * Sets the treatment type of the document in the system.
     * @param geboTreatAs the treatment type to set
     */
    public void setGeboTreatAs(String geboTreatAs) {
        this.geboTreatAs = geboTreatAs;
    }

    /**
     * Gets the description of the file type for the document.
     * @return the file type description
     */
    public String getGeboFileTypeDescription() {
        return geboFileTypeDescription;
    }

    /**
     * Sets the description of the file type for the document.
     * @param geboFileTypeDescription the file type description to set
     */
    public void setGeboFileTypeDescription(String geboFileTypeDescription) {
        this.geboFileTypeDescription = geboFileTypeDescription;
    }

    /**
     * Gets the file type identifier for the document.
     * @return the file type identifier
     */
    public String getGeboFileTypeId() {
        return geboFileTypeId;
    }

    /**
     * Sets the file type identifier for the document.
     * @param geboFileTypeId the file type identifier to set
     */
    public void setGeboFileTypeId(String geboFileTypeId) {
        this.geboFileTypeId = geboFileTypeId;
    }

    /**
     * Gets the document name.
     * @return the document name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the document name.
     * @param name the document name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the number of relevant tokens in the document.
     * @return the number of relevant tokens
     */
    public long getNTokensRelevant() {
        return NTokensRelevant;
    }

    /**
     * Sets the number of relevant tokens in the document.
     * @param nTokensRelevant the number of relevant tokens to set
     */
    public void setNTokensRelevant(long nTokensRelevant) {
        NTokensRelevant = nTokensRelevant;
    }

    /**
     * Gets the number of relevant bytes in the document.
     * @return the number of relevant bytes
     */
    public long getNBytesRelevant() {
        return NBytesRelevant;
    }

    /**
     * Sets the number of relevant bytes in the document.
     * @param nBytesRelevant the number of relevant bytes to set
     */
    public void setNBytesRelevant(long nBytesRelevant) {
        NBytesRelevant = nBytesRelevant;
    }

    /**
     * Creates a list of GResponseDocumentRef objects from extracted documents.
     * Populates each document reference with token and internal reference information.
     * @param extractedDocuments the result containing extracted document data
     * @return a list of GResponseDocumentRef objects
     */
    public static List<GResponseDocumentRef> from(RagDocumentsCachedDaoResult extractedDocuments) {
        final List<GResponseDocumentRef> out = new ArrayList<GResponseDocumentRef>();
        extractedDocuments.getDocumentItems().forEach(x -> {
            if (!x.getFragments().isEmpty()) {
                GResponseDocumentRef documentRef = new GResponseDocumentRef(x.getFragments().get(0).getDocument());
                out.add(documentRef);
                documentRef.setNTokensRelevant(x.getNTokens());
                documentRef.setNTotalContentTokens(x.getTotalFileNTokens());
                if (documentRef.getNTotalContentTokens() > 0) {
                    double percent = 100.0 * ((double) x.getNTokens()) / ((double) x.getTotalFileNTokens());
                    if (percent > 100.0)
                        percent = 100.0;
                    documentRef.loadPercentage = percent;
                }
                x.getFragments().forEach(y -> {
                    DocInternalRef internalRef = new DocInternalRef();
                    internalRef.setId(y.getDocument().getId());
                    Object page = y.getDocument().getMetadata() != null
                            ? y.getDocument().getMetadata().get(DocumentMetaInfos.CONTENT_PAGE)
                            : null;
                    if (page != null) {
                        internalRef.setPage(page.toString());
                    }
                    documentRef.getReferences().add(internalRef);
                });
            }
        });
        return out;
    }

    /**
     * Gets the total number of content tokens in the document.
     * @return the total number of content tokens
     */
    public long getNTotalContentTokens() {
        return NTotalContentTokens;
    }

    /**
     * Sets the total number of content tokens in the document.
     * @param nTotalContentTokens the total number of content tokens to set
     */
    public void setNTotalContentTokens(long nTotalContentTokens) {
        NTotalContentTokens = nTotalContentTokens;
    }

    /**
     * Gets the load percentage of relevant tokens.
     * @return the load percentage
     */
    public double getLoadPercentage() {
        return loadPercentage;
    }

    /**
     * Sets the load percentage of relevant tokens.
     * @param loadPercentage the load percentage to set
     */
    public void setLoadPercentage(double loadPercentage) {
        this.loadPercentage = loadPercentage;
    }

    /**
     * Gets the list of document internal references.
     * @return the list of internal references
     */
    public List<DocInternalRef> getReferences() {
        return references;
    }

    /**
     * Sets the list of document internal references.
     * @param references the list of internal references to set
     */
    public void setReferences(List<DocInternalRef> references) {
        this.references = references;
    }
}