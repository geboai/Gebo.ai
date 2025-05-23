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
import ai.gebo.knlowledgebase.model.contents.GDependencyTree;
import ai.gebo.knlowledgebase.model.contents.GPackageManager;

/**
 * Repository interface for managing {@link GDependencyTree} entities.
 * Extends the {@link IGBaseMongoDBRepository} to provide basic CRUD operations.
 * 
 * AI generated comments
 */
public interface DependencyTreeRepository extends IGBaseMongoDBRepository<GDependencyTree> {
    
    /**
     * Gets the managed type of the repository.
     *
     * @return the class of the entity type managed by the repository, which is {@link GDependencyTree}.
     */
    @Override
    default Class<GDependencyTree> getManagedType() {
        return GDependencyTree.class;
    }

    /**
     * Finds a stream of {@link GDependencyTree} entities by the parent project code.
     *
     * @param parentProjectCode the code of the parent project to search for.
     * @return a stream of matching {@link GDependencyTree} entities.
     */
    public Stream<GDependencyTree> findByParentProjectCode(String parentProjectCode);

    /**
     * Finds a stream of {@link GDependencyTree} entities by the root knowledgebase code.
     *
     * @param rootKnowledgebaseCode the code of the root knowledgebase to search for.
     * @return a stream of matching {@link GDependencyTree} entities.
     */
    public Stream<GDependencyTree> findByRootKnowledgebaseCode(String rootKnowledgebaseCode);

    /**
     * Finds a stream of {@link GDependencyTree} entities by a list of root knowledgebase
     * codes, package manager, module ID, and artifact ID.
     *
     * @param kbs a list of root knowledgebase codes.
     * @param packageManager the package manager associated with the dependency tree.
     * @param moduleId the module ID to search for.
     * @param artifactId the artifact ID to search for.
     * @return a stream of matching {@link GDependencyTree} entities.
     */
    public Stream<GDependencyTree> findByRootKnowledgebaseCodeInAndPackageManagerAndModuleIdAndArtifactId(
            List<String> kbs, GPackageManager packageManager, String moduleId, String artifactId);

    /**
     * Finds a stream of {@link GDependencyTree} entities by a list of root knowledgebase
     * codes, package manager, module ID, artifact ID, and version.
     *
     * @param kbs a list of root knowledgebase codes.
     * @param packageManager the package manager associated with the dependency tree.
     * @param moduleId the module ID to search for.
     * @param artifactId the artifact ID to search for.
     * @param version the version of the artifact to search for.
     * @return a stream of matching {@link GDependencyTree} entities.
     */
    public Stream<GDependencyTree> findByRootKnowledgebaseCodeInAndPackageManagerAndModuleIdAndArtifactIdAndVersion(
            List<String> kbs, GPackageManager packageManager, String moduleId, String artifactId, String version);
}
