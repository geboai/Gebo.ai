/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.mistralai.model;

import ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig;

/**
 * AI generated comments
 * Configuration class for Mistral AI chat model implementations.
 * This class extends the base chat model configuration and specializes it
 * for Mistral AI models by using GMistralChatModelChoice as the type parameter.
 * It currently provides basic functionality with a default constructor,
 * allowing for future extensions with Mistral-specific configuration options.
 */
public class GMistralChatModelConfig extends GBaseChatModelConfig<GMistralChatModelChoice>{
	
	
	/**
	 * Default constructor that initializes a new Mistral chat model configuration.
	 * Uses default values from the parent class.
	 */
	public GMistralChatModelConfig() {
		
	}
	

	

}