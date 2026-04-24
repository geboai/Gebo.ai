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
import ai.gebo.llms.openai_compat.modeltypes.GenericOpenAIChatModelTypeConfig;
import ai.gebo.llms.openai_compat.modeltypes.GenericOpenAIEmbeddingModelTypeConfig;
import ai.gebo.llms.openai_compat.modeltypes.GenericOpenAIImageModelTypeConfig;
import ai.gebo.llms.openai_compat.modeltypes.GenericOpenAIRankerModelType;
import ai.gebo.llms.openai_compat.modeltypes.GenericOpenAITextToSpeechModelType;
import ai.gebo.llms.openai_compat.modeltypes.GenericOpenAITranscriptModelType;

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

	private List<GenericOpenAIImageModelTypeConfig> imageModelProviders = new ArrayList<GenericOpenAIImageModelTypeConfig>();

	private List<GenericOpenAITextToSpeechModelType> textToSpeechModelProviders = new ArrayList<GenericOpenAITextToSpeechModelType>();

	private List<GenericOpenAITranscriptModelType> transcriptModelProviders = new ArrayList<GenericOpenAITranscriptModelType>();

	private List<GenericOpenAIRankerModelType> rankerModelProviders = new ArrayList<>();

	/**
	 * Returns the list of configured chat model providers
	 * 
	 * @return List of chat model provider configurations
	 */
	public List<GenericOpenAIChatModelTypeConfig> getChatModelProviders() {
		return chatModelProviders;
	}

	/**
	 * Sets the list of chat model providers
	 * 
	 * @param chatModelProviders List of chat model provider configurations to set
	 */
	public void setChatModelProviders(List<GenericOpenAIChatModelTypeConfig> chatModelProviders) {
		this.chatModelProviders = chatModelProviders;
	}

	/**
	 * Returns the list of configured embedding model providers
	 * 
	 * @return List of embedding model provider configurations
	 */
	public List<GenericOpenAIEmbeddingModelTypeConfig> getEmbeddingModelProviders() {
		return embeddingModelProviders;
	}

	/**
	 * Sets the list of embedding model providers
	 * 
	 * @param embeddingModelProviders List of embedding model provider
	 *                                configurations to set
	 */
	public void setEmbeddingModelProviders(List<GenericOpenAIEmbeddingModelTypeConfig> embeddingModelProviders) {
		this.embeddingModelProviders = embeddingModelProviders;
	}

	public List<GenericOpenAIImageModelTypeConfig> getImageModelProviders() {
		return imageModelProviders;
	}

	public void setImageModelProviders(List<GenericOpenAIImageModelTypeConfig> imageModelProviders) {
		this.imageModelProviders = imageModelProviders;
	}

	public List<GenericOpenAITextToSpeechModelType> getTextToSpeechModelProviders() {
		return textToSpeechModelProviders;
	}

	public void setTextToSpeechModelProviders(List<GenericOpenAITextToSpeechModelType> textToSpeechModelProviders) {
		this.textToSpeechModelProviders = textToSpeechModelProviders;
	}

	public List<GenericOpenAITranscriptModelType> getTranscriptModelProviders() {
		return transcriptModelProviders;
	}

	public void setTranscriptModelProviders(List<GenericOpenAITranscriptModelType> transcriptModelProviders) {
		this.transcriptModelProviders = transcriptModelProviders;
	}

	public List<GenericOpenAIRankerModelType> getRankerModelProviders() {
		return rankerModelProviders;
	}

	public void setRankerModelProviders(List<GenericOpenAIRankerModelType> rankerModelProviders) {
		this.rankerModelProviders = rankerModelProviders;
	}

}