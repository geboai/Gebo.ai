/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

/**
 * GeboGoogleJsonSecretContent is a class for handling JSON content specific to Google Cloud credentials.
 * Extends AbstractGeboSecretContent providing mechanisms to process and manage Google Cloud JSON credentials.
 * 
 * @Gebo.ai comment agent
 */
package ai.gebo.secrets.model;

public class GeboGoogleJsonSecretContent extends AbstractGeboSecretContent {
	
	// Stores the JSON content related to Google Cloud credentials
	private String jsonContent = null;

	/**
	 * Returns the type of secret content, specific to Google Cloud JSON credentials.
	 * 
	 * @return GeboSecretType representing Google Cloud JSON credentials type
	 */
	@Override
	public GeboSecretType type() {
		return GeboSecretType.GOOGLE_CLOUD_JSON_CREDENTIALS;
	}

	/**
	 * Gets the JSON content associated with this secret.
	 * 
	 * @return A String containing the JSON content
	 */
	public String getJsonContent() {
		return jsonContent;
	}

	/**
	 * Sets the JSON content for this secret.
	 * 
	 * @param jsonContent A String representing the JSON content to be set
	 */
	public void setJsonContent(String jsonContent) {
		this.jsonContent = jsonContent;
	}
}