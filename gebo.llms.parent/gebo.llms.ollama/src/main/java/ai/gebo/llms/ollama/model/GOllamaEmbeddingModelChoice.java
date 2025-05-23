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

import ai.gebo.llms.abstraction.layer.model.GBaseEmbeddingModelChoice;

/**
 * AI generated comments
 * 
 * A class that represents an Ollama embedding model choice.
 * This class extends the base embedding model choice and adds
 * additional model-specific details that can be stored in a map.
 */
public class GOllamaEmbeddingModelChoice extends GBaseEmbeddingModelChoice {
	/** Map to store additional details about the Ollama embedding model */
	public Map<String, Object> modelDetails=new HashMap<String, Object>();
	
	/**
	 * Default constructor that initializes a new instance with an empty model details map
	 */
	public GOllamaEmbeddingModelChoice() {
		
	}
	
	/**
	 * Gets the model details map
	 * 
	 * @return A map containing model-specific details
	 */
	public Map<String, Object> getModelDetails() {
		return modelDetails;
	}
	
	/**
	 * Sets the model details map
	 * 
	 * @param modelDetails A map containing model-specific details to set
	 */
	public void setModelDetails(Map<String, Object> modelDetails) {
		this.modelDetails = modelDetails;
	}

}