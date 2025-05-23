/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.knowledgebase.repositories;

import java.util.stream.Stream;

import org.springframework.data.mongodb.repository.MongoRepository;

import ai.gebo.knlowledgebase.model.jobs.ContentsBatchProcessed;

/**
 * AI generated comments
 * Repository interface for accessing and performing operations on 
 * the 'ContentsBatchProcessed' collection in the MongoDB.
 */
public interface ContentsBatchProcessedRepository extends MongoRepository<ContentsBatchProcessed, String> {

    /**
     * Retrieves a stream of 'ContentsBatchProcessed' objects filtered by the specified job ID.
     *
     * @param code The job ID to filter the 'ContentsBatchProcessed' entries.
     * @return A stream of 'ContentsBatchProcessed' objects matching the given job ID.
     */
    public Stream<ContentsBatchProcessed> findByJobId(String code);

}