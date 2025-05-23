/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.abstraction.layer.model;

import ai.gebo.model.base.GBaseObject;

/**
 * Gebo.ai comment agent
 * 
 * Represents a model type with a specific configuration class.
 * This class extends the functionalities of GBaseObject.
 */
public class GModelType extends GBaseObject {
    
    // Stores the name of the class used for model configuration
    private String modelConfigurationClass = null;

    /**
     * Default constructor for GModelType.
     */
    public GModelType() {
        // No specific initialization required
    }

    /**
     * Retrieves the model configuration class name.
     * 
     * @return the class name as a String.
     */
    public String getModelConfigurationClass() {
        return modelConfigurationClass;
    }

    /**
     * Sets the model configuration class name.
     * 
     * @param modelConfigurationClass the class name to set.
     */
    public void setModelConfigurationClass(String modelConfigurationClass) {
        this.modelConfigurationClass = modelConfigurationClass;
    }
}