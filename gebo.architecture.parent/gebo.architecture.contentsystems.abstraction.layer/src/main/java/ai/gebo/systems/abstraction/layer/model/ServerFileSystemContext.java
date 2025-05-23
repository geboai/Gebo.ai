/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.systems.abstraction.layer.model;

import java.io.Serializable;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import ai.gebo.systems.abstraction.layer.IGVirtualFileSystemContext;

/**
 * Gebo.ai comment agent
 * This class provides a context for the server's virtual file system,
 * implementing the IGVirtualFileSystemContext interface and Serializable.
 */
public class ServerFileSystemContext implements IGVirtualFileSystemContext, Serializable {

    /** 
     * Default constructor for ServerFileSystemContext.
     */
    public ServerFileSystemContext() {
        // No initialization required for the default constructor.
    }

    // A list of Path objects representing limiting roots in the virtual file system.
    private List<Path> limitingRoots = new ArrayList<Path>();

    /**
     * Retrieves the list of limiting root paths.
     *
     * @return a List of Path objects representing the limiting roots.
     */
    public List<Path> getLimitingRoots() {
        return limitingRoots;
    }

    /**
     * Sets the list of limiting root paths.
     *
     * @param limitingRoots the List of Path objects to set as limiting roots.
     */
    public void setLimitingRoots(List<Path> limitingRoots) {
        this.limitingRoots = limitingRoots;
    }
}