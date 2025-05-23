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

import ai.gebo.architecture.persistence.IGBaseMongoDBRepository;
import ai.gebo.knlowledgebase.model.contents.GKnowledgeBase;

/**
 * AI generated comments
 * Repository interface for managing {@link GKnowledgeBase} entities in the database.
 * Provides methods to perform various queries related to knowledge bases.
 */
public interface KnowledgeBaseRepository extends IGBaseMongoDBRepository<GKnowledgeBase> {

    /**
     * Returns the managed type class for the repository.
     * This is used to specify the type of objects the repository works with.
     * 
     * @return the class type of {@link GKnowledgeBase}
     */
    @Override
    default Class<GKnowledgeBase> getManagedType() {
        return GKnowledgeBase.class;
    }

    /**
     * Finds knowledge base entries by matching their codes with the provided list
     * or by matching their parent knowledge base codes with another provided list.
     * 
     * @param kbList list of knowledge base codes to search for
     * @param extList list of external parent knowledgebase codes to search for
     * @return a list of {@link GKnowledgeBase} entities
     */
    public List<GKnowledgeBase> findByCodeInOrParentKnowledgebaseCodeIn(List<String> kbList, List<String> extList);

    /**
     * Retrieves knowledge base entries with codes that are in the provided list and
     * whose parent knowledge base code is null.
     * 
     * @param kbList list of knowledge base codes to search for
     * @return a list of {@link GKnowledgeBase} entities
     */
    public List<GKnowledgeBase> findByCodeInAndParentKnowledgebaseCodeIsNull(List<String> kbList);

    /**
     * Combines the findByCodeInOrParentKnowledgebaseCodeIn query by passing the same
     * list of codes for both parameters, effectively allowing search among a set of codes.
     * 
     * @param codes list of codes to search for both knowledge bases and child knowledge bases
     * @return a list of {@link GKnowledgeBase} entities
     */
    public default List<GKnowledgeBase> findByKnowledgeBaseCodesAndChildKnowledgeBases(List<String> codes) {
        return findByCodeInOrParentKnowledgebaseCodeIn(codes, codes);
    }

    /**
     * Finds knowledge base entries associated with a specific username.
     * 
     * @param username the username to search for
     * @return a list of {@link GKnowledgeBase} entities
     */
    public List<GKnowledgeBase> findByUsername(String username);

    /**
     * Retrieves knowledge base entries whose parent knowledge base code is null,
     * which typically signifies top-level entries.
     * 
     * @return a list of {@link GKnowledgeBase} entities without a parent knowledge base code
     */
    public List<GKnowledgeBase> findByParentKnowledgebaseCodeIsNull();

    /**
     * Finds knowledge base entries with a specific parent knowledge base code.
     * 
     * @param code the parent knowledge base code to search for
     * @return a list of {@link GKnowledgeBase} entities
     */
    public List<GKnowledgeBase> findByParentKnowledgebaseCode(String code);
}