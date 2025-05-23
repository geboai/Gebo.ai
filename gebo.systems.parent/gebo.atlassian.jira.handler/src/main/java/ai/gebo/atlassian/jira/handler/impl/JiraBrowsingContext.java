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
 * This class implements the IGVirtualFileSystemContext interface for Jira browsing operations.
 * It maintains a system code that identifies the Jira system being accessed.
 */
package ai.gebo.atlassian.jira.handler.impl;

import ai.gebo.systems.abstraction.layer.IGVirtualFileSystemContext;

public class JiraBrowsingContext implements IGVirtualFileSystemContext {
	/**
	 * The system code identifying the Jira system
	 */
	private String systemCode = null;

	/**
	 * Returns the system code for the Jira instance
	 * 
	 * @return the system code string
	 */
	public String getSystemCode() {
		return systemCode;
	}

	/**
	 * Sets the system code for the Jira instance
	 * 
	 * @param systemCode the system code to set
	 */
	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}

	/**
	 * Factory method to create a JiraBrowsingContext with the specified system code
	 * 
	 * @param systemCode the system code to initialize with
	 * @return a new JiraBrowsingContext instance
	 */
	public static JiraBrowsingContext of(String systemCode) {
		JiraBrowsingContext context = new JiraBrowsingContext();
		context.systemCode = systemCode;
		return context;
	}
}