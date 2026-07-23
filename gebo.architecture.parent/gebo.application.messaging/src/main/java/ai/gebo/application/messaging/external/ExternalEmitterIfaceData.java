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
 * Gebo.ai comment agent
 * 
 * Represents data related to an external emitter interface in a messaging system.
 * This class extends from {@code AbstractExternalMessageSystemData}.
 */
public class ExternalEmitterIfaceData extends AbstractExternalMessageSystemData {

    // A list to store the types of payloads emitted by the external emitter
    private List<String> emittedPayloadTypes = new ArrayList<String>();

    // Flag to indicate this external emitter may emit any payload type,
    // bypassing the emittedPayloadTypes allow-list (see IGMessageEmitter).
    private boolean emitEveryPayloadType = false;

    /**
     * Gets the list of types of payloads that have been emitted.
     * 
     * @return A list of strings representing the emitted payload types.
     */
    public List<String> getEmittedPayloadTypes() {
        return emittedPayloadTypes;
    }

    /**
     * Sets the list of types of payloads that are to be emitted.
     * 
     * @param emittedPayloadTypes A list of strings representing the emitted payload types.
     */
    public void setEmittedPayloadTypes(List<String> emittedPayloadTypes) {
        this.emittedPayloadTypes = emittedPayloadTypes;
    }

    /**
     * @return {@code true} if this external emitter may emit any payload type,
     *         bypassing {@link #getEmittedPayloadTypes()}.
     */
    public boolean isEmitEveryPayloadType() {
        return emitEveryPayloadType;
    }

    /**
     * @param emitEveryPayloadType whether this external emitter may emit any
     *            payload type regardless of {@link #getEmittedPayloadTypes()}.
     */
    public void setEmitEveryPayloadType(boolean emitEveryPayloadType) {
        this.emitEveryPayloadType = emitEveryPayloadType;
    }
}