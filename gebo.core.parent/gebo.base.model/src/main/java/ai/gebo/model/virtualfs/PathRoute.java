/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.model.virtualfs;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a route within a virtual filesystem.
 * This class holds a reference to the root of the filesystem and a list of path segments.
 * 
 * AI generated comments
 */
public class PathRoute {
    /**
     * The root of the virtual filesystem.
     */
    public GVirtualFilesystemRoot root = null;

    /**
     * List of path segments in the route.
     */
    public List<PathInfo> path = new ArrayList<PathInfo>();
}