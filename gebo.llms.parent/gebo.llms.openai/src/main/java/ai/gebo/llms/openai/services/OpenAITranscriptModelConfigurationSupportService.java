/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.openai.services;

import java.io.InputStream;
import java.util.List;

import org.springframework.ai.openai.OpenAiAudioTranscriptionModel;
import org.springframework.ai.openai.OpenAiAudioTranscriptionOptions;
import org.springframework.ai.openai.api.OpenAiAudioApi;
import org.springframework.ai.openai.api.OpenAiAudioApi.TranscriptResponseFormat;
import org.springframework.ai.openai.api.OpenAiAudioApi.WhisperModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;

import ai.gebo.llms.abstraction.layer.model.GTranscriptModelType;
import ai.gebo.llms.abstraction.layer.services.GAbstractConfigurableTranscriptModel;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableTranscriptModel;
import ai.gebo.llms.abstraction.layer.services.IGModelApiAccessReadUtils;
import ai.gebo.llms.abstraction.layer.services.IGModelApiAccessReadUtils.ApiKeyInfo;
import ai.gebo.llms.abstraction.layer.services.IGTranscriptModelConfigurationSupportService;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.openai.model.GOpenAITranscriptModelChoice;
import ai.gebo.llms.openai.model.GOpenAITranscriptModelConfig;
import ai.gebo.model.OperationStatus;

/**
 * AI generated comments
 * 
 * Service class responsible for configuring and supporting OpenAI transcript models.
 * This service provides functionality to create, configure, and manage OpenAI transcript models
 * that can convert audio to text using OpenAI's Whisper model.
 */
@Service
public class OpenAITranscriptModelConfigurationSupportService implements
		IGTranscriptModelConfigurationSupportService<GOpenAITranscriptModelChoice, GOpenAITranscriptModelConfig> {
	
	/**
	 * Static transcript model type for OpenAI transcript service
	 */
	static final GTranscriptModelType type = new GTranscriptModelType();
	static {
		type.setCode("openai-transcript");
		type.setDescription("OpenAI transcript service");
	}
	
	/**
	 * Utility for reading API access information
	 */
	@Autowired
	IGModelApiAccessReadUtils apiKeyReader;

	/**
	 * Implementation of a configurable transcript model for OpenAI services.
	 * Extends the abstract transcript model and provides OpenAI-specific functionality.
	 */
	public class OpenAIConfigurableTranscriptModel
			extends GAbstractConfigurableTranscriptModel<GOpenAITranscriptModelConfig, OpenAiAudioTranscriptionModel> {

		/**
		 * Processes the audio from an input stream and returns the transcribed text
		 *
		 * @param audioResource The input stream containing audio data to transcribe
		 * @return The transcribed text from the audio
		 * @throws LLMConfigException If there is an issue with the configuration
		 */
		@Override
		public String call(InputStream audioResource) throws LLMConfigException {
			return model.call(new InputStreamResource(audioResource));
		}

		/**
		 * Configures the OpenAI audio transcription model based on the provided configuration
		 *
		 * @param config The OpenAI transcript model configuration
		 * @param type The transcript model type
		 * @return A configured OpenAI audio transcription model
		 * @throws LLMConfigException If there is an issue with configuration or API access
		 */
		@Override
		protected OpenAiAudioTranscriptionModel configureModel(GOpenAITranscriptModelConfig config,
				GTranscriptModelType type) throws LLMConfigException {
			ApiKeyInfo apiKey;

			apiKey = apiKeyReader.getApiKeyInfo(config);
			OpenAiAudioApi audioApi = OpenAiAudioApi.builder().apiKey(apiKey.getApiKey()).build();
			org.springframework.ai.openai.OpenAiAudioTranscriptionOptions.Builder builder = OpenAiAudioTranscriptionOptions
					.builder();

			builder.responseFormat(TranscriptResponseFormat.TEXT).temperature(0f).model(WhisperModel.WHISPER_1.value);
			OpenAiAudioTranscriptionOptions options = builder.build();
			OpenAiAudioTranscriptionModel model = new OpenAiAudioTranscriptionModel(audioApi, options,
					RetryTemplate.defaultInstance());

			return model;
		}
	}

	/**
	 * Default constructor for the OpenAI transcript model configuration support service
	 */
	public OpenAITranscriptModelConfigurationSupportService() {
	}

	/**
	 * Returns the transcript model type supported by this service
	 *
	 * @return The OpenAI transcript model type
	 */
	@Override
	public GTranscriptModelType getType() {
		return type;
	}

	/**
	 * Retrieves available model choices for OpenAI transcript models
	 *
	 * @param config The model configuration
	 * @return Operation status containing a list of available model choices
	 */
	@Override
	public OperationStatus<List<GOpenAITranscriptModelChoice>> getModelChoices(GOpenAITranscriptModelConfig config) {
		GOpenAITranscriptModelChoice choice = new GOpenAITranscriptModelChoice();
		choice.setCode(WhisperModel.WHISPER_1.value);
		choice.setDescription("Whisper 1");
		return OperationStatus.of(List.of(choice));
	}

	/**
	 * Creates a base configuration for an OpenAI transcript model
	 *
	 * @param presetModel Optional preset model identifier (not used in current implementation)
	 * @return A new OpenAI transcript model configuration with default settings
	 */
	@Override
	public GOpenAITranscriptModelConfig createBaseConfiguration(String presetModel) {
		GOpenAITranscriptModelConfig config = new GOpenAITranscriptModelConfig();
		config.setDescription("OpenAI transcript provider");
		config.setChoosedModel(getModelChoices(config).getResult().get(0));
		return config;
	}

	/**
	 * Creates a configurable transcript model based on the provided configuration
	 *
	 * @param config The OpenAI transcript model configuration
	 * @return A configured transcript model ready to use
	 * @throws LLMConfigException If there is an issue with the configuration
	 */
	@Override
	public IGConfigurableTranscriptModel<GOpenAITranscriptModelConfig> create(GOpenAITranscriptModelConfig config)
			throws LLMConfigException {
		OpenAIConfigurableTranscriptModel tModel = new OpenAIConfigurableTranscriptModel();
		tModel.initialize(config, type);
		return tModel;
	}
}