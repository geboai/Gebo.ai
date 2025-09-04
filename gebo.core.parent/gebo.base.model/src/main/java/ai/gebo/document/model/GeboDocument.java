/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.document.model;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ai.gebo.model.virtualfs.VFilesystemReference;
import jakarta.el.MethodNotFoundException;
import lombok.Data;

/**
 * Represents a document with various attributes and methods to manipulate its content.
 * Includes functionality to manage different types of media fragments.
 * 
 * AI generated comments
 */
@Data
public final class GeboDocument implements JSonDuplicableObject<GeboDocument>{
    
    
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
     * Attempts to add media to the document, throwing an exception since it's not implemented.
     * 
     * @param name             Name of the media.
     * @param type              Type of the media.
     * @param subtype           Subtype of the media.
     * @param subtypeSuffix     Suffix for the media subtype.
     * @param charset 
     * @param dataAsByteArray   Byte array of the media data.
     * @throws MethodNotFoundException when called, as implementation is missing.
     */
    public void tryAddMedia(String name, String type, String subtype, String subtypeSuffix, Charset charset, byte[] dataAsByteArray) {
        //throw new MethodNotFoundException("The method tryAddMedia have to be implemented");
    }
}