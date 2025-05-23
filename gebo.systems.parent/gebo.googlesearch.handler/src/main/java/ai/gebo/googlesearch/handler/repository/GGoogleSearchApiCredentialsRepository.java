/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.googlesearch.handler.repository;

import ai.gebo.architecture.persistence.IGBaseMongoDBRepository;
import ai.gebo.googlesearch.handler.model.GGoogleSearchApiCredentials;

/**
 * AI generated comments
 * Repository interface for managing Google Search API credentials in MongoDB.
 * This interface extends the base MongoDB repository to provide CRUD operations
 * for GGoogleSearchApiCredentials objects.
 */
public interface GGoogleSearchApiCredentialsRepository extends IGBaseMongoDBRepository<GGoogleSearchApiCredentials> {
	/**
	 * Gets the managed entity type for this repository.
	 * 
	 * @return The GGoogleSearchApiCredentials class type
	 */
	@Override
	default Class<GGoogleSearchApiCredentials> getManagedType() {
		return GGoogleSearchApiCredentials.class;
	}
}