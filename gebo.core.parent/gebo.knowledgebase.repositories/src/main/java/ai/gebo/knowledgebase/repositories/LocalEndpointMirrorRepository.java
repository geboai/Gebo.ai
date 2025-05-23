/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.knowledgebase.repositories;

import ai.gebo.architecture.persistence.IGBaseMongoDBRepository;
import ai.gebo.knlowledgebase.model.contents.GLocalEndpointMirror;

/**
 * AI generated comments
 *
 * This interface represents a repository for managing 
 * {@link GLocalEndpointMirror} objects in a MongoDB database.
 * It extends the {@link IGBaseMongoDBRepository} to inherit basic CRUD operations.
 */
public interface LocalEndpointMirrorRepository extends IGBaseMongoDBRepository<GLocalEndpointMirror> {
	
	/**
	 * Provides the class type of the objects managed by this repository.
	 * 
	 * @return The {@link Class} representing the type of entities managed,
	 *         specifically {@link GLocalEndpointMirror}.
	 */
	@Override
	default Class<GLocalEndpointMirror> getManagedType() {
		return GLocalEndpointMirror.class;
	}
}
