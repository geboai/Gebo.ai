/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.atlassian.confluence.handler.repositories;

import ai.gebo.architecture.persistence.IGBaseMongoDBRepository;
import ai.gebo.atlassian.confluence.handler.GConfluenceSystem;

/**
 * AI generated comments
 * Repository interface for managing GConfluenceSystem entities in MongoDB.
 * This interface extends the base MongoDB repository interface to provide
 * specific functionality for Confluence system data persistence.
 */
public interface ConfluenceSystemRepository extends IGBaseMongoDBRepository<GConfluenceSystem> {
	/**
	 * Returns the class type being managed by this repository.
	 * This implementation identifies GConfluenceSystem as the entity type.
	 * 
	 * @return The GConfluenceSystem class
	 */
	@Override
	default Class<GConfluenceSystem> getManagedType() {
		return GConfluenceSystem.class;
	}
}