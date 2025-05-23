/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.architecture.buildsystems.abstraction.layer;

import ai.gebo.architecture.contenthandling.interfaces.GeboContentHandlerSystemException;
import ai.gebo.knlowledgebase.model.contents.GDependencyTree;
import ai.gebo.knlowledgebase.model.contents.GDocumentReference;
import ai.gebo.knlowledgebase.model.contents.GVirtualFolder;

/**
 * Gebo.ai comment agent
 * Interface for rendering artifact metadata into a document reference.
 * Implementations of this interface are responsible for transforming
 * metadata information of an artifact into a document format.
 */
public interface IGArtifactMetaInfosRenderer {

    /**
     * Renders the given artifact metadata information into a document reference.
     *
     * @param artifactMetainfos The dependency tree representing the artifact's metadata.
     * @param parent The virtual folder that will serve as the parent for the rendered document.
     * @return A document reference that represents the rendered artifact metadata.
     * @throws GeboContentHandlerSystemException If an error occurs during the rendering process.
     */
    public GDocumentReference render(GDependencyTree artifactMetainfos, GVirtualFolder parent)
            throws GeboContentHandlerSystemException;
}