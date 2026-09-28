package ai.gebo.llms.abstraction.layer.services.impl;

import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.embedding.EmbeddingRequest;
import org.springframework.ai.embedding.EmbeddingResponse;

import ai.gebo.model.ModelType;
import ai.gebo.llms.abstraction.layer.model.GBaseEmbeddingModelConfig;

/**
 * An {@link EmbeddingModel} that records the usage of every call it forwards to the
 * provider model. It is what a configurable embedding model hands to its vector store
 * and to its callers, so ingestion, similarity search and direct embedding are all
 * accounted.
 * <p>
 * Only the methods the providers implement themselves are forwarded: {@link #call},
 * {@link #embed(Document)} and {@link #dimensions()}. Every other method keeps its
 * interface default, which ends in {@link #call} on THIS wrapper, so each batch of a
 * bulk embedding is recorded once, with the token usage the provider returned.
 * <p>
 * The recorder is looked up on every call, not captured: the model is built during
 * {@code initialize()}, before the runtime DAO has handed it the recorder, and a
 * model without one (a test or a detached instance) must keep working unrecorded.
 */
public class UsageRecordingEmbeddingModel implements EmbeddingModel {
	private static final Logger LOGGER = LoggerFactory.getLogger(UsageRecordingEmbeddingModel.class);
	private final EmbeddingModel delegate;
	private final Supplier<? extends GBaseEmbeddingModelConfig> config;
	private final Supplier<LLMUsageRecorder> recorder;

	public UsageRecordingEmbeddingModel(EmbeddingModel delegate, Supplier<? extends GBaseEmbeddingModelConfig> config,
			Supplier<LLMUsageRecorder> recorder) {
		this.delegate = delegate;
		this.config = config;
		this.recorder = recorder;
	}

	/** The provider model this wrapper forwards to. */
	public EmbeddingModel getDelegate() {
		return delegate;
	}

	@Override
	public EmbeddingResponse call(EmbeddingRequest request) {
		LLMUsageRecorder usageRecorder = recorder.get();
		if (usageRecorder == null) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Embedding call not recorded: no usage recorder attached to model="
						+ codeOf(config.get()));
			}
			return delegate.call(request);
		}
		LLMUsageRecorder.Call call = usageRecorder.begin(config.get(), ModelType.EMBEDDING);
		try {
			EmbeddingResponse response = delegate.call(request);
			call.success(response != null && response.getMetadata() != null ? response.getMetadata().getUsage()
					: null);
			return response;
		} catch (RuntimeException e) {
			call.failure();
			throw e;
		}
	}

	@Override
	public float[] embed(Document document) {
		// Providers implement this one directly, so it never reaches call(): recorded
		// here, without tokens since the provider does not return them on this path.
		LLMUsageRecorder usageRecorder = recorder.get();
		if (usageRecorder == null) {
			return delegate.embed(document);
		}
		LLMUsageRecorder.Call call = usageRecorder.begin(config.get(), ModelType.EMBEDDING);
		try {
			float[] embedding = delegate.embed(document);
			call.success();
			return embedding;
		} catch (RuntimeException e) {
			call.failure();
			throw e;
		}
	}

	@Override
	public int dimensions() {
		// Forwarded, never defaulted: the interface default embeds a probe text, which
		// would be a billed call recorded as usage. The providers answer it from a
		// cache or a known dimensions table.
		return delegate.dimensions();
	}

	@Override
	public String getEmbeddingContent(Document document) {
		return delegate.getEmbeddingContent(document);
	}

	private static String codeOf(GBaseEmbeddingModelConfig config) {
		return config != null ? config.getCode() : null;
	}
}
