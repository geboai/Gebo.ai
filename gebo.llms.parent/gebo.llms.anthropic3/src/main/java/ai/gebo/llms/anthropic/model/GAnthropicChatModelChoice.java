/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.anthropic.model;

import java.util.HashMap;
import java.util.Map;

import ai.gebo.llms.abstraction.layer.model.GBaseChatModelChoice;

/**
 * AI generated comments
 * This class represents a specific implementation of a chat model choice for Anthropic LLM.
 * It extends the base chat model choice class and adds Anthropic-specific model details.
 */
public class GAnthropicChatModelChoice extends GBaseChatModelChoice {
	/**
	 * A map containing additional model-specific details for Anthropic's API response.
	 */
	public Map<String, Object> modelDetails=new HashMap<String, Object>();
	
	/**
	 * Default constructor that initializes an empty Anthropic chat model choice.
	 */
	public GAnthropicChatModelChoice() {
		
	}
	
	/**
	 * Gets the model-specific details associated with this Anthropic chat model choice.
	 * 
	 * @return A map containing Anthropic-specific model details
	 */
	public Map<String, Object> getModelDetails() {
		return modelDetails;
	}
	
	/**
	 * Sets the model-specific details for this Anthropic chat model choice.
	 * 
	 * @param modelDetails A map containing Anthropic-specific model details
	 */
	public void setModelDetails(Map<String, Object> modelDetails) {
		this.modelDetails = modelDetails;
	}

}