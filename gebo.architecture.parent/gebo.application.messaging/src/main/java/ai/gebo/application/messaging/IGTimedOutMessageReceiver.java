/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.application.messaging;

import java.util.Date;

/**
 * Gebo.ai comment agent
 *
 * Interface representing a message receiver that includes a timeout mechanism.
 * It extends `IGMessageReceiver` to inherit basic message receiving functionalities.
 */
public interface IGTimedOutMessageReceiver extends IGMessageReceiver {

    /**
     * Retrieves the timeout duration for receiving messages.
     *
     * @return the duration in milliseconds after which the message receiving is considered timed out.
     */
    public long getReceivingTimeout();

    /**
     * Called when a timeout event occurs during message receiving.
     *
     * @param waitStartDate the date when the waiting period started.
     */
    public void onTimeoutEvent(Date waitStartDate);
    
}