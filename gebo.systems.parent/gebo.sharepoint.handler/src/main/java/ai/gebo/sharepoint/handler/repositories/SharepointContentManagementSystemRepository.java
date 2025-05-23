/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.sharepoint.handler.repositories;

import ai.gebo.architecture.persistence.IGBaseMongoDBRepository;
import ai.gebo.sharepoint.handler.GSharepointContentManagementSystem;

/**
 * AI generated comments
 * Repository interface for managing Sharepoint Content Management System entities in MongoDB.
 * This interface extends the base MongoDB repository and specializes in CRUD operations
 * for GSharepointContentManagementSystem objects.
 */
public interface SharepointContentManagementSystemRepository extends IGBaseMongoDBRepository<GSharepointContentManagementSystem>{
	/**
	 * Returns the class type being managed by this repository.
	 * This implementation overrides the default method from the parent interface
	 * to specifically return GSharepointContentManagementSystem class.
	 * 
	 * @return Class object for GSharepointContentManagementSystem
	 */
	@Override
	default Class<GSharepointContentManagementSystem> getManagedType() {
		
		return GSharepointContentManagementSystem.class;
	}
}