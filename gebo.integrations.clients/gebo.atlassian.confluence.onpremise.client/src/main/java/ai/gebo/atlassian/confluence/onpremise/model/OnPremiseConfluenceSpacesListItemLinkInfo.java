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
 * Represents the link information for a Confluence space item in an on-premise instance.
 * This class utilizes Lombok to automatically generate getters, setters, and other utility methods.
 * <p>
 * AI generated comments
 */
@Data
public class OnPremiseConfluenceSpacesListItemLinkInfo {

    /**
     * The URL for the web user interface of the space.
     */
    String webui = null;

    /**
     * The URL for the API call to this space's own resource.
     */
    String self = null;
}