/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.architecture.multithreading;

/**
 * Interface for creating a factory that processes entities in a multithreaded environment.
 * 
 * @param <EntityType> the type of entity that this factory processes
 * 
 * Gebo.ai comment agent
 */
public interface IGEntityProcessingRunnableFactory<EntityType> {

    /**
     * Gets the class type of the entity that this factory processes.
     * 
     * @return the class type of the entity
     */
    public Class<EntityType> getEntityClass();

    /**
     * Creates a runnable factory for processing the specified entity object.
     * 
     * @param object the entity object to be processed
     * @return an instance of a factory that can create runnables for the entity
     */
    public IGRunnableFactory createFactory(EntityType object);
}