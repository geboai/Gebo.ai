/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.knlowledgebase.model.contents;

import java.util.Map;

import org.springframework.data.mongodb.core.index.HashIndexed;
import org.springframework.data.mongodb.core.index.TextIndexed;

import ai.gebo.knlowledgebase.model.projects.GProject;
import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.model.annotations.GObjectReference;
import ai.gebo.model.base.GBaseVersionableObject;
import ai.gebo.model.base.GObjectRef;

/**
 * Abstract class representing a virtual filesystem object.
 * This class provides a base structure for objects participating
 * in a virtual file system, including metadata such as paths and
 * project associations.
 * AI generated comments
 */
public abstract class GAbstractVirtualFilesystemObject extends GBaseVersionableObject {

    /**
     * The code of the parent virtual folder.
     */
    @HashIndexed
    private String parentVirtualFolderCode = null;

    /**
     * The absolute path of the filesystem object.
     */
    @HashIndexed
    private String absolutePath = null;

    /**
     * The code of the parent project associated with this filesystem object.
     * This is a reference to a GProject.
     */
    @HashIndexed
    @GObjectReference(referencedType = GProject.class)
    private String parentProjectCode = null;

    /**
     * The code of the root knowledgebase associated with this filesystem object.
     * This is a reference to a GKnowledgeBase.
     */
    @HashIndexed
    @GObjectReference(referencedType = GKnowledgeBase.class)
    private String rootKnowledgebaseCode = null;

    /**
     * The URI of the filesystem object.
     */
    private String uri = null;

    /**
     * The relative path of the filesystem object.
     */
    private String relativePath = null;

    /**
     * The name of the filesystem object.
     */
    @HashIndexed
    @TextIndexed
    private String name = null;

    /**
     * Flag indicating whether the filesystem object is marked as deleted.
     */
    private Boolean deleted = false;

    /**
     * ID of the messaging module associated with this filesystem object.
     */
    @HashIndexed
    private String messagingModuleId = null;

    /**
     * Reference to the project endpoint associated with this filesystem object.
     */
    @HashIndexed
    private GObjectRef<GProjectEndpoint> projectEndpointReference = null;

    /**
     * Flag indicating whether the filesystem object is nested in an archive.
     */
    private Boolean nestedInArchive = null;

    /**
     * The absolute path of the archive containing this filesystem object.
     */
    private String absoluteArchivePath = null;

    /**
     * The internal path within the archive.
     */
    private String archiveInternalPath = null;

    /**
     * Custom metadata information associated with this filesystem object.
     */
    private Map<String, Object> customMetaInfos = null;

    /**
     * The ID of the latest job associated with this filesystem object.
     */
    @HashIndexed
    private String lastesJobId = null;

    /**
     * Default constructor.
     */
    public GAbstractVirtualFilesystemObject() {
    }

    /**
     * Copy constructor that initializes a new instance using another instance.
     * 
     * @param o The GAbstractVirtualFilesystemObject to copy data from.
     */
    public GAbstractVirtualFilesystemObject(GAbstractVirtualFilesystemObject o) {
        this.setCode(o.getCode());
        this.setDescription(o.getDescription());
        this.absolutePath = o.absolutePath;
        this.messagingModuleId = o.messagingModuleId;
        this.name = o.name;
        this.parentProjectCode = o.parentProjectCode;
        this.projectEndpointReference = o.projectEndpointReference;
        this.parentVirtualFolderCode = o.parentVirtualFolderCode;
        this.rootKnowledgebaseCode = o.rootKnowledgebaseCode;
        this.absolutePath = o.absolutePath;
        this.deleted = o.deleted;
        this.uri = o.uri;
        this.setModificationDate(o.getModificationDate());
        this.setCreationDate(o.getCreationDate());
    }

    /**
     * Sets the project endpoint reference.
     * 
     * @param projectEndpointReference The reference to set.
     */
    public void setProjectEndpointReference(GObjectRef<GProjectEndpoint> projectEndpointReference) {
        this.projectEndpointReference = projectEndpointReference;
    }

    /**
     * Returns the project endpoint reference.
     * 
     * @return The project endpoint reference.
     */
    public GObjectRef<GProjectEndpoint> getProjectEndpointReference() {
        return projectEndpointReference;
    }

    /**
     * Returns the code of the parent virtual folder.
     * 
     * @return The parent virtual folder code.
     */
    public String getParentVirtualFolderCode() {
        return parentVirtualFolderCode;
    }

    /**
     * Sets the parent virtual folder code.
     * 
     * @param parentVirtualFolderCode The code to set.
     */
    public void setParentVirtualFolderCode(String parentVirtualFolderCode) {
        this.parentVirtualFolderCode = parentVirtualFolderCode;
    }

