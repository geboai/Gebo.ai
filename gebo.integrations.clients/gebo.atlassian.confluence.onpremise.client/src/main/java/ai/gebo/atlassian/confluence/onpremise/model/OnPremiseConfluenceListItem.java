/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.atlassian.confluence.onpremise.model;

import lombok.Data;

/**
 * Represents an item in a list of on-premise Confluence data.
 * This class is designed to store the basic information about a Confluence content item.
 * It includes attributes such as id, type, status, title, links, and version.
 * AI generated comments
 */
@Data
public class OnPremiseConfluenceListItem {
     // Unique identifier for the Confluence item
	 String id = null;
	 
     // Type of the Confluence content (e.g., page, blog post)
	 String type = null;
	 
     // Status of the content (e.g., current, archived)
	 String status = null;
	 
     // Title of the Confluence content
	 String title = null;
	 
     // Links related to this Confluence content, providing navigation or actions
	 OnPremiseConfluenceContentLinks _links = null;
	 
     // Version information of the Confluence content
	 OnPremiseConfluenceVersion version = null;
}