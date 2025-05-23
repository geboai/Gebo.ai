/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.googledrive.handlers.impl.model;

import ai.gebo.systems.abstraction.layer.IGRemoteVirtualFilesystemResourceReference;

/**
 * AI generated comments
 * 
 * Represents a reference to a Google Drive resource within the Gebo virtual filesystem.
 * This class implements IGRemoteVirtualFilesystemResourceReference interface to provide
 * a standardized way to identify and reference Google Drive resources (files, folders).
 */
public class GoogleDriveResourceReference implements IGRemoteVirtualFilesystemResourceReference {

	/**
	 * The ID of the Google Drive where the resource is located.
	 */
	public String driveId;
	
	/**
	 * The ID of the parent folder containing the resource.
	 */
	public String folderId;
	
	/**
	 * The unique identifier of the resource itself in Google Drive.
	 */
	public String resourceId;

}