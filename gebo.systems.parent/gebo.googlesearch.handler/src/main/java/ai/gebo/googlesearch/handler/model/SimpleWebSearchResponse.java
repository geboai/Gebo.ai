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
 * Represents a simplified response from a web search operation.
 * This class contains search results as web references and status information
 * about the search operation's success or failure.
 */
public class SimpleWebSearchResponse {
	/**
	 * Inner class representing a single web search result item.
	 * Contains the hyperlink to the resource and a description of the content.
	 */
	public static class WebReferenceItem {
		public String hyperLink = null;    // URL of the search result
		public String description = null;   // Description or snippet of the search result
	}

	private List<WebReferenceItem> webReferences = new ArrayList<SimpleWebSearchResponse.WebReferenceItem>();  // List of search results
	private boolean statusOK = false;       // Flag indicating if the search operation was successful
	private String statusText = null;       // Detailed status message or error information
	
	/**
	 * Returns the list of web references found in the search.
	 * @return List of WebReferenceItem objects representing search results
	 */
	public List<WebReferenceItem> getWebReferences() {
		return webReferences;
	}
	
	/**
	 * Sets the list of web references for this search response.
	 * @param results List of WebReferenceItem objects to set as search results
	 */
	public void setWebReferences(List<WebReferenceItem> results) {
		this.webReferences = results;
	}
	
	/**
	 * Checks if the search operation completed successfully.
	 * @return true if the operation was successful, false otherwise
	 */
	public boolean isStatusOK() {
		return statusOK;
	}
	
	/**
	 * Sets the status flag for the search operation.
	 * @param statusOK true to indicate success, false to indicate failure
	 */
	public void setStatusOK(boolean statusOK) {
		this.statusOK = statusOK;
	}
	
	/**
	 * Gets the status text that provides details about the search operation.
	 * @return A string containing status information or error message
	 */
	public String getStatusText() {
		return statusText;
	}
	
	/**
	 * Sets the status text with details about the search operation.
	 * @param statusText The status message to set
	 */
	public void setStatusText(String statusText) {
		this.statusText = statusText;
	}

}