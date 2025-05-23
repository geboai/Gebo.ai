/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.document.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ai.gebo.model.virtualfs.VFilesystemReference;
import jakarta.el.MethodNotFoundException;

/**
 * Represents a document with various attributes and methods to manipulate its content.
 * Includes functionality to manage different types of media fragments.
 * 
 * AI generated comments
 */
public final class GeboDocument {
    // A static ObjectMapper instance used for JSON processing.
    private final static ObjectMapper mapper = new ObjectMapper();
    
    // Unique identifier of the document.
    private String id = null;
    
    // Identifier for the parent path in the virtual file system.
    private String parentPathId = null;
    
    // URL associated with the document.
    private String url = null;
    
    // Name of the document.
    private String name = null;
    
    // File extension of the document.
    private String extension = null;
    
    // MIME type of the document content.
    private String contentType = null;
    
    // Date when the document was created.
    private Date createdDate = null;
    
    // Date when the document was first processed.
    private Date firstProcessedDate = null;
    
    // Date when the document was last processed.
    private Date lastProcessedDate = null;
    
    // Size of the document in bytes.
    private Long size = null;
    
    // Reference to the parent folder in the virtual file system.
    private VFilesystemReference virtualParentFolderReference = null;
    
    // Reference to the file in the virtual file system.
    private VFilesystemReference virtualFileReference = null;
    
    // Date when the original content of the document was last modified.
    private Date originalContentModifiedDate = null;
    
    // Custom metadata associated with the document.
    private Map<String, Object> customMetaData = new HashMap<>();
    
    // Additional attributes associated with the document.
    private Map<String, Object> additionalAttributes = new HashMap<>();
    
    // List of text document fragments.
    private List<GeboTextDocumentFragment> texts = new ArrayList<>();
    
    // List of image document fragments.
    private List<GeboImageDocumentFragment> images = new ArrayList<>();
    
    // List of audio document fragments.
    private List<GeboAudioDocumentFragment> audios = new ArrayList<>();
    
    // List of video document fragments.
    private List<GeboVideoDocumentFragment> videos = new ArrayList<>();
    
    // List of media document fragments.
    private List<GeboMediaDocumentFragment> media = new ArrayList<>();
    
    // List of cataloging labels.
    private List<CatalogingLabel> catalogingLabel = new ArrayList<>();

    /**
     * Default constructor for GeboDocument class.
     */
    public GeboDocument() {
        // No initialization required
    }

    /**
     * Returns the list of text document fragments.
     * 
     * @return List of GeboTextDocumentFragment objects.
     */
    public List<GeboTextDocumentFragment> getTexts() {
        return texts;
    }

    /**
     * Sets the list of text document fragments.
     * 
     * @param texts The list of GeboTextDocumentFragment objects to set.
     */
    public void setTexts(List<GeboTextDocumentFragment> texts) {
        this.texts = texts;
    }

    /**
     * Returns the list of image document fragments.
     * 
     * @return List of GeboImageDocumentFragment objects.
     */
    public List<GeboImageDocumentFragment> getImages() {
        return images;
    }

    /**
     * Sets the list of image document fragments.
     * 
     * @param images The list of GeboImageDocumentFragment objects to set.
     */
    public void setImages(List<GeboImageDocumentFragment> images) {
        this.images = images;
    }

    /**
     * Returns the list of audio document fragments.
     * 
     * @return List of GeboAudioDocumentFragment objects.
     */
    public List<GeboAudioDocumentFragment> getAudios() {
        return audios;
    }

    /**
     * Sets the list of audio document fragments.
     * 
     * @param audios The list of GeboAudioDocumentFragment objects to set.
     */
    public void setAudios(List<GeboAudioDocumentFragment> audios) {
        this.audios = audios;
    }

    /**
     * Returns the list of video document fragments.
     * 
     * @return List of GeboVideoDocumentFragment objects.
     */
    public List<GeboVideoDocumentFragment> getVideos() {
        return videos;
    }

    /**
     * Sets the list of video document fragments.
     * 
     * @param videos The list of GeboVideoDocumentFragment objects to set.
     */
    public void setVideos(List<GeboVideoDocumentFragment> videos) {
        this.videos = videos;
    }

    /**
     * Returns the list of media document fragments.
     * 
     * @return List of GeboMediaDocumentFragment objects.
     */
    public List<GeboMediaDocumentFragment> getMedia() {
        return media;
    }

    /**
     * Sets the list of media document fragments.
     * 
     * @param media The list of GeboMediaDocumentFragment objects to set.
     */
    public void setMedia(List<GeboMediaDocumentFragment> media) {
        this.media = media;
    }

    /**
     * Returns the custom metadata map.
     * 
     * @return Map of custom metadata.
     */
    public Map<String, Object> getCustomMetaData() {
        return customMetaData;
    }

    /**
     * Sets the custom metadata map.
     * 
     * @param customMetaData Map of custom metadata to set.
     */
    public void setCustomMetaData(Map<String, Object> customMetaData) {
        this.customMetaData = customMetaData;
    }

    /**
     * Returns the list of cataloging labels.
     * 
     * @return List of CatalogingLabel objects.
     */
    public List<CatalogingLabel> getCatalogingLabel() {
        return catalogingLabel;
    }

    /**
     * Sets the list of cataloging labels.
     * 
     * @param catalogingLabel The list of CatalogingLabel objects to set.
     */
    public void setCatalogingLabel(List<CatalogingLabel> catalogingLabel) {
        this.catalogingLabel = catalogingLabel;
    }

    /**
     * Returns the unique identifier of the document.
     * 
     * @return Document ID.
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the document.
     * 
     * @param id Document ID to set.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Returns the URL associated with the document.
     * 
     * @return Document URL.
     */
    public String getUrl() {
        return url;
    }

