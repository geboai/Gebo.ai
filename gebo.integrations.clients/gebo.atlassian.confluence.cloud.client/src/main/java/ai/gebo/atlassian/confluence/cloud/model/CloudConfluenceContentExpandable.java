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
 * Represents the expandable properties for content in Confluence cloud.
 * This class uses Lombok to auto-generate boilerplate code like getters, setters, and other utility methods.
 * 
 * @author AI
 * @version 1.0
 * @since 2023
 * 
 * AI generated comments
 */
@Data
public class CloudConfluenceContentExpandable {

    /** Represents the container property of the content. */
    private String container = null;

    /** Represents the children property of the content. */
    private String children = null;

    /** Represents the space property of the content. */
    private String space = null;
    
    /** Represents the history property of the content. */
    private String history = null;

    /** Represents the body property of the content. */
    private String body = null;
}