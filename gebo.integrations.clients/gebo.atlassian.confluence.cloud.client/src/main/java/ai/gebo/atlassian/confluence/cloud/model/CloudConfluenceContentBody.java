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
 * Represents the body of Confluence content in the cloud environment.
 * Utilizes Lombok's @Data annotation to automatically generate
 * boilerplate code such as getters, setters, and constructors.
 * 
 * AI generated comments
 */
@Data
public class CloudConfluenceContentBody {
    
    /** 
     * The view representation of the Confluence content.
     * Initialized to null by default.
     */
    private CloudConfluenceView view = null;
}