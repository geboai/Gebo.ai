/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.atlassian.confluence.onpremise.model;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;

/**
 * AI generated comments
 *
 * Represents a content item in an on-premise Confluence instance.
 * This class extends the {@link OnPremiseConfluenceListItem} with additional fields specific to content items.
 */
@Data
public class OnPremiseConfluenceContentItem extends OnPremiseConfluenceListItem {
    
    /**
     * A map to store additional properties for the content item.
     * These properties can vary and are stored as key-value pairs.
     */
    Map<String, Object> extendsions = new HashMap<String, Object>();
    
    /**
     * An expandable object that contains details specific to Confluence content items,
     * which can be lazily loaded as needed.
     */
    OnPremiseConfluenceContentExpandable _expandable = null;
}