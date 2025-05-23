/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.architecture.ai.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Gebo.ai comment agent
 * This class represents a tree structure that contains tools categorized under a specific category.
 * It holds a reference to a category and a list of tool references associated with that category.
 */
public class ToolCategoriesTree {

    // Represents the category of tools in this tree
    private ToolsCategory category = null;

    // Holds a list of tool references within the specified category
    private List<ToolReference> toolsReference = new ArrayList<ToolReference>();

    /**
     * Default constructor that initializes an empty ToolCategoriesTree.
     */
    public ToolCategoriesTree() {

    }

    /**
     * Constructor that initializes ToolCategoriesTree with a specific category.
     * @param cate The category to be associated with the tree.
     */
    public ToolCategoriesTree(ToolsCategory cate) {
        this.category = cate;
    }

    /**
     * Gets the category associated with this tree.
     * @return The category of tools.
     */
    public ToolsCategory getCategory() {
        return category;
    }

    /**
     * Sets the category for this tree.
     * @param category The category to be set.
     */
    public void setCategory(ToolsCategory category) {
        this.category = category;
    }

    /**
     * Gets the list of tool references associated with the category.
     * @return A list of tool references.
     */
    public List<ToolReference> getToolsReference() {
        return toolsReference;
    }

    /**
     * Sets the list of tool references for the category.
     * @param functionsReference The list of tool references to be set.
     */
    public void setToolsReference(List<ToolReference> functionsReference) {
        this.toolsReference = functionsReference;
    }
}