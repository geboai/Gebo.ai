/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.application.messaging.model;

import java.util.Date;
import java.util.UUID;

import ai.gebo.application.messaging.IGMessagePayloadType;

/**
 * Gebo.ai comment agent
 * GBaseMessagePayload is a class implementing IGMessagePayloadType
 * that serves as a base for message payloads. It provides a unique
 * identifier and a timestamp for each message payload.
 */
public class GBaseMessagePayload implements IGMessagePayloadType {

    // Unique identifier for the payload, initialized with a random UUID
    private String payloadId = UUID.randomUUID().toString();

    // Timestamp indicating when the payload was created
    private Date timestamp = new Date();

    /**
     * Default constructor for creating a GBaseMessagePayload instance.
     */
    public GBaseMessagePayload() {
        // No initialization required beyond field declarations
    }

    /**
     * Retrieves the unique identifier for the payload.
     * 
     * @return the payload's unique identifier as a String
     */
    @Override
    public String getPayloadId() {
        return payloadId;
    }

    /**
     * Retrieves the timestamp indicating when the payload was created.
     * 
     * @return the payload's timestamp as a Date object
     */
    @Override
    public Date getTimestamp() {
        return timestamp;
    }

    /**
     * Sets the unique identifier for the payload.
     * 
     * @param payloadId the new unique identifier for the payload
     */
    public void setPayloadId(String payloadId) {
        this.payloadId = payloadId;
    }

    /**
     * Sets the timestamp indicating when the payload was created.
     * 
     * @param timestamp the new timestamp for the payload
     */
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

}