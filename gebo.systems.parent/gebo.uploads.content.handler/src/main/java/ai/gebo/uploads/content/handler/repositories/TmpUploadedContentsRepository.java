/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.uploads.content.handler.repositories;

import ai.gebo.architecture.persistence.IGBaseMongoDBRepository;
import ai.gebo.uploads.content.handler.TmpUploadedContents;

/**
 * AI generated comments
 * Repository interface for managing temporary uploaded content data in MongoDB.
 * This interface extends the base MongoDB repository functionality and is 
 * specifically designed to handle temporary storage of uploaded content.
 */
public interface TmpUploadedContentsRepository extends IGBaseMongoDBRepository<TmpUploadedContents> {
	/**
	 * Gets the class type managed by this repository.
	 * This method implements the required method from the base repository interface
	 * to specify the entity type this repository works with.
	 * 
	 * @return The TmpUploadedContents class type
	 */
	@Override
	default Class<TmpUploadedContents> getManagedType() {
		return TmpUploadedContents.class;
	}
}