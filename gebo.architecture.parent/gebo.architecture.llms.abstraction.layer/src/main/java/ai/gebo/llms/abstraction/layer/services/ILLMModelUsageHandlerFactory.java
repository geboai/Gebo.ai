package ai.gebo.llms.abstraction.layer.services;

import ai.gebo.llms.abstraction.layer.model.GBaseModelConfig;

public interface ILLMModelUsageHandlerFactory<ModelType extends GBaseModelConfig> {
	public ILLMModelUsageHandler<ModelType> create(ModelType config);
}
