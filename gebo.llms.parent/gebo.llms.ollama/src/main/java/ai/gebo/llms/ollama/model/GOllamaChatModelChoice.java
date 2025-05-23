/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.ollama.model;

import java.util.HashMap;
import java.util.Map;

import ai.gebo.llms.abstraction.layer.model.GBaseChatModelChoice;

/**
 * AI generated comments
 * A class that represents a choice from an Ollama chat model response.
 * This extends the base chat model choice with additional Ollama-specific model details.
 */
public class GOllamaChatModelChoice extends GBaseChatModelChoice {
	/** Map containing additional details specific to the Ollama model response */
	public Map<String, Object> modelDetails=new HashMap<String, Object>();
	
	/**
	 * Default constructor that initializes a new instance with empty model details.
	 */
	public GOllamaChatModelChoice() {
		
	}
	
	/**
	 * Gets the model-specific details associated with this choice.
	 * @return A map containing model-specific details
	 */
	public Map<String, Object> getModelDetails() {
		return modelDetails;
	}
	
	/**
	 * Sets the model-specific details for this choice.
	 * @param modelDetails A map containing the model-specific details
	 */
	public void setModelDetails(Map<String, Object> modelDetails) {
		this.modelDetails = modelDetails;
	}

}