    /**
     * Sets the URL associated with the document.
     * 
     * @param url Document URL to set.
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Returns the name of the document.
     * 
     * @return Document name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the document.
     * 
     * @param name Document name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the file extension of the document.
     * 
     * @return Document file extension.
     */
    public String getExtension() {
        return extension;
    }

    /**
     * Sets the file extension of the document.
     * 
     * @param extension Document file extension to set.
     */
    public void setExtension(String extension) {
        this.extension = extension;
    }

    /**
     * Returns the MIME type of the document content.
     * 
     * @return Document content type.
     */
    public String getContentType() {
        return contentType;
    }

    /**
     * Sets the MIME type of the document content.
     * 
     * @param contentType Document content type to set.
     */
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    /**
     * Returns the creation date of the document.
     * 
     * @return Document creation date.
     */
    public Date getCreatedDate() {
        return createdDate;
    }

    /**
     * Sets the creation date of the document.
     * 
     * @param createdDate Document creation date to set.
     */
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * Returns the first processed date of the document.
     * 
     * @return Document first processed date.
     */
    public Date getFirstProcessedDate() {
        return firstProcessedDate;
    }

    /**
     * Sets the first processed date of the document.
     * 
     * @param firstProcessedDate Document first processed date to set.
     */
    public void setFirstProcessedDate(Date firstProcessedDate) {
        this.firstProcessedDate = firstProcessedDate;
    }

    /**
     * Returns the last processed date of the document.
     * 
     * @return Document last processed date.
     */
    public Date getLastProcessedDate() {
        return lastProcessedDate;
    }

    /**
     * Sets the last processed date of the document.
     * 
     * @param lastProcessedDate Document last processed date to set.
     */
    public void setLastProcessedDate(Date lastProcessedDate) {
        this.lastProcessedDate = lastProcessedDate;
    }

    /**
     * Returns the size of the document in bytes.
     * 
     * @return Document size.
     */
    public Long getSize() {
        return size;
    }

    /**
     * Sets the size of the document in bytes.
     * 
     * @param size Document size to set.
     */
    public void setSize(Long size) {
        this.size = size;
    }

    /**
     * Returns the original content modified date.
     * 
     * @return Date the content was last modified.
     */
    public Date getOriginalContentModifiedDate() {
        return originalContentModifiedDate;
    }

    /**
     * Sets the original content modified date.
     * 
     * @param originalContentModifiedDate Date to set as last modified.
     */
    public void setOriginalContentModifiedDate(Date originalContentModifiedDate) {
        this.originalContentModifiedDate = originalContentModifiedDate;
    }

    /**
     * Returns the parent path ID.
     * 
     * @return Parent path ID.
     */
    public String getParentPathId() {
        return parentPathId;
    }

    /**
     * Sets the parent path ID.
     * 
     * @param parentPathId Parent path ID to set.
     */
    public void setParentPathId(String parentPathId) {
        this.parentPathId = parentPathId;
    }

    /**
     * Returns additional attributes map.
     * 
     * @return Map of additional attributes.
     */
    public Map<String, Object> getAdditionalAttributes() {
        return additionalAttributes;
    }

    /**
     * Sets additional attributes map.
     * 
     * @param additionalAttributes Map of additional attributes to set.
     */
    public void setAdditionalAttributes(Map<String, Object> additionalAttributes) {
        this.additionalAttributes = additionalAttributes;
    }

    /**
     * Creates a copy of the current GeboDocument instance.
     * 
     * @return A new instance of GeboDocument which is a copy of the current instance.
     * @throws RuntimeException if the copying process fails.
     */
    public GeboDocument createCopy() {
        try {
            // Serialize the current object to JSON and then deserialize it back to create a deep copy.
            String _out = mapper.writeValueAsString(this);
            return mapper.readValue(_out.getBytes(), GeboDocument.class);
        } catch (Throwable th) {
            throw new RuntimeException("GeboDocument.createCopy() passing from ObjectMapper has failed", th);
        }
    }

    /**
     * Returns the reference to the virtual parent folder.
     * 
     * @return Virtual parent folder reference.
     */
    public VFilesystemReference getVirtualParentFolderReference() {
        return virtualParentFolderReference;
    }

    /**
     * Sets the reference to the virtual parent folder.
     * 
     * @param virtualParentFolderReference Virtual parent folder reference to set.
     */
    public void setVirtualParentFolderReference(VFilesystemReference virtualParentFolderReference) {
        this.virtualParentFolderReference = virtualParentFolderReference;
    }

    /**
     * Returns the reference to the virtual file.
     * 
     * @return Virtual file reference.
     */
    public VFilesystemReference getVirtualFileReference() {
        return virtualFileReference;
    }

    /**
     * Sets the reference to the virtual file.
     * 
     * @param virtualFileReference Virtual file reference to set.
     */
    public void setVirtualFileReference(VFilesystemReference virtualFileReference) {
        this.virtualFileReference = virtualFileReference;
    }

    /**
     * Attempts to add media to the document, throwing an exception since it's not implemented.
     * 
     * @param name2             Name of the media.
     * @param type              Type of the media.
     * @param subtype           Subtype of the media.
     * @param subtypeSuffix     Suffix for the media subtype.
     * @param dataAsByteArray   Byte array of the media data.
     * @throws MethodNotFoundException when called, as implementation is missing.
     */
    public void tryAddMedia(String name2, String type, String subtype, String subtypeSuffix, byte[] dataAsByteArray) {
        throw new MethodNotFoundException("The method tryAddMedia have to be implemented");
    }
}