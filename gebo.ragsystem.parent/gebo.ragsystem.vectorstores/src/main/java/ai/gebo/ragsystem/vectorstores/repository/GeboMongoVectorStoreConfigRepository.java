/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.ragsystem.vectorstores.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import ai.gebo.ragsystem.vectorstores.model.GeboMongoVectorStoreConfig;

/**
 * AI generated comments
 * 
 * Repository interface for managing GeboMongoVectorStoreConfig entities in MongoDB.
 * This interface extends MongoRepository to provide CRUD operations and query methods
 * for GeboMongoVectorStoreConfig objects. The repository uses String as the type
 * for the document ID.
 */
public interface GeboMongoVectorStoreConfigRepository extends MongoRepository<GeboMongoVectorStoreConfig, String> {

}