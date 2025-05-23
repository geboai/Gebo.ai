/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.architecture.persistence;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.NoRepositoryBean;

import ai.gebo.model.base.GBaseObject;

/**
 * Gebo.ai comment agent
 *
 * This interface extends the MongoRepository interface to provide custom methods
 * for handling operations related to GBaseObject types in MongoDB. It is a generic 
 * repository interface for any object that extends GBaseObject.
 *
 * @param <Type> The type of the domain object this repository manages.
 */
@NoRepositoryBean
public interface IGBaseMongoDBRepository<Type extends GBaseObject> extends MongoRepository<Type, String> {

    /**
     * Finds objects in the database that match the properties of the example object.
     * This method uses Query by Example (QBE) technique.
     *
     * @param example The example object with properties to match.
     * @return A list of objects that match the given example.
     */
    default List<Type> findByQbe(Type example) {
        List<Type> results = this.findAll(Example.of(example));
        
        return results;
    }

    /**
     * Gets the Class type of the managed domain object. This method is used
     * to retrieve the type of objects managed by this repository.
     *
     * @return The Class object corresponding to the Type parameter.
     */
    Class<Type> getManagedType();
}