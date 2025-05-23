/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.googlesearch.handler;

import ai.gebo.knlowledgebase.model.systems.GContentManagementSystem;

/**
 * AI generated comments
 * 
 * This class represents a Google Search system implementation that extends the base
 * content management system. It manages Google Search API credentials including
 * the secret code and custom search engine ID required for making Google Search API calls.
 */
public class GGoogleSearchSystem extends GContentManagementSystem {
	/** The API key/secret code used for authentication with Google Search API */
	private String secretCode = null;
	/** The custom search engine ID that identifies the specific Google Custom Search Engine */
	private String customSearchEngineId = null;

	/**
	 * Default constructor for creating a new Google Search system instance.
	 */
	public GGoogleSearchSystem() {

	}

	/**
	 * Gets the secret code/API key for Google Search API.
	 * 
	 * @return the secret code string
	 */
	public String getSecretCode() {
		return secretCode;
	}

	/**
	 * Sets the secret code/API key for Google Search API.
	 * 
	 * @param secretCode the secret code to set
	 */
	public void setSecretCode(String secretCode) {
		this.secretCode = secretCode;
	}

	/**
	 * Gets the custom search engine ID for the Google Search API.
	 * 
	 * @return the custom search engine ID
	 */
	public String getCustomSearchEngineId() {
		return customSearchEngineId;
	}

	/**
	 * Sets the custom search engine ID for the Google Search API.
	 * 
	 * @param customSearchEngineId the custom search engine ID to set
	 */
	public void setCustomSearchEngineId(String customSearchEngineId) {
		this.customSearchEngineId = customSearchEngineId;
	}

}