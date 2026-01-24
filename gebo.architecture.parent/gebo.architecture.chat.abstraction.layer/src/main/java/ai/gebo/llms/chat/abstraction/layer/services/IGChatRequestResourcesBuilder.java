package ai.gebo.llms.chat.abstraction.layer.services;

import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.GeboChatRequest;
import ai.gebo.llms.chat.abstraction.layer.llmexchange.model.LLMChatRequestResources;
import ai.gebo.llms.chat.abstraction.layer.model.GUserChatContext;

public interface IGChatRequestResourcesBuilder {
	public LLMChatRequestResources buildRequestResources(GeboChatRequest lastRequest, GUserChatContext actualContext,
			int tokensBudget);
}
