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
 * Represents a reference to a resource in Jira.
 * This class implements the IGRemoteVirtualFilesystemResourceReference interface
 * to provide a standardized way to reference Jira resources within the virtual filesystem.
 */
package ai.gebo.atlassian.jira.handler.impl.model;

import ai.gebo.systems.abstraction.layer.IGRemoteVirtualFilesystemResourceReference;

public class JiraResourceReference implements IGRemoteVirtualFilesystemResourceReference {

	/** The identifier of the issue container */
	public String issueContainerId;
	
	/** The content identifier of the issue */
	public String issueContentId;
	
	/** The identifier of the attachment */
	public String attachmentId;
	
	/** The file name of the attachment */
	public String attachmentFileName;
	
	/** The identifier of the comment */
	public String commentId;
	
	/** The content of the comment */
	public String commentContent;
	
	/** The type of Jira object being referenced */
	public String jiraObjectType;
	
	/** The URL to access the attachment */
	public String attachmentUrl;
	
	/** The content type (MIME type) of the attachment */
	public String attachmentContentType;

	

}