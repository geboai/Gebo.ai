/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.filesystem.content.handler.repositories;

import ai.gebo.filesystem.content.handler.GFilesystemProjectEndpoint;
import ai.gebo.knowledgebase.repositories.IGBaseMongoDBProjectEndpointRepository;

/**
 * AI generated comments
 * 
 * Repository interface for managing filesystem project endpoints in MongoDB.
 * This interface extends the base MongoDB project endpoint repository interface
 * specifically for GFilesystemProjectEndpoint entities.
 */
public interface FilesystemProjectEndpointRepository
		extends IGBaseMongoDBProjectEndpointRepository<GFilesystemProjectEndpoint> {
	/**
	 * Returns the class type managed by this repository.
	 * This method is implemented to fulfill the contract required by the parent interface.
	 * 
	 * @return The GFilesystemProjectEndpoint class that this repository manages
	 */
	@Override
	default Class<GFilesystemProjectEndpoint> getManagedType() {

		return GFilesystemProjectEndpoint.class;
	}

	
}