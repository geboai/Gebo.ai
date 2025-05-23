/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.deepseek.model;

import java.util.HashMap;
import java.util.Map;

import ai.gebo.llms.abstraction.layer.model.GBaseChatModelChoice;

/**
 * AI generated comments
 * 
 * This class represents a chat model choice specific to the Deepseek LLM implementation.
 * It extends the base chat model choice class and adds Deepseek-specific model details.
 */
public class GDeepseekChatModelChoice extends GBaseChatModelChoice {
	/**
	 * Map to store Deepseek-specific model details and metadata.
	 */
	public Map<String, Object> modelDetails=new HashMap<String, Object>();
	
	/**
	 * Default constructor that initializes a new Deepseek chat model choice instance.
	 */
	public GDeepseekChatModelChoice() {
		
	}
	
	/**
	 * Gets the model details map.
	 * 
	 * @return The map containing Deepseek-specific model details
	 */
	public Map<String, Object> getModelDetails() {
		return modelDetails;
	}
	
	/**
	 * Sets the model details map.
	 * 
	 * @param modelDetails The map containing Deepseek-specific model details to set
	 */
	public void setModelDetails(Map<String, Object> modelDetails) {
		this.modelDetails = modelDetails;
	}

}