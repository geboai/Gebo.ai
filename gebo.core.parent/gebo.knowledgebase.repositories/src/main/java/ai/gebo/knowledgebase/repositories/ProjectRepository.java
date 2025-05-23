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
import ai.gebo.knlowledgebase.model.projects.GProject;

/**
 * ProjectRepository interface for performing operations on GProject entities.
 * Extends the IGBaseMongoDBRepository interface specifically for GProject.
 * AI generated comments
 */
public interface ProjectRepository extends IGBaseMongoDBRepository<GProject> {

	/**
	 * Returns the class type of the entity managed by this repository.
	 * Overrides the default implementation from IGBaseMongoDBRepository.
	 * 
	 * @return GProject.class as the class type being managed.
	 */
	@Override
	default Class<GProject> getManagedType() {
		return GProject.class;
	}

	/**
	 * Finds a stream of GProject entities by a given root knowledge base code.
	 * 
	 * @param code the root knowledge base code used as a search criterion.
	 * @return a stream of GProject entities matching the given code.
	 */
	Stream<GProject> findByRootKnowledgeBaseCode(String code);

	/**
	 * Finds a stream of GProject entities by a list of root knowledge base codes.
	 * 
	 * @param code the list of root knowledge base codes used as search criteria.
	 * @return a stream of GProject entities matching any of the given codes.
	 */
	Stream<GProject> findByRootKnowledgeBaseCodeIn(List<String> code);

	/**
	 * Finds a stream of GProject entities using a parent project code.
	 * 
	 * @param code the parent project code used as a search criterion.
	 * @return a stream of GProject entities with the specified parent project code.
	 */
	Stream<GProject> findByParentProjectCode(String code);

	/**
	 * Deletes GProject entities by the specified root knowledge base code.
	 * 
	 * @param code the root knowledge base code used to identify entities for deletion.
	 */
	void deleteByRootKnowledgeBaseCode(String code);
}