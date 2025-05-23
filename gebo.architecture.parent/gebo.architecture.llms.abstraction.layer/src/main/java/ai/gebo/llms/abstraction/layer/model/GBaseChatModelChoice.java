/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.abstraction.layer.model;

/**
 * Gebo.ai comment agent
 * This class represents a choice model specific to chat functionalities.
 * It extends from GBaseModelChoice and includes additional chat-specific features.
 */
public class GBaseChatModelChoice extends GBaseModelChoice {
    // Indicates if the model supports structured output.
	private Boolean supportsStructuredOutput = null;
	
	// Indicates if the model supports function calls.
	private Boolean supportsFunctionCalls = null;
	
	/**
	 * Default constructor for GBaseChatModelChoice.
	 */
	public GBaseChatModelChoice() {
		
	}

	/**
	 * Gets the value that indicates whether the model supports structured output.
	 * @return Boolean value indicating support for structured output.
	 */
	public Boolean getSupportsStructuredOutput() {
		return supportsStructuredOutput;
	}

	/**
	 * Sets the value that indicates whether the model supports structured output.
	 * @param supportsStructuredOutput Boolean value to set.
	 */
	public void setSupportsStructuredOutput(Boolean supportsStructuredOutput) {
		this.supportsStructuredOutput = supportsStructuredOutput;
	}

	/**
	 * Gets the value that indicates whether the model supports function calls.
	 * @return Boolean value indicating support for function calls.
	 */
	public Boolean getSupportsFunctionCalls() {
		return supportsFunctionCalls;
	}

	/**
	 * Sets the value that indicates whether the model supports function calls.
	 * @param supportsFunctionCalls Boolean value to set.
	 */
	public void setSupportsFunctionCalls(Boolean supportsFunctionCalls) {
		this.supportsFunctionCalls = supportsFunctionCalls;
	}
}