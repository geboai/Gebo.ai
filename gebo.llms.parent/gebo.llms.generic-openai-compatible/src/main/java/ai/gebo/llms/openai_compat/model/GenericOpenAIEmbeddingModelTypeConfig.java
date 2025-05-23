/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.openai_compat.model;

import ai.gebo.llms.abstraction.layer.model.GEmbeddingModelType;

/**
 * AI generated comments
 * 
 * Configuration class for generic OpenAI-compatible embedding model types.
 * This class extends GEmbeddingModelType to provide configuration parameters
 * for connecting to OpenAI-compatible embedding services with customizable endpoints.
 */
public class GenericOpenAIEmbeddingModelTypeConfig extends GEmbeddingModelType {
	/** Base URL for the OpenAI-compatible API endpoint */
	private String baseUrl = null;
	/** Provider for the list of available models */
	private String modelsListProvider = null;
	/** Identifier for the service provider */
	private String providerId=null;
	/** Flag indicating whether authentication is optional */
	private boolean optionalAuthentication=false;
	
	/**
	 * Constructor that sets the model configuration class to GenericOpenAIAPIEmbeddingModelConfig.
	 */
	public GenericOpenAIEmbeddingModelTypeConfig() {
		setModelConfigurationClass(GenericOpenAIAPIEmbeddingModelConfig.class.getName());
	}

	/**
	 * Gets the base URL for the API endpoint.
	 * 
	 * @return the base URL string
	 */
	public String getBaseUrl() {
		return baseUrl;
	}

	/**
	 * Sets the base URL for the API endpoint.
	 * 
	 * @param baseUrl the base URL to set
	 */
	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	/**
	 * Gets the models list provider.
	 * 
	 * @return the models list provider string
	 */
	public String getModelsListProvider() {
		return modelsListProvider;
	}

	/**
	 * Sets the models list provider.
	 * 
	 * @param modelsListProvider the models list provider to set
	 */
	public void setModelsListProvider(String modelsListProvider) {
		this.modelsListProvider = modelsListProvider;
	}

	/**
	 * Gets the provider identifier.
	 * 
	 * @return the provider ID string
	 */
	public String getProviderId() {
		return providerId;
	}

	/**
	 * Sets the provider identifier.
	 * 
	 * @param providerId the provider ID to set
	 */
	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}

	/**
	 * Checks if authentication is optional.
	 * 
	 * @return true if authentication is optional, false otherwise
	 */
	public boolean isOptionalAuthentication() {
		return optionalAuthentication;
	}

	/**
	 * Sets whether authentication is optional.
	 * 
	 * @param optionalAuthentication true to make authentication optional, false to require it
	 */
	public void setOptionalAuthentication(boolean optionalAuthentication) {
		this.optionalAuthentication = optionalAuthentication;
	}
}