/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.model.virtualfs;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Root nodes and child navigation components representing a "photograph" of a
 * virtual filesystem tree navigation opened path state.
 * AI generated comments
 */
public class VirtualFilesystemNavigationTreeStatus implements Serializable {

    // Represents the root of the virtual filesystem
    private GVirtualFilesystemRoot root = null;
    
    // Indicates if the current node is selected in the virtual filesystem
    private boolean selected = false;
    
    // Indicates if the current node is opened in the virtual filesystem
    private boolean opened = false;
    
    // List of child nodes in the virtual filesystem
    private List<VirtualFilesystemNavigationNode> childs = new ArrayList<VirtualFilesystemNavigationNode>();

    /**
     * Returns the root of the virtual filesystem.
     * 
     * @return the root node of the filesystem
     */
    public GVirtualFilesystemRoot getRoot() {
        return root;
    }

    /**
     * Sets the root of the virtual filesystem.
     * 
     * @param root the root node to set
     */
    public void setRoot(GVirtualFilesystemRoot root) {
        this.root = root;
    }

    /**
     * Checks if the node is selected.
     * 
     * @return true if the node is selected, false otherwise
     */
    public boolean isSelected() {
        return selected;
    }

    /**
     * Sets the selected state of the node.
     * 
     * @param selected the selection state to set
     */
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    /**
     * Checks if the node is opened.
     * 
     * @return true if the node is opened, false otherwise
     */
    public boolean isOpened() {
        return opened;
    }

    /**
     * Sets the opened state of the node.
     * 
     * @param opened the opened state to set
     */
    public void setOpened(boolean opened) {
        this.opened = opened;
    }

    /**
     * Returns the list of child nodes.
     * 
     * @return list of child nodes in the filesystem
     */
    public List<VirtualFilesystemNavigationNode> getChilds() {
        return childs;
    }

    /**
     * Sets the list of child nodes.
     * 
     * @param childs the child nodes to set
     */
    public void setChilds(List<VirtualFilesystemNavigationNode> childs) {
        this.childs = childs;
    }

    /**
     * Checks whether a given filesystem reference is contained within the current node or its children.
     * 
     * @param value the filesystem reference to check
     * @return true if the reference is contained, false otherwise
     */
    public boolean isContained(VFilesystemReference value) {
        // Checks if the reference path matches the root path
        if (value.path == null && value.root.getAbsolutePath().equals(root.getAbsolutePath()))
            return true;
        
        // Checks if the reference is contained in any of the child nodes
        return childs.stream().anyMatch(x -> x.isContained(value));
    }
}