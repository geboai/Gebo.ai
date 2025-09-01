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
 * Represents expandable properties of Confluence content in an on-premise environment.
 * These properties specify what additional fields of the content can be expanded.
 * 
 * Data annotation is from Lombok to generate boilerplate code such as getters, setters, equals, hash, and toString methods.
 * 
 * AI generated comments
 */
@Data
public class OnPremiseConfluenceContentExpandable {
    // The container property can be expanded to fetch details about the content container.
    String container = null;
    
    // The children property can be expanded to include information about the child entities of the content.
    String children = null;
    
    // The space property can be expanded to retrieve details about the space to which the content belongs.
    String space = null;
    
    // The history property can be expanded to include the changes history of the content.
    String history = null;
    
    // The body property can be expanded to fetch the full content body.
    String body = null;

}