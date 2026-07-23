/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.llms.openai_compat.services;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import org.springframework.ai.model.NoopApiKey;
import org.springframework.ai.openai.OpenAiAudioSpeechModel;
import org.springframework.ai.openai.OpenAiAudioSpeechOptions;

import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.llms.abstraction.layer.model.GTextToSpeechModelType;
import ai.gebo.llms.abstraction.layer.services.GAbstractConfigurableTextToSpeechModel;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableTextToSpeechModel;
import ai.gebo.llms.abstraction.layer.services.IGLlmsServiceClientsProviderFactory;
import ai.gebo.llms.openai.api.utils.IGOpenAIApiUtil;
import ai.gebo.llms.openai.http.OpenAiClientCustomizer;
import ai.gebo.llms.abstraction.layer.services.IGTextToSpeechModelConfigurationSupportService;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.abstraction.layer.services.ModelRuntimeConfigureHandler;
import ai.gebo.llms.openai_compat.model.GenericOpenAIAPITextToSpeechModelChoice;
import ai.gebo.llms.openai_compat.model.GenericOpenAIAPITextToSpeechModelConfig;
import ai.gebo.llms.openai_compat.modeltypes.GenericOpenAITextToSpeechModelType;
import ai.gebo.model.OperationStatus;
import ai.gebo.crypting.services.GeboCryptSecretException;
import ai.gebo.secrets.model.AbstractGeboSecretContent;
import ai.gebo.secrets.model.GeboSecretType;
import ai.gebo.secrets.model.GeboTokenContent;
import ai.gebo.secrets.services.IGeboSecretsAccessService;
import lombok.AllArgsConstructor;

/**
 * Service to configure and create OpenAI text-to-speech models. AI generated
 * comments
 */
