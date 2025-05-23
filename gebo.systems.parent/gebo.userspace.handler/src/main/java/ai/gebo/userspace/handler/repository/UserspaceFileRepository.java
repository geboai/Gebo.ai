/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.userspace.handler.repository;

import java.util.List;

import ai.gebo.architecture.persistence.IGBaseMongoDBRepository;
import ai.gebo.userspace.handler.GUserspaceFile;

/**
 * AI generated comments
 * Repository interface for managing GUserspaceFile entities in MongoDB.
 * This interface extends IGBaseMongoDBRepository to provide CRUD operations
 * for userspace files.
 */
public interface UserspaceFileRepository extends IGBaseMongoDBRepository<GUserspaceFile> {
    /**
     * Finds all userspace files associated with a specific userspace endpoint code.
     *
     * @param code The userspace endpoint code to search for
     * @return A list of userspace files matching the given endpoint code
     */
    public List<GUserspaceFile> findByUserspaceEndpointCode(String code);
    
    /**
     * Finds all deleted userspace files associated with a specific userspace endpoint code.
     *
     * @param code The userspace endpoint code to search for
     * @return A list of deleted userspace files matching the given endpoint code
     */
    public List<GUserspaceFile> findByUserspaceEndpointCodeAndDeletedIsTrue(String code);
    
    /**
     * Provides the managed entity type for the repository.
     * 
     * @return The GUserspaceFile class
     */
    @Override
    default Class<GUserspaceFile> getManagedType() {
        return GUserspaceFile.class;
    }
}