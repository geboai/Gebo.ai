/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.userspace.handler;

import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import jakarta.validation.constraints.NotNull;

/**
 * Represents a project endpoint in the user space context.
 * Extends the base project endpoint class with user-specific information.
 * AI generated comments
 */
public class GUserspaceProjectEndpoint extends GProjectEndpoint {
	/**
	 * The username associated with this project endpoint.
	 * Cannot be null as per validation constraint.
	 */
	@NotNull
	private String username=null;
	
	/**
	 * Default constructor for GUserspaceProjectEndpoint.
	 */
	public GUserspaceProjectEndpoint() {
		
	}
	
	/**
	 * Gets the username associated with this project endpoint.
	 * 
	 * @return the username string
	 */
	public String getUsername() {
		return username;
	}
	
	/**
	 * Sets the username associated with this project endpoint.
	 * 
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

}