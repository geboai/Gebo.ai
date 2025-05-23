/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.sharepoint.handler;

import org.springframework.data.mongodb.core.mapping.Document;

import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.knlowledgebase.model.projects.GVirtualFilesystemProjectEndpoint;
import ai.gebo.model.annotations.EntityDescription;

/**
 * AI generated comments
 * 
 * Represents a SharePoint/OneDrive endpoint for project integration.
 * This class extends the virtual filesystem project endpoint to provide
 * specific functionality for SharePoint and OneDrive document sources.
 * The class is persisted in MongoDB and categorized as a project endpoint.
 */
@EntityDescription(description = "Sharepoint/OneDrive documents source", entityCategory = GProjectEndpoint.class)
@Document
public class GSharepointProjectEndpoint extends GVirtualFilesystemProjectEndpoint {
	/**
	 * Stores the system code identifier for the SharePoint system.
	 * This code is used to identify and connect to the specific SharePoint instance.
	 */
	private String sharePointSystemCode = null;

	/**
	 * Retrieves the SharePoint system code associated with this endpoint.
	 * 
	 * @return the SharePoint system code identifier
	 */
	public String getSharePointSystemCode() {
		return sharePointSystemCode;
	}

	/**
	 * Sets the SharePoint system code for this endpoint.
	 * 
	 * @param sharePointSystemCode the SharePoint system code identifier to set
	 */
	public void setSharePointSystemCode(String sharePointSystemCode) {
		this.sharePointSystemCode = sharePointSystemCode;
	}

}