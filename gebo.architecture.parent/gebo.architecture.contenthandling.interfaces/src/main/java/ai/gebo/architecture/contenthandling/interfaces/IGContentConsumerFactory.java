/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.architecture.contenthandling.interfaces;

import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;

/**
 * Gebo.ai comment agent
 * 
 * Factory interface for creating instances of {@link IGContentConsumer}.
 */
public interface IGContentConsumerFactory {

    /**
     * Creates a content consumer associated with a specified project endpoint.
     * 
     * @param endpoint the project endpoint for which the content consumer is to be created.
     * @return a new instance of {@link IGContentConsumer}.
     */
    public IGContentConsumer create(GProjectEndpoint endpoint);
}