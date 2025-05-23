/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.googlesearch.handler.model;

import java.util.HashMap;
import java.util.List;

import jakarta.validation.constraints.NotNull;

/**
 * AI generated comments
 * 
 * This class represents a simple web search request model that contains the search query parameter
 * and additional query parameters that can be included in a web search request.
 * It provides getters and setters for accessing and modifying these parameters.
 */
public class SimpleWebSearchRequest {
	/**
	 * Map containing additional query parameters that can be included in the search request.
	 * Each parameter can have multiple values represented as a List of Strings.
	 */
	private HashMap<String, List<String>> additionalQueryRequestParameters = new HashMap<String, List<String>>();
	
	/**
	 * The main search query parameter. This field is marked as not null, indicating it's required.
	 */
	@NotNull
	private String searchTextQParam = null;

	/**
	 * Returns the additional query parameters associated with this search request.
	 * 
	 * @return HashMap containing parameter names as keys and list of values as values
	 */
	public HashMap<String, List<String>> getAdditionalQueryRequestParameters() {
		return additionalQueryRequestParameters;
	}

	/**
	 * Sets the additional query parameters for this search request.
	 * 
	 * @param additionalQueryRequestParameters HashMap containing parameter names as keys and list of values as values
	 */
	public void setAdditionalQueryRequestParameters(
			HashMap<String, List<String>> additionalQueryRequestParameters) {
		this.additionalQueryRequestParameters = additionalQueryRequestParameters;
	}

	/**
	 * Gets the main search query text.
	 * 
	 * @return the search query text
	 */
	public String getSearchTextQParam() {
		return searchTextQParam;
	}

	/**
	 * Sets the main search query text.
	 * 
	 * @param searchTextQParam the search query text to set
	 */
	public void setSearchTextQParam(String searchTextQParam) {
		this.searchTextQParam = searchTextQParam;
	}
}