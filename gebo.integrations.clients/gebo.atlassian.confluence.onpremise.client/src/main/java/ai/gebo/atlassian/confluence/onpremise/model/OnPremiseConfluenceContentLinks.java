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
 * AI generated comments
 * Represents the links associated with an OnPremise Confluence content item.
 * This class uses Lombok's @Data annotation to automatically generate
 * boilerplate code such as getters, setters, and other utility methods.
 */
@Data
public class OnPremiseConfluenceContentLinks {

    /**
     * The URL for viewing the content item in the web user interface.
     */
    String webui = null;

    /**
     * The URL for editing the content item.
     */
    String edit = null;

    /**
     * The URL for accessing the content item programmatically.
     */
    String self = null;
}