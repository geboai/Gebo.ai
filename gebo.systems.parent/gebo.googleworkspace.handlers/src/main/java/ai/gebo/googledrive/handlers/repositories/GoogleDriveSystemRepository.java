/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.googledrive.handlers.repositories;

import java.util.List;

import ai.gebo.architecture.persistence.IGBaseMongoDBRepository;
import ai.gebo.googledrive.handlers.GGoogleDriveSystem;

/**
 * AI generated comments
 * Repository interface for managing Google Drive system entities in MongoDB.
 * Extends the base MongoDB repository interface to provide specific data access
 * operations for GGoogleDriveSystem entities.
 */
public interface GoogleDriveSystemRepository extends IGBaseMongoDBRepository<GGoogleDriveSystem> {
	/**
	 * Returns the class type being managed by this repository.
	 * Implements the required method from the parent interface.
	 *
	 * @return The GGoogleDriveSystem class type
	 */
	@Override
	default Class<GGoogleDriveSystem> getManagedType() {
		return GGoogleDriveSystem.class;
	}

	/**
	 * Finds all Google Drive system entities with the specified drive access secret.
	 *
	 * @param driveAccessSecret The drive access secret to search for
	 * @return A list of matching GGoogleDriveSystem entities
	 */
	List<GGoogleDriveSystem> findByDriveAccessSecret(String driveAccessSecret);
}