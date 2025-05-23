/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.atlassian.confluence.cloud.model;

import java.util.HashMap;

import lombok.Data;

/**
 * AI generated comments
 * Represents metadata for an attachment in Confluence Cloud, including media type and associated labels.
 */
@Data
public class CloudConfluenceAttachmentMetaData {

    /** 
     * The media type of the attachment, e.g., "image/png" or "application/pdf".
     */
    private String mediaType = null;

    /**
     * A map of labels associated with the attachment. 
     * The keys are label names and the values are additional label properties.
     */
    private HashMap<String, Object> labels = new HashMap<String, Object>();
}