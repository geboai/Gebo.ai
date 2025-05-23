/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.knlowledgebase.model.contents;

import org.springframework.data.mongodb.core.mapping.Document;

import ai.gebo.model.base.GBaseObject;

/**
 * AI generated comments
 * Represents a local endpoint mirror in the Gebo AI system.
 * This class is a part of the knowledge base model and
 * is used to map the local mirror information of an endpoint
 * in the GeBo AI working directory.
 */
@Document
public class GLocalEndpointMirror extends GBaseObject {

    // The system code associated with the endpoint mirror.
    public String systemCode = null;

    // The endpoint code used to identify the mirror endpoint.
    public String endPointCode = null;

    // The relative path to the local Gebo AI working directory mirror folder.
    public String localGeboAIWorkDirectoryRelativeMirrorFolder = null;

    /**
     * Default constructor for creating a GLocalEndpointMirror instance.
     */
    public GLocalEndpointMirror() {
        // No initialization required at the moment.
    }

    /**
     * Retrieves the system code of the endpoint mirror.
     *
     * @return the system code as a String.
     */
    public String getSystemCode() {
        return systemCode;
    }

    /**
     * Sets the system code of the endpoint mirror.
     *
     * @param systemCode the system code to set.
     */
    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }

    /**
     * Retrieves the endpoint code of the mirror.
     *
     * @return the endpoint code as a String.
     */
    public String getEndPointCode() {
        return endPointCode;
    }

    /**
     * Sets the endpoint code for the mirror.
     *
     * @param endPointCode the endpoint code to set.
     */
    public void setEndPointCode(String endPointCode) {
        this.endPointCode = endPointCode;
    }

    /**
     * Retrieves the relative path to the local Gebo AI work directory mirror folder.
     *
     * @return the relative mirror folder path as a String.
     */
    public String getLocalGeboAIWorkDirectoryRelativeMirrorFolder() {
        return localGeboAIWorkDirectoryRelativeMirrorFolder;
    }

    /**
     * Sets the relative path to the local Gebo AI work directory mirror folder.
     *
     * @param localGeboAIWorkDirectoryRelativeMirrorFolder the mirror folder path to set.
     */
    public void setLocalGeboAIWorkDirectoryRelativeMirrorFolder(String localGeboAIWorkDirectoryRelativeMirrorFolder) {
        this.localGeboAIWorkDirectoryRelativeMirrorFolder = localGeboAIWorkDirectoryRelativeMirrorFolder;
    }

}