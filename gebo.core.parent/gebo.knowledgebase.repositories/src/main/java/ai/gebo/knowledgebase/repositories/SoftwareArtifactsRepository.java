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

import ai.gebo.architecture.persistence.IGBaseMongoDBRepository;
import ai.gebo.knlowledgebase.model.contents.GSoftwareArtifact;

/**
 * AI generated comments
 * Repository interface for managing {@link GSoftwareArtifact} entities.
 * This interface provides methods to find software artifacts based on specific criteria.
 */
public interface SoftwareArtifactsRepository extends IGBaseMongoDBRepository<GSoftwareArtifact> {
    
    /**
     * Gets the class type of the managed entity, which in this case is {@link GSoftwareArtifact}.
     *
     * @return the {@link Class} object that represents the type of the managed entity.
     */
    @Override
    default Class<GSoftwareArtifact> getManagedType() {
        return GSoftwareArtifact.class;
    }

    /**
     * Finds software artifacts by the parent project code.
     *
     * @param parentProjectCode the code of the parent project used to filter software artifacts.
     * @return a {@link Stream} of {@link GSoftwareArtifact} entities that match the given parent project code.
     */
    public Stream<GSoftwareArtifact> findByParentProjectCode(String parentProjectCode);

    /**
     * Finds software artifacts by the root knowledge base code.
     *
     * @param rootKnowledgebaseCode the code of the root knowledge base used to filter software artifacts.
     * @return a {@link Stream} of {@link GSoftwareArtifact} entities that match the given root knowledge base code.
     */
    public Stream<GSoftwareArtifact> findByRootKnowledgebaseCode(String rootKnowledgebaseCode);

    /**
     * Finds software artifacts by a list of root knowledge base codes.
     *
     * @param rootKnowledgebaseCode a list of root knowledge base codes used to filter software artifacts.
     * @return a {@link List} of {@link GSoftwareArtifact} entities that match any of the given root knowledge base codes.
     */
    public List<GSoftwareArtifact> findByRootKnowledgebaseCodeIn(List<String> rootKnowledgebaseCode);
}