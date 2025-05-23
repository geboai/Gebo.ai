/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.git.content.handler;

import ai.gebo.knlowledgebase.model.systems.GContentManagementSystem;

/**
 * AI generated comments
 * 
 * This class represents a Git-based Content Management System that extends the base
 * Content Management System functionality. It provides additional properties specific
 * to Git repositories such as public access settings and default identity code.
 */
public class GGitContentManagementSystem extends GContentManagementSystem {
	/** Flag indicating if the Git repository allows public access */
	private Boolean publicAccess=null;
	
	/** Default identity code used for Git operations */
	private String defaultIdentityCode=null;
	
	/**
	 * Gets the public access setting for the Git repository
	 * @return Boolean indicating whether public access is enabled
	 */
	public Boolean getPublicAccess() {
		return publicAccess;
	}
	
	/**
	 * Sets the public access flag for the Git repository
	 * @param publicAccess Boolean value to set the public access status
	 */
	public void setPublicAccess(Boolean publicAccess) {
		this.publicAccess = publicAccess;
	}
	
	/**
	 * Gets the default identity code used for Git operations
	 * @return String containing the default identity code
	 */
	public String getDefaultIdentityCode() {
		return defaultIdentityCode;
	}
	
	/**
	 * Sets the default identity code for Git operations
	 * @param defaultIdentityCode String value to set as the default identity code
	 */
	public void setDefaultIdentityCode(String defaultIdentityCode) {
		this.defaultIdentityCode = defaultIdentityCode;
	}
}