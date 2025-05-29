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
 * Represents a Confluence user in an on-premise environment.
 * This class holds details about the user such as their type,
 * username, and display name.
 * 
 * Data annotation provided by Lombok to generate 
 * boilerplate code such as getters, setters, toString, etc.
 * 
 * AI generated comments
 */
@Data
public class OnPremiseConfluenceByUser {
     // The type of the user, e.g., admin, user, etc.
     String type = null;
     
     // The username used by the user to log into Confluence
     String username = null;
     
     // The display name of the user as seen in Confluence
     String displayName = null;

}