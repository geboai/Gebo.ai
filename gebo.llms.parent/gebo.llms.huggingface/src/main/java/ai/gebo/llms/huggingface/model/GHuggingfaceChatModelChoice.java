/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.huggingface.model;

import java.util.HashMap;
import java.util.Map;

import ai.gebo.llms.abstraction.layer.model.GBaseChatModelChoice;

/**
 * AI generated comments
 * Represents a chat model choice for Huggingface LLM implementation.
 * Extends the base chat model choice class by adding additional model details
 * specific to Huggingface models.
 */
public class GHuggingfaceChatModelChoice extends GBaseChatModelChoice {
	/**
	 * A map to store additional model-specific details from Huggingface responses.
	 */
	public Map<String, Object> modelDetails=new HashMap<String, Object>();
	
	/**
	 * Default constructor for creating a new Huggingface chat model choice.
	 */
	public GHuggingfaceChatModelChoice() {
		
	}
	
	/**
	 * Retrieves the model-specific details.
	 * 
	 * @return A map containing model-specific details.
	 */
	public Map<String, Object> getModelDetails() {
		return modelDetails;
	}
	
	/**
	 * Sets the model-specific details.
	 * 
	 * @param modelDetails A map containing model-specific details to be set.
	 */
	public void setModelDetails(Map<String, Object> modelDetails) {
		this.modelDetails = modelDetails;
	}

}