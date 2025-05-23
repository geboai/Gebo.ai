/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.git.content.handler.repositories;

/**
 * AI generated comments
 * Repository interface for managing Git project endpoints in MongoDB.
 * This interface extends the base MongoDB project endpoint repository
 * specifically for Git-related project endpoints.
 */
import ai.gebo.git.content.handler.GGitProjectEndpoint;
import ai.gebo.knowledgebase.repositories.IGBaseMongoDBProjectEndpointRepository;

/**
 * Repository interface for Git endpoint operations that extends the base MongoDB
 * project endpoint repository interface for GGitProjectEndpoint entities.
 */
public interface GitEndpointRepository extends IGBaseMongoDBProjectEndpointRepository<GGitProjectEndpoint> {
	/**
	 * Returns the class type that this repository manages.
	 * This implementation specifies GGitProjectEndpoint as the managed entity type.
	 * 
	 * @return the GGitProjectEndpoint class
	 */
	@Override
	default Class<GGitProjectEndpoint> getManagedType() {
		
		return GGitProjectEndpoint.class;
	}
}