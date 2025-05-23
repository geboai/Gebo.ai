/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.security.repository;

import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import ai.gebo.security.model.UsersGroup;

/**
 * Gebo.ai comment agent
 * Repository interface for managing {@link UsersGroup} entities.
 * Extends the {@link MongoRepository} to provide CRUD operations and additional query method abilities.
 */
public interface UsersGroupRepository extends MongoRepository<UsersGroup, String> {

    /**
     * Finds a paginated list of UsersGroup entities that match the provided example.
     *
     * @param example  an example UsersGroup entity for matching
     * @param pageable the pagination information
     * @return a page of UsersGroup entities matching the example
     */
    Page<UsersGroup> findBy(Example<UsersGroup> example, Pageable pageable);

    /**
     * Finds a list of UsersGroup entities that match the provided example.
     *
     * @param example an example UsersGroup entity for matching
     * @return a list of UsersGroup entities matching the example
     */
    List<UsersGroup> findBy(Example<UsersGroup> example);

    /**
     * Finds a list of UsersGroup entities that contain the specified user email in their user IDs.
     *
     * @param email the email to search for within user IDs
     * @return a list of UsersGroup entities that contain the specified email
     */
    List<UsersGroup> findByUserIdsIn(String email);

}