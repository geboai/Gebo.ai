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
 * Represents the body of the content in an on-premise Confluence environment.
 * Utilizes Lombok's @Data for automatic generation of getter, setter, equals, hashCode, and toString methods.
 */
@Data
public class OnPremiseConfluenceContentBody {

    /**
     * The view associated with the Confluence content body.
     * Initially set to null and may be populated with a specific view representation.
     */
    OnPremiseConfluenceView view = null;
}