/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.userspace.handler.repository;

import ai.gebo.knowledgebase.repositories.IGBaseMongoDBProjectEndpointRepository;
import ai.gebo.userspace.handler.GUserspaceProjectEndpoint;

/**
 * AI generated comments
 * Repository interface for managing GUserspaceProjectEndpoint entities in MongoDB.
 * This interface extends the base MongoDB repository to provide specific functionality
 * for working with userspace project endpoints.
 */
public interface UserspaceProjectEndpointRepository extends IGBaseMongoDBProjectEndpointRepository<GUserspaceProjectEndpoint>{
	/**
	 * Returns the class type managed by this repository.
	 * This method overrides the parent interface's method to specify
	 * that this repository handles GUserspaceProjectEndpoint objects.
	 * 
	 * @return The Class object representing GUserspaceProjectEndpoint
	 */
	@Override
	default Class<GUserspaceProjectEndpoint> getManagedType() {
		
		return GUserspaceProjectEndpoint.class;
	}
}