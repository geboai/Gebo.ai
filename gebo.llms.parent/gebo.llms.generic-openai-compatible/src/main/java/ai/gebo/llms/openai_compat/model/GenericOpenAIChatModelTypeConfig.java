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

/**
 * AI generated comments
 * 
 * Represents a configuration for generic OpenAI-compatible chat model types.
 * This class extends GChatModelType and provides additional configuration options
 * specific to OpenAI-compatible API implementations, such as base URL and models list provider.
 */
public class GenericOpenAIChatModelTypeConfig extends GChatModelType {
	/** The base URL of the OpenAI-compatible API */
	private String baseUrl = null;
	/** The provider name for the models list */
	private String modelsListProvider = null;
	/** The ID of the provider */
	private String providerId=null;
	/** Flag indicating whether authentication is optional */
	private boolean optionalAuthentication=false;
	
	/**
	 * Constructor that initializes the model configuration class.
	 * Sets the default model configuration class to GenericOpenAIAPIChatModelConfig.
	 */
	public GenericOpenAIChatModelTypeConfig() {
		setModelConfigurationClass(GenericOpenAIAPIChatModelConfig.class.getName());
	}

	/**
	 * Gets the base URL for the OpenAI-compatible API.
	 * 
	 * @return The base URL string or null if not set
	 */
	public String getBaseUrl() {
		return baseUrl;
	}

	/**
	 * Sets the base URL for the OpenAI-compatible API.
	 * 
	 * @param baseUrl The base URL to set
	 */
	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	/**
	 * Gets the models list provider name.
	 * 
	 * @return The models list provider string or null if not set
	 */
	public String getModelsListProvider() {
		return modelsListProvider;
	}

	/**
	 * Sets the models list provider name.
	 * 
	 * @param modelsListProvider The models list provider to set
	 */
	public void setModelsListProvider(String modelsListProvider) {
		this.modelsListProvider = modelsListProvider;
	}

	/**
	 * Gets the provider ID.
	 * 
	 * @return The provider ID string or null if not set
	 */
	public String getProviderId() {
		return providerId;
	}

	/**
	 * Sets the provider ID.
	 * 
	 * @param providerId The provider ID to set
	 */
	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}

	/**
	 * Checks if authentication is optional for this model.
	 * 
	 * @return true if authentication is optional, false otherwise
	 */
	public boolean isOptionalAuthentication() {
		return optionalAuthentication;
	}

	/**
	 * Sets whether authentication is optional for this model.
	 * 
	 * @param optionalAuthentication The optional authentication flag to set
	 */
	public void setOptionalAuthentication(boolean optionalAuthentication) {
		this.optionalAuthentication = optionalAuthentication;
	}

}