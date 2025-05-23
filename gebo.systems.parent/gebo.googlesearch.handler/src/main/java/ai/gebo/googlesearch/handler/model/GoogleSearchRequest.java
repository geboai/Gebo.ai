/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.googlesearch.handler.model;

/**
 * AI generated comments
 * 
 * A model class representing a request for Google search.
 * This class encapsulates parameters needed to perform a search query.
 */
public class GoogleSearchRequest {
	/** The language preference for search results */
	private String language = null;
	/** The search query string */
	private String query = null;
	/** The number of top results to return */
	private Integer topN = null;

	/**
	 * Gets the search query string.
	 * 
	 * @return the query string
	 */
	public String getQuery() {
		return query;
	}

	/**
	 * Sets the search query string.
	 * 
	 * @param query the query string to set
	 */
	public void setQuery(String query) {
		this.query = query;
	}

	/**
	 * Gets the language preference for search results.
	 * 
	 * @return the language code
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * Sets the language preference for search results.
	 * 
	 * @param language the language code to set
	 */
	public void setLanguage(String language) {
		this.language = language;
	}

	/**
	 * Gets the number of top results to return.
	 * 
	 * @return the number of top results
	 */
	public Integer getTopN() {
		return topN;
	}

	/**
	 * Sets the number of top results to return.
	 * 
	 * @param topN the number of top results to set
	 */
	public void setTopN(Integer topN) {
		this.topN = topN;
	}
}