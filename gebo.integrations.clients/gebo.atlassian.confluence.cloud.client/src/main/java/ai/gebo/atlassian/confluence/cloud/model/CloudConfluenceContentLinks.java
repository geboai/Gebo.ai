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
 * Represents the links associated with Confluence content in a cloud environment.
 * This class contains URLs for various actions that can be performed on the content.
 * 
 * Gebo.ai Commentor: AI generated comments
 */
@Data
public class CloudConfluenceContentLinks {
    // URL for accessing the content in a web UI
    private String webui = null;

    // URL for editing the content
    private String edit = null;

    // URL for accessing the content's self-link reference
    private String self = null;
}