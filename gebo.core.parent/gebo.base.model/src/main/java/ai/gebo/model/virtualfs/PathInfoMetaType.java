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
 * Represents the type of metadata associated with a path in a virtual file system.
 * 
 * AI generated comments
 */
public enum PathInfoMetaType {
    /** Indicates that the path points to a web page. */
    WEB_PAGE, 
    
    /** Indicates that the path points to a file. */
    FILE, 
    
    /** Indicates that the path points to a folder. */
    FOLDER,
    
    /** Indicates that the path points to a device. */
    DEVICE, 
    
    /** Indicates that the type of the path is unknown. */
    UNKNOWN
}