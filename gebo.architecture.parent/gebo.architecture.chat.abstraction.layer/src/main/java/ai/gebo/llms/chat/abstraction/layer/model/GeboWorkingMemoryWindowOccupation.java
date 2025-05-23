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
 * GeboWorkingMemoryWindowOccupation class represents the occupation statistics 
 * of a working memory window, including tokens and their usage percentages.
 * This class encapsulates information about history tokens, document tokens, 
 * and query tokens along with their corresponding percentages in the working memory.
 *
 * Gebo.ai comment agent
 */
public class GeboWorkingMemoryWindowOccupation {
	// Fields to store the number of tokens in different categories and their percentages
	Long historyTokens = null;
	Long documentsTokens = null;
	Long queryTokens = null;
	Double historyPercentage = null;
	Double documentsPercentage = null;
	Double totalWindowUsePercentage = null;

	/**
	 * Default constructor initializing an instance of GeboWorkingMemoryWindowOccupation.
	 */
	public GeboWorkingMemoryWindowOccupation() {

	}

	/**
	 * Gets the number of history tokens.
	 *
	 * @return the number of history tokens.
	 */
	public Long getHistoryTokens() {
		return historyTokens;
	}

	/**
	 * Sets the number of history tokens.
	 *
	 * @param historyTokens the number of history tokens.
	 */
	public void setHistoryTokens(Long historyTokens) {
		this.historyTokens = historyTokens;
	}

	/**
	 * Gets the number of document tokens.
	 *
	 * @return the number of document tokens.
	 */
	public Long getDocumentsTokens() {
		return documentsTokens;
	}

	/**
	 * Sets the number of document tokens.
	 *
	 * @param documentsTokens the number of document tokens.
	 */
	public void setDocumentsTokens(Long documentsTokens) {
		this.documentsTokens = documentsTokens;
	}

	/**
	 * Gets the number of query tokens.
	 *
	 * @return the number of query tokens.
	 */
	public Long getQueryTokens() {
		return queryTokens;
	}

	/**
	 * Sets the number of query tokens.
	 *
	 * @param queryTokens the number of query tokens.
	 */
	public void setQueryTokens(Long queryTokens) {
		this.queryTokens = queryTokens;
	}

	/**
	 * Gets the percentage of history tokens in the window.
	 *
	 * @return the percentage of history tokens.
	 */
	public Double getHistoryPercentage() {
		return historyPercentage;
	}

	/**
	 * Sets the percentage of history tokens in the window.
	 *
	 * @param historyPercentage the percentage of history tokens.
	 */
	public void setHistoryPercentage(Double historyPercentage) {
		this.historyPercentage = historyPercentage;
	}

	/**
	 * Gets the percentage of document tokens in the window.
	 *
	 * @return the percentage of document tokens.
	 */
	public Double getDocumentsPercentage() {
		return documentsPercentage;
	}

	/**
	 * Sets the percentage of document tokens in the window.
	 *
	 * @param documentsPercentage the percentage of document tokens.
	 */
	public void setDocumentsPercentage(Double documentsPercentage) {
		this.documentsPercentage = documentsPercentage;
	}

	/**
	 * Gets the total percentage use of the window.
	 *
	 * @return the total percentage use of the window.
	 */
	public Double getTotalWindowUsePercentage() {
		return totalWindowUsePercentage;
	}

	/**
	 * Sets the total percentage use of the window.
	 *
	 * @param totalWindowUsePercentage the total percentage use of the window.
	 */
	public void setTotalWindowUsePercentage(Double totalWindowUsePercentage) {
		this.totalWindowUsePercentage = totalWindowUsePercentage;
	}

}