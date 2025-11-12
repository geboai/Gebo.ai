/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */
 
 
 

package ai.gebo.llms.openai.services;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import org.springframework.ai.openai.OpenAiAudioSpeechModel;
import org.springframework.ai.openai.OpenAiAudioSpeechOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.ai.openai.api.OpenAiAudioApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;

import ai.gebo.crypting.services.GeboCryptSecretException;
import ai.gebo.llms.abstraction.layer.model.GTextToSpeechModelType;
import ai.gebo.llms.abstraction.layer.services.GAbstractConfigurableTextToSpeechModel;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableTextToSpeechModel;
import ai.gebo.llms.abstraction.layer.services.IGModelApiAccessReadUtils;
import ai.gebo.llms.abstraction.layer.services.IGModelApiAccessReadUtils.ApiKeyInfo;
import ai.gebo.llms.abstraction.layer.services.IGTextToSpeechModelConfigurationSupportService;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.openai.model.GOpenAITextToSpeechModelChoice;
import ai.gebo.llms.openai.model.GOpenAITextToSpeechModelConfig;
import ai.gebo.model.OperationStatus;

/**
 * Service to configure and create OpenAI text-to-speech models.
 * AI generated comments
 */
@Service
public class OpenAITextToSpeechModelConfigurationSupportService implements
		IGTextToSpeechModelConfigurationSupportService<GOpenAITextToSpeechModelChoice, GOpenAITextToSpeechModelConfig> {
	@Autowired
	IGModelApiAccessReadUtils apiKeyReader;
	
	/**
	 * Static definition of the model type with code and description
	 */
	static final GTextToSpeechModelType type = new GTextToSpeechModelType();
	static {
		type.setCode("openai-tts");
		type.setDescription("OpenAI TTS Service");
	}

	/**
	 * Implementation of OpenAI's text-to-speech model that extends the abstract configurable model.
	 */
	class OpenAIConfigurableTextToSpeechModel
			extends GAbstractConfigurableTextToSpeechModel<GOpenAITextToSpeechModelConfig, OpenAiAudioSpeechModel> {

		/**
		 * Converts text to speech and returns the audio as an InputStream.
		 * 
		 * @param text The text to convert to speech
		 * @return InputStream containing the audio data
		 */
		@Override
		public InputStream call(String text) {
			byte buffer[] = model.call(text);

			return new ByteArrayInputStream(buffer);
		}

		/**
		 * Configures and creates an OpenAI audio speech model using the provided configuration.
		 * 
		 * @param config The configuration for the model
		 * @param type The type of text-to-speech model
		 * @return Configured OpenAiAudioSpeechModel
		 * @throws LLMConfigException If the configuration fails
		 */
		@Override
		protected OpenAiAudioSpeechModel configureModel(GOpenAITextToSpeechModelConfig config,
				GTextToSpeechModelType type) throws LLMConfigException {
			ApiKeyInfo apiKey;

			apiKey = apiKeyReader.getApiKeyInfo(config);
			
			OpenAiAudioApi audioApi = OpenAiAudioApi.builder().apiKey(apiKey.getApiKey()).build();
			OpenAiAudioSpeechOptions speechOptions = OpenAiAudioSpeechOptions.builder().model("tts-1")
					.voice(OpenAiAudioApi.SpeechRequest.Voice.ALLOY)
					.responseFormat(OpenAiAudioApi.SpeechRequest.AudioResponseFormat.MP3).speed(1.0f).build();
			OpenAiAudioSpeechModel model = new OpenAiAudioSpeechModel(audioApi, speechOptions,
					RetryTemplate.defaultInstance());

			return model;
		}
	}

	/**
	 * Default constructor for the service.
	 */
	public OpenAITextToSpeechModelConfigurationSupportService() {

	}

	/**
	 * Returns the type of text-to-speech model this service supports.
	 * 
	 * @return The GTextToSpeechModelType for OpenAI
	 */
	@Override
	public GTextToSpeechModelType getType() {

		return type;
	}

	/**
	 * Returns available OpenAI text-to-speech model choices.
	 * 
	 * @param config The configuration
	 * @return OperationStatus containing a list of available model choices
	 */
	@Override
	public OperationStatus<List<GOpenAITextToSpeechModelChoice>> getModelChoices(
			GOpenAITextToSpeechModelConfig config) {
		GOpenAITextToSpeechModelChoice tts1Model = new GOpenAITextToSpeechModelChoice();
		tts1Model.setCode("tts-1");
		tts1Model.setDescription("OpenAI text to speech tts1 model");

		return OperationStatus.of(List.of(tts1Model));
	}

	/**
	 * Creates a base configuration with default model selection.
	 * 
	 * @param presetModel The preset model to use (not currently used)
	 * @return A new configuration with default values
	 */
	@Override
	public GOpenAITextToSpeechModelConfig createBaseConfiguration(String presetModel) {
		GOpenAITextToSpeechModelConfig config = new GOpenAITextToSpeechModelConfig();
		config.setChoosedModel(getModelChoices(config).getResult().get(0));
		return config;
	}

	/**
	 * Creates a configured text-to-speech model using the provided configuration.
	 * 
	 * @param config The configuration to use
	 * @return Configured text-to-speech model
	 * @throws LLMConfigException If configuration fails
	 */
	@Override
	public IGConfigurableTextToSpeechModel<GOpenAITextToSpeechModelConfig> create(GOpenAITextToSpeechModelConfig config)
			throws LLMConfigException {
		OpenAIConfigurableTextToSpeechModel model = new OpenAIConfigurableTextToSpeechModel();
		model.initialize(config, type);
		return model;
	}

	@Override
	public OperationStatus<GOpenAITextToSpeechModelConfig> insertAndConfigure(GOpenAITextToSpeechModelConfig config) {
		// TODO Auto-generated method stub
		return null;
	}

}