/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.architecture.oauth2.client.services.impl;

import org.springframework.data.mongodb.repository.MongoRepository;

import ai.gebo.architecture.oauth2.client.model.Oauth2ModelSecretWrapper;
import ai.gebo.architecture.oauth2.client.model.Oauth2ModelSecretWrapperId;

/**
 * Gebo.ai comment agent
 * Repository interface for performing CRUD operations on Oauth2ModelSecretWrapper entities.
 * This interface extends the MongoRepository, leveraging Spring Data MongoDB's capabilities 
 * to provide abstracted data access capabilities without boilerplate code.
 */
interface Oauth2ModelSecretWrapperRepository
		extends MongoRepository<Oauth2ModelSecretWrapper, Oauth2ModelSecretWrapperId> {

}