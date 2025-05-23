/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.chat.abstraction.layer.model;

import ai.gebo.model.base.GBaseObject;

/**
 * This class represents a short model information entity, which extends the GBaseObject.
 * It includes details about the model type and model code.
 * Gebo.ai comment agent
 */
public class GShortModelInfo extends GBaseObject {
	
	// The type of the model
	String modelType = null;
	
	// The code of the model
	String modelCode = null;

	/**
	 * Retrieves the model type.
	 *
	 * @return the model type as a String
	 */
	public String getModelType() {
		return modelType;
	}

	/**
	 * Sets the model type.
	 *
	 * @param modelType the type of the model to set as a String
	 */
	public void setModelType(String modelType) {
		this.modelType = modelType;
	}

	/**
	 * Retrieves the model code.
	 *
	 * @return the model code as a String
	 */
	public String getModelCode() {
		return modelCode;
	}

	/**
	 * Sets the model code.
	 *
	 * @param modelCode the code of the model to set as a String
	 */
	public void setModelCode(String modelCode) {
		this.modelCode = modelCode;
	}
}