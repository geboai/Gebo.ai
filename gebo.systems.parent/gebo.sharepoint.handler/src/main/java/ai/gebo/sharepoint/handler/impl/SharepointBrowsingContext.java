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
 * 
 * This class implements a browsing context for SharePoint in the virtual file system.
 * It serves as a container for SharePoint system-specific information that's needed
 * for navigating and interacting with SharePoint resources.
 */
package ai.gebo.sharepoint.handler.impl;

import ai.gebo.systems.abstraction.layer.IGVirtualFileSystemContext;

/**
 * A context implementation for browsing SharePoint resources through the
 * virtual file system abstraction layer.
 */
public class SharepointBrowsingContext implements IGVirtualFileSystemContext {
	/** The system code identifier for the SharePoint instance */
	private String systemCode = null;

	/**
	 * Gets the system code that identifies the SharePoint instance.
	 * 
	 * @return The system code string
	 */
	public String getSystemCode() {
		return systemCode;
	}

	/**
	 * Sets the system code for the SharePoint instance.
	 * 
	 * @param systemCode The system code to set
	 */
	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}

	/**
	 * Factory method that creates and initializes a SharepointBrowsingContext with the given system code.
	 * 
	 * @param systemCode The system code to initialize the context with
	 * @return A new SharepointBrowsingContext instance
	 */
	public static SharepointBrowsingContext of(String systemCode) {
		SharepointBrowsingContext context = new SharepointBrowsingContext();
		context.systemCode = systemCode;
		return context;
	}
}