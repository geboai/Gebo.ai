/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.atlassian.jira.handler.repository;

import ai.gebo.atlassian.jira.handler.GJiraProjectEndpoint;
import ai.gebo.knowledgebase.repositories.IGBaseMongoDBProjectEndpointRepository;

/**
 * AI generated comments
 * Repository interface for managing JIRA project endpoints in MongoDB.
 * This interface extends the base MongoDB project endpoint repository
 * with specific implementations for JIRA project endpoints.
 */
public interface JiraProjectEndpointRepository extends IGBaseMongoDBProjectEndpointRepository<GJiraProjectEndpoint> {
	/**
	 * Returns the class type managed by this repository.
	 * This method is required by the parent interface and specifies
	 * that this repository manages GJiraProjectEndpoint objects.
	 * 
	 * @return The GJiraProjectEndpoint class
	 */
	@Override
	default Class<GJiraProjectEndpoint> getManagedType() {
		
		return GJiraProjectEndpoint.class;
	}
}