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
 * Class that provides a context implementation for browsing Confluence systems within a virtual file system.
 * This class implements the IGVirtualFileSystemContext interface to enable Confluence content to be accessed
 * through a virtual file system abstraction.
 */
package ai.gebo.atlassian.confluence.handler.impl;

import ai.gebo.systems.abstraction.layer.IGVirtualFileSystemContext;

public class ConfluenceBrowsingContext implements IGVirtualFileSystemContext {
	/** The unique identifier code for the Confluence system being browsed */
	private String systemCode = null;

	/**
	 * Returns the system code identifier for the current Confluence instance
	 * @return the system code string
	 */
	public String getSystemCode() {
		return systemCode;
	}

	/**
	 * Sets the system code identifier for the Confluence instance
	 * @param systemCode the system code to set
	 */
	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}

	/**
	 * Factory method to create a new ConfluenceBrowsingContext with the specified system code
	 * @param systemCode the system code to initialize the context with
	 * @return a new ConfluenceBrowsingContext instance
	 */
	public static ConfluenceBrowsingContext of(String systemCode) {
		ConfluenceBrowsingContext context = new ConfluenceBrowsingContext();
		context.systemCode = systemCode;
		return context;
	}
}