    /**
     * Returns the parent project code.
     * 
     * @return The parent project code.
     */
    public String getParentProjectCode() {
        return parentProjectCode;
    }

    /**
     * Sets the parent project code.
     * 
     * @param parentProjectCode The code to set.
     */
    public void setParentProjectCode(String parentProjectCode) {
        this.parentProjectCode = parentProjectCode;
    }

    /**
     * Returns the root knowledgebase code.
     * 
     * @return The root knowledgebase code.
     */
    public String getRootKnowledgebaseCode() {
        return rootKnowledgebaseCode;
    }

    /**
     * Sets the root knowledgebase code.
     * 
     * @param rootKnowledgebaseCode The code to set.
     */
    public void setRootKnowledgebaseCode(String rootKnowledgebaseCode) {
        this.rootKnowledgebaseCode = rootKnowledgebaseCode;
    }

    /**
     * Returns the absolute path.
     * 
     * @return The absolute path.
     */
    public String getAbsolutePath() {
        return absolutePath;
    }

    /**
     * Sets the absolute path.
     * 
     * @param absolutePath The path to set.
     */
    public void setAbsolutePath(String absolutePath) {
        this.absolutePath = absolutePath;
    }

    /**
     * Returns the URI of the filesystem object.
     * 
     * @return The URI.
     */
    public String getUri() {
        return uri;
    }

    /**
     * Sets the URI of the filesystem object.
     * 
     * @param uri The URI to set.
     */
    public void setUri(String uri) {
        this.uri = uri;
    }

    /**
     * Returns the relative path.
     * 
     * @return The relative path.
     */
    public String getRelativePath() {
        return relativePath;
    }

    /**
     * Sets the relative path.
     * 
     * @param relativePath The path to set.
     */
    public void setRelativePath(String relativePath) {
        this.relativePath = relativePath;
    }

    /**
     * Returns whether the filesystem object is marked as deleted.
     * 
     * @return True if deleted, false otherwise.
     */
    public Boolean getDeleted() {
        return deleted;
    }

    /**
     * Sets the deleted status of the filesystem object.
     * 
     * @param deleted The status to set.
     */
    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    /**
     * Returns the name of the filesystem object.
     * 
     * @return The name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the filesystem object.
     * 
     * @param name The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the messaging module ID.
     * 
     * @return The ID.
     */
    public String getMessagingModuleId() {
        return messagingModuleId;
    }

    /**
     * Sets the messaging module ID.
     * 
     * @param moduleId The ID to set.
     */
    public void setMessagingModuleId(String moduleId) {
        this.messagingModuleId = moduleId;
    }

    /**
     * Returns whether the filesystem object is nested in an archive.
     * 
     * @return True if nested, false otherwise.
     */
    public Boolean getNestedInArchive() {
        return nestedInArchive;
    }

    /**
     * Sets the nested status of the filesystem object.
     * 
     * @param nestedInArchive The status to set.
     */
    public void setNestedInArchive(Boolean nestedInArchive) {
        this.nestedInArchive = nestedInArchive;
    }

    /**
     * Returns the absolute archive path.
     * 
     * @return The archive path.
     */
    public String getAbsoluteArchivePath() {
        return absoluteArchivePath;
    }

    /**
     * Sets the absolute archive path.
     * 
     * @param absoluteArchivePath The path to set.
     */
    public void setAbsoluteArchivePath(String absoluteArchivePath) {
        this.absoluteArchivePath = absoluteArchivePath;
    }

    /**
     * Returns the archive internal path.
     * 
     * @return The internal path.
     */
    public String getArchiveInternalPath() {
        return archiveInternalPath;
    }

    /**
     * Sets the archive internal path.
     * 
     * @param archiveInternalPath The path to set.
     */
    public void setArchiveInternalPath(String archiveInternalPath) {
        this.archiveInternalPath = archiveInternalPath;
    }

    /**
     * Returns the ID of the latest job.
     * 
     * @return The job ID.
     */
    public String getLastesJobId() {
        return lastesJobId;
    }

    /**
     * Sets the ID of the latest job.
     * 
     * @param lastesJobId The job ID to set.
     */
    public void setLastesJobId(String lastesJobId) {
        this.lastesJobId = lastesJobId;
    }

    /**
     * Returns the custom metadata associated with the filesystem object.
     * 
     * @return The metadata.
     */
    public Map<String, Object> getCustomMetaInfos() {
        return customMetaInfos;
    }

    /**
     * Sets the custom metadata information.
     * 
     * @param customMetaInfos The metadata to set.
     */
    public void setCustomMetaInfos(Map<String, Object> customMetaInfos) {
        this.customMetaInfos = customMetaInfos;
    }
}