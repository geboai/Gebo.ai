/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.ai.app.virtualremotefs.tests.architecture;

import ai.gebo.knowledgebase.repositories.IGBaseMongoDBProjectEndpointRepository;

/**
 * AI generated comments
 * This interface defines the contract for a Test Virtual Filesystem Project Endpoint Repository.
 * It extends the capabilities of IGBaseMongoDBProjectEndpointRepository by specifying
 * the type of project endpoint it manages.
 *
 * @param <TestVirtualRemoteProjectEndpoint> The type of endpoint this repository will manage.
 */
public interface TestVirtualFilesystemProjectEndpointRepository
		extends IGBaseMongoDBProjectEndpointRepository<TestVirtualRemoteProjectEndpoint> {
	
	/**
	 * Provides the managed type for this repository.
	 *
	 * @return The class type that this repository manages, specifically TestVirtualRemoteProjectEndpoint.
	 */
	@Override
	default Class<TestVirtualRemoteProjectEndpoint> getManagedType() {
		return TestVirtualRemoteProjectEndpoint.class;
	}
}