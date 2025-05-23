/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.application.messaging.external;

import ai.gebo.application.messaging.IGMessageReceiver;

/**
 * Gebo.ai comment agent
 *
 * Interface representing an external message receiver in the Gebo application messaging system.
 * Provides a method to retrieve the internal message receiver instance.
 */
public interface IGExternalMessageReceiver {

    /**
     * Retrieves the internal message receiver associated with this external receiver.
     * 
     * @return an instance of IGMessageReceiver
     */
    public IGMessageReceiver getReceiver();
}