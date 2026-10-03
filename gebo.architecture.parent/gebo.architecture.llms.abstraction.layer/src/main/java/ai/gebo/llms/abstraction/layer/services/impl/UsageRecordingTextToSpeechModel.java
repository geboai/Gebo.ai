package ai.gebo.llms.abstraction.layer.services.impl;

import java.io.InputStream;

import ai.gebo.model.ModelType;
import ai.gebo.llms.abstraction.layer.model.GModelPricingConditions;
import ai.gebo.llms.abstraction.layer.model.GBaseTextToSpeachModelConfig;
import ai.gebo.llms.abstraction.layer.model.GTextToSpeechModelType;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableTextToSpeechModel;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;

/**
 * Records the usage of every speech synthesis call of the text to speech model it
 * wraps. Speech has no token accounting, so a record carries the call, its response time
 * and its outcome. Installed by the runtime DAO around every model it registers, so
 * it covers every provider whatever class it extends.
 */
public class UsageRecordingTextToSpeechModel<ModelConfig extends GBaseTextToSpeachModelConfig>
		implements IGConfigurableTextToSpeechModel<ModelConfig> {
	private final IGConfigurableTextToSpeechModel<ModelConfig> delegate;
	private final LLMUsageRecorder recorder;

	public UsageRecordingTextToSpeechModel(IGConfigurableTextToSpeechModel<ModelConfig> delegate,
			LLMUsageRecorder recorder) {
		this.delegate = delegate;
		this.recorder = recorder;
	}

	/** The provider model this wrapper forwards to. */
	public IGConfigurableTextToSpeechModel<ModelConfig> getDelegate() {
		return delegate;
	}

	@Override
	public InputStream call(String text) {
		LLMUsageRecorder.Call call = recorder.begin(delegate.getConfig(), LLMUsageRecorder.safeProviderId(delegate::getProviderId), ModelType.TTS,
				delegate::getPricingConditions);
		try {
			InputStream audio = delegate.call(text);
			call.success();
			return audio;
		} catch (RuntimeException e) {
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
	public GTextToSpeechModelType getType() {
		return delegate.getType();
	}

	@Override
	public void initialize(ModelConfig config, GTextToSpeechModelType type) throws LLMConfigException {
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
	public GModelPricingConditions getProviderApiPricingConditions() {
		return delegate.getProviderApiPricingConditions();
	}

	@Override
	public void delete() throws LLMConfigException {
		delegate.delete();
	}
}
