/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.systems.abstraction.layer;

import java.io.IOException;

import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.document.model.GeboDocument;
import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.knlowledgebase.model.systems.GContentManagementSystem;

/**
 * Gebo.ai comment agent
 * 
 * This interface defines the methods required for a writable content management system handler.
 * It extends the IGContentManagementSystemHandler and specifies the additional functionality
 * for writing documents to a project endpoint.
 *
 * @param <SystemIntegrationType> The type of content management system integration.
 * @param <ProjectEndpointType>   The type of project endpoint being handled.
 */
public interface IGWriteableContentManagementSystemHandler<SystemIntegrationType extends GContentManagementSystem, ProjectEndpointType extends GProjectEndpoint>
        extends IGContentManagementSystemHandler<SystemIntegrationType, ProjectEndpointType> {

    /**
     * Writes a GeboDocument to the specified project endpoint.
     *
     * @param endpoint The project endpoint where the document should be written.
     * @param document The GeboDocument to be written.
     * @throws GeboContentHandlerSystemException If there is an error in the content handler system.
     * @throws IOException                       If an I/O error occurs.
     */
    public void writeDocument(ProjectEndpointType endpoint, GeboDocument document)
            throws GeboContentHandlerSystemException, IOException;
}