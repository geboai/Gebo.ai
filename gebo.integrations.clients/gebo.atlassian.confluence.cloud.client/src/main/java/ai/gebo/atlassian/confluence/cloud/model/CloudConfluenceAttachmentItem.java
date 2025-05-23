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
 * Represents an attachment item in Cloud Confluence.
 * Includes metadata and extension data related to the attachment.
 * AI generated comments
 */
@Data
public class CloudConfluenceAttachmentItem extends CloudConfluenceListItem {

    /**
     * Metadata associated with the Confluence attachment.
     */
	private CloudConfluenceAttachmentMetaData metadata = null;

    /**
     * Extension data associated with the Confluence attachment.
     */
	private CloudConfluenceExtensionsMetaData extensions = null;
	
}