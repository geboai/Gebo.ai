/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.googledrive.handlers.repositories;

import ai.gebo.googledrive.handlers.GGoogleDriveProjectEndpoint;
import ai.gebo.knowledgebase.repositories.IGBaseMongoDBProjectEndpointRepository;

/**
 * AI generated comments
 * Repository interface for managing Google Drive project endpoints in MongoDB.
 * This interface extends the base MongoDB project endpoint repository and is 
 * specialized for Google Drive endpoints.
 */
public interface GoogleDriveProjectEndpointRepository
		extends IGBaseMongoDBProjectEndpointRepository<GGoogleDriveProjectEndpoint> {
	/**
	 * Returns the class type managed by this repository.
	 * This implementation specifies that this repository manages GGoogleDriveProjectEndpoint entities.
	 * 
	 * @return The class object for GGoogleDriveProjectEndpoint
	 */
	@Override
	default Class<GGoogleDriveProjectEndpoint> getManagedType() {
		
		return GGoogleDriveProjectEndpoint.class;
	}
}