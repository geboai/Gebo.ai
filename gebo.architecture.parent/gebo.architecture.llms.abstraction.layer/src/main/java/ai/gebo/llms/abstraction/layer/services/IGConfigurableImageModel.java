package ai.gebo.llms.abstraction.layer.services;

import ai.gebo.llms.abstraction.layer.model.GBaseImageModelConfig;
import ai.gebo.llms.abstraction.layer.model.GChatModelType;

public interface IGConfigurableImageModel<ModelConfig extends GBaseImageModelConfig>
		extends IGConfigurableModel<ModelConfig, GChatModelType> {

}
