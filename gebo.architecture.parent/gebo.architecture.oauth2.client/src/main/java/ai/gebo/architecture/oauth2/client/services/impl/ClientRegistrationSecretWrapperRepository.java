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

import ai.gebo.architecture.oauth2.client.model.ClientRegistrationSecretWrapper;

/**
 * Gebo.ai comment agent
 * 
 * Repository interface for accessing ClientRegistrationSecretWrapper entities 
 * in a MongoDB database. Extends the MongoRepository interface provided by Spring Data.
 * This defines CRUD operations and query methods for ClientRegistrationSecretWrapper objects.
 */
interface ClientRegistrationSecretWrapperRepository extends MongoRepository<ClientRegistrationSecretWrapper, String> {

}