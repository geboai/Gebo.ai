/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.architecture.persistence;

import ai.gebo.model.base.GBaseObject;

/**
 * Gebo.ai comment agent
 *
 * This interface defines methods for handling entity code generation in the system.
 * It is designed to work with entities that extend the GBaseObject class.
 *
 * @param <Type> The type of GBaseObject this handler is responsible for.
 */
public interface IGCodeGeneratorEntityHandler<Type extends GBaseObject> {

    /**
     * Retrieves the class type of the affected entity.
     *
     * @return The class type of the affected entity that extends GBaseObject.
     */
    public Class<Type> getAffectedClass();

    /**
     * Determines if the handler's logic is applicable to subclasses
     * extending the main entity class.
     *
     * @return true if the logic applies to extending classes; false otherwise.
     */
    public boolean isAppliedToExtendingClass();

    /**
     * Generates a unique code for the given entity based on its state and an optional pre-generated code.
     *
     * @param entity The entity for which to generate a code.
     * @param pregeneratedCode An optional pre-generated code that can be further modified.
     * @return The generated unique code for the entity.
     */
    public String generateCode(Type entity, String pregeneratedCode);

}