/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.architecture.persistence;

import org.springframework.data.repository.NoRepositoryBean;
import ai.gebo.architecture.patterns.IGImplementationsRepositoryPattern;

/**
 * Gebo.ai comment agent
 * 
 * This interface defines a repository pattern specific for MongoDB repositories.
 * It extends the IGImplementationsRepositoryPattern interface, which provides
 * generic methods for managing implementations.
 * 
 * This interface includes an additional method to find a repository based on
 * a managed type.
 */
@NoRepositoryBean
public interface IGMongoRepositoriesImplementationRepositoryPattern
        extends IGImplementationsRepositoryPattern<IGBaseMongoDBRepository> {

    /**
     * Finds a MongoDB repository implementation based on the provided managed type.
     * 
     * @param _type The class type of the managed entity.
     * @return An instance of IGBaseMongoDBRepository corresponding to the managed type.
     */
    public IGBaseMongoDBRepository findByManagedType(Class _type);
}