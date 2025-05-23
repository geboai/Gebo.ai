/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.application.messaging;

/**
 * Gebo.ai comment agent
 * 
 * IGTimedOutMessageReceiverFactory is an interface that extends IGMessageReceiverFactory. 
 * It provides a method to get the receiving timeout specific to this interface 
 * and another method to create a message receiver that might deal with time-sensitive operations.
 */
public interface IGTimedOutMessageReceiverFactory extends IGMessageReceiverFactory {

    /**
     * Gets the timeout duration for receiving messages.
     * Implementations of this method should return the time period 
     * (in milliseconds) after which the receiving process is considered timed out.
     * 
     * @return the timeout for receiving messages
     */
    public long getReceivingTimeout();

    /**
     * Creates a new instance of IGTimedOutMessageReceiver.
     * Overrides the create method in IGMessageReceiverFactory to provide 
     * a specific type of message receiver that may incorporate timeout logic.
     *
     * @return a new instance of IGTimedOutMessageReceiver
     */
    @Override
    public IGTimedOutMessageReceiver create();
}