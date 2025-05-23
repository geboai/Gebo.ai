/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.abstraction.layer.repositories;

import ai.gebo.architecture.persistence.IGBaseMongoDBRepository;
import ai.gebo.llms.abstraction.layer.model.RagDocumentCacheItem;

/**
 * Gebo.ai comment agent
 * Repository interface for managing {@link RagDocumentCacheItem} entities.
 * Extends the generic {@link IGBaseMongoDBRepository} interface for MongoDB operations.
 */
public interface RagDocumentCacheItemRepository extends IGBaseMongoDBRepository<RagDocumentCacheItem> {

    /**
     * Returns the class type managed by this repository.
     * This override specifies that the managed type is {@link RagDocumentCacheItem}.
     *
     * @return the class of the managed entity.
     */
    @Override
    default Class<RagDocumentCacheItem> getManagedType() {
        return RagDocumentCacheItem.class;
    }
}