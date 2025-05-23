/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.models.metainfos;

/**
 * AI generated comments
 * 
 * A class to store meta-information about a specific model, including details
 * like provider ID, model ID, and various model capabilities.
 */
public class ModelMetaInfo {
	// Identifier for the provider of the model
	private String providerId = null;
	
	// Identifier for the model
	private String modelId = null;
	
	// Indicates if the model supports chat-based interactions
	private Boolean chatModel = null;

	// Indicates if the model supports embedding
	private Boolean embeddingModel = null;

	// Indicates if the model supports structured output data
	private Boolean supportsStructuredOutput = null;

	// Indicates if the model supports function calls
	private Boolean supportsFunctionCalls = null;

	// Context length for the model; `null` implies unspecified
	private Integer contextLength = null;

	// Threshold for tokenization
	private Integer tokenizingThreashold = null;

	// Maximum tokens for output generation
	private Integer maxOutputToken = null;

	// Description of the model
	private String description = null;

	// URL for additional information about the model
	private String informativeUrl = null;

	/**
	 * Default constructor for ModelMetaInfo.
	 */
	public ModelMetaInfo() {

	}

	/**
	 * Returns the provider ID of the model.
	 * 
	 * @return providerId The provider ID as a String.
	 */
	public String getProviderId() {
		return providerId;
	}

	/**
	 * Sets the provider ID of the model.
	 * 
	 * @param providerId A String representing the provider ID.
	 */
	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}

	/**
	 * Returns the model ID.
	 * 
	 * @return modelId The model ID as a String.
	 */
	public String getModelId() {
		return modelId;
	}

	/**
	 * Sets the model ID.
	 * 
	 * @param modelId A String representing the model ID.
	 */
	public void setModelId(String modelId) {
		this.modelId = modelId;
	}

	/**
	 * Returns whether the model supports chat interactions.
	 * 
	 * @return chatModel A Boolean indicating chat support.
	 */
	public Boolean getChatModel() {
		return chatModel;
	}

	/**
	 * Sets if the model supports chat interactions.
	 * 
	 * @param chatModel A Boolean to set chat support.
	 */
	public void setChatModel(Boolean chatModel) {
		this.chatModel = chatModel;
	}

	/**
	 * Returns whether the model supports embedding.
	 * 
	 * @return embeddingModel A Boolean indicating embedding support.
	 */
	public Boolean getEmbeddingModel() {
		return embeddingModel;
	}

	/**
	 * Sets if the model supports embedding.
	 * 
	 * @param embeddingModel A Boolean to set embedding support.
	 */
	public void setEmbeddingModel(Boolean embeddingModel) {
		this.embeddingModel = embeddingModel;
	}

	/**
	 * Returns the context length for the model.
	 * 
	 * @return contextLength An Integer representing context length.
	 */
	public Integer getContextLength() {
		return contextLength;
	}

	/**
	 * Sets the context length for the model.
	 * 
	 * @param contextLength An Integer to set context length.
	 */
	public void setContextLength(Integer contextLength) {
		this.contextLength = contextLength;
	}

	/**
	 * Returns the tokenizing threshold.
	 * 
	 * @return tokenizingThreashold An Integer representing the tokenizing threshold.
	 */
	public Integer getTokenizingThreashold() {
		return tokenizingThreashold;
	}

	/**
	 * Sets the tokenizing threshold.
	 * 
	 * @param tokenizingThreashold An Integer to set the tokenizing threshold.
	 */
	public void setTokenizingThreashold(Integer tokenizingThreashold) {
		this.tokenizingThreashold = tokenizingThreashold;
	}

	/**
	 * Returns a description of the model.
	 * 
	 * @return description A String describing the model.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description of the model.
	 * 
	 * @param description A String to describe the model.
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Returns the informative URL for the model.
	 * 
	 * @return informativeUrl A String URL with more information.
	 */
	public String getInformativeUrl() {
		return informativeUrl;
	}

	/**
	 * Sets the informative URL for the model.
	 * 
	 * @param informativeUrl A String URL with more information.
	 */
	public void setInformativeUrl(String informativeUrl) {
		this.informativeUrl = informativeUrl;
	}

	/**
	 * Returns the maximum number of tokens for output.
	 * 
	 * @return maxOutputToken An Integer representing the max output token count.
	 */
	public Integer getMaxOutputToken() {
		return maxOutputToken;
	}

	/**
	 * Sets the maximum number of tokens for output.
	 * 
	 * @param maxOutputToken An Integer to set max output token count.
	 */
	public void setMaxOutputToken(Integer maxOutputToken) {
		this.maxOutputToken = maxOutputToken;
	}

	/**
	 * Returns if the model supports structured output.
	 * 
	 * @return supportsStructuredOutput A Boolean for structured output capability.
	 */
	public Boolean getSupportsStructuredOutput() {
		return supportsStructuredOutput;
	}

	/**
	 * Sets if the model supports structured output.
	 * 
	 * @param supportsStructuredOutput A Boolean to set structured output capability.
	 */
	public void setSupportsStructuredOutput(Boolean supportsStructuredOutput) {
		this.supportsStructuredOutput = supportsStructuredOutput;
	}

	/**
	 * Returns if the model supports function calls.
	 * 
	 * @return supportsFunctionCalls A Boolean indicating function call support.
	 */
	public Boolean getSupportsFunctionCalls() {
		return supportsFunctionCalls;
	}

	/**
	 * Sets if the model supports function calls.
	 * 
	 * @param supportsFunctionCalls A Boolean to set function call support.
	 */
	public void setSupportsFunctionCalls(Boolean supportsFunctionCalls) {
		this.supportsFunctionCalls = supportsFunctionCalls;
	}
}