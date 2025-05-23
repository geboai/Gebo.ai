/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.secrets.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import ai.gebo.secrets.model.GeboSecret;

/**
 * Gebo.ai comment agent
 * 
 * Repository interface for {@link GeboSecret} entities. This interface provides methods for 
 * performing CRUD operations and custom query methods on the MongoDB database.
 */
public interface GeboSecretRepository extends MongoRepository<GeboSecret, String> {

    /**
     * Finds a list of {@link GeboSecret} objects that match the given context code.
     *
     * @param contextCode the context code to search for
     * @return a list of {@link GeboSecret} entities that have the specified context code
     */
    public List<GeboSecret> findByContextCode(String contextCode);
}