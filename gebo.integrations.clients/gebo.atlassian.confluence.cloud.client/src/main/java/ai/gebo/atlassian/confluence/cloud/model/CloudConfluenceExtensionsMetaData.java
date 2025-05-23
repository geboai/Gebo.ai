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
 * AI generated comments
 *
 * Class representing metadata for cloud confluence extensions.
 * It includes the media type and file size of a given extension.
 */
@Data
public class CloudConfluenceExtensionsMetaData {
    
    /**
     * The MIME type of the media. For example, "application/pdf" or "image/png".
     */
	private String mediaType = null;
	
    /**
     * The size of the file in bytes. A null value indicates the file size is not specified.
     */
	private Long fileSize = null;
}