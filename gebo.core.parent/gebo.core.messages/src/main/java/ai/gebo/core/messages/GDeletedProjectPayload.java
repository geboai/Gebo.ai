/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.core.messages;

import ai.gebo.application.messaging.model.GBaseMessagePayload;
import ai.gebo.knlowledgebase.model.projects.GProject;

/**
 * AI generated comments
 * The GDeletedProjectPayload class extends GBaseMessagePayload and represents
 * the payload for a deleted project message.
 */
public class GDeletedProjectPayload extends GBaseMessagePayload {

    // Field to store the project related to the deleted project action
    private GProject project = null;

    /**
     * Default constructor for GDeletedProjectPayload.
     */
    public GDeletedProjectPayload() {

    }

    /**
     * Retrieves the project associated with this payload.
     * 
     * @return the project related to the deleted project payload.
     */
    public GProject getProject() {
        return project;
    }

    /**
     * Sets the project associated with this payload.
     * 
     * @param project the project to be associated with the deleted project payload.
     */
    public void setProject(GProject project) {
        this.project = project;
    }

}