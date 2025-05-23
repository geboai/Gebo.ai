/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.knowledgebase.repositories;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import ai.gebo.model.GUserMessage;

/**
 * AI generated comments
 * Repository interface for managing {@link GUserMessage} entities in a MongoDB database.
 * Extends the MongoRepository interface to provide CRUD operations and additional query methods.
 */
public interface UserMessageRepository extends MongoRepository<GUserMessage, String> {

    /**
     * Retrieves all messages associated with the specified jobId as a Stream.
     * 
     * @param jobId the ID of the job whose messages are to be retrieved
     * @return a Stream of {@link GUserMessage} objects
     */
    Stream<GUserMessage> findByJobId(String jobId);

    /**
     * Retrieves a pageable list of messages associated with the specified jobId.
     * 
     * @param jobId the ID of the job whose messages are to be retrieved
     * @param pageable pagination information
     * @return a Page of {@link GUserMessage} objects
     */
    Page<GUserMessage> findByJobId(String jobId, Pageable pageable);

    /**
     * Deletes all messages associated with the specified jobId.
     * 
     * @param jobId the ID of the job whose messages are to be deleted
     */
    void deleteByJobId(String jobId);

    /**
     * Deletes all messages associated with any of the specified job IDs.
     * 
     * @param jobId a list of job IDs whose messages are to be deleted
     */
    void deleteByJobIdIn(List<String> jobId);
}