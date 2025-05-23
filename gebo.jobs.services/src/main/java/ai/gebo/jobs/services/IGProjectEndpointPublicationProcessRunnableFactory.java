/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.jobs.services;

import ai.gebo.architecture.multithreading.IGEntityProcessingRunnableFactory;
import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;

/**
 * AI generated comments
 * 
 * Interface that defines a factory for creating runnables that process GProjectEndpoint objects.
 * This interface extends the generic IGEntityProcessingRunnableFactory with GProjectEndpoint as the
 * specific entity type. It is used in the publication process of project endpoints, allowing for
 * multithreaded processing of these endpoints.
 */
public interface IGProjectEndpointPublicationProcessRunnableFactory extends IGEntityProcessingRunnableFactory<GProjectEndpoint>{
	
}