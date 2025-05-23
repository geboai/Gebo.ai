/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.architecture.multithreading;

import ai.gebo.architecture.patterns.IGImplementationsRepositoryPattern;

/**
 * Gebo.ai comment agent
 * 
 * This interface extends IGImplementationsRepositoryPattern for IGEntityProcessingRunnableFactory.
 * It provides a method to retrieve a specific IGEntityProcessingRunnableFactory based on the entity type.
 *
 * @param <EntityType> the type of entity to be processed
 */
public interface IGEntityProcessingRunnableFactoryRepositoryPattern
        extends IGImplementationsRepositoryPattern<IGEntityProcessingRunnableFactory> {

    /**
     * Retrieves an implementation of IGEntityProcessingRunnableFactory for the given entity type.
     *
     * @param <EntityType> the type of entity to be processed
     * @param type the class type of the entity
     * @return an instance of IGEntityProcessingRunnableFactory that processes the specified entity type
     */
    public default <EntityType> IGEntityProcessingRunnableFactory<EntityType> get(Class<EntityType> type) {
        // Stream through the implementations and find one that handles the entity type
        return getImplementations().stream().filter(x -> type.isAssignableFrom(x.getEntityClass())).findAny().get();
    }
}