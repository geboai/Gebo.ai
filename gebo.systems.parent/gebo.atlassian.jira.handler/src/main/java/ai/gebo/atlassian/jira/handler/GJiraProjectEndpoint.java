/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.atlassian.jira.handler;

import org.springframework.data.mongodb.core.mapping.Document;

import ai.gebo.knlowledgebase.model.projects.GProjectEndpoint;
import ai.gebo.knlowledgebase.model.projects.GVirtualFilesystemProjectEndpoint;
import ai.gebo.model.annotations.EntityDescription;
import lombok.Data;

/**
 * AI generated comments
 * 
 * This class represents a connection endpoint for Atlassian Jira.
 * It extends the virtual filesystem project endpoint to provide Jira-specific 
 * functionality, allowing the system to interact with Jira as a document source.
 * The class is stored as a document in MongoDB and is categorized as a project endpoint.
 */
@Data
@EntityDescription(description = "Atlassian Jira documents source", entityCategory = GProjectEndpoint.class)
@Document
public class GJiraProjectEndpoint extends GVirtualFilesystemProjectEndpoint {
	/**
	 * Unique identifier code for the connected Jira system.
	 * This code is used to identify the specific Jira instance.
	 */
	private String jiraSystemCode = null;

}