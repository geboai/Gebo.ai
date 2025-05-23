/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.sharepoint.handler.repositories;

import ai.gebo.knowledgebase.repositories.IGBaseMongoDBProjectEndpointRepository;
import ai.gebo.sharepoint.handler.GSharepointProjectEndpoint;

/**
 * AI generated comments
 * Repository interface for managing SharePoint project endpoints in MongoDB.
 * This interface extends the base MongoDB repository to provide specific
 * functionality for GSharepointProjectEndpoint entities.
 */
public interface SharepointProjectEndpointRepository
		extends IGBaseMongoDBProjectEndpointRepository<GSharepointProjectEndpoint> {
	/**
	 * Returns the managed entity type class.
	 * This implementation specifies GSharepointProjectEndpoint as the entity type
	 * that this repository manages.
	 * 
	 * @return The GSharepointProjectEndpoint class
	 */
	@Override
	default Class<GSharepointProjectEndpoint> getManagedType() {
		
		return GSharepointProjectEndpoint.class;
	}
}