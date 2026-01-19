/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.llms.openai_compat.model;

import ai.gebo.llms.abstraction.layer.model.GChatModelType;
import lombok.Data;

/**
 * AI generated comments
 * 
 * Represents a configuration for generic OpenAI-compatible chat model types.
 * This class extends GChatModelType and provides additional configuration
 * options specific to OpenAI-compatible API implementations, such as base URL
 * and models list provider.
 */
@Data
public class GenericOpenAIChatModelTypeConfig extends GChatModelType {
	/** The base URL of the OpenAI-compatible API */
	private String baseUrl = null;
	/** The provider name for the models list */
	private String modelsListProvider = null;
	/** The ID of the provider */
	private String providerId = null;
	/** Flag indicating whether authentication is optional */
	private boolean optionalAuthentication = false;
	private boolean applyThinkingMarkupHandling = false;

	/**
	 * Constructor that initializes the model configuration class. Sets the default
	 * model configuration class to GenericOpenAIAPIChatModelConfig.
	 */
	public GenericOpenAIChatModelTypeConfig() {
		setModelConfigurationClass(GenericOpenAIAPIChatModelConfig.class.getName());
	}
}