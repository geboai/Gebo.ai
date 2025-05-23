/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.ollama.model;

import ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig;

/**
 * AI generated comments
 * 
 * This class represents the configuration for Ollama chat models.
 * It extends the base chat model configuration class and specifies
 * GOllamaChatModelChoice as the type parameter for model choices.
 * This configuration is used to set up and customize Ollama chat model
 * behavior when generating responses.
 */
public class GOllamaChatModelConfig extends GBaseChatModelConfig<GOllamaChatModelChoice> {
	
	/**
	 * Default constructor that initializes a new Ollama chat model configuration
	 * with default settings.
	 */
	public GOllamaChatModelConfig() {

	}

	
}