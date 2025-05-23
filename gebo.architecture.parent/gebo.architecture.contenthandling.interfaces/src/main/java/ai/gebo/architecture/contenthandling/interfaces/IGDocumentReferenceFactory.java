/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.architecture.contenthandling.interfaces;

import java.nio.file.Path;
import java.util.Date;
import java.util.HashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.knlowledgebase.model.contents.GVirtualFolder;
import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;

/**
 * Gebo.ai comment agent
 * Interface for creating different types of GDocumentReference objects.
 */
public interface IGDocumentReferenceFactory {
    
    /**
     * Creates a document reference for a document in storage.
     * 
     * @param file Path to the file.
     * @param codePrefix Prefix used for code generation.
     * @param uriPrefix Prefix for the URI.
     * @param completeUrl Full URL of the document.
     * @param projectEndpoint Associated project endpoint.
     * @param virtualFolder The virtual folder where the document resides.
     * @param moduleId Identifier for the module.
     * @return GDocumentReference object representing the document.
     * @throws GeboContentHandlerSystemException if an error occurs during the creation.
     */
    public GDocumentReference createReference(Path file, String codePrefix, String uriPrefix, String completeUrl,
            GProjectEndpoint projectEndpoint, GVirtualFolder virtualFolder, String moduleId)
            throws GeboContentHandlerSystemException;

    /**
     * Creates a reference for a deleted document.
     * 
     * @param file Path to the file.
     * @param codePrefix Prefix used for code generation.
     * @param uriPrefix Prefix for the URI.
     * @param completeUrl Full URL of the document.
     * @param projectEndpoint Associated project endpoint.
     * @param moduleId Identifier for the module.
     * @return GDocumentReference object representing the deleted document.
     * @throws GeboContentHandlerSystemException if an error occurs during the creation.
     */
    public <ProjectEndpointType extends GProjectEndpoint> GDocumentReference createDeletedReference(Path file,
            String codePrefix, String uriPrefix, String completeUrl, GProjectEndpoint projectEndpoint, String moduleId)
            throws GeboContentHandlerSystemException;

    /**
     * Creates an archive reference for a document within a zip file.
     * 
     * @param originalFile Path of the original file outside the zip.
     * @param zipFile The zip file containing the archived document.
     * @param entry The specific entry within the zip file.
     * @param codePrefix Prefix used for code generation.
     * @param projectEndpoint Associated project endpoint.
     * @param virtualFolder The virtual folder where the document resides.
     * @param messagingModuleId Identifier for the messaging module.
     * @return GDocumentReference object representing the archived document.
     */
    public <ProjectEndpointType extends GProjectEndpoint> GDocumentReference createArchiveReference(Path originalFile,
            ZipFile zipFile, ZipEntry entry, String codePrefix, ProjectEndpointType projectEndpoint,
            GVirtualFolder virtualFolder, String messagingModuleId);

    /**
     * Creates a web document reference.
     * 
     * @param spaceFolder The virtual folder representing the space.
     * @param projectEndpoint Associated project endpoint.
     * @param code Unique code identifying the document.
     * @param name Name of the document.
     * @param contentType Type of content the document contains.
     * @param url URL where the document can be accessed.
     * @param modificationTimestamp Date of the last modification to the document.
     * @param meta Metadata associated with the document.
     * @param moduleId Identifier for the module.
     * @return GDocumentReference object representing the web document.
     * @throws GeboContentHandlerSystemException if an error occurs during the creation.
     */
    public GDocumentReference createWebDocumentReference(GVirtualFolder spaceFolder, GProjectEndpoint projectEndpoint,
            String code, String name, String contentType, String url, Date modificationTimestamp,
            HashMap<String, Object> meta, String moduleId) throws GeboContentHandlerSystemException;

}