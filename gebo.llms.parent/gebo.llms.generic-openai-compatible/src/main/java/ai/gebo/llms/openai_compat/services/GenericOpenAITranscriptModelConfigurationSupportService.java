/**
 * This Source Code is subject to the terms of the 
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at 
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/  
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai 
 */

package ai.gebo.llms.openai_compat.services;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.springframework.ai.openai.OpenAiAudioTranscriptionModel;
import org.springframework.ai.openai.OpenAiAudioTranscriptionOptions;
import org.springframework.ai.openai.api.OpenAiAudioApi;
import org.springframework.ai.openai.api.OpenAiAudioApi.TranscriptResponseFormat;
import org.springframework.ai.openai.api.OpenAiAudioApi.WhisperModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;

import ai.gebo.llms.abstraction.layer.model.GTranscriptModelType;
import ai.gebo.llms.abstraction.layer.services.GAbstractConfigurableTranscriptModel;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableTranscriptModel;
import ai.gebo.llms.abstraction.layer.services.IGModelApiAccessReadUtils;
import ai.gebo.llms.abstraction.layer.services.IGModelApiAccessReadUtils.ApiKeyInfo;
import ai.gebo.llms.openai.api.utils.IGOpenAIApiUtil;
import ai.gebo.llms.abstraction.layer.services.IGTranscriptModelConfigurationSupportService;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.openai_compat.model.GenericOpenAITranscriptModelChoice;
import ai.gebo.llms.openai_compat.model.GenericOpenAITranscriptModelConfig;
import ai.gebo.llms.openai_compat.modeltypes.GenericOpenAITranscriptModelType;
import ai.gebo.model.OperationStatus;
import ai.gebo.secrets.services.IGeboSecretsAccessService;
import lombok.AllArgsConstructor;

/**
 * AI generated comments
 * 
 * Service class responsible for configuring and supporting OpenAI transcript
 * models. This service provides functionality to create, configure, and manage
 * OpenAI transcript models that can convert audio to text using OpenAI's
 * Whisper model.
 */
@AllArgsConstructor
public class GenericOpenAITranscriptModelConfigurationSupportService implements
		IGTranscriptModelConfigurationSupportService<GenericOpenAITranscriptModelChoice, GenericOpenAITranscriptModelConfig> {

	/**
	 * Static transcript model type for OpenAI transcript service
	 */
	final GenericOpenAITranscriptModelType type;
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
	 * Implementation of a configurable transcript model for OpenAI services.
	 * Extends the abstract transcript model and provides OpenAI-specific
	 * functionality.
	 */
	public class GenericOpenAIConfigurableTranscriptModel extends
			GAbstractConfigurableTranscriptModel<GenericOpenAITranscriptModelConfig, OpenAiAudioTranscriptionModel> {

		/**
		 * Processes the audio from an input stream and returns the transcribed text
		 *
		 * @param audioResource The input stream containing audio data to transcribe
		 * @return The transcribed text from the audio
		 * @throws LLMConfigException If there is an issue with the configuration
		 * @throws IOException
		 */
		@Override
		public String call(InputStream audioResource) throws LLMConfigException, IOException {
			// receiving .webm format, saving and sending deleting after execution
			Path created = null;
			try {
				created = Files.createTempFile("usr-audio", ".webm");
				try (OutputStream os = Files.newOutputStream(created)) {
					IOUtils.copy(audioResource, os);
					Resource resource = new FileSystemResource(created);
					return model.call(resource);
				}
			} catch (IOException exc) {
				throw new IOException("Handled exception in call", exc);
			} finally {
				try {
					audioResource.close();
				} catch (Throwable t) {
				}
				try {
					if (created != null) {
						Files.deleteIfExists(created);
					}
				} catch (Throwable t) {
				}
			}
		}

		/**
		 * Configures the OpenAI audio transcription model based on the provided
		 * configuration
		 *
		 * @param config The OpenAI transcript model configuration
		 * @param type   The transcript model type
		 * @return A configured OpenAI audio transcription model
		 * @throws LLMConfigException If there is an issue with configuration or API
		 *                            access
		 */
		@Override
		protected OpenAiAudioTranscriptionModel configureModel(GenericOpenAITranscriptModelConfig config,
				GTranscriptModelType type) throws LLMConfigException {

			String apiKey = null;
			OpenAiAudioApi audioApi = OpenAiAudioApi.builder().apiKey(apiKey).build();
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
	public OperationStatus<List<GenericOpenAITranscriptModelChoice>> getModelChoices(
			GenericOpenAITranscriptModelConfig config) {
		GenericOpenAITranscriptModelChoice choice = new GenericOpenAITranscriptModelChoice();
		choice.setCode(WhisperModel.WHISPER_1.value);
		choice.setDescription("Whisper 1");
		return OperationStatus.of(List.of(choice));
	}

	/**
	 * Creates a base configuration for an OpenAI transcript model
	 *
	 * @param presetModel Optional preset model identifier (not used in current
	 *                    implementation)
	 * @return A new OpenAI transcript model configuration with default settings
	 */
	@Override
	public GenericOpenAITranscriptModelConfig createBaseConfiguration(String presetModel) {
		GenericOpenAITranscriptModelConfig config = new GenericOpenAITranscriptModelConfig();
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
	public IGConfigurableTranscriptModel<GenericOpenAITranscriptModelConfig> create(
			GenericOpenAITranscriptModelConfig config) throws LLMConfigException {
		GenericOpenAIConfigurableTranscriptModel tModel = new GenericOpenAIConfigurableTranscriptModel();
		tModel.initialize(config, type);
		return tModel;
	}

	@Override
	public OperationStatus<GenericOpenAITranscriptModelConfig> insertAndConfigure(
			GenericOpenAITranscriptModelConfig config) {
		// TODO Auto-generated method stub
		return null;
	}
}