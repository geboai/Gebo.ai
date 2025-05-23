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

/**
 * Represents an error encountered while accessing contents.
 * This class is serializable.
 * 
 * Gebo.ai comment agent
 */
public class ContentsAccessError implements Serializable {

    /**
     * Enumerates the types of accessed objects.
     */
    public static enum ContentsAccessedObjectType {
        FOLDER, // Represents a folder
        RESOURCE // Represents a specific resource
    };

    private String resourcePath = null; // Path to the resource
    private String resourceCode = null; // Unique code identifying the resource
    private ContentsAccessedObjectType objectType = null; // Type of the accessed object
    private String msg = null; // Error message

    /**
     * Gets the path of the resource.
     * 
     * @return the path of the resource
     */
    public String getResourcePath() {
        return resourcePath;
    }

    /**
     * Sets the path of the resource.
     * 
     * @param resourcePath the path to set
     */
    public void setResourcePath(String resourcePath) {
        this.resourcePath = resourcePath;
    }

    /**
     * Gets the code of the resource.
     * 
     * @return the code of the resource
     */
    public String getResourceCode() {
        return resourceCode;
    }

    /**
     * Sets the code of the resource.
     * 
     * @param resourceCode the code to set
     */
    public void setResourceCode(String resourceCode) {
        this.resourceCode = resourceCode;
    }

    /**
     * Gets the type of the accessed object.
     * 
     * @return the type of the accessed object
     */
    public ContentsAccessedObjectType getObjectType() {
        return objectType;
    }

    /**
     * Sets the type of the accessed object.
     * 
     * @param objectType the type to set
     */
    public void setObjectType(ContentsAccessedObjectType objectType) {
        this.objectType = objectType;
    }

    /**
     * Gets the error message.
     * 
     * @return the error message
     */
    public String getMsg() {
        return msg;
    }

    /**
     * Sets the error message.
     * 
     * @param msg the message to set
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * Creates a ContentsAccessError based on the given throwable, object type, and resource code.
     * 
     * @param th          the throwable from which to extract the error message
     * @param objectType  the type of the accessed object
     * @param resourceCode the code of the resource
     * @return a new instance of ContentsAccessError
     */
    public static ContentsAccessError of(Throwable th, ContentsAccessedObjectType objectType, String resourceCode) {
        ContentsAccessError err = new ContentsAccessError();
        err.msg = th.getMessage(); // Extracts the message from the throwable
        err.resourceCode = resourceCode; // Sets the resource code
        err.objectType = objectType; // Sets the object type
        return err; // Returns the newly created instance
    }
}