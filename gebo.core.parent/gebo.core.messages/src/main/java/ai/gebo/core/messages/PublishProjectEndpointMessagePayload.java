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
import ai.gebo.model.base.GObjectRef;
import lombok.Data;

/**
 * AI generated comments
 * This class represents the payload for a message that is used to publish a project endpoint.
 * It extends the GBaseMessagePayload class and includes information specific to the project endpoint being published.
 */
@Data
public class PublishProjectEndpointMessagePayload extends GBaseMessagePayload {
    
    /**
     * A reference to the project endpoint that is being published.
     */
    private GObjectRef<GProjectEndpoint> projectEndpoint = null;
    
    /**
     * An optional correlation ID that can be used to link the message to a specific request or workflow.
     */
    private String correlationId = null;

}