@AllArgsConstructor
public class GenericOpenAIAPITextToSpeechModelConfigurationSupportService implements
		IGTextToSpeechModelConfigurationSupportService<GenericOpenAIAPITextToSpeechModelChoice, GenericOpenAIAPITextToSpeechModelConfig> {

	/**
	 * Static definition of the model type with code and description
	 */
	final GenericOpenAITextToSpeechModelType type;
	/**
	 * Service for accessing secrets like API keys
	 */
	final IGeboSecretsAccessService secretService;

	/**
	 * Utility for working with OpenAI API
	 */
	final IGOpenAIApiUtil openaiApiUtil;



	/**
	 * Service for retrieving model lists from providers
	 */
	final ModelsListProviderProxyService modelsListProxyService;

	/**
	 * Factory for creating LLM service clients (timeout/retry config from application.yml)
	 */
	final IGLlmsServiceClientsProviderFactory serviceClientsProviderFactory;

	final ModelRuntimeConfigureHandler configureHandler;

	/**
	 * Implementation of OpenAI's text-to-speech model that extends the abstract
	 * configurable model.
	 */
	class GenericOpenAIConfigurableTextToSpeechModel extends
			GAbstractConfigurableTextToSpeechModel<GenericOpenAIAPITextToSpeechModelConfig, OpenAiAudioSpeechModel> {

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
		 * Configures and creates an OpenAI audio speech model using the provided
		 * configuration.
		 * 
		 * @param config The configuration for the model
		 * @param type   The type of text-to-speech model
		 * @return Configured OpenAiAudioSpeechModel
		 * @throws LLMConfigException If the configuration fails
		 */
		@Override
		protected OpenAiAudioSpeechModel configureModel(GenericOpenAIAPITextToSpeechModelConfig config,
				GTextToSpeechModelType type) throws LLMConfigException {
			String apiKey = null;
			if (config.getApiSecretCode() != null && config.getApiSecretCode().trim().length() > 0) {
				try {
					AbstractGeboSecretContent secret = secretService.getSecretContentById(config.getApiSecretCode());
					if (secret.type() == GeboSecretType.TOKEN) {
						apiKey = ((GeboTokenContent) secret).getToken();
					} else {
						throw new LLMConfigException(
								type.getDescription() + " api can work only with an api key of type TOKEN");
					}
				} catch (GeboCryptSecretException e) {
					throw new LLMConfigException(type.getDescription() + " api  key configuration gone wrong ", e);
				}
			}
			String baseUrl = GenericOpenAIAPITextToSpeechModelConfigurationSupportService.this.type.getBaseUrl();
			String modelName = config.getChoosedModel() != null && config.getChoosedModel().getCode() != null
					&& !config.getChoosedModel().getCode().isBlank() ? config.getChoosedModel().getCode() : "tts-1";
			OpenAiAudioSpeechOptions.Builder optionsBuilder = OpenAiAudioSpeechOptions.builder()
					.model(modelName)
					.voice(OpenAiAudioSpeechOptions.Voice.ALLOY)
					.responseFormat(OpenAiAudioSpeechOptions.AudioResponseFormat.MP3)
					.speed(1.0);
			if (apiKey != null) {
				optionsBuilder.apiKey(apiKey);
			} else {
				// A provider that needs no credentials (a local one) keeps the noop key.
				optionsBuilder.apiKey(new NoopApiKey());
			}
			if (baseUrl != null) {
				optionsBuilder.baseUrl(baseUrl);
			}
			OpenAiAudioSpeechOptions speechOptions = optionsBuilder.build();
			OpenAiAudioSpeechModel model = OpenAiAudioSpeechModel.builder()
					.options(speechOptions)
					.httpClientBuilderCustomizer(
							OpenAiClientCustomizer.from(serviceClientsProviderFactory.get(type.getCode())))
					.build();

			return model;
		}
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
	public OperationStatus<List<GenericOpenAIAPITextToSpeechModelChoice>> getModelChoices(
			GenericOpenAIAPITextToSpeechModelConfig config) {
		// When the provider declares a models-list strategy (e.g. openrouter/regolo) use
		// it so text-to-speech models can be discovered and validated; otherwise fall back
		// to the OpenAI default suggestion.
		if (type.getModelsListProvider() != null && type.getModelsListProvider().trim().length() > 0) {
			return modelsListProxyService.geModels(type.getModelsListProvider(), config,
					GenericOpenAIAPITextToSpeechModelChoice.class, type);
		}
		GenericOpenAIAPITextToSpeechModelChoice tts1Model = new GenericOpenAIAPITextToSpeechModelChoice();
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
	public GenericOpenAIAPITextToSpeechModelConfig createBaseConfiguration(String presetModel) {
		GenericOpenAIAPITextToSpeechModelConfig config = new GenericOpenAIAPITextToSpeechModelConfig();
		config.setDescription(type.getProviderId() + " text to speech provider");
		GenericOpenAIAPITextToSpeechModelChoice choice = new GenericOpenAIAPITextToSpeechModelChoice();
		choice.setCode(presetModel != null && !presetModel.isBlank() ? presetModel : "tts-1");
		choice.setDescription(choice.getCode());
		config.setChoosedModel(choice);
		config.setModelTypeCode(getType().getCode());
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
	public IGConfigurableTextToSpeechModel<GenericOpenAIAPITextToSpeechModelConfig> create(
			GenericOpenAIAPITextToSpeechModelConfig config) throws LLMConfigException {
		GenericOpenAIConfigurableTextToSpeechModel model = new GenericOpenAIConfigurableTextToSpeechModel();
		model.initialize(config, type);
		return model;
	}
	@Override
	public String getId() {
		return type.getCode();
	}
	@Override
	public OperationStatus<GenericOpenAIAPITextToSpeechModelConfig> insertAndConfigure(
			GenericOpenAIAPITextToSpeechModelConfig config) throws GeboPersistenceException, LLMConfigException {
		return configureHandler.insertAndConfigure(config, type);
	}

}