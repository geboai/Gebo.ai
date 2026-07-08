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
    public default String getLocalPersistentFolder(GContentManagementSystem contentSystem, GProjectEndpoint projectEndpoint)
            throws GeboContentHandlerSystemException {
        return getLocalPersistentFolder(contentSystem, projectEndpoint.getClass().getName(), projectEndpoint.getCode());
    }

    /**
     * Retrieves the local persistent folder path using the endpoint identity (class name and code)
     * rather than a live endpoint instance. This lets callers that only hold a shareable reference
     * (for instance a {@code GCentralizedProjectEndpoint}'s remote reference) resolve the same folder
     * that was created for the concrete endpoint during ingestion.
     *
     * @param contentSystem      The content management system; only required to create a new mirror,
     *                           it may be {@code null} when an existing mirror is being resolved.
     * @param endpointClassName  The fully qualified class name of the concrete project endpoint.
     * @param endpointCode       The code of the project endpoint.
     * @return The path to the local persistent folder as a String.
     * @throws GeboContentHandlerSystemException If an error occurs while retrieving the folder path.
     */
    public String getLocalPersistentFolder(GContentManagementSystem contentSystem, String endpointClassName,
            String endpointCode) throws GeboContentHandlerSystemException;
}