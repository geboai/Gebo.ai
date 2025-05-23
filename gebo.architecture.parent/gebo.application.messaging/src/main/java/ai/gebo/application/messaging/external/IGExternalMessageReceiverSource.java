/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.application.messaging.external;

import java.util.List;

/**
 * Gebo.ai comment agent
 * 
 * Interface representing a source for receiving external messages.
 * Implementations of this interface should provide functionality
 * to retrieve an identifier and a list of external message receivers.
 */
public interface IGExternalMessageReceiverSource {
    
    /**
     * Retrieves the unique identifier of this external message receiver source.
     * 
     * @return a String representing the unique ID of the source.
     */
    public String getId();
    
    /**
     * Retrieves a list of external message receivers associated with this source.
     * 
     * @return a List of IGExternalMessageReceiver instances.
     */
    public List<IGExternalMessageReceiver> getExternalReceivers();
}