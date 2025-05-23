/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.architecture.persistence.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.patterns.GAbstractImplementationsRepositoryPattern;
import ai.gebo.architecture.persistence.IGBaseMongoDBRepository;
import ai.gebo.architecture.persistence.IGMongoRepositoriesImplementationRepositoryPattern;

/**
 * Gebo.ai comment agent
 * 
 * Implementation of the IGMongoRepositoriesImplementationRepositoryPattern interface.
 * This class extends the GAbstractImplementationsRepositoryPattern and provides
 * methods to interact with MongoDB repositories.
 */
@Service
public class GMongoRepositoriesImplementationRepositoryPatternImpl
        extends GAbstractImplementationsRepositoryPattern<IGBaseMongoDBRepository>
        implements IGMongoRepositoriesImplementationRepositoryPattern {

    /**
     * Constructor for GMongoRepositoriesImplementationRepositoryPatternImpl.
     * Initializes the repository pattern with a list of implementations.
     *
     * @param implementations a list of IGBaseMongoDBRepository instances.
     */
    public GMongoRepositoriesImplementationRepositoryPatternImpl(
            @Autowired(required = false) List<IGBaseMongoDBRepository> implementations) {
        super(implementations);
    }

    /**
     * Retrieves the code value based on the managed type of the given repository.
     *
     * @param x the IGBaseMongoDBRepository for which to get the code value.
     * @return the name of the managed type if available; "Unknown" otherwise.
     */
    @Override
    public String getCodeValue(IGBaseMongoDBRepository x) {
        Class _type = x.getManagedType();
        return _type != null ? _type.getName() : "Unkown";
    }

    /**
     * Finds a repository by its managed type.
     *
     * @param _type the Class type to find the repository for.
     * @return the IGBaseMongoDBRepository matching the type, or null if not found.
     */
    @Override
    public IGBaseMongoDBRepository findByManagedType(Class _type) {
        List<IGBaseMongoDBRepository> impls = getImplementations();
        for (IGBaseMongoDBRepository gBaseMongoDBRepository : impls) {
            if (gBaseMongoDBRepository.getManagedType() != null
                    && gBaseMongoDBRepository.getManagedType().equals(_type)) {
                return gBaseMongoDBRepository;
            }
        }
        return null;
    }

}