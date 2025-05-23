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
 * Repository interface for managing Git content management systems in MongoDB.
 * This interface extends the base MongoDB repository functionality specifically for GGitContentManagementSystem entities.
 */
package ai.gebo.git.content.handler.repositories;

import ai.gebo.architecture.persistence.IGBaseMongoDBRepository;
import ai.gebo.git.content.handler.GGitContentManagementSystem;

/**
 * Repository interface to perform CRUD operations on GGitContentManagementSystem objects in MongoDB.
 * Extends the base MongoDB repository interface which provides standard database operations.
 */
public interface GitSystemsRepository
		extends IGBaseMongoDBRepository<GGitContentManagementSystem> {
	/**
	 * Returns the class type being managed by this repository.
	 * This implementation specifies GGitContentManagementSystem as the entity type.
	 * 
	 * @return The GGitContentManagementSystem class
	 */
	@Override
	default Class<GGitContentManagementSystem> getManagedType() {
		
		return GGitContentManagementSystem.class;
	}
}