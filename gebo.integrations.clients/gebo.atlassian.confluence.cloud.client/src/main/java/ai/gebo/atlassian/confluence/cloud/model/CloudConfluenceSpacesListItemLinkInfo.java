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
 * Represents link information for a Confluence space list item.
 * This class holds the URLs for both the web user interface and API access.
 */
@Data
public class CloudConfluenceSpacesListItemLinkInfo {

    /** The URL for accessing the space via the web user interface. */
    private String webui = null;

    /** The URL for accessing the space programmatically via the API. */
    private String self = null;
}