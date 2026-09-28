package ai.gebo.llms.abstraction.layer.services.impl;

import java.io.IOException;
import java.io.InputStream;

import ai.gebo.model.ModelType;
import ai.gebo.llms.abstraction.layer.model.GModelPricingConditions;
import ai.gebo.llms.abstraction.layer.model.GBaseTranscriptModelConfig;
import ai.gebo.llms.abstraction.layer.model.GTranscriptModelType;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableTranscriptModel;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;

/**
 * Records the usage of every transcription call of the transcript model it wraps.
 * Transcription has no token accounting, so a record carries the call, its response time
 * and its outcome. Installed by the runtime DAO around every model it registers, so
 * it covers every provider whatever class it extends.
 */
public class UsageRecordingTranscriptModel<ModelConfig extends GBaseTranscriptModelConfig>
		implements IGConfigurableTranscriptModel<ModelConfig> {
	private final IGConfigurableTranscriptModel<ModelConfig> delegate;
	private final LLMUsageRecorder recorder;

	public UsageRecordingTranscriptModel(IGConfigurableTranscriptModel<ModelConfig> delegate,
			LLMUsageRecorder recorder) {
		this.delegate = delegate;
		this.recorder = recorder;
	}

	/** The provider model this wrapper forwards to. */
	public IGConfigurableTranscriptModel<ModelConfig> getDelegate() {
		return delegate;
	}

	@Override
	public String call(InputStream audioResource) throws LLMConfigException, IOException {
		LLMUsageRecorder.Call call = recorder.begin(delegate.getConfig(), ModelType.TRANSCRIPT, delegate::getPricingConditions);
		try {
			String transcript = delegate.call(audioResource);
			call.success();
			return transcript;
		} catch (LLMConfigException | IOException | RuntimeException e) {
			call.failure();
			throw e;
		}
	}

	@Override
	public String getCode() {
		return delegate.getCode();
	}

	@Override
	public String getDescription() {
		return delegate.getDescription();
	}

	@Override
	public GTranscriptModelType getType() {
		return delegate.getType();
	}

	@Override
	public void initialize(ModelConfig config, GTranscriptModelType type) throws LLMConfigException {
		delegate.initialize(config, type);
	}

	@Override
	public void reconfigure(ModelConfig config) throws LLMConfigException {
		delegate.reconfigure(config);
	}

	@Override
	public ModelConfig getConfig() {
		return delegate.getConfig();
	}

	@Override
	public GModelPricingConditions getPricingConditions() {
		return delegate.getPricingConditions();
	}

	@Override
	public GModelPricingConditions getConfiguredPricingConditions() {
		return delegate.getConfiguredPricingConditions();
	}

	@Override
	public void delete() throws LLMConfigException {
		delegate.delete();
	}
}
