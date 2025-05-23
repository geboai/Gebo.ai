/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.atlassian.confluence.handler;

import ai.gebo.knlowledgebase.model.systems.GContentManagementSystem;

/**
 * AI generated comments
 * Represents a Confluence system within the Gebo knowledge base framework.
 * This class extends the generic content management system to provide Confluence-specific functionality.
 */
public class GConfluenceSystem extends GContentManagementSystem {
	/** The version of the Confluence instance */
	private ConfluenceVersion confluenceVersion=null;
	/** Secret code used for authentication or other security purposes */
	private String secretCode = null;
	
	/**
	 * Default constructor for creating a new Confluence system instance.
	 */
	public GConfluenceSystem() {

	}

	/**
	 * Retrieves the secret code used for authentication.
	 * 
	 * @return The secret code string
	 */
	public String getSecretCode() {
		return secretCode;
	}

	/**
	 * Sets the secret code for authentication.
	 * 
	 * @param secureCode The secret code to set
	 */
	public void setSecretCode(String secureCode) {
		this.secretCode = secureCode;
	}

	/**
	 * Gets the version of the Confluence instance.
	 * 
	 * @return The Confluence version
	 */
	public ConfluenceVersion getConfluenceVersion() {
		return confluenceVersion;
	}

	/**
	 * Sets the version of the Confluence instance.
	 * 
	 * @param confluenceVersion The Confluence version to set
	 */
	public void setConfluenceVersion(ConfluenceVersion confluenceVersion) {
		this.confluenceVersion = confluenceVersion;
	}

	

}