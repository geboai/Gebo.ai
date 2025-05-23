/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.atlassian.confluence.handler.impl.model;

/**
 * AI generated comments
 * Enum representing the different types of nodes that can exist in a Confluence path hierarchy.
 * This is used to categorize elements in the Confluence structure for navigation and processing.
 */
public enum ConfluencePathNodeType {
	/** Represents a Confluence space, which is a top-level container for pages */
	SPACE, 
	/** Represents a Confluence page, which contains content */
	PAGE, 
	/** Represents a container that can hold multiple pages */
	PAGE_CONTAINER, 
	/** Represents an attachment to a Confluence page */
	ATTACHMENT
}