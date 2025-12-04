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
 * Gebo.ai comment agent Service for managing and processing user chat
 * interactions to create a document representation.
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
	 * @param userContext     The user's chat context containing interaction
	 *                        details.
	 * @param tokensThreshold The threshold number of tokens for processing
	 *                        interactions.
	 * @return A Document object representing the interactions as a concatenated
	 *         string.
	 */
	public Document interactionsAsDocument(GUserChatContext userContext, int tokensThreshold) {
		if (userContext == null || userContext.getInteractions() == null) {
			return new Document("");
		}

		StringBuilder buffer = new StringBuilder();
		int usedTokens = 0;

		List<ChatInteractions> interactions = userContext.getInteractions();

		// Iterate from most recent to oldest
		for (int i = interactions.size() - 1; i >= 0; i--) {
			ChatInteractions chatInteractions = interactions.get(i);
			if (chatInteractions == null) {
				continue;
			}

			Object userText = chatInteractions.getRequest() != null ? chatInteractions.getRequest().getQuery() : null;

			Object assistantText = chatInteractions.getResponse() != null
					? chatInteractions.getResponse().getQueryResponse()
					: null;

			if ((userText == null || userText.toString().isBlank())
					&& (assistantText == null || assistantText.toString().isBlank())) {
				continue;
			}

			StringBuilder turnBuilder = new StringBuilder();
			turnBuilder.append("### Turn ").append(i + 1).append("\n");
			if (userText != null && !userText.toString().isBlank()) {
				turnBuilder.append("User: ").append(userText.toString().trim()).append("\n");
			}
			if (assistantText != null && !assistantText.toString().isBlank()) {
				turnBuilder.append("Assistant: ").append(assistantText.toString().trim()).append("\n");
			}
			turnBuilder.append("\n");

			String turnBlock = turnBuilder.toString();
			int turnTokens = estimateTokens(turnBlock);

			if (usedTokens + turnTokens > tokensThreshold) {
				break;
			}

			// Prepend so that older turns end up on top in the final text
			buffer.insert(0, turnBlock);
			usedTokens += turnTokens;
		}

		return new Document(buffer.toString());
	}

	/**
	 * Very rough token estimation: ~4 chars per token. Replace with a proper
	 * tokenizer if you need accuracy.
	 */
	private int estimateTokens(String text) {
		if (text == null || text.isEmpty()) {
			return 0;
		}
		return Math.max(1, text.length() / 4);
	}

}