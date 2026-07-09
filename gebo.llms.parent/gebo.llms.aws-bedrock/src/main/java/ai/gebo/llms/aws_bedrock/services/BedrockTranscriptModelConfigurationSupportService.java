/**
 * This Source Code is subject to the terms of the
 * Gebo.ai community version Mozilla Public License Version 2.0 (MPL-2.0) — With Data Protection Clauses
 * If a copy of the LICENCE was not distributed with this file, You can obtain one at
 * https://gebo.ai/gebo-ai-community-version-mozilla-public-license-version-2-0-mpl-2-0-with-data-protection-clauses/
 * and https://mozilla.org/MPL/2.0/.
 * Copyright (c) 2025+ Gebo.ai
 */

package ai.gebo.llms.aws_bedrock.services;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import ai.gebo.architecture.persistence.GeboPersistenceException;
import ai.gebo.llms.abstraction.layer.model.GTranscriptModelType;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableTranscriptModel;
import ai.gebo.llms.abstraction.layer.services.IGTranscriptModelConfigurationSupportService;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.llms.abstraction.layer.services.ModelRuntimeConfigureHandler;
import ai.gebo.llms.aws_bedrock.model.GBedrockTranscriptModelChoice;
import ai.gebo.llms.aws_bedrock.model.GBedrockTranscriptModelConfig;
import ai.gebo.model.OperationStatus;
import lombok.AllArgsConstructor;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.transcribestreaming.TranscribeStreamingAsyncClient;
import software.amazon.awssdk.services.transcribestreaming.model.AudioEvent;
import software.amazon.awssdk.services.transcribestreaming.model.AudioStream;
import software.amazon.awssdk.services.transcribestreaming.model.MediaEncoding;
import software.amazon.awssdk.services.transcribestreaming.model.StartStreamTranscriptionRequest;
import software.amazon.awssdk.services.transcribestreaming.model.StartStreamTranscriptionResponseHandler;
import software.amazon.awssdk.services.transcribestreaming.model.TranscriptEvent;

/**
 * Speech-to-text configuration support backed by Amazon Transcribe streaming.
 * Bedrock itself does not host transcription; on AWS that capability is Amazon
 * Transcribe, exposed here under the AWS provider module for coherence with the
 * other categories. The audio input stream is streamed to Transcribe and the
 * final (non-partial) results are concatenated into the returned transcript.
 */
