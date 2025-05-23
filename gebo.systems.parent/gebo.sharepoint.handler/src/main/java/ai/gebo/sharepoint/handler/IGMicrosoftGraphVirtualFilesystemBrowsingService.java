/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.sharepoint.handler;

import java.util.List;

import ai.gebo.model.OperationStatus;
import ai.gebo.model.virtualfs.GVirtualFilesystemRoot;
import ai.gebo.sharepoint.handler.impl.SharepointBrowsingContext;
import ai.gebo.systems.abstraction.layer.IGVirtualFilesystemBrowsingService;

/**
 * AI generated comments
 * This interface extends the virtual filesystem browsing service specifically for Microsoft Graph API.
 * It provides methods to interact with Sharepoint content through Microsoft Graph API, allowing
 * navigation and retrieval of content from Sharepoint sites as if they were a virtual filesystem.
 */
public interface IGMicrosoftGraphVirtualFilesystemBrowsingService
		extends IGVirtualFilesystemBrowsingService<SharepointBrowsingContext> {

	/**
	 * Retrieves all available filesystem roots from a Sharepoint content management system.
	 * 
	 * @param object The Sharepoint content management system to retrieve roots from
	 * @return An OperationStatus containing a list of virtual filesystem roots if successful
	 */
	OperationStatus<List<GVirtualFilesystemRoot>> getRoots(GSharepointContentManagementSystem object);

	

	
}