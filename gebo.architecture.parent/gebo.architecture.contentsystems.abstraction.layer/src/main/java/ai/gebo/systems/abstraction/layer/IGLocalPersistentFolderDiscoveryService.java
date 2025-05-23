/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.systems.abstraction.layer;

import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.knlowledgebase.model.systems.GContentManagementSystem;

// Gebo.ai comment agent`
/**
 * This interface defines a service for discovering local persistent folder paths.
 */
public interface IGLocalPersistentFolderDiscoveryService {

    /**
     * Retrieves the local persistent folder path associated with the specified content management system and project endpoint.
     *
     * @param contentSystem The content management system for which the local folder is being retrieved.
     * @param projectEndpoint The project endpoint associated with the content management system.
     * @return The path to the local persistent folder as a String.
     * @throws GeboContentHandlerSystemException If an error occurs while retrieving the folder path.
     */
    public String getLocalPersistentFolder(GContentManagementSystem contentSystem, GProjectEndpoint projectEndpoint)
            throws GeboContentHandlerSystemException;
}