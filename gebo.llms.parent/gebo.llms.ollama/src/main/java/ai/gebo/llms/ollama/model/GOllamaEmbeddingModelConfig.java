/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.ollama.model;

import ai.gebo.llms.abstraction.layer.model.GBaseEmbeddingModelConfig;

/**
 * AI generated comments
 * 
 * Represents a configuration for Ollama embedding models.
 * This class extends the base embedding model configuration class and specifies
 * that it works with Ollama-specific embedding model choices.
 * No additional configuration parameters are required beyond what the base class provides.
 */
public class GOllamaEmbeddingModelConfig extends GBaseEmbeddingModelConfig<GOllamaEmbeddingModelChoice> {
	
    /**
     * Default constructor for initializing a new Ollama embedding model configuration.
     * Creates an instance with default settings.
     */
	public GOllamaEmbeddingModelConfig() {

	}

	
}