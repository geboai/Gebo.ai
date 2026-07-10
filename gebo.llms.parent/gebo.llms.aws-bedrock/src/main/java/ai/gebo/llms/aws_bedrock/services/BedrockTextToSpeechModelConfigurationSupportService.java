/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.aws_bedrock.services;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.llms.abstraction.layer.model.GTextToSpeechModelType;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableTextToSpeechModel;
import ai.gebo.llms.abstraction.layer.services.IGTextToSpeechModelConfigurationSupportService;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.abstraction.layer.services.ModelRuntimeConfigureHandler;
import ai.gebo.llms.aws_bedrock.model.GBedrockTextToSpeechModelChoice;
import ai.gebo.llms.aws_bedrock.model.GBedrockTextToSpeechModelConfig;
import ai.gebo.model.OperationStatus;
import lombok.AllArgsConstructor;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.polly.PollyClient;
import software.amazon.awssdk.services.polly.model.DescribeVoicesResponse;
import software.amazon.awssdk.services.polly.model.Engine;
import software.amazon.awssdk.services.polly.model.OutputFormat;
import software.amazon.awssdk.services.polly.model.SynthesizeSpeechRequest;
import software.amazon.awssdk.services.polly.model.SynthesizeSpeechResponse;
import software.amazon.awssdk.services.polly.model.Voice;
import software.amazon.awssdk.services.polly.model.VoiceId;

/**
 * Text-to-speech configuration support backed by Amazon Polly. Bedrock itself
 * does not host speech synthesis; on AWS that capability is Amazon Polly, exposed
 * here under the AWS provider module for coherence with the other categories.
 */
@ConditionalOnProperty(prefix = "ai.gebo.llms.config", name = "awsBedrockEnabled", havingValue = "true")
@Service
@AllArgsConstructor
public class BedrockTextToSpeechModelConfigurationSupportService implements
		IGTextToSpeechModelConfigurationSupportService<GBedrockTextToSpeechModelChoice, GBedrockTextToSpeechModelConfig> {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(BedrockTextToSpeechModelConfigurationSupportService.class);

	static final GTextToSpeechModelType type = new GTextToSpeechModelType();
	static {
		type.setCode("aws-polly-tts");
		type.setDescription("Amazon Polly text to speech service");
		type.setModelConfigurationClass(GBedrockTextToSpeechModelConfig.class.getName());
	}

	final BedrockCredentialsResolver credentialsResolver;
	final ModelRuntimeConfigureHandler configureHandler;

	class BedrockPollyConfigurableTextToSpeechModel
			implements IGConfigurableTextToSpeechModel<GBedrockTextToSpeechModelConfig> {

		private GBedrockTextToSpeechModelConfig config;
		private GTextToSpeechModelType modelType;
		private PollyClient pollyClient;
		private VoiceId voiceId;
		private Engine engine;
		private OutputFormat outputFormat = OutputFormat.MP3;

		@Override
		public String getCode() {
			return config != null ? config.getCode() : null;
		}

		@Override
		public String getDescription() {
			return config != null ? config.getDescription() : null;
		}

		@Override
		public GTextToSpeechModelType getType() {
			return modelType;
		}

		@Override
		public void initialize(GBedrockTextToSpeechModelConfig config, GTextToSpeechModelType type)
				throws LLMConfigException {
			this.modelType = type;
			reconfigure(config);
		}

		@Override
		public void reconfigure(GBedrockTextToSpeechModelConfig config) throws LLMConfigException {
			this.config = config;
			AwsCredentialsProvider credentials = credentialsResolver.resolveCredentials(config.getApiSecretCode());
			Region region = credentialsResolver.resolveRegion(config.getApiSecretCode());
			this.pollyClient = PollyClient.builder().region(region).credentialsProvider(credentials).build();
			String voice = config.getVoice();
			if ((voice == null || voice.trim().length() == 0) && config.getChoosedModel() != null) {
				voice = config.getChoosedModel().getCode();
			}
			this.voiceId = voice != null ? VoiceId.fromValue(voice) : VoiceId.JOANNA;
			this.engine = config.getEngine() != null ? Engine.fromValue(config.getEngine()) : Engine.NEURAL;
		}

		@Override
		public GBedrockTextToSpeechModelConfig getConfig() {
			return config;
		}

		@Override
		public void delete() throws LLMConfigException {
			if (pollyClient != null) {
				try {
					pollyClient.close();
				} catch (Throwable t) {
					// ignore
				}
				pollyClient = null;
			}
		}

		@Override
		public InputStream call(String text) {
			SynthesizeSpeechRequest request = SynthesizeSpeechRequest.builder()
					.text(text)
					.voiceId(voiceId)
					.engine(engine)
					.outputFormat(outputFormat)
					.build();
			ResponseInputStream<SynthesizeSpeechResponse> audio = pollyClient.synthesizeSpeech(request);
			return audio;
		}
	}

	@Override
	public GTextToSpeechModelType getType() {
		return type;
	}

	@Override
	public IGConfigurableTextToSpeechModel<GBedrockTextToSpeechModelConfig> create(
			GBedrockTextToSpeechModelConfig config) throws LLMConfigException {
		BedrockPollyConfigurableTextToSpeechModel model = new BedrockPollyConfigurableTextToSpeechModel();
		model.initialize(config, type);
		return model;
	}

	@Override
	public OperationStatus<List<GBedrockTextToSpeechModelChoice>> getModelChoices(
			GBedrockTextToSpeechModelConfig config) {
		try {
			AwsCredentialsProvider credentials = credentialsResolver.resolveCredentials(config.getApiSecretCode());
			Region region = credentialsResolver.resolveRegion(config.getApiSecretCode());
			try (PollyClient polly = PollyClient.builder().region(region).credentialsProvider(credentials).build()) {
				DescribeVoicesResponse response = polly.describeVoices();
				List<GBedrockTextToSpeechModelChoice> choices = new ArrayList<>();
				for (Voice voice : response.voices()) {
					GBedrockTextToSpeechModelChoice choice = new GBedrockTextToSpeechModelChoice();
					choice.setCode(voice.idAsString());
					choice.setDescription(voice.name() + " - " + voice.languageName());
					choices.add(choice);
				}
				return OperationStatus.of(choices);
			}
		} catch (Throwable t) {
			LOGGER.error("Unable to list Amazon Polly voices", t);
			return OperationStatus.ofError("Unable to list Amazon Polly voices", t.getMessage());
		}
	}

	@Override
	public GBedrockTextToSpeechModelConfig createBaseConfiguration(String presetModel) {
		GBedrockTextToSpeechModelConfig clean = new GBedrockTextToSpeechModelConfig();
		clean.setChoosedModel(new GBedrockTextToSpeechModelChoice());
		clean.getChoosedModel().setCode(presetModel);
		clean.getChoosedModel().setDescription("Amazon Polly voice " + presetModel);
		clean.setVoice(presetModel);
		clean.setDescription("Amazon Polly text to speech " + presetModel);
		clean.setModelTypeCode(getType().getCode());
		return clean;
	}

	@Override
	public OperationStatus<GBedrockTextToSpeechModelConfig> insertAndConfigure(GBedrockTextToSpeechModelConfig config)
			throws GeboPersistenceException, LLMConfigException {
		return configureHandler.insertAndConfigure(config, type);
	}
}
