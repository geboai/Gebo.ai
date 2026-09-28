package ai.gebo.llms.abstraction.layer.services;

import org.springframework.ai.chat.model.ChatModel;

import ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig;

public interface IChatModelUsageAdvisorFactory extends ILLMModelUsageHandlerFactory<GBaseChatModelConfig> {
	@Override
	public IChatModelUsageAdvisor create(GBaseChatModelConfig config);

	/**
	 * Wraps a raw chat model so that calls made on it directly, bypassing the
	 * ChatClient and so the advisor from {@link #create(GBaseChatModelConfig)}, are
	 * recorded as usage too. The default records nothing.
	 */
	public default ChatModel recording(ChatModel model, GBaseChatModelConfig config) {
		return model;
	}
}
