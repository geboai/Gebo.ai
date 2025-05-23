/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.googledrive.handlers;

import ai.gebo.systems.abstraction.layer.IGVirtualFilesystemBrowsingService;

/**
 * AI generated comments
 * 
 * Interface for Google Drive virtual filesystem browsing functionality.
 * This interface extends the generic virtual filesystem browsing service
 * specifically for Google Drive operations, allowing clients to browse
 * files and folders in Google Drive using the virtual filesystem abstraction.
 * 
 * By extending IGVirtualFilesystemBrowsingService with the GoogleDriveSystemContext,
 * implementations of this interface will provide Google Drive-specific browsing
 * capabilities while adhering to the common virtual filesystem interface contract.
 */
public interface IGGoogleDriveVirtualFilesystemBrowser
		extends IGVirtualFilesystemBrowsingService<GoogleDriveSystemContext> {

}