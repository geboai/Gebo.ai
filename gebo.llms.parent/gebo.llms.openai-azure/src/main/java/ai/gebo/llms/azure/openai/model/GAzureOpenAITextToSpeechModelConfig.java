/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.azure.openai.model;

import ai.gebo.llms.abstraction.layer.model.GBaseTextToSpeachModelConfig;

/**
 * AI generated comments
 * 
 * This class represents the configuration for OpenAI's text-to-speech model.
 * It extends the base text-to-speech model configuration and is specialized for
 * handling OpenAI's specific configuration options through the GOpenAITextToSpeechModelChoice type.
 */
public class GAzureOpenAITextToSpeechModelConfig extends GBaseTextToSpeachModelConfig<GAzureOpenAITextToSpeechModelChoice> {

	/**
	 * Default constructor for creating a new OpenAI text-to-speech model configuration.
	 * Initializes an empty configuration that can be populated with specific settings.
	 */
	public GAzureOpenAITextToSpeechModelConfig() {

	}

}