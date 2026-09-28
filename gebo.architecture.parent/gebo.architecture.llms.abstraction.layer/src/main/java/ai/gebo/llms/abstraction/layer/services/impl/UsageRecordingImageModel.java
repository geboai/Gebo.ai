package ai.gebo.llms.abstraction.layer.services.impl;

import org.springframework.ai.image.ImageModel;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;

import ai.gebo.model.ModelType;
import ai.gebo.llms.abstraction.layer.model.GModelPricingConditions;
import ai.gebo.llms.abstraction.layer.model.GBaseImageModelConfig;
import ai.gebo.llms.abstraction.layer.model.GImageModelType;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableImageModel;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;

/**
 * Records the usage of every generation of the image model it wraps, with the
 * token usage the provider returns, if any. Both entry points are covered:
 * {@link #call(ImagePrompt)} and the {@link ImageModel} handed out by
 * {@link #getImageModel()}, which is what {@code call} uses by default. Installed by
 * the runtime DAO around every model it registers.
 */
public class UsageRecordingImageModel<ModelConfig extends GBaseImageModelConfig>
		implements IGConfigurableImageModel<ModelConfig> {
	private final IGConfigurableImageModel<ModelConfig> delegate;
	private final LLMUsageRecorder recorder;

	public UsageRecordingImageModel(IGConfigurableImageModel<ModelConfig> delegate, LLMUsageRecorder recorder) {
		this.delegate = delegate;
		this.recorder = recorder;
	}

	/** The provider model this wrapper forwards to. */
	public IGConfigurableImageModel<ModelConfig> getDelegate() {
		return delegate;
	}

	@Override
	public ImageModel getImageModel() {
		ImageModel model = delegate.getImageModel();
		if (model == null) {
			return null;
		}
		// Read at every call: the delegate rebuilds its model when reconfigured.
		return request -> recorded(() -> model.call(request));
	}

	@Override
	public ImageResponse call(ImagePrompt request) {
		// The delegate's own call(), which a provider may override; not getImageModel(),
		// which would record the same generation twice.
		return recorded(() -> delegate.call(request));
	}

	private ImageResponse recorded(java.util.function.Supplier<ImageResponse> generation) {
		LLMUsageRecorder.Call call = recorder.begin(delegate.getConfig(), ModelType.IMAGE, delegate::getPricingConditions);
		try {
			ImageResponse response = generation.get();
			call.success(response != null && response.getMetadata() != null ? response.getMetadata().getUsage()
					: null);
			return response;
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
	public GImageModelType getType() {
		return delegate.getType();
	}

	@Override
	public void initialize(ModelConfig config, GImageModelType type) throws LLMConfigException {
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
