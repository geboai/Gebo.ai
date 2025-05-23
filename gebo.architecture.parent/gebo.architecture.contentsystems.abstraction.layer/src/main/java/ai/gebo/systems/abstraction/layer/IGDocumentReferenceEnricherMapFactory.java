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
import ai.gebo.core.messages.GAbstractContentMessageFragmentPayload;
import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.model.base.GBaseVersionableObject;

/**
 * Gebo.ai comment agent
 * Factory interface for creating document reference enricher mappers.
 */
public interface IGDocumentReferenceEnricherMapFactory {

    /**
     * Contains mappers used for enriching content items and payloads.
     */
    public static class EnricherMappers {

        // Function for mapping GBaseVersionableObject instances
        private final java.util.function.Function<GBaseVersionableObject, GBaseVersionableObject> contentItemMapper;
        
        // Function for mapping GAbstractContentMessageFragmentPayload instances
        private final java.util.function.Function<GAbstractContentMessageFragmentPayload, GAbstractContentMessageFragmentPayload> contentPayloadMapper;

        /**
         * Constructs an EnricherMappers object with the provided mapper functions.
         * 
         * @param contentItemMapper Function to map content items.
         * @param contentPayloadMapper Function to map content payloads.
         */
        public EnricherMappers(java.util.function.Function<GBaseVersionableObject, GBaseVersionableObject> contentItemMapper, java.util.function.Function<GAbstractContentMessageFragmentPayload, GAbstractContentMessageFragmentPayload> contentPayloadMapper) {
            this.contentItemMapper = contentItemMapper;
            this.contentPayloadMapper = contentPayloadMapper;
        }

        /**
         * Retrieves the function to map content items.
         * 
         * @return A function that maps GBaseVersionableObject instances.
         */
        public java.util.function.Function<GBaseVersionableObject, GBaseVersionableObject> getContentItemMapper() {
            return contentItemMapper;
        }

        /**
         * Retrieves the function to map content payloads.
         * 
         * @return A function that maps GAbstractContentMessageFragmentPayload instances.
         */
        public java.util.function.Function<GAbstractContentMessageFragmentPayload, GAbstractContentMessageFragmentPayload> getContentPayloadMapper() {
            return contentPayloadMapper;
        }

    }

    /**
     * Generates enricher mappers based on the provided project endpoint.
     * 
     * @param endpoint The endpoint of the project.
     * @return An EnricherMappers instance containing the mapping functions.
     * @throws GeboContentHandlerSystemException if an error occurs while handling the content.
     */
    public EnricherMappers mappers(GProjectEndpoint endpoint) throws GeboContentHandlerSystemException;
}