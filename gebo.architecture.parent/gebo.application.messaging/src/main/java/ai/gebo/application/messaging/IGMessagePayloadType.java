/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.application.messaging;

import java.io.Serializable;
import java.util.Date;

/**
 * Gebo.ai comment agent
 * 
 * An interface that defines the methods required for a message payload type.
 * Implementations of this interface should provide a unique identifier for 
 * the payload and the timestamp when the payload was created.
 */
public interface IGMessagePayloadType extends Serializable{
    
    /**
     * Retrieve the unique identifier for the payload.
     * 
     * @return A String representing the payload's unique identifier.
     */
    public String getPayloadId();

    /**
     * Get the timestamp indicating when the payload was created.
     * 
     * @return A Date object representing the timestamp of the payload creation.
     */
    public Date getTimestamp();
}