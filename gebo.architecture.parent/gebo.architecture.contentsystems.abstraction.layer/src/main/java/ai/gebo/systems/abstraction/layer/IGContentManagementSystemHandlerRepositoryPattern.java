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
 * Interface for handling operations related to content management system handlers.
 * Extends the IGImplementationsRepositoryPattern to support implementation
 * retrieval for IGContentManagementSystemHandler instances.
 */
public interface IGContentManagementSystemHandlerRepositoryPattern
        extends IGImplementationsRepositoryPattern<IGContentManagementSystemHandler> {

    /**
     * Finds the content management system handler associated with the specified endpoint.
     *
     * @param endpoint The project endpoint to find the handler for.
     * @return The content management system handler that manages the given endpoint.
     */
    public default IGContentManagementSystemHandler findByHandledEndpoint(GProjectEndpoint endpoint) {
        // Use the findImplementation method to locate the handler managing the endpoint
        return this.findImplementation(x -> {
            // Check if the endpoint is managed by the current handler
            return x.isManagedEndpoint(endpoint);
        });
    }

}