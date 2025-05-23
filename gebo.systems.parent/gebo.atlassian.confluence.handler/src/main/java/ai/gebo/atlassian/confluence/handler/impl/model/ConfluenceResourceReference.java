/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.atlassian.confluence.handler.impl.model;

import ai.gebo.systems.abstraction.layer.IGRemoteVirtualFilesystemResourceReference;

/**
 * AI generated comments
 * 
 * This class represents a reference to a Confluence resource in the virtual filesystem.
 * It implements the IGRemoteVirtualFilesystemResourceReference interface to provide
 * a standardized way to reference Confluence resources like pages and attachments.
 */
public class ConfluenceResourceReference implements IGRemoteVirtualFilesystemResourceReference {

	/** The ID of the Confluence page */
	public String pageId = null;
	/** The title of the attachment in Confluence */
	public String attachmentTitle = null;
	/** The ID of the Confluence space */
	public String spaceId = null;
	/** The version of the Confluence resource */
	public String confluenceVersion = null;
	/** The ID of the attachment */
	public String attachmentId = null;
	/** The type of the resource (e.g., page, attachment) */
	public String resourceType = null;
	/** The ID of the parent page for this resource */
	public String parentPageId = null;

	/**
	 * Returns a string representation of this Confluence resource reference.
	 * 
	 * @return A string containing the key properties of this resource reference
	 */
	@Override
	public String toString() {
		return "{resourceType=" + resourceType + ",pageId=" + pageId + ",parentPageId=" + parentPageId
				+ ",attachmentTitle=" + attachmentTitle + ",attachmentId=" + attachmentId + ",confluenceVersion="
				+ confluenceVersion + "}";
	}
}