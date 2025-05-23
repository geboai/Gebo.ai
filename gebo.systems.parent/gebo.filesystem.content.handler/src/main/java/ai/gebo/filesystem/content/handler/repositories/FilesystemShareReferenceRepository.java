/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.filesystem.content.handler.repositories;

import ai.gebo.architecture.persistence.IGBaseMongoDBRepository;
import ai.gebo.filesystem.content.handler.GFileSystemShareReference;

/**
 * AI generated comments
 * Repository interface for managing file system share references.
 * Extends the base MongoDB repository interface to provide CRUD operations
 * for GFileSystemShareReference objects.
 */
public interface FilesystemShareReferenceRepository extends IGBaseMongoDBRepository<GFileSystemShareReference>{
	/**
	 * Returns the class type that this repository manages.
	 * This implementation specifies GFileSystemShareReference as the managed entity type.
	 * 
	 * @return Class object representing GFileSystemShareReference
	 */
	@Override
	default Class<GFileSystemShareReference> getManagedType() {
		return GFileSystemShareReference.class;
	}
}