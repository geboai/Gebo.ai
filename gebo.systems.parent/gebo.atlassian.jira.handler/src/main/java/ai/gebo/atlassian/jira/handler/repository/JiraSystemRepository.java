/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

/**
 * Repository interface for managing Jira system entities in MongoDB.
 * This interface extends the base MongoDB repository to handle GJiraSystem objects.
 * AI generated comments
 */
package ai.gebo.atlassian.jira.handler.repository;

import ai.gebo.architecture.persistence.IGBaseMongoDBRepository;
import ai.gebo.atlassian.jira.handler.GJiraSystem;

/**
 * Provides CRUD operations for GJiraSystem entities in a MongoDB database.
 * Extends the base MongoDB repository interface.
 */
public interface JiraSystemRepository extends IGBaseMongoDBRepository<GJiraSystem> {
	/**
	 * Returns the class type managed by this repository.
	 * This implementation specifies GJiraSystem as the managed entity type.
	 * 
	 * @return The Class object for GJiraSystem
	 */
	@Override
	default Class<GJiraSystem> getManagedType() {
		return GJiraSystem.class;
	}
}