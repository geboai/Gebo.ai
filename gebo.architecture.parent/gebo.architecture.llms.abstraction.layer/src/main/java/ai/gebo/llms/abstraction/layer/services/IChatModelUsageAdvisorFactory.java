package ai.gebo.llms.abstraction.layer.services;

import java.util.function.Supplier;

import org.springframework.ai.chat.model.ChatModel;

import ai.gebo.llms.abstraction.layer.model.GBaseChatModelConfig;
import ai.gebo.llms.abstraction.layer.model.GModelPricingConditions;

public interface IChatModelUsageAdvisorFactory extends ILLMModelUsageHandlerFactory<GBaseChatModelConfig> {
	@Override
	public IChatModelUsageAdvisor create(GBaseChatModelConfig config);

	/**
	 * Creates the usage advisor of a chat model whose calls are priced with the given
	 * pricing conditions, the model's {@code IGConfigurableModel.getPricingConditions()}.
	 * The default ignores the pricing.
	 */
	public default IChatModelUsageAdvisor create(GBaseChatModelConfig config,
			Supplier<GModelPricingConditions> pricing) {
		return create(config);
	}

	/**
	 * Wraps a raw chat model so that calls made on it directly, bypassing the
	 * ChatClient and so the advisor from {@link #create(GBaseChatModelConfig)}, are
	 * recorded as usage too. The default records nothing.
	 */
	public default ChatModel recording(ChatModel model, GBaseChatModelConfig config) {
		return model;
	}

	/**
	 * As {@link #recording(ChatModel, GBaseChatModelConfig)}, pricing the calls with
	 * the model's pricing conditions. The default ignores the pricing.
	 */
	public default ChatModel recording(ChatModel model, GBaseChatModelConfig config,
			Supplier<GModelPricingConditions> pricing) {
		return recording(model, config);
	}
}
