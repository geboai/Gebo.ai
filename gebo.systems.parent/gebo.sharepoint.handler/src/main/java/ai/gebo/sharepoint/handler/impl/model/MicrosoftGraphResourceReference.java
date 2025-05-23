/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.sharepoint.handler.impl.model;

import ai.gebo.systems.abstraction.layer.IGRemoteVirtualFilesystemResourceReference;

/**
 * AI generated comments
 * 
 * Implementation of a resource reference for Microsoft Graph API objects.
 * This class provides identifiers for various Microsoft SharePoint/OneDrive resources
 * including drives, drive items, sites, and site pages.
 * It implements the generic remote virtual filesystem resource reference interface.
 */
public class MicrosoftGraphResourceReference implements IGRemoteVirtualFilesystemResourceReference {
	/** The unique identifier for a drive in Microsoft Graph */
	public String driveId = null;
	
	/** The unique identifier for an item within a drive in Microsoft Graph */
	public String driveItemId = null;
	
	/** The unique identifier for a SharePoint site */
	public String siteId = null;
	
	/** The unique identifier for a page within a SharePoint site */
	public String sitePageId = null;
}