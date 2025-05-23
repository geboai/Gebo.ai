/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.ai.app.virtualremotefs.tests.architecture;

/**
 * Represents a test virtual filesystem with customizable content.
 * AI generated comments
 */
public class TestVirtualFilesystemCustomContent {

    /**
     * Enum representing the type of a virtual filesystem node.
     */
    public static enum TestVirtualFilesystemNodeType {
        LEAF, // Represents a leaf node in the virtual filesystem
        NODE  // Represents a non-leaf node in the virtual filesystem
    }

    // The type of the virtual filesystem node
    TestVirtualFilesystemNodeType type = null;

    // The identifier for the node
    String id = null;

    /**
     * Gets the type of the virtual filesystem node.
     *
     * @return the type of the node, either LEAF or NODE
     */
    public TestVirtualFilesystemNodeType getType() {
        return type;
    }

    /**
     * Sets the type of the virtual filesystem node.
     *
     * @param type the type to set, either LEAF or NODE
     */
    public void setType(TestVirtualFilesystemNodeType type) {
        this.type = type;
    }

    /**
     * Gets the identifier of the node.
     *
     * @return the id of the node
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the identifier for the node.
     *
     * @param id the id to set for the node
     */
    public void setId(String id) {
        this.id = id;
    }
}