/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.onxx_transformers_embedding.model;

import java.util.HashMap;
import java.util.Map;

import ai.gebo.llms.abstraction.layer.model.GBaseEmbeddingModelChoice;

/**
 * AI generated comments
 * This class represents a model choice for ONNX Transformers embedding models.
 * It extends the base embedding model choice class and provides additional 
 * functionality to store model-specific details.
 */
public class GONNXTransformersEmbeddingModelChoice extends GBaseEmbeddingModelChoice {
	/**
	 * Map to store additional model details and configuration parameters
	 * specific to ONNX Transformers embedding models.
	 */
	public Map<String, Object> modelDetails=new HashMap<String, Object>();
	
	/**
	 * Default constructor that initializes the model choice with an empty details map.
	 */
	public GONNXTransformersEmbeddingModelChoice() {
		
	}
	
	/**
	 * Retrieves the current model details map.
	 * 
	 * @return A map containing model-specific details and configuration parameters
	 */
	public Map<String, Object> getModelDetails() {
		return modelDetails;
	}
	
	/**
	 * Sets the model details map.
	 * 
	 * @param modelDetails A map containing model-specific details and configuration parameters
	 */
	public void setModelDetails(Map<String, Object> modelDetails) {
		this.modelDetails = modelDetails;
	}

}