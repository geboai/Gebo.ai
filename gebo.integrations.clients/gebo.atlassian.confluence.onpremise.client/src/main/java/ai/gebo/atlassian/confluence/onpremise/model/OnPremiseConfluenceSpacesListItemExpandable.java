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
 * Represents an expandable list item for Confluence spaces on-premises.
 * This class holds various expandable properties for a Confluence space.
 */
@Data
public class OnPremiseConfluenceSpacesListItemExpandable {
    
    /** Metadata information for the Confluence space. */
    String metadata = null;
    
    /** Icon associated with the Confluence space. */
    String icon = null;
    
    /** Description of the Confluence space. */
    String description = null;
    
    /** Homepage URL or identifier for the Confluence space. */
    String homepage = null;
}