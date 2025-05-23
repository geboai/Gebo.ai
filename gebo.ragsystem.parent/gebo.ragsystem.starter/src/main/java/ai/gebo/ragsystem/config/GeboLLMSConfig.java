/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.ragsystem.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * AI generated comments
 * Configuration class for managing LLM (Large Language Model) service enablement settings.
 * This class maps application.properties or application.yml properties with the prefix
 * "ai.gebo.llms.config" to corresponding fields in this class.
 */
@Configuration
@ConfigurationProperties(value = "ai.gebo.llms.config")
public class GeboLLMSConfig {
	/**
	 * Flag to enable/disable Mistral AI service integration.
	 * Default value is true.
	 */
	Boolean mistralAIEnabled = true;
	
	/**
	 * Flag to enable/disable Ollama service integration.
	 * Default value is true.
	 */
	Boolean ollamaEnabled = true;
	
	/**
	 * Flag to enable/disable Google Vertex AI service integration.
	 * Default value is true.
	 */
	Boolean googleVertexEnabled = true;
	
	/**
	 * Flag to enable/disable Anthropic service integration.
	 * Default value is true.
	 */
	Boolean anthropicEnabled = true;
	
	/**
	 * Flag to enable/disable Hugging Face service integration.
	 * Default value is true.
	 */
	Boolean huggingfaceEnabled = true;
	
	/**
	 * Gets the enabled status of Mistral AI service.
	 * @return Boolean value indicating if Mistral AI is enabled
	 * @deprecated Use getMistralAIEnabled() instead
	 */
	public Boolean getmistralAIEnabled() {
		return mistralAIEnabled;
	}
	
	/**
	 * Sets the enabled status for Mistral AI service.
	 * @param mistralAIEnabled Boolean value to enable/disable Mistral AI
	 * @deprecated Use setMistralAIEnabled() instead
	 */
	public void setmistralAIEnabled(Boolean mistralAIEnabled) {
		this.mistralAIEnabled = mistralAIEnabled;
	}

	/**
	 * Gets the enabled status of Ollama service.
	 * @return Boolean value indicating if Ollama is enabled
	 */
	public Boolean getOllamaEnabled() {
		return ollamaEnabled;
	}
	
	/**
	 * Sets the enabled status for Ollama service.
	 * @param ollamaEnabled Boolean value to enable/disable Ollama
	 */
	public void setOllamaEnabled(Boolean ollamaEnabled) {
		this.ollamaEnabled = ollamaEnabled;
	}
	
	/**
	 * Gets the enabled status of Google Vertex AI service.
	 * @return Boolean value indicating if Google Vertex AI is enabled
	 */
	public Boolean getGoogleVertexEnabled() {
		return googleVertexEnabled;
	}
	
	/**
	 * Sets the enabled status for Google Vertex AI service.
	 * @param googleVertexEnabled Boolean value to enable/disable Google Vertex AI
	 */
	public void setGoogleVertexEnabled(Boolean googleVertexEnabled) {
		this.googleVertexEnabled = googleVertexEnabled;
	}
	
	/**
	 * Gets the enabled status of Anthropic service.
	 * @return Boolean value indicating if Anthropic is enabled
	 */
	public Boolean getAnthropicEnabled() {
		return anthropicEnabled;
	}
	
	/**
	 * Sets the enabled status for Anthropic service.
	 * @param anthropicEnabled Boolean value to enable/disable Anthropic
	 */
	public void setAnthropicEnabled(Boolean anthropicEnabled) {
		this.anthropicEnabled = anthropicEnabled;
	}
	
	/**
	 * Gets the enabled status of Hugging Face service.
	 * @return Boolean value indicating if Hugging Face is enabled
	 */
	public Boolean getHuggingfaceEnabled() {
		return huggingfaceEnabled;
	}
	
	/**
	 * Sets the enabled status for Hugging Face service.
	 * @param huggingfaceEnabled Boolean value to enable/disable Hugging Face
	 */
	public void setHuggingfaceEnabled(Boolean huggingfaceEnabled) {
		this.huggingfaceEnabled = huggingfaceEnabled;
	}
	
	/**
	 * Gets the enabled status of Mistral AI service.
	 * @return Boolean value indicating if Mistral AI is enabled
	 */
	public Boolean getMistralAIEnabled() {
		return mistralAIEnabled;
	}
	
	/**
	 * Sets the enabled status for Mistral AI service.
	 * @param mistralAIEnabled Boolean value to enable/disable Mistral AI
	 */
	public void setMistralAIEnabled(Boolean mistralAIEnabled) {
		this.mistralAIEnabled = mistralAIEnabled;
	}
}