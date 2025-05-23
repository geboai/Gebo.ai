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
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import ai.gebo.architecture.patterns.IGDynamicConfigurationSource;
import ai.gebo.model.base.GBaseObject;

/**
 * Gebo.ai comment agent
 * 
 * Adapter class for dynamic configuration sources that facilitates interaction with a MongoDB repository. 
 * It implements the IGDynamicConfigurationSource interface for generic types extending GBaseObject.
 *
 * @param <Type>      The type of the GBaseObject entity.
 * @param <RepoType>  The type of the repository interface extending IGBaseMongoDBRepository.
 */
public class GDynamicConfigurationSourceAdapter<Type extends GBaseObject, RepoType extends IGBaseMongoDBRepository<Type>>
        implements IGDynamicConfigurationSource<Type> {

    // Injecting the repository to be used for database operations.
    @Autowired
    protected RepoType repository;

    /**
     * Default constructor.
     */
    public GDynamicConfigurationSourceAdapter() {

    }

    /**
     * Constructor with repository parameter.
     *
     * @param repository The repository to be used for database interactions.
     */
    protected GDynamicConfigurationSourceAdapter(RepoType repository) {
        this.repository = repository;
    }

    /**
     * Retrieves all configuration objects from the repository.
     *
     * @return A list of configuration objects or an empty list if the repository is null.
     */
    @Override
    public List<Type> getConfigurations() {
        return repository != null ? repository.findAll() : List.of();
    }

    /**
     * Searches for a configuration object by its code.
     *
     * @param code The code of the configuration object to be searched.
     * @return The configuration object if found, otherwise null.
     */
    @Override
    public Type findByCode(String code) {
        Optional<Type> entry = repository.findById(code);
        if (entry.isPresent())
            return entry.get();
        return null;
    }

    /**
     * Factory method to create an instance of GDynamicConfigurationSourceAdapter.
     *
     * @param repository The repository to be used by the adapter.
     * @param <Type>     The type of the GBaseObject entity.
     * @param <RepoType> The type of the repository interface.
     * @return A new instance of IGDynamicConfigurationSource with the specified repository.
     */
    public static <Type extends GBaseObject, RepoType extends IGBaseMongoDBRepository<Type>> IGDynamicConfigurationSource<Type> of(
            RepoType repository) {
        return new GDynamicConfigurationSourceAdapter<Type, IGBaseMongoDBRepository<Type>>(repository);
    }
}