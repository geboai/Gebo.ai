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
 * Represents a list item containing information about a Confluence Space in the cloud.
 * This class extends {@link CloudConfluenceListItem}.
 * The class is immutable and uses Lombok's @Data annotation to generate getters, setters, and other utility methods.
 */
@Data
public class CloudConfluenceSpacesListItem extends CloudConfluenceListItem {
    
    /** The unique key of the Confluence Space. */
    String key = null;
    
    /** The name of the Confluence Space. */
    String name = null;
    
    /** The homepage content of the Confluence Space. */
    CloudConfluenceContent homepage = null;
    
    /** Additional expandable properties related to the Confluence Space. */
    CloudConfluenceSpacesListItemExpandable _expandable = null;
}