/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.application.messaging.model;

import java.util.List;

import jakarta.validation.constraints.NotNull;

/**
 * Gebo.ai comment agent
 * 
 * Represents the payload for an internal deletion message within the system.
 * This payload specifies the type of objects to be deleted and their corresponding codes.
 */
public class GInternalDeletionMessagePayload extends GBaseMessagePayload {
    
    /**
     * Enum representing the types of objects that can be deleted.
     */
    public static enum ObjectType {
        KNOWLEDGEBASE, PROJECT, DOCUMENTREF, ENDPOINT, VIRTUALFOLDER
    }

    // The type of objects that are targeted for deletion.
    private ObjectType objectsType = null;
    
    // List of codes identifying the objects slated for deletion.
    private @NotNull List<String> codes4deletion = null;

    /**
     * Default constructor for GInternalDeletionMessagePayload.
     */
    public GInternalDeletionMessagePayload() {

    }

    /**
     * Retrieves the list of codes for the objects to be deleted.
     *
     * @return a list of strings, each representing a code for deletion.
     */
    public List<String> getCodes4deletion() {
        return codes4deletion;
    }

    /**
     * Sets the list of codes for objects to be deleted.
     *
     * @param codes a list of strings representing codes for deletion.
     */
    public void setCodes4deletion(List<String> codes) {
        this.codes4deletion = codes;
    }

    /**
     * Retrieves the type of objects that are targeted for deletion.
     *
     * @return the object type as specified in the ObjectType enum.
     */
    public ObjectType getObjectsType() {
        return objectsType;
    }

    /**
     * Sets the type of objects that are targeted for deletion.
     *
     * @param objectsType the type of objects to set for deletion.
     */
    public void setObjectsType(ObjectType objectsType) {
        this.objectsType = objectsType;
    }

    
}