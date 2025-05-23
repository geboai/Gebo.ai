/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.chat.abstraction.layer.model;

/**
 * Gebo.ai comment agent
 * 
 * Represents a specialized chat response in the Gebo.ai system, extending
 * the functionality of a templated chat response with a specific type
 * parameter of String.
 */
public class GeboChatResponse extends GeboTemplatedChatResponse<String> {
	
    /**
     * Default constructor for GeboChatResponse. Initializes a new instance 
     * of this class with no parameters.
     */
	public GeboChatResponse() {
	}
	
    /**
     * Constructs a new GeboChatResponse by copying the data from an existing 
     * GeboTemplatedChatResponse.
     *
     * @param r The existing GeboTemplatedChatResponse object to be copied.
     */
	public GeboChatResponse(GeboTemplatedChatResponse<String> r) {
		super(r);
	}
	
}