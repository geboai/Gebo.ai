/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.atlassian.confluence.handler.repositories;

import ai.gebo.atlassian.confluence.handler.GConfluenceProjectEndpoint;
import ai.gebo.knowledgebase.repositories.IGBaseMongoDBProjectEndpointRepository;

/**
 * AI generated comments
 * Repository interface for Confluence project endpoints.
 * This interface extends the base MongoDB project endpoint repository
 * to provide specific functionality for Confluence project endpoints.
 */
public interface ConfluenceProjectEndpointRepository
		extends IGBaseMongoDBProjectEndpointRepository<GConfluenceProjectEndpoint> {
	/**
	 * Returns the class type that this repository manages.
	 * This implementation specifies GConfluenceProjectEndpoint as the managed type.
	 * 
	 * @return The GConfluenceProjectEndpoint class
	 */
	@Override
	default Class<GConfluenceProjectEndpoint> getManagedType() {
		
		return GConfluenceProjectEndpoint.class;
	}
}