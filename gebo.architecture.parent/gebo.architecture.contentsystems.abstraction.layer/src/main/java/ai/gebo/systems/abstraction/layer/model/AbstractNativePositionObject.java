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
import java.util.Date;
import java.util.HashMap;

import ai.gebo.model.virtualfs.PathInfo;

/**
 * AI generated comments
 * Abstract class representing a native position object. This class provides the basic structure
 * and methods needed for any position object that is used to interface with native systems.
 * Implements Serializable interface for object serialization.
 */
public abstract class AbstractNativePositionObject implements Serializable {
    // Path information associated with the position object
    PathInfo path = null;

    /**
     * Gets the PathInfo object associated with this position object.
     *
     * @return the path object.
     */
    public PathInfo getPath() {
        return path;
    }

    /**
     * Sets the PathInfo object for this position object.
     *
     * @param path the path to set
     */
    public void setPath(PathInfo path) {
        this.path = path;
    }

    /**
     * Returns the code of the position object.
     *
     * @return the code as a String.
     */
    public abstract String getCode();

    /**
     * Returns the name of the position object.
     *
     * @return the name as a String.
     */
    public abstract String getName();

    /**
     * Returns the URL of the position object.
     *
     * @return the URL as a String.
     */
    public abstract String getUrl();

    /**
     * Determines if the position object is a resource.
     *
     * @return true if it is a resource, false otherwise.
     */
    public abstract boolean isResource();

    /**
     * Determines if the position object is a folder.
     *
     * @return true if it is a folder, false otherwise.
     */
    public abstract boolean isFolder();

    /**
     * Provides meta-information associated with the resource reference.
     *
     * @return a map of meta-information key-value pairs.
     */
    public abstract HashMap<String, Object> getResourceReferenceMetaInfos();

    /**
     * Provides the content type of the resource.
     *
     * @return the resource content type as a String.
     */
    public abstract String getResourceContentType();

    /**
     * Provides the last modification time of the resource.
     *
     * @return the modification time as a Date object.
     */
    public abstract Date getResourceModificationTime();

    /**
     * Provides the size of the resource file.
     *
     * @return the file size as a Long.
     */
    public abstract Long getResourceFileSize();

    /**
     * @return A string representation of the position object, including
     * its name, path, code, whether it is a resource, its content type,
     * URL, file size, and meta-information.
     */
    @Override
    public String toString() {
        String _string = "{name=" + getName() + ", path=" + path + ",code=" + getCode() + ",resource=" + isResource()
                + ",resourceContentType=" + getResourceContentType() + ",url=" + getUrl() + ",resourceFileSize="
                + getResourceFileSize() + " metainfos=" + getResourceReferenceMetaInfos() + "}";
        return _string;
    }
}