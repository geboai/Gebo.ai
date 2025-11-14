/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.llms.chat.abstraction.layer.model;

/**
 * Gebo.ai comment agent
 * 
 * The ChatModelRequestContextWindowStats class holds statistics regarding the
 * tokenized context window of a chat model request. It provides metrics for
 * analyzing how tokens are distributed among different components of the
 * request.
 */
public class ChatModelRequestContextWindowStats {

	/** The total length of the context window in terms of tokens. */
	public double contextWindowLengthNTokens = 0.0;

	/** Number of tokens available for processing within the context window. */
	public double availableNTokens = 0.0;

	/**
	 * Number of tokens used for the conversation history within the context window.
	 */
	public double historyNTokens = 0.0;

	/** Number of tokens consumed by the current query within the context window. */
	public double queryNTokens = 0.0;

	/**
	 * Number of tokens used for any actual documents involved in the context
	 * window.
	 */
	public double documentsNTokens = 0.0;
	/**
	 * Number of tokens used for any context historic documents involved in the
	 * context window.
	 */
	public double contextDocumentsNTokens = 0.0;

	/**
	 * Percentage of the context window's available tokens relative to its total
	 * length.
	 */
	public double availableSharePerc = 0.0;

	/**
	 * Percentage of the context window's tokens allocated for history relative to
	 * its total length.
	 */
	public double historySharePerc = 0.0;

	/**
	 * Percentage of the context window's tokens allocated for documents relative to
	 * its total length.
	 */
	public double documentsSharePerc = 0.0;
	/**
	 * Percentage of the context window's tokens allocated context historic documents relative to
	 * its total length.
	 */
	public double contextDocumentsSharePerc = 0.0;

}