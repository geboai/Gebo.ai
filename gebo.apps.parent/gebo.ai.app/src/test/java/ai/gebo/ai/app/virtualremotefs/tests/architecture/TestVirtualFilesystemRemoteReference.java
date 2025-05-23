/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.ai.app.virtualremotefs.tests.architecture;

import ai.gebo.systems.abstraction.layer.IGRemoteVirtualFilesystemResourceReference;

/**
 * AI generated comments
 * Represents a test implementation of a remote filesystem resource reference.
 * This class implements the IGRemoteVirtualFilesystemResourceReference interface
 * and provides methods to manage virtual filesystem resources.
 */
public class TestVirtualFilesystemRemoteReference implements IGRemoteVirtualFilesystemResourceReference {

    // Unique identifier for the resource
    private String id = null;

    // Flags to determine if this is a folder or a generic resource
    private boolean folder = false, resource = false;

    /**
     * Retrieves the unique identifier of the resource.
     * @return the unique identifier of the resource
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the resource.
     * @param id the new unique identifier for the resource
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Checks if the resource is a folder.
     * @return true if the resource is a folder, false otherwise
     */
    public boolean isFolder() {
        return folder;
    }

    /**
     * Sets whether the resource is a folder.
     * @param folder true if the resource is a folder, false otherwise
     */
    public void setFolder(boolean folder) {
        this.folder = folder;
    }

    /**
     * Checks if the resource is a generic resource.
     * @return true if the resource is a generic resource, false otherwise
     */
    public boolean isResource() {
        return resource;
    }

    /**
     * Sets whether the resource is a generic resource.
     * @param resource true if the resource is a generic resource, false otherwise
     */
    public void setResource(boolean resource) {
        this.resource = resource;
    }
}