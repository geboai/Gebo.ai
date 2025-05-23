/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.ai.app.virtualremotefs.tests.architecture;

import java.util.Date;
import java.util.HashMap;

import ai.gebo.architecture.integration.tests.model.TestVirtualFilesystemNode;
import ai.gebo.systems.abstraction.layer.model.AbstractNativePositionObject;

/**
 * AI generated comments
 * This class represents a test object for a virtual filesystem node that 
 * extends from AbstractNativePositionObject. It holds information about 
 * virtual filesystem nodes, including resources and folders.
 */
public class TestVirtualFilesystemNativeObject extends AbstractNativePositionObject {

    // Constants for identifying node types
    public static final String FOLDER_NODE_ID = "FOLDER_NODE_ID";
    public static final String RESOURCE_NODE_ID = "RESOURCE_NODE_ID";

    // Definition of basic properties of the virtual filesystem node
    private String code = null, name = null, url = null;
    boolean resource = false, folder = false;

    // Metadata information about resources
    private HashMap<String, Object> metainfos = new HashMap<String, Object>();
    private String resourceContentType = null;
    private Date resourceModificationTime;

    // Node objects representing resources and folders
    private TestVirtualFilesystemNode resourceObject = null;
    private TestVirtualFilesystemNode folderObject = null;
    private Long resourceFileSize = null;

    /** 
     * Returns the code of the node.
     * @return the code of the node.
     */
    @Override
    public String getCode() {
        return code;
    }

    /** 
     * Returns the name of the node.
     * @return the name of the node.
     */
    @Override
    public String getName() {
        return name;
    }

    /** 
     * Returns the URL of the node.
     * @return the URL of the node.
     */
    @Override
    public String getUrl() {
        return url;
    }

    /** 
     * Checks if the node is a resource.
     * @return true if the node is a resource, otherwise false.
     */
    @Override
    public boolean isResource() {
        return resource;
    }

    /** 
     * Checks if the node is a folder.
     * @return true if the node is a folder, otherwise false.
     */
    @Override
    public boolean isFolder() {
        return folder;
    }

    /** 
     * Returns metadata information of the resource as a HashMap.
     * @return metadata information of the resource.
     */
    @Override
    public HashMap<String, Object> getResourceReferenceMetaInfos() {
        return metainfos;
    }

    /** 
     * Returns the content type of the resource.
     * @return the content type of the resource.
     */
    @Override
    public String getResourceContentType() {
        return resourceContentType;
    }

    /** 
     * Returns the last modification date of the resource.
     * @return last modification date of the resource.
     */
    @Override
    public Date getResourceModificationTime() {
        return this.resourceModificationTime;
    }

    /** 
     * Returns the file size of the resource.
     * @return the file size of the resource.
     */
    @Override
    public Long getResourceFileSize() {
        return resourceFileSize;
    }

    /** 
     * Returns metadata information as a HashMap.
     * @return metadata information.
     */
    public HashMap<String, Object> getMetainfos() {
        return metainfos;
    }

    /** 
     * Sets the metadata information.
     * @param metainfos the metadata information to set.
     */
    public void setMetainfos(HashMap<String, Object> metainfos) {
        this.metainfos = metainfos;
    }

    /** 
     * Sets the code of the node.
     * @param code the code to set.
     */
    public void setCode(String code) {
        this.code = code;
    }

    /** 
     * Sets the name of the node.
     * @param name the name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /** 
     * Sets the URL of the node.
     * @param url the URL to set.
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /** 
     * Specifies whether the node is a resource.
     * @param resource the resource status to set.
     */
    public void setResource(boolean resource) {
        this.resource = resource;
    }

    /** 
     * Specifies whether the node is a folder.
     * @param folder the folder status to set.
     */
    public void setFolder(boolean folder) {
        this.folder = folder;
    }

    /** 
     * Sets the content type of the resource.
     * @param resourceContentType the content type to set.
     */
    public void setResourceContentType(String resourceContentType) {
        this.resourceContentType = resourceContentType;
    }

    /** 
     * Sets the last modification time of the resource.
     * @param resourceModificationTime the modification time to set.
     */
    public void setResourceModificationTime(Date resourceModificationTime) {
        this.resourceModificationTime = resourceModificationTime;
    }

    /** 
     * Returns the resource node object.
     * @return the resource node object.
     */
    public TestVirtualFilesystemNode getResourceObject() {
        return resourceObject;
    }

    /**
     * Sets basic data properties using the given node and updates internal state.
     * @param node the node with data to set.
     */
    private void setData(TestVirtualFilesystemNode node) {
        this.code = node.getId();
        this.name = node.getName();
        this.url = node.getUrl();
        this.resourceFileSize = node.getFileSize();
    }

    /** 
     * Sets the resource node object and updates the internal state.
     * @param resourceObject the resource node object to set.
     */
    public void setResourceObject(TestVirtualFilesystemNode resourceObject) {
        this.resourceObject = resourceObject;
        this.setData(resourceObject);
        this.resource = true;
        this.metainfos.put(RESOURCE_NODE_ID, resourceObject.getId());
        this.resourceContentType = "text/plain";
    }

    /** 
     * Returns the folder node object.
     * @return the folder node object.
     */
    public TestVirtualFilesystemNode getFolderObject() {
        return folderObject;
    }

    /** 
     * Sets the folder node object and updates internal state.
     * @param folderObject the folder node object to set.
     */
    public void setFolderObject(TestVirtualFilesystemNode folderObject) {
        this.folderObject = folderObject;
        this.setData(folderObject);
        this.folder = true;
        this.metainfos.put(FOLDER_NODE_ID, folderObject.getId());
    }
}