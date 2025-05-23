/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.openai_compat.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import ai.gebo.architecture.utils.GeboYamlPropertySourceFactory;
import ai.gebo.llms.openai_compat.model.GenericOpenAIChatModelTypeConfig;
import ai.gebo.llms.openai_compat.model.GenericOpenAIEmbeddingModelTypeConfig;

/**
 * AI generated comments
 * 
 * Configuration class for managing OpenAI-compatible provider configurations.
 * This class loads provider configurations from a YAML file and binds them to
 * properties prefixed with "ai.gebo.llms.generic.config".
 */
@Configuration
@ConfigurationProperties(value = "ai.gebo.llms.generic.config")
@PropertySource(value = "classpath:/openai-compatible-providers/providers.yml", factory = GeboYamlPropertySourceFactory.class)
public class GenericOpenAICompatibleProvidersConfig {
	
	/**
	 * List of chat model providers with OpenAI-compatible configurations
	 */
	private List<GenericOpenAIChatModelTypeConfig> chatModelProviders = new ArrayList<GenericOpenAIChatModelTypeConfig>();
	
	/**
	 * List of embedding model providers with OpenAI-compatible configurations
	 */
	private List<GenericOpenAIEmbeddingModelTypeConfig> embeddingModelProviders = new ArrayList<GenericOpenAIEmbeddingModelTypeConfig>();
	
	/**
	 * Returns the list of configured chat model providers
	 * @return List of chat model provider configurations
	 */
	public List<GenericOpenAIChatModelTypeConfig> getChatModelProviders() {
		return chatModelProviders;
	}
	
	/**
	 * Sets the list of chat model providers
	 * @param chatModelProviders List of chat model provider configurations to set
	 */
	public void setChatModelProviders(List<GenericOpenAIChatModelTypeConfig> chatModelProviders) {
		this.chatModelProviders = chatModelProviders;
	}
	
	/**
	 * Returns the list of configured embedding model providers
	 * @return List of embedding model provider configurations
	 */
	public List<GenericOpenAIEmbeddingModelTypeConfig> getEmbeddingModelProviders() {
		return embeddingModelProviders;
	}
	
	/**
	 * Sets the list of embedding model providers
	 * @param embeddingModelProviders List of embedding model provider configurations to set
	 */
	public void setEmbeddingModelProviders(List<GenericOpenAIEmbeddingModelTypeConfig> embeddingModelProviders) {
		this.embeddingModelProviders = embeddingModelProviders;
	}

}