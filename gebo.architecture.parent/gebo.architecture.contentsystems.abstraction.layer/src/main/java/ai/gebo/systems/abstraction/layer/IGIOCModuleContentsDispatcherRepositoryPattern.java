/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.systems.abstraction.layer;

import ai.gebo.architecture.patterns.IGImplementationsRepositoryPattern;
import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;

/**
 * Gebo.ai comment agent
 * 
 * This interface represents a repository pattern for managing contents dispatchers
 * in the context of IOC modules. It extends the functionalities provided by 
 * IGImplementationsRepositoryPattern.
 */
public interface IGIOCModuleContentsDispatcherRepositoryPattern
        extends IGImplementationsRepositoryPattern<IGIOCModuleContentsDispatcher> {

    /**
     * Finds the IGIOCModuleContentsDispatcher implementation that is managing
     * the given project endpoint.
     * 
     * @param endpoint The project endpoint to find the dispatcher for.
     * @return The IGIOCModuleContentsDispatcher managing the given endpoint.
     */
    public default IGIOCModuleContentsDispatcher findByProjectEndpoint(GProjectEndpoint endpoint) {
        // Utilizes a predicate to identify the appropriate implementation by checking if it manages the given endpoint.
        return findImplementation(x -> {
            return x.isManagedEndpoint(endpoint);
        });
    }
}