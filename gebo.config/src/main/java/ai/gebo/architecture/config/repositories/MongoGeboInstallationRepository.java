/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.architecture.config.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * AI generated comments
 * Repository interface for MongoDB operations on the MongoGeboInstallation entity.
 * Extends the MongoRepository interface provided by Spring Data MongoDB.
 * Provides CRUD operations and facilitates interaction with the MongoDB database.
 */
public interface MongoGeboInstallationRepository extends MongoRepository<ai.gebo.config.model.MongoGeboInstallation, String> {

}