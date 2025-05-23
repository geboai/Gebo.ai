/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.architecture.integration.tests.model;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * AI generated comments
 * Represents a node in a virtual filesystem used for testing purposes.
 */
public class TestVirtualFilesystemNode {
    // Parent node in the hierarchy
    public TestVirtualFilesystemNode parent = null;
    
    // Static ObjectMapper instance for JSON operations
    static ObjectMapper mapper = new ObjectMapper();
    
    // Indicates if the node is a resource
    boolean resource = false;
    // Indicates if the node is a folder
    boolean folder = false;
    
    // Unique identifier of the node
    String id = null;
    
    // Name of the node
    String name = null;
    
    // URL associated with the node
    String url = null;
    
    // Content within the node, if applicable
    String content = null;
    
    // Size of the file if the node is a file
    Long fileSize = null;
    
    // Date associated with the node
    String date = null;
    
    // List of child nodes
    List<TestVirtualFilesystemNode> childs = new ArrayList<TestVirtualFilesystemNode>();

    /**
     * Checks if the node is a resource.
     * @return true if the node is a resource, false otherwise
     */
    public boolean isResource() {
        return resource;
    }

    /**
     * Sets whether the node is a resource.
     * @param resource true if the node is a resource, false otherwise
     */
    public void setResource(boolean resource) {
        this.resource = resource;
    }

    /**
     * Checks if the node is a folder.
     * @return true if the node is a folder, false otherwise
     */
    public boolean isFolder() {
        return folder;
    }

    /**
     * Sets whether the node is a folder.
     * @param folder true if the node is a folder, false otherwise
     */
    public void setFolder(boolean folder) {
        this.folder = folder;
    }

    /**
     * Gets the unique identifier of the node.
     * @return the node's identifier
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the node.
     * @param id the node's identifier
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the name of the node.
     * @return the node's name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the node.
     * @param name the node's name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the URL associated with the node.
     * @return the node's URL
     */
    public String getUrl() {
        return url;
    }

    /**
     * Sets the URL associated with the node.
     * @param url the node's URL
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Gets the content of the node.
     * @return the node's content
     */
    public String getContent() {
        return content;
    }

    /**
     * Sets the content of the node.
     * @param content the node's content
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Gets the list of child nodes.
     * @return list of child nodes
     */
    public List<TestVirtualFilesystemNode> getChilds() {
        return childs;
    }

    /**
     * Sets the list of child nodes.
     * @param childs the list of child nodes
     */
    public void setChilds(List<TestVirtualFilesystemNode> childs) {
        this.childs = childs;
    }

    /**
     * Loads a TestVirtualFilesystemNode from a specified resource path.
     * This method uses Jackson ObjectMapper to parse the resource.
     * 
     * @param path the path to the resource
     * @return the root node loaded from the resource
     * @throws IOException if the resource cannot be loaded
     */
    public static TestVirtualFilesystemNode loadResources(String path) throws IOException {
        InputStream is = TestVirtualFilesystemNode.class.getResourceAsStream(path);
        if (is == null)
            throw new RuntimeException("Cannot load " + path + " from resources");
        TestVirtualFilesystemNode root = mapper.readValue(is, TestVirtualFilesystemNode.class);
        linkParents(root, root.childs);
        return root;
    }

    /**
     * Recursively links child nodes to their parent node.
     * 
     * @param root the root node to which children are linked
     * @param childs the list of child nodes to link
     */
    private static void linkParents(TestVirtualFilesystemNode root, List<TestVirtualFilesystemNode> childs) {
        for (TestVirtualFilesystemNode child : childs) {
            child.parent = root;
            linkParents(child, child.childs);
        }
    }

    /**
     * Gets the file size of the node.
     * @return the file size
     */
    public Long getFileSize() {
        return fileSize;
    }

    /**
     * Sets the file size of the node.
     * @param fileSize the file size
     */
    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    /**
     * Gets the date associated with the node.
     * @return the date
     */
    public String getDate() {
        return date;
    }

    /**
     * Sets the date associated with the node.
     * @param date the date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Removes a node with the specified id from the filesystem.
     * The node is removed from its parent's child list.
     * 
     * @param id the identifier of the node to be removed
     */
    public void removeNode(String id) {
        TestVirtualFilesystemNode node = findNode(id);
        if (node != null) {
            if (node.parent != null) {
                node.parent.childs.remove(node);
            }
            node.childs.clear();
        }
    }

    /**
     * Finds a node with the specified id within the current node's hierarchy.
     * 
     * @param id the identifier of the node to find
     * @return the node if found, null otherwise
     */
    public TestVirtualFilesystemNode findNode(String id) {
        if (id != null && this.id != null && this.id.equals(id)) {
            return this;
        }
        if (childs != null)
            for (TestVirtualFilesystemNode child : childs) {
                TestVirtualFilesystemNode found = child.findNode(id);
                if (found != null)
                    return found;
            }
        return null;
    }
}