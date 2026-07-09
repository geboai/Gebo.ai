/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.mcpclient.content.handler.repository;

import ai.gebo.knowledgebase.repositories.IGBaseMongoDBProjectEndpointRepository;
import ai.gebo.mcpclient.content.handler.MCPClientProjectEndpoint;

/**
 * MongoDB repository for {@link MCPClientProjectEndpoint} entities.
 */
public interface MCPClientProjectEndpointRepository
		extends IGBaseMongoDBProjectEndpointRepository<MCPClientProjectEndpoint> {

	@Override
	default Class<MCPClientProjectEndpoint> getManagedType() {
		return MCPClientProjectEndpoint.class;
	}
}
