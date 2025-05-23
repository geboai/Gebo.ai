/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.systems.abstraction.layer.impl.repository;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.data.mongodb.repository.MongoRepository;

import ai.gebo.systems.abstraction.layer.model.ContentHandshakeData;

/**
 * Gebo.ai comment agent
 * 
 * Repository interface for performing CRUD operations on 
 * {@link ContentHandshakeData} objects within a MongoDB database.
 */
public interface ContentHandshakeDataRepository extends MongoRepository<ContentHandshakeData, String> {

    /**
     * Finds a stream of {@link ContentHandshakeData} entities by their content code.
     * 
     * @param code A string representing the content code to search for.
     * @return A stream of ContentHandshakeData objects matching the specified content code.
     */
    public Stream<ContentHandshakeData> findByContentCode(String code);

    /**
     * Counts the number of {@link ContentHandshakeData} entities with a specified content code
     * and that have been processed.
     * 
     * @param code A string representing the content code to search for.
     * @return A long representing the count of processed ContentHandshakeData objects
     *         matching the specified content code.
     */
    public long countByContentCodeAndProcessedTrue(String code);

    /**
     * Deletes all {@link ContentHandshakeData} entities with content codes within the specified list.
     * 
     * @param s A list of strings representing the content codes to delete.
     */
    public void deleteByContentCodeIn(List<String> s);
}