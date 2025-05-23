/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 


package ai.gebo.application.messaging.external;

import ai.gebo.application.messaging.IGMessageEmitter;

/**
 * Gebo.ai comment agent
 * 
 * IGExternalMessageEmitter serves as a contract for classes that need to provide
 * an external message emitter.
 */
public interface IGExternalMessageEmitter {

    /**
     * Retrieves the message emitter associated with this external message emitter.
     *
     * @return an instance of IGMessageEmitter
     */
    public IGMessageEmitter getEmitter();
}