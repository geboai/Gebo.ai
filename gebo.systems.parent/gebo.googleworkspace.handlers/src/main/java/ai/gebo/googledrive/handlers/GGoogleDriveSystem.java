/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.googledrive.handlers;

import ai.gebo.knlowledgebase.model.systems.GContentManagementSystem;

/**
 * AI generated comments
 * This class represents a Google Drive system that extends the base content management system.
 * It provides functionality to handle Google Drive specific attributes and operations.
 */
public class GGoogleDriveSystem extends GContentManagementSystem {
	/** The access secret used for authenticating with Google Drive API */
	private String driveAccessSecret=null;
	
	/**
	 * Default constructor for creating a new Google Drive system instance.
	 */
	public GGoogleDriveSystem() {
		
	}
	
	/**
	 * Retrieves the Google Drive access secret.
	 * 
	 * @return The drive access secret string
	 */
	public String getDriveAccessSecret() {
		return driveAccessSecret;
	}
	
	/**
	 * Sets the Google Drive access secret.
	 * 
	 * @param driveAccessSecret The drive access secret to set
	 */
	public void setDriveAccessSecret(String driveAccessSecret) {
		this.driveAccessSecret = driveAccessSecret;
	}

}