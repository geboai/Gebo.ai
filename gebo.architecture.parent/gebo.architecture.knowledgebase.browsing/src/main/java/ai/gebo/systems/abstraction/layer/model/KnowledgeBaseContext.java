/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.systems.abstraction.layer.model;

import java.util.ArrayList;
import java.util.List;

import ai.gebo.knlowledgebase.model.contents.GKnowledgeBase;
import ai.gebo.systems.abstraction.layer.IGVirtualFileSystemContext;

/**
 * Gebo.ai comment agent
 * The KnowledgeBaseContext class serves as a container for managing a list of GKnowledgeBase objects.
 * It implements the IGVirtualFileSystemContext interface, providing methods to manipulate knowledge base data.
 */
public class KnowledgeBaseContext implements IGVirtualFileSystemContext {

    /**
     * Default constructor for KnowledgeBaseContext.
     * Initializes an empty list of knowledge bases.
     */
    public KnowledgeBaseContext() {

    }

    /**
     * A list that holds objects of type GKnowledgeBase.
     */
    public List<GKnowledgeBase> knowledgeBases = new ArrayList<GKnowledgeBase>();

    /**
     * Retrieves the list of GKnowledgeBase objects.
     * 
     * @return the list of knowledge bases.
     */
    public List<GKnowledgeBase> getKnowledgeBases() {
        return knowledgeBases;
    }

    /**
     * Updates the current list of GKnowledgeBase objects.
     * 
     * @param knowledgeBases the list of knowledge bases to be set.
     */
    public void setKnowledgeBases(List<GKnowledgeBase> knowledgeBases) {
        this.knowledgeBases = knowledgeBases;
    }

    /**
     * Creates a new instance of KnowledgeBaseContext with a specified list of GKnowledgeBase objects.
     * 
     * @param k the list of GKnowledgeBase objects to initialize the context with.
     * @return a new instance of KnowledgeBaseContext initialized with the provided list.
     */
    public static KnowledgeBaseContext of(List<GKnowledgeBase> k) {
        KnowledgeBaseContext kbs = new KnowledgeBaseContext();
        kbs.knowledgeBases = new ArrayList<GKnowledgeBase>(k);
        return kbs;
    }
}