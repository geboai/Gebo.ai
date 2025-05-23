/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.application.messaging.external;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the data structure for an external receiver interface.
 * Provides methods to manage payload types accepted by the external receiver.
 * Extends the AbstractExternalMessageSystemData class.
 * 
 * Gebo.ai comment agent
 */
public class ExternalReceiverIfaceData extends AbstractExternalMessageSystemData {

    // List of accepted payload types for this external receiver
    private List<String> acceptedPayloadTypes = new ArrayList<String>();
    
    // Flag to indicate if all payload types are accepted
    private boolean isAcceptEveryPayloadType = false;

    /**
     * Retrieves the list of payload types accepted by this external receiver.
     *
     * @return List of accepted payload types.
     */
    public List<String> getAcceptedPayloadTypes() {
        return acceptedPayloadTypes;
    }

    /**
     * Sets the list of payload types that this external receiver will accept.
     *
     * @param acceptedPayloadTypes List of payload types to be accepted.
     */
    public void setAcceptedPayloadTypes(List<String> acceptedPayloadTypes) {
        this.acceptedPayloadTypes = acceptedPayloadTypes;
    }

    /**
     * Checks if this external receiver is set to accept all payload types.
     *
     * @return true if all payload types are accepted, false otherwise.
     */
    public boolean isAcceptEveryPayloadType() {
        return isAcceptEveryPayloadType;
    }

    /**
     * Sets whether this external receiver should accept all payload types.
     *
     * @param isAcceptEveryPayloadType Boolean flag to set acceptance of all payload types.
     */
    public void setAcceptEveryPayloadType(boolean isAcceptEveryPayloadType) {
        this.isAcceptEveryPayloadType = isAcceptEveryPayloadType;
    }
}