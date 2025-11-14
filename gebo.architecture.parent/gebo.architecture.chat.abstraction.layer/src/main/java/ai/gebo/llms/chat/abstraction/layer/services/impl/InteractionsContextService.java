/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.chat.abstraction.layer.services.impl;

import java.util.List;

import org.springframework.ai.document.Document;
import org.springframework.stereotype.Service;

import ai.gebo.llms.chat.abstraction.layer.model.ChatInteractions;
import ai.gebo.llms.chat.abstraction.layer.model.GUserChatContext;

/**
 * Gebo.ai comment agent
 * Service for managing and processing user chat interactions to create a document representation.
 */
@Service
public class InteractionsContextService {

    /**
     * Default constructor for InteractionsContextService.
     */
    public InteractionsContextService() {

    }

    /**
     * Converts the interactions within a user's chat context into a document.
     *
     * @param userContext The user's chat context containing interaction details.
     * @param tokensThreshold The threshold number of tokens for processing interactions.
     * @return A Document object representing the interactions as a concatenated string.
     */
    public Document interactionsAsDocument(GUserChatContext userContext, int tokensThreshold) {
        StringBuffer buffer = new StringBuffer(); // Buffer to accumulate interaction data
        if (userContext != null) {
            List<ChatInteractions> interactions = userContext.getInteractions();
            if (interactions != null) {
                for (ChatInteractions chatInteractions : interactions) {
                    // Check if the interaction and its request/response are non-null
                    if (chatInteractions != null && chatInteractions.request != null
                            && chatInteractions.response != null) {
                        // Append the request and response queries to the buffer
                        buffer.append(chatInteractions.request.getQuery() + " \n");
                        buffer.append(chatInteractions.response.getQueryResponse() + " \n");
                    }
                }
            }
        }
        return new Document(buffer.toString()); // Create a Document with the accumulated buffer content
    }

}