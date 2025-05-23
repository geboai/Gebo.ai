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
import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;

/**
 * AI generated comments
 * Represents payload data for a deleted project endpoint message.
 * This class extends GBaseMessagePayload and encapsulates details about a specific
 * project endpoint that has been deleted.
 */
public class GDeletedProjectEndpointPayload extends GBaseMessagePayload {

    // The project endpoint that has been deleted.
    private GProjectEndpoint endpoint = null;

    /**
     * Default constructor.
     * Initializes a new instance of the GDeletedProjectEndpointPayload class with no endpoint set.
     */
    public GDeletedProjectEndpointPayload() {

    }

    /**
     * Constructor with endpoint parameter.
     * Initializes a new instance of the GDeletedProjectEndpointPayload class with the specified deleted endpoint.
     * 
     * @param endpoint the project endpoint that has been deleted.
     */
    public GDeletedProjectEndpointPayload(GProjectEndpoint endpoint) {
        this.endpoint = endpoint;
    }

    /**
     * Gets the deleted project endpoint.
     * 
     * @return the deleted project endpoint.
     */
    public GProjectEndpoint getEndpoint() {
        return endpoint;
    }

    /**
     * Sets the project endpoint that has been deleted.
     * 
     * @param endpoint the project endpoint to be set as deleted.
     */
    public void setEndpoint(GProjectEndpoint endpoint) {
        this.endpoint = endpoint;
    }

}