/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.core.contents.security.services;

import java.util.List;

import ai.gebo.knlowledgebase.model.contents.GKnowledgeBase;

/**
 * AI generated comments
 * This interface defines methods for managing the visibility of knowledge bases.
 * It provides functionalities to retrieve various types of visible knowledge bases.
 */
public interface IGKnowledgebaseVisibilityService {

    /**
     * Retrieves all visible knowledge bases.
     * 
     * @return a list of all visible GKnowledgeBase instances.
     */
    public List<GKnowledgeBase> allVisibleKnowledgebases();

    /**
     * Retrieves all visible root knowledge bases.
     * 
     * @return a list of visible root GKnowledgeBase instances.
     */
    public List<GKnowledgeBase> allVisibleRootKnowledgebases();

    /**
     * Retrieves personal knowledge bases.
     * 
     * @return a list of personal GKnowledgeBase instances.
     */
    public List<GKnowledgeBase> getPersonalKnowledgebases();

    /**
     * Retrieves visible knowledge bases and their child knowledge bases based on a list of root knowledge base codes.
     * 
     * @param rootkbCodes - a list of root knowledge base codes to filter.
     * @return a list of visible GKnowledgeBase instances including their children.
     */
    public List<GKnowledgeBase> visiblesAndChildKnowledgebases(List<String> rootkbCodes);

    /**
     * Retrieves visible root knowledge bases based on a list of root knowledge base codes.
     * 
     * @param rootkbCodes - a list of root knowledge base codes to filter.
     * @return a list of visible root GKnowledgeBase instances.
     */
    public List<GKnowledgeBase> visiblesRootKnowledgebases(List<String> rootkbCodes);
}