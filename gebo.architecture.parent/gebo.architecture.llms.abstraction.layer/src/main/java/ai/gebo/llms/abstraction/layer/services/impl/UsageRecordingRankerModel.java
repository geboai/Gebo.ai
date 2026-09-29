package ai.gebo.llms.abstraction.layer.services.impl;

import ai.gebo.model.ModelType;
import ai.gebo.llms.abstraction.layer.model.GModelPricingConditions;
import ai.gebo.llms.abstraction.layer.model.GBaseRankerModelConfig;
import ai.gebo.llms.abstraction.layer.model.GRankerModelType;
import ai.gebo.llms.abstraction.layer.services.IGConfigurableRankerModel;
import ai.gebo.llms.abstraction.layer.services.LLMConfigException;
import ai.gebo.ranker.model.RankerModel;
import ai.gebo.ranker.model.RankingOutput;

/**
 * Records the usage of every ranking call of the ranker model it wraps, through the
 * {@link RankerModel} handed out by {@link #getRankerModel()}, the only way a ranker
 * is invoked. Ranking has no token accounting, so a record carries the call, its
 * response time and its outcome. Installed by the runtime DAO around every model it
 * registers.
 */
public class UsageRecordingRankerModel<ModelConfig extends GBaseRankerModelConfig>
		implements IGConfigurableRankerModel<ModelConfig> {
	private final IGConfigurableRankerModel<ModelConfig> delegate;
	private final LLMUsageRecorder recorder;

	public UsageRecordingRankerModel(IGConfigurableRankerModel<ModelConfig> delegate, LLMUsageRecorder recorder) {
		this.delegate = delegate;
		this.recorder = recorder;
	}

	/** The provider model this wrapper forwards to. */
	public IGConfigurableRankerModel<ModelConfig> getDelegate() {
		return delegate;
	}

	@Override
	public RankerModel getRankerModel() {
		RankerModel model = delegate.getRankerModel();
		if (model == null) {
			return null;
		}
		return input -> {
			LLMUsageRecorder.Call call = recorder.begin(delegate.getConfig(), ModelType.RANKER, delegate::getPricingConditions);
			try {
				RankingOutput output = model.call(input);
				call.success();
				return output;
			} catch (RuntimeException e) {
				call.failure();
				throw e;
			}
		};
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
	public GRankerModelType getType() {
		return delegate.getType();
	}

	@Override
	public void initialize(ModelConfig config, GRankerModelType type) throws LLMConfigException {
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
