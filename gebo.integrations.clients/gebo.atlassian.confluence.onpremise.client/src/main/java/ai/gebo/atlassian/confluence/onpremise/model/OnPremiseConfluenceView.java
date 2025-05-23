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
 * Represents a view of a Confluence page on-premise, typically including
 * its content value and the representation format.
 * <p>
 * The class uses Lombok's {@code @Data} annotation to auto-generate
 * boilerplate code such as getters, setters, and other utility methods.
 * </p>
 * 
 * @author AI
 * @version 1.0
 * @since 2023
 * 
 * AI generated comments
 */
@Data
public class OnPremiseConfluenceView {
    /** The content value of the Confluence view. */
	String value = null;
    
    /** The format in which the content is represented. (e.g., 'storage', 'editor') */
	String representation = null;
}