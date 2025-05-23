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

import org.springframework.data.repository.NoRepositoryBean;

import ai.gebo.architecture.persistence.IGBaseMongoDBRepository;
import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;

/**
 * AI generated comments
 * Repository interface for GProjectEndpoint entities, providing methods for accessing and manipulating
 * project endpoint data stored in a MongoDB database.
 *
 * @param <Type> the type of GProjectEndpoint
 */
@NoRepositoryBean
public interface IGBaseMongoDBProjectEndpointRepository<Type extends GProjectEndpoint> extends IGBaseMongoDBRepository<Type> {

    /**
     * Retrieves a list of Type entities that belong to the parent project identified by the given code.
     *
     * @param parentProjectCode the code of the parent project
     * @return a list of Type entities matching the specified parent project code
     */
    public List<Type> findByParentProjectCode(String parentProjectCode);

    /**
     * Deletes Type entities that belong to the parent project identified by the given code.
     *
     * @param code the code of the parent project
     */
    public void deleteByParentProjectCode(String code);
}