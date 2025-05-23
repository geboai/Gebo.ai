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
import java.util.Map;

import lombok.Data;

/**
 * AI generated comments
 * Represents a content item in Cloud Confluence with associated metadata and expandable properties.
 * This class extends the {@link CloudConfluenceListItem} class.
 * Uses Lombok's @Data annotation to generate boilerplate code such as getters, setters, and toString.
 */
@Data
public class CloudConfluenceContentItem extends CloudConfluenceListItem {
    
    /**
     * A map to hold extension data for the content item. This allows for additional attributes or metadata beyond the predefined fields.
     */
    private Map<String, Object> extensions = new HashMap<String, Object>();

    /**
     * Represents expandable properties for the content item. This is initialized as null by default.
     */
    private CloudConfluenceContentExpandable _expandable = null;
}