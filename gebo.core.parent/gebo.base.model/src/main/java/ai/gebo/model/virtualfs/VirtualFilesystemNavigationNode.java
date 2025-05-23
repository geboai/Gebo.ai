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

import jakarta.validation.constraints.NotNull;

/**
 * Intermediate node in a virtual file system navigation tree.
 * Represents a node that contains references to a file system element
 * and potentially has child nodes, allowing the structure to form a tree.
 * 
 * AI generated comments
 */
public class VirtualFilesystemNavigationNode implements Serializable {
	
	@NotNull
	private VFilesystemReference value = null; // Reference to the file system element associated with this node.
	
	private List<VirtualFilesystemNavigationNode> childs = new ArrayList<VirtualFilesystemNavigationNode>(); // List of child nodes.

	private boolean selected = false; // Indicates whether this node is selected.
	private boolean opened = false; // Indicates whether this node is opened.

	/**
	 * Gets the list of child nodes.
	 *
	 * @return List of child nodes.
	 */
	public List<VirtualFilesystemNavigationNode> getChilds() {
		return childs;
	}

	/**
	 * Sets the child nodes for this node.
	 *
	 * @param childs List of child nodes to be set.
	 */
	public void setChilds(List<VirtualFilesystemNavigationNode> childs) {
		this.childs = childs;
	}

	/**
	 * Checks if the current node is selected.
	 *
	 * @return true if the node is selected, false otherwise.
	 */
	public boolean isSelected() {
		return selected;
	}

	/**
	 * Sets the node as selected or not.
	 *
	 * @param selected true to select the node, false to deselect it.
	 */
	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	/**
	 * Checks if the current node is opened.
	 *
	 * @return true if the node is opened, false otherwise.
	 */
	public boolean isOpened() {
		return opened;
	}

	/**
	 * Sets the node as opened or closed.
	 *
	 * @param opened true to open the node, false to close it.
	 */
	public void setOpened(boolean opened) {
		this.opened = opened;
	}

	/**
	 * Gets the file system element reference associated with this node.
	 *
	 * @return The file system reference.
	 */
	public VFilesystemReference getValue() {
		return value;
	}

	/**
	 * Sets the file system element reference for this node.
	 *
	 * @param value The file system reference to be set.
	 */
	public void setValue(VFilesystemReference value) {
		this.value = value;
	}

	/**
	 * Checks if a given file system reference is contained within this node
	 * or any of its child nodes.
	 *
	 * @param _value The file system reference to check.
	 * @return true if the reference is contained, false otherwise.
	 */
	public boolean isContained(VFilesystemReference _value) {
		// Checks if the root and path of the given reference matches this node's reference.
		if (_value.root != null && this.value.root != null
				&& _value.root.getAbsolutePath().equals(this.value.root.getAbsolutePath())
				&& ((_value.path == null && this.value.path == null) || (_value.path != null && this.value.path != null
						&& _value.path.absolutePath.equals(this.value.path.absolutePath)))) {
			return true;
		}
		// Recursively check if any child node contains the given reference.
		return childs.stream().anyMatch(x -> x.isContained(_value));
	}
}