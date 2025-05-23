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
 * Represents a Confluence user in the cloud environment.
 * <p>
 * This class holds information about a user including their type, username, and display name.
 * It uses Lombok's @Data annotation for automatic generation of boilerplate code like getters, setters, and more.
 * </p>
 * @auther AI generated comments
 */
@Data
public class CloudConfluenceByUser {
    /** The type of user, typically indicating the role or function of the user within Confluence. */
    private String type = null;
    
    /** The unique username of the user in the Confluence cloud. */
    private String username = null;
    
    /** The display name of the user, used for displaying the user's name in the UI. */
    private String displayName = null;
}