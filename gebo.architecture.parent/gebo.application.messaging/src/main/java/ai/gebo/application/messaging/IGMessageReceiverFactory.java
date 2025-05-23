/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.application.messaging;

import java.util.List;

/**
 * Gebo.ai comment agent
 * A factory interface for creating message receivers in a messaging system.
 * Provides methods to configure and create message receiver instances
 * with specific payload acceptance criteria and thread management.
 */
public interface IGMessageReceiverFactory extends IGMessagingSystem {

    /**
     * Retrieves a list of accepted payload types for the message receivers
     * created by this factory.
     * 
     * @return a list of strings representing the payload types that are accepted.
     */
    public List<String> getAcceptedPayloadTypes();

    /**
     * Gets the number of simultaneous receiver instances that this factory can
     * produce or manage.
     * 
     * @return the pool cardinality as an integer.
     */
    public int getPoolCardinality();

    /**
     * Determines whether message receivers should use the sender's thread
     * for processing.
     * 
     * @return true if sender thread should be used, otherwise false.
     */
    public boolean useSenderThread();

    /**
     * Checks if the factory is set up to accept every type of payload.
     * 
     * @return true if every payload type is accepted, otherwise false.
     */
    public boolean isAcceptEveryPayloadType();

    /**
     * Creates a new instance of a message receiver according to the
     * factory's configuration.
     * 
     * @return a new instance of IGMessageReceiver.
     */
    public IGMessageReceiver create();
}