/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.ai.app.virtualremotefs.tests.architecture;

import ai.gebo.architecture.integration.tests.model.TestVirtualFilesystemNode;

/**
 * AI generated comments
 * The TestRootHolder class holds a reference to the root node of a virtual filesystem
 * and provides functionality to find a node within this filesystem by its ID.
 */
public class TestRootHolder {

    /** 
     * The rootNode is a static reference to the root of the virtual filesystem.
     * It can be accessed globally within the application.
     */
    public static TestVirtualFilesystemNode rootNode = null;

    /**
     * Finds a node in the virtual filesystem by its unique identifier.
     *
     * @param id the unique identifier of the node to be found
     * @return the node with the specified ID, or null if the rootNode is not initialized or the node cannot be found
     */
    public static TestVirtualFilesystemNode findNode(String id) {
        if (rootNode == null)
            return null; // Return null if the rootNode is not set
        return rootNode.findNode(id); // Delegate the search to the rootNode
    }
}