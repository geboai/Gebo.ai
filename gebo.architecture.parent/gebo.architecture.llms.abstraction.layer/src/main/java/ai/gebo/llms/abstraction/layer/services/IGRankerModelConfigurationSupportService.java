package ai.gebo.llms.abstraction.layer.services;

import ai.gebo.llms.abstraction.layer.model.GBaseRankerModelChoice;
import ai.gebo.llms.abstraction.layer.model.GBaseRankerModelConfig;
import ai.gebo.llms.abstraction.layer.model.GRankerModelType;

public interface IGRankerModelConfigurationSupportService<ModelChoice extends GBaseRankerModelChoice, ModelConfig extends GBaseRankerModelConfig>
		extends
		IGModelConfigurationSupportService<GRankerModelType, ModelChoice, ModelConfig, IGConfigurableRankerModel> {

	IGConfigurableRankerModel create(ModelConfig config) throws LLMConfigException;
}
