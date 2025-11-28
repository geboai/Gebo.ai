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
import lombok.Data;

/**
 * AI generated comments Represents a request in a chat model where the content
 * is limited by tokens. It manages the history, documents, and query with their
 * respective token limits.
 */
@Data
public class RagChatModelLimitedRequest {

	/** The maximum number of tokens allowed for the context window */
	private int contextWindowNToken;

	/** The chat history limited by tokens */
	private TokenLimitedContent<List<ChatInteractions>> history;

	/** The cached documents limited by tokens */
	private TokenLimitedContent<RagDocumentsCachedDaoResult> documents;
	/**
	 * The cached documents found on paste and actual interactions limited by tokens
	 */
	private TokenLimitedContent<RagDocumentsCachedDaoResult> contextDocuments;
	private TokenLimitedContent<RagDocumentsCachedDaoResult> uploadedDocuments;

	/** The query string limited by tokens */
	private TokenLimitedContent<String> query;

	/** The remaining token space available */
	private int residualTokenSpace;

	/**
	 * Calculates and retrieves the context window statistics including token
	 * distribution among various components.
	 *
	 * @return the statistics of the context window
	 */
	public ChatModelRequestContextWindowStats getStats() {
		ChatModelRequestContextWindowStats stats = new ChatModelRequestContextWindowStats();
		stats.contextWindowLengthNTokens = contextWindowNToken;
		stats.documentsNTokens = documents != null ? documents.getNToken() : 0;
		stats.historyNTokens = history != null ? history.getNToken() : 0;
		stats.queryNTokens = query != null ? query.getNToken() : 0;
		stats.uploadedDocumentsNTokens = uploadedDocuments != null ? uploadedDocuments.getNToken() : 0;
		stats.contextDocumentsNTokens = contextDocuments != null ? contextDocuments.getNToken() : 0;
		
		if (stats.contextWindowLengthNTokens > 0.0) {
			// Calculate available tokens and percentage shares for each component
			stats.availableNTokens = stats.contextWindowLengthNTokens - (stats.documentsNTokens + stats.queryNTokens
					+ stats.documentsNTokens + stats.contextDocumentsNTokens);
			stats.documentsSharePerc = 100.0 * stats.documentsNTokens / stats.contextWindowLengthNTokens;
			stats.historySharePerc = 100.0 * stats.historyNTokens / stats.contextWindowLengthNTokens;
			stats.queryNTokens = 100.0 * stats.queryNTokens / stats.contextWindowLengthNTokens;
			stats.historySharePerc = 100.0 * stats.historyNTokens / stats.contextWindowLengthNTokens;
			stats.availableSharePerc = 100.0 * stats.availableNTokens / stats.contextWindowLengthNTokens;
			stats.contextDocumentsSharePerc = 100.0 * stats.contextDocumentsNTokens / stats.contextWindowLengthNTokens;
			stats.uploadedDocumentsSharePerc=100.0 * stats.uploadedDocumentsNTokens/stats.contextWindowLengthNTokens;
		}
		return stats;
	}

}