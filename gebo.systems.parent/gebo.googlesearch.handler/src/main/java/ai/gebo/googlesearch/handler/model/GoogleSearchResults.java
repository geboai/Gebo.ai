/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.googlesearch.handler.model;

import java.util.ArrayList;
import java.util.List;

/**
 * AI generated comments
 * Represents a collection of Google search results.
 * This class serves as a container for search result items retrieved from a Google search query.
 */
public class GoogleSearchResults {
	/**
	 * List that stores the individual search result items.
	 * Initialized as an empty ArrayList.
	 */
	private List<GoogleSearchResultItem> items = new ArrayList<GoogleSearchResultItem>();

	/**
	 * Retrieves the list of search result items.
	 * 
	 * @return The list of GoogleSearchResultItem objects representing search results
	 */
	public List<GoogleSearchResultItem> getItems() {
		return items;
	}

	/**
	 * Sets the list of search result items.
	 * 
	 * @param items The list of GoogleSearchResultItem objects to set
	 */
	public void setItems(List<GoogleSearchResultItem> items) {
		this.items = items;
	}
}