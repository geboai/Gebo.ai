/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.atlassian.confluence.cloud.model;

import lombok.Data;

/**
 * Represents a view in a cloud-based Confluence instance.
 * This class is used to capture the view's content and its representation type.
 * 
 * @author AI generated comments
 */
@Data
public class CloudConfluenceView {
	
	/** 
	 * The content of the Confluence view, stored as a String.
	 */
	private String value = null;
	
	/** 
	 * The format in which the view's content is represented, e.g., "view", "export_view", etc.
	 */
	private String representation = null;
}