/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.filesystem.content.handler.config;

/**
 * Configuration class for Gebo file sharing functionality.
 * This class holds settings related to file shares within the Gebo system.
 * 
 * AI generated comments
 */
public class GeboFileShareConfig {
	// Path and description fields for the file share configuration
	private String absolutePath=null,description=null;
	
	/**
	 * Default constructor for creating a new file share configuration.
	 */
	public GeboFileShareConfig() {
		
	}
	
	/**
	 * Gets the absolute path of the file share location.
	 * 
	 * @return The absolute path as a String
	 */
	public String getAbsolutePath() {
		return absolutePath;
	}
	
	/**
	 * Sets the absolute path for the file share location.
	 * 
	 * @param absolutePath The absolute path to set
	 */
	public void setAbsolutePath(String absolutePath) {
		this.absolutePath = absolutePath;
	}
	
	/**
	 * Gets the description of the file share.
	 * 
	 * @return The description as a String
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * Sets the description for the file share.
	 * 
	 * @param description The description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

}