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
 * The IGExternalMessageEmitterSource interface provides a contract for
 * classes that act as a source for external message emitters. Implementations
 * of this interface are responsible for providing a unique identifier and a list
 * of external message emitters.
 */
public interface IGExternalMessageEmitterSource {

    /**
     * Retrieves the unique identifier for this message emitter source.
     *
     * @return A String representing the unique identifier.
     */
    public String getId();

    /**
     * Retrieves a list of external message emitters associated with this source.
     *
     * @return A List of IGExternalMessageEmitter objects.
     */
    public List<IGExternalMessageEmitter> getExternalEmitters();
}