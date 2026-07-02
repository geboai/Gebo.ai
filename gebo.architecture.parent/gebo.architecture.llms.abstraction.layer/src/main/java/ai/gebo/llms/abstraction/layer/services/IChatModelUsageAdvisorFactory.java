package ai.gebo.llms.abstraction.layer.services;

import ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig;

public interface IChatModelUsageAdvisorFactory extends ILLMModelUsageHandlerFactory<GBaseChatModelConfig> {
	@Override
	public IChatModelUsageAdvisor create(GBaseChatModelConfig config);
}
