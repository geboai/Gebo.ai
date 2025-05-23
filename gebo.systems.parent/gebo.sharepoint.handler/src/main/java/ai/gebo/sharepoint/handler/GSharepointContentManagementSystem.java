/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

/**
 * AI generated comments
 * This class represents a Sharepoint Content Management System which extends the generic 
 * GContentManagementSystem class. It provides functionality to manage Sharepoint-specific 
 * attributes like secret code and Sharepoint version.
 */
package ai.gebo.sharepoint.handler;

import ai.gebo.knlowledgebase.model.systems.GContentManagementSystem;
import jakarta.validation.constraints.NotNull;

public class GSharepointContentManagementSystem extends GContentManagementSystem {
	/**
	 * Secret code required for authentication with the Sharepoint system.
	 * This field cannot be null.
	 */
	@NotNull
	private String secretCode=null;
	
	/**
	 * The version of Sharepoint being used.
	 * This field cannot be null.
	 */
	@NotNull
	private SharepointVersion sharepointVersion=null;
	
	/**
	 * Gets the secret code used for Sharepoint authentication.
	 * @return the secret code as a String
	 */
	public String getSecretCode() {
		return secretCode;
	}

	/**
	 * Sets the secret code used for Sharepoint authentication.
	 * @param secretCode the secret code to set
	 */
	public void setSecretCode(String secretCode) {
		this.secretCode = secretCode;
	}

	/**
	 * Gets the Sharepoint version.
	 * @return the Sharepoint version
	 */
	public SharepointVersion getSharepointVersion() {
		return sharepointVersion;
	}

	/**
	 * Sets the Sharepoint version.
	 * @param sharepointVersion the Sharepoint version to set
	 */
	public void setSharepointVersion(SharepointVersion sharepointVersion) {
		this.sharepointVersion = sharepointVersion;
	}
}