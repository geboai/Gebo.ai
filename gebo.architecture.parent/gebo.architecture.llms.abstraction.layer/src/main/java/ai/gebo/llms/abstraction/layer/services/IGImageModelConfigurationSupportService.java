package ai.gebo.llms.abstraction.layer.services;

import ai.gebo.llms.abstraction.layer.model.GBaseImageModelChoice;
import ai.gebo.llms.abstraction.layer.model.GBaseImageModelConfig;
import ai.gebo.llms.abstraction.layer.model.GChatModelType;
import ai.gebo.llms.abstraction.layer.model.GImageModelType;

public interface IGImageModelConfigurationSupportService<ModelChoice extends GBaseImageModelChoice, ModelConfig extends GBaseImageModelConfig>
		extends
		IGModelConfigurationSupportService<GImageModelType, ModelChoice, ModelConfig, IGConfigurableImageModel> {
	public IGConfigurableImageModel create(ModelConfig config) throws LLMConfigException;

}
