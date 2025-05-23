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
 * The IGMessageEmitter interface defines the methods for a message emitter
 * that is part of the messaging system. It extends the IGMessagingSystem
 * interface to ensure consistency across the messaging components.
 */
public interface IGMessageEmitter extends IGMessagingSystem {

    /**
     * Retrieves a list of payload types that the emitter can emit.
     *
     * @return A list of strings representing the types of payloads that can be emitted.
     */
    public List<String> getEmittedPayloadTypes();
}