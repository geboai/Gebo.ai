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
import ai.gebo.knlowledgebase.model.jobs.VectorizatorBatchProcessed;

/**
 * AI generated comments
 * Repository interface for performing CRUD operations on VectorizatorBatchProcessed documents in MongoDB.
 * Extends the MongoRepository interface provided by Spring Data MongoDB.
 */
public interface VectorizatorBatchProcessedRepository extends MongoRepository<VectorizatorBatchProcessed, String> {

    /**
     * Retrieves a stream of VectorizatorBatchProcessed entities filtered by the specified job ID.
     *
     * @param jobId The ID of the job to filter the VectorizatorBatchProcessed entities.
     * @return A Stream of VectorizatorBatchProcessed entities.
     */
    Stream<VectorizatorBatchProcessed> findByJobId(String jobId);

}