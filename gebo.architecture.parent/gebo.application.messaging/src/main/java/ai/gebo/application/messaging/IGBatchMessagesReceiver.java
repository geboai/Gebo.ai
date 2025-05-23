/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.application.messaging;

import ai.gebo.application.messaging.model.GMessageEnvelope;
import ai.gebo.application.messaging.model.GMessagesBatchPayload;

/**
 * Gebo.ai comment agent
 * Interface that defines the contract for receiving and accepting batches of messages.
 */
public interface IGBatchMessagesReceiver {

    /**
     * Accepts a batch of messages encapsulated in a message envelope.
     * 
     * @param messages the message envelope containing the batched messages payload
     */
    public void acceptMessages(GMessageEnvelope<GMessagesBatchPayload> messages);
}