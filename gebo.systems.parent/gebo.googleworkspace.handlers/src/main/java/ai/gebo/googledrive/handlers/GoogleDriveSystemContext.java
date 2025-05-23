/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.googledrive.handlers;

import ai.gebo.systems.abstraction.layer.IGVirtualFileSystemContext;

/**
 * AI generated comments
 * 
 * Provides a Google Drive system context implementation of the virtual file system context interface.
 * This class maintains information specific to Google Drive connections and serves as a context
 * object for Google Drive operations within the virtual file system abstraction layer.
 */
public class GoogleDriveSystemContext implements IGVirtualFileSystemContext {
	/** Stores the system code that identifies this Google Drive context */
	private String systemCode = null;

	/**
	 * Gets the system code for this Google Drive context.
	 * 
	 * @return the system code string
	 */
	public String getSystemCode() {
		return systemCode;
	}

	/**
	 * Sets the system code for this Google Drive context.
	 * 
	 * @param systemCode the system code to set
	 */
	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}

	/**
	 * Factory method to create a new GoogleDriveSystemContext with the specified code.
	 * 
	 * @param code the system code to initialize the context with
	 * @return a new GoogleDriveSystemContext instance
	 */
	public static GoogleDriveSystemContext of(String code) {
		GoogleDriveSystemContext c = new GoogleDriveSystemContext();
		c.systemCode = code;
		return c;
	}
}