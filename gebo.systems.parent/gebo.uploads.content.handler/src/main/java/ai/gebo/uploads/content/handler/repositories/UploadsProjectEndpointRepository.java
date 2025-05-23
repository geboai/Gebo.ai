/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

/**
 * AI generated comments
 * Repository interface for managing uploads project endpoints in MongoDB.
 * This interface extends the base MongoDB project endpoint repository for specific handling
 * of upload-related project endpoints.
 */
package ai.gebo.uploads.content.handler.repositories;

import ai.gebo.knowledgebase.repositories.IGBaseMongoDBProjectEndpointRepository;
import ai.gebo.uploads.content.handler.GUploadsProjectEndpoint;

/**
 * Repository interface for CRUD operations on GUploadsProjectEndpoint entities.
 * Extends the base MongoDB repository interface to provide specialized 
 * functionality for upload project endpoints.
 */
public interface UploadsProjectEndpointRepository
		extends IGBaseMongoDBProjectEndpointRepository<GUploadsProjectEndpoint> {
	/**
	 * Returns the class type being managed by this repository.
	 * This implementation provides the default class type of GUploadsProjectEndpoint.
	 * 
	 * @return Class object representing GUploadsProjectEndpoint
	 */
	@Override
	default Class<GUploadsProjectEndpoint> getManagedType() {
		return GUploadsProjectEndpoint.class;
	}

	
}