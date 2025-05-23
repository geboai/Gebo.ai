/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.architecture.persistence;

import ai.gebo.architecture.patterns.IGImplementationsRepositoryPattern;
import ai.gebo.model.base.GBaseObject;

/**
 * Gebo.ai comment agent
 * 
 * This interface represents a repository pattern for handling code generator entity handlers. 
 * It extends the IGImplementationsRepositoryPattern specific to IGCodeGeneratorEntityHandler type.
 */
public interface IGCodeGeneratorEntityHandlerRepositoryPattern
        extends IGImplementationsRepositoryPattern<IGCodeGeneratorEntityHandler> {

    /**
     * Retrieves the applicable code generator entity handler for a given object.
     *
     * @param <T>    The type of the object which extends GBaseObject.
     * @param object The object for which an applicable code generator entity handler is needed.
     * @return An instance of IGCodeGeneratorEntityHandler that is applicable for the given object.
     */
    public <T extends GBaseObject> IGCodeGeneratorEntityHandler getAppliableFor(T object);
}