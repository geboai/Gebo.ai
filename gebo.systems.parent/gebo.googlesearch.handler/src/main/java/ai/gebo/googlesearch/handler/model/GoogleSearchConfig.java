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
 * Configuration class for Google Search API integration.
 * Contains settings necessary for making Google Search API requests.
 * AI generated comments
 */
public class GoogleSearchConfig {

	/**
	 * Default constructor for GoogleSearchConfig.
	 * Creates an instance with default values.
	 */
	public GoogleSearchConfig() {

	}

	// Google API key used for authentication with Google Search API
	private String apiKey = null;
	// Custom Search Engine ID that identifies the specific search engine to use
	private String customSearchEngineId = null;
	// Flag indicating whether Google Search functionality is enabled
	private Boolean enabled = false;
	
	/**
	 * Gets the Google API key.
	 * @return the API key string
	 */
	public String getApiKey() {
		return apiKey;
	}
	
	/**
	 * Sets the Google API key.
	 * @param apiKey the API key to set
	 */
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}
	
	/**
	 * Gets the Custom Search Engine ID.
	 * @return the Custom Search Engine ID string
	 */
	public String getCustomSearchEngineId() {
		return customSearchEngineId;
	}
	
	/**
	 * Sets the Custom Search Engine ID.
	 * @param customSearchEngineId the Custom Search Engine ID to set
	 */
	public void setCustomSearchEngineId(String customSearchEngineId) {
		this.customSearchEngineId = customSearchEngineId;
	}
	
	/**
	 * Checks if Google Search is enabled.
	 * @return true if enabled, false otherwise
	 */
	public Boolean getEnabled() {
		return enabled;
	}
	
	/**
	 * Sets whether Google Search is enabled.
	 * @param enabled the enabled status to set
	 */
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

}