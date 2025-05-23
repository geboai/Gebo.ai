/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.core.messages;

import ai.gebo.application.messaging.model.GBaseMessagePayload;
import ai.gebo.knlowledgebase.model.contents.GKnowledgeBase;

/**
 * AI generated comments
 * Represents the payload for a deleted knowledge base message.
 * Extends from GBaseMessagePayload to utilize common message payload functionalities.
 */
public class GDeletedKnowledgeBasePayload extends GBaseMessagePayload {

    // Holds the reference to the knowledge base that has been deleted.
    private GKnowledgeBase knowledgeBase = null;

    /**
     * Default constructor for GDeletedKnowledgeBasePayload.
     */
    public GDeletedKnowledgeBasePayload() {
        // No initialization done in the constructor.
    }

    /**
     * Retrieves the knowledge base associated with this payload.
     * 
     * @return the instance of GKnowledgeBase that represents the deleted knowledge base.
     */
    public GKnowledgeBase getKnowledgeBase() {
        return knowledgeBase;
    }

    /**
     * Sets the knowledge base for this payload.
     * 
     * @param knowledgeBase the instance of GKnowledgeBase to be set.
     */
    public void setKnowledgeBase(GKnowledgeBase knowledgeBase) {
        this.knowledgeBase = knowledgeBase;
    }

}