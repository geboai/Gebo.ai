/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.googlesearch.handler.model;

import org.springframework.data.mongodb.core.mapping.Document;

import ai.gebo.model.base.GBaseObject;
import jakarta.validation.constraints.NotNull;

/**
 * AI generated comments
 * 
 * This class represents Google Search API credentials for authentication.
 * It extends GBaseObject and is stored as a document in MongoDB.
 * Contains the necessary information to authenticate and use Google's Custom Search API.
 */
@Document
public class GGoogleSearchApiCredentials extends GBaseObject {
	/**
	 * The API key/secret code required for authentication with Google Search API.
	 * Must not be null.
	 */
	@NotNull
	private String secretCode = null;
	
	/**
	 * The custom search engine ID that identifies the specific Google Custom Search Engine.
	 * Must not be null.
	 */
	@NotNull
	private String customSearchEngineId = null;

	/**
	 * Default constructor that initializes an empty credentials object.
	 */
	public GGoogleSearchApiCredentials() {

	}

	/**
	 * Gets the secret code (API key) used for Google Search API authentication.
	 * 
	 * @return The Google API secret code/key
	 */
	public String getSecretCode() {
		return secretCode;
	}

	/**
	 * Sets the secret code (API key) used for Google Search API authentication.
	 * 
	 * @param secretCode The Google API secret code/key to set
	 */
	public void setSecretCode(String secretCode) {
		this.secretCode = secretCode;
	}

	/**
	 * Gets the Custom Search Engine ID that identifies the search engine configuration.
	 * 
	 * @return The Custom Search Engine ID
	 */
	public String getCustomSearchEngineId() {
		return customSearchEngineId;
	}

	/**
	 * Sets the Custom Search Engine ID that identifies the search engine configuration.
	 * 
	 * @param customSearchEngineId The Custom Search Engine ID to set
	 */
	public void setCustomSearchEngineId(String customSearchEngineId) {
		this.customSearchEngineId = customSearchEngineId;
	}

}