/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.atlassian.jira.handler.impl.model;

/**
 * AI generated comments
 *
 * Represents a component of a JIRA path structure.
 * This class stores information about a node in a JIRA path, including its type
 * and identifier.
 */
public class JiraPathComponent {
	/**
	 * The type of the JIRA path node, represented by a JiraPathNodeType enum value.
	 * Defaults to null when not initialized.
	 */
	public JiraPathNodeType type = null;
	
	/**
	 * The identifier string for this path component.
	 * Defaults to null when not initialized.
	 */
	public String id = null;
}