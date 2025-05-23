/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.chat.abstraction.layer.model;

import java.util.List;

import ai.gebo.llms.abstraction.layer.model.RagDocumentsCachedDaoResult;
import ai.gebo.llms.chat.abstraction.layer.model.GUserChatContext.ChatInteractions;

/**
 * AI generated comments
 * Represents a request in a chat model where the content is limited by tokens.
 * It manages the history, documents, and query with their respective token limits.
 */
public class ChatModelLimitedRequest {

    /** The maximum number of tokens allowed for the context window */
    private int contextWindowNToken;

    /** The chat history limited by tokens */
    private TokenLimitedContent<List<ChatInteractions>> history;

    /** The cached documents limited by tokens */
    private TokenLimitedContent<RagDocumentsCachedDaoResult> documents;

    /** The query string limited by tokens */
    private TokenLimitedContent<String> query;

    /** The remaining token space available */
    private int residualTokenSpace;

    /**
     * Gets the token limitation for the context window.
     *
     * @return the number of tokens allowed in context window
     */
    public int getContextWindowNToken() {
        return contextWindowNToken;
    }

    /**
     * Sets the token limitation for the context window.
     *
     * @param tokensLimitation the maximum number of tokens allowed
     */
    public void setContextWindowNToken(int tokensLimitation) {
        this.contextWindowNToken = tokensLimitation;
    }

    /**
     * Gets the documents limited by tokens.
     *
     * @return the token-limited documents
     */
    public TokenLimitedContent<RagDocumentsCachedDaoResult> getDocuments() {
        return documents;
    }

    /**
     * Sets the documents limited by tokens.
     *
     * @param documents the token-limited documents
     */
    public void setDocuments(TokenLimitedContent<RagDocumentsCachedDaoResult> documents) {
        this.documents = documents;
    }

    /**
     * Gets the query limited by tokens.
     *
     * @return the token-limited query
     */
    public TokenLimitedContent<String> getQuery() {
        return query;
    }

    /**
     * Sets the query limited by tokens.
     *
     * @param query the token-limited query
     */
    public void setQuery(TokenLimitedContent<String> query) {
        this.query = query;
    }

    /**
     * Gets the residual token space available.
     *
     * @return the available token space
     */
    public int getResidualTokenSpace() {
        return residualTokenSpace;
    }

    /**
     * Sets the residual token space available.
     *
     * @param residualTokenSpace the available token space
     */
    public void setResidualTokenSpace(int residualTokenSpace) {
        this.residualTokenSpace = residualTokenSpace;
    }

    /**
     * Calculates and retrieves the context window statistics including token distribution among various components.
     *
     * @return the statistics of the context window
     */
    public ChatModelRequestContextWindowStats getStats() {
        ChatModelRequestContextWindowStats stats = new ChatModelRequestContextWindowStats();
        stats.contextWindowLengthNTokens = contextWindowNToken;
        stats.documentsNTokens = documents != null ? documents.getNToken() : 0;
        stats.historyNTokens = history != null ? history.getNToken() : 0;
        stats.queryNTokens = query != null ? query.getNToken() : 0;
        if (stats.contextWindowLengthNTokens > 0.0) {
            // Calculate available tokens and percentage shares for each component
            stats.availableNTokens = stats.contextWindowLengthNTokens
                    - (stats.documentsNTokens + stats.queryNTokens + stats.documentsNTokens);
            stats.documentsSharePerc = 100.0 * stats.documentsNTokens / stats.contextWindowLengthNTokens;
            stats.historySharePerc = 100.0 * stats.historyNTokens / stats.contextWindowLengthNTokens;
            stats.queryNTokens = 100.0 * stats.queryNTokens / stats.contextWindowLengthNTokens;
            stats.historySharePerc = 100.0 * stats.historyNTokens / stats.contextWindowLengthNTokens;
            stats.availableSharePerc = 100.0 * stats.availableNTokens / stats.contextWindowLengthNTokens;
        }
        return stats;
    }

    /**
     * Gets the chat interactions history limited by tokens.
     *
     * @return the token-limited history
     */
    public TokenLimitedContent<List<ChatInteractions>> getHistory() {
        return history;
    }

    /**
     * Sets the chat interactions history limited by tokens.
     *
     * @param history the token-limited history
     */
    public void setHistory(TokenLimitedContent<List<ChatInteractions>> history) {
        this.history = history;
    }
}