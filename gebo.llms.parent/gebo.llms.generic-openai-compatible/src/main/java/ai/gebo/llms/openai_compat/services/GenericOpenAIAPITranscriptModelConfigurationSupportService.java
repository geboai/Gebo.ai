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

import com.openai.models.audio.AudioResponseFormat;
import org.apache.commons.io.IOUtils;
import org.springframework.ai.audio.transcription.AudioTranscriptionPrompt;
import org.springframework.ai.model.NoopApiKey;
import org.springframework.ai.openai.OpenAiAudioTranscriptionModel;
import org.springframework.ai.openai.OpenAiAudioTranscriptionOptions;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.llms.abstraction.layer.model.GTranscriptModelType;
import ai.gebo.llms.abstraction.layer.services.GAbstractConfigurableTranscriptModel;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableTranscriptModel;
import ai.gebo.llms.abstraction.layer.services.IGLlmsServiceClientsProviderFactory;
import ai.gebo.llms.openai.api.utils.IGOpenAIApiUtil;
import ai.gebo.llms.openai.http.OpenAiClientCustomizer;
import ai.gebo.llms.abstraction.layer.services.IGTranscriptModelConfigurationSupportService;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.abstraction.layer.services.ModelRuntimeConfigureHandler;
import ai.gebo.llms.openai_compat.model.GenericOpenAIAPITranscriptModelChoice;
import ai.gebo.llms.openai_compat.model.GenericOpenAIAPITranscriptModelConfig;
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
public class GenericOpenAIAPITranscriptModelConfigurationSupportService implements
		IGTranscriptModelConfigurationSupportService<GenericOpenAIAPITranscriptModelChoice, GenericOpenAIAPITranscriptModelConfig> {

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
	 * Factory for creating LLM service clients (timeout/retry config from application.yml)
	 */
	final IGLlmsServiceClientsProviderFactory serviceClientsProviderFactory;

	final ModelRuntimeConfigureHandler configureHandler;

	/**
	 * Implementation of a configurable transcript model for OpenAI services.
	 * Extends the abstract transcript model and provides OpenAI-specific
	 * functionality.
	 */
	public class GenericOpenAIConfigurableTranscriptModel extends
			GAbstractConfigurableTranscriptModel<GenericOpenAIAPITranscriptModelConfig, OpenAiAudioTranscriptionModel> {

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
					return model.call(new AudioTranscriptionPrompt(resource)).getResult().getOutput();
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
		protected OpenAiAudioTranscriptionModel configureModel(GenericOpenAIAPITranscriptModelConfig config,
				GTranscriptModelType type) throws LLMConfigException {

			String baseUrl = GenericOpenAIAPITranscriptModelConfigurationSupportService.this.type.getBaseUrl();
			org.springframework.ai.openai.OpenAiAudioTranscriptionOptions.Builder builder = OpenAiAudioTranscriptionOptions
					.builder();

			String modelName = config.getChoosedModel() != null && config.getChoosedModel().getCode() != null
					&& !config.getChoosedModel().getCode().isBlank() ? config.getChoosedModel().getCode() : "whisper-1";
			builder.apiKey(new NoopApiKey()).responseFormat(AudioResponseFormat.TEXT).temperature(0f).model(modelName);
			if (baseUrl != null) {
				builder.baseUrl(baseUrl);
			}
			OpenAiAudioTranscriptionOptions options = builder.build();
			OpenAiAudioTranscriptionModel model = OpenAiAudioTranscriptionModel.builder()
					.options(options)
					.httpClientBuilderCustomizer(
							OpenAiClientCustomizer.from(serviceClientsProviderFactory.get(type.getCode())))
					.build();

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
	public OperationStatus<List<GenericOpenAIAPITranscriptModelChoice>> getModelChoices(
			GenericOpenAIAPITranscriptModelConfig config) {
		// When the provider declares a models-list strategy (e.g. openrouter/regolo) use
		// it so transcript models can be discovered and validated; otherwise fall back to
		// the OpenAI default suggestion.
		if (type.getModelsListProvider() != null && type.getModelsListProvider().trim().length() > 0) {
			return modelsListProxyService.geModels(type.getModelsListProvider(), config,
					GenericOpenAIAPITranscriptModelChoice.class, type);
		}
		GenericOpenAIAPITranscriptModelChoice choice = new GenericOpenAIAPITranscriptModelChoice();
		choice.setCode("whisper-1");
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
	public GenericOpenAIAPITranscriptModelConfig createBaseConfiguration(String presetModel) {
		GenericOpenAIAPITranscriptModelConfig config = new GenericOpenAIAPITranscriptModelConfig();
		config.setDescription("OpenAI transcript provider");
		GenericOpenAIAPITranscriptModelChoice choice = new GenericOpenAIAPITranscriptModelChoice();
		choice.setCode(presetModel != null && !presetModel.isBlank() ? presetModel : "whisper-1");
		choice.setDescription(choice.getCode());
		config.setChoosedModel(choice);
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
	public IGConfigurableTranscriptModel<GenericOpenAIAPITranscriptModelConfig> create(
			GenericOpenAIAPITranscriptModelConfig config) throws LLMConfigException {
		GenericOpenAIConfigurableTranscriptModel tModel = new GenericOpenAIConfigurableTranscriptModel();
		tModel.initialize(config, type);
		return tModel;
	}
	@Override
	public String getId() {
		return type.getCode();
	}
	@Override
	public OperationStatus<GenericOpenAIAPITranscriptModelConfig> insertAndConfigure(
			GenericOpenAIAPITranscriptModelConfig config) throws GeboPersistenceException, LLMConfigException {
		return configureHandler.insertAndConfigure(config, type);
	}
}