@ConditionalOnProperty(prefix = "ai.gebo.llms.config", name = "awsBedrockEnabled", havingValue = "true")
@Service
@AllArgsConstructor
public class BedrockTranscriptModelConfigurationSupportService implements
		IGTranscriptModelConfigurationSupportService<GBedrockTranscriptModelChoice, GBedrockTranscriptModelConfig> {

	static final GTranscriptModelType type = new GTranscriptModelType();
	static {
		type.setCode("aws-transcribe");
		type.setDescription("Amazon Transcribe speech to text service");
		type.setModelConfigurationClass(GBedrockTranscriptModelConfig.class.getName());
	}

	static final int DEFAULT_SAMPLE_RATE = 16000;
	static final int CHUNK_SIZE = 4096;
	static final long TRANSCRIBE_TIMEOUT_SECONDS = 300;

	final BedrockCredentialsResolver credentialsResolver;
	final ModelRuntimeConfigureHandler configureHandler;

	class BedrockTranscribeConfigurableTranscriptModel
			implements IGConfigurableTranscriptModel<GBedrockTranscriptModelConfig> {

		private GBedrockTranscriptModelConfig config;
		private GTranscriptModelType modelType;
		private TranscribeStreamingAsyncClient client;
		private String languageCode;
		private MediaEncoding mediaEncoding;
		private int sampleRate;

		@Override
		public String getCode() {
			return config != null ? config.getCode() : null;
		}

		@Override
		public String getDescription() {
			return config != null ? config.getDescription() : null;
		}

		@Override
		public GTranscriptModelType getType() {
			return modelType;
		}

		@Override
		public void initialize(GBedrockTranscriptModelConfig config, GTranscriptModelType type)
				throws LLMConfigException {
			this.modelType = type;
			reconfigure(config);
		}

		@Override
		public void reconfigure(GBedrockTranscriptModelConfig config) throws LLMConfigException {
			this.config = config;
			AwsCredentialsProvider credentials = credentialsResolver.resolveCredentials(config.getApiSecretCode());
			Region region = credentialsResolver.resolveRegion(config.getRegion());
			this.client = TranscribeStreamingAsyncClient.builder().region(region).credentialsProvider(credentials)
					.build();
			this.languageCode = config.getLanguageCode() != null ? config.getLanguageCode() : "en-US";
			this.mediaEncoding = config.getMediaEncoding() != null ? MediaEncoding.fromValue(config.getMediaEncoding())
					: MediaEncoding.PCM;
			this.sampleRate = config.getSampleRateHertz() != null ? config.getSampleRateHertz() : DEFAULT_SAMPLE_RATE;
		}

		@Override
		public GBedrockTranscriptModelConfig getConfig() {
			return config;
		}

		@Override
		public void delete() throws LLMConfigException {
			if (client != null) {
				try {
					client.close();
				} catch (Throwable t) {
					// ignore
				}
				client = null;
			}
		}

		@Override
		public String call(InputStream audioResource) throws LLMConfigException, IOException {
			StartStreamTranscriptionRequest request = StartStreamTranscriptionRequest.builder()
					.languageCode(languageCode)
					.mediaEncoding(mediaEncoding)
					.mediaSampleRateHertz(sampleRate)
					.build();

			StringBuilder transcript = new StringBuilder();
			StartStreamTranscriptionResponseHandler responseHandler = StartStreamTranscriptionResponseHandler.builder()
					.subscriber(event -> {
						if (event instanceof TranscriptEvent transcriptEvent) {
							transcriptEvent.transcript().results().forEach(result -> {
								if (!Boolean.TRUE.equals(result.isPartial()) && !result.alternatives().isEmpty()) {
									String text = result.alternatives().get(0).transcript();
									if (text != null && !text.isBlank()) {
										transcript.append(text).append(' ');
									}
								}
							});
						}
					})
					.build();

			try {
				CompletableFuture<Void> future = client.startStreamTranscription(request,
						new AudioStreamPublisher(audioResource), responseHandler);
				future.get(TRANSCRIBE_TIMEOUT_SECONDS, TimeUnit.SECONDS);
			} catch (Exception e) {
				throw new IOException("Amazon Transcribe streaming failed", e);
			} finally {
				try {
					audioResource.close();
				} catch (Throwable t) {
					// ignore
				}
			}
			return transcript.toString().trim();
		}
	}

	/**
	 * Minimal reactive-streams publisher that emits the audio input stream as a
	 * sequence of {@link AudioEvent} chunks, honouring downstream demand.
	 */
	static class AudioStreamPublisher implements Publisher<AudioStream> {
		private final InputStream inputStream;

		AudioStreamPublisher(InputStream inputStream) {
			this.inputStream = inputStream;
		}

		@Override
		public void subscribe(Subscriber<? super AudioStream> subscriber) {
			subscriber.onSubscribe(new Subscription() {
				private final AtomicBoolean done = new AtomicBoolean(false);

				@Override
				public void request(long n) {
					if (done.get()) {
						return;
					}
					try {
						for (long i = 0; i < n; i++) {
							byte[] buffer = new byte[CHUNK_SIZE];
							int read = inputStream.read(buffer);
							if (read < 0) {
								if (done.compareAndSet(false, true)) {
									subscriber.onComplete();
								}
								return;
							}
							byte[] chunk = read == buffer.length ? buffer : java.util.Arrays.copyOf(buffer, read);
							subscriber.onNext(AudioEvent.builder().audioChunk(SdkBytes.fromByteArray(chunk)).build());
						}
					} catch (Throwable t) {
						if (done.compareAndSet(false, true)) {
							subscriber.onError(t);
						}
					}
				}

				@Override
				public void cancel() {
					done.set(true);
				}
			});
		}
	}

	@Override
	public GTranscriptModelType getType() {
		return type;
	}

	@Override
	public IGConfigurableTranscriptModel<GBedrockTranscriptModelConfig> create(GBedrockTranscriptModelConfig config)
			throws LLMConfigException {
		BedrockTranscribeConfigurableTranscriptModel model = new BedrockTranscribeConfigurableTranscriptModel();
		model.initialize(config, type);
		return model;
	}

	@Override
	public OperationStatus<List<GBedrockTranscriptModelChoice>> getModelChoices(GBedrockTranscriptModelConfig config) {
		// Amazon Transcribe exposes no model catalogue; the "model" is a language +
		// encoding combination configured on the transcript configuration itself.
		GBedrockTranscriptModelChoice choice = new GBedrockTranscriptModelChoice();
		choice.setCode("amazon-transcribe-streaming");
		choice.setDescription("Amazon Transcribe streaming");
		return OperationStatus.of(List.of(choice));
	}

	@Override
	public GBedrockTranscriptModelConfig createBaseConfiguration(String presetModel) {
		GBedrockTranscriptModelConfig clean = new GBedrockTranscriptModelConfig();
		clean.setChoosedModel(getModelChoices(clean).getResult().get(0));
		clean.setLanguageCode("en-US");
		clean.setDescription("Amazon Transcribe speech to text");
		clean.setModelTypeCode(getType().getCode());
		return clean;
	}

	@Override
	public OperationStatus<GBedrockTranscriptModelConfig> insertAndConfigure(GBedrockTranscriptModelConfig config)
			throws GeboPersistenceException, LLMConfigException {
		return configureHandler.insertAndConfigure(config, type);
	}
}
