/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.model.virtualfs;

/**
 * AI generated comments
 * The BrowseParam class represents parameters used for browsing within a virtual filesystem.
 */
public class BrowseParam {

    /**
     * The root of the virtual filesystem, represented by a GVirtualFilesystemRoot object.
     */
    public GVirtualFilesystemRoot root = null;

    /**
     * The path information within the virtual filesystem, represented by a PathInfo object.
     */
    public PathInfo path = null;

    /**
     * Returns a string representation of the BrowseParam object, displaying the root and path.
     *
     * @return A string in the format "{root=root_value,path=path_value}".
     */
    @Override
    public String toString() {
        return "{root=" + root + ",path=" + path + "}";
    }
}