/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.core.messages;

import java.util.Date;

import ai.gebo.application.messaging.model.GBaseMessagePayload;
import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.model.base.GObjectRef;
import lombok.Data;

/**
 * AI generated comments
 * A payload class used for acknowledging the publish operation of a project endpoint.
 * This class extends from {@link GBaseMessagePayload} and contains details about 
 * the project endpoint, correlation details, start time, and job identification.
 */
@Data
public class PublishProjectEndpointAckMessagePayload extends GBaseMessagePayload{

    /** 
     * Reference to the project endpoint object.
     */
    private GObjectRef<GProjectEndpoint> projectEndpoint = null;

    /** 
     * Unique identifier to correlate messages or operations.
     */
    private String correlationId = null;

    /** 
     * The start time when the operation or process began.
     */
    private Date startTime = null;

    /** 
     * Identifier for the specific job or operation instance.
     */
    private String jobId = null;

}